import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class World {
	public int width;
	public int height;
	public Parameter parameter;
	public Output output;

	public Map<Location, ArrayList<Person>> people = new HashMap<Location, ArrayList<Person>>();
	public Patch[][] patches = new Patch[50][50];

	public World(int width, int height, Parameter parameter) {
		super();
		this.width = width;
		this.height = height;
		this.parameter = parameter;
		patches = new Patch[width][height];
	}

	public void setupWorld() {
		setupPatch();
		setupPeople(parameter.numOfPeople);
	}

	public void run(int ticknum) {
		Location location;

		ArrayList<Person> allPeople = new ArrayList<Person>();
		float averageGrains = 0;
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				location = new Location(i, j);
				ArrayList<Person> list = people.get(location);
				// System.out.println(list.size());
				if (ticknum % parameter.growInterval == 0) {
					if (patches[i][j].grains + parameter.numOfGrow < patches[i][j].maxGrains) {
						patches[i][j].grow(parameter.numOfGrow);
					} else {
						patches[i][j].grains = patches[i][j].maxGrains;
					}
				}

				if (list.size() != 0) {
					averageGrains = patches[i][j].grains / list.size();
					// System.out.println(averageGrains);
					for (Person p : list) {
						p.harvest(averageGrains);
						patches[i][j].harvest();
						p.moveEatAgeDie(getRichest(), getPoorest(), parameter, this);
						// System.out.println(p.toString());
						allPeople.add(p);
					}
				}

			}
		}
		saveNewWorld(allPeople);

	}

	private void setupPatch() {
		Random random = new Random();
		Location location;
		// set best land
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				ArrayList<Person> list = new ArrayList<Person>();
				location = new Location(i, j);
				// each location has one list, at start it is empty
				people.put(location, list);
				if (random.nextFloat() <= parameter.percentBestLand) {
					patches[i][j] = new Patch(parameter.MAXGRAIN, parameter.MAXGRAIN);
				} else {
					patches[i][j] = new Patch(0, 0);
				}
			}
		}

		for (int n = 0; n < 5; n++) {
			for (int i = 0; i < width; i++) {
				for (int j = 0; j < height; j++) {
					if (patches[i][j].maxGrains != 0) {
						patches[i][j].grains = patches[i][j].maxGrains;
						diffuse(i, j, 0.25);
					}
				}
			}
		}

		for (int n = 0; n < 10; n++) {
			for (int i = 0; i < width; i++) {
				for (int j = 0; j < height; j++) {
					if(patches[i][j].grains!=0){
						diffuse(i, j, 0.25);
					}	
				}
			}
		}
		
		for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
            	if (patches[i][j].grains != 0) {
					patches[i][j].maxGrains = patches[i][j].grains;
				}
            	System.out.print(patches[i][j].grains+" ");
            }
            System.out.println();
        } 

	}

	public void diffuse(int i, int j, double percent) {
		float average = (float) (patches[i][j].grains * percent / 8);
		patches[i][j].grains *= percent;

		int left = i - 1 >= 0 ? i - 1 : width - 1;
		int right = i + 1 < width ? i + 1 : 0;
		int up = j + 1 < height ? j + 1 : 0;
		int down = j - 1 >= 0 ? j - 1 : height - 1;
		
		patches[left][up].grains += average;
		patches[i][up].grains += average;
		patches[right][up].grains += average;
		patches[right][j].grains += average;
		patches[right][down].grains += average;
		patches[i][down].grains += average;
		patches[left][down].grains += average;
		patches[left][j].grains += average;	

	}

	private void setupPeople(int num) {
		Random random = new Random();
		Person person;
		Location location;
		int vision;
		int metabolism;
		int life;
		float wealth;
		int age;
		for (int n = 0; n < num; n++) {
			location = new Location(random.nextInt(width), random.nextInt(height));
			vision = 1 + random.nextInt(parameter.maxVision);
			metabolism = 1 + random.nextInt(parameter.maxMetabolism);
			life = parameter.minLifeSpan + random.nextInt(1 + parameter.maxLifeSpan - parameter.minLifeSpan);
			wealth = metabolism + random.nextInt(parameter.MAXGRAIN);
			age = 0;
			person = new Person(n, location, vision, metabolism, life, wealth, age);
			// add person to the people list of this location

			ArrayList<Person> newlist = people.get(location);
			newlist.add(person);
			people.put(location, newlist);
		}

	}

	public void saveNewWorld(ArrayList<Person> allPeople) {
		people.clear();
		Location location;
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				location = new Location(i, j);
				ArrayList<Person> list = new ArrayList<Person>();
				// each location has one list, at start it is empty
				people.put(location, list);
			}
		}

		for (Person p : allPeople) {
			// System.out.println(p.location.toString());
			people.get(p.location).add(p);
		}
	}

	/**
	 * get the richest person
	 * 
	 * @return
	 */
	public float getPoorest() {
		Location location;
		float poorest = (float) 1000000.0;
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				location = new Location(i, j);
				ArrayList<Person> list = people.get(location);
				for (Person p : list) {
					if (p.wealth < poorest) {
						poorest = p.wealth;
					}
				}
			}
		}
		return poorest;
	}

	/**
	 * get the poorest person
	 * 
	 * @return
	 */
	public float getRichest() {
		Location location;
		float richest = (float) 0.0;
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				location = new Location(i, j);
				ArrayList<Person> list = people.get(location);
				for (Person p : list) {
					if (p.wealth > richest) {
						richest = p.wealth;
					}
				}
			}
		}
		return richest;
	}

}

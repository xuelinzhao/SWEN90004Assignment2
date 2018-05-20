import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class Output {
	public World world;
	public Parameter parameter;
	private File file;

	public Output(World world, Parameter parameter) {
		super();
		this.world = world;
		this.parameter = parameter;
		try {
			file = new File("output.csv");
			if (file.exists()) {
				file.delete();
				file.createNewFile();
			} else {
				file.createNewFile();
			}
			String title = "Low,Mid,High,LowAverage,MidAverage,HighAverage,MaxWealth,MinWealth";
			generateCSV(title);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void printResult(World world, int tick) {
		ArrayList<Person> allPeople = new ArrayList<Person>();
		getAllPeople(allPeople);
		String data = countLevel(allPeople);
		generateCSV(data);
	}

	public void generateCSV(String data) {
		try {
			FileWriter fw = new FileWriter(file, true);
			// BufferedWriter writer give better performance
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(data);
			bw.write(System.getProperty("line.separator"));
			// closing BufferedWriter Stream
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		lorenzOutput();
		giniOutput();
	}

	public void getAllPeople(ArrayList<Person> allPeople) {
		for (int i = 0; i < world.width; i++) {
			for (int j = 0; j < world.height; j++) {
				Location location = new Location(i, j);
				ArrayList<Person> list = world.people.get(location);
				if (list.size() != 0) {
					for (Person p : list) {
						allPeople.add(p);
					}
				}
			}
		}
		// System.out.println("AllPeople:"+allPeople.size());
	}

	int lowNum = 0;
	int midNum = 0;
	int highNum = 0;
	ArrayList<Person> richPeople = new ArrayList<Person>();
	ArrayList<Person> middlePeople = new ArrayList<Person>();
	ArrayList<Person> poorPeople = new ArrayList<Person>();

	public String countLevel(ArrayList<Person> allPeople) {
		for (Person p : allPeople) {
			if (p.wealth <= world.getRichest() / 3) {
				poorPeople.add(p);
				lowNum++;
			} else if ((p.wealth > world.getRichest() / 3) && (p.wealth <= world.getRichest() * 2 / 3)) {
				middlePeople.add(p);
				midNum++;
			} else {
				richPeople.add(p);
				highNum++;
			}
		}
		String output = "";
		float lowNumber = (float) lowNum / parameter.numOfPeople;
		float midNumber = (float) midNum / parameter.numOfPeople;
		float highNumber = (float) highNum / parameter.numOfPeople;
		float minWealth = sortList(allPeople).get(0);
		float maxWealth = sortList(allPeople).get(allPeople.size() - 1);
		output += "Low:" + (float) lowNumber * 100 + "%";
		output += " Mid:" + (float) midNumber * 100 + "%";
		output += " High:" + (float) highNumber * 100 + "%";
		output += " LowAverage:" + calculateAverage(poorPeople);
		output += " MidAverage:" + calculateAverage(middlePeople);
		output += " HighAverage:" + calculateAverage(richPeople);
		output += " MaxWealth:" + maxWealth;
		output += " MinWealth:" + minWealth;
		String csvData = lowNumber + "," + midNumber + "," + highNumber + "," + calculateAverage(poorPeople) + ","
				+ calculateAverage(middlePeople) + "," + calculateAverage(richPeople) + "," + maxWealth + ","
				+ minWealth;
		System.out.println(output);
		poorPeople.clear();
		middlePeople.clear();
		poorPeople.clear();
		lowNum = 0;
		midNum = 0;
		highNum = 0;
		return csvData;
	}

	public float calculateAverage(ArrayList<Person> peopleClass) {
		float total = 0;
		for (Person p : peopleClass) {
			total += p.wealth;
		}
		return total / peopleClass.size();
	}

	public ArrayList<Float> sortList(ArrayList<Person> allpeople) {
		ArrayList<Float> floatList = new ArrayList<Float>();

		for (Person p : allpeople) {
			floatList.add(p.wealth);
		}
		Collections.sort(floatList);
		return floatList;
	}

	public void lorenzOutput() {

	}

	public void giniOutput() {

	}

}

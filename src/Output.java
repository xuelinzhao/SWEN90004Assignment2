import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Output the results after one tick
 * 
 * @author Xuelin Zhao&Feng Zhao
 * student ID: 801736	838219
 *
 */
public class Output {
	public World world;
	public Parameter parameter;
	private File PeopleFile;
	private File LorenzFile;

	public Output(World world, Parameter parameter) {
		super();
		this.world = world;
		this.parameter = parameter;
		try {
			PeopleFile = new File("output.csv");
			LorenzFile = new File("lorenz.csv");
			if (PeopleFile.exists()) {
				PeopleFile.delete();
				PeopleFile.createNewFile();
			} else {
				PeopleFile.createNewFile();
			}
			if (LorenzFile.exists()) {
				LorenzFile.delete();
				LorenzFile.createNewFile();
			} else {
				LorenzFile.createNewFile();
			}
			String title = "Low,Mid,High,LowPercent,MidPercent,HighPercent," +
					"LowAverage,MidAverage,HighAverage,MaxWealth,MinWealth,Gini";
			generateCSV(title, PeopleFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * print the results and use the results to generate CSV files
	 * 
	 * @param world
	 * @param tick
	 */
	public void printResult(World world, int tick) {
		ArrayList<Person> allPeople = new ArrayList<Person>();
		float[] lorenzPoints = new float[100];
		double gini = 0;
		getAllPeople(allPeople);
		String peopleData = countLevel(allPeople);
		String lorenzData = "";
		lorenzPoints = lorenzOutput(sortList(allPeople));
		for (float f : lorenzPoints) {
			lorenzData += f + ",";
		}
		gini = giniOutput(lorenzPoints);
		// System.out.println(gini+"!!!");
		peopleData = peopleData + "," + gini;
		generateCSV(peopleData, PeopleFile);
		generateCSV(lorenzData, LorenzFile);
	}

	/**
	 * genetate CSV files
	 * 
	 * @param data
	 * @param file
	 */
	public void generateCSV(String data, File file) {
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

	}

	/**
	 * get all people in this world
	 * 
	 * @param allPeople
	 */
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
	}

	int lowNum = 0;
	int midNum = 0;
	int highNum = 0;
	ArrayList<Person> richPeople = new ArrayList<Person>();
	ArrayList<Person> middlePeople = new ArrayList<Person>();
	ArrayList<Person> poorPeople = new ArrayList<Person>();

	/**
	 * count the level of all people
	 * 
	 * @param allPeople
	 * @return
	 */
	public String countLevel(ArrayList<Person> allPeople) {
		for (Person p : allPeople) {
			if (p.wealth <= world.getRichest() / 3) {
				poorPeople.add(p);
				lowNum++;
			} else if ((p.wealth > world.getRichest() / 3) && (p.wealth <=
					world.getRichest() * 2 / 3)) {
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
		output += "Low:" + lowNum;
		output += " Mid:" + midNum;
		output += " High:" + highNum;
		output += " LowPercent:" + (float) lowNumber * 100 + "%";
		output += " MidPercent:" + (float) midNumber * 100 + "%";
		output += " HighPercent:" + (float) highNumber * 100 + "%";
		output += " LowAverage:" + calculateAverage(poorPeople);
		output += " MidAverage:" + calculateAverage(middlePeople);
		output += " HighAverage:" + calculateAverage(richPeople);
		output += " MaxWealth:" + maxWealth;
		output += " MinWealth:" + minWealth;
		String csvData = lowNum+","+midNum+","+highNum+","+lowNumber + "," +
				midNumber + "," + highNumber + "," + calculateAverage(poorPeople) + ","
				+ calculateAverage(middlePeople) + "," + calculateAverage(richPeople) +
				"," + maxWealth + "," + minWealth;
		System.out.println(output);
		poorPeople.clear();
		middlePeople.clear();
		poorPeople.clear();
		lowNum = 0;
		midNum = 0;
		highNum = 0;
		return csvData;
	}

	/**
	 * calculate the average wealth of one level
	 * 
	 * @param peopleClass
	 * @return
	 */
	public float calculateAverage(ArrayList<Person> peopleClass) {
		float total = 0;
		for (Person p : peopleClass) {
			total += p.wealth;
		}
		return total / peopleClass.size();
	}

	/**
	 * sort people list to wealth list ascend
	 * 
	 * @param allpeople
	 * @return
	 */
	public ArrayList<Float> sortList(ArrayList<Person> allpeople) {
		ArrayList<Float> floatList = new ArrayList<Float>();

		for (Person p : allpeople) {
			floatList.add(p.wealth);
		}
		Collections.sort(floatList);
		return floatList;
	}

	/**
	 * calculate the lorenz points
	 * 
	 * @param sortedList
	 * @return
	 */
	public float[] lorenzOutput(ArrayList<Float> sortedList) {
		float totalWealth = 0;
		float sumWealth = 0;
		float[] lorenzPoints = new float[101];
		for (Float f : sortedList) {
			totalWealth += f;
		}

		for(int l = 0;l<=100;l++){
			for (int i = 0; i < (int)(0.01*l*parameter.numOfPeople); i++) {
				sumWealth += sortedList.get(i);
			}
			lorenzPoints[l] = 100 * sumWealth / totalWealth;
			sumWealth=0;
		}

		return lorenzPoints;
	}

	/**
	 * calculate the Gini index
	 * 
	 * @param lorenzPoints
	 * @return gini index
	 */
	public double giniOutput(float[] lorenzPoints) {
        double preGini = 0;
        for(float f : lorenzPoints){
            preGini += f;
        }
        double gini = (5000-preGini)/5000;
        return gini;
	}

}

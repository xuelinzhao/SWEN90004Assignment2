
public class Output {

	public Person[] richPeople;
	public Person[] middlePeople;
	public Person[] poorPeople;

	public World world;
	public Parameter parameter;
	
	
	public Output(World world, Parameter parameter) {
		super();
		this.world = world;
		this.parameter = parameter;
	}

	public void printResult(World world,int tick){
		String output="";
		output += "Tick:"+tick;
		output += "Low:"+world.lowNum;
		output += "Mid:"+world.midNum;
		output += "High:"+world.highNum;
		System.out.println(output);
	}
	
	public void generateCSV() {
		lorenzOutput();
		giniOutput();
	}
	
	public void lorenzOutput() {
		
	}
	
	public void giniOutput() {
		
	}
	
}

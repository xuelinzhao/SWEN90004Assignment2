
public class Parameter {
	public final static int WORLDWIDTH = 50;
	public final static int WORLDHEIGHT = 50;
	public final static int MAXGRAIN = 50;
	
	/*
	 * The parameters need to input by user
	 */
	public int numOfPeople;
	public int maxVision;
	public int maxMetabolism;
	public int minLifeSpan;
	public int maxLifeSpan;
	public float percentBestLand;
	public int growInterval;
	public int numOfGrow;
	
	// The max tick times
	public int tickNum;
	
	public Parameter(String[] args){
		numOfPeople = Integer.parseInt(args[0]);
		maxVision = Integer.parseInt(args[1]);
		maxMetabolism = Integer.parseInt(args[2]);
		minLifeSpan = Integer.parseInt(args[3]);
		maxLifeSpan = Integer.parseInt(args[4]);
		percentBestLand = Float.parseFloat(args[5]);
		growInterval = Integer.parseInt(args[6]);
		numOfGrow = Integer.parseInt(args[7]);
		tickNum = Integer.parseInt(args[8]);
	}
	
	
	
}

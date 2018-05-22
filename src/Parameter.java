import com.sun.org.glassfish.external.arc.Taxonomy;

/**
 * The Parameter class, which contains the input parameters and constant
 * parameters
 * 
 * @author Xuelin Zhao&Feng Zhao
 *
 */
public class Parameter {
	public final static int WORLDWIDTH = 50;
	public final static int WORLDHEIGHT = 50;
	public final static int MAXGRAIN = 50;
	public final static boolean INHERITANCE_ENABLE = false;
	public final static float INHERITANCE_PERCENT = (float) 0.8;
	public final static boolean TAX_ENABLE = false;
	public final static float TAX_HIGH_PERCENT = (float) 0.5;
	public final static float TAX_MID_PERCENT = (float) 0.3;
	public final static float TAX_LOW_PERCENT = (float) 0.1;

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

	public Parameter(String[] args) {
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

/**
 * The Simulator of the model, tick and run the world
 * 
 * @author Xuelin Zhao&Feng Zhao
 * student ID: 801736	838219
 *
 */
public class Simulator {
	private World world;
	private Parameter parameter;
	private Output output;
	private int tickNum;

	public static void main(String[] args) {
		Parameter para = new Parameter(args);
		Simulator simulator = new Simulator();
		simulator.setup(para);
		simulator.run();

	}

	/**
	 * set up the world
	 * 
	 * @param para
	 */
	public void setup(Parameter para) {
		parameter = para;
		tickNum = parameter.tickNum;
		world = new World(parameter.WORLDWIDTH, parameter.WORLDHEIGHT, parameter);
		world.setupWorld();
		output = new Output(world, parameter);
	}

	/**
	 * Run the simulation for tick number times
	 */
	public void run() {
		for (int i = 1; i <= tickNum; i++) {
			tick(i);
			System.out.println("Tick:" + i);
			output.printResult(world, i);
		}
	}

	/**
	 * One tick
	 */
	public void tick(int ticknum) {
		world.run(ticknum);
	}

}
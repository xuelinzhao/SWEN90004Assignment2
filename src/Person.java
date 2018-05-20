
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.TreeMap;


public class Person {
	public int ID;
	public Location location;
	public int vision;
	private float metabolism;
	private int lifeExpectancy;
	public float wealth;
	public int age;

	public Person(int ID, Location location, int vision, float metabolism, int lifeExpectancy, float wealth, int age) {
		super();
		this.ID = ID;
		this.location = location;
		this.vision = vision;
		this.metabolism = metabolism;
		this.lifeExpectancy = lifeExpectancy;
		this.wealth = wealth;
		this.age = age;
	}

	public void harvest(float grains) {
		wealth += grains;

	}

	public void moveEatAgeDie(float richest, float poorest, Parameter parameter, World world) {
//		if (ID == 1) {
//			System.out.println(this.toString());
//		}
		Random random = new Random();
		wealth -= metabolism;
		age += 1;
		// create new person when died
		if (age >= lifeExpectancy || wealth < 0) {
			// this.wealth = poorest + random.nextInt((int) (richest - poorest))
			// + random.nextFloat();
			// if (wealth>0) {
			// this.wealth = wealth/3;
			// }else{
			// this.wealth = 1 + random.nextInt(parameter.MAXGRAIN);
			// }
			this.age = 0;
			this.vision = 1 + random.nextInt(parameter.maxVision);
			this.metabolism = 1 + random.nextInt(parameter.maxMetabolism);
			this.wealth = metabolism + random.nextInt(parameter.MAXGRAIN);
			this.lifeExpectancy = parameter.minLifeSpan
					+ random.nextInt(1 + parameter.maxLifeSpan - parameter.minLifeSpan);
		} else {
			this.location = chooseNextLocation(world);
		}

	}

	public Location chooseNextLocation(World world) {
		Location next = location;
		// System.out.println(this.toString());
		int x = location.getX();
		int y = location.getY();
		float leftGrains = 0, rightGrains = 0, upGrains = 0, downGrains = 0;
		int left = x - vision >= 0 ? x - vision : 0;
		int right = x + vision <= 49 ? x + vision : 49;
		int up = y + vision <= 49 ? y + vision : 49;
		int down = y - vision >= 0 ? y - vision : 0;
		// get the grains in four directions
		for (int i = 1; i <= x - left; i++) {
			leftGrains += world.patches[x - i][y].grains;
		}
		for (int i = 1; i <= right - x; i++) {
			rightGrains += world.patches[x + i][y].grains;
		}
		for (int i = 1; i <= up - y; i++) {
			upGrains += world.patches[x][y + i].grains;
		}
		for (int i = 1; i <= y - down; i++) {
			downGrains += world.patches[x][y - i].grains;
		}

		Map<Float, String> direction = new TreeMap();
		direction.put(leftGrains, "left");
		direction.put(rightGrains, "right");
		direction.put(upGrains, "up");
		direction.put(downGrains, "down");

		String value = "";
		Set<Float> keySet = direction.keySet();
		Iterator<Float> iter = keySet.iterator();
		while (iter.hasNext()) {
			Float key = iter.next();
			// System.out.println(key + ":" + direction.get(key));
			value = direction.get(key);
			if (key == 0.0) {
				value = "here";
			}
		}
		// System.out.println(value);
		// System.out.println();

		// check the best direction
		switch (value) {
		case "left":
			next = new Location(x - 1, y);
			break;
		case "right":
			next = new Location(x + 1, y);
			break;
		case "up":
			next = new Location(x, y + 1);
			break;
		case "down":
			next = new Location(x, y - 1);
			break;
		case "here":
			next = location;
			break;
		default:
			next = location;
			break;
		}

		// System.out.println(next.toString());
		return next;
	}

//	public String getLevel(int richest) {
//		if (wealth <= 1 / 3 * richest) {
//			return "low";
//		} else if ((wealth > richest / 3) && (wealth <= richest * 2 / 3)) {
//			return "mid";
//		} else {
//			return "high";
//		}
//	}

	public String toString() {
		return "ID:" + ID + " Location:" + location.toString() + " Vision:" + vision + " Metabolism:" + metabolism
				+ " Life:" + lifeExpectancy + " Wealth:" + wealth + " Age:" + age;
	}

}

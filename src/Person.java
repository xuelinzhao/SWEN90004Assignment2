import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import com.sun.org.apache.regexp.internal.recompile;

import apple.laf.JRSUIConstants.Direction;

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
		Random random = new Random();
		wealth -= metabolism;
		age += 1;
		if (age >= lifeExpectancy || wealth < 0) {
			// create new person when died
			if (wealth > 0) {
				wealth = poorest + random.nextInt((int) (richest - poorest)) + random.nextFloat();
			}
			this.age = 0;
			this.vision = 1 + random.nextInt(parameter.maxVision);
			this.metabolism = 1 + random.nextInt(parameter.maxMetabolism);
			this.lifeExpectancy = parameter.minLifeSpan
					+ random.nextInt(1 + parameter.maxLifeSpan - parameter.minLifeSpan);
		} else {
			this.location = chooseNextLocation(world);
		}

	}

	public Location chooseNextLocation(World world) {
		Location next = location;
		int x = location.getX();
		int y = location.getY();
		float leftGrains=0,rightGrains=0,upGrains=0,downGrains = 0;
		int left = x - vision >= 0 ? x - vision : 0;
		int right = x + vision <= 49 ? x + vision : 49;
		int up = y + vision <= 49 ? y + vision : 49;
		int down = y - vision >= 0 ? y - vision : 0;
		// get the grains in four directions
		for(int i=1;i<=x-left;i++){
			leftGrains+=world.patches[x-i][y].grains;
		}
		for(int i=1;i<=right-x;i++){
			rightGrains+=world.patches[x+i][y].grains;
		}
		for(int i=1;i<=up-y;i++){
			upGrains+=world.patches[x][y+i].grains;
		}
		for(int i=1;i<=y-down;i++){
			downGrains+=world.patches[x][y-i].grains;
		}
		// check the best direction
		float maxGrains = 0;
		if (leftGrains>=maxGrains) {
			next = new Location(x-1, y);
			maxGrains = leftGrains;
			if(rightGrains>=maxGrains){
				next = new Location(x+1, y);
				maxGrains = rightGrains;
				if (upGrains>=maxGrains) {
					next = new Location(x, y+1);
					maxGrains = upGrains;
					if(downGrains>=maxGrains){
						next = new Location(x, y-1);
					}
				} 
			}
		}
		
		return next;
	}
	

	public String getLevel(int richest) {
		if (wealth <= 1 / 3 * richest) {
			return "low";
		} else if ((wealth > 1 / 3 * richest) && (wealth < 2 / 3 * richest)) {
			return "mid";
		} else {
			return "high";
		}
	}

}

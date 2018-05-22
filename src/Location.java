/**
 * The Location class, which contains the coordinate
 * 
 * @author Xuelin Zhao&Feng Zhao
 * student ID: 801736	838219
 *
 */
public class Location {
	private int x;
	private int y;

	public Location(int x, int y) {
		super();
		this.x = x;
		this.y = y;
	}

	/**
	 * override the compare function of Location
	 */
	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof Location)) {
			return false;
		}
		Location location = (Location) o;
		return location.x == x && location.y == y;
	}

	@Override
	public int hashCode() {
		int result = 17;
		result = 31 * result + x;
		result = 31 * result + y;
		return result;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	};

	public String toString() {
		return "X:" + x + " Y:" + y;
	}
}

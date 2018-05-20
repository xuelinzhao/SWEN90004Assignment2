
public class Patch {
	//how many grains in this patch
	public float grains;
	//number of grain grow
	public float growCount;
	
	public Patch(float grains, float growCount) {
		super();
		this.grains = grains;
		this.growCount = growCount;
	}

	public void grow() {
		grains += growCount;
	}
	
	public void harvest() {
		grains = 0;
	}
	
	
}

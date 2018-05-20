
public class Patch {
	//how many grains in this patch
	public float grains;
	public float maxGrains;
	
	public Patch(float grains, float maxGrains) {
		super();
		this.grains = grains;
		this.maxGrains = maxGrains;
	}

	public void grow(float growCount) {
		grains += growCount;
	}
	
	public void harvest() {
		grains = 0;
	}
	
	
}

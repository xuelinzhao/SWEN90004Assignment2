/**
 * The patch class, which has grains and maxGrains
 * @author Xuelin Zhao&Feng Zhao
 *
 */
public class Patch {
	//how many grains in this patch
	public float grains;
	//the max grains could contain in this patch
	public float maxGrains;
	
	public Patch(float grains, float maxGrains) {
		super();
		this.grains = grains;
		this.maxGrains = maxGrains;
	}

	/**
	 * the patch grow new grains
	 * @param growCount
	 */
	public void grow(float growCount) {
		grains += growCount;
	}
	
	/**
	 * the patch was harvested by person
	 */
	public void harvest() {
		grains = 0;
	}
	
	
}

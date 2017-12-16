package a4;

public class CenterPoint {
	protected double class_value;
	protected double[] sums;
	protected int[] cnts;
	protected int n;
	
	public CenterPoint(double cVal, int n) {
		this.n = n;
		this.class_value = cVal;
		this.sums = new double[n];
		this.cnts = new int[n];
	}
	
	public double getAvg(int attr) {
		return sums[attr] / (double)cnts[attr];
	}
	public double[] toArray() {
		double[] vals = new double[n];
		for(int i = 0; i < vals.length; i++) {
			vals[i] = getAvg(i);
		}
		return vals;
	}
}

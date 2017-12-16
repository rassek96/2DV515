package a4;

import java.util.ArrayList;

import core.Classifier;
import core.Dataset;
import core.Instance;
import core.Result;

public class RBFKernel implements Classifier {
	private Dataset data;
	private double offset;
	
	public RBFKernel() {
	}
	
	@Override
	public void train(Dataset data) {
		this.data = data;
		calcOffset();
	}
	private void calcOffset() {
		ArrayList<Instance> l0 = new ArrayList<>();
		ArrayList<Instance> l1 = new ArrayList<>();
		for(Instance inst : data.toList()) {
			if(inst.getClassAttribute().numericalValue() == 0.0) {
				l0.add(inst);
			}
			else if(inst.getClassAttribute().numericalValue() == 1.0) {
				l1.add(inst);
			}
		}
		double sum0 = 0;
		double sum1 = 0;
		for(int i1 = 0; i1 < l0.size(); i1++) {
			double[] v1 = l0.get(i1).getAttributeArrayNumerical();
			for(int i2 = 0; i2 < l0.size(); i2++) {
				double[] v2 = l0.get(i2).getAttributeArrayNumerical();
				sum0 += rbf(v1, v2);
			}
		}
		for(int i1 = 0; i1 < l1.size(); i1++) {
			double[] v1 = l1.get(i1).getAttributeArrayNumerical();
			for(int i2 = 0; i2 < l1.size(); i2++) {
				double[] v2 = l1.get(i2).getAttributeArrayNumerical();
				sum1 += rbf(v1, v2);
			}
		}
		offset = (1.0 / Math.pow(l1.size(), 2)) * sum1 - (1.0 / Math.pow(l0.size(), 2)) * sum0;
	}
	private double rbf(double[] v1, double[] v2) { 
		double gamma = 20;
		double sq_dist = 0;
		for(int i = 0; i < v1.length; i++) {
			sq_dist += Math.pow(v1[i] - v2[i], 2);
		}
		double rb = Math.pow(Math.E,  -gamma * sq_dist);
		return rb;
	}

	@Override
	public Result classify(Instance inst) {
		double[] sum = new double[2];
		double[] count = new double[2];
		double[] point = inst.getAttributeArrayNumerical();
		
		for(Instance i : data.toList()) {
			double[] vals = i.getAttributeArrayNumerical();
			int index = (int)i.getClassAttribute().numericalValue();
			sum[index] += rbf(point, vals);
			count[index]++;
		}
		double y = (1.0 / count[0]) * sum[0] - (1.0 / count[1]) * sum[1] + offset;
		if(y > 0) {
			return new Result(0);
		}
		else {
			return new Result(1);
		}
	}
	@Override
	public String toString() {
		return "rbf Kernel";
	}
}

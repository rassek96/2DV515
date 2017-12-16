package wekaA4;

import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.trees.J48;
import weka.core.Instances;
import weka.core.converters.ConverterUtils;

public class DecisionTree {
	private Instances data;
	private Classifier cl;
	
	public DecisionTree(String filename) {
		readData(filename);
		this.train();
		this.test();
	}
	
	private void train() {
		try {
			int lastIndex = data.numAttributes() - 1;
			data.setClassIndex(lastIndex);
			cl = new J48();
			cl.buildClassifier(data);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	private void test() {
		try {
			System.out.println("==========================");
			System.out.println("======== Weka J48 ========");
			Evaluation eval = new Evaluation(data);
			eval.crossValidateModel(cl, data, 10, new java.util.Random(1));
			System.out.println(eval.toSummaryString(false));
			System.out.println(eval.toMatrixString());
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	private void readData(String filename) {
		ConverterUtils.DataSource source;
		try {
			source = new ConverterUtils.DataSource(filename);
			data = source.getDataSet();
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}

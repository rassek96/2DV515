package wekaA4;

import weka.classifiers.Evaluation;
import weka.classifiers.functions.MultilayerPerceptron;
import weka.core.Instances;
import weka.core.converters.ConverterUtils;

public class NeuralNetwork {
	private Instances data;
	private MultilayerPerceptron mlP;
	
	public NeuralNetwork(String filename) {
		readData(filename);
		this.train();
		this.test();
	}
	
	private void train() {
		try {
			int lastIndex = data.numAttributes() - 1;
			data.setClassIndex(lastIndex);
			mlP = new MultilayerPerceptron();
			mlP.setLearningRate(0.1);
			mlP.setMomentum(0.2);
			mlP.setTrainingTime(2000);
			mlP.buildClassifier(data);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	private void test() {
		try {
			System.out.println("===========================");
			System.out.println("=== Weka Neural Network ===");
			Evaluation eval = new Evaluation(data);
			eval.crossValidateModel(mlP, data, 10, new java.util.Random(1));
			System.out.println(eval.errorRate());
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

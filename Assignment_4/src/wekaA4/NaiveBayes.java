package wekaA4;

import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.bayes.NaiveBayesMultinomial;
import weka.core.Instances;
import weka.core.converters.ConverterUtils;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.StringToWordVector;

public class NaiveBayes {
	private Instances data;
	private Classifier cl;
	
	public NaiveBayes(String filename) {
		readData(filename);
		train();
		test();
	}
	
	public void train() {
		try {
			cl = new NaiveBayesMultinomial();
			cl.buildClassifier(data);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	public void test() {
		try {
			System.out.println("==========================");
			System.out.println("==== Weka Naive Bayes ====");
			Evaluation eval = new Evaluation(data);
			eval.crossValidateModel(cl, data, 10, new java.util.Random(1));
			System.out.println(eval.toSummaryString());
			System.out.println(eval.toMatrixString());
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	private void readData(String filename) {
		try {
			ConverterUtils.DataSource source = new ConverterUtils.DataSource(filename);
			Instances raw = source.getDataSet();
			
			StringToWordVector stw = new StringToWordVector(10000);
			stw.setLowerCaseTokens(true);
			stw.setInputFormat(raw);
			
			data = Filter.useFilter(raw, stw);
			data.setClassIndex(0);
		}
		catch(Exception e) {
			e.printStackTrace();
			System.exit(0);
		}
	}
	
}

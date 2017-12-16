package a4;

import wekaA4.Weka;
import a4.NaiveBayes;
import core.Evaluator;

public class Application {
	public static void main(String[] args) {
		new Weka();
		
		Evaluator eval = new Evaluator(new NaiveBayes(), "data/FIFA_skill_nominal.arff");
		eval.evaluateWholeSet();
		eval.evaluateCV();
	}
}

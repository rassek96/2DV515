package a4;

import wekaA4.Weka;
import a4.NaiveBayes;
import core.Evaluator;

public class Application {
	public static void main(String[] args) {
		new Weka();
		System.out.println("\n-------------------------------------------\n");
		Evaluator evalBayes = new Evaluator(new NaiveBayes(), "data/FIFA_skill_nominal.arff");
		evalBayes.evaluateWholeSet();
		evalBayes.evaluateCV();
		System.out.println("\n-------------------------------------------\n");
		Evaluator evalKernel = new Evaluator(new RBFKernel(), "data/matchmaker_fixed.arff");
		evalKernel.evaluateWholeSet();
	}
}

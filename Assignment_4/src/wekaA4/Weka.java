package wekaA4;

public class Weka {
	
	public Weka() {
		new NaiveBayes("data/wikipedia_70.arff");
		System.out.println("\n-------------------------------------------\n");
		new DecisionTree("data/FIFA_skill.arff");
		System.out.println("\n-------------------------------------------\n");
		new SVM("data/matchmaker_fixed.arff");
		System.out.println("\n-------------------------------------------\n");
		new NeuralNetwork("data/matchmaker_fixed.arff");
	}
}

package wekaA4;

public class Weka {
	
	public Weka() {
		new NaiveBayes("data/wikipedia_70.arff");
		new DecisionTree("data/FIFA_skill.arff");
		new SVM("data/matchmaker_fixed.arff");
		new NeuralNetwork("data/matchmaker_fixed.arff");
	}
}

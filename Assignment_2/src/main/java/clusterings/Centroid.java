package clusterings;

import java.util.ArrayList;

public class Centroid {
	
	private ArrayList<Double> words;
	private ArrayList<Article> cluster = new ArrayList<Article>();
	private ArrayList<Article> lastCluster = new ArrayList<>();
	private ArrayList<Article> lastCluster2 = new ArrayList<>();
	
	public Centroid(ArrayList<Double> aWords) {
		words = aWords;
	}
	
	public ArrayList<Double> getCenter() {
		return words;
	}
	public ArrayList<Article> getCluster() {
		return cluster;
	}
	
	public void clearAssignments() {
		lastCluster2 = lastCluster;
		lastCluster = new ArrayList<Article>(cluster);
		cluster.clear();
	}
	public void addToCentroid(Article article) {
		cluster.add(article);
	}
	public void recalcCenter() {
		//Average word count for every article in cluster
		for(int i = 0; i < words.size(); i++) {
			double avg = 0.0;
			for(Article a : cluster) {
				if(i < a.getWords().size()) {
					avg += a.getWords().get(i).count;
				}		
			}
			avg = avg / (double)cluster.size();
			words.set(i, avg);
		}
	}
	public boolean matchesPreviousAssignment() {
		return lastCluster.containsAll(cluster) || lastCluster2.containsAll(cluster);
	}
	public String toString() {
		String output = "";
		for(Article a : cluster) {
    		output += a.getName() + "\n";
    	}
		return output;
	}
}
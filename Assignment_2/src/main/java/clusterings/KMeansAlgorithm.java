package clusterings;


import java.util.ArrayList;
import java.util.Random;

import model.BlogData;

public class KMeansAlgorithm {
	public KMeansAlgorithm() {
		
	}
	
	public ArrayList<Centroid> kmeans(int k, BlogData blogData) {
		ArrayList<Article> articles = blogData.getArticles();
		ArrayList<Centroid> centroids = new ArrayList<>();
		Random rnd = new Random();
		//Get words highest count
		ArrayList<Integer> highestWordCount = new ArrayList<>();
		for(int i = 0; i < articles.get(0).getWords().size(); i++) {
			int highest = 0;
			for(Article a : articles) {
				if(highest < (int)a.getWords().get(i).count) {
					highest = (int)a.getWords().get(i).count;
				}
			}
			highestWordCount.add(highest);
		}
		//Add k number of centroids at random places
		for(int i = 0; i < k; i++) {
			ArrayList<Double> centroidWords = new ArrayList<Double>();
			for(int i2 = 0; i2 < articles.get(0).getWords().size(); i2++) {
				if(i < highestWordCount.size()) {
					double rndCentr;
					rndCentr = rnd.nextInt(highestWordCount.get(i));
					centroidWords.add(rndCentr);
				}
			}
			centroids.add(new Centroid(centroidWords));
		}
		
		boolean done = false;
		int cnt = 0;
		while(!done) {
			for(Centroid centroid : centroids) {
				centroid.clearAssignments();
			}
			//For every article check which centroid is closest and assign it 
			for(Article a : articles) {
				Centroid closest = null;
				double score = 0.0;
				for(Centroid c: centroids) {
					if(closest == null) {
						closest = c;
						score = a.calc_Pearson(c.getCenter());
					}
					else if(a.calc_Pearson(c.getCenter()) > score) {
						closest = c;
						score = a.calc_Pearson(c.getCenter());
					}
				}
				closest.addToCentroid(a);
			}
			
			for(Centroid c : centroids) {
				c.recalcCenter();
			}
			//Reiterate unless all centroids are the same as previously or we've reached a hundred iterations
			done = true;
			for(Centroid c : centroids) {
				if(!c.matchesPreviousAssignment()) {
					done = false;
				}
			}
			cnt++;
			if(cnt > 100) {
				done = true;
			}
		}
		return centroids;
	}
}
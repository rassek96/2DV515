package clusterings;

import java.util.ArrayList;

import model.BlogData;

public class HierarchicalAlgorithm {
	private BlogData blogData;
	private ArrayList<Article> articles;
	private ArrayList<Cluster> clusters;
	
	public HierarchicalAlgorithm(BlogData blogData) {
		this.blogData = blogData;
		articles = this.blogData.getArticles();
		init();
	}
	
	public ArrayList<Cluster> getClusters() {
		return clusters;
	}
	
	private void init() {
		clusters = new ArrayList<>();
		for(Article article : articles) {
			clusters.add(new Cluster(article));
		}
		
		while(clusters.size() > 1) {
			iterate();
		}
	}
	
	private void iterate() {
		double closest = Double.MAX_VALUE;
		Cluster bestA = null;
		Cluster bestB = null;
		for(int i = 0; i < clusters.size(); i++) {
			for(int i2 = i + 1; i2 < clusters.size(); i2++) {
				Cluster cA = clusters.get(i);
				Cluster cB = clusters.get(i2);
				double distance = cA.article.calc_Pearson(cB.article);
				if(distance < closest) {
					closest = distance;
					bestA = cA;
					bestB = cB;
				}
			}
		}
		Cluster m = bestA.merge(bestB, closest);
		clusters.add(m);
		clusters.remove(bestA);
		clusters.remove(bestB);
	}
	
}
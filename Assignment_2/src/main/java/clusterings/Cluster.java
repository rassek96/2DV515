package clusterings;

import java.util.ArrayList;
import java.util.HashMap;

public class Cluster {
	private Cluster left;
	private Cluster right;
	protected Article article;
	double distance;
	private Cluster parent;
	
	public Cluster(Article article) {
		this.article = article;
	}
	public Cluster() {
		
	}
	public boolean isRoot() {
		return parent == null;
	}
	public Article getArticle() {
		return article;
	}
	public Cluster getLeft() {
		return this.left;
	}
	public Cluster getRight() {
		return this.right;
	}
	public Cluster merge(Cluster oc, double distance) {
		Cluster p = new Cluster();
		p.left = this;
		this.parent = p;
		p.right = oc;
		oc.parent = p;
		
		Article nA = new Article("", new HashMap<>(), new ArrayList<>());
		for(int i = 0; i < article.getWords().size(); i++) {
			Word wA = article.getWords().get(i);
			Word wB = oc.article.getWords().get(i);
			double nCnt = (wA.count + wB.count) / 2.0;
			nA.addWord(new Word(wA.getWord(), nCnt, ""));
		}
		p.article = nA;
		p.distance = distance;
		
		return p;
	}
	public String toString() {
		return article.getName();
	}
}

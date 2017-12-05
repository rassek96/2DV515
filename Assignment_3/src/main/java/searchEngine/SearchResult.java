package searchEngine;

public class SearchResult implements Comparable<SearchResult> {
	private Page page;
	private double score;
	public String url; 
	public double getScore;

	public SearchResult(Page p, double score) {
		this.page = p;
		this.score = score;
		this.getScore = (double) Math.round(score * 100) / 100;
		this.url = this.page.getURL();
	}
	
	public double getScore() {
		return score;
	}

	@Override
	public int compareTo(SearchResult o) {
		if(this.score > o.getScore()) {
			return 1;
		}
		else if(this.score < o.getScore()) {
			return -1;
		}
		else {
			return 0;
		}
	}
	
	@Override
	public String toString() {
		return "Score: " + score + " ---- Page: " + page.getURL();
	}
}

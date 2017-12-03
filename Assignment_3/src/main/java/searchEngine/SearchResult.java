package searchEngine;

public class SearchResult implements Comparable<SearchResult> {
	private Page page;
	private double score;

	public SearchResult(Page p, double score) {
		this.page = p;
		this.score = score;
	}
	
	public double getScore() {
		return score;
	}

	@Override
	public int compareTo(SearchResult o) {
		//return (int) Math.ceil(this.getScore() - o.getScore());
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

package searchEngine;

import java.util.ArrayList;

public class Page {
	private String url;
	public double pageRank = 1.0;
	private ArrayList<Integer> words;
	private ArrayList<String> links;
	
	public Page(String url, ArrayList<Integer> words, ArrayList<String> links) {
		this.url = url;
		this.words = words;
		this.links = links;
	}
	
	public ArrayList<Integer> getWords() {
		return words;
	}
	public String getURL() {
		return url;
	}
	public int linksCount() {
		return links.size();
	}
	public boolean hasLinkTo(String url) {
		return links.contains("/wiki/" + url);
	}
}

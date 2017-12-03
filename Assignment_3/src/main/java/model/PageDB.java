package model;

import java.util.ArrayList;
import java.util.HashMap;

import searchEngine.Page;

public class PageDB {
	private HashMap<String, Integer> wordToId;
	private ArrayList<Page> pages;
	
	public PageDB() {
		wordToId = new HashMap<String, Integer>();
		pages = new ArrayList<Page>();
	}
	
	public ArrayList<Page> getPages() {
		return pages;
	}
	
	public void addPage(Page page) {
		pages.add(page);
	}
	public int getIdForWord(String word) {
		String lword = word.toLowerCase();
		if(wordToId.containsKey(lword)) {
			return wordToId.get(lword);
		}
		else {
			int id = wordToId.size();
			wordToId.put(lword, id);
			return id;
		}
	}
}

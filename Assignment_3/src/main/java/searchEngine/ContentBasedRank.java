package searchEngine;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import model.PageDB;

public class ContentBasedRank {
	
	private PageDB db;
	
	public ContentBasedRank(PageDB db) {
		this.db = db;
	}
	
	public double getCountFrequencyScore(Page page, String query) {
		int score = 0;
		ArrayList<String> queryWords = new ArrayList<>();
		String[] words = query.split(" ");
		queryWords.addAll(Arrays.asList(words));
		for(String word : queryWords) {
			int idForWord = db.getIdForWord(word);
			for(int id : page.getWords()) {
				if(idForWord == id) {
					score++;
				}
			}
		}
		return score;
	}
	public double getCountLocationScore(Page page, String query) {
		int score = 0;
		ArrayList<String> queryWords = new ArrayList<>();
		String[] words = query.split(" ");
		queryWords.addAll(Arrays.asList(words));
		for(String word : queryWords) {
			int idForWord = db.getIdForWord(word);
			for(int id : page.getWords()) {
				if(idForWord == id) {
					score += page.getWords().indexOf(id);
				}
			}
			score += 100000;
		}
		
		return score;
	}
	public double getWordDistanceScore(Page page, String query) {
		int score = 0;
		ArrayList<String> queryWords = new ArrayList<>();
		String[] words = query.split(" ");
		queryWords.addAll(Arrays.asList(words));
		//TODO Implement this
		return score;
	}
}

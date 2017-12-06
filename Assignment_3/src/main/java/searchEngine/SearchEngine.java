package searchEngine;

import java.util.ArrayList;
import java.util.Collections;

import model.PageDB;

public class SearchEngine {
	
	private PageDB db;
	ContentBasedRank cbs;
	
	public SearchEngine(PageDB db, ContentBasedRank cbs) {
		this.db = db;
		this.cbs = cbs;
	}
	
	public ArrayList<SearchResult> query(String query) {
		ArrayList<SearchResult> result = new ArrayList<>();
		
		double[] content = new double[db.getPages().size()];
		double[] location = new double[db.getPages().size()];
		double[] pageRank = new double[db.getPages().size()];
		for(int i = 0; i < db.getPages().size(); i++) {
			Page page = db.getPages().get(i);
			content[i] = cbs.getCountFrequencyScore(page, query);
			location[i] = cbs.getCountLocationScore(page, query);
			pageRank[i] = page.pageRank;
		}
		content = normalizeScores(content, false);
		location = normalizeScores(location, true);
		pageRank = normalizeScores(pageRank, false);
		
		for(int i = 0; i < db.getPages().size(); i++) {
			Page p = db.getPages().get(i);
			double score = (1.0 * content[i]) + (1.0 * pageRank[i]) + (0.5 * location[i]);
			result.add(new SearchResult(p, score, content[i], pageRank[i], location[i]));
		}
		
		Collections.sort(result);
		Collections.reverse(result);
		
		return result;
	}
	private double[] normalizeScores(double[] scores, boolean smallIsBetter) {
		if(smallIsBetter) {
			double min = Double.MAX_VALUE;
			for(double s : scores) {
				if(s < min) {
					min = s;
				}
			}
			for(int i = 0; i < scores.length; i++) {
				scores[i] = min / Math.max(scores[i], 0.00001);
			}
			return scores;
		}
		else {
			double max = Double.MIN_VALUE;
			for(double s : scores) {
				if(s > max) {
					max = s;
				}
			}
			if(max == 0.0) {
				max = 0.00001;
			}
			for(int i = 0; i < scores.length; i++) {
				scores[i] = scores[i] / max;
			}
			return scores;
		}
	}
}

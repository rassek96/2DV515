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
		for(int i = 0; i < db.getPages().size(); i++) {
			Page page = db.getPages().get(i);
			content[i] = cbs.getCountFrequencyScore(page, query);
			location[i] = cbs.getCountLocationScore(page, query);
		}
		
		normalizeScores(content, false);
		normalizeScores(location, true);
		
		for(int i = 0; i < db.getPages().size(); i++) {
			Page p = db.getPages().get(i);
			double score = 1.0 * content[i] + 0.5 * location[i];
			result.add(new SearchResult(p, score));
		}
		
		Collections.sort(result);
		Collections.reverse(result);
		
		return result;
	}
	private void normalizeScores(double[] scores, boolean smallIsBetter) {
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
		}
	}
	public void calculatePageRank(PageDB db) {
		for(int i = 0; i < 20; i++) {
			for(Page p : db.getPages()) {
				iteratePageRank(p, db);
			}
		}
	}
	private void iteratePageRank(Page p, PageDB db) {
		double pr = 0;
		for(Page po : db.getPages()) {
			if(po.hasLinkTo(p.getURL())) {
				pr += po.pageRank / (double)po.linksCount();
			}
		}
		pr = 0.85 * pr + 0.15;
		p.pageRank = pr;
	}
}

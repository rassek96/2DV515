package clusterings;

import java.util.ArrayList;
import java.util.HashMap;

public class Article {
	private String name;
	private HashMap<String, Integer> wordsCount = new HashMap<String, Integer>();
	private ArrayList<Word> words = new ArrayList<Word>();
	
	public Article(String aName, HashMap<String, Integer> aWordsCount, ArrayList<Word> aWords) {
		name = aName;
		wordsCount = aWordsCount;
		words = aWords;
	}
	
	public String getName() {
		return name;
	}
	public HashMap<String, Integer> getWordsCount() {
		return wordsCount;
	}
	public ArrayList<Word> getWords() {
		return words;
	}
	
	public double calc_Pearson(ArrayList<Double> aB) {
		double sumA = 0;
		double sumAsq = 0;
		for(Word w : getWords()) {
			sumA += w.count;
			sumAsq += Math.pow(w.count, 2);
		}
		double sumB = 0;
		double sumBsq = 0;
		for(Double w : aB) {
			sumB += w;
			sumBsq += Math.pow(w, 2);
		}
		double pSum = 0;
		int n = Math.min(getWords().size(), aB.size());
		for(int i = 0; i < n; i++) {
			pSum += getWords().get(i).count * aB.get(i);
		}
		//Algorithm for pearson
		double num = pSum - (sumA*sumB / n);
		double den = Math.sqrt((sumAsq - Math.pow(sumA, 2) / n) * (sumBsq - Math.pow(sumB, 2) / n));
		
		if(den == 0) {
			return 0;
		}
		return 1.0 - num / den;
	}
}
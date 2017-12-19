package recSystem;

import java.util.HashMap;
import java.util.Map;

public class User {
	private int id;
	private HashMap<Integer, Double> ratings; //<movieId, rating>
	
	public User(int id) {
		this.id = id;
		ratings = new HashMap<>();
	}
	
	public int getId() {
		return id;
	}
	public HashMap<Integer, Double> getRatings() {
		return ratings;
	}
	
	public void addRating(int movieId, double rating) {
		ratings.put(movieId, rating);
	}
	
	public double calcEuclidean(User b) {
		double sim = 0.0;
		int cntSim = 0;
		for(Map.Entry<Integer, Double> rA : ratings.entrySet()) {
			int movieId = rA.getKey();
			if(b.getRatings().containsKey(movieId)) {
				double ratingA = rA.getValue();
				double ratingB = b.getRatings().get(movieId);
				sim += Math.pow(ratingA - ratingB, 2);
				cntSim++;
			}
		}
		if(cntSim == 0) {
			return 0;
		}
		return 1.0 / (1.0 + sim);
	}
	
	public double calcPearson(User b) {
		double sum1 = 0.0;
		double sum2 = 0.0;
		double sum1sq = 0.0;
		double sum2sq = 0.0;
		double pSum = 0.0;
		int n = 0;
		
		for(Map.Entry<Integer, Double> rA : ratings.entrySet()) {
			int movieId = rA.getKey();
			if(b.getRatings().containsKey(movieId)) {
				sum1 += rA.getValue();
				sum2 += b.getRatings().get(movieId);
				sum1sq += Math.pow(rA.getValue(), 2);
				sum2sq += Math.pow(b.getRatings().get(movieId), 2);
				pSum += rA.getValue() * b.getRatings().get(movieId);
				n++;
			}
		}
		
		if(n == 0) {
			return 0;
		}
		double num = pSum - (sum1 * sum2 / n);
		double den = Math.sqrt((sum1sq - Math.pow(sum1, 2) / n) * (sum2sq - Math.pow(sum2, 2) / n));
		
		if(den == 0) {
			return 0;
		}
		
		double r = num / den;
		return r;
	}
}

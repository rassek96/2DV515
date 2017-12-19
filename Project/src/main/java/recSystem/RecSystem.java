package recSystem;

import java.util.HashMap;
import java.util.Map;

import model.MovieDB;

public class RecSystem {
	private MovieDB db;
	
	public RecSystem(MovieDB db) {
		this.db = db;
	}
	
	public HashMap<Integer, Double> findRecMoviesForUser(int userId, boolean pearsonSimilarity) {
		HashMap<Integer, Double> similarity = new HashMap<>(); //user id, sim score
		User userA = db.getUsers().get(userId);
		//Get similarity scores
		if(pearsonSimilarity) {
			for(User userB : db.getUsers().values()) {
				double sim = userA.calcPearson(userB);
				similarity.put(userB.getId(), sim);
			}
		}
		else {
			for(User userB : db.getUsers().values()) {
				double sim = userA.calcEuclidean(userB);
				similarity.put(userB.getId(), sim);
			}
		}
		
		
		//Calculate weighted scores
		HashMap<Integer, HashMap<Integer, Double>> weightedScore = new HashMap<>(); //movie id, hashmap<user id, weighted score>
		HashMap<Integer, Double> totalScore = new HashMap<>(); //movie id, total w scoreTotal weighted score
		for(Movie movie : db.getMovies().values()) {
			int movieId = movie.getId();
			HashMap<Integer, Double> wScores = new HashMap<>();
			
			for(User userB : db.getUsers().values()) {
				if(userB.getRatings().containsKey(movieId)) {
					double userRatingForMovie = userB.getRatings().get(movieId);
					double wScore = (double)userRatingForMovie * (double)similarity.get(userB.getId());
					//Add to total weighted score for movie
					if(totalScore.containsKey(movieId)) {
						totalScore.put(movieId, totalScore.get(movieId) + wScore);
					}
					else {
						totalScore.put(movieId, wScore);
					}
					
					wScores.put(userB.getId(), wScore);
				}
			}
			weightedScore.put(movieId, wScores);
		}
		
		//Calculate similarity scores
		HashMap<Integer, Double> simSum = new HashMap<>();
		for(Map.Entry<Integer, Double> ratings : userA.getRatings().entrySet()) {
			int movieId = ratings.getKey();
			
			for(User userB : db.getUsers().values()) {
				if(userB.getRatings().containsKey(movieId)) {
					if(simSum.containsKey(movieId)) {
						simSum.put(movieId, simSum.get(movieId) + similarity.get(userB.getId()));
					}
					else {
						simSum.put(movieId, similarity.get(userB.getId()));
					}
				}
			}
		}
		
		//Update weighted score
		HashMap<Integer, Double> calcedWeightScore = new HashMap<>(); //movie id, score
		for(Map.Entry<Integer, Double> score : totalScore.entrySet()) {
			if(simSum.containsKey(score.getKey())) {
				calcedWeightScore.put(score.getKey(), (score.getValue() / simSum.get(score.getKey())));
			}
		}
		return calcedWeightScore;
	}
}

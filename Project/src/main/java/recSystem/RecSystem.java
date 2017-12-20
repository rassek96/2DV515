package recSystem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import model.MovieDB;

public class RecSystem {
	private MovieDB db;
	
	public RecSystem(MovieDB db) {
		this.db = db;
	}
	
	public ArrayList<Result> findRecMoviesForUser(int userId, boolean pearsonSimilarity) {
		HashMap<Integer, Double> similarity = new HashMap<>(); //user id, sim score
		User userA = db.getUsers().get(userId);
		//Get similarity scores
		for(User userB : db.getUsers().values()) {
			double sim;
			if(pearsonSimilarity) {
				sim = userA.calcPearson(userB);
			}
			else {
				sim = userA.calcEuclidean(userB);
			}
			similarity.put(userB.getId(), sim);
		}
		
		//Calculate weighted scores and totals
		HashMap<Integer, HashMap<Integer, Double>> weightedScore = new HashMap<>(); //movie id, hashmap<user id, weighted score>
		HashMap<Integer, Double> totalScore = new HashMap<>(); //movie id, total w scoreTotal weighted score
		HashMap<Integer, Double> totalSim = new HashMap<>(); //MovieID, simsum
		for(Movie movie : db.getMovies().values()) {
			int movieId = movie.getId();
			HashMap<Integer, Double> wScores = new HashMap<>();
			
			for(User userB : db.getUsers().values()) {
				if(userB.getRatings().containsKey(movieId)) {
					double userRatingForMovie = userB.getRatings().get(movieId);
					double wScore = (double)userRatingForMovie * (double)similarity.get(userB.getId());
					//Add to total weighted score for movie
					if(wScore > 0) {
						if(totalScore.containsKey(movieId)) {
							totalScore.put(movieId, totalScore.get(movieId) + wScore);
						}
						else {
							totalScore.put(movieId, wScore);
						}
					}
					//Add to total similarity for movie
					if(similarity.get(userB.getId()) > 0) {
						if(totalSim.containsKey(movieId)) {
							totalSim.put(movieId, totalSim.get(movieId) + similarity.get(userB.getId()));
						}
						else {
							totalSim.put(movieId, similarity.get(userB.getId()));
						}
					}
					wScores.put(userB.getId(), wScore);
				}
			}
			weightedScore.put(movieId, wScores);
		}
		
		//Update weighted score
		HashMap<Integer, Double> calcedWeightScore = new HashMap<>(); //movie id, score
		for(Map.Entry<Integer, Double> score : totalScore.entrySet()) {
			if(totalSim.containsKey(score.getKey())) {
				calcedWeightScore.put(score.getKey(), (score.getValue() / totalSim.get(score.getKey())));
			}
		}
		
		//Convert to results
		ArrayList<Result> results = new ArrayList<Result>();
		for(Map.Entry<Integer, Double> score : calcedWeightScore.entrySet()) {
			if(!userA.getRatings().containsKey(score.getKey())) {
				String movieTitle = db.getMovies().get(score.getKey()).getTitle();
				results.add(new Result(score.getKey(), movieTitle, score.getValue()));
			}
		}
		return results;
	}
	
	public ArrayList<Result> findRecMoviesForMovie(int userId, boolean pearsonSimilarity) {
		HashMap<Integer, HashMap<Integer, Double>> similarity = new HashMap<>(); //movie id, HashMap<movieId,sim score>
		HashMap<Integer, HashMap<Integer, Double>> weightedScore = new HashMap<>();
		HashMap<Integer, Double> totalScore = new HashMap<>();
		HashMap<Integer, Double> totalSim = new HashMap<>();
		for(Movie movie : db.getMoviesItemBased().values()) {
			//Get similarity scores and weighted scores
			if(movie.getRatings().containsKey(userId)) {
				HashMap<Integer, Double> mSim = new HashMap<>();
				HashMap<Integer, Double> mWeight = new HashMap<>();
				for(Movie movieB : db.getMoviesItemBased().values()) {
					double sim;
					if(pearsonSimilarity) {
						sim = movie.calcPearson(movieB);
					}
					else {
						sim = movie.calcEuclidean(movieB);
					}
					mSim.put(movieB.getId(), sim);
					double wScore = (double)movie.getRatings().get(userId) * sim;
					mWeight.put(movieB.getId(), wScore);
					//Add to totals
					if(totalSim.containsKey(movieB.getId())) {
						totalSim.put(movieB.getId(), totalSim.get(movieB.getId()) + sim);
					}
					else {
						totalSim.put(movieB.getId(), sim);
					}
					if(totalScore.containsKey(movieB.getId())) {
						totalScore.put(movieB.getId(), totalScore.get(movieB.getId()) + wScore);
					}
					else {
						totalScore.put(movieB.getId(), wScore);
					}
				}
				similarity.put(movie.getId(), mSim);
				weightedScore.put(movie.getId(), mWeight);
			}
		}
		
		//Update weighted score
		HashMap<Integer, Double> calcedWeightScore = new HashMap<>(); //movie id, score
		for(Map.Entry<Integer, Double> score : totalScore.entrySet()) {
			if(totalSim.containsKey(score.getKey())) {
				calcedWeightScore.put(score.getKey(), (score.getValue() / totalSim.get(score.getKey())));
			}
		}
		
		//Convert to results
		ArrayList<Result> results = new ArrayList<Result>();
		for(Map.Entry<Integer, Double> score : calcedWeightScore.entrySet()) {
			if(!db.getMoviesItemBased().get(score.getKey()).getRatings().containsKey(userId)) {
				String movieTitle = db.getMoviesItemBased().get(score.getKey()).getTitle();
				results.add(new Result(score.getKey(), movieTitle, score.getValue()));
			}
		}
		return results;
	}
}

package recSystem;


import java.util.HashMap;

import model.MovieDB;

public class RecSystemItemBased {
	private MovieDB db;
	
	public RecSystemItemBased(MovieDB db) {
		this.db = db;
	}
	
	public void findRecMoviesForMovie(int movieId, boolean pearsonSimilarity) {
		HashMap<Integer, Double> similarity = new HashMap<>(); //movie id, sim score
		Movie movieA = db.getMovies().get(movieId);
		//Get similarity scores
		if(pearsonSimilarity) {
			for(Movie movieB : db.getMovies().values()) {
				double sim = movieA.calcPearson(movieB);
				similarity.put(movieB.getId(), sim);
				System.out.println(movieB.getId() + " - " + sim);
			}
		}
		else {
			for(Movie movieB : db.getMovies().values()) {
				double sim = movieA.calcEuclidean(movieB);
				similarity.put(movieB.getId(), sim);
				System.out.println(movieB.getId() + " - " + sim);
			}
		}
	}
}

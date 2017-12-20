package model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;

import recSystem.Movie;
import recSystem.User;

public class Data {
	
	private MovieDB db;
	private String filePath = "src/main/resources/data/";
	
	public Data(MovieDB db) {
		this.db = db;
		readMovies();
		readUsers();
		
		generateItemBased();
	}
	
	private void readMovies() {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(filePath+"movies.csv"));
			String line = reader.readLine();
			while((line = reader.readLine()) != null) {
				//Split line to attributes
				String[] attributes = line.split(",");
				
				int id = Integer.parseInt(attributes[0]);
				String title = attributes[1];
				//Split genres attribute
				String[] aGenres = attributes[2].split("|");
				ArrayList<String> genres = new ArrayList<>(Arrays.asList(aGenres));
				Movie movie = new Movie(id, title, genres);
				db.movies.put(id, movie);
			}
			
			reader.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	private void readUsers() {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(filePath+"ratings.csv"));
			String line = reader.readLine();
			while((line = reader.readLine()) != null) {
				//Split line to attributes
				String[] attributes = line.split(",");
				
				int userId = Integer.parseInt(attributes[0]);
				int movieId = Integer.parseInt(attributes[1]);
				double rating = Double.parseDouble(attributes[2]);
				User user;
				//Add user if it doesn't already exist
				if(!db.users.containsKey(userId)) {
					user = new User(userId);
					db.users.put(userId, user);
				}
				else {
					user = db.users.get(userId);
				}
				user.addRating(movieId, rating);
			}
			reader.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private void generateItemBased() {
		Iterator<User> it = db.users.values().iterator();
		while(it.hasNext()) {
			User user = it.next();
			for(Map.Entry<Integer, Double> rating : user.getRatings().entrySet()) {
				Integer movieId = rating.getKey();
				Movie movie = db.movies.get(movieId);
				double score = rating.getValue();
				if(!db.moviesItemBased.containsKey(movieId)) {
					db.moviesItemBased.put(movieId, movie);
				}
				Movie nMovie = db.moviesItemBased.get(movieId);
				nMovie.addRating(user.getId(), score);
			}
		}
	}
}

package model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;

import recSystem.Movie;
import recSystem.User;

public class Data {
	
	private MovieDB db;
	private String filePath = "src/main/resources/data/";
	
	public Data(MovieDB db) {
		this.db = db;
		readMovies();
		readUsers();
	}
	
	private void readMovies() {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(filePath+"movies.csv"));
			String line = reader.readLine();
			System.out.println(line);
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
			System.out.println(db.movies.size());
			
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
			System.out.println(line);
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
			System.out.println(db.users.size());
			reader.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
}

package model;

import java.util.HashMap;

import recSystem.Movie;
import recSystem.User;

public class MovieDB {
	//User based
	protected HashMap<Integer, User> users;
	protected HashMap<Integer, Movie> movies;
	
	//Item based
	protected HashMap<Integer, Movie> moviesItemBased;
	
	public MovieDB() {
		users = new HashMap<>();
		movies = new HashMap<>();
		
		moviesItemBased = new HashMap<>();
		
		//Send database to data class to populate it with csv files
		new Data(this);
	}
	
	public HashMap<Integer, User> getUsers() {
		return users;
	}
	public HashMap<Integer, Movie> getMovies() {
		return movies;
	}
	public HashMap<Integer, Movie> getMoviesItemBased() {
		return moviesItemBased;
	}
}

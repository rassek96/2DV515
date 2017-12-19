package model;

import java.util.HashMap;

import recSystem.Movie;
import recSystem.User;

public class MovieDB {
	protected HashMap<Integer, User> users;
	protected HashMap<Integer, Movie> movies;
	
	public MovieDB() {
		users = new HashMap<>();
		movies = new HashMap<>();
		//Send database to data class to populate it with csv files
		new Data(this);
	}
	
	public HashMap<Integer, User> getUsers() {
		return users;
	}
	public HashMap<Integer, Movie> getMovies() {
		return movies;
	}
}

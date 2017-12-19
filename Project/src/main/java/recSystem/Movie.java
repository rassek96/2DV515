package recSystem;

import java.util.ArrayList;

public class Movie {
	private int id;
	private String title;
	private ArrayList<String> genres;
	
	public Movie(int id, String title, ArrayList<String> genres) {
		this.id = id;
		this.title = title;
		this.genres = genres;
	}
	
	public int getId() {
		return id;
	}
	public String getTitle() {
		return title;
	}
	public ArrayList<String> getGenres() {
		return genres;
	}
}

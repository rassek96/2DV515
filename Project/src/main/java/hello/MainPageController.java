package hello;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import model.MovieDB;
import recSystem.RecSystem;
import recSystem.Result;

@Controller
public class MainPageController {
	MovieDB db;
	RecSystem recSystem;
	
	public MainPageController() {
		this.db = new MovieDB();
		this.recSystem = new RecSystem(db);
	}
    
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String index(Model model) {
	    return "index";
	}
	
	@RequestMapping(value = "/user", params={"userId"}, method = RequestMethod.GET)
    public String user(@RequestParam Map<String, String> params, Model model) {
		
		try {
			ArrayList<Result> recMovies;
			if(params.get("based").equals("itemBased")) {
				if(params.get("similarity").equals("pearson")) {
					recMovies = recSystem.findRecMoviesForMovie(Integer.parseInt(params.get("userId")), true);
				}
				else {
					recMovies = recSystem.findRecMoviesForMovie(Integer.parseInt(params.get("userId")), false);
				}
			}
			else {
				if(params.get("similarity").equals("pearson")) {
					recMovies = recSystem.findRecMoviesForUser(Integer.parseInt(params.get("userId")), true);
				}
				else {
					recMovies = recSystem.findRecMoviesForUser(Integer.parseInt(params.get("userId")), false);
				}
			}
			Collections.sort(recMovies);
			Collections.reverse(recMovies);
			ArrayList<Result> results = new ArrayList<Result>();
			for(int i = 0; i < recMovies.size(); i++) {
				//if(recMovies.get(i).score > 4) {
				results.add(recMovies.get(i));
				//}
			}
			model.addAttribute("userId", "Recommendations for user " + params.get("userId"));
			model.addAttribute("results", results);
	    	return "user";
		}
		catch(NumberFormatException e) {
			model.addAttribute("userId", "User ID must be a number");
	    	return "user";
		}
    }
}
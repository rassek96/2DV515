package hello;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import model.MovieDB;
import recSystem.RecSystem;

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
			HashMap<Integer, Double> recMovies;
			if(params.get("similarity").equals("pearson")) {
				recMovies = recSystem.findRecMoviesForUser(Integer.parseInt(params.get("userId")), true);
			}
			else {
				recMovies = recSystem.findRecMoviesForUser(Integer.parseInt(params.get("userId")), false);
			}
			ArrayList<Integer> movieIds = new ArrayList<>(recMovies.keySet());
			ArrayList<Double> score = new ArrayList<>(recMovies.values());
			for(int i = 0; i < movieIds.size(); i++) {
				System.out.println(movieIds.get(i) + ": " + score.get(i));
			}
			model.addAttribute("userId", "Recommendations for user " + params.get("userId"));
	    	return "user";
		}
		catch(NumberFormatException e) {
			model.addAttribute("userId", "User ID must be a number");
	    	return "user";
		}
    }
}
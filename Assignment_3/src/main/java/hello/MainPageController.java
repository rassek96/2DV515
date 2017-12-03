package hello;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import model.PageDB;
import model.WikiData;
import searchEngine.ContentBasedRank;
import searchEngine.SearchEngine;

@Controller
public class MainPageController {
	private WikiData wikiData;
	private PageDB db;
	private SearchEngine searchEngine;
	
	public MainPageController() {
		wikiData = new WikiData(new PageDB());
		db = wikiData.populateDB("src/main/resources/data/Words");
		ContentBasedRank cbs = new ContentBasedRank(db);
		System.out.println(db.getPages().size());
		searchEngine = new SearchEngine(db, cbs);
	}
    
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String index(Model model) {
	    return "index";
	}
	
    @RequestMapping(value = "/", params={"query"}, method = RequestMethod.GET)
    public String search(@RequestParam(value = "query", required = true) String query, Model model) {
    	System.out.println(query);
    	model.addAttribute("query", query);
    	System.out.println("----------------------------------");
    	searchEngine.query(query);
    	System.out.println("----------------------------------");
    	return "index";
    }
}
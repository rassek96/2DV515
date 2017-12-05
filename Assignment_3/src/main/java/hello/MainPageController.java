package hello;

import java.util.ArrayList;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import model.PageDB;
import model.WikiData;
import searchEngine.ContentBasedRank;
import searchEngine.SearchEngine;
import searchEngine.SearchResult;

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
    	model.addAttribute("query", query);
    	ArrayList<SearchResult> result = searchEngine.query(query);
		ArrayList<SearchResult> resultLimit = new ArrayList<>();
		for(int i = 0; i < 10; i++) {
			resultLimit.add(result.get(i));
		}
    	model.addAttribute("results", resultLimit);
    	return "index";
    }
}
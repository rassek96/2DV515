package hello;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicLong;

import clusterings.Article;
import clusterings.Centroid;
import clusterings.KMeansAlgorithm;
import model.BlogData;

@RestController
public class KMeansAPIController {
    private final AtomicLong counter = new AtomicLong();
    
    @RequestMapping("/api/k-means/centroids")
    public Greeting<ArrayList<String>> greetingAPI() {
    	BlogData blogData = new BlogData("src/main/resources/blogdata.txt");
    	KMeansAlgorithm kmeans = new KMeansAlgorithm();
    	
    	ArrayList<ArrayList<String>> content = new ArrayList<>();
    	for(Centroid c : kmeans.kmeans(5, blogData)) {
    		ArrayList<String> cArticles = new ArrayList<>();
    		for(Article a : c.getCluster()) {
    			cArticles.add(a.getName());
    		}
    		content.add(cArticles);
    		
    	}
    	ArrayList<String> links = new ArrayList<>();
    	links.add("http://localhost:8080/api");
        return new Greeting(counter.incrementAndGet(), content, links);
    }
    
}
package hello;

import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import clusterings.Centroid;
import clusterings.KMeansAlgorithm;
import model.BlogData;

@Controller
public class KMeansController {
    
    @RequestMapping(value = "/k-means/centroids")
    public String greeting(Model model) {
    	BlogData blogData = new BlogData("src/main/resources/blogdata.txt");
    	KMeansAlgorithm kmeans = new KMeansAlgorithm();
    	ArrayList<Centroid> centroids = kmeans.kmeans(5, blogData);
    	model.addAttribute("centroids", centroids);
    	return "centroids";
    }
    
}
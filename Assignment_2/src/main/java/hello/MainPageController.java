package hello;

import java.util.ArrayList;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import clusterings.Centroid;
import clusterings.Cluster;
import clusterings.HierarchicalAlgorithm;
import clusterings.KMeansAlgorithm;
import model.BlogData;

@Controller
public class MainPageController {
	private BlogData blogData;
	private BlogData wikiData;
	
	public MainPageController() {
		this.blogData = new BlogData("src/main/resources/blogdata.txt");
		this.wikiData = new BlogData("src/main/resources/wikidata.txt");
	}
    
    @RequestMapping(value = "/")
    public String index(Model model) {
    	return "index";
    }
    
    @RequestMapping(value = "/k-means/centroids")
    public String centroids(Model model) {
    	KMeansAlgorithm kmeans = new KMeansAlgorithm();
    	ArrayList<Centroid> centroids = kmeans.kmeans(3, blogData);
    	model.addAttribute("centroids", centroids);
    	return "centroids";
    }
    
    @RequestMapping(value = "/hierarchical")
    public String hierarchical(Model model) {
    	HierarchicalAlgorithm hierarchical = new HierarchicalAlgorithm(blogData);
    	ArrayList<Cluster> clusters = hierarchical.getClusters();
    	Cluster root = clusters.get(0);
    	String tree = buildTree(root);
    	model.addAttribute("htmlBody", tree);
    	return "hierarchical";
    }
    
    @RequestMapping(value = "wiki/k-means/centroids")
    public String wikiCentroids(Model model) {
    	KMeansAlgorithm kmeans = new KMeansAlgorithm();
    	ArrayList<Centroid> centroids = kmeans.kmeans(3, wikiData);
    	model.addAttribute("centroids", centroids);
    	return "centroids";
    }
    
    @RequestMapping(value = "wiki/hierarchical")
    public String wikiHierarchical(Model model) {
    	HierarchicalAlgorithm hierarchical = new HierarchicalAlgorithm(wikiData);
    	ArrayList<Cluster> clusters = hierarchical.getClusters();
    	Cluster root = clusters.get(0);
    	String tree = buildTree(root);
    	model.addAttribute("htmlBody", tree);
    	return "hierarchical";
    }

    private ArrayList<String> html;
    private String buildTree(Cluster root) {
    	html = new ArrayList<>();
    	html.add("<ul>");
    	html.add("</ul>");
    	addNodesHTML(1, root);
    	String str = "";
    	for(String s : html) {
    		str += s + "\n";
    	}
    	return str;
    }
    private void addNodesHTML(int i, Cluster c) {
    	if(c.getRight() != null) {
    		String art = c.getRight().toString();
    		if(art.equals("")) {
    			html.add(i, "<li><div class=\"toggle\" onclick=\"collapse(this);\">(-)</div><ul>");
    			html.add(i+1, "</ul></li>");
    		}
    		else {
    			art = art.replaceAll("\"", "'");
    			html.add(i, "<li data-jstree='{\"disabled\":true}'><b>" + art + "</b></li>");
    		}
    		addNodesHTML(i + 1, c.getRight());
    	}
    	if(c.getLeft() != null) {
    		String art = c.getLeft().toString();
    		if(art.equals("")) {
    			html.add(i, "<li><div class=\"toggle\" onclick=\"collapse(this);\">(-)</div><ul>");
    			html.add(i+1, "</ul></li>");
    		}
    		else {
    			art = art.replaceAll("\"", "'");
    			html.add(i, "<li data-jstree='{\"disabled\":true}'><b>" + art + "</b></li>");
    		}
    		addNodesHTML(i + 1, c.getLeft());
    	}
    }
}
package hello;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainPageAPIController {
    private final AtomicLong counter = new AtomicLong();
    @RequestMapping("/api")
    public Greeting<String> indexAPI(@RequestParam(value="name", defaultValue="World") String name) {
    	
    	ArrayList<String> links = new ArrayList<>();
    	links.add("http://localhost:8080/api/k-means/centroids");
    	
    	ArrayList<String> content = new ArrayList<>();
    	content.add("Assignment 2 main page");
    	
        return new Greeting<String>(counter.incrementAndGet(), content, links);
    }
}
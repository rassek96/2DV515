package hello;

import java.util.ArrayList;

public class Greeting<E> {

    private final long id;
    private final ArrayList<E> content;
    private final ArrayList<String> links;

    public Greeting(long aId, ArrayList<E> aContent, ArrayList<String> aLinks) {
        id = aId;
        content = aContent;
        links = aLinks;
    }

    public long getId() {
        return id;
    }
    public ArrayList<E> getContent() {
        return content;
    }
    public ArrayList<String> getLinks() {
    	return links;
    }
}
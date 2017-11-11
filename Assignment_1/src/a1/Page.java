package a1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;

public class Page {
	//Fields
	private String pageUrl;
	//Page name = title
	private String pageName;
	private URL url;
	private ArrayList<String> links = new ArrayList<String>();
	//Level from root, where root = 0
	private int pageLevel;
	private String trimmedHTML;
	
	//Constructor
	public Page(String page_url, int page_level) {
		pageUrl = page_url;
		pageName = pageUrl.replace("/wiki/", "").replace("/", "").replaceAll("\\W", "");
		try {
			url = new URL("https://en.wikipedia.org" + pageUrl);
		} catch (MalformedURLException e) {
			System.out.println("Incorrect URL. Please enter the url in the correct format and try again.");
			System.out.println("Current URL: https://en.wikipedia.org" + pageUrl);
			System.exit(0);
		}
		pageLevel = page_level;
	}
	
	//Getters
	public ArrayList<String> getLinks() { return links; }
	public int getLevel() { return pageLevel; }
	public String getPageUrl() { return pageUrl; }
	public String getPageName() { return pageName; }
	public String getTrimmedHTML() { return trimmedHTML; }

	//Methods
	public String scrapePage() {
		/*Scrape page*/
		String contents = "";
		try {
			BufferedReader reader;
			reader = new BufferedReader(new InputStreamReader(url.openStream()));
			String line;
			while((line = reader.readLine()) != null) {
				contents += line + System.lineSeparator();
			}
			reader.close();
		} catch (IOException e) {
			System.out.println("Something went wrong when reading the webpage.");
		}
		/*Scrape page end*/
		filterLinks(contents);
		trimHTML(contents);
		return contents;
	}
	
	private ArrayList<String> filterLinks(String contents) {
		/*Find links*/
		//Regex from lecture
		Pattern p = Pattern.compile("href=\"(.*?)\"");
		Matcher m = p.matcher(contents);
		while(!m.hitEnd()) {
			boolean a = m.find();
			if(a) {
				String match = contents.substring(m.start(), m.end());
				String link = match.substring(match.indexOf("\"") + 1, match.lastIndexOf("\""));
				//Filter away unwanted links and add to list of links
				if(link.startsWith("/wiki/") && !link.contains(":") && !link.equals(pageUrl) && !link.equals("/wiki/Main_Page")) {
					if(!links.contains(link)) {
						links.add(link);
					}
				}
			}
		}
		return links;
	}
	
	public String trimHTML(String contents) {
		trimmedHTML = Jsoup.parse(contents).text();
		trimmedHTML = trimmedHTML.toLowerCase();
		//Removes everything that is not alphanumeric character or space or dash
		trimmedHTML = trimmedHTML.replaceAll("[^a-zA-Z0-9\\s\\-]", "");
		//Removes double-spaces
		trimmedHTML = trimmedHTML.replaceAll("  ", " ");
		return trimmedHTML;
	}
	
}
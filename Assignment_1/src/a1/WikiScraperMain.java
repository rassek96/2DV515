package a1;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.LinkedHashSet;

public class WikiScraperMain {
	
	private static LinkedHashSet<Page> visited = new LinkedHashSet<Page>();
	private static int counter = 0;
	public static void main(String[] args) {
		Page wikiPage1 = new Page(args[0], 0);
		Page wikiPage2 = new Page(args[1], 0);
		
		//Create dirs
		new File("data/Links/" + wikiPage1.getPageName()).mkdirs();
		new File("data/Words/" + wikiPage1.getPageName()).mkdirs();
		new File("data/Raw_HTML/" + wikiPage1.getPageName()).mkdirs();
		new File("data/Links/" + wikiPage2.getPageName()).mkdirs();
		new File("data/Words/" + wikiPage2.getPageName()).mkdirs();
		new File("data/Raw_HTML/" + wikiPage2.getPageName()).mkdirs();
		
		counter = 0;
		LinkedHashSet<Page> s1 = new LinkedHashSet<Page>();
		s1.add(wikiPage1);
		bfs(s1, wikiPage1);
		counter = 0;
		LinkedHashSet<Page> s2 = new LinkedHashSet<Page>();
		s2.add(wikiPage2);
		bfs(s2, wikiPage2);
		System.out.println("Finished");
	}
	
	private static void bfs(LinkedHashSet<Page> s, Page rootPage) {
		LinkedHashSet<Page> s2 = new LinkedHashSet<Page>();
		for(Page page : s) {
			if(page.getLevel() >= 2 ) {
				return;
			}
			if(!visited.contains(page)) {
				visited.add(page);
				String content = page.scrapePage();
				if(counter < 50 || page.getLevel() < 2) {
					writeRawHTMLFile(rootPage, page, content);
				}
			}
			for(String link : page.getLinks()) {
				Page newPage = new Page(link, page.getLevel() + 1);
				if(!visited.contains(newPage)) {
					String content = newPage.scrapePage();
					if(counter < 50 || page.getLevel() < 2) {
						writeRawHTMLFile(rootPage, newPage, content);
					}
					visited.add(newPage);
					s2.add(newPage);
					//Stop after 1000
					if(counter > 500) {
						writeFiles(rootPage, page, s2);
						return;
					}
					System.out.println(counter++);
				}
			}
			writeFiles(rootPage, page, s2);
		}
		if(!s2.isEmpty()) {
			bfs(s2, rootPage);
		}
	}
	
	private static void writeRawHTMLFile(Page root, Page page, String content) {
		try {
			String pathLinks = "data/Raw_HTML/" + root.getPageName() + "/" + page.getPageName() + ".txt";
			PrintWriter writer = new PrintWriter(pathLinks, "UTF-8");
			writer.println(content);
			writer.close();
		} catch (FileNotFoundException e) {
			System.out.println("Something went wrong. File not found.");
		} catch (UnsupportedEncodingException e) {
			System.out.println("Something went wrong. Encoding not supported for file.");
		}
	}
	private static void writeFiles(Page root, Page page, LinkedHashSet<Page> pages) {
		try {
			//Write Links
			String pathLinks = "data/Links/" + root.getPageName() + "/" + page.getPageName() + ".txt";
			PrintWriter writerLinks = new PrintWriter(pathLinks, "UTF-8");
			//Print all urls for the page
			for(Page p : pages) {
				writerLinks.println(p.getPageUrl());
			}
			writerLinks.close();
			//Write Words
			String pathWords = "data/Words/" + root.getPageName() + "/" + page.getPageName() + ".txt";
			PrintWriter writerWords = new PrintWriter(pathWords, "UTF-8");
			writerWords.println(page.getTrimmedHTML());
			writerWords.close();
		} catch (FileNotFoundException e) {
			System.out.println("Something went wrong. File not found.");
		} catch (UnsupportedEncodingException e) {
			System.out.println("Something went wrong. Encoding not supported for file.");
		}
	}
}
package model;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import searchEngine.Page;

public class PageDB {
	private LinkedHashMap<String, Integer> wordToId;
	private ArrayList<Page> pages;
	
	public PageDB() {
		wordToId = new LinkedHashMap<String, Integer>();
		pages = new ArrayList<Page>();
	}
	
	public ArrayList<Page> getPages() {
		return pages;
	}
	public HashMap<String, Integer> getWordToId() {
		return wordToId;
	}
	public void addPage(Page page) {
		//Do not allow duplicates
		for(Page p : pages) {
			if(p.getURL().equals(page.getURL())) {
				return;
			}
		}
		pages.add(page);
	}
	public int getIdForWord(String word) {
		String lword = word.toLowerCase();
		if(wordToId.containsKey(lword)) {
			return wordToId.get(lword);
		}
		else {
			int id = wordToId.size();
			wordToId.put(lword, id);
			return id;
		}
	}
	public void calculatePageRank() {
		for(int i = 0; i < 20; i++) {
			for(Page p : this.getPages()) {
				this.iteratePageRank(p);
			}
		}
	}
	private void iteratePageRank(Page p) {
		double pr = 0.0;
		for(Page po : this.getPages()) {
			if(po.hasLinkTo(p.getURL())) {
				pr += po.pageRank / (double)po.linksCount();
			}
		}
		pr = 0.85 * pr + 0.15;
		p.pageRank = pr;
	}
	public void savePageIndex(String filePath) {
		try {
			PrintWriter writer = new PrintWriter(filePath);
			for (Map.Entry<String, Integer> entry : wordToId.entrySet()) {
			    String word = entry.getKey();
			    Integer id = entry.getValue();
			    writer.println(id + " " + word);
			}
			writer.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	public HashMap<String, Integer> loadPageIndex(String filePath) {
		wordToId = new LinkedHashMap<>();
		try {
			BufferedReader reader = new BufferedReader(new FileReader(filePath));
			String line;
			while((line = reader.readLine()) != null) {
				String[] list = line.split(" ");
				String word = list[1];
				int id = Integer.parseInt(list[0]);
				wordToId.put(word, id);
			}
			reader.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return wordToId;
	}
}

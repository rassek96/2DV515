package model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

import groovyjarjarantlr.StringUtils;
import searchEngine.Page;

public class WikiData {
	PageDB db;
	
	public WikiData(PageDB db) {
		this.db = db;
	}
		
	public PageDB populateDB(String filePath) {
		//Get all directories in filepath (wiki data directory)
		File dir = new File(filePath);
		File[] dirList = dir.listFiles();
		if(dirList != null) {
			//For every file or directory
			for(File child : dirList) {
				//If it's a directory go into it
				if(child.exists() && child.isDirectory()) {
					populateDB(filePath + "/" + child.getName());
				} 
				//If it's a file generate Page object
				else if(child.exists() && child.isFile()) {
					//Get matching links file
					String linkFilePath = filePath.replaceFirst("Words", "Links"); 
					File links = new File(linkFilePath + "/" + child.getName());
					String url = "/wiki/" + child.getName();
					generatePage(url, child, links);
				}
			}
		}
		return db;
	}
	
	private void generatePage(String url, File wordsFile, File linksFile) {
		try {
			ArrayList<Integer> words = new ArrayList<>();
			ArrayList<String> links = new ArrayList<>();
			//Read from words file
			BufferedReader wReader = new BufferedReader(new FileReader(wordsFile));
			String line;
			while((line = wReader.readLine()) != null) {
				String[] wlist = line.split(" ");
				for(String w : wlist) {
					int id = db.getIdForWord(w);
					words.add(id);
				}
			}
			wReader.close();
			//Read from links file
			BufferedReader lReader = new BufferedReader(new FileReader(linksFile));
			while((line = lReader.readLine()) != null) {
				String[] lList = line.split(" ");
				for(String l : lList) {
					links.add(l);
				}
			}
			lReader.close();
			Page page = new Page(url, words, links);
			db.addPage(page);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
}

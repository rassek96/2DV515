package model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

import clusterings.Article;
import clusterings.Word;

public class BlogData {
	private ArrayList<Article> articles = new ArrayList<Article>();
	private ArrayList<String> words = new ArrayList<String>();
	private String contents;
	
	public BlogData(String filePath) {
		this.contents = this.getContent(filePath);
	}
	
	public ArrayList<Article> getArticles() {
		return articles;
	}
	public ArrayList<String> getWords() {
		return words;
	}
	public String getContents() {
		return contents;
	}
	
	private String getContent(String filePath) {
		String content = "";
		try {
			//Read from file blogdata.txt
			BufferedReader reader = new BufferedReader(new FileReader(filePath));
			String line;
			int counter = 0;
			while((line = reader.readLine()) != null) {
				content += line + System.lineSeparator();
				//If it's the first line add the words to an arraylist for future use
				if(counter == 0) {
					String[] wordsArray = line.split("\t");
					for(String word : wordsArray) {
						if(!word.equals("")) {
							words.add(word);
						}
					}
				}
				//Add articles to class Article with their word counts
				else {
					HashMap<String, Integer> wordsCount = new HashMap<String, Integer>();
					ArrayList<Word> articleWords = new ArrayList<Word>();
					String articleName = "";
					String[] articleArray = line.split("\t");
					int iterateCount = 0;
					for(String word : articleArray) {
						//If it's the first word it's the name
						if(iterateCount == 0) {
							articleName = word;
						}
						//Attach the word count with the word using a hashmap
						else if(!word.equals("")) {
							wordsCount.put(words.get(iterateCount-1), Integer.parseInt(word));
							Word newWord = new Word(words.get(iterateCount-1), Integer.parseInt(word), articleName);
							articleWords.add(newWord);
						}
						iterateCount++;
					}
					//Create the Article class and add it to a list of Articles for future use
					Article article = new Article(articleName, wordsCount, articleWords);
					articles.add(article);
				}
				counter++;
			}
			reader.close();
		} catch (FileNotFoundException e) {
			//If file is not found, generate wiki data and try again
			this.generateWikiData("src/main/resources/data/Words");
			getContent(filePath);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return content;
	}
	
	//Generate Wiki data
	private ArrayList<String> lines = new ArrayList<>();
	private String[] artWords = {"WikiArticle", "Language", "Programming", "Computer", "Software", "Hardware", "Data", "Player", "Online", "System", "Development", "Machine", "Console", "Developer", "Design", "History", "Technology", "Standard", "Information", "Article", "Example", "Game", "Programming", "Action", "Company", "Latest", "Version", "Released", "Character", "Main", "Line", "Code", "Series"};
	public void generateWikiData(String filePath) {
		String artWordline = "";
		for(String s : artWords) {
			if(!artWordline.equals("")) {
				artWordline += "\t";
			}
			artWordline += s;
		}
		lines.add(artWordline);
		this.getWiki(filePath);
		this.printWiki();
		System.out.println("Done!");
	}
	private void getWiki(String filePath) {
		//Get all directories in filepath (wiki data directory)
		File dir = new File(filePath);
		File[] dirList = dir.listFiles();
		if(dirList != null) {
			//For every file or directory
			for(File child : dirList) {
				//If it's a directory go into it
				if(child.exists() && child.isDirectory()) {
					this.getWiki(filePath + "/" + child.getName());
				} 
				//If it's a file count words for that article
				else if(child.exists() && child.isFile()) {
					this.convertFile(child, filePath);
				}
			}
		}
	}
	private void convertFile(File child, String filePath) {
		//Count words in Wiki files and stringify it  
		String name = child.getName();
		LinkedHashMap<String, Integer> articleWords = new LinkedHashMap<>();
		File file = new File(filePath + "/" + child.getName());
		try (LineNumberReader reader = new LineNumberReader(new FileReader(file))) {
			String line;
			while((line = reader.readLine()) != null) {
				for(String wordName : artWords) {
					int count = 0;
					articleWords.put(wordName, count);
					for(String el : line.split(" ")) {
						if(el.equalsIgnoreCase(wordName)) {
							count++;
							articleWords.put(wordName, count);
						}
					}
				}
			}
			
			//Add to datalines file
			line = "";
			for(Integer wCount : articleWords.values()) {
				line += "\t" + wCount;
			}
			lines.add(name + line);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	private void printWiki() {
		//Save wikiarticle data to file
		try(PrintWriter writer = new PrintWriter("src/main/resources/wikidata.txt")) {
			for(String line : lines) {
				writer.println(line);
			}
		} 
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}
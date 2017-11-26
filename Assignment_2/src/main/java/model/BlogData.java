package model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;

import clusterings.Article;
import clusterings.Word;

public class BlogData {
	private ArrayList<Article> articles = new ArrayList<Article>();
	private ArrayList<String> words = new ArrayList<String>();
	private String contents;
	
	public BlogData(String filePath) {
		contents = getContent(filePath);
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
		} catch (Exception e) {
			e.printStackTrace();
		}
		return content;
	}
}
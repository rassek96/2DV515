package clusterings;

public class Word {
	private String word;
	private String article;
	public double count;
	
	public Word(String wWord, double wCount, String wArticle) {
		word = wWord;
		article = wArticle;
		count = wCount;
	}
	
	public String getWord() {
		return word;
	}
	public String getArticle() {
		return article;
	}
}
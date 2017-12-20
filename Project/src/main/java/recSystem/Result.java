package recSystem;

public class Result implements Comparable<Result> {
	public int movieId;
	public String movieTitle;
	public double score;
	
	public Result(int movieId, String movieTitle, double score) { 
		this.movieId = movieId;
		this.movieTitle = movieTitle;
		double temp = score * 1000;
		temp = Math.round(temp);
		temp = temp / 1000;
		this.score = temp;
	}

	@Override
	public int compareTo(Result resultB) {
		if(this.score > resultB.score) {
			return 1;
		}
		else if(this.score < resultB.score) {
			return -1;
		}
		else {
			return 0;
		}
	}
}

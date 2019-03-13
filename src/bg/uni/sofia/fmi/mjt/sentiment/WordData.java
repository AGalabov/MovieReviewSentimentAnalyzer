package bg.uni.sofia.fmi.mjt.sentiment;

public class WordData {
	private int sumRatings;
	private int occurances;

	public WordData() {
		sumRatings = 0;
		occurances = 0;
	}

	public void addRatings(int rating) {
		this.sumRatings += rating;
		this.occurances++;
	}

	public int getOccurances() {
		return occurances;
	}

	public double getAverageRating() {
		return (double) sumRatings / occurances;
	}
}

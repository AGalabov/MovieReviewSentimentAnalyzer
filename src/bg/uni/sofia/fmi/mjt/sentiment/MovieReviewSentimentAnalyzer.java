package bg.uni.sofia.fmi.mjt.sentiment;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.stream.Collectors;

public class MovieReviewSentimentAnalyzer implements SentimentAnalyzer {

	private String reviewsFileName;
	private String stopwordsFileName;
	private HashSet<String> stopWords = new HashSet<String>();
	private HashMap<String, WordData> sentimentDictionary = new HashMap<String, WordData>();
	private HashMap<Integer, String> ratingsAsWord = new HashMap<Integer, String>();
	private static final double DOUBLE_TO_INT_PARSE_HELPER = 0.5;
	private static final double UNKNOWN_RATING = -1.0;

	public MovieReviewSentimentAnalyzer(String reviewsFileName, String stopwordsFileName) {
		this.reviewsFileName = reviewsFileName;
		this.stopwordsFileName = stopwordsFileName;
		getStopWords();
		setSentimentDictionary();
		setRatingsAsWord();
	}

	private void setSentimentDictionary() {
		String currentLine;
		try (BufferedReader reader = new BufferedReader(new FileReader(reviewsFileName))) {
			while ((currentLine = reader.readLine()) != null) {
				addSentimentDictionary(currentLine);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private int parseToInt(char x) {
		return x - '0';
	}

	private void addSentimentDictionary(String sentence) {
		char[] sentanceChar = sentence.toCharArray();
		int rating = parseToInt(sentanceChar[0]);
		String[] parts = sentence.substring(2).toLowerCase().split("[^a-zA-Z0-9]");
		for (int i = 0; i < parts.length; i++) {
			if (!stopWords.contains(parts[i])) {
				WordData wordData = new WordData();
				if (sentimentDictionary.containsKey(parts[i])) {
					wordData = sentimentDictionary.get(parts[i]);
				}
				wordData.addRatings(rating);
				sentimentDictionary.put(parts[i], wordData);
			}
		}
	}

	private void getStopWords() {
		String currentLine = new String();
		try (BufferedReader reader = new BufferedReader(new FileReader(stopwordsFileName))) {
			while (currentLine != null) {
				stopWords.add(currentLine.toLowerCase());
				currentLine = reader.readLine();
			}
			this.stopWords.add("");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void setRatingsAsWord() {
		ratingsAsWord.put(-1, "unknown");
		ratingsAsWord.put(0, "negative");
		ratingsAsWord.put(1, "somewhat negative");
		ratingsAsWord.put(2, "neutral");
		ratingsAsWord.put(3, "somewhat positive");
		ratingsAsWord.put(4, "positive");
	}

	@Override
	public double getReviewSentiment(String review) {
		double ratingSum = 0;
		int wordsCount = 0;
		String[] parts = review.toLowerCase().split("[^a-zA-Z0-9]");
		for (int i = 0; i < parts.length; i++) {
			if (getWordSentiment(parts[i]) != -1) {
				ratingSum += getWordSentiment(parts[i]);
				wordsCount++;
			}
		}
		return (wordsCount == 0 ? UNKNOWN_RATING : ratingSum / wordsCount);
	}

	@Override
	public String getReviewSentimentAsName(String review) {
		double rating = getReviewSentiment(review);
		rating = (rating == UNKNOWN_RATING ? rating : rating + DOUBLE_TO_INT_PARSE_HELPER);
		String output = ratingsAsWord.get((int) rating);
		return output;
	}

	@Override
	public double getWordSentiment(String word) {
		if (sentimentDictionary.containsKey(word)) {
			return sentimentDictionary.get(word.toLowerCase()).getAverageRating();
		}
		return UNKNOWN_RATING;
	}

	@Override
	public Collection<String> getMostFrequentWords(int n) {
		return sentimentDictionary.entrySet().stream()
				.sorted((e1, e2) -> Integer.compare(e2.getValue().getOccurances(), e1.getValue().getOccurances()))
				.limit(n).map(Map.Entry::getKey).collect(Collectors.toList());
	}

	@Override
	public Collection<String> getMostPositiveWords(int n) {

		return sentimentDictionary.entrySet().stream()
				.sorted((e1, e2) -> Double.compare(e2.getValue().getAverageRating(), e1.getValue().getAverageRating()))
				.limit(n).map(Map.Entry::getKey).collect(Collectors.toList());
	}

	@Override
	public Collection<String> getMostNegativeWords(int n) {
		return sentimentDictionary.entrySet().stream()
				.sorted((e1, e2) -> Double.compare(e1.getValue().getAverageRating(), e2.getValue().getAverageRating()))
				.limit(n).map(Map.Entry::getKey).collect(Collectors.toList());
	}

	@Override
	public int getSentimentDictionarySize() {
		return sentimentDictionary.size();
	}

	@Override
	public boolean isStopWord(String word) {
		return stopWords.contains(word);
	}
	
	
}

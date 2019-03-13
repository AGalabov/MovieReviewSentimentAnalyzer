package bg.uni.sofia.fmi.mjt.sentiment;

import java.util.Collection;

public class Main {
    public static void main(String[] args)
    {
        MovieReviewSentimentAnalyzer analyzer = new MovieReviewSentimentAnalyzer("movieReviews.txt","stopwords.txt");
        //System.out.println(w.getReviewSentimentAsName("Heart warming movie - love all the details and how they portray the Mexican culture beautifully!"));
        Collection<String> freq = analyzer.getMostFrequentWords(11);
        Collection<String> pos = analyzer.getMostPositiveWords(982);
        Collection<String> neg = analyzer.getMostNegativeWords(968);


        System.out.println("Some word sentiment tests:");

        System.out.println(analyzer.getWordSentiment("skateboards") == 4.0);
        System.out.println(analyzer.getWordSentiment("staggering") == 4.0);
        System.out.println(analyzer.getWordSentiment("achievements") == 3.5);
        System.out.println(analyzer.getWordSentiment("popular") == 3.25);
        System.out.println(analyzer.getWordSentiment("international") == 2.6);
        System.out.println(analyzer.getWordSentiment("snowball") == 2.0);
        System.out.println(analyzer.getWordSentiment("dude") == 1.75);
        System.out.println(analyzer.getWordSentiment("mediterranean") == 1.0);
        System.out.println(analyzer.getWordSentiment("cash") == 0.0);

        System.out.println("Some review sentiment scores:");

        System.out.println(analyzer.getReviewSentiment("A weak script that ends with a quick and boring finale."));
        System.out.println(analyzer.getReviewSentiment("The funniest comedy of the year, good work! Don't miss it!"));


        System.out.println("Some collection tests:");

        System.out.println(freq.contains("good"));

        //None of the most positive words should have anything but 4.0 rating
        for(String str : pos) {
            if(analyzer.getWordSentiment(str) != 4.0)
            {
                System.out.println("FALSE!!!");
            }
        }

        //None of the most negative words should anything but 0.0
        for(String str : neg) {
            if(analyzer.getWordSentiment(str) != 0.0)
            {
                System.out.println("FALSE!!!");
            }
        }
    }
}

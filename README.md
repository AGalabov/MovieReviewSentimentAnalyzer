# MovieReviewSentimentAnalyzer
The program reads a file of reviews starting with a mark varying from 0 to 4.

  0 -> "negative"
  1 -> "somewhat negative"
  2 -> "neutral"
  3 -> "somewhat positive"
  4 -> "positive"

Based on that knowledge it can access the top few positive/negative/most used words and also calculate a mark for any review.

##Usage
```java
public double getReviewSentiment(String review)
public String getReviewSentimentAsName(String review)
public double getWordSentiment(String word)
public Collection<String> getMostFrequentWords(int n)
public Collection<String> getMostPositiveWords(int n)
public Collection<String> getMostNegativeWords(int n)
```

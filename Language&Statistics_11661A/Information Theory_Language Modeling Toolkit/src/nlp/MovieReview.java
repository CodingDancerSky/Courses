package nlp;

import java.util.HashMap;

public class MovieReview {
  public int id;
  int wordCount;
  int[] letterDistribution = new int[26];
  HashMap<String, Integer> diversity= new HashMap<String, Integer>();
  HashMap<Integer, Integer> sentenceLength= new HashMap<Integer, Integer>();
  
  public MovieReview(int id) {
    this.id = id;
  }
  
  public void setID(int id) {
    this.id = id;
  }
  public void setWordCount(int wordCount) {
    this.wordCount = wordCount;
  }
  public int[] getLetterDistribution() {
    return letterDistribution;
  }

  public void setLetterDistribution(int[] letterDistribution) {
    this.letterDistribution = letterDistribution;
  }

  public int getWordCount() {
    return wordCount;
  }

  public HashMap<String, Integer> getDiversity() {
    return diversity;
  }

  public HashMap<Integer, Integer> getSentenceLength() {
    return sentenceLength;
  }

  public void setDiversity(HashMap<String, Integer> diversity) {
    this.diversity = diversity;
  }
  public void setSentenceLength(HashMap<Integer, Integer> sentenceLength) {
    this.sentenceLength = sentenceLength;
  }
  public void setletterDistribution(int[] letterDistribution) {
    this.letterDistribution = letterDistribution;
  }
}

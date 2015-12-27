package nlp;

import java.util.HashMap;

public class ReviewAnalysis {
  public String status;
  int totalWordCounts;
  int[] totalLetterDistribution = new int[26];
  HashMap<String, Integer> totalDiversity= new HashMap<String, Integer>();
  HashMap<Integer, Integer> totalSentenceLength= new HashMap<Integer, Integer>();
  
//  String[] majorDiversity= new String[];
  HashMap<Integer, Integer> majorSentenceLength= new HashMap<Integer, Integer>();
  
//  public HashMap<String, Integer> getMajorDiversity() {
//    return majorDiversity;
//  }
//
//  public void setMajorDiversity(HashMap<String, Integer> majorDiversity) {
//    this.majorDiversity = majorDiversity;
//  }

  public HashMap<Integer, Integer> getMajorSentenceLength() {
    return majorSentenceLength;
  }

  public void setMajorSentenceLength(HashMap<Integer, Integer> majorSentenceLength) {
    this.majorSentenceLength = majorSentenceLength;
  }

  public ReviewAnalysis(String status){
    this.status = status;
  }
  
  public int getTotalWordCounts() {
    return totalWordCounts;
  }
  public void setTotalWordCounts(int totalWordCounts) {
    this.totalWordCounts = totalWordCounts;
  }
  public int[] getTotalLetterDistribution() {
    return totalLetterDistribution;
  }
  public void setTotalLetterDistribution(int[] totalLetterDistribution) {
    this.totalLetterDistribution = totalLetterDistribution;
  }
  public HashMap<String, Integer> getTotalDiversity() {
    return totalDiversity;
  }
  public void setTotalDiversity(HashMap<String, Integer> totalDiversity) {
    this.totalDiversity = totalDiversity;
  }
  public HashMap<Integer, Integer> getTotalSentenceLength() {
    return totalSentenceLength;
  }
  public void setTotalSentenceLength(HashMap<Integer, Integer> totalSentenceLength) {
    this.totalSentenceLength = totalSentenceLength;
  }
  
}

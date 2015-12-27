package ud;

import java.util.HashMap;

public class Unigram {
  HashMap<String, Float> wordProb = new HashMap<String, Float>();
  HashMap<Float, String> wordProbTotal = new HashMap<Float, String>();
  float[] probTotal = new float[100000];
  
  public HashMap<String, Float> getWordProb() {
    return wordProb;
  }
  public void setWordProb(HashMap<String, Float> wordProb) {
    this.wordProb = wordProb;
  }
  public HashMap<Float, String> getWordProbTotal() {
    return wordProbTotal;
  }
  public void setWordProbTotal(HashMap<Float, String> wordProbTotal) {
    this.wordProbTotal = wordProbTotal;
  }
  public float[] getProbTotal() {
    return probTotal;
  }
  public void setProbTotal(float[] probTotal) {
    this.probTotal = probTotal;
  }
}

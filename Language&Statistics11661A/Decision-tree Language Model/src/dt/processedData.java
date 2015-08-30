package dt;

import java.util.HashMap;

public class processedData {
  HashMap<String, Float> wordFreq = new HashMap<String, Float>();
  HashMap<String, Float> category1 = new HashMap<String, Float>();
  HashMap<String, Float> category2 = new HashMap<String, Float>();


  public HashMap<String, Float> getWordFreq() {
    return wordFreq;
  }

  public void setWordFreq(HashMap<String, Float> wordFreq) {
    this.wordFreq = wordFreq;
  }

  public HashMap<String, Float> getCategory1() {
    return category1;
  }

  public void setCategory1(HashMap<String, Float> category1) {
    this.category1 = category1;
  }

  public HashMap<String, Float> getCategory2() {
    return category2;
  }

  public void setCategory2(HashMap<String, Float> category2) {
    this.category2 = category2;
  }

  
}

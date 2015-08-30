package dt;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import utils.CategoryOne;
import utils.CategoryTwo;
import utils.FileUtils;


public class main {
  static float H;
  public static void main(String[] args) throws IOException {
    processedData trainning = displayLLAndComplexity("trainingCorpus");
    processedData testing = displayLLAndComplexity("testingCorpus");
    
    HashMap<String, Float> category1 = new HashMap<String, Float>();
    char[] groups1 = {'C', 'D', 'E', 'F', 'I', 'J', 'L', 'M', 'N', 'P', 'R', 'S', 'T', 'U', 'V', 'W'};
    for(int i = 0; i < groups1.length; i ++){
      char first = groups1[i];
      for(int j = 0; j < groups1.length; j ++){
        if(i != j){
          char second = groups1[j];
          CategoryOne.processCategoryOne(H, "sources/trainingCorpus", trainning, first, second);
        }
      }
     
//      System.out.println(getSortedHashtableByValue(trainning.category1));
    }
//    for(int i = 2; i < 5; i ++){
//      for(int j = 2; j < 5; j ++){
//        if(i != j){
//          CategoryTwo.processCategoryTwo(H, "sources/trainingCorpus", trainning, i, j);
//        }
//      }
    
    for(String s: trainning.category1.keySet()){    
      System.out.println(s + ":" + trainning.category1.get(s));
    }
  }
  
  static processedData displayLLAndComplexity(String file) throws IOException{
    processedData predData = FileUtils.readData("sources/" + file);
    HashMap<String, Float> wordProbability = predData.getWordFreq();
    float avgLogLiklihood = 0f;
    float entropy = 0f;
    for(String s: wordProbability.keySet()){
      float f = wordProbability.get(s);
      avgLogLiklihood += (Math.log(f) / Math.log(2));
      entropy -= f*(Math.log(f) / Math.log(2));
    }
    avgLogLiklihood /= wordProbability.size();
//    System.out.println(wordProbability.size());
    if(file.equals("trainingCorpus"))
      H = entropy;
    double complexity =  Math.pow(2F, -avgLogLiklihood);
    System.out.println(file + " Log likelihood: "+ avgLogLiklihood);
    System.out.println(file + " Complexity: "+ complexity);
    System.out.println(file + " Entropy: "+ entropy);
    
    return predData;
  }
  
  public static ArrayList getSortedHashtableByValue(HashMap h) {          
    ArrayList<HashMap.Entry<String,List>> l = new ArrayList<HashMap.Entry<String,List>>(h.entrySet());    
    Collections.sort(l, new Comparator<HashMap.Entry<String, List>>() {      
        public int compare(HashMap.Entry<String, List> o1, HashMap.Entry<String, List> o2) {      
            return (o2.getValue().size() - o1.getValue().size());      
        }      
    });       
    return l;    
}   
}

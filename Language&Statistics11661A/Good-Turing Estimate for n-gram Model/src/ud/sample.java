package ud;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;

import utils.FileUtil;

public class sample {
  static Random random = new Random();
  static float EPSSION = 0.000000307838783F;
  
  public static void main(String[] args) throws Exception {
    Unigram unigram = FileUtil.readData();
    
    
      try {
      
        File file = new File("sources/output.txt");
   
        // if file doesnt exists, then create it
        if (!file.exists()) {
          file.createNewFile();
        }
        HashMap<String, Integer> strings = new HashMap<String, Integer>();
        FileWriter fw = new FileWriter(file.getAbsoluteFile());
        BufferedWriter bw = new BufferedWriter(fw);
        for(int i = 0; i < 10000; i++){
          String w = getSample(unigram.wordProbTotal, unigram.probTotal);
          if(strings.containsKey(w)){
            int c = strings.get(w) + 1;
            strings.put(w, c);
          } else strings.put(w, 1);
          
          bw.write(w);
          bw.newLine();
        }
       
        bw.close();
        
        File file2 = new File("sources/output2.txt");
        
        // if file doesnt exists, then create it
        if (!file2.exists()) {
          file2.createNewFile();
        }
        HashMap<String, Integer> strings2 = new HashMap<String, Integer>();
        FileWriter fw2 = new FileWriter(file.getAbsoluteFile());
        BufferedWriter bw2 = new BufferedWriter(fw2);
        for(int i = 0; i < 10000; i++){
          String w = getSample(unigram.wordProbTotal, unigram.probTotal);
          if(strings2.containsKey(w)){
            int c = strings2.get(w) + 1;
            strings2.put(w, c);
          } else strings2.put(w, 1);
          
          bw2.write(w);
          bw2.newLine();
        }
       
        bw2.close();
   
        System.out.println("Done");
       
        int singleton = 0;
        int doubleton = 0;
        float trueP2 = 0f;
        float trueP33 = 0f;
        
        int count2 = 0;
        int count33 = 0;
        for(String s: strings.keySet()){
          trueP33 += unigram.wordProb.get(s);
          if(strings2.containsKey(s))
            count33 += strings2.get(s);
          if(strings.get(s) == 1){
            singleton++;
            trueP2 += unigram.wordProb.get(s);
            if(strings2.containsKey(s))
              count2 += strings2.get(s);
          }
          if(strings.get(s) == 2)
            doubleton++;
//          else System.out.println(s);
        }
        
        System.out.println();
        System.out.println("Problem 4:");
        
        if(strings.containsKey("of"))
          System.out.println("MLE(E1) = " + (float)strings.get("of") / 10000f);
        else System.out.println("MLE(E1) = " + 0.0);
        
        float Smle = (float)singleton / 10000f;

        System.out.println("MLE(E2) = " + Smle);
        
        System.out.println("MLE(E3) = " + 0.0);
        
        System.out.println();
        System.out.println("Problem 5:");
        
        float gte2 = (float)doubleton * 2 / 10000f;
        System.out.println("GTE(P_E2) = " + gte2);
        
        float gte3 = (float)singleton / 10000f;
        System.out.println("GTE(P_E3) = " + gte3);
        
        System.out.println();
        System.out.println("Problem 6:");
        System.out.println("P_true(E1) = " + unigram.wordProb.get("of"));
        System.out.println("P_true(E2) = " + trueP2);
        System.out.println("P_true(E3) = " + (1f - trueP33));
        
        System.out.println();
        System.out.println("Problem 6:");
        
        
        System.out.println("Count(E1) = " + strings2.get("of"));
        System.out.println("Count(E2) = " + count2);
        System.out.println("Count(E2) = " + (10000 - count2));
      } catch (IOException e) {
        e.printStackTrace();
      }    
  }
  
  public static String getSample(HashMap<Float, String> wordProbTotal, float[] probTotal){
    float r = random.nextFloat();
//    System.out.println(probTotal.length - 1);
    int index = getIndex(probTotal, r, 0, probTotal.length - 1);
//    System.out.println("get random:" + r);
    
    return wordProbTotal.get(probTotal[index]);
  }

  public static int getIndex(float[] probTotal, float r, int start, int end) {
    int mid = start + (end - start) / 2;
//    System.out.println("mid" + mid);
    
    if(Math.abs(probTotal[mid] - r) <= EPSSION)
      return mid;
    if(start >= end)
      return start;
    if(probTotal[mid] > r)
      return getIndex(probTotal, r, start, mid - 1);
    else return  getIndex(probTotal, r, mid + 1, end);
  }
}

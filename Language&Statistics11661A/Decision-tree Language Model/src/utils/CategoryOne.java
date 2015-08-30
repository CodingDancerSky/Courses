package utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Random;
import java.util.StringTokenizer;

import dt.processedData;

public class CategoryOne {
  public static void processCategoryOne(float entropy, String path, processedData predData, char first, char second) throws IOException{
    HashMap<String, Float> category1 = predData.getCategory1();
    HashMap<String, Integer> wordFreq1 = new HashMap<String, Integer>();
    HashMap<String, Integer> wordFreq2 = new HashMap<String, Integer>();
    HashMap<String, Integer> wordFreq3 = new HashMap<String, Integer>();
    
    try(BufferedReader br = new BufferedReader(new FileReader(path))) { 
      String line = br.readLine();
      int N = 0;
      int N1 = 0;
      int N2 = 0;
      int N3 = 0;

      String last = null;
      
      while (line != null) {
//        System.out.println(line);
        StringTokenizer st =new StringTokenizer(line);

        while(st.hasMoreTokens()){
          String word = st.nextToken();
//          System.out.println("prob"+prob);
          N++;
          
          if(N == 1) {
            last = word;
            N3++;
            if(!wordFreq3.containsKey(word))
              wordFreq3.put(word, 1);
            else{
              int count = wordFreq3.get(word) + 1;
              wordFreq3.put(word, count);
            }
            
            continue;
          }
          
          if(last.charAt(0) == first){
            N1 ++;
//            if(N1 <= 20) System.out.println(entropy1);
            if(!wordFreq1.containsKey(word))
              wordFreq1.put(word, 1);
            else{
              int count = wordFreq1.get(word) + 1;
              wordFreq1.put(word, count);
            }        
          }
          else{
            if(last.charAt(0) == second){
              N2 ++;
              if(!wordFreq2.containsKey(word))
                wordFreq2.put(word, 1);
              else{
                int count = wordFreq2.get(word) + 1;
                wordFreq2.put(word, count);
              }
              
            } else {
              N3++;
              if(!wordFreq3.containsKey(word))
                wordFreq3.put(word, 1);
              else{
                int count = wordFreq3.get(word) + 1;
                wordFreq3.put(word, count);
              }
          }
        }
        
        line = br.readLine();
       }
      }
      
      float entropy1 = getEntropy(wordFreq1, N);
      float entropy2 = getEntropy(wordFreq2, N);
      float entropy3 = getEntropy(wordFreq3, N);
//      System.out.println("N: " +N + " "+ "N1: "+ N1+" "+"N2: "+N2+" "+"N3: " + N3);
//      System.out.println("E: " +entropy + " "+ "E1: "+ entropy1+" "+"N2: "+entropy2+" "+"E3: " + entropy3);
      float WI = -10*(entropy - ((float)N1/(float)N) * entropy1 - ((float)N2/(float)N) * entropy2 - ((float)N3/(float)N) * entropy3)+0.1f;
//      System.out.println("WI"+WI);
      category1.put(first +""+ second, WI);
      predData.setCategory1(category1);
      
    }
  }

  public static float getEntropy(HashMap<String, Integer> wordFreq, int N) {
    // TODO Auto-generated method stub
    float e = 0f;
    for(String s: wordFreq.keySet()){
      float p = (float)wordFreq.get(s)/(float)N;
      e -= p*Math.log(p) / Math.log(2);
    }
    
    return e;
  }
}

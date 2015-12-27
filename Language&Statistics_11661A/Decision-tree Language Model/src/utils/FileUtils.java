package utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.StringTokenizer;

import dt.processedData;

public class FileUtils {
  
   public static processedData readData(String path) throws IOException{
     processedData pedData = new processedData();
     HashMap<String, Integer> wordFreq = new HashMap<String, Integer>();
     
     try(BufferedReader br = new BufferedReader(new FileReader(path))) { 
       String line = br.readLine();
       int N = 0;
       while (line != null) {
//         System.out.println(line);
         StringTokenizer st =new StringTokenizer(line);
//         String word = st.nextToken();
         while(st.hasMoreTokens()){
           String word = st.nextToken();
//           System.out.println(word);
           N++;
           
           if(!wordFreq.containsKey(word))
             wordFreq.put(word, 1);
           else{
             int count = wordFreq.get(word) + 1;
             wordFreq.put(word, count);
           }
         }
         
         line = br.readLine();
       }
       
       HashMap<String, Float> wordProb = new HashMap<String, Float>();
       for(String s: wordFreq.keySet()){
         float p = (float)wordFreq.get(s) / (float)N;
         wordProb.put(s, p);
       }
       pedData.setWordFreq(wordProb);
      
       return pedData;
     }
   }
}

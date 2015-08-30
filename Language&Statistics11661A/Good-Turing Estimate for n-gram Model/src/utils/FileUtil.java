package utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.StringTokenizer;

import ud.Unigram;


/*
 * Function: Read the documentations in a given forders from the given path.
 */

public class FileUtil {
  public static Unigram readData() throws IOException{
    Unigram unigram = new Unigram();
    HashMap<String, Float> wordProb = new HashMap<String, Float>();
    HashMap<Float, String> wordProbTotal = new HashMap<Float, String>();
    float[] probTotal = new float[100000];
    float sum = 0f;
    int i = 0;
    
    try(BufferedReader br = new BufferedReader(new FileReader("sources/unigram"))) { 
      String line = br.readLine();
      
      while (line != null) {
//        System.out.println(i + ": " + line);
        StringTokenizer st =new StringTokenizer(line);
        String word = st.nextToken();
//        System.out.println(word);
        float prob = Float.parseFloat(st.nextToken());
        
        sum += prob;
        
        wordProb.put(word, prob);
        wordProbTotal.put(sum, word);
        probTotal[i++] = sum;
        
        line = br.readLine();
      }  
    }
    
    unigram.setProbTotal(probTotal);
    unigram.setWordProb(wordProb);
    unigram.setWordProbTotal(wordProbTotal);
    
    return unigram;
  }  
}


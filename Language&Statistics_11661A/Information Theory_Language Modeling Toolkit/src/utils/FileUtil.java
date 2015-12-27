package utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;
import java.util.StringTokenizer;

import nlp.MovieReview;
import nlp.ReviewAnalysis;

/*
 * Function: Read the documentations in a given forders from the given path.
 */

public class FileUtil {
  public static List<MovieReview> readFilesInFolder(File folder) throws IOException{
    List<MovieReview> movieRs = new ArrayList<MovieReview>();
    int id = 1;
    
    for(File file: folder.listFiles()){
      if(file.isFile()){
        // a file corresponds to a movie
        MovieReview movieR = new MovieReview(id++);
        HashMap<String, Integer> diversity= new HashMap<String, Integer>();
        HashMap<Integer, Integer> sentenceLength= new HashMap<Integer, Integer>();
//        int[] letterDistribution = new int[26];
        
        StringBuilder sb = new StringBuilder();  // the content of the movie
        BufferedReader br = null;
      
        try{
          int wordCount = 0;
          br = new BufferedReader(new FileReader(file));
          String line = br.readLine();
          
          if(line != null){
            sb.append(line);
            StringTokenizer st = new StringTokenizer(line, " ");
            while(st.hasMoreTokens()){
              String s = st.nextToken().trim();
              if(s != null){
                wordCount++;
                if(diversity.containsKey(s)){
                  int count = diversity.get(s);
                  diversity.put(s, count + 1);
                } else diversity.put(s, 1);
              }
            }
          }
          
          movieR.setWordCount(wordCount);
          movieR.setDiversity(diversity);
          
          StringTokenizer sentenceT = new StringTokenizer(sb.toString());
          while(sentenceT.hasMoreTokens()){
            String s = sentenceT.nextToken().trim();
            int len = s.length();
            if(sentenceLength.containsKey(len)){
              int count = sentenceLength.get(len);
              sentenceLength.put(len, count + 1);
            } else sentenceLength.put(len, 1);
          }
          
          movieR.setSentenceLength(sentenceLength);
          movieRs.add(movieR);
        } 
        finally{
          if(br != null)
            br.close();
        }
      }
    }
    return movieRs;
  }
  
  public static void main(String[] args) throws IOException {
    ReviewAnalysis neg = new ReviewAnalysis("neg");
    List<MovieReview> movieRs = readFilesInFolder(new File("src/sources/tokens/pos"));
    int totalWordCounts = 0;;
    int[] totalLetterDistribution = new int[26];
    HashMap<String, Integer> totalDiversity= new HashMap<String, Integer>();
    HashMap<Integer, Integer> totalSentenceLength= new HashMap<Integer, Integer>();
    
    for(MovieReview mr: movieRs){
      totalWordCounts += mr.getWordCount();
//      System.out.println(totalWordCounts);
      HashMap<String, Integer> diversity= mr.getDiversity();
      HashMap<Integer, Integer> sentenceLength= mr.getSentenceLength();
      
      for(String s: diversity.keySet()){
        if(totalDiversity.containsKey(s)){
          int count = totalDiversity.get(s) + diversity.get(s);
          totalDiversity.put(s, count);
        } else totalDiversity.put(s, diversity.get(s));
      }
      
      for(Integer i: sentenceLength.keySet()){
        if(totalSentenceLength.containsKey(i)){
          int len = totalSentenceLength.get(i) + sentenceLength.get(i);
          totalSentenceLength.put(i, len);
        } else totalSentenceLength.put(i, sentenceLength.get(i));
      }
    }
      
      neg.setTotalDiversity(totalDiversity);
      neg.setTotalWordCounts(totalWordCounts);
      neg.setTotalSentenceLength(totalSentenceLength);
//      HashMap<String, Integer> majorDiversity= new HashMap<String, Integer>();
//      HashMap<Integer, Integer> majorSentenceLength= new HashMap<Integer, Integer>();
      
//      System.out.println("total number of different words: "+ totalWordCounts);
      
//      for(String s: totalDiversity.keySet()){
//        if(totalDiversity.get(s) >= 100 && totalDiversity.get(s) <= 500)
//        System.out.println("word: " + s + "  number: "+totalDiversity.get(s));
//////        int num = majorDiversity.get(s);
//////        if(!minDiversity.isEmpty()){
//////          if(minDiversity.size() <= 20){
//////          if(num > majorDiversity.get(minDiversity.peek())){
//////            
//////            }
//////          }
//////        } else minDiversity.push(s);
//      }
//      
      for(Integer i: totalSentenceLength.keySet()){
        if(totalSentenceLength.get(i) >= 10)
        System.out.println("the length of sentence: " + i + "  number: "+totalSentenceLength.get(i));
      }
    }
}


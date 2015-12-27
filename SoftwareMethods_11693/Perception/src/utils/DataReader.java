package utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import perception.Point;

/*
 * Function: Read file from the given path.
 */

public class DataReader {
    public static List<Point> readCSVFile(String resourcePath) {
      List<Point> result = new ArrayList<Point>();
      InputStream stream = DataReader.class.getClassLoader().getResourceAsStream(resourcePath);

      BufferedReader reader = null;
      try {
        reader = new BufferedReader(new InputStreamReader(stream, "utf-8"));
      } catch (UnsupportedEncodingException e2) {
        e2.printStackTrace();
      }

      int skipline = 2;
      String line = null;
      try {
        while ((line = reader.readLine()) != null) {
          if(--skipline >= 0)
            continue;
          String[] split = line.split(",");

          List<Character> features = new ArrayList<Character>();
          for(int i=0; i<split.length-1; i++) {
            features.add(split[i].charAt(0));
          }

          char[] arrayFeature = new char[features.size()];
          for(int i=0; i<features.size(); i++) {
            arrayFeature[i] = features.get(i);
          }
          
          Point p = new Point(arrayFeature, split[split.length-1]);
          result.add(p);
        }
      } catch (IOException e1) {
        e1.printStackTrace();
      }
      
      try {
        reader.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
      
      return result;
    }
    
    public static void main(String[] args) {
      List<Point> readCSVFile = DataReader.readCSVFile("Ravi/Ravi.csv");
      System.out.println(readCSVFile.size());
    }
  }

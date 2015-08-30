package utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import knn.Point;

/**
 * Function: Read the CVS file into point
 * @author Jude
 *
 */
public class FileToPoint {
public static List<Point> readCSVFile(String resourcePath){
  ArrayList<Point> result=new ArrayList<Point>();
  //find the file we need to read, "steam" is the connector
  InputStream stream=FileToPoint.class.getClassLoader().getResourceAsStream(resourcePath);
  //read text from a character-input stream, buffering characters
  BufferedReader reader = null;
  try {
    reader = new BufferedReader(new InputStreamReader(stream, "utf-8"));
  } catch (UnsupportedEncodingException e2) {
    e2.printStackTrace();
  }
  
  int skipline = 2;
  String line = null;
  try {
    while ((line = reader.readLine()) != null) {// read one line of the file
      if(--skipline >= 0) continue;//skip the nonsense lines.
      String[] split = line.split(","); //put the context into split by every ','
//如何读取label的？？？？？？？？？？？
      //store the position of the points
      ArrayList<Double> features = new ArrayList<Double>();
      for(int i=0; i<split.length-1; i++) {
        features.add(Double.parseDouble(split[i]));
      }
      double[] arrayFeature = new double[features.size()];
      for(int i=0; i<features.size(); i++) {
        arrayFeature[i] = features.get(i);
      }
      
      //store the points
      Point p = new Point(arrayFeature, split[split.length-1]);
      result.add(p);
    }
  } catch (IOException e1) {
    e1.printStackTrace();
  }
  
  //remember to close the read
  try {
    reader.close();
  } catch (IOException e) {
    e.printStackTrace();
  }
  
  return result;
}

}
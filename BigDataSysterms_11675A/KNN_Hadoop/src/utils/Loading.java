package utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.mapreduce.Mapper.Context;

import knn.KNN_Point;

/**
 * Function: Read the CVS file into point
 * @author Scarlett
 *
 */
public class Loading {
  public static List<KNN_Point> readFile(String resourcePath, Context context) throws IOException{
    ArrayList<KNN_Point> result = new ArrayList<KNN_Point>();
    FileSystem fs = FileSystem.get(context.getConfiguration());
    //find the file we need to read, "steam" is the connector
//    InputStream stream = Loading.class.getClassLoader().getResourceAsStream(resourcePath);
    //read text from a character-input stream, buffering characters
    BufferedReader reader = null;
    //reader = new BufferedReader(new InputStreamReader(stream, "utf-8"));
    reader = new BufferedReader(new InputStreamReader(fs.open(new Path(resourcePath))));
    
    String line = null;
    int id = 0;
    try {
      while ((line = reader.readLine()) != null) {
        String[] split = line.split(","); 

        //store the position of the points
        double[] arrayFeature = new double[4];
        for(int i = 0; i < 4; i++) {
          arrayFeature[i] = Double.parseDouble(split[i]);
        }
        
        //store the points
        KNN_Point p = new KNN_Point(id, arrayFeature);
        result.add(p);
        id++;
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
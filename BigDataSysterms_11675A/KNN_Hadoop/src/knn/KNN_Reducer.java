package knn;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class KNN_Reducer extends Reducer<Text, Text, Text, Text>{

    protected void reduce(
            Text key,
            Iterable<Text> value,
            Context context)
            throws java.io.IOException, InterruptedException {
      // sort 
      HashMap<String, Integer> labels = new HashMap<String, Integer>();
      for (Text val: value) {
        for(String v: val.toString().split(",")) {
          if(labels.containsKey(v)){
            int f = labels.get(v) + 1;
            labels.put(v, f);
          } else labels.put(v, 1);
        }
      }
      
      
      int max = 0;
      String label = "";
      for(String v: labels.keySet()){
        if(labels.get(v) > max){
          max = labels.get(v);
          label = v;
        }
      }
      
      Text outvalue = new Text();
      outvalue.set(label);
      context.setStatus(key.toString());
      context.write(key, outvalue);
  }

}
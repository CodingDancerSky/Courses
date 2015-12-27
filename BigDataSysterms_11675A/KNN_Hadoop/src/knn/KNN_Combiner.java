package knn;

import java.util.ArrayList;
import java.util.Collections;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.Reducer.Context;

public class KNN_Combiner extends Reducer<Text, Text, Text, Text>{
  protected void reduce(
          Text key,
          Iterable<Text> value,
          Context context)
          throws java.io.IOException, InterruptedException {
    // sort 
    ArrayList<String> sort = new ArrayList<String>();
    for(Text v: value) {
      String s = v.toString();
      sort.add(s);
    }
    Collections.sort(sort);
    int k = context.getConfiguration().getInt("k", 5);
    String s = "";
    for(int i  = 0; i < k - 1; i++){
      s += sort.get(i) + ",";
    }
    s += sort.get(k - 1);
    Text labels = new Text();
    labels.set(s);
    
    context.write(key, labels);
}
}

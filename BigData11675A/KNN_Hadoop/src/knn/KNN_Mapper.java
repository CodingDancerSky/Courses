package knn;

import java.util.ArrayList;
import java.util.List;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;

import utils.Loading;

public class KNN_Mapper extends Mapper<Object, Text, Text, Text>{
  List<KNN_Point> testingData = new ArrayList<KNN_Point>();
  
  protected void setup(
          org.apache.hadoop.mapreduce.Mapper<Object, Text, Text, Text>.Context context)
          throws java.io.IOException, InterruptedException {
      // load the test 
//      FileSystem fs = FileSystem.get(context.getConfiguration());
      //testingData = Loading.readFile(fs.open(new Path(context.getConfiguration().get(
      //        "org.niubility.learning.test", "test.arff"))));
      testingData = Loading.readFile("resources/iris_test_data.csv", context);
  }
  
  protected void map(
          Object key, 
          Text value, 
          org.apache.hadoop.mapreduce.Mapper<Object, Text, Text, Text>.Context context)
          throws java.io.IOException, InterruptedException {
      // calculate the distance for each test sample with the training data
      context.setStatus(key.toString());
      for (KNN_Point testCase : testingData) {
        // key: testdata id + distance   value: label
        Text keyout = new Text();
        keyout.set(testCase.getFeature());
        Text valueout = new Text();
        valueout.set(testCase.getDistance(value));
        context.write(keyout, valueout);
      }
  }    
}

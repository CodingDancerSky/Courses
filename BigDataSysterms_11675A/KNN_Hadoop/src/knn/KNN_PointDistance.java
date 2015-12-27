package knn;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.WritableComparable;

public class KNN_PointDistance implements WritableComparable<KNN_PointDistance>{
  float distance;
  String label;
  
  @Override
  public void readFields(DataInput arg0) throws IOException {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void write(DataOutput arg0) throws IOException {
    // TODO Auto-generated method stub
    
  }

  @Override
  public int compareTo(KNN_PointDistance another) {
    if(distance > another.distance)
      return 1;
    else return -1;
  }

}

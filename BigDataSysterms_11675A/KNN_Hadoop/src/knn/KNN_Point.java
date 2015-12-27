package knn;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.Writable;


/**
 * wrap the position and label of the point 
 */

// Vector2SF
public class KNN_Point implements Writable{
  double[] feature;
  String label;
  float distance;
  int id;

  public KNN_Point(int id, double[] feature) {
    super();
    this.id = id;
    this.feature = feature;
  }

  public float getDistance() {
    return distance;
  }

  public void setDistance(float distance) {
    this.distance = distance;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getFeature() {
    
    return feature[0] + "," + feature[1] + "," + feature[2] +"," + feature[3];
  }

  public void setFeature(double[] feature) {
    this.feature = feature;
  }

  public String getLabel() {
    return label;
  }

  public void setLabel(String label) {
    this.label = label;
  }
  
  public String getDistance(Text p) {
    double distance = 0d;
    String[] another = p.toString().split(",");
    for (int i = 0; i < 4; i++)
      distance += Math.pow(feature[i] - Double.parseDouble(another[i]), 2);
    
    distance = Math.sqrt(distance);
    
    return distance + "," + another[4];
  }

  @Override
  public void readFields(DataInput arg0) throws IOException {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void write(DataOutput arg0) throws IOException {
    // TODO Auto-generated method stub
    
  }
 
}

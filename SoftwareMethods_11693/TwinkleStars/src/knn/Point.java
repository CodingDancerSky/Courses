/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package knn;

public class Point {
  double[] feature;
  String label;

  public Point(double[] feature, String label) {
    super();
    this.feature = feature;
    this.label = label;
  }

  public double[] getFeature() {
    return feature;
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
  
  public double getDistance(Point p) {
    double distance = 0.0;
    for (int i = 0; i < feature.length; i++)
      distance += Math.pow(feature[i] - p.feature[i], 2); 
    
    distance = Math.sqrt(distance);
    return distance;
  }
 
}

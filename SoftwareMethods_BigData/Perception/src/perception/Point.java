package perception;

public class Point {
  char[] feature;
  String label;

  public Point() {
    super();
  }
  
  public Point(char[] feature, String label) {
    super();
    this.feature = feature;
    this.label = label;
  }

  public char[] getFeature() {
    return feature;
  }

  public void setFeature(char[] feature) {
    this.feature = feature;
  }

  public String getLabel() {
    return label;
  }

  public void setLabel(String label) {
    this.label = label;
  } 
}

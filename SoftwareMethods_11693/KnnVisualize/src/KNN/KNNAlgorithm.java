package KNN;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 * Get the label of a testing data
 */

public class KNNAlgorithm {
  List<Point> model;

  public KNNAlgorithm(List<Point> model) {
    super();
    this.model = model;
  }

  public String Cluster(Point target, int K) {
    // 1) find the knn
    List<Point> knn = getKNN(target, K); 
    
    // 2) return the majority tag in knn
    Map<String, Integer> frequencyMap = new HashMap<String, Integer>();
    
    for (Point p : knn) {
      if (!frequencyMap.containsKey(p.getLabel()))
        frequencyMap.put(p.getLabel(), 1);
      else
        frequencyMap.put(p.getLabel(), frequencyMap.get(p.getLabel()) + 1);
    }

    String cluster = null;
    int clusterFrequency = 0;
    for (String c : frequencyMap.keySet()) {
      int frequncy = frequencyMap.get(c);
      if (null == cluster || clusterFrequency < frequncy) {
        cluster = c;
        clusterFrequency = frequncy;
      }
    }
    return cluster;
  }

  private List<Point> getKNN(Point target, int k) {
    Edge[] knn = new Edge[k];

    for (Point p : model) {
      double distance = target.getDistance(p);
      int insertingIndex = -1;
      
      for (int i = 0; i < k; i++) {
        if (null == knn[i] || distance < knn[i].distance) {
          insertingIndex = i;
          break;
        }
      }

      if (insertingIndex != -1) {
        for (int j = k - 1; j > insertingIndex; j--) {
          knn[j] = knn[j - 1];
        }
        
        Edge edge = new Edge(p, distance);
        knn[insertingIndex] = edge;
      }
    }
    
    List<Point> result = new ArrayList<Point>();
    for (Edge e : knn) {
      result.add(e.p);
    }
    
    return result;
  }

  private static class Edge {
    Point p;
    double distance;

    public Edge(Point p, double distance) {
      super();
      this.p = p;
      this.distance = distance;
    }
  }
}

package KNN;

import java.util.List;

import utils.FileUtil;

public class KNN {
  private List<Point> trainingData;
  private List<Point> testingData;

  static KNNAlgorithm algorithm;

  public void init() {
    trainingData = FileUtil.readCSVFile("KNN/IrisTrain.csv");
    testingData = FileUtil.readCSVFile("KNN/IrisTest.csv");

    algorithm = new KNNAlgorithm(trainingData);
  }

  public void testing() {
    int index = 1;
    
    for (Point testp : testingData) {
      System.out.println(index++ + "\t" + algorithm.Cluster(testp, 5));
    }
  }

 

  public static void main(String[] args) {
    KNN obj = new KNN();
    obj.init();
    obj.testing();    
  }  
}

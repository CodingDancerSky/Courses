package perception;

import java.util.List;
import java.util.Random;

public class Algorithms {
  public static Random rand = new Random();
  double[] theta;
  double alpha = 0.0005;
  // int[] labels;

  public double[] training(List<ProcessingData> trainingData) {
    // labels = new int[trainingData.size()];
    theta = new double[16];

    // int index = 0;
    // for (ProcessingData data : trainingData) {
    // labels[index++] = data.status;
    // }

    for (int i = 0; i < 16; i++) {
      theta[i] = rand.nextDouble();
    }

    int count = 1;
    int size = trainingData.size();
    while (--count > 0) {
      for (int k = 0; k < trainingData.size(); k++) {
        // ProcessingData data = trainingData.get((k * 2 + k * 2 / size) % size);
        // ProcessingData data = trainingData.get(k);
        ProcessingData data = trainingData.get(k / 2 + (k % 2 * size / 2));  //mix the data with different status

        double y = data.status;
        double h = 0.0;
        for (int i = 0; i < 16; i++) {
          h += theta[i] * data.getFeatures()[i];
        }

        for (int i = 0; i < 16; i++) {
          theta[i] = theta[i] + alpha * (y - h) * data.getFeatures()[i];
        }
        
//        for (int i = 0; i < 16; i++) {
//          System.out.print(theta[i] + " ");
//        }
        
//        System.out.println();
//        System.out.println(y);
      }
    }
//    getError(trainingData);
    return theta;
  }

  public double[] getError(List<ProcessingData> trainingData) {
    double j = 0.0;
    double j_final = 0.0;
    double precision = 0.0;
    double hit = 0.0;
    
    for (ProcessingData data : trainingData) {
      double y = data.status;
      double h = 0.0;
      for (int i = 0; i < data.getFeatures().length; i++) {
        h += theta[i] * data.getFeatures()[i];
      }
//      System.out.println(h + "\t" + y);
      
      j += Math.pow(h - y, 2);  //overall error
      if (h * y <= 0) {
        j_final += Math.pow(h - y, 2);   //error
      } else {
        hit++;
      }
    }
    precision = hit / trainingData.size();
    System.out.println(j + "\t" + j_final + "\t" + precision);
    return new double[]{j, j_final, precision};
  }

}

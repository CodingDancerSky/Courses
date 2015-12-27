package perception;

import java.util.ArrayList;
import java.util.List;

import utils.DataReader;

public class Percept {
  List<ProcessingData> data = new ArrayList<ProcessingData>();
//    List<ProcessingData> training = new ArrayList<ProcessingData>();
//    List<ProcessingData> testing = new ArrayList<ProcessingData>();
  
  Algorithms algorithm = new Algorithms();
  
  public List<double[]> processing() {
    List<Point> lists = DataReader.readCSVFile("Ravi/Ravi.csv");
//    System.out.println(lists.size());
    
    for(Point p : lists) {
      ProcessingData pd = new ProcessingData();
      pd.setData(p);
      data.add(pd);
    }
    algorithm.training(data);
    List<double[]> dis = new ArrayList<double[]>();
      
    dis.add(algorithm.training(data));
    dis.add(algorithm.getError(data));
    return dis;
  }
  
  public static void main(String[] args) {
    Percept p = new Percept();
    p.processing();
  }  
}

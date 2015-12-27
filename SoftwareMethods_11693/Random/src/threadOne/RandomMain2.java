package threadOne;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class RandomMain2 {
  static final int threadNumber = 4;
  int number;

  List<RandomThread> threads = new ArrayList<RandomThread>();
  OutputStream stream = null;
  
  public RandomMain2(String path, int n) {
    try {
      stream = new FileOutputStream(new File(path));
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    number = n;
  }

  public RandomMain2(OutputStream s, int n) {
    stream = s;
    number = n;
  }

  public void init() {
    for (int i = 0; i < threadNumber; i++) {
      threads.add(new RandomThread(stream, this));
    }
    for (RandomThread thread : threads) {
      thread.start();
    }
  }
  
  /*
   * make sure only one thread in the function
   */
  public synchronized boolean checkNumber() {
    if (number > 0)
      return true;
    return false;
  }
  
  public synchronized void numberReduction() {
    number--;
  }

  public static void main(String[] args) {
    RandomMain2 main = new RandomMain2(System.out, 1000000);
    main.init();
  }
}

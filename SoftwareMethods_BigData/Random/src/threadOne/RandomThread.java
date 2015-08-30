package threadOne;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Random;

public class RandomThread extends Thread {
  public static final Random rand = new Random();

  double bound = 0.5;
  long NumOf1 = 1000;
  long NumOf0 = 1000;

  public long getNumOf1() {
    return NumOf1;
  }

  public void setNumOf1(long n) {
    NumOf1 = n;
  }

  public long getNumOf0() {
    return NumOf0;
  }

  public void setNumOf0(long n) {
    NumOf0 = n;
  }

  boolean running = true;

  RandomMain2 main;

  OutputStream stream;

  public RandomThread(OutputStream s, RandomMain2 m) {
    stream = s;
    main = m;
  }

  public int nextRand() {
    int r = 0;
    if (rand.nextDouble() > bound)
      r = 1;

    if (r == 0)
      NumOf1++;
    else
      NumOf0++;
    bound = ((double) NumOf1) / (NumOf1 + NumOf0);

    return r;
  }

  @Override
  public void run() {
    PrintWriter writer = new PrintWriter(stream);
    while (running) {
      if (!main.checkNumber()) {
        running = false;
        break;
      }
      writer.print(nextRand());
      main.numberReduction();
    }
    writer.close();
    System.err.println();
    System.err.println((NumOf1 - 1000) + "\t" + (NumOf0 - 1000));
  }
}

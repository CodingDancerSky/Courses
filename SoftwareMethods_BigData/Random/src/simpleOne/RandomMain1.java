package simpleOne;
import java.util.Random;

public class RandomMain1 {
  // avoid the beginning
  static int NumOf0 = 1000;
  static int NumOf1 = 1000;

  public static void main(String[] args) {
    int count = 1000;
    while (--count >= 0) {
      double bound = getbound();

      int rand = getRand(bound);
      if (rand == 0)
        NumOf0++;
      if (rand == 1)
        NumOf1++;
    }
  }

  static Random random = new Random();

  private static int getRand(double bound) {
    // TODO Auto-generated method stub
    double double_rand = random.nextDouble();

    int rand = 0;

    if (double_rand > bound)
      rand = 1;

    System.out.print(rand);

    return rand;
  }

  /*
   * balance the probability of 0 or 1 
   */
  private static double getbound() {
    // TODO Auto-generated method stub
    // if (NumOf0 == 0 && NumOf1 == 0)
    // return 0.5;

    double bound = ((double) NumOf0) / (NumOf0 + NumOf1);
    return bound;
  }
}
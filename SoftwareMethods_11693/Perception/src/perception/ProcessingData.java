package perception;

/*
 * Process these data and store as Point type
 */

public class ProcessingData {
  int[] o = new int[8];
  int[] x = new int[8];
  int status = 0; // Space:0 X:1 O:2

  public int[] getFeatures() {
    int[] feature = new int[o.length + x.length];
    for (int i = 0; i < 8; i++) {
      feature[i] = o[i];
    }
    for (int i = 8; i < 16; i++) {
      feature[i] = x[i - 8];
    }
    
    return feature;
  }

  public ProcessingData setData(Point p) {
    x = setInt(p.feature, 'x');
    o = setInt(p.feature, 'o');
    if (p.label.trim().equals("positive")) {
      status = 1;
    } else
      status = -1;
//    System.out.println(p.label + "\t" + status);
    
    return this;
  }

  public int[] setInt(char[] chars, char c) {
    int[] f = new int[9];
    int[] set = new int[8];
    for (int i = 0; i < chars.length; i++) {
      if (chars[i] == c)
        f[i] = 1;
      else
        f[i] = 0;
    }

    set[0] = f[0] + f[1] + f[2];
    set[1] = f[3] + f[4] + f[5];
    set[2] = f[6] + f[7] + f[8];
    set[3] = f[0] + f[3] + f[6];
    set[4] = f[1] + f[4] + f[7];
    set[5] = f[2] + f[5] + f[8];
    set[6] = f[0] + f[4] + f[8];
    set[7] = f[2] + f[4] + f[6];

    return set;
  }
}

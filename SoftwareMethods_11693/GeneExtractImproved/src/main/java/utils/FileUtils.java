package utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class FileUtils {
  @SuppressWarnings("resource")
  public static List<String> getLinesInFile(File f) {
    return getLinesInFile(f, "utf-8");
  }

  @SuppressWarnings("resource")
  public static List<String> getLinesInFile(File file, String mEncoding) {
    BufferedReader reader = null;
    try {
      reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), mEncoding));
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    String line = null;
    List<String> result = new ArrayList<String>();

    try {
      while ((line = reader.readLine()) != null) {
        line = line.trim();
        if ("".equals(line))
          continue;
        result.add(line);
      }
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      try {
        reader.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    return result;
    // TODO Auto-generated method stub

  }
}

import java.io.IOException;
import java.io.File;

import org.apache.uima.cas.CAS;
import org.apache.uima.cas.CASException;
import org.apache.uima.collection.CollectionException;
import org.apache.uima.collection.CollectionReader_ImplBase;
import org.apache.uima.util.Progress;
import org.apache.uima.util.FileUtils;
import org.apache.uima.jcas.JCas;


public class reader extends CollectionReader_ImplBase {
  private File mfile;
  private String mEncoding;
  public static final String PARAM_FILE= "InputFile";
  private int i=0;
  
  public void getNext(CAS aCAS) throws IOException, CollectionException {
    // TODO Auto-generated method stub
    JCas jcas;
    String fileName = (String) getConfigParameterValue(PARAM_FILE);
    System.out.println(fileName);
    mfile = new File(fileName);
    try {
      jcas = aCAS.getJCas();
    } catch (CASException e) {
      throw new CollectionException(e);
    }
    File file =(File) mfile;
    String text=FileUtils.file2String(file, mEncoding);
    jcas.setDocumentText(text);
  }

  public boolean hasNext() throws IOException, CollectionException {
    // TODO Auto-generated method stub
    if (i==0) {
      i++;
      return true;
      }
    else
    {
      i++;
      return false;}
  }

  public Progress[] getProgress() {
    // TODO Auto-generated method stub
    return null;
  }

  public void close() throws IOException {
    // TODO Auto-generated method stub
  }

}

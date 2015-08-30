import java.io.IOException;
import java.io.File;
import java.util.List;

import org.apache.uima.cas.CAS;
import org.apache.uima.cas.CASException;
import org.apache.uima.collection.CollectionException;
import org.apache.uima.collection.CollectionReader_ImplBase;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.util.Progress;
import org.apache.uima.util.FileUtils;
import org.apache.uima.jcas.JCas;

public class reader extends CollectionReader_ImplBase {
  private File mfile;

  public static final String PARAM_FILE = "InputFile";

  private int i = 0;

  private List<String> linesInFile;

  @Override
  public void initialize() throws ResourceInitializationException {

    String fileName = (String) getConfigParameterValue(PARAM_FILE);
    //String filePath = reader.class.getClassLoader().getResource(fileName).getFile();
    File file = new File(fileName);
    linesInFile = utils.FileUtils.getLinesInFile(file);
    super.initialize();
  }

  public void getNext(CAS aCAS) throws IOException, CollectionException {
    JCas jcas;
    try {
      jcas = aCAS.getJCas();
    } catch (CASException e) {
      throw new CollectionException(e);
    }
    jcas.setDocumentText(linesInFile.get(i++));
  }

  public boolean hasNext() throws IOException, CollectionException {
    // TODO Auto-generated method stub
    return linesInFile.size() > i;
  }

  public Progress[] getProgress() {
    // TODO Auto-generated method stub
    return null;
  }

  public void close() throws IOException {
    // TODO Auto-generated method stub
  }

}

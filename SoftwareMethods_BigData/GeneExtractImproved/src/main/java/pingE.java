import java.io.File;
import java.io.IOException;

import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.jcas.JCas;

import uima.types.gene;

import com.aliasi.chunk.Chunk;
import com.aliasi.chunk.Chunker;
import com.aliasi.chunk.Chunking;
import com.aliasi.util.AbstractExternalizable;

/**
 * Function: find the gene name by LingPipe recognizer
 * @author Jude
 *
 */
public class pingE extends JCasAnnotator_ImplBase {

  @Override
  public void process(JCas aJCas) throws AnalysisEngineProcessException {
    // TODO Auto-generated method stub
    JCas jcas = aJCas;
//    File currentModel = new File("src/main/resources/data/ne-en-bio-genetag-2.HmmChunker");

    Chunker temp = null;

    try {
      temp = (Chunker) AbstractExternalizable.readResourceObject("/data/ne-en-bio-genetag-2.HmmChunker");
    } catch (IOException e) {
      e.printStackTrace();
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }
    String documentText = jcas.getDocumentText();
    String[] split = documentText.split(" +", 2);

    String content = split[1];
    String id = split[0];
//    System.out.println("came here!");
//    System.out.println(content);
    Chunking chunk = temp.chunk(content);

    String gene;
    // Set<Chunk> it = chunk.chunkSet();
    for (Chunk c : chunk.chunkSet()) {
      gene = (content.substring(c.start(), c.end()));

      int begin = c.start();
      int end = c.end();
      begin = begin - countSpace(content.substring(0, begin));
      end = begin + gene.length() - countSpace(gene) - 1;

      gene gt = new uima.types.gene(aJCas);
      gt.setID(id);
      gt.setNAME(gene);
      gt.setBegin(begin);
      gt.setEnd(end);
      gt.addToIndexes();

    }

  }

  /** 
   * Function: count the space in each sentence to get the start and end indexes.
   * @param str
   * @return
   */
  private int countSpace(String str) {
    int countSpace = 0;
    for (int i = 0; i < str.length(); i++) {
      if (Character.isWhitespace(str.charAt(i))) {
        countSpace++;
      }
    }
    return countSpace;
  }

}

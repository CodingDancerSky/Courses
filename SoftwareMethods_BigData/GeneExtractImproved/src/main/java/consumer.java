import java.io.FileNotFoundException;
import java.io.PrintWriter;

import org.apache.uima.cas.CAS;
import org.apache.uima.cas.CASException;
import org.apache.uima.cas.FSIterator;
import org.apache.uima.collection.CasConsumer_ImplBase;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.tcas.Annotation;
import org.apache.uima.resource.ResourceProcessException;

import uima.types.gene;
import uima.types.numberIndex;

/**
 * Function: Get the gene from abner and LingPipe.
 * @author Jude
 *
 */
public class consumer extends CasConsumer_ImplBase {
  public String total = "";
  //Static file = new 
  public void processCas(CAS aCAS) throws ResourceProcessException {
    
    JCas jcas = null;
    try {
      jcas = aCAS.getJCas();
      FSIterator<Annotation> getGene = jcas.getAnnotationIndex(gene.type).iterator();
      // FSIterator<Annotation> getNum = jcas.getAnnotationIndex(numberIndex.type).iterator();
      PrintWriter author = new PrintWriter("hw2-xiangzhl.out");
      while (getGene.hasNext()) {

        gene g = (gene) getGene.next();
        // numberIndex n = (numberIndex) getNum.next();
//        System.out.println(g.getID() + " +++ " + g.getNAME());

        total += g.getID() + "|" + g.getBegin() + " " + g.getEnd() + "|" + g.getNAME() + "\n";
      }
      author.println(total);
      author.close();
    } catch (CASException e) {
      e.printStackTrace();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
  }
}

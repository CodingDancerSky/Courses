import java.util.HashSet;
import java.util.Set;

import org.apache.uima.UimaContext;
import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;

import uima.types.gene;
import abner.Tagger;

/**
 * Function: find the gene name by abner direction
 * @author Jude
 *
 */
public class abnerE extends JCasAnnotator_ImplBase {

  Tagger t = null;

  Set<String> resultType = null;

  @Override
  public void initialize(UimaContext aContext) throws ResourceInitializationException {
    resultType = new HashSet<String>();
    resultType.add("PROTEIN");
    resultType.add("DNA");
    resultType.add("RNA");

    if (null == t) {
      t = new Tagger();
      t.setTokenization(false);
      // TODO Auto-generated method stub
      super.initialize(aContext);
    }
  }

  @Override
  public void process(JCas jcas) throws AnalysisEngineProcessException {
    String documentText = jcas.getDocumentText();
    String[] split = documentText.split(" +", 2);

//    System.out.println("---------------------------");
//    for (String line : split) {
//      System.out.println(">>" + line);
//    }

    int contentMarker = 0;
    String[][] entities = t.getEntities(split[1]);
    String noSpaceString = split[1].replaceAll(" ", "");
    if (entities.length > 0) {
      for (int i = 0; i < entities[0].length; i++) {
//        System.out.println("++" + entities[0][i]);// gene name
//        System.out.println("++" + entities[1][i]);// gene type
        String noSpaceGene = entities[0][i].replaceAll(" ", "");
        int start = noSpaceString.indexOf(noSpaceGene, contentMarker);
        int end = start + noSpaceGene.length() - 1;
        
        System.out.println(start + "\t" + end + "\t" + entities[0][i]);
        System.out.println(split[1]);
        
        contentMarker = end;
        annotateGene(jcas, start, end, entities[0][i], split[0]);
      }
    }
  }

  private void annotateGene(JCas aJcas, int start, int end, String content, String id) {
    gene gene = new gene(aJcas);
    gene.setBegin(start);
    gene.setEnd(end);
    gene.setNAME(content);
    gene.setID(id);
    gene.addToIndexes();
  }
}


/* First created by JCasGen Tue Oct 07 19:08:06 EDT 2014 */
package uima.types;

import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.cas.impl.CASImpl;
import org.apache.uima.cas.impl.FSGenerator;
import org.apache.uima.cas.FeatureStructure;
import org.apache.uima.cas.impl.TypeImpl;
import org.apache.uima.cas.Type;
import org.apache.uima.cas.impl.FeatureImpl;
import org.apache.uima.cas.Feature;
import org.apache.uima.jcas.tcas.Annotation_Type;

/** 
 * Updated by JCasGen Tue Oct 07 19:08:06 EDT 2014
 * @generated */
public class numberIndex_Type extends Annotation_Type {
  /** @generated 
   * @return the generator for this type
   */
  @Override
  protected FSGenerator getFSGenerator() {return fsGenerator;}
  /** @generated */
  private final FSGenerator fsGenerator = 
    new FSGenerator() {
      public FeatureStructure createFS(int addr, CASImpl cas) {
  			 if (numberIndex_Type.this.useExistingInstance) {
  			   // Return eq fs instance if already created
  		     FeatureStructure fs = numberIndex_Type.this.jcas.getJfsFromCaddr(addr);
  		     if (null == fs) {
  		       fs = new numberIndex(addr, numberIndex_Type.this);
  			   numberIndex_Type.this.jcas.putJfsFromCaddr(addr, fs);
  			   return fs;
  		     }
  		     return fs;
        } else return new numberIndex(addr, numberIndex_Type.this);
  	  }
    };
  /** @generated */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = numberIndex.typeIndexID;
  /** @generated 
     @modifiable */
  @SuppressWarnings ("hiding")
  public final static boolean featOkTst = JCasRegistry.getFeatOkTst("uima.types.numberIndex");
 
  /** @generated */
  final Feature casFeat_Start;
  /** @generated */
  final int     casFeatCode_Start;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public int getStart(int addr) {
        if (featOkTst && casFeat_Start == null)
      jcas.throwFeatMissing("Start", "uima.types.numberIndex");
    return ll_cas.ll_getIntValue(addr, casFeatCode_Start);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setStart(int addr, int v) {
        if (featOkTst && casFeat_Start == null)
      jcas.throwFeatMissing("Start", "uima.types.numberIndex");
    ll_cas.ll_setIntValue(addr, casFeatCode_Start, v);}
    
  
 
  /** @generated */
  final Feature casFeat_End;
  /** @generated */
  final int     casFeatCode_End;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public int getEnd(int addr) {
        if (featOkTst && casFeat_End == null)
      jcas.throwFeatMissing("End", "uima.types.numberIndex");
    return ll_cas.ll_getIntValue(addr, casFeatCode_End);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setEnd(int addr, int v) {
        if (featOkTst && casFeat_End == null)
      jcas.throwFeatMissing("End", "uima.types.numberIndex");
    ll_cas.ll_setIntValue(addr, casFeatCode_End, v);}
    
  



  /** initialize variables to correspond with Cas Type and Features
	 * @generated
	 * @param jcas JCas
	 * @param casType Type 
	 */
  public numberIndex_Type(JCas jcas, Type casType) {
    super(jcas, casType);
    casImpl.getFSClassRegistry().addGeneratorForType((TypeImpl)this.casType, getFSGenerator());

 
    casFeat_Start = jcas.getRequiredFeatureDE(casType, "Start", "uima.cas.Integer", featOkTst);
    casFeatCode_Start  = (null == casFeat_Start) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_Start).getCode();

 
    casFeat_End = jcas.getRequiredFeatureDE(casType, "End", "uima.cas.Integer", featOkTst);
    casFeatCode_End  = (null == casFeat_End) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_End).getCode();

  }
}



    
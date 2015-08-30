

/* First created by JCasGen Tue Oct 07 19:08:06 EDT 2014 */
package uima.types;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import org.apache.uima.jcas.tcas.Annotation;


/** 
 * Updated by JCasGen Tue Oct 07 19:08:06 EDT 2014
 * XML source: /Users/Jude/Documents/workspace/hw2-xiangzhl/src/main/resources/typeSystemDescriptor.xml
 * @generated */
public class numberIndex extends Annotation {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(numberIndex.class);
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int type = typeIndexID;
  /** @generated
   * @return index of the type  
   */
  @Override
  public              int getTypeIndexID() {return typeIndexID;}
 
  /** Never called.  Disable default constructor
   * @generated */
  protected numberIndex() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated
   * @param addr low level Feature Structure reference
   * @param type the type of this Feature Structure 
   */
  public numberIndex(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated
   * @param jcas JCas to which this Feature Structure belongs 
   */
  public numberIndex(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated
   * @param jcas JCas to which this Feature Structure belongs
   * @param begin offset to the begin spot in the SofA
   * @param end offset to the end spot in the SofA 
  */  
  public numberIndex(JCas jcas, int begin, int end) {
    super(jcas);
    setBegin(begin);
    setEnd(end);
    readObject();
  }   

  /** 
   * <!-- begin-user-doc -->
   * Write your own initialization here
   * <!-- end-user-doc -->
   *
   * @generated modifiable 
   */
  private void readObject() {/*default - does nothing empty block */}
     
 
    
  //*--------------*
  //* Feature: Start

  /** getter for Start - gets 
   * @generated
   * @return value of the feature 
   */
  public int getStart() {
    if (numberIndex_Type.featOkTst && ((numberIndex_Type)jcasType).casFeat_Start == null)
      jcasType.jcas.throwFeatMissing("Start", "uima.types.numberIndex");
    return jcasType.ll_cas.ll_getIntValue(addr, ((numberIndex_Type)jcasType).casFeatCode_Start);}
    
  /** setter for Start - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setStart(int v) {
    if (numberIndex_Type.featOkTst && ((numberIndex_Type)jcasType).casFeat_Start == null)
      jcasType.jcas.throwFeatMissing("Start", "uima.types.numberIndex");
    jcasType.ll_cas.ll_setIntValue(addr, ((numberIndex_Type)jcasType).casFeatCode_Start, v);}    
   
    
  //*--------------*
  //* Feature: End

  /** getter for End - gets 
   * @generated
   * @return value of the feature 
   */
  public int getEnd() {
    if (numberIndex_Type.featOkTst && ((numberIndex_Type)jcasType).casFeat_End == null)
      jcasType.jcas.throwFeatMissing("End", "uima.types.numberIndex");
    return jcasType.ll_cas.ll_getIntValue(addr, ((numberIndex_Type)jcasType).casFeatCode_End);}
    
  /** setter for End - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setEnd(int v) {
    if (numberIndex_Type.featOkTst && ((numberIndex_Type)jcasType).casFeat_End == null)
      jcasType.jcas.throwFeatMissing("End", "uima.types.numberIndex");
    jcasType.ll_cas.ll_setIntValue(addr, ((numberIndex_Type)jcasType).casFeatCode_End, v);}    
  }

    


/* First created by JCasGen Mon Sep 22 21:31:40 EDT 2014 */
package some.typename.you;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import org.apache.uima.jcas.tcas.Annotation;


/** 
 * Updated by JCasGen Mon Sep 22 21:33:26 EDT 2014
 * XML source: /Users/Jude/Documents/workspace/hw1-xiangzhl/src/typeSystemDescriptor.xml
 * @generated */
public class gene extends Annotation {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(gene.class);
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
  protected gene() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated
   * @param addr low level Feature Structure reference
   * @param type the type of this Feature Structure 
   */
  public gene(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated
   * @param jcas JCas to which this Feature Structure belongs 
   */
  public gene(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated
   * @param jcas JCas to which this Feature Structure belongs
   * @param begin offset to the begin spot in the SofA
   * @param end offset to the end spot in the SofA 
  */  
  public gene(JCas jcas, int begin, int end) {
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
  //* Feature: ID

  /** getter for ID - gets 
   * @generated
   * @return value of the feature 
   */
  public String getID() {
    if (gene_Type.featOkTst && ((gene_Type)jcasType).casFeat_ID == null)
      jcasType.jcas.throwFeatMissing("ID", "some.typename.you.gene");
    return jcasType.ll_cas.ll_getStringValue(addr, ((gene_Type)jcasType).casFeatCode_ID);}
    
  /** setter for ID - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setID(String v) {
    if (gene_Type.featOkTst && ((gene_Type)jcasType).casFeat_ID == null)
      jcasType.jcas.throwFeatMissing("ID", "some.typename.you.gene");
    jcasType.ll_cas.ll_setStringValue(addr, ((gene_Type)jcasType).casFeatCode_ID, v);}    
   
    
  //*--------------*
  //* Feature: NAME

  /** getter for NAME - gets 
   * @generated
   * @return value of the feature 
   */
  public String getNAME() {
    if (gene_Type.featOkTst && ((gene_Type)jcasType).casFeat_NAME == null)
      jcasType.jcas.throwFeatMissing("NAME", "some.typename.you.gene");
    return jcasType.ll_cas.ll_getStringValue(addr, ((gene_Type)jcasType).casFeatCode_NAME);}
    
  /** setter for NAME - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setNAME(String v) {
    if (gene_Type.featOkTst && ((gene_Type)jcasType).casFeat_NAME == null)
      jcasType.jcas.throwFeatMissing("NAME", "some.typename.you.gene");
    jcasType.ll_cas.ll_setStringValue(addr, ((gene_Type)jcasType).casFeatCode_NAME, v);}    
  }

    
package org.fleen.rModel.core;

import java.awt.Color;

public abstract class SPE_Smell extends Square_PerceptualEvent_Abstract{

  /*
   * ################################
   * CONSTRUCTOR
   * ################################
   */
  
  public SPE_Smell(Vignette rmodel,int x,int y,int span){
    super(rmodel,x,y,span);}
  
  public SPE_Smell(Vignette rmodel,int span){
    super(rmodel,span);}

  /*
   * ################################
   * MANIFESTATION
   * ################################
   */
  
  public static final String TYPE="Smell";
  public static final Color COLOR=new Color(249,220,52);
  
  public String getType(){
    return TYPE;}
  
  public Color getColor(){
    return COLOR;}
  
}

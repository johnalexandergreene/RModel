package org.fleen.rModel.core;

import java.awt.Color;

public abstract class SPE_Touch extends Square_PerceptualEvent_Abstract{

  /*
   * ################################
   * CONSTRUCTOR
   * ################################
   */
  
  public SPE_Touch(Vignette vignette,int x,int y,int span){
    super(vignette,x,y,span);}
  
  public SPE_Touch(Vignette vignette,int span){
    super(vignette,span);}

  /*
   * ################################
   * MANIFESTATION
   * ################################
   */
  
  public static final String TYPE="Touch";
  public static final Color COLOR=new Color(232,86,89);
  
  public String getType(){
    return TYPE;}
  
  public Color getColor(){
    return COLOR;}

}

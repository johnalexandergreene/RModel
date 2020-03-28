package org.fleen.rModel.core;

import java.awt.Color;

public abstract class SPE_Taste extends Square_PerceptualEvent_Abstract{

  /*
   * ################################
   * CONSTRUCTOR
   * ################################
   */
  
  public SPE_Taste(Vignette vignette,int x,int y,int span){
    super(vignette,x,y,span);}
  
  public SPE_Taste(Vignette vignette,int span){
    super(vignette,span);}

  /*
   * ################################
   * MANIFESTATION
   * ################################
   */
  
  public static final String TYPE="Taste";
  public static final Color COLOR=new Color(249,155,70);
  
  public String getType(){
    return TYPE;}
  
  public Color getColor(){
    return COLOR;}

}

package org.fleen.rModel.core;

import java.awt.Color;

public abstract class SPE_Strange extends Square_PerceptualEvent_Abstract{

  /*
   * ################################
   * CONSTRUCTOR
   * ################################
   */
  
  public SPE_Strange(Vignette vignette,int x,int y,int span){
    super(vignette,x,y,span);}
  
  public SPE_Strange(Vignette vignette,int span){
    super(vignette,span);}

  /*
   * ################################
   * MANIFESTATION
   * ################################
   */
  
  public static final String TYPE="Strange";
  public static final Color COLOR=new Color(208,111,208);
  
  public String getType(){
    return TYPE;}
  
  public Color getColor(){
    return COLOR;}

}

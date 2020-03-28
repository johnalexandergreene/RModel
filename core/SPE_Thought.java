package org.fleen.rModel.core;

import java.awt.Color;

public abstract class SPE_Thought extends Square_PerceptualEvent_Abstract{

  /*
   * ################################
   * CONSTRUCTOR
   * ################################
   */
  
  public SPE_Thought(Vignette vignette,int x,int y,int span){
    super(vignette,x,y,span);}
  
  public SPE_Thought(Vignette vignette,int span){
    super(vignette,span);}

  /*
   * ################################
   * MANIFESTATION
   * ################################
   */
  
  public static final String TYPE="Thought";
  public static final Color COLOR=new Color(30,157,231);
  
  public String getType(){
    return TYPE;}
  
  public Color getColor(){
    return COLOR;}

}

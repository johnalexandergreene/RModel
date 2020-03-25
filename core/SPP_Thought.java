package org.fleen.rModel.core;

import java.awt.Color;

public abstract class SPP_Thought extends Square_PerceptualPhenomenon_Abstract{

  /*
   * ################################
   * CONSTRUCTOR
   * ################################
   */
  
  public SPP_Thought(Vignette_Abstract vignette,int x,int y,int span){
    super(vignette,x,y,span);}
  
  public SPP_Thought(Vignette_Abstract vignette,int span){
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

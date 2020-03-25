package org.fleen.rModel.core;

import java.awt.Color;

public abstract class SPP_Taste extends Square_PerceptualPhenomenon_Abstract{

  /*
   * ################################
   * CONSTRUCTOR
   * ################################
   */
  
  public SPP_Taste(Vignette_Abstract vignette,int x,int y,int span){
    super(vignette,x,y,span);}
  
  public SPP_Taste(Vignette_Abstract vignette,int span){
    super(vignette,span);}

  /*
   * ################################
   * MANIFESTATION
   * ################################
   */
  
  public static final String TYPE="Taste";
  public static final Color COLOR=new Color(249,175,108);
  
  public String getType(){
    return TYPE;}
  
  public Color getColor(){
    return COLOR;}

}

package org.fleen.rModel.core;

import java.awt.Color;

public abstract class SPP_Smell extends Square_PerceptualPhenomenon_Abstract{

  /*
   * ################################
   * CONSTRUCTOR
   * ################################
   */
  
  public SPP_Smell(Vignette_Abstract rmodel,int x,int y,int span){
    super(rmodel,x,y,span);}
  
  public SPP_Smell(Vignette_Abstract rmodel,int span){
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

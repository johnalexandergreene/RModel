package org.fleen.rModel.core;

import java.awt.Color;

public abstract class SPE_Sight extends Square_PerceptualEvent_Abstract{

  /*
   * ################################
   * CONSTRUCTOR
   * ################################
   */
  
  public SPE_Sight(Vignette rmodel,int x,int y,int span){
    super(rmodel,x,y,span);}
  
  public SPE_Sight(Vignette rmodel,int span){
    super(rmodel,span);}

  /*
   * ################################
   * MANIFESTATION
   * ################################
   */
  
  public static final String TYPE="Sight";
  public static final Color COLOR=new Color(251,174,213);
  
  public String getType(){
    return TYPE;}
  
  public Color getColor(){
    return COLOR;}

}

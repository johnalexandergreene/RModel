package org.fleen.rModel.core;

import java.awt.Color;

public abstract class SPE_Sound extends Square_PerceptualEvent_Abstract{

  /*
   * ################################
   * CONSTRUCTOR
   * ################################
   */
  
  public SPE_Sound(Vignette rmodel,int x,int y,int span){
    super(rmodel,x,y,span);}
  
  public SPE_Sound(Vignette rmodel,int span){
    super(rmodel,span);}

  /*
   * ################################
   * MANIFESTATION
   * ################################
   */
  
  public static final String TYPE="Sound";
  public static final Color COLOR=new Color(163,187,75);

  public String getType(){
    return TYPE;}
  
  public Color getColor(){
    return COLOR;}

}

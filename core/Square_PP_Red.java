package org.fleen.rModel.core;

import java.awt.Color;

public class Square_PP_Red extends Square_PP_Abstract{

  /*
   * ################################
   * CONSTRUCTOR
   * ################################
   */
  
  public Square_PP_Red(RModel rmodel,int x,int y,int span){
    super(rmodel,x,y,span);}
  
  public Square_PP_Red(RModel rmodel,int span){
    super(rmodel,span);}

  /*
   * ################################
   * STATE
   * ################################
   */
  
  public int getMaxAge(){
    return 100;
  }

  /*
   * ################################
   * RENDER
   * ################################
   */
  
  public double getIntensity(){
    return 1.0;
  }

  public Color getColor(){
    return Color.red;}
  
  public String getTitle(){
    return "Red";
  }

  public String getNarrative(){
    return "A wild Red appears.";
  }

}

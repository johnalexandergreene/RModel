package org.fleen.rModel.core;

import java.awt.Color;
import java.util.Random;

public class Square_PP_Thought extends Square_PP_Abstract{

  /*
   * ################################
   * CONSTRUCTOR
   * ################################
   */
  
  public Square_PP_Thought(RModel rmodel,int x,int y,int span){
    super(rmodel,x,y,span);}
  
  public Square_PP_Thought(RModel rmodel,int span){
    super(rmodel,span);
    Random rnd=new Random();
    maxage=rnd.nextInt(100)+30;}

  /*
   * ################################
   * STATE
   * ################################
   */
  
  int maxage;
  
  public int getMaxAge(){
    return maxage;}

  /*
   * ################################
   * RENDER
   * Override this for specific phenomena
   * ################################
   */
  
  public static final String NAME="Thought";
  public static final Color COLOR=new Color(30,157,231);
  
  public double getIntensity(){
    double 
      a=getAge(),
      s=getMaxAge();
    if(a/s<0.2){
      return a/(s*0.2);    
    }else if(a/s>0.8){
      return (s-a)/(s*0.2);}
    return 1.0;}

  public Color getColor(){
    return COLOR;}
  
  public String getType(){
    return NAME;}

  public String getNarrative(){
    return "Suddenly you think something.";}

}

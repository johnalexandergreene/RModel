package org.fleen.rModel.core;

import java.awt.Color;

public abstract class Square_PP_Abstract extends Square_Minimal{

  /*
   * ################################
   * CONSTRUCTORS
   * ################################
   */
  
  public Square_PP_Abstract(RModel rmodel,int x,int y,int span){
    super(rmodel,x,y,span);
    birthday=rmodel.age;}
  
  public Square_PP_Abstract(RModel rmodel,int span){
    this(rmodel,0,0,span);}
  
  /*
   * ################################
   * STATE
   * ################################
   */
  
  public int birthday;
  
  public int getAge(){
    return rmodel.age-birthday;}
  
  public boolean destroyMe(){
    return getAge()>getMaxAge();}
  
  //ie lifespan
  public abstract int getMaxAge();
  
  /*
   * ################################
   * RENDER THE PHENOMENON, GRAPHICALLY AND OTHERWISE
   * ################################
   */
  
  /*
   * governs zoom, alpha, maybe some other video (or even audio TODO) effect.
   * Range [0,1]
   * This will be a function of lifespan
   * a ramp up at the beginning, a persistence value (probably 1.0) and then a ramp down
   */
  public abstract double getIntensity();
  
  /*
   * to distinguish it from the other species
   */
  public abstract Color getColor();
  
  /*
   * printed on the icon 
   * used for type id too I suppose
   */
  public abstract String getType();
  
  /*
   * Used in the narrative stream 
   */
  public abstract String getNarrative();
  
}

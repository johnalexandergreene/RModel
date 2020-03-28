package org.fleen.rModel.core;

import java.awt.Color;

public abstract class Square_PerceptualEvent_Abstract extends Square_Minimal{

  /*
   * ################################
   * CONSTRUCTORS
   * ################################
   */
  
  public Square_PerceptualEvent_Abstract(Vignette rmodel,int x,int y,int span){
    super(rmodel,x,y,span);
    birthday=rmodel.age;}
  
  public Square_PerceptualEvent_Abstract(Vignette rmodel,int span){
    this(rmodel,0,0,span);}
  
  /*
   * ################################
   * STATE
   * summed as birth, death and intensity
   * birth happens when it's created
   * intensity is how loud, bright etc
   * death happens when age hits max 
   * ################################
   */
  
  protected int birthday;
  
  public int getAge(){
    return rmodel.age-birthday;}
  
  //ie lifespan
  protected abstract int getMaxAge();
  
  public boolean destroyMe(){
    return getAge()>getMaxAge();}
  
  /*
   * ################################
   * RENDER THE PHENOMENON, GRAPHICALLY AND OTHERWISE
   * ################################
   */
  
  /*
   * minimal abstract perceptual phenomenon manifestation
   * governs zoom, alpha, maybe some other video (or even audio TODO) effect.
   * Range [0,1]
   * This will be a function of age
   * a ramp up at the beginning, then a persist value (probably 1.0) and then a ramp down
   */
  public abstract double getIntensity();
  
  /*
   * to distinguish it from the other types
   */
  public abstract Color getColor();
  
  /*
   * sight, sound, smell etc
   */
  public abstract String getType();
  
  /*
   * the perceptual phenomenon in a word
   */
  public abstract String getWord();
  
  /*
   * Used in the narrative stream 
   */
  public abstract String getNarrative();
  
}

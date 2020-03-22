package org.fleen.rModel.core;

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
   * governs zoom, alpha, maybe some other video (or even autio TODO) effect.
   */
  public abstract double getIntensity();
  
  
  
  
}

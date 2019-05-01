package org.fleen.rModel.core;

import java.util.ArrayList;
import java.util.List;

public class RModel{
  
  /*
   * ################################
   * CONSTRUCTOR
   * ################################
   */
  
  public RModel(int width,int height){
    this.width=width;
    this.height=height;}
  
  /*
   * ################################
   * FIELD
   * ################################
   */
  
  public int width,height;
  
  /*
   * ################################
   * PHENOMENA
   * ################################
   */
  
  public int age=0; //the number of times that advanceState has been invoked
  public List<Phenomenon> phenomena=new ArrayList<Phenomenon>();
  
  public void clear(){
    phenomena.clear();
    age=0;}
  
  public void advanceState(){
    removeDeadPhenomena();
    advancePhenomena();
    advancePhysics();
    conditionallyCreatePhenomena();
    age++;
    notifyObservers();}
  
  /*
   * a phenomenon changed it's state. Growing or shrinking or maintaining or throbbing or something
   * offering data for a circle, it's location and radius. Its color, sound, throbbing frequency...
   */
  void advancePhenomena(){
    for(Phenomenon p:phenomena)
      p.advance();}
  
  void removeDeadPhenomena(){
    List<Phenomenon> toremove=new ArrayList<Phenomenon>();
    for(Phenomenon p:phenomena)
      if(p.dead)
        toremove.add(p);
    phenomena.removeAll(toremove);
    System.out.println("removed "+toremove.size());}
  
  /*
   * the phenomenon-representation-circles (PRC) grow and shrink and press on each other
   * account for the physics of that
   * the PRCs gravitate to the center of the clump, thus grouping and packing themselves. 
   * Account for the physics of that too 
   */
  void advancePhysics(){
    
  }
  
  /*
   * create 0..n new phenomenon objects
   * place them, initialize them
   * 
   * what we do depends on anything we like
   *   the present population of the model
   *   the population of certain phenomenon types
   *   the age of the model (in terms of the number of times that advance has been invoked)
   *   ... etc
   * 
   */
  void conditionallyCreatePhenomena(){
    //TEST
    if(phenomena.size()<333)
      phenomena.add(new Phenomenon(this));
    
  }
  
  /*
   * ################################
   * OBSERVERS
   * ################################
   */
  
  public List<RModelObserver> observers=new ArrayList<RModelObserver>();
  
  void notifyObservers(){
    for(RModelObserver a:observers)
      a.advanced();}
  
  /*
   * ################################
   * OBJECT
   * ################################
   */
  
  public String toString(){
    StringBuffer a=new StringBuffer("RMODEL");
    a.append("\n pcount : "+phenomena.size());
    a.append("\n age : "+age);
    return a.toString();}
  
}

package org.fleen.rModel.test_kgrid_based_circle_arrangement;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/*
 * we are arranging our circles with a KGrid
 * We have a bunch of hexagons (of varying sizes) defined within the kgrid, one for each phenomenon-circle
 * (thus no moving or packing and it looks nice)
 * We probably start with a hexagon centered at 0,0,0,0
 * Then we get new hexagons by selecting randomly from lists of prospects... 
 *   near the whole clump 
 *   near the clump of a certain type
 *   in the interstices rather than on the edge, or vice-versa
 *   etc.
 * 
 */
public class RModel{
  
  static final int TESTPCOUNT=1;
  
  /*
   * ################################
   * CONSTRUCTOR
   * ################################
   */
  
  public RModel(){
    addRandomPhenomenon();
    addRandomPhenomenon();
  }
  
  /*
   * ################################
   * KGRIDHEXAGONSYSTEM
   * It manages our geometry
   * ################################
   */
  
  public KGridHexagonSystem kghs=new KGridHexagonSystem();
  
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
//    if(phenomena.size()<TESTPCOUNT)
//      addRandomPhenomenon();
    
  }
  
  void addRandomPhenomenon(){
    Phenomenon p=new Phenomenon(this);
    phenomena.add(p);
    int s=new Random().nextInt(3)+1;
    p.hexagon=kghs.createHexagon(s);}
  
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

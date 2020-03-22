package org.fleen.rModel.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import org.fleen.geom_2D.DPoint;

/*
 * reality model
 * a bunch of square icons representing perceptual phenomena
 * taken together, as a pattern, represents a whole river perceptual phenomena. Ie reality.
 * 
 * So we have these perceptual phenomenon squares : class PPSquare
 * A PPSquare appears somewhere in the perspective. 
 *   It's appears. A colored square. 
 *   It fades in, persists, fades out. The duration of the fading is arbitrary, rresembling the phenomenon somewhat.
 *     (Example 1 : a "dog bark" will fade-zoom in quicky, persist for a moment, and immediately fade-zoom out.)
 *     (Example 2 : a "Car sound" will slowly fade-zoom in, persist for a mement, and then slowly fade-zoom out.)
 *   It will have a name, or an initial.
 *   It will have a color corresponding to sight, sound, thought etc.
 * Another PPSquare may appear, then another.
 * PPSquares will group by sense.
 * PPSquare size corresponds to intensity.
 * Fading in and out correspond to rise and fall in intensity.
 * Intensity corresponds to size and alpha.
 * 
 * Each group of sense squares will build in a certain direction, constrained to an angle-section from the origin.
 *   
 */
public class RModel{
  
  /*
   * ################################
   * CONSTRUCTOR
   * ################################
   */
  
  public RModel(){
    squares.add(new Square_PP_Red(this,-6,-6,12));
    squares.add(new Square_PP_Red(this,-6,7,3));
    squares.add(new Square_PP_Red(this,3,7,2));
    squares.add(new Square_PP_Red(this,7,4,8));
    
    
  }
  
  /*
   * ################################
   * GEOMETRY
   * ################################
   */
  
  public static final double CELLSPAN=1.0;
  
  /*
   * ################################
   * VIEW
   * ################################
   */
  
  public DPoint viewcenter=new DPoint(0,0);
  
  public double scale=10;
  
//  void setViewCenter(PDisk disk){
////    viewcenter=disk.getCenter().getDPoint();
//    }
//  
//  void setViewCenter(List<PDisk> disks){
//    //TODO
//    //centroid or whatever
//    }
  
  /*
   * ################################
   * STATE
   * ################################
   */
  
  public int age=0;
  
  public void advanceState(){

    
//    //TEST
//    Iterator<Mandala_Basic> i=mandalas.iterator();
//    Mandala_Basic m;
//    while(i.hasNext()){
//      m=i.next();
//      if(m instanceof Mandala_PP)
//        if(((Mandala_PP)m).destroyMe())
//          i.remove();
//    }
//    
//    
//    if(mandalas.size()<44)
//      addMandala(new Mandala_Red(this,0,0,rnd.nextInt(3)+1));
    
    
    observer.incrementedState();
    age++;}
  
  
  /*
   * ################################
   * OBSERVER
   * ################################
   */
  
  public RModelObserver observer;
  
  /*
   * ################################
   * SQUARES
   * ################################
   */
  
  public List<Square_Minimal> squares=new ArrayList<Square_Minimal>();
  
  public void addSquare(Mandala_Basic m){
//    if(mandalas.isEmpty()){
//      m.setLocation(0,0);
//    }else{
//      Mandala_Basic location=getLocationForNewMandala(m.radius);
//      m.setLocation(location.cx,location.cy);}
//    //
//    mandalas.add(m);
    
  }
  
//  void removeRandomMandala(){
//    int a=rnd.nextInt(mandalas.size());
//    mandalas.remove(a);}
 
  /*
   * ################################
   * OBJECT
   * ################################
   */
  
  public String toString(){
    StringBuffer a=new StringBuffer("RMODEL");
    a.append("\n scount : "+squares.size());
    a.append("\n age : "+age);
    return a.toString();}
  
  /*
   * ################################
   * UTIL
   * ################################
   */
  
  private Random rnd=new Random();

}

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
    addSquare(new Square_PP_Red(this,8));
    addSquare(new Square_PP_Red(this,8));
    
    
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
  
  public double scale=40;
  
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

    
    squares.clear();
    for(int i=0;i<8;i++)
      addSquare(new Square_PP_Red(this,rnd.nextInt(4)+1));
    
    
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
  
  /*
   * TODO make this work with type cone placement 
   */
  public void addSquare(Square_Minimal newsquare){
    if(squares.isEmpty()){ 
      newsquare.setLocation(0,0);
    }else{
      setLocationForNewSquare(newsquare);}
    //
    squares.add(newsquare);}
 
  /*
   * ################################
   * GET LOCATION FOR NEW SQUARE
   * We build clumps of squares
   * Probably clumped by type
   * All the clumps clump together around the origin
   * A typed clump is guided by a conical constraint fanning out from the origin. Thus we are organized.
   * 
   * given a new square : newsquare
   * get its skin cells
   * get the set of all extant squares' edge cells
   * get the set of all locations for newsquare where a particular skincell and an particular edgecell coincide
   * ################################
   */
  
  private void setLocationForNewSquare(Square_Minimal newsquare){
    //get the edges of all extant squares. we use it for a few things
    Set<Cell> extantedges=new HashSet<Cell>();
    for(Square_Minimal s:squares)
      extantedges.addAll(s.getEdge());
    //
    Set<Square_Minimal> prospects=getRawProspects(extantedges,newsquare);
    //
    cullCollisions(prospects);
    
    //test
    List<Square_Minimal> foo=new ArrayList<Square_Minimal>(prospects);
    int a=rnd.nextInt(prospects.size());
    Square_Minimal b=foo.get(a);
    newsquare.setLocation(b.x,b.y);
    
  }
  
//  /*
//   * for each prospect
//   * does its edge collide with any extant edge 
//   */
//  private void cullCollisions(Set<Cell> extantedges,Set<Square_Minimal> prospects){
//    Iterator<Square_Minimal> i=prospects.iterator();
//    Square_Minimal prospect;
//    Set<Cell> testprospectedge;
//    while(i.hasNext()){
//      prospect=i.next();
//      testprospectedge=new HashSet<Cell>(prospect.getEdge());
//      testprospectedge.retainAll(extantedges);
//      if(!testprospectedge.isEmpty())
//        i.remove();}}
  
  /*
   * for each prospect
   * does it collide any extant square?
   * test corner point interiority
   */
  private void cullCollisions(Set<Square_Minimal> prospects){
    Iterator<Square_Minimal> i=prospects.iterator();
    Square_Minimal prospect;
    while(i.hasNext()){
      prospect=i.next();
      if(collides(prospect))
        i.remove();}}
  
  private boolean collides(Square_Minimal prospect){
    DPoint[] cp=prospect.getCornerPoints();
    for(Square_Minimal s:squares){
      if(s.getEdgePath().contains(cp[0].x,cp[0].y))return true;
      if(s.getEdgePath().contains(cp[1].x,cp[1].y))return true;
      if(s.getEdgePath().contains(cp[2].x,cp[2].y))return true;
      if(s.getEdgePath().contains(cp[3].x,cp[3].y))return true;}
    return false;}
  
  /*
   * for every cell in newsquare.skin
   * for every cell in the set of all cells in the edge of all extant squares : extantedges
   * where the 2 coincide, that's a raw prospect.
   */
  private Set<Square_Minimal> getRawProspects(Set<Cell> extantedges,Square_Minimal newsquare){
    List<Cell> newsquareskin=newsquare.getSkin();
    //
    Set<Square_Minimal> prospects=new HashSet<Square_Minimal>();
    Square_Minimal prospect;
    for(Cell c0:newsquareskin){
      for(Cell c1:extantedges){
        prospect=new Square_Minimal(
          this,
          c1.x-c0.x,
          c1.y-c0.y,
          newsquare.span);
        prospects.add(prospect);}}
    //
    return prospects;}
  
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

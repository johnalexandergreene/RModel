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
  
  public RModel(){}
  
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
    //increment PPSquare states
    for(Mandala_Basic m:mandalas)
      if(m instanceof Mandala_PP)((Mandala_PP)m).incrementState();
    
    //TEST
    Iterator<Mandala_Basic> i=mandalas.iterator();
    Mandala_Basic m;
    while(i.hasNext()){
      m=i.next();
      if(m instanceof Mandala_PP)
        if(((Mandala_PP)m).destroyMe())
          i.remove();
    }
    
    
    if(mandalas.size()<44)
      addMandala(new Mandala_Red(this,0,0,rnd.nextInt(3)+1));
    
    
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
   * MANDALAS
   * Each represents a perceptual phenomenon
   * They are animated little things
   * They have a birth, a lifespan, a death. 
   * Thus representing the rise, persistence and fall of perceptual phenomena.
   * 
   * One trick is visually intelligible dynamic packing.
   * When a new mandala is added to the system we need to put it someplace nice. 
   * It needs to fit into the arrangement nicely. We might want to group it.
   * 
   * ################################
   */
  
  public List<Mandala_Basic> mandalas=new ArrayList<Mandala_Basic>();
  
  public void addMandala(Mandala_Basic m){
    if(mandalas.isEmpty()){
      m.setLocation(0,0);
    }else{
      Mandala_Basic location=getLocationForNewMandala(m.radius);
      m.setLocation(location.cx,location.cy);}
    //
    mandalas.add(m);}
  
  void removeRandomMandala(){
    int a=rnd.nextInt(mandalas.size());
    mandalas.remove(a);}
  
  /*
   * ################################
   * GET LOCATION FOR NEW MANDALA WITHOUT GROUPING
   * ################################
   */
  
  private static final int DEFAULTRANDOMSELECTIONSAMPLESIZE=2;
  
  //TODO : also do a method to position adjacent to a subset. A group, by tag
  Mandala_Basic getLocationForNewMandala(int radius){
    //get edge cells for all extant mandalas
    Set<Cell> extedges=new HashSet<Cell>();
    for(Mandala_Basic mext:mandalas)
      extedges.addAll(mext.getEdgeCells());
    //get all of the new mandala's skin cells in an origin-centered form
    Mandala_Basic mnew=new Mandala_Basic(this,radius);
    Set<Cell> mnewskin=mnew.getSkinCells();
    //get all possible collisions between the extant mandala edge cells and the new mandala skin cells
    //convert them to all prospective locations for the new mandala 
    List<Mandala_Basic> prospects=new ArrayList<Mandala_Basic>();
    int dx,dy;
    for(Cell ce:extedges){
      for(Cell cs:mnewskin){
        dx=ce.x-cs.x;
        dy=ce.y-cs.y;
        prospects.add(new Mandala_Basic(this,dx,dy,radius));}}
    //cull all locations that would result in the new circle intersecting an extant circle
    Mandala_Basic prospect;
    Iterator<Mandala_Basic> i;
    i=prospects.iterator();
    while(i.hasNext()){
      prospect=i.next();
      if(prospect.isColliding())i.remove();}
    //now we (should) have a number of viable locations. pick the best ones
    //we want our location to put the mandala where it snuggles up closely to another mandala. Ideally several will be snuggled.
    //we want our location to put the mandala as close to the origin as possible.
    //so we need a rating system. Then we sort them by rating
    Map<Mandala_Basic,Double> ratings=rateProspects(prospects);
    Collections.sort(prospects,new PMLComparator(ratings));
    //pick from the best several
    int s=DEFAULTRANDOMSELECTIONSAMPLESIZE;
    if(prospects.size()<s)s=prospects.size();
    Mandala_Basic winner=prospects.get(rnd.nextInt(s));
    return winner;}
  
  static final double 
    DISTANCESCALE=1.9,
    TOUCHSCALE=1.0;
  
  /*
   * ################################
   * RATE PLACEMENT PROSPECTS FOR NEW MANDALA 
   * ################################
   */
  
  private Map<Mandala_Basic,Double> rateProspects(List<Mandala_Basic> prospects){
    Map<Mandala_Basic,Double> ratings=new HashMap<Mandala_Basic,Double>();
    double distance,touches,rating;
    for(Mandala_Basic p:prospects){
      distance=p.getDistanceToOrigin();
      touches=p.getTouchCount();
      rating=touches*TOUCHSCALE-distance*DISTANCESCALE;//distance is negative because lower is better
      ratings.put(p,rating);}
    return ratings;}
  
  class PMLComparator implements Comparator<Mandala_Basic>{
    
    PMLComparator(Map<Mandala_Basic,Double> ratings){
      this.ratings=ratings;}
    
    Map<Mandala_Basic,Double> ratings;

    public int compare(
      Mandala_Basic a0,
      Mandala_Basic a1){
      double 
        r0=ratings.get(a0),
        r1=ratings.get(a1);
      if(r0==r1){
        return 0;
      }else if(r0<r1){
        return 1;
      }else{
        return -1;}}}
  
  /*
   * ################################
   * OBJECT
   * ################################
   */
  
  public String toString(){
    StringBuffer a=new StringBuffer("RMODEL");
    a.append("\n mcount : "+mandalas.size());
    a.append("\n age : "+age);
    return a.toString();}
  
  /*
   * ################################
   * UTIL
   * ################################
   */
  
  private Random rnd=new Random();

}

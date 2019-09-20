package org.fleen.rModel.test_kgrid_based_circle_arrangement;

import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.fleen.geom_2D.DPoint;
import org.fleen.geom_Kisrhombille.KGrid;
import org.fleen.geom_Kisrhombille.KPoint;
import org.fleen.geom_Kisrhombille.KPolygon;

/*
 * a kgrid with facilities for adding and removing nicely arranged hexagons of varying sizes
 */
public class KGridHexagonSystem extends KGrid{

  private static final long serialVersionUID=3263793356925786731L;
  
  /*
   * ################################
   * CONSTRUCTOR
   * ################################
   */
  
  public KGridHexagonSystem(){
    super();}
  
  /*
   * ################################
   * HEXAGONS
   * ################################
   */
  
  List<KGHSHexagon> hexagons=new ArrayList<KGHSHexagon>();
  
  /*
   * If hexcount is 0 then add one at the center of the grid
   * Otherwise check zone for viable hexagons
   * Pick one at random. Prefer interstitial to edge.
   */
  public KGHSHexagon createHexagon(int size){
    KGHSHexagon h=null;
    //TODO
    //need to address interstitial vs edge, clump, etc
    if(hexagons.isEmpty()){
      h=createOriginHexagon(size);
    }else{
      h=createEnclumpedHexagon(size);}
    hexagons.add(h);
    return h;}
  
  private KGHSHexagon createOriginHexagon(int size){
    KPoint c=new KPoint(0,0,0,0);
    KGHSHexagon h=new KGHSHexagon(c,size);
    return h;}
  
  /*
   * create a hexagon, of the specified size, that 
   *   is adjacent to an extant hexagon
   *   does not collide with any extant hexagon
   * 
   * ---
   * 
   * get the list of all extant hexagons : eh
   * get a random extant hexagon from eh, remove it from eh : h0
   * get its reticulated (not just the corners but between the corners too) edge points
   * for each of those edge points : p0
   *   create all prospective hexagons with p0 as a corner point : rawprospects
   *     for each hexagon in rawprospects : prospect
   *       if prospect does not collide with any extant hexagon then store it in prospects
   * if prospects is empty then try another extant hexagon
   * if prospects is not empty then pick a hexagon at random from that and return it.
   * if we are out of extant hexagons and we have no prospects then FAIL, return null
   *   
   */
  private KGHSHexagon createEnclumpedHexagon(int size){
    Random r=new Random();
    //get all extant hexagons
    List<KGHSHexagon> extants=new ArrayList<KGHSHexagon>(hexagons);
    //while no prospects, try random selections from that
    List<KGHSHexagon> prospects=new ArrayList<KGHSHexagon>();
    KGHSHexagon testextant;
    KPolygon reticulation;
    while(prospects.isEmpty()&&(!extants.isEmpty())){
      testextant=extants.remove(r.nextInt(extants.size()));
      reticulation=testextant.getReticulation();
      for(KPoint kp:reticulation)
        prospects.addAll(getProspects(kp,size));}
    //now we have prospects, pick one
    if(prospects.isEmpty())return null;
    KGHSHexagon h=prospects.get(r.nextInt(prospects.size()));
    return h;}
  
  /*
   * return the list of all hexagons that
   *   use the specified kpoint as a corner point
   *   does not use any points (that means its reticulation too) that are inside any extant hexagon
   *   
   * ---
   * Get every point at size+1 intervals from kp
   * from each of those points create a KGHSHexagon
   * test each of those hexagons to see if its reticulation contains any of the extant hexagons' internal points
   * if not then we have a prospect
   * if no prospects then return empty list 
   *   
   */
  private List<KGHSHexagon> getProspects(KPoint kp,int size){
    List<KPoint> hcenters;
    
  }
  
  /*
   * ################################
   * BOUNDS
   * ################################
   */
  
  public Rectangle2D.Double getBounds(){
    double minx=Double.MAX_VALUE,maxx=Double.MIN_VALUE,miny=minx,maxy=maxx;
    DPoint dp;
    for(KGHSHexagon h:hexagons){
      for(KPoint kp:h){
        dp=kp.getBasicPoint2D();
        if(dp.x<minx)minx=dp.x;
        if(dp.x>maxx)maxx=dp.x;
        if(dp.y<miny)miny=dp.y;
        if(dp.y>maxy)maxy=dp.y;}}
    Rectangle2D.Double b=new Rectangle2D.Double(minx,miny,maxx-minx,maxy-miny);
    return b;}
  

}

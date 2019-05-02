package org.fleen.rModel.test_kgrid_based_circle_arrangement;

import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

import org.fleen.geom_2D.DPoint;
import org.fleen.geom_Kisrhombille.KGrid;
import org.fleen.geom_Kisrhombille.KPoint;

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
    }
    return h;}
  
  private KGHSHexagon createOriginHexagon(int size){
    KPoint c=new KPoint(0,0,0,0);
    KGHSHexagon h=new KGHSHexagon(c,size);
    hexagons.add(h);
    return h;}
  
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

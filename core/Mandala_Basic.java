package org.fleen.rModel.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.fleen.geom_2D.GD;

/*
 * Base mandala class
 * Just the geometry
 * Used for testing the system
 * Also used for testing geometry in the dynamic circle packing thingy
 */
public class Mandala_Basic{
  
  /*
   * ################################
   * CONSTRUCTORS
   * ################################
   */
  
  public Mandala_Basic(RModel rmodel,int centerx,int centery,int radius){
    this.rmodel=rmodel;
    this.cx=centerx;
    this.cy=centery;
    this.radius=radius;}
  
  public Mandala_Basic(RModel rmodel,int radius){
    this(rmodel,0,0,radius);}
  
  /*
   * ################################
   * RMODEL
   * ################################
   */
  
  public RModel rmodel;

  /*
   * ################################
   * GEOMETRY
   * ################################
   */
  
  public int cx,cy,radius;
  
  /*
   * note that we invalidate the cell sets when we do this
   */
  public void setLocation(int x,int y){
    cx=x;
    cy=y;
    edgecells=null;
    skincells=null;}
  
  public double getDistanceToOrigin(){
    double d=GD.getDistance_PointPoint(cx,cy,0,0);
    return d;}
  
  /*
   * returns true if this mandala circle is in a state of collision with any mandala in the rmodel
   * if the distance between centers is less than radius+radius+CELLSPAN then it's collision
   */
  public boolean isColliding(){
    double r0,r1;
    for(Mandala_Basic m:rmodel.mandalas){
      r0=m.radius+radius+RModel.CELLSPAN;//note our carefully tweaked padding
      r1=GD.getDistance_PointPoint(cx,cy,m.cx,m.cy);
      if(r1<r0)return true;}
    return false;}
  
  /*
   * ################################
   * EDGE CELLS
   * Cell representation of the circle. A circle of square cells
   * drawn by Bresenham's
   * ################################
   */
  
  private Set<Cell> edgecells=null;
    
  public Set<Cell> getEdgeCells(){
   if(edgecells==null)initEdgeCells();
   return edgecells;}
  
  public void initEdgeCells(){
    int[][] preoffset=getCircleEdgeCellCoors(radius);
    edgecells=new HashSet<Cell>(preoffset.length);
    for(int[] a:preoffset)
      edgecells.add(new Cell(a[0]+cx,a[1]+cy));}
  
  /*
   * ################################
   * SKIN CELLS
   * the cells adjacent to the edge cells on the outside of the circle. 
   * we use them for collision testing.
   * ################################
   */
  
  private Set<Cell> skincells=null;
  
  public Set<Cell> getSkinCells(){
    if(skincells==null)initSkinCells();
    return skincells;}
  
  /*
   * for each cell in edgecells : ec
   * get all cells adjacent to ec
   * put them all in a set : b
   * remove from b all edge cells
   * remove from b cells inside the circle
   */
  private void initSkinCells(){
    Set<Cell> ec=getEdgeCells();
    skincells=new HashSet<Cell>();
    for(Cell c:ec)
      skincells.addAll(c.getAdjacents());
    skincells.removeAll(edgecells);
    sc_RemoveInsideCells(skincells);}
  
  private void sc_RemoveInsideCells(Set<Cell> skincells){
    Iterator<Cell> i=skincells.iterator();
    Cell c;
    while(i.hasNext()){
      c=i.next();
      if(sc_IsInside(c))
        i.remove();}}
  
  private boolean sc_IsInside(Cell c){
    double a=c.center.getDistance(cx,cy);
    boolean b=a<radius;
    return b;}
  
  /*
   * MANDALAS TOUCHING EACH OTHER
   * This calculates how many times this mandala touches other mandalas
   * This mandala is probably being used for location testing so it isn't actually in the model
   * get set of all cells of all edges of all mandalas in the rmodel : extantedges
   * get set of all cells in this mandala's skin : skin
   * if a cell is in both sets then that's a touch
   * count those cells
   */
  public int getTouchCount(){
    Set<Cell> extantedges=new HashSet<Cell>();
    for(Mandala_Basic m:rmodel.mandalas)
      extantedges.addAll(m.getEdgeCells());
    Set<Cell> skin=new HashSet<Cell>(getSkinCells());
    skin.retainAll(extantedges);
    return skin.size();}
  
  /*
   * ################################
   * STATIC BRESENHAM GEOMETRY
   * ################################
   */
  
  /*
   * lazy init bresenham circles
   */
  private static Map<Integer,int[][]> edgecellsbyradius=new HashMap<Integer,int[][]>();
  
  private static final int[][] getCircleEdgeCellCoors(int radius){
    int[][] c=edgecellsbyradius.get(radius);
    if(c==null){
      c=initCircleEdgeCellCoors(radius);
      edgecellsbyradius.put(radius,c);}
    return c;}
  
  /*
   * get the cellcoors of the edge of a circle in terms of offset from 0,0 
   * Then hold onto 1 for each radius (1,2,3... probably up to 7 or so)
   * thus we have pre-calculated that stuff
   */
  private static final int[][] initCircleEdgeCellCoors(int radius){
    List<int[]> coors=new ArrayList<int[]>();
    int y=radius;
    int x=0;
    int delta=calculateStartDelta(radius);
    while(y>=x){
      getCoorAndReflect(x,y,coors);
      if(delta<0) {
        delta=calculateDeltaForHorizontalPixel(delta, x);
      }else{
        delta=calculateDeltaForDiagonalPixel(delta, x, y);
        y--;}
      x++;}
    int[][] coorsa=coors.toArray(new int[coors.size()][2]);
    return coorsa;}

  private static int calculateStartDelta(int radius){
    return 3-2*radius;}

  private static int calculateDeltaForHorizontalPixel(int olddelta,int x){
    return olddelta+4*x+6;}

  private static int calculateDeltaForDiagonalPixel(int olddelta,int x,int y) {
    return olddelta+4*(x-y)+10;}

  private static void getCoorAndReflect(int x,int y,List<int[]> coors){
    coors.add(new int[]{x,y});
    coors.add(new int[]{x,-y});
    coors.add(new int[]{-x,y});
    coors.add(new int[]{-x,-y});
    //
    coors.add(new int[]{-y,x});
    coors.add(new int[]{-y,-x});
    coors.add(new int[]{y,x});
    coors.add(new int[]{y,-x});}
}

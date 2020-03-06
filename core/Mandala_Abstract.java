package org.fleen.rModel.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class Mandala_Abstract{
  
  /*
   * ################################
   * CONSTRUCTORS
   * ################################
   */
  
  public Mandala_Abstract(RModel rmodel,int centerx,int centery,int radius){
    this.rmodel=rmodel;
    this.centerx=centerx;
    this.centery=centery;
    this.radius=radius;
    birthday=rmodel.age;}
  
  public Mandala_Abstract(RModel rmodel,int radius){
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
  
  public int centerx,centery,radius;
  
  List<Cell> 
    edgecells=null,//the cells at the edge of this mandala circle
    skincells=null;//the cells just beyond the edge. we test them for collisions and such
  
  public List<Cell> getEdgeCells(){
   if(edgecells==null)initEdgeCells();
   return edgecells;}
  
  public void initEdgeCells(){
    int[][] edgecellcoors=getCircleEdgeCellCoors(radius);
    edgecells=new ArrayList<Cell>(edgecellcoors.length);
    for(int[] a:edgecellcoors)
      edgecells.add(new Cell(this,a[0]+centerx,a[1]+centery));}
  
  /*
   * ################################
   * STATE
   * ################################
   */
  
  public int birthday;
  
  public int getAge(){
    return rmodel.age=birthday;}
  
  public abstract void incrementState();
  
  public abstract boolean destroyMe();
  
  /*
   * ################################
   * STATIC GEOMETRY
   * ################################
   */
  
  /*
   * lazy init bresenhams circles
   */
  
  private static Map<Integer,int[][]> edgecellsbyradius=new HashMap<Integer,int[][]>();
  
  public static final int[][] getCircleEdgeCellCoors(int radius){
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
  public static final int[][] initCircleEdgeCellCoors(int radius){
    List<int[]> coors=new ArrayList<int[]>();
    int y=radius;
    int x=0;
    int delta=calculateStartDelta(radius);
    while(y>=x){
      getCoorAndReflect(x,y,coors);
      if(delta<0) {
        delta = calculateDeltaForHorizontalPixel(delta, x);
      }else{
        delta = calculateDeltaForDiagonalPixel(delta, x, y);
        y--;}
      x++;}
    int[][] coorsa=coors.toArray(new int[coors.size()][2]);
    return coorsa;}

  private static int calculateStartDelta(int radius){
    return 3-2*radius;}

  private static int calculateDeltaForHorizontalPixel(int oldDelta,int x){
    return oldDelta+4*x+6;}

  private static int calculateDeltaForDiagonalPixel(int oldDelta,int x,int y) {
    return oldDelta+4*(x-y)+10;}

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

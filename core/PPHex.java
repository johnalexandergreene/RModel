package org.fleen.rModel.core;

import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

import org.fleen.geom_2D.DPoint;
import org.fleen.geom_2D.GD;

/*
 * A hexagon in an array of hexagons. Arranged flat-up.
 * coordinates are A and B
 * A+ is up and to the right
 * B+ is up
 * default radius is 1.0 (center to point)
 */
public class PPHex{
  
  /*
   * ################################
   * CONSTRUCTORS
   * ################################
   */
  
  PPHex(int a,int b){
    coora=a;
    coorb=b;}
  
  public PPHex(){
    this(0,0);}
  
  /*
   * ################################
   * GEOMETRY
   * ################################
   */
  
  //directions of the 6 points relative to center
  public static final double[] POINTDIRS={
      GD.PI2*(1.0/12.0),//NE
      GD.PI2*(3.0/12.0),//E
      GD.PI2*(5.0/12.0),//SE
      GD.PI2*(7.0/12.0),//SW
      GD.PI2*(9.0/12.0),//W
      GD.PI2*(11.0/12.0)};//NW
  
  public static final double 
    COORAPLUSDIR=GD.PI2*(2.0/12.0),
    COORBPLUSDIR=0.0;
  
  public static final double 
    RADIUS=1.0,//
    HEIGHT=(GD.SQRT3/2.0)*2.0;//height of an equliateral triangle
  
  public static final DPoint ORIGIN=new DPoint(0,0);
  
  /*
   * HEX COORS
   * hexagons are arranged flat-up.
   * A+ is up and to the right
   * B+ is up
   */
  
  //the (a,b) coordinates of this hexagon
  public int coora,coorb;
  
  public void setCoor(int a,int b){
    coora=a;
    coorb=b;
    center=null;}
  
  /*
   * CENTER
   */
  
  DPoint center=null;
  
  DPoint getCenter(){
    if(center==null)doCenter();
    return center;}
  
  private void doCenter(){
    DPoint a=ORIGIN.getPoint(COORAPLUSDIR,HEIGHT*coora);
    center=a.getPoint(COORBPLUSDIR,HEIGHT*coorb);}
  
  /*
   * The 6 corner points
   * Listed NE,E,SE,SE,W,NW
   * Default radius is 1.0
   * Negative radius is inward
   */

  public List<DPoint> getPoints(double radius){
    DPoint c=getCenter(),corner;
    List<DPoint> points=new ArrayList<DPoint>(6);
    for(int i=0;i<6;i++){
      corner=c.getPoint(POINTDIRS[i],radius);
      points.add(corner);}
    return points;}
  
  public List<DPoint> getPoints(){
    return getPoints(RADIUS);}
  
  /*
   * ################################
   * NEIGHBORS
   * N,NE,SE,S,SW,NW
   * ################################
   */
  
  public List<PPHex> getNeighbors(){
    List<PPHex> n=new ArrayList<PPHex>();
    n.add(new PPHex(coora,coorb+1));
    n.add(new PPHex(coora+1,coorb));
    n.add(new PPHex(coora+1,coorb-1));
    n.add(new PPHex(coora,coorb-1));
    n.add(new PPHex(coora-1,coorb));
    n.add(new PPHex(coora-1,coorb+1));
    return n;}
  
  
  /*
   * ################################
   * TYPE NAME
   * This matter in the subclasses
   * It's either this or use classes and I prefer this
   * ################################
   */
  
  private String typename="default";
  
  public void setTypeName(String n){
    typename=n;}
  
  public String getTypeName(){
    return typename;}
  
  /*
   * ################################
   * JAVA2D
   * ################################
   */
  
  public Path2D.Double getPath2D(double radius){
    List<DPoint> points=getPoints(radius);
    Path2D.Double path=new Path2D.Double();
    DPoint p=points.get(0); 
    path.moveTo(p.x,p.y);
    for(int i=1;i<6;i++){
      p=points.get(i);
      path.lineTo(p.x,p.y);}
    path.closePath();
    return path;}
  
  public Path2D.Double getPath2D(){
    return getPath2D(RADIUS);}
  
  public Point2D.Double getCenterPoint2D(){
    DPoint c=getCenter();
    Point2D.Double d=new Point2D.Double(c.x,c.y);
    return d;}
  
  /*
   * ################################
   * OBJECT
   * ################################
   */
  
  public int hashCode(){
    return coora*53+coorb*59;}
  
  public boolean equals(Object a){
    PPHex b=(PPHex)a;
    boolean e=(coora==b.coora)&&(coorb==b.coorb);
    return e;}
  
  public String toString(){
    String a="["+coora+","+coorb+"]";
    return a;}

}

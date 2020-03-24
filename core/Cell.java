package org.fleen.rModel.core;

import java.util.ArrayList;
import java.util.List;

import org.fleen.geom_2D.DPoint;

public class Cell{
  
  /*
   * ################################
   * GEOMETRY
   * ################################
   */
  
  public Cell(int x,int y){
    this.x=x;
    this.y=y;
    center=new DPoint(x,y);}
  
  public DPoint getCenter(){
    return new DPoint(x+0.5,y+0.5);}
  
  double distancetoorigin=-1;
  
  public double getDistanceToOrigin(){
    if(distancetoorigin==-1)
      initDistanceToOrigin();
    return distancetoorigin;}
  
  private void initDistanceToOrigin(){
    distancetoorigin=getCenter().getDistance(0,0);}
  
  public double getDistanceToPoint(DPoint p){
    DPoint a=getCenter();
    return p.getDistance(a);
  }
  
  /*
   * ################################
   * GEOMETRY
   * ################################
   */
  
  public int x,y;
  public DPoint center;
  
  public List<Cell> getAdjacents(){
    List<Cell> a=new ArrayList<Cell>(8);
    a.add(new Cell(x-1,y-1));//NW
    a.add(new Cell(x,y-1));//N
    a.add(new Cell(x+1,y-1));//NE
    a.add(new Cell(x+1,y));//E
    a.add(new Cell(x+1,y+1));//SE
    a.add(new Cell(x,y+1));//S
    a.add(new Cell(x-1,y+1));//SW
    a.add(new Cell(x-1,y));//W
    return a;}
  
  /*
   * ################################
   * OBJECT
   * ################################
   */
  
  public int hashCode(){
    return x*43+y*47;}
  
  public boolean equals(Object a){
    Cell b=(Cell)a;
    return x==b.x&&y==b.y;}
  
  public String toString(){
    return "["+x+","+y+"]";}
  
  

}

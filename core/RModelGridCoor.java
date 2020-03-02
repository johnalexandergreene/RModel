package org.fleen.rModel.core;

import org.fleen.geom_2D.DPoint;

public class RModelGridCoor{
  
  public RModelGridCoor(RModel rmodel,int gx,int gy){
    this.rmodel=rmodel;
    this.gx=gx;
    this.gy=gy;}
  
  RModel rmodel;
  //coors in terms of grid
  int gx,gy;
  
  public double getRealX(){
    return rmodel.getGridInterval()*gx;}
  
  public double getRealY(){
    return rmodel.getGridInterval()*gy;}
  
  public DPoint getDPoint(){
    return new DPoint(getRealX(),getRealY());}

}

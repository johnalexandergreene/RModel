package org.fleen.rModel.test_kgrid_based_circle_arrangement;

import java.util.Random;

import org.fleen.geom_2D.DPoint;

public class Phenomenon{
  
  public Phenomenon(RModel rmodel){
    this.rmodel=rmodel;}
  
  
  RModel rmodel;
  
  boolean dead=false;
  
  public void advance(){
//    if(rmodel.age>(birthday+maxage))
//      dead=true;
    }
  
  /*
   * we'll need lifespan
   * entryspeed,exitspeed
   * type...
   */
  public double radius;
  public int birthday,maxage;
  public DPoint center;
  public KGHSHexagon hexagon;

}

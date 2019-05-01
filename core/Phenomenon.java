package org.fleen.rModel.core;

import java.util.Random;

public class Phenomenon{
  
  public Phenomenon(RModel rmodel){
    this.rmodel=rmodel;
    //TEST
    Random r=new Random();
    x=r.nextDouble()*rmodel.width;
    y=r.nextDouble()*rmodel.height;
    radius=r.nextDouble()*(rmodel.width/4)+16;
    birthday=rmodel.age;
    maxage=r.nextInt(30)+r.nextInt(30)+r.nextInt(30)+3;
    
    
  }
  
  RModel rmodel;
  
  boolean dead=false;
  
  public void advance(){
    if(rmodel.age>(birthday+maxage))
      dead=true;}
  
  /*
   * we'll need lifespan
   * entryspeed,exitspeed
   * type...
   */
  public double x,y,radius;
  public int birthday,maxage;

}

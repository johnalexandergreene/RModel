package org.fleen.rModel.test_freerange_2d_circles;

import org.fleen.geom_2D.DPoint;
import org.fleen.geom_2D.DVector;
import org.fleen.geom_2D.GD;

public class PCircle{
  
  public PCircle(RModel rmodel,DPoint center,double radius,double attack,double decay,int lifespan){
    this.rmodel=rmodel;
    this.center=new DPoint(center);
    this.maxradius=radius;
    this.attack=attack;
    this.decay=decay;
    this.lifespan=lifespan;
    birthday=rmodel.age;
  }
  
  RModel rmodel;
  double attack,decay;
  
  public boolean killMe(){
    return getAge()>lifespan;}
  
  public void advance(){
    
  }
  
  private double maxradius;
  public int birthday,lifespan;
  public DPoint center;
  
  public double getRadius(){
    double a=getAge();
    double r=attack*a;
    if(r>maxradius)r=maxradius;
    double shrinkpoint=lifespan-(maxradius/decay);
    if(a>shrinkpoint)
      r=maxradius-((a-shrinkpoint)*decay);
    return r;}
  
  public int getAge(){
    return rmodel.age-birthday;}
  
  /*
   * ################################
   * VECTOR
   * ################################
   */
  
  DVector vector=new DVector(GD.PI*0.75,0.1);
  static final double GRAVITYVECTORMAG=5.5;
  static final double VECTORRETARD=0.05;
  
  public DVector getVector(){
    vector.add(getGravityVector());
    DVector c=getCollisionVector();
    if(c!=null)vector=c;
    vector.magnitude*=VECTORRETARD;
    return vector;}
  
  static final double COLLISIONMAGFACTOR=0.01;
  
  private DVector getCollisionVector(){
    Collision c=rmodel.getCollision(this);
    if(c==null)return null;
    PCircle other;
    if(c.c0==this)
      other=c.c1;
    else
      other=c.c0;
    double dir=other.center.getDirection(center);
    double mag=(other.getRadius()+getRadius())-other.center.getDistance(center)*COLLISIONMAGFACTOR;
    return new DVector(dir,mag);}
  
  
  
  private DVector getGravityVector(){
    double dir=center.getDirection(rmodel.center);
    return new DVector(dir,GRAVITYVECTORMAG);
  }
  
  public boolean contains(DPoint p){
    double a=p.getDistance(center);
    return a<getRadius();}

}

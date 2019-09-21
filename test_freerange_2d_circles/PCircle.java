package org.fleen.rModel.test_freerange_2d_circles;

import java.util.ArrayList;
import java.util.List;

import org.fleen.geom_2D.DPoint;
import org.fleen.geom_2D.DVector;
import org.fleen.geom_2D.GD;

public class PCircle{
  
  public PCircle(RModel rmodel,DPoint center,double radius,double attack,double decay,int lifespan){
    this.rmodel=rmodel;
    centers.add(new DPoint(center));
    this.maxradius=radius;
    this.attack=attack;
    this.decay=decay;
    this.lifespan=lifespan+(int)(maxradius/attack)+(int)(maxradius/decay);
    birthday=rmodel.age;
  }
  
  RModel rmodel;
  double attack,decay;
  
  public boolean killMe(){
    return getAge()>lifespan;}
  
  /*
   * this will advance the graphical representation 1 frame
   */
  public void advance(){
    
  }
  
  private double maxradius;
  public int birthday,lifespan;
//  public DPoint center;
  
  /*
   * ################################
   * CENTER
   * we use the average of a list of centers
   * a new center added at every iteration of the system
   * this acts as a shock-absorber, so the circles don't vibrate so much
   * ################################
   */
  
  static final int CENTERLISTMAXSIZE=22;
  List<DPoint> centers=new ArrayList<DPoint>(CENTERLISTMAXSIZE+1);
  
  public void addCenter(DPoint c){
    centers.add(c);
    if(centers.size()>CENTERLISTMAXSIZE)
      centers.remove(0);}
  
  public DPoint getCenter(){
    DPoint g=GD.getPoint_Mean(centers);
    return g;}
  
  /*
   * ################################
   * RADIUS
   * Dependent on age, attack and decay
   * ################################
   */
  
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
  
  DVector vector=new DVector(0,0);
  static final double 
    GRAVITYVECTORMAG=3.1,
    VECTORRETARD=0.27,//0.05,
    COLLISIONMAGFACTOR=0.005;
  
  public DVector getVector(){
    vector.add(getGravityVector());
    DVector c=getSummedCollisionVectors();
    if(c!=null)vector.add(c);
    vector.magnitude*=VECTORRETARD;
    return vector;}
  
  private DVector getSummedCollisionVectors(){
    //are there a collisions?
    List<Collision> collisions=rmodel.getCollisions(this);
    if(collisions.isEmpty())return null;
    //sum those collision vectors
    DVector s=new DVector(0,0);
    for(Collision c:collisions)
      s.add(getCollisionVector(c));
    return s;}
  
  private DVector getCollisionVector(Collision collision){
    //specify which circle got collided with
    PCircle other;
    if(collision.c0==this)
      other=collision.c1;
    else
      other=collision.c0;
    //get dir and mag
    double dir=other.getCenter().getDirection(getCenter());
    double mag=(other.getRadius()+getRadius())-other.getCenter().getDistance(getCenter())*COLLISIONMAGFACTOR;
    //scale mag by relative size
    //in a collision between small and large circles, the small gets pushed harder than the large
    double relativesizemagfactor=other.getRadius()/getRadius();
    relativesizemagfactor=Math.sqrt(relativesizemagfactor);//flatten the curve a bit
    if(relativesizemagfactor>2)relativesizemagfactor=2;
    if(relativesizemagfactor<0.5)relativesizemagfactor=0.5;
    mag*=relativesizemagfactor;
    //
    DVector v=new DVector(dir,mag);
    return v;}
  
  private DVector getGravityVector(){
    double dir=getCenter().getDirection(rmodel.center);
    return new DVector(dir,GRAVITYVECTORMAG);}
  
  public boolean contains(DPoint p){
    double a=p.getDistance(getCenter());
    return a<getRadius();}

}

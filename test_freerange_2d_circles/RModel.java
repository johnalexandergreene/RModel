package org.fleen.rModel.test_freerange_2d_circles;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.fleen.geom_2D.DPoint;
import org.fleen.geom_2D.DVector;

public class RModel{
  
  static final int TESTPCOUNT=2;
  
  /*
   * ################################
   * CONSTRUCTOR
   * ################################
   */
  
  public RModel(double width,double height){
    this.width=width;
    this.height=height;
    center=new DPoint(width/2,height/2);
    
  //TEST
    
//    circles.add(new PCircle(this,120,100,33));
//    circles.add(new PCircle(this,220,100,55));
//    circles.add(new PCircle(this,120,300,11));
//    circles.add(new PCircle(this,220,300,22));
    
    }
  
  /*
   * ################################
   * FIELD
   * ################################
   */
  
  public double width,height;
  DPoint center;
  
  
  /*
   * ################################
   * CIRCLES
   * ################################
   */
  
  public int age=0; //the number of times that advanceState has been invoked
  public List<PCircle> circles=new ArrayList<PCircle>();
  
  public void clear(){
    circles.clear();
    age=0;}
  
  public void advanceState(){
    removeDeadCircles();
    advanceCircles();
    advancePhysics();
    conditionallyCreateCircles();
    age++;
    notifyObservers();}
  
  /*
   * a phenomenon changed it's state. Growing or shrinking or maintaining or throbbing or something
   * offering data for a circle, it's location and radius. Its color, sound, throbbing frequency...
   */
  void advanceCircles(){
    for(PCircle p:circles)
      p.advance();}
  
  void removeDeadCircles(){
    List<PCircle> toremove=new ArrayList<PCircle>();
    for(PCircle p:circles)
      if(p.killMe())
        toremove.add(p);
    circles.removeAll(toremove);
    System.out.println("removed "+toremove.size());}
  
  static final double BASECIRCLECREATIONPROBABILITY=0.01;
  
  /*
   * create 0..n new phenomenon objects
   * place them, initialize them
   * 
   * what we do depends on anything we like
   *   the present population of the model
   *   the population of certain phenomenon types
   *   the age of the model (in terms of the number of times that advance has been invoked)
   *   ... etc
   * 
   */
  void conditionallyCreateCircles(){
    if(circles.isEmpty()){
      createFirstCircle();
    }else{
      if(rnd.nextDouble()<BASECIRCLECREATIONPROBABILITY){
        createCircle();
      }
    }
    
    //TEST
//    if(phenomena.size()<TESTPCOUNT)
//      phenomena.add(new Phenomenon(this));
    
  }
  
  Random rnd=new Random();
  
  static final double CIRCLEMAXRADIUS=100,CIRCLEMINRADIUS=10;
  
  void createCircle(){
    DPoint p=getRandomFreePoint();
    if(p==null)return;
    PCircle c=new PCircle(
      this,
      p,
      getRandomCircleRadius(),
      getRandomCircleAttack(),
      getRandomCircleDecay(),
      getRandomCircleLifespan());
    circles.add(c);}
  
  void createFirstCircle(){
    PCircle c=new PCircle(
      this,
      center,
      getRandomCircleRadius(),
      getRandomCircleAttack(),
      getRandomCircleDecay(),
      getRandomCircleLifespan());
    circles.add(c);}
  
  static final int CIRCLEMAXLIFESPAN=200,CIRCLEMINLIFESPAN=100;
  
  int getRandomCircleLifespan(){
    int a=rnd.nextInt(CIRCLEMAXLIFESPAN-CIRCLEMINLIFESPAN)+CIRCLEMINLIFESPAN;
    return a;}
  
  static final double CIRCLEATTACKMAX=1,CIRCLEATTACKMIN=0.1;
  
  double getRandomCircleAttack(){
    return rnd.nextDouble()*(CIRCLEATTACKMAX-CIRCLEATTACKMIN)+CIRCLEATTACKMIN;}
  
  static final double CIRCLEDECAYMAX=1,CIRCLEDECAYMIN=0.1;
  
  double getRandomCircleDecay(){
    return rnd.nextDouble()*(CIRCLEDECAYMAX-CIRCLEDECAYMIN)+CIRCLEDECAYMIN;}
  
  double getRandomCircleRadius(){
    return rnd.nextDouble()*(CIRCLEMAXRADIUS-CIRCLEMINRADIUS)+CIRCLEMINRADIUS;}
  
  static final int MAXFREEPOINTTESTS=100;
  
  DPoint getRandomFreePoint(){
    boolean isfree=false;
    int testcount=0;
    DPoint p=null;
    while(!isfree&&testcount<MAXFREEPOINTTESTS){
      p=new DPoint(rnd.nextDouble()*width,rnd.nextDouble()*height);
      isfree=true;
      SEEK:for(PCircle c:circles){
        if(c.contains(p)){
          p=null;
          isfree=false;
          break SEEK;}}
      testcount++;}
    return p;
  }
  
  /*
   * ################################
   * PHYSICS
   * ################################
   */
  
  static final double MAXVECTORMAGNITUDE=0.1;
  
  /*
   * the phenomenon-representation-circles (PRC) grow and shrink and press on each other
   * account for the physics of that
   * the PRCs gravitate to the center of the clump, thus grouping and packing themselves. 
   * Account for the physics of that too 
   */
//  void advancePhysics(){
//    //get raw vectors
//    List<DVector> gravity=getGravityVectors();
//    List<DVector> bump=getBumpVectors();
//    //sum, normalize and apply
//    PCircle p;
//    DVector sum;
//    for(int i=0;i<circles.size();i++){
//      p=circles.get(i);
//      sum=new DVector(gravity.get(i),bump.get(i));
//      if(sum.magnitude>MAXVECTORMAGNITUDE)
//        sum.magnitude=MAXVECTORMAGNITUDE;
//      p.center.applyVector(sum);}}
  
  void advancePhysics(){
    doCollisions();
    List<DVector> v=new ArrayList<DVector>();
    for(PCircle p:circles)
      v.add(p.getVector());
    PCircle p;
    for(int i=0;i<circles.size();i++){
      p=circles.get(i);
      p.center.applyVector(v.get(i));}}
  
  Set<Collision> collisions;
  
  
  private void doCollisions(){
    collisions=new HashSet<Collision>();
    double d;
    for(PCircle c0:circles){
      for(PCircle c1:circles){
        if(c0!=c1){
          d=(c0.getRadius()+c1.getRadius())-c0.center.getDistance(c1.center);
          if(d>0)
            collisions.add(new Collision(c0,c1));}}}}
  
  public Collision getCollision(PCircle c){
    for(Collision a:collisions)
      if(a.c0==c||a.c1==c)
        return a;
    return null;}
  
  /*
   * ################################
   * OBSERVERS
   * ################################
   */
  
  public List<RModelObserver> observers=new ArrayList<RModelObserver>();
  
  void notifyObservers(){
    for(RModelObserver a:observers)
      a.advanced();}
  
  /*
   * ################################
   * OBJECT
   * ################################
   */
  
  public String toString(){
    StringBuffer a=new StringBuffer("RMODEL");
    a.append("\n pcount : "+circles.size());
    a.append("\n age : "+age);
    return a.toString();}
  
}

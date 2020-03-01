package org.fleen.rModel.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import org.fleen.geom_2D.DPoint;
import org.fleen.geom_2D.GD;
import org.fleen.rModel.test000.RModelObserver;

/*
 * reality model
 * a bunch of disks representing perceptual phenomena
 * taken together, as a pattern of disks, represents a whole river perceptual phenomena. Ie reality.
 * 
 * Each disk represents a particular perceptual pehnomenon.
 * For example, sonsider these symbolis representations
 *   Green circle = a sound, like the quack of a duck, or the roar of a car engine
 *   A pink circle = a sight. A vicual pehnomenon. Something seen. Like the sight of a kittycat, or seeing the moon, 
 *     or seeing a character on the screen.  
 *   A blue rectangle = a thought. A thought about a hamburger, or about money, or about that person over there
 * 
 * So when you see a green circle appear on the screen, that's the roar of a car going by.
 * 
 * Disks will come and go. Arriving when the phenomenon begins, leaving when the phenomenon is over.
 * A disk grows from a point and fades into visiblity when it enters the stage
 * The disk shrinks to a point and fades from visibility when it leaves
 * 
 * Disks will be sized according to their intensity.
 * So the sight of the noon sun might be a huge pink circle
 * And the sight of a man in a window a block away in a a small pink circle
 * And the blat of an air-horn is a big green circle 
 * And an overwhelming thought about sex is a huge blue rectangle  
 * 
 * Disks will be rendered with nicely scribbly edges and a bit of a throb or pulse, the lend sharacter and appeal.
 * 
 * So anyway, our RModel here contains a number of these PDisks
 * 
 * When a PDisk arrives
 *   1. find a nice location for it
 *   2. Fade it in one frame at a time, keep it for a while, then fade it out
 *   
 * Find a nice location. 
 *   Map the local space with a square grid. To the limits of the bounds of the disks, + a margin.
 *   Thus map the edges of the disks. It could be coarse, no matter. 
 *   Then get random samples until we have a number of prospective locations for the new disk. 
 *   Then pick one at random.
 *   
 * We will have a cell map. An array of squares
 * Use it for mapping the space, for finding spaces to put new disks
 * 
 * A PDisk has center and radius in terms of RModel span
 * 
 * Rectangles could fit against other rectangles like bricks or tetris. To illustrate the the idea of ideas fitting together int a mass of models.
 * 
 * The view center is arbitrary. Init somewhere and then move as focus moves.
 * The cell map dimensions are constant.
 * The PDisk dimensions are in terms of the cell map dimensions. IE a circle with diameter=1 will span the map
 * The visual field fits the map
 * We will have a scale. Zooming in as focus increases 
 * 
 * To lend a bit of graphical unity we will orthogonalize the rectangles and mod the circle centers, and all the centers
 * 
 * All disks are circles. Because it would increase the complexity muchly otherwise
 * 
 */
public class RModel{
  
  /*
   * ################################
   * CONSTRUCTOR
   * ################################
   */
  
  public RModel(){
    
    PDisk a=new PDisk_Test(new DPoint(0.0,0.0),GRIDINTERVAL*5);
    pdisks.add(a);
    
  }
  
  /*
   * ################################
   * GEOMETRY
   * ################################
   */
  
  /*
   * all disks have location x and y that are integer multiple of this
   * all disks have radius that is integer multiple of this
   * Thus we get a bit of graphical unity
   */
  public static final double GRIDINTERVAL=1.0;
  
  /*
   * ################################
   * VIEW
   * ################################
   */
  
  public DPoint viewcenter=new DPoint(0,0);
  
  public double scale=123;
  
  void setViewCenter(PDisk disk){
    viewcenter=disk.getCenter();}
  
  void setViewCenter(List<PDisk> disks){
    //TODO
    //centroid or whatever
    }
  
  /*
   * ################################
   * STATE
   * ################################
   */
  
  int age=0;
  
  public void advanceState(){
    
    Random rnd=new Random();
    addDisk(new PDisk_Test(rnd.nextInt(3)*GRIDINTERVAL+GRIDINTERVAL*3),null);
    
    advanceDiskStates();
    observer.advance();
    age++;}
  
  /*
   * ################################
   * OBSERVER
   * ################################
   */
  
  public RModelObserver observer;
  
  /*
   * ################################
   * DISKS
   * Each disk represents a perceptual phenomenon
   * ################################
   */
  
  public List<PDisk> pdisks=new ArrayList<PDisk>();
  
  void advanceDiskStates(){
    for(PDisk p:pdisks)
      p.advance();}
  
  /*
   * type may be null
   * if it isn't null then we try to place it with disks of the same type
   */
  void addDisk(PDisk disk,String type){
    //TEST
    DPoint location=null;
//    if(type==null)
//      location=getLocationForNewDisk(disk);
//    else
//      location=getLocationForNewDiskWithGrouping(disk,type);
    location=getPlacementForNewDiskWithoutGrouping(disk);
    if(location!=null){
      disk.setCenter(location);
      pdisks.add(disk);}
    
  }
  
  /*
   * for each disk in the map
   *   get all possible 
   */
  DPoint getPlacementForNewDiskWithGrouping(PDisk disk,String type){
    //TODO
    return null;
  }
  
  /*
   * get map of all possible placements
   * are there any dupes? If so then get the dupes in a set and discard the others
   * from what remains get one at random. 
   * 
   * prefer placements closer to 0,0
   */
  DPoint getPlacementForNewDiskWithoutGrouping(PDisk disk){
    //TODO
    //this is a test
    Map<PDisk,Set<DPoint>> prospects=getPlacementProspectsForNewDiskWithoutGrouping(disk);
    if(prospects.isEmpty())return null;
    List<DPoint> a=new ArrayList<DPoint>();
    for(Set<DPoint> z:prospects.values())
      a.addAll(z);
    Random r=new Random();
    return a.get(r.nextInt(a.size()));
    
    
    
  }
  
  
  /*
   * group with the disks that share the specified type
   */
  Map<PDisk,Set<DPoint>> getPlacementProspectsForNewDiskWithGrouping(PDisk newdisk,String type){
    Map<PDisk,Set<DPoint>> prospects;
    List<PDisk> possiblegroupings=new ArrayList<PDisk>();
    for(PDisk p:pdisks)
      if(p.getType().equals(type))
        possiblegroupings.add(p);
    if(possiblegroupings.isEmpty())
      prospects=getPlacementProspectsForNewDiskWithoutGrouping(newdisk);
    else
      prospects=getPlacementProspectsForNewDisk(newdisk,possiblegroupings);
    if(prospects.isEmpty())
      prospects=getPlacementProspectsForNewDiskWithoutGrouping(newdisk);
    return prospects;}
  
  Map<PDisk,Set<DPoint>> getPlacementProspectsForNewDiskWithoutGrouping(PDisk newdisk){
    List<PDisk> possiblegroupings=new ArrayList<PDisk>(pdisks);
    Map<PDisk,Set<DPoint>> prospects=getPlacementProspectsForNewDisk(newdisk,possiblegroupings);
    return prospects;}
  
  /*
   * Given newdisk.
   * Get all possible places to put it on the the map. 
   * Consider all disks presently in the map : olddisks .
   * Grid points on the circumferences of olddisks are our prospective placements.
   * Cull placements that are too close or collide with any olddisks.
   */
  Map<PDisk,Set<DPoint>> getPlacementProspectsForNewDisk(PDisk newdisk,List<PDisk> possiblegroupings){
    Map<PDisk,Set<DPoint>> rawplacementprospects=new HashMap<PDisk,Set<DPoint>>();
    Set<DPoint> a;
    for(PDisk olddisk:possiblegroupings){
      a=new HashSet<DPoint>();
      a.addAll(
        getGridPointsOnCircle(
          olddisk.getCenter(),olddisk.getRadius()+newdisk.getRadius()+GRIDINTERVAL));
      cullCollisions(olddisk,a);
      if(!a.isEmpty())
        rawplacementprospects.put(olddisk,a);}
    return rawplacementprospects;}
  
  /*
   * Given a disk : newdisk
   * Given a set of possible points to place it : prospects
   * for newdisk centered at each prospect
   *   check distance from each disk in the map : olddisk
   *     if newdisk is too close to olddisk then this point is a collision, remove it
   */
  void cullCollisions(PDisk newdisk,Set<DPoint> prospects){
    List<DPoint> toremove=new ArrayList<DPoint>();
    for(DPoint prospect:prospects){
      TEST:for(PDisk olddisk:pdisks){
        if(isCollision(newdisk,olddisk,prospect)){
          toremove.add(prospect);
          break TEST;}}}
    prospects.removeAll(toremove);}
  
  boolean isCollision(PDisk newdisk,PDisk olddisk,DPoint prospect){
    double d=prospect.getDistance(olddisk.getCenter());
    double min=olddisk.getRadius()+newdisk.getRadius()+GRIDINTERVAL;
    if(d<min)
      return true;
    return false;}
  
  /*
   * Given a circle
   * Get all points on circumference, at GRIDINTERVAL spacing (a rough spacing, yes, but probably thorough enough)
   * then get a grid point near each of those circumference points
   * and put them all in a set to cull dupes
   */
  Set<DPoint> getGridPointsOnCircle(DPoint center,double r){
    Set<DPoint> locations=new HashSet<DPoint>(); 
    double circumference=GD.PI*r*2;
    int locationscount=(int)(circumference/GRIDINTERVAL)+1;
    double angularinterval=GD.PI2/locationscount;
    double d;
    double[] rawpoint;
    for(int i=0;i<locationscount;i++){
      d=angularinterval*i;
      rawpoint=GD.getPoint_PointDirectionInterval(center.x,center.y,d,r);
      locations.add(getFurthestGridPoint(rawpoint,center));}
    return locations;}
  
  /*
   * Of the 4 grid points adjacent to raw, get the furthest from center
   * yes, it's crude
   */
  DPoint getFurthestGridPoint(double[] raw,DPoint center){
    double[][] gp=get4GridPoints(raw);
    double 
      d0=center.getDistance(gp[0][0],gp[0][1]),
      d1=center.getDistance(gp[1][0],gp[1][1]),
      d2=center.getDistance(gp[2][0],gp[2][1]),
      d3=center.getDistance(gp[3][0],gp[3][1]);
    if(d0>d1&&d0>d2&&d0>d3)
      return new DPoint(gp[0]);
    else if(d1>d0&&d1>d2&&d1>d3)
      return new DPoint(gp[1]);
    else if(d2>d0&&d2>d1&&d2>d3)
      return new DPoint(gp[2]);
    else
      return new DPoint(gp[3]);}
  
  /*
   * given point p
   * get the 4 grid points closest to raw g0,g1,g2,g3 using mod math
   */
  static double[][] get4GridPoints(double[] p){
    double px=p[0],py=p[1],gx0,gx1,gy0,gy1;
    if(px>0){
      gx0=px-(px%GRIDINTERVAL);
      gx1=gx0+GRIDINTERVAL;
    }else{
      gx0=px-(px%GRIDINTERVAL);
      gx1=gx0-GRIDINTERVAL;}
    if(py>0){
      gy0=py-(py%GRIDINTERVAL);
      gy1=gy0+GRIDINTERVAL;
    }else{
      gy0=py-(py%GRIDINTERVAL);
      gy1=gy0-GRIDINTERVAL;}
    double[][] gridpoints={
      {gx0,gy0},
      {gx0,gy1},
      {gx1,gy1},
      {gx1,gy0}};
    return gridpoints;}
  
//  public static final void main(String[] a){
//    double[] p={-0.2,0.2}; 
//    double[][] gp=get4GridPoints(p,0.3);
//    System.out.println("point=("+p[0]+","+p[1]+")");
//    System.out.println("gridpoints");
//    System.out.println("gp0("+gp[0][0]+","+gp[0][1]+")");
//    System.out.println("gp1("+gp[1][0]+","+gp[1][1]+")");
//    System.out.println("gp2("+gp[2][0]+","+gp[2][1]+")");
//    System.out.println("gp3("+gp[3][0]+","+gp[3][1]+")");
//    
//  }
  
  
  /*
   * ################################
   * OBJECT
   * ################################
   */
  
  public String toString(){
    StringBuffer a=new StringBuffer("RMODEL");
    a.append("\n pcount : "+pdisks.size());
    a.append("\n age : "+age);
    return a.toString();}

}

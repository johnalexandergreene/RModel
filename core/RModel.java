package org.fleen.rModel.core;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.fleen.geom_2D.DPoint;
import org.fleen.geom_2D.GD;
import org.fleen.rModel.test_freerange_2d_circles.RModelObserver;

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
    
    //TEST
    //init with a single disk
    PDisk a=new PDisk_Test(new DPoint(0.2,0.2),0.2);
    pdisks.add(a);
    a=new PDisk_Test(new DPoint(0.3,0.3),0.1);
    pdisks.add(a);
    //focused upon
    viewcenter=a.getCenter();
    //scaled to fit the new pdisk nicely
    scale=0.7;//graphically the viewport span is 1.0 . So a circle with diameter 0.7 is a bit smaller than the viewport. 
    
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
  public static final double GRIDINTERVAL=0.05;
  
  /*
   * ################################
   * SHAPES
   * ################################
   */
  
  static double LOCATIONINCREMENT=0.05;
  
  public List<PDisk> pdisks=new ArrayList<PDisk>();
  
  void advanceDiskStates(){
    for(PDisk p:pdisks)
      p.advance();}
  
  /*
   * type may be null
   * if it isn't null then we try to place it with disks of the same type
   */
  void addDisk(PDisk disk,String type){
    DPoint location=null;
    if(type==null)
      location=getLocationForNewDisk(disk);
    else
      location=getLocationForNewDiskWithGrouping(disk,type);
    
    //get location for the disk
    //set location
    //add it to the list
    
  }
  
  DPoint getLocationForNewDisk(PDisk disk){
    
  }
  
  DPoint getLocationForNewDiskWithGrouping(PDisk disk,String type){
    
  }
  
  List<DPoint> getRawLocationProspectsForUnmappedDiskWithGrouping(PDisk unmappeddisk,String type){
    
  }
  
  /*
   * For every pdisk on the map : disk0
   *   get a set of possible adjacent locations for newdisk : disk0prospects
   * disk0prospects locations will be at 
   *   disk0.radius+newdisk.radius+GRIDINTERVAL, rounded outward to the nearest mod(GRIDINTERVAL) x and y
   */
  Set<DPoint> getRawLocationProspectsForUnmappedDisk(PDisk unmapped){
    //first get points at mappeddisk.radius+unmappeddisk.radius+GRIDINTERVAL distance from mappeddisk.center
    //get a number of points = olddisk.circumference/GRIDINTERVAL or thereabouts , equispaced
    //then for each of those points, get the 4 GRIDINTERVAL points near that
    //of those 4, keep the one furthest from olddisk.center
    //that's the possible points for that particular mappeddisk
    //we'll put them in a set to avoid dupes
    Set<DPoint> rawlocations=new HashSet<DPoint>();
    for(PDisk mapped:pdisks){
      rawlocations.addAll(
        getLocationsOnCircle(
          mapped.getCenter(),mapped.getRadius()+unmapped.getRadius()+GRIDINTERVAL));}
    return rawlocations;}
  
  Set<DPoint> getLocationsOnCircle(DPoint center,double r){
    Set<DPoint> locations=new HashSet<DPoint>(); 
    double circumference=GD.PI*r*2;
    int locationscount=(int)(circumference/GRIDINTERVAL)+1;
    double angularinterval=GD.PI2/locationscount;
    double d;
    double[] rawpoint;
    for(int i=0;i<locationscount;i++){
      d=angularinterval*i;
      rawpoint=GD.getPoint_PointDirectionInterval(center.x,center.y,d,r);
      locations.add(getFurthestPointOnGrid(rawpoint,center));}
    return locations;}
  
  /*
   * given point raw and center
   * get the 8 grid points closest to raw : p0,p1... p7
   * get the grid point furthest from p1
   */
  
  DPoint getFurthestPointOnGrid(double[] raw,DPoint center){
    double a=raw[1]%GRIDINTERVAL;
    double[] p0={raw[0],raw[1]+a};
    
    
  }
  
  
  
  /*
   * ################################
   * VIEW
   * ################################
   */
  
  public DPoint viewcenter;
  public double scale;
  
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
   * OBJECT
   * ################################
   */
  
  public String toString(){
    StringBuffer a=new StringBuffer("RMODEL");
    a.append("\n pcount : "+pdisks.size());
    a.append("\n age : "+age);
    return a.toString();}

}

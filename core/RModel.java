package org.fleen.rModel.core;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import org.fleen.geom_2D.DPoint;
import org.fleen.geom_2D.GD;

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
    
    addMandala(new Mandala_Test(this,5));
    addMandala(new Mandala_Test(this,3));
  }
  
  /*
   * ################################
   * GEOMETRY
   * ################################
   */
  
  
  
  /*
   * ################################
   * VIEW
   * ################################
   */
  
  public DPoint viewcenter=new DPoint(0,0);
  
  public double scale=10;
  
//  void setViewCenter(PDisk disk){
////    viewcenter=disk.getCenter().getDPoint();
//    }
//  
//  void setViewCenter(List<PDisk> disks){
//    //TODO
//    //centroid or whatever
//    }
  
  /*
   * ################################
   * STATE
   * ################################
   */
  
  int age=0;
  
  public void advanceState(){
    incrementMandalaStates();
    
    //TEST
    Random rnd=new Random();
    mandalas.clear();
    addMandala(new Mandala_Test(this,rnd.nextInt(6)+1));
    addMandala(new Mandala_Test(this,rnd.nextInt(6)+1));
    addMandala(new Mandala_Test(this,rnd.nextInt(6)+1));
    addMandala(new Mandala_Test(this,rnd.nextInt(6)+1));
    addMandala(new Mandala_Test(this,rnd.nextInt(6)+1));
    addMandala(new Mandala_Test(this,rnd.nextInt(6)+1));
    addMandala(new Mandala_Test(this,rnd.nextInt(6)+1));
    addMandala(new Mandala_Test(this,rnd.nextInt(6)+1));
    
    observer.incrementedState();
    age++;}
  
  void incrementMandalaStates(){
    for(Mandala_Abstract m:mandalas)
      m.incrementState();}
  
  /*
   * ################################
   * OBSERVER
   * ################################
   */
  
  public RModelObserver observer;
  
  /*
   * ################################
   * MANDALAS
   * Each represents a perceptual phenomenon
   * They are animated little things
   * They have a birth, a lifespan, a death. 
   * Thus representing the rise, persistence and fall of perceptual pehnomena.
   * 
   * Our big trick is visually intelligible dynamic packing.
   * When a new mandala is added to the system we need to put it someplace nice. 
   * It needs to fit into the arrangement nicely. We might want to group it.
   * 
   * ################################
   */
  
  public List<Mandala_Abstract> mandalas=new ArrayList<Mandala_Abstract>();
  
  public void addMandala(Mandala_Abstract m){
    if(mandalas.isEmpty()){
      m.setCenter(0,0);
    }else{
      int[] c=getCoorsForNewMandala(m);
      m.setCenter(c[0],c[1]);}
    //
    mandalas.add(m);}
  
  /*
   * (TODO : also do a method to position adjacent to a subset. A group, by tag)
   * 
   * given our new mandala : mnew
   * given all of the extant mandalas : mext
   * get all of mext edge cells : mextedge
   * get all of the new mandala's skin cells : mnewskin
   * for each cell in mextedge : ce
   *   for each cell in mnewskin : cs
   *     get the center coors for mnew where cedge and cmex collide
   *     
   * now we're got a set of possible positions for the cnew : pos0
   * from that remove all where mnew and mex collide : pos1
   * order by number of shared cells between mexskincells and medge
   * (maybe order by closeness to origin too, to keep the layout compact)
   * keep the ones with the most shared cells
   * pick one at random
   * 
   */
  int[] getCoorsForNewMandala(Mandala_Abstract mnew){
    //get edge cells for all extant mandalas
    Set<Cell> mextedge=new HashSet<Cell>();
    for(Mandala_Abstract mext:mandalas)
      mextedge.addAll(mext.getEdgeCells());
    //get all of the new mandala's skin cells
    Set<Cell> mnewskin=mnew.getSkinCells();
    //get all possible collisions between the extant mandala edge cells and the new mandala skin cells
    //the collision is defined by a set of coordinates for the new mandala. 
    //That is, if these 2 cells collide then the coordinates must be foo. Put foo in a list. 
    List<int[]> locations0=new ArrayList<int[]>();
    int dx,dy;
    for(Cell ce:mextedge){
      for(Cell cs:mnewskin){
        dx=ce.x-cs.x;
        dy=ce.y-cs.y;
        locations0.add(new int[]{dx,dy});}}
    //cull all locations that would result in the new circle intersecting an extant circle
    double r0,r1;
    int[] loc;
    Iterator<int[]> i;
    for(Mandala_Abstract m:mandalas){
      r0=m.getRadius()+mnew.getRadius()+1;//our cells are 1x1. It ensures a proper distance.
      i=locations0.iterator();
      while(i.hasNext()){
        loc=i.next();
        r1=GD.getDistance_PointPoint(loc[0],loc[1],m.getCenterX(),m.getCenterY());
        if(r1<r0)i.remove();}}
    //now we (should) have a number of viable locations. pick the best ones
    //we want our location to put the mandala where it snuggles up closely to another mandala. Ideally several will be snuggled.
    //we want our location to put the mandala as close to the origin as possible.
    //so we need a rating system. Then we sort them by rating
    Map<int[],Double> distancesbylocation=getDistances(locations0);
    Map<int[],Integer> touchcountbylocation=getTouchcounts(locations0);
    
    //TEST
    Random rnd=new Random();
    return locations0.get(rnd.nextInt(locations0.size()));
    
  }
  
  /*
   * type may be null
   * if it isn't null then we try to place it with disks of the same type
   */
//  void addDisk(PDisk disk,String type){
//    //TEST
//    RModelGridCoor location=null;
////    if(type==null)
////      location=getLocationForNewDisk(disk);
////    else
////      location=getLocationForNewDiskWithGrouping(disk,type);
//    location=getPlacementForNewDiskWithoutGrouping(disk);
//    if(location!=null){
//      disk.setCenter(location);
//      pdisks.add(disk);}
    
//  }
  
  
  
  /*
   * ################################
   * OBJECT
   * ################################
   */
  
  public String toString(){
    StringBuffer a=new StringBuffer("RMODEL");
    a.append("\n mcount : "+mandalas.size());
    a.append("\n age : "+age);
    return a.toString();}

}

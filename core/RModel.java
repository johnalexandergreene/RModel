package org.fleen.rModel.core;

import java.util.ArrayList;
import java.util.List;

import org.fleen.geom_2D.DPoint;

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
   * ################################
   */
  
  public List<Mandala_Abstract> mandalas=new ArrayList<Mandala_Abstract>();
  
  public void addMandala(Mandala_Abstract m){
    if(mandalas.isEmpty()){
      m.centerx=0;
      m.centery=0;
    }else{
      int[] c=getCoorsForNewMandala(m);
      m.centerx=c[0];
      m.centery=c[1];}
    //
    mandalas.add(m);}
  
  /*
   * (TODO : also do a method to position adjacent to a subset. A group, by tag)
   * 
   * given our new mandala : mnew
   * given all of the extant mandalas : mex
   * get all of mex skin cells : mexskincells
   * get all of new edge cells : medge
   * for each cell in mexskincells : cmex
   *   for each cell in medge : cedge
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
  int[] getCoorsForNewMandala(Mandala_Abstract m){
    return new int[]{2,3};//test
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

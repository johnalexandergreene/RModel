package org.fleen.rModel.core;

import java.util.ArrayList;
import java.util.List;

import org.fleen.geom_2D.DPoint;
import org.fleen.rModel.test_freerange_2d_circles.RModelObserver;

/*
 * reality model
 * a bunch of shapes representing perceptual phenomena
 * taken together, as a pattern of shapes, represents a whole river perceptual phenomena. Ie reality.
 * 
 * Each shape represents a particular perceptual pehnomenon.
 * For example, sonsider these symbolis representations
 *   Green circle = a sound, like the quack of a duck, or the roar of a car engine
 *   A pink circle = a sight. A vicual pehnomenon. Something seen. Like the sight of a kittycat, or seeing the moon, 
 *     or seeing a character on the screen.  
 *   A blue rectangle = a thought. A thought about a hamburger, or about money, or about that person over there
 * 
 * So when you see a green circle appear on the screen, that's the roar of a car going by.
 * 
 * Shapes will come and go. Arriving when the phenomenon begins, leaving when the phenomenon is over.
 * A shape grows from a point and fades into visiblity when it enters the stage
 * The shape shrinks to a point and fades from visibility when it leaves
 * 
 * Shapes will be sized according to their intensity.
 * So the sight of the noon sun might be a huge pink circle
 * And the sight of a man in a window a block away in a a small pink circle
 * And the blat of an air-horn is a big green circle 
 * And an overwhelming thought about sex is a huge blue rectangle  
 * 
 * Shapes will be rendered with nicely scribbly edges and a bit of a throb or pulse, the lend sharacter and appeal.
 * 
 * So anyway, our RModel here contains a number of these PShapes
 * 
 * When a PShape arrives
 *   1. find a nice location for it
 *   2. Fade it in one frame at a time, keep it for a while, then fade it out
 *   
 * Find a nice location. 
 *   Map the local space with a square grid. To the limits of the bounds of the shapes, + a margin.
 *   Thus map the edges of the shapes. It could be coarse, no matter. 
 *   Then get random samples until we have a number of prospective locations for the new shape. 
 *   Then pick one at random.
 *   
 * We will have a cell map. An array of squares
 * Use it for mapping the space, for finding spaces to put new shapes
 * 
 * A PShape has center and radius in terms of RModel span
 * 
 * Rectangles could fit against other rectangles like bricks or tetris. To illustrate the the idea of ideas fitting together int a mass of models.
 * 
 * The view center is arbitrary. Init somewhere and then move as focus moves.
 * The cell map dimensions are constant.
 * The PShape dimensions are in terms of the cell map dimensions. IE a circle with diameter=1 will span the map
 * The visual field fits the map
 * We will have a scale. Zooming in as focus increases 
 * 
 */
public class RModel{
  
  public RModel(){
    //init with a single shape
    PShape a=new PShape_TestCircle(new DPoint(0,0),0.2);
    phenomena.add(a);
    //focused upon
    focus=a.getCenter();
    //scaled to fit the new pshape nicely
    scale=0.6;//scale = span of viewport (not the rendered span, of course, which is scaled again)
    
  }
  
  public List<PShape> phenomena=new ArrayList<PShape>();
  
  public DPoint focus;
  public double scale;
  
  /*
   * ################################
   * STATE
   * ################################
   */
  
  
  int age=0;
  
  public void advanceState(){
    observer.advanced();
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
    a.append("\n pcount : "+phenomena.size());
    a.append("\n age : "+age);
    return a.toString();}

}

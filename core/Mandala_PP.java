package org.fleen.rModel.core;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/*
 * abstract class for perceptual phenomena mandalas
 * geometry, state, graphics
 */
public abstract class Mandala_PP extends Mandala_Basic{

  /*
   * ################################
   * CONSTRUCTOR
   * ################################
   */
  
  public Mandala_PP(RModel rmodel,int x,int y,int r){
    super(rmodel,x,y,r);
    birthday=rmodel.age;}

  /*
   * ################################
   * STATE
   * ################################
   */
  
  public int birthday;
  
  public int getAge(){
    return rmodel.age-birthday;}
  
  public abstract void incrementState();
  
  public abstract boolean destroyMe();
  
  /*
   * ################################
   * GRAPHICS
   * ################################
   */
  
  public abstract void render(BufferedImage i,Graphics2D g);

}

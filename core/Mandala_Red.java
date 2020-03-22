package org.fleen.rModel.core;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Path2D;
import java.awt.geom.Path2D.Double;
import java.awt.image.BufferedImage;
import java.util.Random;

import org.fleen.geom_2D.GD;

public class Mandala_Red extends Mandala_PP{

  public Mandala_Red(RModel rmodel,int x,int y,int r){
    super(rmodel,x,y,r);
    lifespan=rnd.nextInt(100)+radiusramp*2;}

  int lifespan;
  
  public void incrementState(){
    
    
  }

  Random rnd=new Random();
  int radiusramp=rnd.nextInt(30)+20;
  
  static final double STROKETHICKNESS=0.8;
  
  public boolean destroyMe(){
    if(getAge()>lifespan)return true;
    return false;}

  public void render(BufferedImage i,Graphics2D g){
    //ramp radius at birth and death
    double paddedradius=radius+0.5;
    double displayradius=paddedradius;
    int age=getAge();
    if(age<radiusramp){
      displayradius=paddedradius*((double)age)/((double)radiusramp);
    }else if(age>(lifespan-radiusramp)){
      displayradius=paddedradius*((double)(lifespan-age))/((double)radiusramp);}
    //ramp alpha too. And curve it. 
    double a=((double)displayradius)/((double)paddedradius);
    a*=a;
    int alpha=(int)(a*((double)255));
    //draw it
    Ellipse2D.Double e=new Ellipse2D.Double(
      cx-displayradius,
      cy-displayradius,
      displayradius*2,
      displayradius*2);
      g.setPaint(new Color(255,0,0,alpha));
      g.fill(e);
    if(displayradius>STROKETHICKNESS){
      e=new Ellipse2D.Double(
        cx-(displayradius-STROKETHICKNESS),
        cy-(displayradius-STROKETHICKNESS),
        (displayradius-STROKETHICKNESS)*2,
        (displayradius-STROKETHICKNESS)*2);
      g.setPaint(new Color(0,0,0,alpha));
      g.fill(e);}}

}

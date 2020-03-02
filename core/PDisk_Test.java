package org.fleen.rModel.core;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.geom.Ellipse2D;

import org.fleen.geom_2D.DPoint;

public class PDisk_Test implements PDisk{
  
  PDisk_Test(RModelGridCoor c,double r){
    center=c;
    radius=r;
  }
  
  PDisk_Test(double r){
    radius=r;
  }
  
  RModelGridCoor center;
  double radius;
  double STROKEWIDTH=2.0;
  
  Color color=Color.red;
  
  public void render(Graphics2D target,double scale){
    target.setPaint(color);
    Ellipse2D e=new Ellipse2D.Double(center.getRealX()-radius,center.getRealY()-radius,radius*2,radius*2);
    Stroke s=new BasicStroke((float)((1.0/scale)*STROKEWIDTH));
    target.setStroke(s);
    target.draw(e);}
  
  public RModelGridCoor getCenter(){
    return center;}
  
  public void advance(){
    
  }
  
  public double getRadius(){
    return radius;
  }

  public void setCenter(RModelGridCoor c){
    center=c;}

  public String getType(){
    return null;
  }
  
}

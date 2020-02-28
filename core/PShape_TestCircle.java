package org.fleen.rModel.core;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.geom.Ellipse2D;

import org.fleen.geom_2D.DPoint;

public class PShape_TestCircle implements PShape{
  
  PShape_TestCircle(DPoint c,double r){
    center=c;
    radius=r;
  }
  
  DPoint center;
  double radius;
  double STROKEWIDTH=2.0;
  
  Color color=Color.blue;
  
  public void render(Graphics2D target,double scale){
    Ellipse2D e=new Ellipse2D.Double(center.x-radius,center.y-radius,radius*2,radius*2);
    Stroke s=new BasicStroke((float)((1.0/scale)*STROKEWIDTH));
    target.setStroke(s);
    target.draw(e);}
  
  public DPoint getCenter(){
    return center;}
  
}

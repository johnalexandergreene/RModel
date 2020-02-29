package org.fleen.rModel.core;

import java.awt.Graphics2D;

import org.fleen.geom_2D.DPoint;

public interface PDisk{
  
  DPoint getCenter();
  
  double getRadius();
  
  void render(Graphics2D graphics,double scale);
  
  //increment age and graphics 
  void advance();

}

package org.fleen.rModel.core;

import java.awt.Graphics2D;

import org.fleen.geom_2D.DPoint;

public interface PShape{
  
  DPoint getCenter();
  
  void render(Graphics2D target,double scale);

}

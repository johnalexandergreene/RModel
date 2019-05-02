package org.fleen.rModel.test_kgrid_based_circle_arrangement;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.HashMap;

public class Renderer{
  
  public static final HashMap<RenderingHints.Key,Object> RENDERING_HINTS=
      new HashMap<RenderingHints.Key,Object>();
    
  static{
    RENDERING_HINTS.put(
      RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
    RENDERING_HINTS.put(
      RenderingHints.KEY_RENDERING,RenderingHints.VALUE_RENDER_QUALITY);
    RENDERING_HINTS.put(
      RenderingHints.KEY_DITHERING,RenderingHints.VALUE_DITHER_DISABLE);
    RENDERING_HINTS.put(
      RenderingHints.KEY_INTERPOLATION,RenderingHints.VALUE_INTERPOLATION_BICUBIC);
    RENDERING_HINTS.put(
      RenderingHints.KEY_ALPHA_INTERPOLATION,RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
    RENDERING_HINTS.put(
      RenderingHints.KEY_COLOR_RENDERING,RenderingHints.VALUE_COLOR_RENDER_QUALITY); 
    RENDERING_HINTS.put(
      RenderingHints.KEY_STROKE_CONTROL,RenderingHints.VALUE_STROKE_NORMALIZE);}
  
  Renderer(Test test){
    this.test=test;}
  
  Test test;
  
  BufferedImage image;
  
  static final int 
    MINIMAGESIZE=1200,
    PADDING=30;
  
  void render(){
    //get bounds of total kgrid hexagon system + padding
    Rectangle2D.Double kghsbounds=test.rmodel.kghs.getBounds();
    //scale it up to min image size if it's too small
    double mindim=Math.min(kghsbounds.width,kghsbounds.height);
    double scale=1.0;
    if(mindim<(MINIMAGESIZE-PADDING*2))
      scale=(MINIMAGESIZE-PADDING*2)/mindim;
    //get transform to scale and center
    AffineTransform t=AffineTransform.getScaleInstance(scale,-scale);//flip the y to make it cartesian
    double 
      offsetx=-kghsbounds.getMinX()+PADDING/scale,
      offsety=kghsbounds.getMinY()-PADDING/scale;
    t.concatenate(AffineTransform.getTranslateInstance(offsetx,offsety));
    //init image
    int 
      w=(int)(kghsbounds.width*scale+PADDING*2),
      h=(int)(kghsbounds.height*scale+PADDING*2);
    image=new BufferedImage(w,h,BufferedImage.TYPE_INT_ARGB);
    Graphics2D g=image.createGraphics();
    g.setRenderingHints(RENDERING_HINTS);
    g.setPaint(Color.white);
    g.fillRect(0,0,w,h);
    g.setTransform(t);
    //render hexagons 
    //TODO circles, animation frames...
    g.setPaint(Color.orange);
    g.setStroke(new BasicStroke(0.1f));
    for(Phenomenon p:test.rmodel.phenomena){
      g.draw(p.hexagon.getDefaultPolygon2D().getPath2D());}}

}

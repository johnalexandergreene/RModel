package org.fleen.rModel.test;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.util.HashMap;

import org.fleen.rModel.core.Phenomenon;

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
      
  void render(){
    int 
      w=test.rmodel.width,
      h=test.rmodel.height;
    image=new BufferedImage(w,h,BufferedImage.TYPE_INT_ARGB);
    Graphics2D g=image.createGraphics();
    g.setRenderingHints(RENDERING_HINTS);
    g.setPaint(Color.white);
    g.fillRect(0,0,w,h);
    g.setPaint(Color.orange);
    g.setStroke(new BasicStroke(5.0f));
    Ellipse2D.Double e;
    for(Phenomenon p:test.rmodel.phenomena){
      e=new Ellipse2D.Double(p.x,p.y,p.radius/2,p.radius/2);
      g.draw(e);}}

}

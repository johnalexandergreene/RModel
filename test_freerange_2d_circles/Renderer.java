package org.fleen.rModel.test_freerange_2d_circles;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.HashMap;

import org.fleen.geom_2D.DPoint;
import org.fleen.rModel.core.PShape;

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
  
  static final int DEFAULT_VIEWPORT_SPAN=600;
  
  void render(){
    render(DEFAULT_VIEWPORT_SPAN,DEFAULT_VIEWPORT_SPAN);}
      
  void render(int w,int h){
    image=new BufferedImage(w,h,BufferedImage.TYPE_INT_ARGB);
    Graphics2D g=image.createGraphics();
    g.setRenderingHints(RENDERING_HINTS);
    g.setPaint(Color.white);
    g.fillRect(0,0,w,h);
    //do transform
    double dw=w,dh=h,scale=((double)DEFAULT_VIEWPORT_SPAN)/test.rmodel.scale;
    DPoint viewcenter=test.rmodel.focus;
    AffineTransform t=new AffineTransform();
    t.scale(scale,scale);
    t.translate((dw/(2*scale))-viewcenter.x,(dh/(2*scale))-viewcenter.y);
    g.setTransform(t);
    //render all the shapes
    System.out.println("rendering "+test.rmodel.phenomena.size()+" phenomena");
    for(PShape p:test.rmodel.phenomena)
      p.render(g,scale);}
  
}

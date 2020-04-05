package org.fleen.rModel.test000;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.util.HashMap;

import org.fleen.rModel.core.Square_Minimal;
import org.fleen.rModel.core.Square_PerceptualEvent_Abstract;

public class Renderer_Test{
  
  //RENDERING HINTS
  
  public static final HashMap<RenderingHints.Key,Object> RENDERING_HINTS=
      new HashMap<RenderingHints.Key,Object>();
    
  static{
    RENDERING_HINTS.put(
      RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
    RENDERING_HINTS.put(
      RenderingHints.KEY_RENDERING,RenderingHints.VALUE_RENDER_QUALITY);
    RENDERING_HINTS.put(
      RenderingHints.KEY_DITHERING,RenderingHints.VALUE_DITHER_ENABLE);
    RENDERING_HINTS.put(
      RenderingHints.KEY_INTERPOLATION,RenderingHints.VALUE_INTERPOLATION_BICUBIC);
    RENDERING_HINTS.put(
      RenderingHints.KEY_ALPHA_INTERPOLATION,RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
    RENDERING_HINTS.put(
      RenderingHints.KEY_COLOR_RENDERING,RenderingHints.VALUE_COLOR_RENDER_QUALITY); 
    RENDERING_HINTS.put(
      RenderingHints.KEY_STROKE_CONTROL,RenderingHints.VALUE_STROKE_NORMALIZE);}
  
  //
  
  Renderer_Test(Test test){
    this.test=test;}
  
  Test test;
  
  public BufferedImage image;
  
  public static final Color BACKGROUNDCOLOR=new Color(255,255,255);
  
  static final int DEFAULT_VIEWPORT_SPAN=600;
  
  void render(){
    render(DEFAULT_VIEWPORT_SPAN,DEFAULT_VIEWPORT_SPAN);}
      
  void render(int w,int h){
    System.out.println("rendering");
    image=new BufferedImage(w,h,BufferedImage.TYPE_INT_ARGB);
    Graphics2D g=image.createGraphics();
    g.setRenderingHints(RENDERING_HINTS);
    g.setPaint(BACKGROUNDCOLOR);
    g.fillRect(0,0,w,h);
    
    //do transform
//    double dw=w,dh=h,scale=20;
//    AffineTransform t=new AffineTransform();
//    t.scale(scale,scale);
//    t.translate((dw/(2*scale)),(dh/(2*scale)));
//    g.setTransform(t);
    //
    for(Square_Minimal s:test.vignette.squares)
      if(s instanceof Square_PerceptualEvent_Abstract)
        renderSquare(g,(Square_PerceptualEvent_Abstract)s);
      
  }
  
  static final double PADDING=2;
  
  
  void renderSquare(Graphics2D g,Square_PerceptualEvent_Abstract s){
    int
      scale=30,
      offsetx=image.getWidth()/2,
      offsety=image.getHeight()/2;
    g.setPaint(s.getColor());
    int padding=(int)(scale*(1.0-s.getIntensity())*s.span/2)+2;
    g.fillRect(
      offsetx+(scale*s.x)+padding,
      offsety+(scale*s.y)+padding,
      (scale*s.span)-padding*2,
      (scale*s.span)-padding*2);
    
    
//    Kernel kernel = new Kernel(3, 3,
//        
//        new float[] {
//       
//      1f/9f, 1f/9f, 1f/9f,
//       
//      1f/9f, 1f/9f, 1f/9f,
//       
//      1f/9f, 1f/9f, 1f/9f});
//       
//        BufferedImageOp op = new ConvolveOp(kernel);
//       
//        image = op.filter(image, null);
    
    
    
  }
  
//  void renderSquare(Graphics2D g,Square_PerceptualEvent_Abstract s){
//    DPoint center=s.getCenter();
//    double intensity=s.getIntensity();
//    double scaledpadding=PADDING/g.getTransform().getScaleX();
////    double scaledpadding=PADDING;
//    double span=s.span*intensity-scaledpadding;
//    double[][] c={
//      {center.x-span/2,center.y-span/2},
//      {center.x-span/2,center.y+span/2},
//      {center.x+span/2,center.y+span/2},
//      {center.x+span/2,center.y-span/2}};
//    //
//    Path2D.Double path=new Path2D.Double();
//    path.moveTo(c[0][0],c[0][1]);
//    path.lineTo(c[1][0],c[1][1]);
//    path.lineTo(c[2][0],c[2][1]);
//    path.lineTo(c[3][0],c[3][1]);
//    path.closePath();
//    //
//    Color color=s.getColor();
//    int alpha=(int)(intensity*255);
//    color=new Color(color.getRed(),color.getGreen(),color.getBlue(),alpha);
//    g.setPaint(color);
//    g.fill(path);
//    
//    
//  }
  
    
//    //do transform
//    double dw=w,dh=h,scale=((double)DEFAULT_VIEWPORT_SPAN)/test.rmodel.scale;
//    DPoint viewcenter=test.rmodel.viewcenter;
//    AffineTransform t=new AffineTransform();
//    t.scale(scale,scale);
//    t.translate((dw/(2*scale))-viewcenter.x,(dh/(2*scale))-viewcenter.y);
//    g.setTransform(t);
//    //render all the shapes
//    System.out.println("rendering "+test.rmodel.pdisks.size()+" phenomena");
//    for(PDisk p:test.rmodel.pdisks)
//      p.render(g,scale);}
  
}

package org.fleen.rModel.test000;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;
import java.awt.image.BufferedImage;
import java.util.HashMap;

import org.fleen.geom_2D.DPoint;
import org.fleen.rModel.core.Square_Minimal;
import org.fleen.rModel.core.Square_PP_Abstract;

public class Renderer{
  
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
  
  Renderer(Test test){
    this.test=test;}
  
  Test test;
  
  BufferedImage image;
  
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
    double dw=w,dh=h,scale=10;
    AffineTransform t=new AffineTransform();
    t.scale(scale,scale);
    t.translate((dw/(2*scale)),(dh/(2*scale)));
    g.setTransform(t);
    //
    for(Square_Minimal s:test.rmodel.squares)
      if(s instanceof Square_PP_Abstract)
        renderSquare(g,(Square_PP_Abstract)s);
      
  }
    
  void renderSquare(Graphics2D g,Square_PP_Abstract s){
    DPoint[] cp=s.getCornerPoints();
    Path2D.Double path=new Path2D.Double();
    path.moveTo(cp[0].x,cp[0].y);
    path.lineTo(cp[1].x,cp[1].y);
    path.lineTo(cp[2].x,cp[2].y);
    path.lineTo(cp[3].x,cp[3].y);
    path.closePath();
    g.setPaint(s.getColor());
    g.fill(path);
    Stroke t=new BasicStroke((float)(3.0/g.getTransform().getScaleX()));
    g.setPaint(Color.white);
    g.draw(path);
    
      
    
  }
  
//  private void renderMandala(Graphics2D g,Mandala_Basic m){
//    System.out.println("rendering a mandala");
//    Set<Cell> 
//      edge=m.getEdgeCells(),
//      skin=m.getSkinCells();
//    //
//    g.setStroke(new BasicStroke((float)(1.0)));
//    g.setPaint(new Color(255,0,0,64));
//    for(Cell c:edge)
//      g.drawLine(c.x,c.y,c.x,c.y);  
//    //
//    g.setPaint(new Color(0,255,0,64));
//    for(Cell c:skin)
//      g.drawLine(c.x,c.y,c.x,c.y);  
//    //
//    double x=m.cx,y=m.cy,r=m.radius;
//    g.setStroke(new BasicStroke((float)(2.0/g.getTransform().getScaleX())));
//    g.setPaint(Color.black);
//    Ellipse2D e=new Ellipse2D.Double(x-r,y-r,r*2,r*2);
//    g.draw(e);}
    
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

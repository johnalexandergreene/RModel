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
    Random rnd=new Random();
    lifespan=rnd.nextInt(60)+60;}

  int lifespan;
  
  public void incrementState(){
    
    
  }

  public boolean destroyMe(){
    if(getAge()>lifespan)return true;
    return false;}

  public void render(BufferedImage i,Graphics2D g){
    g.setStroke(new BasicStroke((float)(3.0/g.getTransform().getScaleX())));
    g.setPaint(Color.white);
    Ellipse2D.Double e=new Ellipse2D.Double(cx-radius,cy-radius,radius*2,radius*2);
//    g.draw(e);
    
    /*
     * java circle drawing appears to be broken. makes jagged circles. so we're gonna do it better
     * 
     * get a center point
     * get a block of pixels surrounding that point
     * address all pixels. color by distance
     * this will be nice for animation too
     */
    
//    Path2D.Double circle=new Path2D.Double();
//    double s=16;
//    double[] h;
//    for(int k=0;k<s-1;k++){
//      h=GD.getPoint_PointDirectionInterval(cx,cy,k*GD.PI2/s,radius);
//      if(k==0){
//        circle.moveTo(h[0],h[1]);
//      }else{
//        circle.lineTo(h[0],h[1]);}}
//    circle.closePath();
//    g.draw(circle);
    
    double[] a={cx,cy,cx-radius,cy-radius,cx+radius,cy+radius};
    AffineTransform t=g.getTransform();
    double scale=t.getScaleX();
    t.transform(a,0,a,0,3);
    Color tt;
    double d;
    int rgb;
    float[] hsbvals=new float[3];
    for(int ax=(int)a[2];ax<a[4];ax++){
      for(int ay=(int)a[3];ay<a[5];ay++){
        d=GD.getDistance_PointPoint(ax,ay,a[0],a[1]);
        d=1.0-(d/(radius*scale));
        if(d<0)d=0;
        tt=new Color(255,0,0,(int)(d*255));
        rgb=i.getRGB(ax,ay);
        Color yy=new Color(rgb);
        Color.RGBtoHSB(yy.getRed(),yy.getGreen(),yy.getBlue(),hsbvals);
        hsbvals[0]+=d;
        yy=Color.getHSBColor(hsbvals[0],hsbvals[1],hsbvals[2]);
        i.setRGB(ax,ay,yy.getRGB());
      }
    }
    
    
    
    
    
    
    
  }

}

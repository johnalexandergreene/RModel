package org.fleen.rModel.test;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Renderer{
  
  Renderer(Test test){
    this.test=test;}
  
  Test test;
  
  BufferedImage image;
      
  void render(){
    
    image=new BufferedImage(256,256,BufferedImage.TYPE_INT_ARGB);
    Graphics2D g=image.createGraphics();
    Random r=new Random();
    g.setPaint(new Color(r.nextInt(256),r.nextInt(256),r.nextInt(256)));
    g.fillRect(0,0,256,256);
    
  }

}

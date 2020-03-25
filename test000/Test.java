package org.fleen.rModel.test000;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;

import org.fleen.rModel.abstractStinkyDog.VIG_AbstractStinkyDog;
import org.fleen.rModel.core.VignetteObserver;
import org.fleen.rModel.core.Vignette_Abstract;

public class Test{
  
  /*
   * ################################
   * CONSTRUCTOR
   * ################################
   */
  
  Test(){
    vignette=new VIG_AbstractStinkyDog();
    vignette.observer=observer;
    ui=new UI(this);
    renderer=new Renderer(this);
    exporter=new Exporter(this);}
  
  /*
   * ################################
   * OBSERVER
   * ################################
   */
  
  VignetteObserver observer=new VignetteObserver(){
    public void incrementedState(){
      System.out.println("incremented state");
      renderer.render();
      ui.repaint();
//      exporter.export();
      }};
      
  /*
   * ################################
   * EXPORT
   * ################################
   */
      
  static final String EXPORTDIR="/home/john/Desktop/rmodel_export";
  static final double EXPORTSCALE=0.9;
  static final AffineTransform EXPORTTRANSFORM=AffineTransform.getScaleInstance(EXPORTSCALE,EXPORTSCALE);
  public File exportdir=new File(EXPORTDIR);
  Exporter exporter;
  
  public BufferedImage getImageForExport(){
    BufferedImage a=new BufferedImage(
      (int)(renderer.image.getWidth()*EXPORTSCALE),
      (int)(renderer.image.getHeight()*EXPORTSCALE),
      BufferedImage.TYPE_INT_RGB);
    Graphics2D g=a.createGraphics();
    g.drawImage(renderer.image,EXPORTTRANSFORM,null);
    return a;}
  
  /*
   * ################################
   * RMODEL, UI, RENDERER AND EXPORTER
   * ################################
   */
  
  Vignette_Abstract vignette;
  UI ui; 
  Renderer renderer;
  
  /*
   * @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
   * @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
   * @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
   * MAIN
   * @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
   * @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
   * @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
   */
  
  public static final int FRAMECOUNT=2000;
  
  public static final void main(String[] a){
    Test test=new Test();
    for(int i=0;i<FRAMECOUNT;i++){
      test.vignette.advanceState();
     try{
      Thread.sleep(100);
     }catch(Exception x){};}}
    
}

package org.fleen.rModel.test_freerange_2d_circles;

import java.io.File;

public class Test{
  
  /*
   * ################################
   * CONSTRUCTOR
   * ################################
   */
  
  Test(){
    rmodel=new RModel(SPAN,SPAN);
    rmodel.observers.add(observer);
    ui=new UI(this);
    renderer=new Renderer(this);
    exporter=new Exporter(this);}
  
  /*
   * ################################
   * OBSERVER
   * ################################
   */
  
  RModelObserver observer=new RModelObserver(){
    public void advanced(){
      renderer.render();
      ui.repaint();
//      exporter.export();
      }};
      
  /*
   * ################################
   * EXPORTDIR
   * ################################
   */
      
  static final String EXPORT="/home/john/Desktop/rmodel_export";
  
  public File exportdir=new File(EXPORT);
  
  /*
   * ################################
   * RMODEL, UI, RENDERER AND EXPORTER
   * ################################
   */
  
  RModel rmodel;
  UI ui; 
  Renderer renderer;
  Exporter exporter;
  
  /*
   * @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
   * @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
   * @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
   * MAIN
   * @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
   * @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
   * @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
   */
  
  public static final int SPAN=400;
  
  public static final void main(String[] a){
    Test test=new Test();
    for(int i=0;i<5500;i++){
      test.rmodel.advanceState();
      System.out.println("RMODEL : "+test.rmodel);
     try{
      Thread.sleep(20);
     }catch(Exception x){};}}
    
}

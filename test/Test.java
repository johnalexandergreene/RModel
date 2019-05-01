package org.fleen.rModel.test;

import org.fleen.rModel.core.RModel;
import org.fleen.rModel.core.RModelObserver;

public class Test{
  
  /*
   * ################################
   * CONSTRUCTOR
   * ################################
   */
  
  Test(){
    rmodel=new RModel();
    rmodel.observers.add(observer);
    ui=new UI(this);
    renderer=new Renderer(this);}
  
  /*
   * ################################
   * RMODEL
   * ################################
   */
  
  RModel rmodel;
  
  /*
   * ################################
   * OBSERVER
   * ################################
   */
  
  RModelObserver observer=new RModelObserver(){
    public void advanced(){
      renderer.render();
      ui.repaint();}};
  
  /*
   * ################################
   * UI
   * ################################
   */
  
  UI ui;
      
  /*
   * ################################
   * RENDERER
   * ################################
   */
    
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
  
  public static final void main(String[] a){
    Test test=new Test();
    for(int i=0;i<100;i++){
      test.rmodel.advanceState();
      System.out.println("RMODEL : "+test.rmodel);
     try{
      Thread.sleep(1000);
     }catch(Exception x){};}}
    
}

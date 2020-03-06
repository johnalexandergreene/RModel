package org.fleen.rModel.core;

public class Cell{
  
  /*
   * ################################
   * GEOMETRY
   * ################################
   */
  
  public Cell(Mandala_Abstract m,int x,int y){
    mandala=m;
    this.x=x;
    this.y=y;}
  
  /*
   * ################################
   * MANDALA
   * ################################
   */
  
  Mandala_Abstract mandala;
  
  /*
   * ################################
   * GEOMETRY
   * ################################
   */
  
  public int x,y;
  
  /*
   * ################################
   * OBJECT
   * ################################
   */
  
  public int hashCode(){
    return x+23*y;}
  
  public boolean equals(Object a){
    Cell b=(Cell)a;
    return x==b.x&&y==b.y;}
  
  public String toString(){
    return "["+x+","+y+"]";}
  
  

}

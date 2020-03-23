package org.fleen.rModel.core;

import java.awt.geom.Path2D;
import java.util.ArrayList;
import java.util.List;

import org.fleen.geom_2D.DPoint;
import org.fleen.geom_2D.GD;

public class Square_Minimal{
  
  /*
   * ################################
   * CONSTRUCTOR
   * ################################
   */
  
  public Square_Minimal(RModel rmodel,int x,int y,int span){
    this.rmodel=rmodel;
    this.x=x;
    this.y=y;
    this.span=span;}
  
  /*
   * ################################
   * RMODEL
   * ################################
   */
  
  RModel rmodel;
  
  /*
   * ################################
   * GEOMETRY
   * ################################
   */
  
  //coordinates of SW corner cell in square
  public int x,y;
  //height and width of the square, in cells
  public int span;
  
  public void setLocation(int x,int y){
    this.x=x;
    this.y=y;
    edge=null;
    skin=null;}
  
  //sw,nw,ne,se
  public DPoint[] getCornerPoints(){
    DPoint[] cp=new DPoint[4];
    cp[0]=new DPoint(x*RModel.CELLSPAN,y*RModel.CELLSPAN);
    cp[1]=new DPoint(x*RModel.CELLSPAN,y*RModel.CELLSPAN+span*RModel.CELLSPAN);
    cp[2]=new DPoint(x*RModel.CELLSPAN+span*RModel.CELLSPAN,y*RModel.CELLSPAN+span*RModel.CELLSPAN);
    cp[3]=new DPoint(x*RModel.CELLSPAN+span*RModel.CELLSPAN,y*RModel.CELLSPAN);
    return cp;}
  
  Path2D.Double edgepath=null;
  
  public Path2D.Double getEdgePath(){
    if(edgepath==null)
      initEdgePath();
    return edgepath;}
  
  private void initEdgePath(){
    edgepath=new Path2D.Double();
    DPoint[] cp=getCornerPoints();
    edgepath.moveTo(cp[0].x,cp[0].y);
    edgepath.lineTo(cp[1].x,cp[1].y);
    edgepath.lineTo(cp[2].x,cp[2].y);
    edgepath.lineTo(cp[3].x,cp[3].y);
    edgepath.closePath();}
  
  public DPoint getCenter(){
    DPoint[] c=getCornerPoints();
    double[] a=GD.getPoint_Mid2Points(c[0].x,c[0].y,c[2].x,c[2].y);
    return new DPoint(a);}
  
  /*
   * ################################
   * CELLS
   * ################################
   */
  
  //CELLS
  //includes edge but not skin
  private List<Cell> cells=null;
  
  public List<Cell> getCells(){
    if(cells==null)
      initCells();
    return cells;}
  
  private void initCells(){
    cells=new ArrayList<Cell>(span*2);
    for(int cx=x;cx<x+span;cx++){
      for(int cy=y;cy<y+span;cy++){
        cells.add(new Cell(cx,cy));}}}
  
  //EDGE 
  private List<Cell> edge=null;
  
  public List<Cell> getEdge(){
    if(edge==null)
      initEdge();
    return edge;}
  
  /*
   * get the cells at the edge of this square. 
   * a square has a number of edge cells on each side equal to span; 
   */
  private void initEdge(){
    edge=getSquare(x,y,span);}
  
  //SKIN
  private List<Cell> skin=null;
  
  public List<Cell> getSkin(){
    if(skin==null)
      initSkin();
    return skin;}
  
  /*
   * get the cells at one cell beyond the edge of this square. 
   * a square has a number of edge cells on each side equal to span+2; 
   */
  private void initSkin(){
    skin=getSquare(x-1,y-1,span+2);}
  
  /*
   * --------------------------------
   * EDGE AND SKIN CELLS GETTER 
   * get cells at edge of square
   */
  private List<Cell> getSquare(int x,int y,int span){
    List<Cell> square;
    if(span==0){
      throw new IllegalArgumentException("span=0");
    }else if(span==1){
      square=new ArrayList<Cell>(1);
      square.add(new Cell(x,y));
    }else if(span==2){
      square=new ArrayList<Cell>(4);
      square.add(new Cell(x,y));
      square.add(new Cell(x,y+1));
      square.add(new Cell(x+1,y+1));
      square.add(new Cell(x+1,y));
    }else{
      square=new ArrayList<Cell>((span-1)*4);
      //west edge
      for(int cy=y;cy<y+span-1;cy++)
        square.add(new Cell(x,cy));
      //north edge
      for(int cx=x;cx<x+span-1;cx++)
        square.add(new Cell(cx,y+span-1));
      //east edge
      for(int cy=y+span-1;cy>y;cy--)
        square.add(new Cell(x+span-1,cy));
      //south edge
      for(int cx=x+span-1;cx>x;cx--)
        square.add(new Cell(cx,y));}
    //
    return square;}
  
  /*
   * ################################
   * OBJECT
   * ################################
   */
  
  public int hashCode(){
    return x*53+y*59+span*61;}
  
  public boolean equals(Object a){
    Square_Minimal b=(Square_Minimal)a;
    boolean e=(x==b.x)&&(y==b.y)&&(span==b.span);
    return e;}
  
  public String toString(){
    String a="["+x+","+y+","+span+"]";
    return a;}

}

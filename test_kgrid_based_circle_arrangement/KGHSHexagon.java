package org.fleen.rModel.test_kgrid_based_circle_arrangement;

import org.fleen.geom_Kisrhombille.GK;
import org.fleen.geom_Kisrhombille.KPoint;
import org.fleen.geom_Kisrhombille.KPolygon;

/*
 * our kgrid and hexagons are all point-up
 */
public class KGHSHexagon extends KPolygon{
  
  private static final long serialVersionUID=7630228924325355002L;
  
  /*
   * ################################
   * CONSTRUCTOR
   * ################################
   */

  public KGHSHexagon(KPoint c,int size){
    center=c;
    this.size=size;
    initPoints();}
  
  /*
   * ################################
   * GEOMETRY
   * ################################
   */
  
  KPoint center;
  int size;
  
  /*
   * given the center point
   * traverse the kgrid north until a viable point (of type 6 or 12, not a 4) is encountered
   * get the size'th point of this type
   *   that is to say, if size is 1 then get the first viable point
   *   if size is 2 then get the second, etc.
   * Do the same thing in the other 5 directions.
   * Now we have our points 
   *   
   */
  private void initPoints(){
    add(getNthPointInDirection(size,GK.DIRECTION_0));
    add(getNthPointInDirection(size,GK.DIRECTION_2));
    add(getNthPointInDirection(size,GK.DIRECTION_4));
    add(getNthPointInDirection(size,GK.DIRECTION_6));
    add(getNthPointInDirection(size,GK.DIRECTION_8));
    add(getNthPointInDirection(size,GK.DIRECTION_10));}
  
  private KPoint getNthPointInDirection(int size,int dir){
    int countdown=size;
    KPoint a=center.getVertex_Adjacent(dir);
    while(countdown!=0){
      if(a.getGeneralType()!=GK.VERTEX_GTYPE_4){
        countdown--;}}
    return a;}

}

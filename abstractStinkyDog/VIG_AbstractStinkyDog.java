package org.fleen.rModel.abstractStinkyDog;

import java.util.Random;

import org.fleen.geom_2D.DPoint;
import org.fleen.geom_2D.GD;
import org.fleen.rModel.core.SPP_Sight;
import org.fleen.rModel.core.SPP_Smell;
import org.fleen.rModel.core.SPP_Sound;
import org.fleen.rModel.core.Vignette_Abstract;

public class VIG_AbstractStinkyDog extends Vignette_Abstract{
  
  protected void initClumpAnglesAndCenters(){
    Random rnd=new Random();
    double 
      initoffset=rnd.nextDouble()*GD.PI2,
      offset=GD.PI2/3;
    double[] angles=new double[3];
    for(int i=0;i<3;i++)
      angles[i]=initoffset+offset*i;
    //
    String[] names=new String[7];
    names[0]=SPP_Sight.TYPE;
    names[1]=SPP_Smell.TYPE;
    names[2]=SPP_Sound.TYPE;
    //
    double[] a;
    for(int i=0;i<3;i++){
      clumpangles.put(names[i],angles[i]);
      a=GD.getPoint_PointDirectionInterval(0,0,angles[i],2);
      clumpcenters.put(names[i],new DPoint(a));}}
  
  public void advanceState(){
    Random rnd=new Random();
    if(squares.size()<3)
      addSquare(new SPP_DogBark(this,rnd.nextInt(3)+1));
    super.advanceState();}
  
  

}

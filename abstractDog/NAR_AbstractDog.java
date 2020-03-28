package org.fleen.rModel.abstractDog;

import java.util.Iterator;
import java.util.Random;

import org.fleen.rModel.core.Narrative;
import org.fleen.rModel.core.Square_PerceptualEvent_Abstract;
import org.fleen.rModel.core.Vignette;

public class NAR_AbstractDog implements Narrative{

  Vignette vignette;
  
  public void setVignette(Vignette v){
    vignette=v;}

  public void advanceState(){
    //remove dead
    Square_PerceptualEvent_Abstract square;
    Iterator<Square_PerceptualEvent_Abstract> i=vignette.squares.iterator();
    while(i.hasNext()){
      square=i.next();
      if(square.destroyMe())
        i.remove();}
    //
    Random rnd=new Random();
    int z=rnd.nextInt(VIG_AbstractDog.PETYPES.length);
    if(vignette.squares.size()<12){
      if(z==0){
        vignette.addSquare(new SPE_DogBark(vignette,rnd.nextInt(3)+1));
      }else if(z==1){
        vignette.addSquare(new SPE_DogSight(vignette,rnd.nextInt(3)+1));
      }else{
        vignette.addSquare(new SPE_DogSmell(vignette,rnd.nextInt(3)+1));}}
      
    
  }
  

}

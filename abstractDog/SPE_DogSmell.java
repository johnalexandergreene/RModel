package org.fleen.rModel.abstractDog;

import java.util.Random;

import org.fleen.rModel.core.SPE_Smell;
import org.fleen.rModel.core.Vignette;

public class SPE_DogSmell extends SPE_Smell{

  /*
   * ################################
   * CONSTRUCTOR
   * ################################
   */
  
  public SPE_DogSmell(Vignette rmodel,int x,int y,int span){
    super(rmodel,x,y,span);}
  
  public SPE_DogSmell(Vignette rmodel,int span){
    super(rmodel,span);}

  /*
   * ################################
   * STATE
   * ################################
   */

  int maxage=-1;
  
  protected int getMaxAge(){
    if(maxage==-1){
      Random rnd=new Random();
      maxage=rnd.nextInt(100)+40;}
    return maxage;}

  /*
   * ################################
   * MANIFESTATION
   * ################################
   */
  
  public static final String WORD="Stink";
  public static final String NARRATIVE="The dog stinks.";
  
  public double getIntensity(){
    double 
      a=getAge(),
      s=getMaxAge();
    if(a/s<0.2){
      return a/(s*0.2);    
    }else if(a/s>0.8){
      return (s-a)/(s*0.2);}
    return 1.0;}

  public String getWord(){
    return WORD;}

  public String getNarrative(){
    return NARRATIVE;}

}

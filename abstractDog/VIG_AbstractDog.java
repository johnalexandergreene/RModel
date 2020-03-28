package org.fleen.rModel.abstractDog;

import org.fleen.rModel.core.Vignette;

public class VIG_AbstractDog extends Vignette{

  public static final String[] PETYPES={
    SPE_DogSmell.TYPE,
    SPE_DogSight.TYPE,
    SPE_DogBark.TYPE,
  };
  
  public VIG_AbstractDog(){
    super(new NAR_AbstractDog(),PETYPES);}

}

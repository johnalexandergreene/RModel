package org.fleen.rModel.core;

/*
 * This is a schedule of events for a Vignette. 
 * What events occur and when
 * Events can be scheduled literally, programatically, randomly...
 * 
 * The way it works is
 * At each tick of the Vignette we invoke the tick method here
 * And then whatever logic we put into tick 
 * 
 */
public interface Narrative{

  void setVignette(Vignette v);
  
  void advanceState();
  
}

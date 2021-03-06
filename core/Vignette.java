package org.fleen.rModel.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import org.fleen.geom_2D.DPoint;
import org.fleen.geom_2D.GD;

/*
 * Vignette
 * A model of reality. A bunch of sounds, sights, smells, thoughts (call them Perceptual Phenomena) etc dancing around.
 * 
 * A hex grid. Colored hexagons representing Perceptual Phenomena. 
 * Colored by type (sight, sound, smell, thought...). Faded by intensity (very pastel to bold high satch) 
 * 
 * We have a bunch of perceptual phenomena arriving, manifesting and leaving. 
 * That is
 * A hexagon fades in. White-to-pastel-to-bold.
 * Hangs out for the duration of its manifestation. Maybe it throbs a bit.
 * Then it departs. Slow or fast according to its (the perceptual phenomen's) nature.
 * 
 * a bunch of hexagonal icons representing perceptual phenomena
 * taken together, as a pattern, represents a whole river perceptual phenomena. Ie reality.
 * 
 * We will have a simple narrative text stream beneath the hexagon animation. 
 * "The dog barks". "The pumpkin reeks". "You think about oranges".
 * 
 * ---
 * 
 * Vignette is a pattern of Perceptual Phenomena. Governed by some associated logic.
 * The pattern is rendered as the bunch of hexagons and the narrative
 * 
 * So we have hexagons. 
 * We create class PerceptualPhenomenonHexagon objects
 * Create. Add to list. Remove from list when appropriate.
 * throw Strings at the narrative scroller.
 * Keep doing that for a number of cycles. Incrementally export video frames. 
 * 
 * Assume 30fps
 * 
 * Hexagon placement is a thing we do. PPHex geometry.
 * Hex0 goes in the center.
 * get growth cone for hex0
 * hex1 goes next to hex0
 * and so on.
 * Constrain growth to growth cones. Cones of growth-restriction, 1 for each type
 * try to keep the types nicely grouped into descreetish clumps.
 * adjacent but distinct.
 *   
 */
public class Vignette{
  
  /*
   * ################################
   * CONSTRUCTOR
   * ################################
   */
  
  public Vignette(Narrative narrative,String[] petypes){
    this.narrative=narrative;
    narrative.setVignette(this);
    initClumpAnglesAndCenters(petypes);}
  
  /*
   * we specify the number of perceptual phenomena types expected.
   * 7 is our usual number. seven "senses"
   * sight,sound,touch,smell,thought,taste,strange(undefined)  
   * 
   */
  public Vignette(int typecount){
    initTypeGrowthDirections(typecount);}
  
  /*
   * ################################
   * PPHEX GROWTH VECTORS
   * For each pphex type we have a growth vector
   * this is a direction from the center in which that type builds hexagons
   * ################################
   */
  
  public int pphextypecount;
  private List<Double> rawpphextypegrowthdirections;
  
  private void initTypeGrowthDirections(int typecount){
    pphextypecount=typecount;
    rawpphextypegrowthdirections=new ArrayList<Double>(typecount);
    Random r=new Random();
    double 
      d=r.nextDouble()*GD.PI2,
      angularinterval=GD.PI2/((double)typecount);
    for(int i=0;i<typecount;i++){
      d+=((double)i)*angularinterval;
      d=GD.normalizeDirection(d);
      rawpphextypegrowthdirections.add(d);}}
  
  /*
   * lazy init growth directions by PPHex type name
   * for rando PPHex type. 
   * If it's your first time then init direction from the list of raws at random
   */
  
  HashMap<String,Double> dirbyname=new HashMap<String,Double>();
  
  public double getGrowthDirection(PPHex pphex){
    Double d=dirbyname.get(pphex.getTypeName());
    if(d==null){
      Random r=new Random();
      int i=r.nextInt(rawpphextypegrowthdirections.size());
      d=rawpphextypegrowthdirections.remove(i);
      dirbyname.put(pphex.getTypeName(),d);}
    return d;}
   
  /*
   * ################################
   * PERCEPTUAL PHENOMENON HEXAGONS
   * ################################
   */
  
  public Set<PPHex> pphexagons=new HashSet<PPHex>();
  
  /*
   * the big question is where to put it
   * 
   * if there are no hexes then put the new one in the center
   * if there is one then put then new one next to it
   * calculate growth cones too 
   */
  public void addPPHex(PPHex newpphex){
    //even if we don't use it we want to initialize it.
    double growthdir=getGrowthDirection(newpphex);
    System.out.println("growthdir="+growthdir);
    //if there aren't any then put it in the center
    if(pphexagons.isEmpty()){
      newpphex.setCoor(0,0);
      pphexagons.add(newpphex);
    }else{
      PPHex placement=getPlacementForNewPPHex(newpphex,growthdir);
      newpphex.setCoor(placement.coora,placement.coorb);
      pphexagons.add(newpphex);}}
  
  /*
   * ################################
   * PPHEX PLACEMENT
   * get all possible
   * remove any already occupied
   * rate prospective placements by 
   *   # of similar neighbors
   *   closeness to growth angle
   * sort by rating
   * return the top rated
   * ################################
   */
  
  PPHex getPlacementForNewPPHex(PPHex newpphex,double growthdir){
    //get all possible
    Set<PPHex> prospects=new HashSet<PPHex>();
    for(PPHex hex:pphexagons)
      prospects.addAll(hex.getNeighbors());
    //cull extant
    for(PPHex hex:pphexagons){
      prospects.remove(hex);
    }
    
    //TEST
    List<PPHex> foo=new ArrayList<PPHex>(prospects);
    Random r=new Random();
    return foo.get(r.nextInt(foo.size()));
    
    
  }
  
  
  /*
   * ################################
   * NARRATIVE
   * ################################
   */
  
  Narrative narrative;
  
  /*
   * ################################
   * OBSERVER
   * ################################
   */
  
  public VignetteObserver observer;
  
  /*
   * ################################
   * STATE
   * ################################
   */
  
  public int age=0;
  
  /*
   * TODO
   * override and extend this
   * add ppsquare creation logic for directing the vignette
   * super.advanceState should probably come after the directing logic
   */
  public void advanceState(){
    System.out.println("advanced state");
//    narrative.advanceState();
    //report to observer
    
    //TEST
    addPPHex(new PPHex());
    
    observer.advancedState();
    
    //increment age
    age++;}
  
  /*
   * ################################
   * SQUARES
   * ################################
   */
  
  public List<Square_PerceptualEvent_Abstract> squares=new ArrayList<Square_PerceptualEvent_Abstract>();
  
  public void addSquare(Square_PerceptualEvent_Abstract newsquare){
    if(squares.isEmpty()){ 
      DPoint cc=clumpcenters.get(newsquare.getType());
      newsquare.setLocation((int)(cc.x-newsquare.span/2),(int)(cc.y-newsquare.span/2));
    }else{
      setLocationForNewSquare(newsquare);}
    //
    squares.add(newsquare);}
  
  /*
   * ################################
   * CLUMP ANGLES AND CENTERS
   * Used for placing squares and arranging clumps of squares
   * ################################
   */
  
  public static final double CLUMPCENTERORIGINOFFSET=2.0;
  
  protected Map<String,Double> clumpangles=new HashMap<String,Double>();
  
  protected Map<String,DPoint> clumpcenters=new HashMap<String,DPoint>();
  
  private void initClumpAnglesAndCenters(String[] types){
    Random rnd=new Random();
    double 
      initoffset=rnd.nextDouble()*GD.PI2,
      offset=GD.PI2/types.length;
    double[] angles=new double[types.length];
    for(int i=0;i<types.length;i++)
      angles[i]=initoffset+offset*i;
    //
    double[] a;
    for(int i=0;i<types.length;i++){
      clumpangles.put(types[i],angles[i]);
      a=GD.getPoint_PointDirectionInterval(0,0,angles[i],CLUMPCENTERORIGINOFFSET);
      clumpcenters.put(types[i],new DPoint(a));}}
 
  /*
   * ################################
   * GET LOCATION FOR NEW SQUARE
   * We build clumps of squares
   * Probably clumped by type
   * All the clumps clump together around the origin
   * A typed clump is guided by a conical constraint fanning out from the origin. Thus we are organized.
   * 
   * given a new square : newsquare
   * get its skin cells
   * get the set of all extant squares' edge cells
   * get the set of all locations for newsquare where a particular skincell and an particular edgecell coincide
   * ################################
   */
  
  private void setLocationForNewSquare(Square_PerceptualEvent_Abstract newsquare){
    Set<Square_Minimal> rawprospects=getRawProspects(newsquare);
    cullCollisions(rawprospects);
    if(rawprospects.isEmpty())throw new IllegalArgumentException("no raw prospects");
    Map<Square_Minimal,Double> ratings=rateProspects(rawprospects,newsquare);
    //
    List<Square_Minimal> orderedprospects=new ArrayList<Square_Minimal>(rawprospects);
    Collections.sort(orderedprospects,new ProspectComparator(ratings));
    //
    Square_Minimal b=orderedprospects.get(0);//top rated.
    newsquare.setLocation(b.x,b.y);}
  
  /*
   * test for collision between newsquare and all extant squares
   * 
   * get the set of all cells in all extant squares : extantcells
   * for each prospect
   * get its cell set : prospectcells
   * if prospectcells contains any cell in extantcells then collision 
   */
  private void cullCollisions(Set<Square_Minimal> prospects){
    Set<Cell> extant=new HashSet<Cell>();
    for(Square_Minimal s:squares)
      extant.addAll(s.getCells());
    //
    Iterator<Square_Minimal> i=prospects.iterator();
    Square_Minimal prospect;
    Set<Cell> test;
    while(i.hasNext()){
      prospect=i.next();
      test=new HashSet<Cell>(prospect.getCells());
      test.retainAll(extant);
      if(!test.isEmpty())
        i.remove();}}
  
  /*
   * for every cell in newsquare.skin
   * for every cell in the set of all cells in the edge of all extant squares : extantedges
   * where the 2 coincide, that's a raw prospect.
   */
  private Set<Square_Minimal> getRawProspects(Square_PerceptualEvent_Abstract newsquare){
    Set<Cell> extantedges=new HashSet<Cell>();
    //get edges by name
    for(Square_Minimal s:squares)
      if(((Square_PerceptualEvent_Abstract)s).getType().equals(newsquare.getType()))
      extantedges.addAll(s.getEdge());
    //if that fails then just get edges
    if(extantedges.isEmpty()){
      for(Square_Minimal s:squares)
        extantedges.addAll(s.getEdge());}
    //
    List<Cell> newsquareskin=newsquare.getSkin();
    //
    Set<Square_Minimal> prospects=new HashSet<Square_Minimal>();
    Square_Minimal prospect;
    for(Cell c0:newsquareskin){
      for(Cell c1:extantedges){
        prospect=new Square_Minimal(
          this,
          c1.x-c0.x,
          c1.y-c0.y,
          newsquare.span);
        prospects.add(prospect);}}
    //
    return prospects;}
  
  class ProspectComparator implements Comparator<Square_Minimal>{
    
    ProspectComparator(Map<Square_Minimal,Double> ratings){
      this.ratings=ratings;}
    
    Map<Square_Minimal,Double> ratings;

    public int compare(
      Square_Minimal a0,
      Square_Minimal a1){
      double 
        r0=ratings.get(a0),
        r1=ratings.get(a1);
      if(r0==r1){
        return 0;
      }else if(r0<r1){
        return 1;
      }else{
        return -1;}}}
  
  /*
   * ################################
   * RATE LOCATION PROSPECTS
   * Rate prospects by
   *   number of neighbors (number of alike-tagged neighbors TODO)
   *   closeness to origin
   *   closeness to clump ray
   * ################################
   */
  
  private Map<Square_Minimal,Double> rateProspects(Set<Square_Minimal> rawprospects,Square_PerceptualEvent_Abstract newsquare){
    Map<Square_Minimal,Double> ratings=new HashMap<Square_Minimal,Double>();
    double 
      neighborcount,
      closenesstoorigin,
      closenesstoclumpangle,
      rating;
    for(Square_Minimal prospect:rawprospects){
      neighborcount=getNeighborCount(prospect);
      closenesstoorigin=getClosenessToClumpPoint(prospect,newsquare);
      closenesstoclumpangle=getClosenessToClumpAngle(prospect,newsquare);
      rating=
        neighborcount*SCALENEIGHBORCOUNT+
        closenesstoorigin*CLOSENESSTOORIGINSCALE+
        closenesstoclumpangle*CLOSENESSTOCLUMPANGLESCALE;
      ratings.put(prospect,rating);}
    return ratings;}
  
  static final double SCALENEIGHBORCOUNT=1.0;
  
  /*
   * get set of all extant edges cells
   * get set of prospect skin cells
   * neighbor count is the overlap
   */
  double getNeighborCount(Square_Minimal prospect){
    Set<Cell> extantedges=new HashSet<Cell>();
    for(Square_Minimal s:squares)
      extantedges.addAll(s.getEdge());
    //
    Set<Cell> testprospectskin=new HashSet<Cell>(prospect.getSkin()); 
    //
    testprospectskin.retainAll(extantedges);
    return testprospectskin.size();}
  
  private static final double CLOSENESSTOORIGINSCALE=-1.0;
  
  /*
   * test distance to all cell centers
   * return closest
   * negativize the value because smaller is better
   */
  double getClosenessToClumpPoint(Square_Minimal prospect,Square_PerceptualEvent_Abstract newsquare){
    List<Cell> cells=new ArrayList<Cell>(prospect.getCells());
    DPoint clumpcenter=clumpcenters.get(newsquare.getType());
    Collections.sort(cells,new CellDistanceComparator(clumpcenter));
    return cells.get(0).getDistanceToPoint(clumpcenter);}
  
  class CellDistanceComparator implements Comparator<Cell>{
    
    CellDistanceComparator(DPoint clumpcenter){
      this.clumpcenter=clumpcenter;}

    DPoint clumpcenter;
    
    public int compare(Cell a0,Cell a1){
      double 
        d0=((Cell)a0).getDistanceToPoint(clumpcenter),
        d1=((Cell)a1).getDistanceToPoint(clumpcenter);
      //
      if(d0==d1){
        return 0;
      }else if(d0<d1){
        return -1;
      }else{
        return 1;}}}
  
  private static final double CLOSENESSTOCLUMPANGLESCALE=-2.0;
  
  double getClosenessToClumpAngle(Square_Minimal prospect,Square_PerceptualEvent_Abstract newsquare){
    double clumpangle=clumpangles.get(newsquare.getType());
    DPoint center=prospect.getCenter();
    double prospectangle=GD.getDirection_PointPoint(0,0,center.x,center.y);
    double difference=GD.getAbsDeviation_2Directions(clumpangle,prospectangle);
    return difference;}
  
  /*
   * ################################
   * OBJECT
   * ################################
   */
  
  public String toString(){
    StringBuffer a=new StringBuffer("VIGNETTE");
    a.append("\n scount : "+squares.size());
    a.append("\n age : "+age);
    return a.toString();}

}

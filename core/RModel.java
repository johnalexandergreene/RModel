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
 * reality model
 * a bunch of square icons representing perceptual phenomena
 * taken together, as a pattern, represents a whole river perceptual phenomena. Ie reality.
 * 
 * So we have these perceptual phenomenon squares : class PPSquare
 * A PPSquare appears somewhere in the perspective. 
 *   It's appears. A colored square. 
 *   It fades in, persists, fades out. The duration of the fading is arbitrary, rresembling the phenomenon somewhat.
 *     (Example 1 : a "dog bark" will fade-zoom in quicky, persist for a moment, and immediately fade-zoom out.)
 *     (Example 2 : a "Car sound" will slowly fade-zoom in, persist for a mement, and then slowly fade-zoom out.)
 *   It will have a name, or an initial.
 *   It will have a color corresponding to sight, sound, thought etc.
 * Another PPSquare may appear, then another.
 * PPSquares will group by sense.
 * PPSquare size corresponds to intensity.
 * Fading in and out correspond to rise and fall in intensity.
 * Intensity corresponds to size and alpha.
 * 
 * Each group of sense squares will build in a certain direction, constrained to an angle-section from the origin.
 *   
 */
public class RModel{
  
  /*
   * ################################
   * CONSTRUCTOR
   * now we're gonna do it with 7 types
   * ################################
   */
  
  public RModel(){
    double 
      initoffset=rnd.nextDouble()*GD.PI2,
      offset=GD.PI2/7;
    double[] angles=new double[7];
    for(int i=0;i<7;i++)
      angles[i]=initoffset+offset*i;
    //
    String[] names=new String[7];
    names[0]=Square_PP_Sight.NAME;
    names[1]=Square_PP_Smell.NAME;
    names[2]=Square_PP_Sound.NAME;
    names[3]=Square_PP_Strange.NAME;
    names[4]=Square_PP_Taste.NAME;
    names[5]=Square_PP_Thought.NAME;
    names[6]=Square_PP_Touch.NAME;
    //
    double[] a;
    for(int i=0;i<7;i++){
      clumpangles.put(names[i],angles[i]);
      a=GD.getPoint_PointDirectionInterval(0,0,angles[i],2);
      clumpcenters.put(names[i],new DPoint(a));}}
  
  /*
   * ################################
   * GEOMETRY
   * ################################
   */
  
  public static final double CELLSPAN=1.0;
  
  Map<String,Double> clumpangles=new HashMap<String,Double>();
  
  Map<String,DPoint> clumpcenters=new HashMap<String,DPoint>();
  
  /*
   * ################################
   * VIEW
   * ################################
   */
  
  public DPoint viewcenter=new DPoint(0,0);
  
  public double scale=40;
  
//  void setViewCenter(PDisk disk){
////    viewcenter=disk.getCenter().getDPoint();
//    }
//  
//  void setViewCenter(List<PDisk> disks){
//    //TODO
//    //centroid or whatever
//    }
  
  /*
   * ################################
   * STATE
   * ################################
   */
  
  public int age=0;
  
  public void advanceState(){
    //remove dead
    Square_PP_Abstract square;
    Iterator<Square_PP_Abstract> i=squares.iterator();
    while(i.hasNext()){
      square=i.next();
      if(square.destroyMe())
        i.remove();}
    //
    int r;
    if(squares.size()<8){
      r=rnd.nextInt(7);
      if(r==0){
        addSquare(new Square_PP_Sight(this,rnd.nextInt(3)+1));
      }else if(r==1){
        addSquare(new Square_PP_Smell(this,rnd.nextInt(3)+1));
      }else if(r==2){
        addSquare(new Square_PP_Sound(this,rnd.nextInt(3)+1));
      }else if(r==3){
        addSquare(new Square_PP_Strange(this,rnd.nextInt(3)+1));
      }else if(r==4){
        addSquare(new Square_PP_Taste(this,rnd.nextInt(3)+1));
      }else if(r==5){
        addSquare(new Square_PP_Thought(this,rnd.nextInt(3)+1));
      }else{
        addSquare(new Square_PP_Touch(this,rnd.nextInt(3)+1));}}
    //   
    observer.incrementedState();
    age++;}
  
  
  /*
   * ################################
   * OBSERVER
   * ################################
   */
  
  public RModelObserver observer;
  
  /*
   * ################################
   * SQUARES
   * ################################
   */
  
  public List<Square_PP_Abstract> squares=new ArrayList<Square_PP_Abstract>();
  
  /*
   * TODO make this work with type cone placement 
   */
  public void addSquare(Square_PP_Abstract newsquare){
    if(squares.isEmpty()){ 
      DPoint cc=clumpcenters.get(newsquare.getType());
      newsquare.setLocation((int)(cc.x-newsquare.span/2),(int)(cc.y-newsquare.span/2));
    }else{
      setLocationForNewSquare(newsquare);}
    //
    squares.add(newsquare);}
 
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
  
  private void setLocationForNewSquare(Square_PP_Abstract newsquare){
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
  private Set<Square_Minimal> getRawProspects(Square_PP_Abstract newsquare){
    Set<Cell> extantedges=new HashSet<Cell>();
    //get edges by name
    for(Square_Minimal s:squares)
      if(((Square_PP_Abstract)s).getType().equals(newsquare.getType()))
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
  
  private Map<Square_Minimal,Double> rateProspects(Set<Square_Minimal> rawprospects,Square_PP_Abstract newsquare){
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
  double getClosenessToClumpPoint(Square_Minimal prospect,Square_PP_Abstract newsquare){
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
  
  double getClosenessToClumpAngle(Square_Minimal prospect,Square_PP_Abstract newsquare){
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
    StringBuffer a=new StringBuffer("RMODEL");
    a.append("\n scount : "+squares.size());
    a.append("\n age : "+age);
    return a.toString();}
  
  /*
   * ################################
   * UTIL
   * ################################
   */
  
  private Random rnd=new Random();

}

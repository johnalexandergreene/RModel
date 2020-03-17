package org.fleen.rModel.zCellSystem;

import java.util.Iterator;


public interface ZCellMass extends Iterable<ZCell>{
  
  int getCellCount();
  
  Iterator<ZCell> iterator();
  
}

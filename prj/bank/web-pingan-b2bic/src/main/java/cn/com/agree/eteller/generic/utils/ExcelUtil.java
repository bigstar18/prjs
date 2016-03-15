package cn.com.agree.eteller.generic.utils;

import jxl.Cell;

public class ExcelUtil
{
  public static boolean isEmptyRow(Cell[] rowCells, Integer startCol, Integer endCol)
  {
    startCol = Integer.valueOf(startCol == null ? 0 : startCol.intValue());
    endCol = Integer.valueOf(endCol == null ? rowCells.length : endCol.intValue());
    

    int emptyCellCount = 0;
    for (int j = startCol.intValue() - 1; j < endCol.intValue(); j++) {
      if (ComFunction.isEmpty(rowCells[j].getContents())) {
        emptyCellCount++;
      }
    }
    if (emptyCellCount == endCol.intValue() - startCol.intValue() + 1) {
      return true;
    }
    return false;
  }
}

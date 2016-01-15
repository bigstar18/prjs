package gnnt.MEBS.util;

import java.util.Vector;

public class ObjSet extends Vector
{
  private int totalCount;
  private int pageSize = 1;

  private int pageCount = 1;

  private int pageIndex = 1;

  public int getTotalCount()
  {
    return this.totalCount;
  }

  public int getPageSize()
  {
    return this.pageSize;
  }

  public int getCurNum()
  {
    return size();
  }

  public int getPageCount()
  {
    if (this.pageSize > 0) {
      if (this.totalCount % this.pageSize != 0) {
        this.pageCount = (this.totalCount / this.pageSize + 1);
      }
      else {
        this.pageCount = (this.totalCount / this.pageSize);
      }
    }
    return this.pageCount;
  }

  public int getPageIndex()
  {
    return this.pageIndex;
  }

  public static ObjSet getInstance(Object[] objs, int totalCount, int pageSize, int pageIndex)
  {
    ObjSet objSet = new ObjSet();
    for (int i = 0; i < objs.length; i++) {
      objSet.add(objs[i]);
    }
    objSet.totalCount = totalCount;
    objSet.pageSize = pageSize;
    objSet.pageIndex = pageIndex;
    return objSet;
  }
}
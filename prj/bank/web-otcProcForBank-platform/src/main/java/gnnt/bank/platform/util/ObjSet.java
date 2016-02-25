package gnnt.bank.platform.util;

import java.util.List;
import java.util.Vector;

public class ObjSet
  extends Vector<Object>
{
  private static final long serialVersionUID = 1000L;
  private int totalCount = 0;
  private int pageSize = 0;
  private int pageCount = 0;
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
      } else {
        this.pageCount = (this.totalCount / this.pageSize);
      }
    }
    return this.pageCount;
  }
  
  public int getPageIndex()
  {
    return this.pageIndex;
  }
  
  public static ObjSet getInstance(Vector<Object> objs, int pageSize, int pageIndex)
  {
    ObjSet objSet = new ObjSet();
    if (objs != null)
    {
      objSet.totalCount = objs.size();
      objSet.pageSize = pageSize;
      objSet.pageIndex = pageIndex;
      for (int i = 0; i < objs.size(); i++) {
        if (((pageIndex - 1) * pageSize <= i) && (i < pageIndex * pageSize)) {
          objSet.add(objs.get(i));
        }
      }
    }
    return objSet;
  }
  
  public static ObjSet getInstance(List<Object> objs, int pageSize, int pageIndex)
  {
    ObjSet objSet = new ObjSet();
    if (objs != null)
    {
      objSet.totalCount = objs.size();
      objSet.pageSize = pageSize;
      objSet.pageIndex = pageIndex;
      for (int i = 0; i < objs.size(); i++) {
        if (((pageIndex - 1) * pageSize <= i) && (i < pageIndex * pageSize)) {
          objSet.add(objs.get(i));
        }
      }
    }
    return objSet;
  }
}

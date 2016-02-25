package gnnt.MEBS.vendue.util;

import java.util.Vector;

public class ObjSet
  extends Vector
{
  private static final long serialVersionUID = -139301892051258885L;
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
  
  public static ObjSet getInstance(Object[] paramArrayOfObject, int paramInt1, int paramInt2, int paramInt3)
  {
    ObjSet localObjSet = new ObjSet();
    if (paramArrayOfObject != null)
    {
      for (int i = 0; i < paramArrayOfObject.length; i++) {
        localObjSet.add(paramArrayOfObject[i]);
      }
      localObjSet.totalCount = paramInt1;
      localObjSet.pageSize = paramInt2;
      localObjSet.pageIndex = paramInt3;
    }
    return localObjSet;
  }
}

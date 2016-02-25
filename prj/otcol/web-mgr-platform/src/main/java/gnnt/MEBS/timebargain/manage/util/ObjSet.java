package gnnt.MEBS.timebargain.manage.util;

import java.util.List;
import java.util.Map;
import java.util.Vector;

public class ObjSet
  extends Vector
{
  private static final long serialVersionUID = 1690197650654049819L;
  private int totalCount;
  private int pageSize = 1;
  private int pageCount = 1;
  private int pageIndex = 1;
  private List list;
  private Map<String, String> map;
  
  public Map<String, String> getMap()
  {
    return this.map;
  }
  
  public void setMap(Map<String, String> paramMap)
  {
    this.map = paramMap;
  }
  
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
  
  public List getList()
  {
    return this.list;
  }
  
  public static ObjSet getInstance(List paramList, int paramInt1, int paramInt2, int paramInt3)
  {
    ObjSet localObjSet = new ObjSet();
    localObjSet.list = paramList;
    localObjSet.totalCount = paramInt1;
    localObjSet.pageSize = paramInt2;
    localObjSet.pageIndex = paramInt3;
    return localObjSet;
  }
}

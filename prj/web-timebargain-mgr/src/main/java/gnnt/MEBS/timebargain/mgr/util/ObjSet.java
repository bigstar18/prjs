package gnnt.MEBS.timebargain.mgr.util;

import java.util.List;
import java.util.Map;
import java.util.Vector;

public class ObjSet extends Vector
{
  private static final long serialVersionUID = 1690197650654049819L;
  private int totalCount;
  private int pageSize;
  private int pageCount;
  private int pageIndex;
  private List list;
  private Map<String, String> map;

  private ObjSet()
  {
    this.pageSize = 1;
    this.pageCount = 1;
    this.pageIndex = 1;
  }

  public Map<String, String> getMap()
  {
    return this.map;
  }

  public void setMap(Map<String, String> map)
  {
    this.map = map;
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
    if (this.pageSize > 0)
      if (this.totalCount % this.pageSize != 0)
        this.pageCount = (this.totalCount / this.pageSize + 1);
      else
        this.pageCount = (this.totalCount / this.pageSize);
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

  public static ObjSet getInstance(List list, int totalCount, int pageSize, int pageIndex)
  {
    ObjSet objSet = new ObjSet();

    objSet.list = list;
    objSet.totalCount = totalCount;
    objSet.pageSize = pageSize;
    objSet.pageIndex = pageIndex;
    return objSet;
  }
}
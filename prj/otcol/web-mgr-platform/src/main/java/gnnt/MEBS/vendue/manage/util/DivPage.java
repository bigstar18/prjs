package gnnt.MEBS.vendue.manage.util;

public class DivPage
{
  public int totalCount;
  public int pageSize;
  public int pageIndex;
  public int pageCount;
  
  public void setTotalCount(int paramInt)
  {
    this.totalCount = paramInt;
  }
  
  public int getTotalCount()
  {
    return this.totalCount;
  }
  
  public void setPageSize(int paramInt)
  {
    this.pageSize = paramInt;
  }
  
  public int getPageSize()
  {
    return this.pageSize;
  }
  
  public void setPageIndex(int paramInt)
  {
    this.pageIndex = paramInt;
  }
  
  public int getPageIndex()
  {
    return this.pageIndex;
  }
  
  public void setPageCount(int paramInt)
  {
    this.pageCount = paramInt;
  }
  
  public int getPageCount()
  {
    return this.pageCount;
  }
}

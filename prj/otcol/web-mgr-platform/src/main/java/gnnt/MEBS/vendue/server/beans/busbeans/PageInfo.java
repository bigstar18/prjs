package gnnt.MEBS.vendue.server.beans.busbeans;

import java.util.List;

public class PageInfo
{
  private Integer pageIndex;
  private Integer pageCount;
  private Integer pageSize;
  private Long recordCount;
  private List resultList;
  
  public int getResultSize()
  {
    if (this.resultList == null) {
      return 0;
    }
    return this.resultList.size();
  }
  
  public Object loadFirstResult()
  {
    if ((this.resultList == null) || (this.resultList.size() == 0)) {
      return null;
    }
    return this.resultList.get(0);
  }
  
  public List getResultList()
  {
    return this.resultList;
  }
  
  public void setResultList(List paramList)
  {
    this.resultList = paramList;
  }
  
  public PageInfo()
  {
    this(null, null);
  }
  
  public PageInfo(Integer paramInteger1, Integer paramInteger2)
  {
    if (paramInteger1 == null) {
      this.pageIndex = new Integer(0);
    } else {
      this.pageIndex = paramInteger1;
    }
    if (paramInteger2 == null) {
      this.pageSize = new Integer(2147483647);
    } else {
      this.pageSize = paramInteger2;
    }
  }
  
  public Long getRecordCount()
  {
    return this.recordCount;
  }
  
  public void setRecordCount(Long paramLong)
  {
    this.recordCount = paramLong;
  }
  
  public Integer getPageCount()
  {
    return this.pageCount;
  }
  
  public void setPageCount(Integer paramInteger)
  {
    this.pageCount = paramInteger;
  }
  
  public Integer getPageIndex()
  {
    return this.pageIndex;
  }
  
  public void setPageIndex(Integer paramInteger)
  {
    this.pageIndex = paramInteger;
  }
  
  public Integer getPageSize()
  {
    return this.pageSize;
  }
  
  public void setPageSize(Integer paramInteger)
  {
    this.pageSize = paramInteger;
  }
  
  public String toString()
  {
    return "PageIndex: " + this.pageIndex + "\n" + "PageCount: " + this.pageCount + "\n" + "PageSize: " + this.pageSize + "\n" + "RecordCount: " + this.recordCount;
  }
}

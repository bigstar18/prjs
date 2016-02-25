package gnnt.MEBS.common.util.query;

import java.util.ArrayList;
import java.util.List;

public class PageInfo
{
  private int pageSize;
  private int pageNo;
  private int totalPages;
  private int totalRecords;
  private List orderFields = new ArrayList();
  
  public PageInfo() {}
  
  public PageInfo(int paramInt1, int paramInt2, String paramString, boolean paramBoolean)
  {
    this.pageNo = paramInt1;
    this.pageSize = paramInt2;
    addOrderField(paramString, paramBoolean);
  }
  
  public void addOrderField(String paramString, boolean paramBoolean)
  {
    this.orderFields.add(new OrderField(paramString, paramBoolean));
  }
  
  public List getOrderFields()
  {
    return this.orderFields;
  }
  
  public void setOrderFields(List paramList)
  {
    this.orderFields = paramList;
  }
  
  public int getPageNo()
  {
    return this.pageNo;
  }
  
  public void setPageNo(int paramInt)
  {
    this.pageNo = paramInt;
  }
  
  public int getPageSize()
  {
    return this.pageSize;
  }
  
  public void setPageSize(int paramInt)
  {
    this.pageSize = paramInt;
  }
  
  public int getTotalPages()
  {
    return this.totalPages;
  }
  
  public void setTotalPages(int paramInt)
  {
    this.totalPages = paramInt;
  }
  
  public int getTotalRecords()
  {
    return this.totalRecords;
  }
  
  public void setTotalRecords(int paramInt)
  {
    this.totalRecords = paramInt;
  }
}

package gnnt.MEBS.base.query.hibernate;

import java.util.ArrayList;
import java.util.List;

public class PageInfo
{
  private int pageSize = 100000000;
  private int pageNo = 1;
  private int totalPages;
  private int totalRecords;
  private List orderFields = new ArrayList();
  private String orderString;
  private int startCount;
  
  public PageInfo() {}
  
  public PageInfo(int pageNo, int pageSize, String field, boolean isOrderDesc)
  {
    this.pageNo = pageNo;
    this.pageSize = pageSize;
    addOrderField(field, isOrderDesc);
  }
  
  public void addOrderField(String field, boolean isOrderDesc)
  {
    this.orderFields.add(new OrderField(field, isOrderDesc));
  }
  
  public List getOrderFields()
  {
    return this.orderFields;
  }
  
  public void setOrderFields(List orderFields)
  {
    this.orderFields = orderFields;
  }
  
  public int getPageNo()
  {
    return this.pageNo;
  }
  
  public void setPageNo(int pageNo)
  {
    this.pageNo = pageNo;
  }
  
  public int getPageSize()
  {
    return this.pageSize;
  }
  
  public void setPageSize(int pageSize)
  {
    this.pageSize = pageSize;
  }
  
  public int getTotalPages()
  {
    return this.totalPages;
  }
  
  public void setTotalPages(int totalPages)
  {
    this.totalPages = totalPages;
  }
  
  public int getTotalRecords()
  {
    return this.totalRecords;
  }
  
  public void setTotalRecords(int totalRecords)
  {
    this.totalRecords = totalRecords;
  }
  
  public int getStartCount()
  {
    if (this.startCount == 0) {
      this.startCount = ((getPageNo() - 1) * getPageSize());
    }
    return this.startCount;
  }
  
  public void setStartCount(int startCount)
  {
    this.startCount = startCount;
  }
  
  public String getOrderString()
  {
    return this.orderString;
  }
  
  public void setOrderString(String orderString)
  {
    this.orderString = orderString;
  }
}

package gnnt.MEBS.timebargain.mgr.util;

import java.util.ArrayList;
import java.util.List;

public class PageInfo
{
  private int pageSize;
  private int pageNo;
  private int totalPages;
  private int totalRecords;
  private List orderFields = new ArrayList();

  public PageInfo()
  {
  }

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
}
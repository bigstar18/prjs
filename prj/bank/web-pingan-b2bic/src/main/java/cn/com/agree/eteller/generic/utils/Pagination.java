package cn.com.agree.eteller.generic.utils;

import java.io.Serializable;
import java.util.List;

public class Pagination
  implements Serializable
{
  private static final long serialVersionUID = -2894495306691804752L;
  public static final Integer SYS_PERPAGE_RECORDS;
  public static final Integer SYS_VISIBLE_PAGE;
  private Integer curPage;
  private Integer maxPage;
  private Integer visiblePage;
  private Integer perPageRecords;
  private Integer allRecords;
  private Integer firstRecord;
  private String orderField;
  private String orderDirection;
  
  static
  {
    ConfigUtil conf = new ConfigUtil("page.properties");
    SYS_PERPAGE_RECORDS = Integer.valueOf(conf.getInt("perPageRecords"));
    SYS_VISIBLE_PAGE = Integer.valueOf(conf.getInt("visiblePage"));
  }
  
  public Pagination()
  {
    this.curPage = Integer.valueOf(1);
    this.visiblePage = SYS_VISIBLE_PAGE;
    this.perPageRecords = SYS_PERPAGE_RECORDS;
    this.firstRecord = Integer.valueOf((this.curPage.intValue() - 1) * this.perPageRecords.intValue());
  }
  
  public Pagination(Integer curPage, Integer perPageRecords)
  {
    this();
    this.curPage = curPage;
    this.perPageRecords = perPageRecords;
    this.firstRecord = Integer.valueOf((curPage.intValue() - 1) * perPageRecords.intValue());
  }
  
  public Pagination(Pagination page)
  {
    this.curPage = page.getCurPage();
    this.maxPage = page.getMaxPage();
    this.visiblePage = page.getVisiblePage();
    this.perPageRecords = page.getPerPageRecords();
    this.allRecords = page.getAllRecords();
    this.firstRecord = page.getFirstRecord();
  }
  
  public static <T> List<T> splitPageWithAllRecords(List<T> list, Pagination page)
  {
    page.setAllRecords(Integer.valueOf(list.size()));
    Integer begin = page.getFirstRecord();
    Integer end = Integer.valueOf(page.getCurPage().intValue() * page.getPerPageRecords().intValue());
    list = list.subList(begin.intValue(), end.intValue() > list.size() ? list.size() : end.intValue());
    return list;
  }
  
  public Integer getCurPage()
  {
    return Integer.valueOf(this.curPage == null ? 0 : this.curPage.intValue());
  }
  
  public void setCurPage(Integer curPage)
  {
    this.curPage = curPage;
  }
  
  public Integer getMaxPage()
  {
    return Integer.valueOf(this.maxPage == null ? 0 : this.maxPage.intValue());
  }
  
  public void setMaxPage(Integer maxPage)
  {
    this.maxPage = maxPage;
  }
  
  public Integer getVisiblePage()
  {
    return this.visiblePage;
  }
  
  public void setVisiblePage(Integer visiblePage)
  {
    this.visiblePage = visiblePage;
  }
  
  public Integer getPerPageRecords()
  {
    return this.perPageRecords;
  }
  
  public void setPerPageRecords(Integer perPageRecords)
  {
    this.perPageRecords = perPageRecords;
  }
  
  public Integer getAllRecords()
  {
    return Integer.valueOf(this.allRecords == null ? 0 : this.allRecords.intValue());
  }
  
  public void setAllRecords(Integer allRecords)
  {
    if ((allRecords == null) || (allRecords.intValue() == 0))
    {
      this.allRecords = Integer.valueOf(0);
      return;
    }
    this.allRecords = allRecords;
    this.maxPage = Integer.valueOf((allRecords.intValue() - 1) / this.perPageRecords.intValue() + 1);
    this.firstRecord = Integer.valueOf((this.curPage.intValue() - 1) * this.perPageRecords.intValue());
  }
  
  public Integer getFirstRecord()
  {
    return Integer.valueOf(this.firstRecord == null ? 0 : this.firstRecord.intValue());
  }
  
  public void setFirstRecord(Integer firstRecord)
  {
    this.firstRecord = firstRecord;
  }
  
  public String getOrderField()
  {
    return this.orderField;
  }
  
  public void setOrderField(String orderField)
  {
    this.orderField = orderField;
  }
  
  public String getOrderDirection()
  {
    return this.orderDirection;
  }
  
  public void setOrderDirection(String orderDirection)
  {
    this.orderDirection = orderDirection;
  }
}

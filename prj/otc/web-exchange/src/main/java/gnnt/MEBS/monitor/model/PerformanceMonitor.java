package gnnt.MEBS.monitor.model;

import gnnt.MEBS.base.model.Clone;
import java.util.Date;

public class PerformanceMonitor
  extends Clone
{
  private static final long serialVersionUID = -6046973216262963731L;
  private Integer id;
  private String type;
  private Date dateTime;
  private Integer num;
  private String categoryType;
  
  public String getCategoryType()
  {
    return this.categoryType;
  }
  
  public void setCategoryType(String categoryType)
  {
    this.categoryType = categoryType;
  }
  
  public String getType()
  {
    return this.type;
  }
  
  public void setType(String type)
  {
    this.type = type;
  }
  
  public Date getDateTime()
  {
    return this.dateTime;
  }
  
  public void setDateTime(Date dateTime)
  {
    this.dateTime = dateTime;
  }
  
  public Integer getNum()
  {
    return this.num;
  }
  
  public void setNum(Integer num)
  {
    this.num = num;
  }
  
  public Integer getId()
  {
    return this.id;
  }
  
  public void setId(Integer id)
  {
    this.id = id;
  }
  
  public void setPrimary(String primary) {}
}

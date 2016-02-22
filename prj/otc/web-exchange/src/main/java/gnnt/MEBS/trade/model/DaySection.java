package gnnt.MEBS.trade.model;

import gnnt.MEBS.base.model.Clone;

public class DaySection
  extends Clone
{
  private Integer weekDay;
  private Long sectionId;
  private Integer status;
  
  public Integer getWeekDay()
  {
    return this.weekDay;
  }
  
  public void setWeekDay(Integer weekDay)
  {
    this.weekDay = weekDay;
  }
  
  public Long getSectionId()
  {
    return this.sectionId;
  }
  
  public void setSectionId(Long sectionId)
  {
    this.sectionId = sectionId;
  }
  
  public Integer getStatus()
  {
    return this.status;
  }
  
  public void setStatus(Integer status)
  {
    this.status = status;
  }
  
  public String getId()
  {
    return null;
  }
  
  public void setPrimary(String primary) {}
}

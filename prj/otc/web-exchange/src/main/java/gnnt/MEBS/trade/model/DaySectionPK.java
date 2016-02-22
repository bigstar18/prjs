package gnnt.MEBS.trade.model;

import java.io.Serializable;

public class DaySectionPK
  implements Serializable
{
  private int weekDay;
  private Long sectionId;
  
  public Integer getWeekDay()
  {
    return Integer.valueOf(this.weekDay);
  }
  
  public void setWeekDay(int weekDay)
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
  
  public int hashCode()
  {
    int prime = 31;
    int result = 1;
    result = 31 * result + (
      this.sectionId == null ? 0 : this.sectionId.hashCode());
    result = 31 * result + this.weekDay;
    return result;
  }
  
  public boolean equals(Object obj)
  {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    DaySectionPK other = (DaySectionPK)obj;
    if (this.sectionId == null)
    {
      if (other.sectionId != null) {
        return false;
      }
    }
    else if (!this.sectionId.equals(other.sectionId)) {
      return false;
    }
    if (this.weekDay != other.weekDay) {
      return false;
    }
    return true;
  }
}

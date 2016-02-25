package gnnt.MEBS.timebargain.manage.model;

import java.io.Serializable;

public class Delay
  extends BaseObject
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  private Long sectionID;
  private String name;
  private String startTime;
  private String endTime;
  private String startMiddleTime;
  private String endMiddleTime;
  private Short type;
  private Short status;
  private String modifyTime;
  
  public String getStartMiddleTime()
  {
    return this.startMiddleTime;
  }
  
  public void setStartMiddleTime(String paramString)
  {
    this.startMiddleTime = paramString;
  }
  
  public String getEndMiddleTime()
  {
    return this.endMiddleTime;
  }
  
  public void setEndMiddleTime(String paramString)
  {
    this.endMiddleTime = paramString;
  }
  
  public String getEndTime()
  {
    return this.endTime;
  }
  
  public void setEndTime(String paramString)
  {
    this.endTime = paramString;
  }
  
  public String getModifyTime()
  {
    return this.modifyTime;
  }
  
  public void setModifyTime(String paramString)
  {
    this.modifyTime = paramString;
  }
  
  public String getName()
  {
    return this.name;
  }
  
  public void setName(String paramString)
  {
    this.name = paramString;
  }
  
  public Long getSectionID()
  {
    return this.sectionID;
  }
  
  public void setSectionID(Long paramLong)
  {
    this.sectionID = paramLong;
  }
  
  public String getStartTime()
  {
    return this.startTime;
  }
  
  public void setStartTime(String paramString)
  {
    this.startTime = paramString;
  }
  
  public Short getStatus()
  {
    return this.status;
  }
  
  public void setStatus(Short paramShort)
  {
    this.status = paramShort;
  }
  
  public Short getType()
  {
    return this.type;
  }
  
  public void setType(Short paramShort)
  {
    this.type = paramShort;
  }
  
  public boolean equals(Object paramObject)
  {
    return false;
  }
  
  public int hashCode()
  {
    return 0;
  }
  
  public String toString()
  {
    return null;
  }
}

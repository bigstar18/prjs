package gnnt.MEBS.timebargain.manage.model;

import java.io.Serializable;

public class NotTradeDay
  extends BaseObject
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  private Short id;
  private String[] week;
  private String day;
  private String modifyTime;
  private String crud = "";
  
  public String getCrud()
  {
    return this.crud;
  }
  
  public void setCrud(String paramString)
  {
    this.crud = paramString;
  }
  
  public String getDay()
  {
    return this.day;
  }
  
  public void setDay(String paramString)
  {
    this.day = paramString;
  }
  
  public Short getId()
  {
    return this.id;
  }
  
  public void setId(Short paramShort)
  {
    this.id = paramShort;
  }
  
  public String getModifyTime()
  {
    return this.modifyTime;
  }
  
  public void setModifyTime(String paramString)
  {
    this.modifyTime = paramString;
  }
  
  public String toString()
  {
    return null;
  }
  
  public boolean equals(Object paramObject)
  {
    return false;
  }
  
  public int hashCode()
  {
    return 0;
  }
  
  public String[] getWeek()
  {
    return this.week;
  }
  
  public void setWeek(String[] paramArrayOfString)
  {
    this.week = paramArrayOfString;
  }
}

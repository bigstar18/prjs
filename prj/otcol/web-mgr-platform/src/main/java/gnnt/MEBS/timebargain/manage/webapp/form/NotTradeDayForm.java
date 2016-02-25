package gnnt.MEBS.timebargain.manage.webapp.form;

import java.io.Serializable;

public class NotTradeDayForm
  extends BaseForm
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  private String id;
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
  
  public String getId()
  {
    return this.id;
  }
  
  public void setId(String paramString)
  {
    this.id = paramString;
  }
  
  public String getModifyTime()
  {
    return this.modifyTime;
  }
  
  public void setModifyTime(String paramString)
  {
    this.modifyTime = paramString;
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

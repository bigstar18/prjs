package gnnt.MEBS.timebargain.manage.webapp.form;

import java.io.Serializable;

public class DelayForm
  extends BaseForm
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  private String sectionID;
  private String name;
  private String startTime;
  private String endTime;
  private String startMiddleTime;
  private String endMiddleTime;
  private String kindid;
  private String type;
  private String status;
  private String modifyTime;
  private String crud = "";
  private String recoverTime;
  
  public String getRecoverTime()
  {
    return this.recoverTime;
  }
  
  public void setRecoverTime(String paramString)
  {
    this.recoverTime = paramString;
  }
  
  public String getCrud()
  {
    return this.crud;
  }
  
  public void setCrud(String paramString)
  {
    this.crud = paramString;
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
  
  public String getSectionID()
  {
    return this.sectionID;
  }
  
  public void setSectionID(String paramString)
  {
    this.sectionID = paramString;
  }
  
  public String getStartTime()
  {
    return this.startTime;
  }
  
  public void setStartTime(String paramString)
  {
    this.startTime = paramString;
  }
  
  public String getStatus()
  {
    return this.status;
  }
  
  public void setStatus(String paramString)
  {
    this.status = paramString;
  }
  
  public String getType()
  {
    return this.type;
  }
  
  public void setType(String paramString)
  {
    this.type = paramString;
  }
  
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
  
  public String getKindid()
  {
    return this.kindid;
  }
  
  public void setKindid(String paramString)
  {
    this.kindid = paramString;
  }
}

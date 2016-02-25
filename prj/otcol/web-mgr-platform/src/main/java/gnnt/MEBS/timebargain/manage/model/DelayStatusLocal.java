package gnnt.MEBS.timebargain.manage.model;

import java.io.Serializable;

public class DelayStatusLocal
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  private String tradeDate;
  private String status;
  private Long sectionID;
  private String note;
  private String recoverTime;
  
  public String getNote()
  {
    return this.note;
  }
  
  public void setNote(String paramString)
  {
    this.note = paramString;
  }
  
  public String getRecoverTime()
  {
    return this.recoverTime;
  }
  
  public void setRecoverTime(String paramString)
  {
    this.recoverTime = paramString;
  }
  
  public Long getSectionID()
  {
    return this.sectionID;
  }
  
  public void setSectionID(Long paramLong)
  {
    this.sectionID = paramLong;
  }
  
  public String getStatus()
  {
    return this.status;
  }
  
  public void setStatus(String paramString)
  {
    this.status = paramString;
  }
  
  public String getTradeDate()
  {
    return this.tradeDate;
  }
  
  public void setTradeDate(String paramString)
  {
    this.tradeDate = paramString;
  }
}

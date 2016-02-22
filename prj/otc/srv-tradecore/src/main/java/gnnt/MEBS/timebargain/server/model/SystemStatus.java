package gnnt.MEBS.timebargain.server.model;

import java.io.Serializable;
import java.util.Date;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class SystemStatus
  implements Serializable
{
  private static final long serialVersionUID = 3690197650654049113L;
  private Date clearDate;
  private Date tradeDate;
  private Date nextTradeDate;
  private int status;
  private Integer sectionID;
  private String note;
  private String recoverTime;
  private char pauseType;
  
  public SystemStatus() {}
  
  public SystemStatus(int status)
  {
    this.status = status;
  }
  
  public String toString()
  {
    return ToStringBuilder.reflectionToString(this, 
      ToStringStyle.MULTI_LINE_STYLE);
  }
  
  public String getNote()
  {
    return this.note;
  }
  
  public void setNote(String note)
  {
    this.note = note;
  }
  
  public Integer getSectionID()
  {
    return this.sectionID;
  }
  
  public void setSectionID(Integer sectionID)
  {
    this.sectionID = sectionID;
  }
  
  public int getStatus()
  {
    return this.status;
  }
  
  public void setStatus(int status)
  {
    this.status = status;
  }
  
  public Date getTradeDate()
  {
    return this.tradeDate;
  }
  
  public void setTradeDate(Date tradeDate)
  {
    this.tradeDate = tradeDate;
  }
  
  public Date getNextTradeDate()
  {
    return this.nextTradeDate;
  }
  
  public void setNextTradeDate(Date nextTradeDate)
  {
    this.nextTradeDate = nextTradeDate;
  }
  
  public Date getClearDate()
  {
    return this.clearDate;
  }
  
  public void setClearDate(Date tradeDate)
  {
    this.clearDate = tradeDate;
  }
  
  public String getRecoverTime()
  {
    return this.recoverTime;
  }
  
  public void setRecoverTime(String recoverTime)
  {
    this.recoverTime = recoverTime;
  }
  
  public char getPauseType()
  {
    return this.pauseType;
  }
  
  public void setPauseType(char pauseType)
  {
    this.pauseType = pauseType;
  }
}

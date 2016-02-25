package gnnt.MEBS.zcjs.model;

import gnnt.MEBS.base.model.Clone;
import java.util.Date;

public class Broadcast
  extends Clone
{
  private long broadcastId;
  private String broadcastTitle;
  private String broadcastSender;
  private String broadcastContent;
  private Date broadcastSendTime;
  private Date broadcastCreateTime;
  private String broadcastFirmId;
  
  public long getBroadcastId()
  {
    return this.broadcastId;
  }
  
  public void setBroadcastId(long paramLong)
  {
    this.broadcastId = paramLong;
  }
  
  public String getBroadcastTitle()
  {
    return this.broadcastTitle;
  }
  
  public void setBroadcastTitle(String paramString)
  {
    this.broadcastTitle = paramString;
  }
  
  public String getBroadcastSender()
  {
    return this.broadcastSender;
  }
  
  public void setBroadcastSender(String paramString)
  {
    this.broadcastSender = paramString;
  }
  
  public String getBroadcastContent()
  {
    return this.broadcastContent;
  }
  
  public void setBroadcastContent(String paramString)
  {
    this.broadcastContent = paramString;
  }
  
  public Date getBroadcastSendTime()
  {
    return this.broadcastSendTime;
  }
  
  public void setBroadcastSendTime(Date paramDate)
  {
    this.broadcastSendTime = paramDate;
  }
  
  public Date getBroadcastCreateTime()
  {
    return this.broadcastCreateTime;
  }
  
  public void setBroadcastCreateTime(Date paramDate)
  {
    this.broadcastCreateTime = paramDate;
  }
  
  public String getBroadcastFirmId()
  {
    return this.broadcastFirmId;
  }
  
  public void setBroadcastFirmId(String paramString)
  {
    this.broadcastFirmId = paramString;
  }
}

package gnnt.MEBS.member.broker.model;

import java.util.Date;

public class BrokerLog
  extends Cloneable
{
  private long logId;
  private Date createTime;
  private String brokerId;
  private String operatorId;
  private int type;
  private String action;
  
  public long getLogId()
  {
    return this.logId;
  }
  
  public void setLogId(long paramLong)
  {
    this.logId = paramLong;
  }
  
  public Date getCreateTime()
  {
    return this.createTime;
  }
  
  public void setCreateTime(Date paramDate)
  {
    this.createTime = paramDate;
  }
  
  public String getBrokerId()
  {
    return this.brokerId;
  }
  
  public void setBrokerId(String paramString)
  {
    this.brokerId = paramString;
  }
  
  public String getOperatorId()
  {
    return this.operatorId;
  }
  
  public void setOperatorId(String paramString)
  {
    this.operatorId = paramString;
  }
  
  public int getType()
  {
    return this.type;
  }
  
  public void setType(int paramInt)
  {
    this.type = paramInt;
  }
  
  public String getAction()
  {
    return this.action;
  }
  
  public void setAction(String paramString)
  {
    this.action = paramString;
  }
}

package gnnt.MEBS.delivery.model;

import gnnt.MEBS.base.model.Clone;
import java.util.Date;

public class OperateLog
  extends Clone
{
  private long id;
  private String userId;
  private String billId;
  private Date operatetime = new Date();
  private String operation;
  private String content;
  private long popedom;
  
  public String getOperation()
  {
    return this.operation;
  }
  
  public void setOperation(String paramString)
  {
    this.operation = paramString;
  }
  
  public String getContent()
  {
    return this.content;
  }
  
  public void setContent(String paramString)
  {
    this.content = paramString;
  }
  
  public long getId()
  {
    return this.id;
  }
  
  public void setId(long paramLong)
  {
    this.id = paramLong;
  }
  
  public Date getOperatetime()
  {
    return this.operatetime;
  }
  
  public void setOperatetime(Date paramDate)
  {
    this.operatetime = paramDate;
  }
  
  public long getPopedom()
  {
    return this.popedom;
  }
  
  public void setPopedom(long paramLong)
  {
    this.popedom = paramLong;
  }
  
  public String getUserId()
  {
    return this.userId;
  }
  
  public void setUserId(String paramString)
  {
    this.userId = paramString;
  }
  
  public String getBillId()
  {
    return this.billId;
  }
  
  public void setBillId(String paramString)
  {
    this.billId = paramString;
  }
}

package gnnt.MEBS.integrated.front.model;

import gnnt.MEBS.common.front.model.StandardModel;
import gnnt.MEBS.common.front.model.StandardModel.PrimaryInfo;
import gnnt.MEBS.common.front.model.translate.ClassDiscription;
import java.util.Date;

public class Message
  extends StandardModel
{
  private static final long serialVersionUID = -5666712408996819608L;
  @ClassDiscription(name="消息ID", description="")
  private Long messageId;
  @ClassDiscription(name="消息", description="")
  private String message;
  @ClassDiscription(name="接收人类型", description="接收人类型：1 在线交易员 2 在线管理员 3 在线用户 4 指定交易员 5 指定管理员")
  private Integer recieverType;
  @ClassDiscription(name="接收交易员ID", description="")
  private String traderID;
  @ClassDiscription(name="发布时间", description="")
  private Date createTime;
  @ClassDiscription(name="管理员ID", description="")
  private String userId;
  
  public Long getMessageId()
  {
    return this.messageId;
  }
  
  public void setMessageId(Long paramLong)
  {
    this.messageId = paramLong;
  }
  
  public String getMessage()
  {
    return this.message;
  }
  
  public void setMessage(String paramString)
  {
    this.message = paramString;
  }
  
  public Integer getRecieverType()
  {
    return this.recieverType;
  }
  
  public void setRecieverType(Integer paramInteger)
  {
    this.recieverType = paramInteger;
  }
  
  public String getTraderID()
  {
    return this.traderID;
  }
  
  public void setTraderID(String paramString)
  {
    this.traderID = paramString;
  }
  
  public Date getCreateTime()
  {
    return this.createTime;
  }
  
  public void setCreateTime(Date paramDate)
  {
    this.createTime = paramDate;
  }
  
  public String getUserId()
  {
    return this.userId;
  }
  
  public void setUserId(String paramString)
  {
    this.userId = paramString;
  }
  
  public StandardModel.PrimaryInfo fetchPKey()
  {
    return new StandardModel.PrimaryInfo("messageId", this.messageId);
  }
}

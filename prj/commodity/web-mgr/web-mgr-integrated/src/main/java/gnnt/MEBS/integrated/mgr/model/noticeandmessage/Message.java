package gnnt.MEBS.integrated.mgr.model.noticeandmessage;

import gnnt.MEBS.common.mgr.model.StandardModel;
import gnnt.MEBS.common.mgr.model.StandardModel.PrimaryInfo;
import gnnt.MEBS.common.mgr.model.translate.ClassDiscription;
import java.util.Date;

public class Message
  extends StandardModel
{
  private static final long serialVersionUID = 3564014360250512014L;
  @ClassDiscription(name="消息ID", description="")
  private Integer messageId;
  @ClassDiscription(name="消息", description="")
  private String message;
  @ClassDiscription(name="创建时间", description="")
  private Date createTime;
  @ClassDiscription(name="管理员ID", description="")
  private String userId;
  @ClassDiscription(name="接收人类型", description="1 在线交易员 2 在线管理员 3 在线用户 4 指定交易员 5 指定管理员")
  private Integer recieverType;
  @ClassDiscription(name="接收人", description="")
  private String traderId;
  
  public String getTraderId()
  {
    return this.traderId;
  }
  
  public void setTraderId(String paramString)
  {
    this.traderId = paramString;
  }
  
  public Integer getRecieverType()
  {
    return this.recieverType;
  }
  
  public void setRecieverType(Integer paramInteger)
  {
    this.recieverType = paramInteger;
  }
  
  public Integer getMessageId()
  {
    return this.messageId;
  }
  
  public void setMessageId(Integer paramInteger)
  {
    this.messageId = paramInteger;
  }
  
  public String getMessage()
  {
    return this.message;
  }
  
  public void setMessage(String paramString)
  {
    this.message = paramString;
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

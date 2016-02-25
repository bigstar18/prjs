package gnnt.MEBS.timebargain.manage.model;

import java.io.Serializable;
import java.util.Date;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class Broadcast
  extends BaseObject
  implements Serializable
{
  private static final long serialVersionUID = 3690197650654049817L;
  private Long id;
  private String title;
  private String content;
  private Short status;
  private String customerID;
  private Date createTime;
  private String author;
  private String sendTime;
  private String type = "";
  
  public String getType()
  {
    return this.type;
  }
  
  public void setType(String paramString)
  {
    this.type = paramString;
  }
  
  public boolean equals(Object paramObject)
  {
    if (this == paramObject) {
      return true;
    }
    if (!(paramObject instanceof Broadcast)) {
      return false;
    }
    Broadcast localBroadcast = (Broadcast)paramObject;
    return this.id != null ? this.id.equals(localBroadcast.id) : localBroadcast.id == null;
  }
  
  public int hashCode()
  {
    return this.id != null ? this.id.hashCode() : 0;
  }
  
  public String toString()
  {
    return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
  }
  
  public String getContent()
  {
    return this.content;
  }
  
  public void setContent(String paramString)
  {
    this.content = paramString;
  }
  
  public Date getCreateTime()
  {
    return this.createTime;
  }
  
  public void setCreateTime(Date paramDate)
  {
    this.createTime = paramDate;
  }
  
  public String getCustomerID()
  {
    return this.customerID;
  }
  
  public void setCustomerID(String paramString)
  {
    this.customerID = paramString;
  }
  
  public Long getId()
  {
    return this.id;
  }
  
  public void setId(Long paramLong)
  {
    this.id = paramLong;
  }
  
  public Short getStatus()
  {
    return this.status;
  }
  
  public void setStatus(Short paramShort)
  {
    this.status = paramShort;
  }
  
  public String getTitle()
  {
    return this.title;
  }
  
  public void setTitle(String paramString)
  {
    this.title = paramString;
  }
  
  public String getAuthor()
  {
    return this.author;
  }
  
  public void setAuthor(String paramString)
  {
    this.author = paramString;
  }
  
  public String getSendTime()
  {
    return this.sendTime;
  }
  
  public void setSendTime(String paramString)
  {
    this.sendTime = paramString;
  }
}

package gnnt.MEBS.timebargain.tradeweb.model;

import java.io.Serializable;
import java.util.Date;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class Broadcast
  extends BaseObject
  implements Serializable
{
  private static final long serialVersionUID = 3690197650654049827L;
  private Long id;
  private String title;
  private String content;
  private Short status;
  private String firmID;
  private Date createTime;
  private Date sendTime;
  
  public boolean equals(Object o)
  {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Broadcast)) {
      return false;
    }
    Broadcast m = (Broadcast)o;
    
    return this.id != null ? this.id.equals(m.id) : m.id == null;
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
  
  public void setContent(String content)
  {
    this.content = content;
  }
  
  public Date getCreateTime()
  {
    return this.createTime;
  }
  
  public void setCreateTime(Date createTime)
  {
    this.createTime = createTime;
  }
  
  public String getFirmID()
  {
    return this.firmID;
  }
  
  public void setFirmID(String firmID)
  {
    this.firmID = firmID;
  }
  
  public Long getId()
  {
    return this.id;
  }
  
  public void setId(Long id)
  {
    this.id = id;
  }
  
  public Short getStatus()
  {
    return this.status;
  }
  
  public void setStatus(Short status)
  {
    this.status = status;
  }
  
  public String getTitle()
  {
    return this.title;
  }
  
  public void setTitle(String title)
  {
    this.title = title;
  }
  
  public Date getSendTime()
  {
    return this.sendTime;
  }
  
  public void setSendTime(Date sendTime)
  {
    this.sendTime = sendTime;
  }
}

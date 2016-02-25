package gnnt.MEBS.vendue.server.beans.dtobeans;

import java.sql.Timestamp;

public class Broadcast
  implements Comparable, Cloneable
{
  private Long id;
  private String title;
  private String author;
  private String content;
  private String type;
  private Timestamp sendtime;
  private Timestamp endtime;
  private Timestamp createtime;
  private Timestamp updatetime;
  private Timestamp activeTime;
  private StringBuffer xmlStringBuffer;
  
  public Timestamp getActiveTime()
  {
    return this.activeTime;
  }
  
  public void setActiveTime(Timestamp paramTimestamp)
  {
    this.activeTime = paramTimestamp;
  }
  
  public String getAuthor()
  {
    return this.author;
  }
  
  public void setAuthor(String paramString)
  {
    this.author = paramString;
  }
  
  public String getContent()
  {
    return this.content;
  }
  
  public void setContent(String paramString)
  {
    this.content = paramString;
  }
  
  public Long getId()
  {
    return this.id;
  }
  
  public void setId(Long paramLong)
  {
    this.id = paramLong;
  }
  
  public String getTitle()
  {
    return this.title;
  }
  
  public void setTitle(String paramString)
  {
    this.title = paramString;
  }
  
  public String getType()
  {
    return this.type;
  }
  
  public void setType(String paramString)
  {
    this.type = paramString;
  }
  
  public StringBuffer getXmlStringBuffer()
  {
    return this.xmlStringBuffer;
  }
  
  public void setXmlStringBuffer(StringBuffer paramStringBuffer)
  {
    this.xmlStringBuffer = paramStringBuffer;
  }
  
  public Timestamp getCreatetime()
  {
    return this.createtime;
  }
  
  public void setCreatetime(Timestamp paramTimestamp)
  {
    this.createtime = paramTimestamp;
  }
  
  public Timestamp getSendtime()
  {
    return this.sendtime;
  }
  
  public void setSendtime(Timestamp paramTimestamp)
  {
    this.sendtime = paramTimestamp;
  }
  
  public Timestamp getUpdatetime()
  {
    return this.updatetime;
  }
  
  public void setUpdatetime(Timestamp paramTimestamp)
  {
    this.updatetime = paramTimestamp;
  }
  
  public Timestamp getEndtime()
  {
    return this.endtime;
  }
  
  public void setEndtime(Timestamp paramTimestamp)
  {
    this.endtime = paramTimestamp;
  }
  
  public int compareTo(Object paramObject)
  {
    if ((paramObject instanceof Broadcast))
    {
      Broadcast localBroadcast = (Broadcast)paramObject;
      return this.updatetime.compareTo(localBroadcast.updatetime);
    }
    return 1;
  }
  
  public Object clone()
    throws CloneNotSupportedException
  {
    return super.clone();
  }
}

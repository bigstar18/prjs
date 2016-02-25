package gnnt.MEBS.timebargain.manage.webapp.form;

import java.io.Serializable;

public class BroadcastForm
  extends BaseForm
  implements Serializable
{
  private static final long serialVersionUID = 3690197650654049817L;
  private String id;
  private String title;
  private String content;
  private String status;
  private String customerID;
  private String createTime;
  private String author;
  private String sendTime;
  private String crud = "";
  private String type = "";
  
  public String getContent()
  {
    return this.content;
  }
  
  public void setContent(String paramString)
  {
    this.content = paramString;
  }
  
  public String getCreateTime()
  {
    return this.createTime;
  }
  
  public void setCreateTime(String paramString)
  {
    this.createTime = paramString;
  }
  
  public String getCrud()
  {
    return this.crud;
  }
  
  public void setCrud(String paramString)
  {
    this.crud = paramString;
  }
  
  public String getCustomerID()
  {
    return this.customerID;
  }
  
  public void setCustomerID(String paramString)
  {
    this.customerID = paramString;
  }
  
  public String getId()
  {
    return this.id;
  }
  
  public void setId(String paramString)
  {
    this.id = paramString;
  }
  
  public String getStatus()
  {
    return this.status;
  }
  
  public void setStatus(String paramString)
  {
    this.status = paramString;
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
  
  public String getType()
  {
    return this.type;
  }
  
  public void setType(String paramString)
  {
    this.type = paramString;
  }
}

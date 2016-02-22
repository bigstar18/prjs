package gnnt.MEBS.timebargain.server.model;

import java.io.Serializable;
import java.util.Date;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class SysLog
  implements Serializable
{
  private static final long serialVersionUID = 3690197650654049811L;
  private Long ID;
  private String userID;
  private String action;
  private Date createTime;
  private String note;
  
  public SysLog(String note)
  {
    this.userID = "system";
    this.action = "03";
    this.note = note;
  }
  
  public SysLog(String userID, String action, String note)
  {
    this.userID = userID;
    this.action = action;
    this.note = note;
  }
  
  public String toString()
  {
    return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
  }
  
  public String getAction()
  {
    return this.action;
  }
  
  public void setAction(String action)
  {
    this.action = action;
  }
  
  public Date getCreateTime()
  {
    return this.createTime;
  }
  
  public void setCreateTime(Date createTime)
  {
    this.createTime = createTime;
  }
  
  public Long getID()
  {
    return this.ID;
  }
  
  public void setID(Long id)
  {
    this.ID = id;
  }
  
  public String getNote()
  {
    return this.note;
  }
  
  public void setNote(String note)
  {
    this.note = note;
  }
  
  public String getUserID()
  {
    return this.userID;
  }
  
  public void setUserID(String userID)
  {
    this.userID = userID;
  }
}

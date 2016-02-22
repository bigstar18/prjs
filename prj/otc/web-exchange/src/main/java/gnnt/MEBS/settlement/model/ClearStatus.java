package gnnt.MEBS.settlement.model;

import gnnt.MEBS.base.model.Clone;
import java.util.Date;

public class ClearStatus
  extends Clone
{
  private Long actionId;
  private String actionNote;
  private String status;
  private Date finishTime;
  
  public Date getFinishTime()
  {
    return this.finishTime;
  }
  
  public void setFinishTime(Date finishTime)
  {
    this.finishTime = finishTime;
  }
  
  public Long getActionId()
  {
    return this.actionId;
  }
  
  public void setActionId(Long actionId)
  {
    this.actionId = actionId;
  }
  
  public String getActionNote()
  {
    return this.actionNote;
  }
  
  public void setActionNote(String actionNote)
  {
    this.actionNote = actionNote;
  }
  
  public String getStatus()
  {
    return this.status;
  }
  
  public void setStatus(String status)
  {
    this.status = status;
  }
  
  public Long getId()
  {
    return this.actionId;
  }
  
  public void setPrimary(String primary)
  {
    this.actionId = Long.valueOf(Long.parseLong(primary));
  }
}

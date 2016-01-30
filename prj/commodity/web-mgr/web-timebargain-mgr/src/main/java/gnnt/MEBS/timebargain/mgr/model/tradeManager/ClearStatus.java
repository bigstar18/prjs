package gnnt.MEBS.timebargain.mgr.model.tradeManager;

import gnnt.MEBS.common.mgr.model.StandardModel;
import gnnt.MEBS.common.mgr.model.StandardModel.PrimaryInfo;
import gnnt.MEBS.common.mgr.model.translate.ClassDiscription;
import java.util.Date;

public class ClearStatus extends StandardModel
{
  private static final long serialVersionUID = 5784112412311672026L;

  @ClassDiscription(name="交易节ID", description="")
  private Integer actionId;

  @ClassDiscription(name="结算动作说明", description="")
  private String actionNote;

  @ClassDiscription(name="完成动作状态", description="")
  private String status;

  @ClassDiscription(name="完成时间", description="")
  private Date finishTime;

  public Integer getActionId()
  {
    return this.actionId;
  }

  public String getActionNote()
  {
    return this.actionNote;
  }

  public String getStatus()
  {
    return this.status;
  }

  public Date getFinishTime()
  {
    return this.finishTime;
  }

  public void setActionId(Integer actionId)
  {
    this.actionId = actionId;
  }

  public void setActionNote(String actionNote)
  {
    this.actionNote = actionNote;
  }

  public void setStatus(String status)
  {
    this.status = status;
  }

  public void setFinishTime(Date finishTime)
  {
    this.finishTime = finishTime;
  }

  public StandardModel.PrimaryInfo fetchPKey()
  {
    return new StandardModel.PrimaryInfo( "actionId", this.actionId);
  }
}
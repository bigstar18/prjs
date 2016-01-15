package gnnt.MEBS.finance.mgr.model;

import gnnt.MEBS.common.mgr.model.StandardModel;
import gnnt.MEBS.common.mgr.model.StandardModel.PrimaryInfo;
import gnnt.MEBS.common.mgr.model.translate.ClassDiscription;
import java.util.Date;

public class ClearStatus extends StandardModel
{
  private static final long serialVersionUID = 5784112412311672026L;

  @ClassDiscription(name="ID", description="")
  private Integer actionId;

  @ClassDiscription(name="结算动作说明", description="")
  private String actionNote;

  @ClassDiscription(name="科目级别", description="Y:完成 N:未执行")
  private String status;

  @ClassDiscription(name="借贷方向标志", description="")
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

  public void setActionId(Integer paramInteger)
  {
    this.actionId = paramInteger;
  }

  public void setActionNote(String paramString)
  {
    this.actionNote = paramString;
  }

  public void setStatus(String paramString)
  {
    this.status = paramString;
  }

  public void setFinishTime(Date paramDate)
  {
    this.finishTime = paramDate;
  }

  public StandardModel.PrimaryInfo fetchPKey()
  {
    return new StandardModel.PrimaryInfo( "actionId", this.actionId);
  }
}
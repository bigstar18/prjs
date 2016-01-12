package gnnt.MEBS.timebargain.mgr.model.dataquery;

import gnnt.MEBS.common.mgr.model.StandardModel;
import gnnt.MEBS.common.mgr.model.StandardModel.PrimaryInfo;
import gnnt.MEBS.common.mgr.model.translate.ClassDiscription;
import java.util.Date;

public class SystemStatusModel extends StandardModel
{
  private static final long serialVersionUID = -4979167849962847775L;

  @ClassDiscription(name="交易时间", description="")
  private Date tradeDate;

  @ClassDiscription(name="状态", description="")
  private Long status;

  @ClassDiscription(name="交易节编号", description="")
  private Long sectionId;

  @ClassDiscription(name="备注", description="")
  private String note;

  @ClassDiscription(name="暂停后自动恢复时间", description="")
  private String recoverTime;

  public Date getTradeDate()
  {
    return this.tradeDate;
  }

  public void setTradeDate(Date tradeDate)
  {
    this.tradeDate = tradeDate;
  }

  public Long getStatus()
  {
    return this.status;
  }

  public void setStatus(Long status)
  {
    this.status = status;
  }

  public Long getSectionId()
  {
    return this.sectionId;
  }

  public void setSectionId(Long sectionId)
  {
    this.sectionId = sectionId;
  }

  public String getNote()
  {
    return this.note;
  }

  public void setNote(String note)
  {
    this.note = note;
  }

  public String getRecoverTime()
  {
    return this.recoverTime;
  }

  public void setRecoverTime(String recoverTime)
  {
    this.recoverTime = recoverTime;
  }

  public StandardModel.PrimaryInfo fetchPKey()
  {
    return null;
  }
}
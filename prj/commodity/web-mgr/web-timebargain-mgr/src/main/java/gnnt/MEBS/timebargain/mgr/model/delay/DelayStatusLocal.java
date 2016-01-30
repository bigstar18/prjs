package gnnt.MEBS.timebargain.mgr.model.delay;

import gnnt.MEBS.common.mgr.model.StandardModel;
import gnnt.MEBS.common.mgr.model.StandardModel.PrimaryInfo;
import gnnt.MEBS.common.mgr.model.translate.ClassDiscription;
import gnnt.MEBS.common.mgr.statictools.Tools;
import java.util.Date;

public class DelayStatusLocal extends StandardModel
{
  private static final long serialVersionUID = 1L;

  @ClassDiscription(name="交易日期", description="")
  private Date tradeDate;

  @ClassDiscription(name="状态", description="")
  private String status;

  @ClassDiscription(name="交易节编号", description="")
  private Long sectionID;

  @ClassDiscription(name="备注", description="")
  private String note;

  @ClassDiscription(name="暂停后自动恢复时间", description="")
  private String recoverTime;

  public String getNote()
  {
    return this.note;
  }
  public void setNote(String note) {
    this.note = note;
  }
  public String getRecoverTime() {
    return this.recoverTime;
  }
  public void setRecoverTime(String recoverTime) {
    this.recoverTime = recoverTime;
  }
  public Long getSectionID() {
    return this.sectionID;
  }
  public void setSectionID(Long sectionID) {
    this.sectionID = sectionID;
  }
  public String getStatus() {
    return this.status;
  }
  public void setStatus(String status) {
    this.status = status;
  }
  public Date getTradeDate() {
    return this.tradeDate;
  }
  public void setTradeDate(String tradeDate) {
    this.tradeDate = Tools.strToDate(tradeDate);
  }
  public void setTradeDate(Date tradeDate) {
    this.tradeDate = tradeDate;
  }

  public StandardModel.PrimaryInfo fetchPKey() {
    return new StandardModel.PrimaryInfo( "tradeDate", this.tradeDate);
  }
}
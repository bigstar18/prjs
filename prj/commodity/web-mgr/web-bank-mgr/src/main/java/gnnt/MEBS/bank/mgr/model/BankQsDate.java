package gnnt.MEBS.bank.mgr.model;

import gnnt.MEBS.common.mgr.model.StandardModel;
import gnnt.MEBS.common.mgr.model.StandardModel.PrimaryInfo;
import gnnt.MEBS.common.mgr.model.translate.ClassDiscription;
import java.util.Date;

public class BankQsDate extends StandardModel
{
  private static final long serialVersionUID = 8657677068330759794L;

  @ClassDiscription(name="银行", description="")
  private Bank bank;

  @ClassDiscription(name="清算日期 ", description="")
  private Date tradeDate;

  @ClassDiscription(name="清算类型 ", description="")
  private Integer tradeType;

  @ClassDiscription(name="清算状态", description="0 成功,1 失败")
  private Integer tradeStatus;

  @ClassDiscription(name=" 备注信息", description="")
  private String note;

  @ClassDiscription(name="记录创建时间", description="")
  private Date createDate;

  public Bank getBank()
  {
    return this.bank;
  }

  public void setBank(Bank bank)
  {
    this.bank = bank;
  }

  public Date getTradeDate()
  {
    return this.tradeDate;
  }

  public void setTradeDate(Date tradeDate) {
    this.tradeDate = tradeDate;
  }

  public Integer getTradeType()
  {
    return this.tradeType;
  }

  public void setTradeType(Integer tradeType)
  {
    this.tradeType = tradeType;
  }

  public Integer getTradeStatus()
  {
    return this.tradeStatus;
  }

  public void setTradeStatus(Integer tradeStatus)
  {
    this.tradeStatus = tradeStatus;
  }

  public String getNote()
  {
    return this.note;
  }

  public void setNote(String note)
  {
    this.note = note;
  }

  public Date getCreateDate()
  {
    return this.createDate;
  }

  public void setCreateDate(Date createDate)
  {
    this.createDate = createDate;
  }

  public StandardModel.PrimaryInfo fetchPKey()
  {
    return null;
  }
}
package gnnt.MEBS.bank.mgr.model;

import gnnt.MEBS.bank.mgr.model.integrated.MFirm;
import gnnt.MEBS.common.mgr.model.StandardModel;
import gnnt.MEBS.common.mgr.model.StandardModel.PrimaryInfo;
import gnnt.MEBS.common.mgr.model.translate.ClassDiscription;
import java.util.Date;

public class BankCapitalResult extends StandardModel
{
  private static final long serialVersionUID = 285990770155682469L;

  @ClassDiscription(name="银行", description="")
  private Bank bank;

  @ClassDiscription(name="交易商", description="")
  private MFirm firm;

  @ClassDiscription(name="银行权益", description="")
  private Double bankRight;

  @ClassDiscription(name="市场权益 ", description="")
  private Double maketRight;

  @ClassDiscription(name="不平原因", description="0金额不平 1银行端账户未建立 2机构端账户未建立")
  private Integer reason;

  @ClassDiscription(name="日期时间", description="")
  private Date bdate;

  public Bank getBank()
  {
    return this.bank;
  }

  public void setBank(Bank bank)
  {
    this.bank = bank;
  }

  public MFirm getFirm()
  {
    return this.firm;
  }

  public void setFirm(MFirm firm)
  {
    this.firm = firm;
  }

  public Double getBankRight()
  {
    return this.bankRight;
  }

  public void setBankRight(Double bankRight)
  {
    this.bankRight = bankRight;
  }

  public Double getMaketRight()
  {
    return this.maketRight;
  }

  public void setMaketRight(Double maketRight)
  {
    this.maketRight = maketRight;
  }

  public Integer getReason()
  {
    return this.reason;
  }

  public void setReason(Integer reason)
  {
    this.reason = reason;
  }

  public Date getBdate()
  {
    return this.bdate;
  }

  public void setBdate(Date bdate)
  {
    this.bdate = bdate;
  }

  public StandardModel.PrimaryInfo fetchPKey()
  {
    return null;
  }
}
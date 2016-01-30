package gnnt.MEBS.bank.mgr.model;

import gnnt.MEBS.bank.mgr.model.integrated.MFirm;
import gnnt.MEBS.common.mgr.model.StandardModel;
import gnnt.MEBS.common.mgr.model.StandardModel.PrimaryInfo;
import gnnt.MEBS.common.mgr.model.translate.ClassDiscription;
import java.util.Date;

public class FirmKXH extends StandardModel
{
  private static final long serialVersionUID = -5023752969447878716L;

  @ClassDiscription(name="银行前置流水号", description="")
  private String funID;

  @ClassDiscription(name="交易状态", description="1 开户,2 销户,3 待确认")
  private Integer status;

  @ClassDiscription(name="会员子账号 ", description="")
  private String account1;

  @ClassDiscription(name="子账户性质", description="")
  private Integer type;

  @ClassDiscription(name="子账户名称 ", description="")
  private String account1Name;

  @ClassDiscription(name="交易商", description="")
  private MFirm firm;

  @ClassDiscription(name="交易日期", description="")
  private Date tradeDate;

  @ClassDiscription(name="操作柜员号", description="")
  private String counterID;

  @ClassDiscription(name="银行", description="")
  private Bank bank;

  @ClassDiscription(name="记录创建日期", description="")
  private Date createDate;

  public String getFunID()
  {
    return this.funID;
  }

  public void setFunID(String funID)
  {
    this.funID = funID;
  }

  public Integer getStatus()
  {
    return this.status;
  }

  public void setStatus(Integer status)
  {
    this.status = status;
  }

  public String getAccount1()
  {
    return this.account1;
  }

  public void setAccount1(String account1)
  {
    this.account1 = account1;
  }

  public Integer getType()
  {
    return this.type;
  }

  public void setType(Integer type)
  {
    this.type = type;
  }

  public String getAccount1Name()
  {
    return this.account1Name;
  }

  public void setAccount1Name(String account1Name)
  {
    this.account1Name = account1Name;
  }

  public MFirm getFirm()
  {
    return this.firm;
  }

  public void setFirm(MFirm firm)
  {
    this.firm = firm;
  }

  public Date getTradeDate()
  {
    return this.tradeDate;
  }

  public void setTradeDate(Date tradeDate)
  {
    this.tradeDate = tradeDate;
  }

  public String getCounterID()
  {
    return this.counterID;
  }

  public void setCounterID(String counterID)
  {
    this.counterID = counterID;
  }

  public Bank getBank()
  {
    return this.bank;
  }

  public void setBank(Bank bank)
  {
    this.bank = bank;
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
package gnnt.MEBS.bank.mgr.model;

import gnnt.MEBS.bank.mgr.model.integrated.MFirm;
import gnnt.MEBS.common.mgr.model.StandardModel;
import gnnt.MEBS.common.mgr.model.StandardModel.PrimaryInfo;
import gnnt.MEBS.common.mgr.model.translate.ClassDiscription;
import java.util.Date;

public class BankCompareInfo extends StandardModel
{
  private static final long serialVersionUID = -1150447736514962744L;

  @ClassDiscription(name="银行对账信息ID", description="")
  private Long id;

  @ClassDiscription(name="银行流水号", description="")
  private String funID;

  @ClassDiscription(name="交易商", description="")
  private MFirm firm;

  @ClassDiscription(name="交易商账号", description="")
  private String account;

  @ClassDiscription(name="操作类型", description=" 0 入金,1 出金")
  private Integer type;

  @ClassDiscription(name="金额", description="")
  private Double money;

  @ClassDiscription(name="对账日期", description="")
  private Date compareDate;

  @ClassDiscription(name=" 备注", description="")
  private String note;

  @ClassDiscription(name="创建日期 ", description="")
  private Date createTime;

  @ClassDiscription(name="操作状态 ", description="")
  private Integer status;

  @ClassDiscription(name="银行 ", description="")
  private Bank bank;

  public Long getId()
  {
    return this.id;
  }

  public void setId(Long id)
  {
    this.id = id;
  }

  public String getFunID()
  {
    return this.funID;
  }

  public void setFunID(String funID)
  {
    this.funID = funID;
  }

  public MFirm getFirm()
  {
    return this.firm;
  }

  public void setFirm(MFirm firm)
  {
    this.firm = firm;
  }

  public String getAccount()
  {
    return this.account;
  }

  public void setAccount(String account)
  {
    this.account = account;
  }

  public Integer getType()
  {
    return this.type;
  }

  public void setType(Integer type)
  {
    this.type = type;
  }

  public Double getMoney()
  {
    return this.money;
  }

  public void setMoney(Double money)
  {
    this.money = money;
  }

  public Date getCompareDate()
  {
    return this.compareDate;
  }

  public void setCompareDate(Date compareDate)
  {
    this.compareDate = compareDate;
  }

  public String getNote()
  {
    return this.note;
  }

  public void setNote(String note)
  {
    this.note = note;
  }

  public Date getCreateTime()
  {
    return this.createTime;
  }

  public void setCreateTime(Date createTime)
  {
    this.createTime = createTime;
  }

  public Integer getStatus()
  {
    return this.status;
  }

  public void setStatus(Integer status)
  {
    this.status = status;
  }

  public Bank getBank()
  {
    return this.bank;
  }

  public void setBank(Bank bank)
  {
    this.bank = bank;
  }

  public StandardModel.PrimaryInfo fetchPKey()
  {
    return new StandardModel.PrimaryInfo( "id", this.id);
  }
}
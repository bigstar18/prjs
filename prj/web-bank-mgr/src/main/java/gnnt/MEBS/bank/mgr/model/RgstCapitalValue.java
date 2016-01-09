package gnnt.MEBS.bank.mgr.model;

import gnnt.MEBS.bank.mgr.model.integrated.MFirm;
import gnnt.MEBS.common.mgr.model.StandardModel;
import gnnt.MEBS.common.mgr.model.StandardModel.PrimaryInfo;
import gnnt.MEBS.common.mgr.model.translate.ClassDiscription;
import java.util.Date;

public class RgstCapitalValue extends StandardModel
{
  private static final long serialVersionUID = -6625706832487309359L;

  @ClassDiscription(name="记录流水号", description="")
  private Long id;

  @ClassDiscription(name="市场流水号", description="")
  private Long actionID;

  @ClassDiscription(name="交易商", description="")
  private MFirm firm;

  @ClassDiscription(name="签约协议号", description="")
  private String account;

  @ClassDiscription(name="银行 ", description="")
  private Bank bank;

  @ClassDiscription(name="流水类型", description="1 签约,2 解约")
  private Integer type;

  @ClassDiscription(name="创建时间", description="")
  private Date createTime;

  @ClassDiscription(name="完成时间", description="")
  private Date bankTime;

  @ClassDiscription(name="流水状态", description=" 0成功 1失败 2处理中")
  private Integer status;

  @ClassDiscription(name="户名", description="")
  private String accountName;

  @ClassDiscription(name="证件类型", description="")
  private String cardType;

  @ClassDiscription(name="证件号", description="")
  private String card;

  @ClassDiscription(name="备注 ", description="")
  private String note;

  public Long getId()
  {
    return this.id;
  }

  public void setId(Long id)
  {
    this.id = id;
  }

  public Long getActionID()
  {
    return this.actionID;
  }

  public void setActionID(Long actionID)
  {
    this.actionID = actionID;
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

  public Bank getBank()
  {
    return this.bank;
  }

  public void setBank(Bank bank)
  {
    this.bank = bank;
  }

  public Integer getType()
  {
    return this.type;
  }

  public void setType(Integer type)
  {
    this.type = type;
  }

  public Date getCreateTime()
  {
    return this.createTime;
  }

  public void setCreateTime(Date createTime)
  {
    this.createTime = createTime;
  }

  public Date getBankTime()
  {
    return this.bankTime;
  }

  public void setBankTime(Date bankTime)
  {
    this.bankTime = bankTime;
  }

  public Integer getStatus()
  {
    return this.status;
  }

  public void setStatus(Integer status)
  {
    this.status = status;
  }

  public String getAccountName()
  {
    return this.accountName;
  }

  public void setAccountName(String accountName)
  {
    this.accountName = accountName;
  }

  public String getCardType()
  {
    return this.cardType;
  }

  public void setCardType(String cardType)
  {
    this.cardType = cardType;
  }

  public String getCard()
  {
    return this.card;
  }

  public void setCard(String card)
  {
    this.card = card;
  }

  public String getNote()
  {
    return this.note;
  }

  public void setNote(String note)
  {
    this.note = note;
  }

  public StandardModel.PrimaryInfo fetchPKey()
  {
    return new StandardModel.PrimaryInfo( "id", this.id);
  }
}
package gnnt.MEBS.bank.mgr.model;

import gnnt.MEBS.bank.mgr.model.integrated.MFirm;
import gnnt.MEBS.common.mgr.model.StandardModel;
import gnnt.MEBS.common.mgr.model.StandardModel.PrimaryInfo;
import gnnt.MEBS.common.mgr.model.translate.ClassDiscription;
import java.util.Date;

public class CapitalInfo extends StandardModel
{
  private static final long serialVersionUID = 4594056539993383762L;

  @ClassDiscription(name="流水ID", description="")
  private Long id;

  @ClassDiscription(name="交易商 ", description="")
  private MFirm firm;

  @ClassDiscription(name="银行流水号", description="")
  private String funID;

  @ClassDiscription(name="银行 ", description="")
  private Bank bank;

  @ClassDiscription(name=" 贷方代码", description="")
  private String debitID;

  @ClassDiscription(name="借方代码 ", description="")
  private String creditID;

  @ClassDiscription(name="流水类型", description="")
  private Integer type;

  @ClassDiscription(name="金额", description="")
  private Double money;

  @ClassDiscription(name=" 业务代码", description="")
  private String operator;

  @ClassDiscription(name="创建时间", description="")
  private Date createtime;

  @ClassDiscription(name="银行时间", description="")
  private Date bankTime;

  @ClassDiscription(name="状态", description="0 成功, 1 失败,2 处理中,3 一次审核,4 二次审核,5 银行返回信息为空,6 银行返回市场流水号和市场保存流水号不一致,13 市场假银行出入金待审核状态")
  private Integer status;

  @ClassDiscription(name="备注信息", description="")
  private String note;

  @ClassDiscription(name="业务流水", description="")
  private Long actionID;

  @ClassDiscription(name="是否加急", description="")
  private Integer express;

  @ClassDiscription(name="特殊加的(银行名称) ", description="")
  private String bankName;

  @ClassDiscription(name="特殊加的(银行账号)", description="")
  private String account;

  @ClassDiscription(name="创建日期", description="")
  private String createDate;

  @ClassDiscription(name="流水2", description="")
  private String funID2;

  public Long getId()
  {
    return this.id;
  }

  public void setId(Long id)
  {
    this.id = id;
  }

  public MFirm getFirm()
  {
    return this.firm;
  }

  public void setFirm(MFirm firm)
  {
    this.firm = firm;
  }

  public String getFunID()
  {
    return this.funID;
  }

  public void setFunID(String funID)
  {
    this.funID = funID;
  }

  public Bank getBank()
  {
    return this.bank;
  }

  public void setBank(Bank bank)
  {
    this.bank = bank;
  }

  public String getDebitID()
  {
    return this.debitID;
  }

  public void setDebitID(String debitID)
  {
    this.debitID = debitID;
  }

  public String getCreditID()
  {
    return this.creditID;
  }

  public void setCreditID(String creditID)
  {
    this.creditID = creditID;
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

  public String getOperator()
  {
    return this.operator;
  }

  public void setOperator(String operator)
  {
    this.operator = operator;
  }

  public Date getCreatetime()
  {
    return this.createtime;
  }

  public void setCreatetime(Date createtime)
  {
    this.createtime = createtime;
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

  public String getNote()
  {
    return this.note;
  }

  public void setNote(String note)
  {
    this.note = note;
  }

  public Long getActionID()
  {
    return this.actionID;
  }

  public void setActionID(Long actionID)
  {
    this.actionID = actionID;
  }

  public Integer getExpress()
  {
    return this.express;
  }

  public void setExpress(Integer express)
  {
    this.express = express;
  }

  public String getBankName()
  {
    return this.bankName;
  }

  public void setBankName(String bankName)
  {
    this.bankName = bankName;
  }

  public String getAccount()
  {
    return this.account;
  }

  public void setAccount(String account)
  {
    this.account = account;
  }

  public String getCreateDate()
  {
    return this.createDate;
  }

  public void setCreateDate(String createDate)
  {
    this.createDate = createDate;
  }

  public String getFunID2()
  {
    return this.funID2;
  }

  public void setFunID2(String funID2)
  {
    this.funID2 = funID2;
  }

  public StandardModel.PrimaryInfo fetchPKey()
  {
    return new StandardModel.PrimaryInfo( "id", this.id);
  }
}
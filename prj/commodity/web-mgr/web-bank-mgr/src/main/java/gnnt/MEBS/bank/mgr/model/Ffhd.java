package gnnt.MEBS.bank.mgr.model;

import gnnt.MEBS.bank.mgr.model.integrated.MFirm;
import gnnt.MEBS.common.mgr.model.StandardModel;
import gnnt.MEBS.common.mgr.model.StandardModel.PrimaryInfo;
import gnnt.MEBS.common.mgr.model.translate.ClassDiscription;
import java.util.Date;

public class Ffhd extends StandardModel
{
  private static final long serialVersionUID = 8021603226485320376L;

  @ClassDiscription(name="序列化编号", description="")
  private Bank bank;

  @ClassDiscription(name=" 交易日期", description="")
  private Date tradeDate;

  @ClassDiscription(name="交易商", description="")
  private MFirm firm;

  @ClassDiscription(name="交易商银行账号", description="")
  private String account;

  @ClassDiscription(name="币别", description="001 人民币,013 港币,014 美元")
  private String currency;

  @ClassDiscription(name="钞汇标识", description="0 钞,1 汇")
  private Integer type;

  @ClassDiscription(name="不平原因", description="0 金额不平,1 银行端资金存管账户未建立,2 市场端交易商代码不存在")
  private Integer reasion;

  @ClassDiscription(name="市场总权益", description="")
  private Double balancem;

  @ClassDiscription(name="市场现金权益 ", description="")
  private Double cashm;

  @ClassDiscription(name="市场票据权益", description="")
  private Double billm;

  @ClassDiscription(name="市场可用资金", description="")
  private Double usebalancem;

  @ClassDiscription(name="市场占用现金", description="")
  private Double frozencashm;

  @ClassDiscription(name=" 市场占用贷款金额", description="")
  private Double frozenloanm;

  @ClassDiscription(name="银行总权益 ", description="")
  private Double balanceb;

  @ClassDiscription(name="银行现金权益", description="")
  private Double cashb;

  @ClassDiscription(name="银行票据权益", description="")
  private Double billb;

  @ClassDiscription(name="银行可用资金", description="")
  private Double usebalanceb;

  @ClassDiscription(name="银行占用现金", description="")
  private Double frozencashb;

  @ClassDiscription(name="银行占用贷款金额", description="")
  private Double frozenloanb;

  @ClassDiscription(name="创建时间", description="")
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

  public void setTradeDate(Date tradeDate)
  {
    this.tradeDate = tradeDate;
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

  public String getCurrency()
  {
    return this.currency;
  }

  public void setCurrency(String currency)
  {
    this.currency = currency;
  }

  public Integer getType()
  {
    return this.type;
  }

  public void setType(Integer type)
  {
    this.type = type;
  }

  public Integer getReasion()
  {
    return this.reasion;
  }

  public void setReasion(Integer reasion)
  {
    this.reasion = reasion;
  }

  public Double getBalancem()
  {
    return this.balancem;
  }

  public void setBalancem(Double balancem)
  {
    this.balancem = balancem;
  }

  public Double getCashm()
  {
    return this.cashm;
  }

  public void setCashm(Double cashm)
  {
    this.cashm = cashm;
  }

  public Double getBillm()
  {
    return this.billm;
  }

  public void setBillm(Double billm)
  {
    this.billm = billm;
  }

  public Double getUsebalancem()
  {
    return this.usebalancem;
  }

  public void setUsebalancem(Double usebalancem)
  {
    this.usebalancem = usebalancem;
  }

  public Double getFrozencashm()
  {
    return this.frozencashm;
  }

  public void setFrozencashm(Double frozencashm)
  {
    this.frozencashm = frozencashm;
  }

  public Double getFrozenloanm()
  {
    return this.frozenloanm;
  }

  public void setFrozenloanm(Double frozenloanm)
  {
    this.frozenloanm = frozenloanm;
  }

  public Double getBalanceb()
  {
    return this.balanceb;
  }

  public void setBalanceb(Double balanceb)
  {
    this.balanceb = balanceb;
  }

  public Double getCashb()
  {
    return this.cashb;
  }

  public void setCashb(Double cashb)
  {
    this.cashb = cashb;
  }

  public Double getBillb()
  {
    return this.billb;
  }

  public void setBillb(Double billb)
  {
    this.billb = billb;
  }

  public Double getUsebalanceb()
  {
    return this.usebalanceb;
  }

  public void setUsebalanceb(Double usebalanceb)
  {
    this.usebalanceb = usebalanceb;
  }

  public Double getFrozencashb()
  {
    return this.frozencashb;
  }

  public void setFrozencashb(Double frozencashb)
  {
    this.frozencashb = frozencashb;
  }

  public Double getFrozenloanb()
  {
    return this.frozenloanb;
  }

  public void setFrozenloanb(Double frozenloanb)
  {
    this.frozenloanb = frozenloanb;
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
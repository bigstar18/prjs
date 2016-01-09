package gnnt.MEBS.bank.mgr.model;

import gnnt.MEBS.common.mgr.model.StandardModel;
import gnnt.MEBS.common.mgr.model.StandardModel.PrimaryInfo;
import gnnt.MEBS.common.mgr.model.translate.ClassDiscription;
import java.util.Date;

public class FirmBalance extends StandardModel
{

  @ClassDiscription(name="", description="")
  private static final long serialVersionUID = 3500114550017085952L;

  @ClassDiscription(name="子账号", description="")
  private String custacctID;

  @ClassDiscription(name="账户名", description="")
  private String custName;

  @ClassDiscription(name="会员代码", description="")
  private String thirdcustID;

  @ClassDiscription(name="状态", description="1 正常,2 退出,3 待确定")
  private Integer status;

  @ClassDiscription(name="账号属性", description="1 普通会员子账号,2 挂账子账号,3 手续费子账号,4 利息子账号,6 清算子账号")
  private Integer type;

  @ClassDiscription(name="是否实子账号", description="")
  private Integer isTruecont;

  @ClassDiscription(name="总额 ", description="")
  private Double balance;

  @ClassDiscription(name="可用余额", description="")
  private Double usrBalance;

  @ClassDiscription(name="冻结资金", description="")
  private Double frozenBalance;

  @ClassDiscription(name="利息基数", description="")
  private Double interest;

  @ClassDiscription(name="银行代码", description="")
  private Bank bank;

  @ClassDiscription(name="交易日期", description="")
  private Date tradeDate;

  @ClassDiscription(name="信息创建时间", description="")
  private Date createDate;

  public String getCustacctID()
  {
    return this.custacctID;
  }

  public void setCustacctID(String custacctID)
  {
    this.custacctID = custacctID;
  }

  public String getCustName()
  {
    return this.custName;
  }

  public void setCustName(String custName)
  {
    this.custName = custName;
  }

  public String getThirdcustID()
  {
    return this.thirdcustID;
  }

  public void setThirdcustID(String thirdcustID)
  {
    this.thirdcustID = thirdcustID;
  }

  public Integer getStatus()
  {
    return this.status;
  }

  public void setStatus(Integer status)
  {
    this.status = status;
  }

  public Integer getType()
  {
    return this.type;
  }

  public void setType(Integer type)
  {
    this.type = type;
  }

  public Integer getIsTruecont()
  {
    return this.isTruecont;
  }

  public void setIsTruecont(Integer isTruecont)
  {
    this.isTruecont = isTruecont;
  }

  public Double getBalance()
  {
    return this.balance;
  }

  public void setBalance(Double balance)
  {
    this.balance = balance;
  }

  public Double getUsrBalance()
  {
    return this.usrBalance;
  }

  public void setUsrBalance(Double usrBalance)
  {
    this.usrBalance = usrBalance;
  }

  public Double getFrozenBalance()
  {
    return this.frozenBalance;
  }

  public void setFrozenBalance(Double frozenBalance)
  {
    this.frozenBalance = frozenBalance;
  }

  public Double getInterest()
  {
    return this.interest;
  }

  public void setInterest(Double interest)
  {
    this.interest = interest;
  }

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
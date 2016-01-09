package gnnt.MEBS.bank.mgr.model;

import gnnt.MEBS.common.mgr.model.StandardModel;
import gnnt.MEBS.common.mgr.model.StandardModel.PrimaryInfo;
import gnnt.MEBS.common.mgr.model.translate.ClassDiscription;

public class BankAccount extends StandardModel
{

  @ClassDiscription(name="", description="")
  private static final long serialVersionUID = -5823338185755864090L;

  @ClassDiscription(name=" 银行", description="")
  private Bank bank;

  @ClassDiscription(name="银行帐号", description="")
  private String account;

  @ClassDiscription(name="状态", description="0 可用,1 冻结")
  private Integer status;

  @ClassDiscription(name="账户名 ", description="")
  private String accountName;

  @ClassDiscription(name="银行名称", description="")
  private String bankName;

  @ClassDiscription(name="银行省份", description="")
  private String bankProvince;

  @ClassDiscription(name="银行所在市", description="")
  private String bankCity;

  @ClassDiscription(name="电话号码 ", description="")
  private String mobile;

  @ClassDiscription(name="邮箱", description="")
  private String email;

  @ClassDiscription(name=" 帐户类型", description="1 身份证,2军官证,3国内护照,4户口本,5学员证,6退休证,7临时身份证,8组织机构代码,9营业执照,A警官证,B解放军士兵证,C回乡证,D外国护照,E港澳台居民身份证,F台湾通行证及其他有效旅行证,G海外客户编号,H解放军文职干部证,I武警文职干部证,J武警士兵证,X其他有效证件,Z重号身份证")
  private String cardType;

  @ClassDiscription(name="证件号码 ", description="")
  private String card;

  public Bank getBank()
  {
    return this.bank;
  }

  public void setBank(Bank bank)
  {
    this.bank = bank;
  }

  public String getAccount()
  {
    return this.account;
  }

  public void setAccount(String account)
  {
    this.account = account;
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

  public String getBankName()
  {
    return this.bankName;
  }

  public void setBankName(String bankName)
  {
    this.bankName = bankName;
  }

  public String getBankProvince()
  {
    return this.bankProvince;
  }

  public void setBankProvince(String bankProvince)
  {
    this.bankProvince = bankProvince;
  }

  public String getBankCity()
  {
    return this.bankCity;
  }

  public void setBankCity(String bankCity)
  {
    this.bankCity = bankCity;
  }

  public String getMobile()
  {
    return this.mobile;
  }

  public void setMobile(String mobile)
  {
    this.mobile = mobile;
  }

  public String getEmail()
  {
    return this.email;
  }

  public void setEmail(String email)
  {
    this.email = email;
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

  public StandardModel.PrimaryInfo fetchPKey()
  {
    return null;
  }
}
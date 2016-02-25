package gnnt.MEBS.bank.front.model;

import gnnt.MEBS.common.front.model.StandardModel;
import gnnt.MEBS.common.front.model.StandardModel.PrimaryInfo;
import gnnt.MEBS.common.front.model.integrated.MFirm;
import gnnt.MEBS.common.front.model.translate.ClassDiscription;
import gnnt.trade.bank.vo.CorrespondValue;
import java.util.Date;

public class FirmIDAndAccount
  extends StandardModel
{
  private static final long serialVersionUID = 6949112070939632618L;
  @ClassDiscription(name="银行", description="")
  private Bank bank;
  @ClassDiscription(name="交易商", description="")
  private MFirm firm;
  @ClassDiscription(name="银行帐号", description="")
  private String account;
  @ClassDiscription(name="银行内部账号", description="")
  private String account1;
  @ClassDiscription(name="状态", description="")
  private Integer status;
  @ClassDiscription(name="账户名", description="")
  private String accountName;
  @ClassDiscription(name="银行名称 ", description="")
  private String bankName;
  @ClassDiscription(name="银行省份", description="")
  private String bankProvince;
  @ClassDiscription(name=" 银行所在市", description="")
  private String bankCity;
  @ClassDiscription(name="电话号码", description="")
  private String mobile;
  @ClassDiscription(name="邮箱", description="")
  private String email;
  @ClassDiscription(name="是否已签约", description="0 未签约,1 已签约")
  private Integer isOpen;
  @ClassDiscription(name="帐户类型", description="\t  1 身份证,2军官证,3国内护照,4户口本,5学员证,6退休证,7临时身份证,8组织机构代码,9营业执照,A警官证,B解放军士兵证,C回乡证,D外国护照,E港澳台居民身份证,F台湾通行证及其他有效旅行证,G海外客户编号,H解放军文职干部证,I武警文职干部证,J武警士兵证,X其他有效证件,Z重号身份证")
  private String cardType;
  @ClassDiscription(name="证件号码", description="")
  private String card;
  @ClassDiscription(name="冻结资金", description="")
  private Double frozenFuns;
  @ClassDiscription(name="银行内部账户名称", description="")
  private String accountName1;
  @ClassDiscription(name="银行内部账户名称", description="")
  private Date openTime;
  @ClassDiscription(name="销户时间 ", description="")
  private Date delTime;
  @ClassDiscription(name="交易商入世协议号", description="")
  private String inmarketCode;
  
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
  
  public String getAccount()
  {
    return this.account;
  }
  
  public void setAccount(String account)
  {
    this.account = account;
  }
  
  public String getAccount1()
  {
    return this.account1;
  }
  
  public void setAccount1(String account1)
  {
    this.account1 = account1;
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
  
  public Integer getIsOpen()
  {
    return this.isOpen;
  }
  
  public void setIsOpen(Integer isOpen)
  {
    this.isOpen = isOpen;
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
  
  public Double getFrozenFuns()
  {
    return this.frozenFuns;
  }
  
  public void setFrozenFuns(Double frozenFuns)
  {
    this.frozenFuns = frozenFuns;
  }
  
  public String getAccountName1()
  {
    return this.accountName1;
  }
  
  public void setAccountName1(String accountName1)
  {
    this.accountName1 = accountName1;
  }
  
  public Date getOpenTime()
  {
    return this.openTime;
  }
  
  public void setOpenTime(Date openTime)
  {
    this.openTime = openTime;
  }
  
  public Date getDelTime()
  {
    return this.delTime;
  }
  
  public void setDelTime(Date delTime)
  {
    this.delTime = delTime;
  }
  
  public String getInmarketCode()
  {
    return this.inmarketCode;
  }
  
  public void setInmarketCode(String inmarketCode)
  {
    this.inmarketCode = inmarketCode;
  }
  
  public CorrespondValue getCorrespondValue()
  {
    CorrespondValue result = new CorrespondValue();
    result.account = getAccount();
    result.account1 = getAccount1();
    result.accountName = getAccountName();
    result.accountName1 = getAccountName1();
    result.bankCity = getBankCity();
    if (getBank() != null) {
      result.bankID = getBank().getBankID();
    }
    result.bankName = getBankName();
    result.bankProvince = getBankProvince();
    result.card = getCard();
    result.cardType = getCardType();
    result.deltime = getDelTime();
    result.email = getEmail();
    if (getFirm() != null) {
      result.firmID = getFirm().getFirmID();
    }
    if (getFrozenFuns() != null) {
      result.frozenFuns = getFrozenFuns().doubleValue();
    }
    result.inMarketCode = getInmarketCode();
    if (getIsOpen() != null) {
      result.isOpen = getIsOpen().intValue();
    }
    result.mobile = getMobile();
    result.opentime = getOpenTime();
    if (getStatus() != null) {
      result.status = getStatus().intValue();
    }
    return result;
  }
  
  public StandardModel.PrimaryInfo fetchPKey()
  {
    return null;
  }
}

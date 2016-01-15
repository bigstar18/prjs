package gnnt.MEBS.integrated.broker.model;

import gnnt.MEBS.common.broker.model.StandardModel;
import gnnt.MEBS.common.broker.model.StandardModel.PrimaryInfo;
import gnnt.MEBS.common.broker.model.User;
import java.util.Date;

public class CustomerModel extends StandardModel
{
  private static final long serialVersionUID = 5784146401111672026L;
  private Long customerId;
  private MFirm firm;
  private String name;
  private String fullName;
  private String cardType;
  private String card;
  private Integer type;
  private String bankCode;
  private String bankAccount;
  private String address;
  private String contactMan;
  private String phone;
  private String email;
  private String postcode;
  private String status;
  private String note;
  private Date createTime;
  private Date modifyTime;
  private User user;

  public Long getCustomerId()
  {
    return this.customerId;
  }

  public void setCustomerId(Long paramLong)
  {
    this.customerId = paramLong;
  }

  public MFirm getFirm()
  {
    return this.firm;
  }

  public void setFirm(MFirm paramMFirm)
  {
    this.firm = paramMFirm;
  }

  public String getName()
  {
    return this.name;
  }

  public void setName(String paramString)
  {
    this.name = paramString;
  }

  public String getFullName()
  {
    return this.fullName;
  }

  public void setFullName(String paramString)
  {
    this.fullName = paramString;
  }

  public String getCardType()
  {
    return this.cardType;
  }

  public void setCardType(String paramString)
  {
    this.cardType = paramString;
  }

  public String getCard()
  {
    return this.card;
  }

  public void setCard(String paramString)
  {
    this.card = paramString;
  }

  public Integer getType()
  {
    return this.type;
  }

  public void setType(Integer paramInteger)
  {
    this.type = paramInteger;
  }

  public String getBankCode()
  {
    return this.bankCode;
  }

  public void setBankCode(String paramString)
  {
    this.bankCode = paramString;
  }

  public String getBankAccount()
  {
    return this.bankAccount;
  }

  public void setBankAccount(String paramString)
  {
    this.bankAccount = paramString;
  }

  public String getAddress()
  {
    return this.address;
  }

  public void setAddress(String paramString)
  {
    this.address = paramString;
  }

  public String getContactMan()
  {
    return this.contactMan;
  }

  public void setContactMan(String paramString)
  {
    this.contactMan = paramString;
  }

  public String getPhone()
  {
    return this.phone;
  }

  public void setPhone(String paramString)
  {
    this.phone = paramString;
  }

  public String getEmail()
  {
    return this.email;
  }

  public void setEmail(String paramString)
  {
    this.email = paramString;
  }

  public String getPostcode()
  {
    return this.postcode;
  }

  public void setPostcode(String paramString)
  {
    this.postcode = paramString;
  }

  public String getStatus()
  {
    return this.status;
  }

  public void setStatus(String paramString)
  {
    this.status = paramString;
  }

  public String getNote()
  {
    return this.note;
  }

  public void setNote(String paramString)
  {
    this.note = paramString;
  }

  public Date getCreateTime()
  {
    return this.createTime;
  }

  public void setCreateTime(Date paramDate)
  {
    this.createTime = paramDate;
  }

  public Date getModifyTime()
  {
    return this.modifyTime;
  }

  public void setModifyTime(Date paramDate)
  {
    this.modifyTime = paramDate;
  }

  public User getUser()
  {
    return this.user;
  }

  public void setUser(User paramUser)
  {
    this.user = paramUser;
  }

  public StandardModel.PrimaryInfo fetchPKey()
  {
    return new StandardModel.PrimaryInfo( "customerId", this.customerId);
  }
}
package gnnt.MEBS.timebargain.mgr.model;

import gnnt.MEBS.common.mgr.model.StandardModel;
import gnnt.MEBS.common.mgr.model.StandardModel.PrimaryInfo;
import gnnt.MEBS.common.mgr.model.translate.ClassDiscription;
import java.sql.Date;

public class DemoCustomerModel extends StandardModel
{
  private static final long serialVersionUID = -7821957278209148951L;

  @ClassDiscription(name="客户表编号", description="")
  private Long customerId;

  @ClassDiscription(name="客户所属交易商", description="")
  private String firmId;

  @ClassDiscription(name="客户名", description="")
  private String name;

  @ClassDiscription(name="客户全称", description="")
  private String fullName;

  @ClassDiscription(name="证件类型", description="")
  private String cardType;

  @ClassDiscription(name="证件号码", description="")
  private String card;

  @ClassDiscription(name="客户类型", description="")
  private Long type;

  @ClassDiscription(name="银行代码", description="")
  private String bankCode;

  @ClassDiscription(name="银行账号", description="")
  private String bankAccount;

  @ClassDiscription(name="地址", description="")
  private String address;

  @ClassDiscription(name="联系人", description="")
  private String contactMan;

  @ClassDiscription(name="电话号码", description="")
  private String phone;

  @ClassDiscription(name="邮箱", description="")
  private String email;

  @ClassDiscription(name="", description="")
  private String postCode;

  @ClassDiscription(name="", description="")
  private String status;

  @ClassDiscription(name="", description="")
  private String note;

  @ClassDiscription(name="", description="")
  private Date createTime;

  @ClassDiscription(name="", description="")
  private Date modifyTime;

  @ClassDiscription(name="", description="")
  private String userId;

  public Long getCustomerId()
  {
    return this.customerId;
  }

  public void setCustomerId(Long customerId)
  {
    this.customerId = customerId;
  }

  public String getFirmId()
  {
    return this.firmId;
  }

  public void setFirmId(String firmId)
  {
    this.firmId = firmId;
  }

  public String getName()
  {
    return this.name;
  }

  public void setName(String name)
  {
    this.name = name;
  }

  public String getFullName()
  {
    return this.fullName;
  }

  public void setFullName(String fullName)
  {
    this.fullName = fullName;
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

  public Long getType()
  {
    return this.type;
  }

  public void setType(Long type)
  {
    this.type = type;
  }

  public String getBankCode()
  {
    return this.bankCode;
  }

  public void setBankCode(String bankCode)
  {
    this.bankCode = bankCode;
  }

  public String getBankAccount()
  {
    return this.bankAccount;
  }

  public void setBankAccount(String bankAccount)
  {
    this.bankAccount = bankAccount;
  }

  public String getAddress()
  {
    return this.address;
  }

  public void setAddress(String address)
  {
    this.address = address;
  }

  public String getContactMan()
  {
    return this.contactMan;
  }

  public void setContactMan(String contactMan)
  {
    this.contactMan = contactMan;
  }

  public String getPhone()
  {
    return this.phone;
  }

  public void setPhone(String phone)
  {
    this.phone = phone;
  }

  public String getEmail()
  {
    return this.email;
  }

  public void setEmail(String email)
  {
    this.email = email;
  }

  public String getPostCode()
  {
    return this.postCode;
  }

  public void setPostCode(String postCode)
  {
    this.postCode = postCode;
  }

  public String getStatus()
  {
    return this.status;
  }

  public void setStatus(String status)
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

  public Date getCreateTime()
  {
    return this.createTime;
  }

  public void setCreateTime(Date createTime)
  {
    this.createTime = createTime;
  }

  public Date getModifyTime()
  {
    return this.modifyTime;
  }

  public void setModifyTime(Date modifyTime)
  {
    this.modifyTime = modifyTime;
  }

  public String getUserId()
  {
    return this.userId;
  }

  public void setUserId(String userId)
  {
    this.userId = userId;
  }

  public StandardModel.PrimaryInfo fetchPKey()
  {
    return new StandardModel.PrimaryInfo( "customerId", this.customerId);
  }
}
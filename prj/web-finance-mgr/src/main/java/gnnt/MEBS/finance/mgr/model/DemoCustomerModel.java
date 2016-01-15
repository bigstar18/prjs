package gnnt.MEBS.finance.mgr.model;

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

  @ClassDiscription(name="证件类型", description="01 身份证 02 机构代码 03 营业执照")
  private String cardType;

  @ClassDiscription(name="证件号码", description="")
  private String card;

  @ClassDiscription(name="户类型", description=" 双边客户 1 唯买客户 2 唯卖客户")
  private Long type;

  @ClassDiscription(name="银行代码", description="01 工行 02 中行 03 交通 04 建行 05 农行")
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

  @ClassDiscription(name="邮政编码", description="")
  private String postCode;

  @ClassDiscription(name="状态", description=" N 可用 E 禁用")
  private String status;

  @ClassDiscription(name="备注信息", description="")
  private String note;

  @ClassDiscription(name="创建时间", description="")
  private Date createTime;

  @ClassDiscription(name="修改时间", description="")
  private Date modifyTime;

  @ClassDiscription(name="最后修改客户管理员", description="")
  private String userId;

  public Long getCustomerId()
  {
    return this.customerId;
  }

  public void setCustomerId(Long paramLong)
  {
    this.customerId = paramLong;
  }

  public String getFirmId()
  {
    return this.firmId;
  }

  public void setFirmId(String paramString)
  {
    this.firmId = paramString;
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

  public Long getType()
  {
    return this.type;
  }

  public void setType(Long paramLong)
  {
    this.type = paramLong;
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

  public String getPostCode()
  {
    return this.postCode;
  }

  public void setPostCode(String paramString)
  {
    this.postCode = paramString;
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

  public String getUserId()
  {
    return this.userId;
  }

  public void setUserId(String paramString)
  {
    this.userId = paramString;
  }

  public StandardModel.PrimaryInfo fetchPKey()
  {
    return new StandardModel.PrimaryInfo( "customerId", this.customerId);
  }
}
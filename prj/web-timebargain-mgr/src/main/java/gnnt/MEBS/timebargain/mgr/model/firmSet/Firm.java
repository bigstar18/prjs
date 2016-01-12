package gnnt.MEBS.timebargain.mgr.model.firmSet;

import gnnt.MEBS.common.mgr.model.StandardModel;
import gnnt.MEBS.common.mgr.model.StandardModel.PrimaryInfo;
import gnnt.MEBS.common.mgr.model.translate.ClassDiscription;
import java.util.Date;

public class Firm extends StandardModel
{
  private static final long serialVersionUID = 4173824450011296987L;

  @ClassDiscription(name="交易商ID", description="")
  private String firmID;

  @ClassDiscription(name="交易商名称", description="")
  private String name;

  @ClassDiscription(name="交易商全称", description="")
  private String fullName;

  @ClassDiscription(name="交易商类型 ", description="")
  private Integer type;

  @ClassDiscription(name="联系人 ", description="")
  private String contactMan;

  @ClassDiscription(name="证件类型", description="1.居民身份证   2.士官证  3.学生证  4.驾驶证 5.护照 6.港澳通行证")
  private Integer certificateType;

  @ClassDiscription(name="证件号码", description="")
  private String certificateNO;

  @ClassDiscription(name="联系电话", description="")
  private String phone;

  @ClassDiscription(name="手机号码", description="")
  private Long mobile;

  @ClassDiscription(name="传真 ", description="")
  private String fax;

  @ClassDiscription(name="交易商地址", description="")
  private String address;

  @ClassDiscription(name="交易商地址", description="")
  private String postCode;

  @ClassDiscription(name="交易商地址", description="")
  private String email;

  @ClassDiscription(name="交易商地址", description="")
  private String zoneCode;

  @ClassDiscription(name="交易商地址", description="")
  private String industryCode;

  @ClassDiscription(name="织机构代码", description="")
  private String organizationCode;

  @ClassDiscription(name="织机构代码", description="")
  private String corporateRepresentative;

  @ClassDiscription(name="备注", description="")
  private String note;

  @ClassDiscription(name="XML扩展信息", description="")
  private String extendData;

  @ClassDiscription(name="交易商创建时间 ", description="")
  private Date createTime;

  @ClassDiscription(name="交易商修改时间", description="")
  private Date modifyTime;

  @ClassDiscription(name="交易商修改时间", description=" N：正常 Normal,D：禁用 Disable, E：删除 Erase")
  private String status;

  public String getFirmID()
  {
    return this.firmID;
  }

  public void setFirmID(String firmID)
  {
    this.firmID = firmID;
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

  public Integer getType()
  {
    return this.type;
  }

  public void setType(Integer type)
  {
    this.type = type;
  }

  public String getStatus()
  {
    return this.status;
  }

  public void setStatus(String status)
  {
    this.status = status;
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

  public String getFax()
  {
    return this.fax;
  }

  public void setFax(String fax)
  {
    this.fax = fax;
  }

  public String getPostCode()
  {
    return this.postCode;
  }

  public void setPostCode(String postCode)
  {
    this.postCode = postCode;
  }

  public String getEmail()
  {
    return this.email;
  }

  public void setEmail(String email)
  {
    this.email = email;
  }

  public String getNote()
  {
    return this.note;
  }

  public void setNote(String note)
  {
    this.note = note;
  }

  public String getZoneCode()
  {
    return this.zoneCode;
  }

  public void setZoneCode(String zoneCode)
  {
    this.zoneCode = zoneCode;
  }

  public String getIndustryCode()
  {
    return this.industryCode;
  }

  public void setIndustryCode(String industryCode)
  {
    this.industryCode = industryCode;
  }

  public String getExtendData()
  {
    return this.extendData;
  }

  public void setExtendData(String extendData)
  {
    this.extendData = extendData;
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

  public Integer getCertificateType()
  {
    return this.certificateType;
  }

  public void setCertificateType(Integer certificateType)
  {
    this.certificateType = certificateType;
  }

  public String getCertificateNO()
  {
    return this.certificateNO;
  }

  public void setCertificateNO(String certificateNO)
  {
    this.certificateNO = certificateNO;
  }

  public Long getMobile()
  {
    return this.mobile;
  }

  public void setMobile(Long mobile)
  {
    this.mobile = mobile;
  }

  public String getOrganizationCode()
  {
    return this.organizationCode;
  }

  public void setOrganizationCode(String organizationCode)
  {
    this.organizationCode = organizationCode;
  }

  public String getCorporateRepresentative()
  {
    return this.corporateRepresentative;
  }

  public void setCorporateRepresentative(String corporateRepresentative)
  {
    this.corporateRepresentative = corporateRepresentative;
  }

  public StandardModel.PrimaryInfo fetchPKey()
  {
    return new StandardModel.PrimaryInfo( "firmID", this.firmID);
  }
}
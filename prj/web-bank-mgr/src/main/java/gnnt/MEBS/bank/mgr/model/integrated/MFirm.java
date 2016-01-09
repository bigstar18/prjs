package gnnt.MEBS.bank.mgr.model.integrated;

import gnnt.MEBS.common.mgr.model.StandardModel;
import gnnt.MEBS.common.mgr.model.StandardModel.PrimaryInfo;
import java.util.Date;

public class MFirm extends StandardModel
{
  private static final long serialVersionUID = -6771715147557233383L;
  private String firmID;
  private String name;
  private String fullName;
  private Integer type;
  private String contactMan;
  private Integer certificateType;
  private String certificateNO;
  private String phone;
  private Long mobile;
  private String fax;
  private String address;
  private String postCode;
  private String email;
  private String zoneCode;
  private String industryCode;
  private String organizationCode;
  private String corporateRepresentative;
  private String note;
  private String extendData;
  private Date createTime;
  private Date modifyTime;
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
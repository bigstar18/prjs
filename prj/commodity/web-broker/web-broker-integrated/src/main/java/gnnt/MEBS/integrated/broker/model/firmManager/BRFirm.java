package gnnt.MEBS.integrated.broker.model.firmManager;

import gnnt.MEBS.common.broker.model.StandardModel;
import gnnt.MEBS.common.broker.model.StandardModel.PrimaryInfo;
import java.util.Date;

public class BRFirm extends StandardModel
{
  private static final long serialVersionUID = -1L;
  private String firmId;
  private String name;
  private String fullName;
  private Integer type;
  private String contactMan;
  private Integer certificateType;
  private String certificateNO;
  private String phone;
  private String mobile;
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
  private FirmAndBroker firmAndBroker;

  public FirmAndBroker getFirmAndBroker()
  {
    return this.firmAndBroker;
  }

  public void setFirmAndBroker(FirmAndBroker paramFirmAndBroker)
  {
    this.firmAndBroker = paramFirmAndBroker;
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

  public Integer getType()
  {
    return this.type;
  }

  public void setType(Integer paramInteger)
  {
    this.type = paramInteger;
  }

  public String getContactMan()
  {
    return this.contactMan;
  }

  public void setContactMan(String paramString)
  {
    this.contactMan = paramString;
  }

  public Integer getCertificateType()
  {
    return this.certificateType;
  }

  public void setCertificateType(Integer paramInteger)
  {
    this.certificateType = paramInteger;
  }

  public String getCertificateNO()
  {
    return this.certificateNO;
  }

  public void setCertificateNO(String paramString)
  {
    this.certificateNO = paramString;
  }

  public String getPhone()
  {
    return this.phone;
  }

  public void setPhone(String paramString)
  {
    this.phone = paramString;
  }

  public String getMobile()
  {
    return this.mobile;
  }

  public void setMobile(String paramString)
  {
    this.mobile = paramString;
  }

  public String getFax()
  {
    return this.fax;
  }

  public void setFax(String paramString)
  {
    this.fax = paramString;
  }

  public String getAddress()
  {
    return this.address;
  }

  public void setAddress(String paramString)
  {
    this.address = paramString;
  }

  public String getPostCode()
  {
    return this.postCode;
  }

  public void setPostCode(String paramString)
  {
    this.postCode = paramString;
  }

  public String getEmail()
  {
    return this.email;
  }

  public void setEmail(String paramString)
  {
    this.email = paramString;
  }

  public String getZoneCode()
  {
    return this.zoneCode;
  }

  public void setZoneCode(String paramString)
  {
    this.zoneCode = paramString;
  }

  public String getIndustryCode()
  {
    return this.industryCode;
  }

  public void setIndustryCode(String paramString)
  {
    this.industryCode = paramString;
  }

  public String getOrganizationCode()
  {
    return this.organizationCode;
  }

  public void setOrganizationCode(String paramString)
  {
    this.organizationCode = paramString;
  }

  public String getCorporateRepresentative()
  {
    return this.corporateRepresentative;
  }

  public void setCorporateRepresentative(String paramString)
  {
    this.corporateRepresentative = paramString;
  }

  public String getNote()
  {
    return this.note;
  }

  public void setNote(String paramString)
  {
    this.note = paramString;
  }

  public String getExtendData()
  {
    return this.extendData;
  }

  public void setExtendData(String paramString)
  {
    this.extendData = paramString;
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

  public String getStatus()
  {
    return this.status;
  }

  public void setStatus(String paramString)
  {
    this.status = paramString;
  }

  public String getFirmId()
  {
    return this.firmId;
  }

  public void setFirmId(String paramString)
  {
    this.firmId = paramString;
  }

  public StandardModel.PrimaryInfo fetchPKey()
  {
    return new StandardModel.PrimaryInfo( "firmId", this.firmId);
  }
}
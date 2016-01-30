package gnnt.MEBS.bill.mgr.model.usermanage;

import gnnt.MEBS.common.mgr.model.StandardModel;
import gnnt.MEBS.common.mgr.model.StandardModel.PrimaryInfo;
import gnnt.MEBS.common.mgr.model.translate.ClassDiscription;
import java.util.Date;

public class MFirm
  extends StandardModel
{
  private static final long serialVersionUID = -2128172735312930898L;
  @ClassDiscription(name="交易商ID", description="")
  private String firmId;
  @ClassDiscription(name="交易商名称", description="")
  private String name;
  @ClassDiscription(name="交易商全称", description="")
  private String fullName;
  @ClassDiscription(name="交易商类型", description="")
  private Integer type;
  @ClassDiscription(name="联系人称", description="")
  private String contactMan;
  @ClassDiscription(name="证件类型", description="")
  private String certificateType;
  @ClassDiscription(name="证件号码", description="")
  private String certificateNO;
  @ClassDiscription(name="联系电话 ", description="")
  private String phone;
  @ClassDiscription(name="手机号码", description="")
  private Long mobile;
  @ClassDiscription(name="传真", description="")
  private String fax;
  @ClassDiscription(name="交易商地址", description="")
  private String address;
  @ClassDiscription(name="邮编", description="")
  private String postCode;
  @ClassDiscription(name="电子信箱", description="")
  private String email;
  @ClassDiscription(name="交易商所在区域代码", description="")
  private String zoneCode;
  @ClassDiscription(name="交易商所在行业代码", description="")
  private String industryCode;
  @ClassDiscription(name="组织机构代码 ", description="")
  private String organizationCode;
  @ClassDiscription(name="法人代表", description="")
  private String corporateRepresentative;
  @ClassDiscription(name="备注", description="")
  private String note;
  @ClassDiscription(name=" XML扩展信息", description="")
  private String extendData;
  @ClassDiscription(name="交易商创建时间 ", description="")
  private Date createTime;
  @ClassDiscription(name="交易商修改时间", description="")
  private Date modifyTime;
  @ClassDiscription(name="交易商状态", description="交易商状态：N：正常 Normal D：禁用 Disable E：删除 Erase")
  private String status;
  
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
  
  public Integer getType()
  {
    return this.type;
  }
  
  public void setType(Integer paramInteger)
  {
    this.type = paramInteger;
  }
  
  public String getStatus()
  {
    return this.status;
  }
  
  public void setStatus(String paramString)
  {
    this.status = paramString;
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
  
  public String getFax()
  {
    return this.fax;
  }
  
  public void setFax(String paramString)
  {
    this.fax = paramString;
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
  
  public String getNote()
  {
    return this.note;
  }
  
  public void setNote(String paramString)
  {
    this.note = paramString;
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
  
  public String getCertificateNO()
  {
    return this.certificateNO;
  }
  
  public String getCertificateType()
  {
    return this.certificateType;
  }
  
  public void setCertificateType(String paramString)
  {
    this.certificateType = paramString;
  }
  
  public void setCertificateNO(String paramString)
  {
    this.certificateNO = paramString;
  }
  
  public Long getMobile()
  {
    return this.mobile;
  }
  
  public void setMobile(Long paramLong)
  {
    this.mobile = paramLong;
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
  
  public StandardModel.PrimaryInfo fetchPKey()
  {
    return new StandardModel.PrimaryInfo("firmId", this.firmId);
  }
}

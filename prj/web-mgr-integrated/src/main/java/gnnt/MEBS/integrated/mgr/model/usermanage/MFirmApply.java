package gnnt.MEBS.integrated.mgr.model.usermanage;

import gnnt.MEBS.common.mgr.model.StandardModel;
import gnnt.MEBS.common.mgr.model.StandardModel.PrimaryInfo;
import gnnt.MEBS.common.mgr.model.translate.ClassDiscription;
import java.util.Date;

public class MFirmApply
  extends StandardModel
{
  private static final long serialVersionUID = -7932477428307299550L;
  @ClassDiscription(name="注册申请代码", description="")
  private Long applyID;
  @ClassDiscription(name="用户名", description="")
  private String userID;
  @ClassDiscription(name="密码", description="")
  private String password;
  @ClassDiscription(name="交易商简称", description="")
  private String name;
  @ClassDiscription(name="交易商全称", description="")
  private String fullName;
  @ClassDiscription(name="交易商类型", description="1：法人2：代理3：个人")
  private Integer type;
  @ClassDiscription(name="联系人", description="")
  private String contactMan;
  @ClassDiscription(name="证件类型", description="1.居民身份证   2.士官证  3.学生证  4.驾驶证 5.护照 6.港澳通行证")
  private Integer certificateType;
  @ClassDiscription(name="证件号码", description="")
  private String certificateNO;
  @ClassDiscription(name="电话", description="")
  private String phone;
  @ClassDiscription(name="手机号码", description="")
  private Long mobile;
  @ClassDiscription(name="传真", description="")
  private String fax;
  @ClassDiscription(name="地址 ", description="")
  private String address;
  @ClassDiscription(name="邮编", description="")
  private String postcode;
  @ClassDiscription(name="电子邮箱", description="")
  private String email;
  @ClassDiscription(name="所属地域编码", description="")
  private String zoneCode;
  @ClassDiscription(name="所属行业编码", description="")
  private String industryCode;
  @ClassDiscription(name="组织机构代码 ", description="")
  private String organizationCode;
  @ClassDiscription(name="法人代表", description="")
  private String corporateRepresentative;
  @ClassDiscription(name="备注", description="")
  private String note;
  @ClassDiscription(name="XML扩展信息 ", description="")
  private String extendData;
  @ClassDiscription(name="创建时间", description="")
  private Date createTime;
  @ClassDiscription(name="修改时间", description="")
  private Date modifyTime;
  @ClassDiscription(name="申请状态", description=" 0：待审核 1：审核通过  2：审核不通过")
  private Integer status;
  @ClassDiscription(name="审核意见", description="")
  private String auditNote;
  @ClassDiscription(name="会员选择", description="")
  private String brokerId;
  @ClassDiscription(name="银行卡数组形式", description="")
  private byte[] picture;
  @ClassDiscription(name="正面数组形式", description="")
  private byte[] picturecs;
  @ClassDiscription(name="反面数组形式", description="")
  private byte[] pictureos;
  @ClassDiscription(name="营业执照副本二进制形式", description="")
  private byte[] yingYePic;
  @ClassDiscription(name="税务登记证副本二进制形式", description="")
  private byte[] shuiWuPic;
  @ClassDiscription(name="组织机构代码证副本二进制形式", description="")
  private byte[] zuZhiPic;
  @ClassDiscription(name="开户许可证二进制形式", description="")
  private byte[] kaiHuPic;
  
  public Long getApplyID()
  {
    return this.applyID;
  }
  
  public void setApplyID(Long applyID)
  {
    this.applyID = applyID;
  }
  
  public String getUserID()
  {
    return this.userID;
  }
  
  public void setUserID(String userID)
  {
    this.userID = userID;
  }
  
  public String getPassword()
  {
    return this.password;
  }
  
  public void setPassword(String password)
  {
    this.password = password;
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
  
  public String getContactMan()
  {
    return this.contactMan;
  }
  
  public void setContactMan(String contactMan)
  {
    this.contactMan = contactMan;
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
  
  public String getPhone()
  {
    return this.phone;
  }
  
  public void setPhone(String phone)
  {
    this.phone = phone;
  }
  
  public Long getMobile()
  {
    return this.mobile;
  }
  
  public void setMobile(Long mobile)
  {
    this.mobile = mobile;
  }
  
  public String getFax()
  {
    return this.fax;
  }
  
  public void setFax(String fax)
  {
    this.fax = fax;
  }
  
  public String getAddress()
  {
    return this.address;
  }
  
  public void setAddress(String address)
  {
    this.address = address;
  }
  
  public String getPostcode()
  {
    return this.postcode;
  }
  
  public void setPostcode(String postcode)
  {
    this.postcode = postcode;
  }
  
  public String getEmail()
  {
    return this.email;
  }
  
  public void setEmail(String email)
  {
    this.email = email;
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
  
  public String getNote()
  {
    return this.note;
  }
  
  public void setNote(String note)
  {
    this.note = note;
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
  
  public Integer getStatus()
  {
    return this.status;
  }
  
  public void setStatus(Integer status)
  {
    this.status = status;
  }
  
  public String getAuditNote()
  {
    return this.auditNote;
  }
  
  public void setAuditNote(String auditNote)
  {
    this.auditNote = auditNote;
  }
  
  public StandardModel.PrimaryInfo fetchPKey()
  {
    return new StandardModel.PrimaryInfo("applyID", this.applyID);
  }
  
  public String getBrokerId()
  {
    return this.brokerId;
  }
  
  public void setBrokerId(String brokerId)
  {
    this.brokerId = brokerId;
  }
  
  public byte[] getPicture()
  {
    return this.picture;
  }
  
  public void setPicture(byte[] picture)
  {
    this.picture = picture;
  }
  
  public byte[] getPicturecs()
  {
    return this.picturecs;
  }
  
  public void setPicturecs(byte[] picturecs)
  {
    this.picturecs = picturecs;
  }
  
  public byte[] getPictureos()
  {
    return this.pictureos;
  }
  
  public void setPictureos(byte[] pictureos)
  {
    this.pictureos = pictureos;
  }
  
  public byte[] getYingYePic()
  {
    return this.yingYePic;
  }
  
  public void setYingYePic(byte[] yingYePic)
  {
    this.yingYePic = yingYePic;
  }
  
  public byte[] getShuiWuPic()
  {
    return this.shuiWuPic;
  }
  
  public void setShuiWuPic(byte[] shuiWuPic)
  {
    this.shuiWuPic = shuiWuPic;
  }
  
  public byte[] getZuZhiPic()
  {
    return this.zuZhiPic;
  }
  
  public void setZuZhiPic(byte[] zuZhiPic)
  {
    this.zuZhiPic = zuZhiPic;
  }
  
  public byte[] getKaiHuPic()
  {
    return this.kaiHuPic;
  }
  
  public void setKaiHuPic(byte[] kaiHuPic)
  {
    this.kaiHuPic = kaiHuPic;
  }
}

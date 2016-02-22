package gnnt.MEBS.account.model;

import gnnt.MEBS.base.model.Clone;
import gnnt.MEBS.base.model.inner.ClassDiscription;
import gnnt.MEBS.base.model.inner.ClassStatus;
import gnnt.MEBS.member.ActiveUser.MD5;
import java.math.BigDecimal;
import java.util.Date;

public class NetCustomerInZS
  extends Clone
{
  private String customerNo;
  private String name;
  private String memberNo;
  private String memberName;
  private Integer papersType;
  private String papersName;
  private String address;
  private String phone;
  private String fax;
  private String postCode;
  private String email;
  private String extendData;
  private String phonePWD;
  private String password;
  private String status;
  private String memberNoChange;
  private String brokerageNo;
  private String brokerageName;
  private String managerNo;
  private String managerName;
  private String organizationNo;
  private String organizationName;
  private Date createTime;
  private Date signTime;
  private Date activateTime;
  private Date freezeTime;
  private Date demiseTime;
  private BigDecimal useFunds;
  private String protocolno;
  private String accountagreement;
  private String riskoverbook;
  private String accountprotocol;
  private String instruction;
  private Date createtime_protocol;
  private CustomerStatus customerStatus;
  
  public String getBrokerageName()
  {
    return this.brokerageName;
  }
  
  public void setBrokerageName(String brokerageName)
  {
    this.brokerageName = brokerageName;
  }
  
  public String getManagerName()
  {
    return this.managerName;
  }
  
  public void setManagerName(String managerName)
  {
    this.managerName = managerName;
  }
  
  public String getOrganizationName()
  {
    return this.organizationName;
  }
  
  public void setOrganizationName(String organizationName)
  {
    this.organizationName = organizationName;
  }
  
  public String getPassword()
  {
    return this.password;
  }
  
  public void setPassword(String password)
  {
    this.password = password;
  }
  
  @ClassDiscription(name="密码")
  public String getPassword_v()
  {
    if ((this.password == null) || ("".equals(this.password))) {
      return null;
    }
    return MD5.getMD5("******", this.password);
  }
  
  public String getProtocolno()
  {
    return this.protocolno;
  }
  
  public void setProtocolno(String protocolno)
  {
    this.protocolno = protocolno;
  }
  
  public String getAccountagreement()
  {
    return this.accountagreement;
  }
  
  public void setAccountagreement(String accountagreement)
  {
    this.accountagreement = accountagreement;
  }
  
  public String getRiskoverbook()
  {
    return this.riskoverbook;
  }
  
  public void setRiskoverbook(String riskoverbook)
  {
    this.riskoverbook = riskoverbook;
  }
  
  public String getAccountprotocol()
  {
    return this.accountprotocol;
  }
  
  public void setAccountprotocol(String accountprotocol)
  {
    this.accountprotocol = accountprotocol;
  }
  
  public String getInstruction()
  {
    return this.instruction;
  }
  
  public void setInstruction(String instruction)
  {
    this.instruction = instruction;
  }
  
  public Date getCreatetime_protocol()
  {
    return this.createtime_protocol;
  }
  
  public void setCreatetime_protocol(Date createtime_protocol)
  {
    this.createtime_protocol = createtime_protocol;
  }
  
  public BigDecimal getUseFunds()
  {
    return this.useFunds;
  }
  
  public void setUseFunds(BigDecimal useFunds)
  {
    this.useFunds = useFunds;
  }
  
  public Date getCreateTime()
  {
    return this.createTime;
  }
  
  public void setCreateTime(Date createTime)
  {
    this.createTime = createTime;
  }
  
  public Date getSignTime()
  {
    return this.signTime;
  }
  
  public void setSignTime(Date signTime)
  {
    this.signTime = signTime;
  }
  
  public Date getActivateTime()
  {
    return this.activateTime;
  }
  
  public void setActivateTime(Date activateTime)
  {
    this.activateTime = activateTime;
  }
  
  public Date getFreezeTime()
  {
    return this.freezeTime;
  }
  
  public void setFreezeTime(Date freezeTime)
  {
    this.freezeTime = freezeTime;
  }
  
  public Date getDemiseTime()
  {
    return this.demiseTime;
  }
  
  public void setDemiseTime(Date demiseTime)
  {
    this.demiseTime = demiseTime;
  }
  
  @ClassDiscription(name="居间代码")
  public String getBrokerageNo()
  {
    return this.brokerageNo;
  }
  
  public void setBrokerageNo(String brokerageNo)
  {
    this.brokerageNo = brokerageNo;
  }
  
  public String getManagerNo()
  {
    return this.managerNo;
  }
  
  public void setManagerNo(String managerNo)
  {
    this.managerNo = managerNo;
  }
  
  @ClassDiscription(name="机构代码")
  public String getOrganizationNo()
  {
    return this.organizationNo;
  }
  
  public void setOrganizationNo(String organizationNo)
  {
    this.organizationNo = organizationNo;
  }
  
  @ClassDiscription(name="客户转移会员名称")
  public String getMemberNoChange()
  {
    return this.memberNoChange;
  }
  
  public void setMemberNoChange(String memberNoChange)
  {
    this.memberNoChange = memberNoChange;
  }
  
  @ClassDiscription(name="客户状态", isStatus=true)
  @ClassStatus(status={@gnnt.MEBS.base.model.inner.StatusDiscription(key="C", value="已开户"), @gnnt.MEBS.base.model.inner.StatusDiscription(key="U", value="未激活"), @gnnt.MEBS.base.model.inner.StatusDiscription(key="N", value="正常"), @gnnt.MEBS.base.model.inner.StatusDiscription(key="F", value="冻结"), @gnnt.MEBS.base.model.inner.StatusDiscription(key="D", value="终止")})
  public String getStatus()
  {
    if (this.customerStatus == null) {
      return this.status;
    }
    return this.customerStatus.getStatus();
  }
  
  public void setStatus(String status)
  {
    this.customerStatus.setStatus(status);
  }
  
  public CustomerStatus getCustomerStatus()
  {
    return this.customerStatus;
  }
  
  public void setCustomerStatus(CustomerStatus customerStatus)
  {
    this.customerStatus = customerStatus;
  }
  
  public NetCustomerInZS() {}
  
  public NetCustomerInZS(String customerNo, String name, String memberNo, String memberName, Integer papersType, String papersName, String address, String phone, String fax, String postCode, String email, String extendData, String phonePWD, String status, String brokerageNo, String brokerageName, String managerNo, String managerName, String organizationNO, String organizationName, Date createTime, Date signTime, Date activateTime, Date freezeTime, Date demiseTime, BigDecimal useFunds, String protocolno, String accountagreement, String riskoverbook, String accountprotocol, String instruction, Date createtime_protocol)
  {
    this.customerNo = customerNo;
    this.name = name;
    this.memberNo = memberNo;
    this.memberName = memberName;
    this.papersType = papersType;
    this.papersName = papersName;
    this.address = address;
    this.phone = phone;
    this.fax = fax;
    this.postCode = postCode;
    this.email = email;
    this.extendData = extendData;
    this.phonePWD = phonePWD;
    this.status = status;
    this.brokerageNo = brokerageNo;
    this.brokerageName = brokerageName;
    this.managerNo = managerNo;
    this.managerName = managerName;
    this.organizationNo = organizationNO;
    this.organizationName = organizationName;
    this.createTime = createTime;
    this.signTime = signTime;
    this.activateTime = activateTime;
    this.freezeTime = freezeTime;
    this.demiseTime = demiseTime;
    this.useFunds = useFunds;
    this.protocolno = protocolno;
    this.accountagreement = accountagreement;
    this.riskoverbook = riskoverbook;
    this.accountprotocol = accountprotocol;
    this.instruction = instruction;
    this.createtime_protocol = createtime_protocol;
  }
  
  @ClassDiscription(name="交易账户", key=true, keyWord=true)
  public String getCustomerNo()
  {
    return this.customerNo;
  }
  
  public void setCustomerNo(String id)
  {
    this.customerNo = id;
  }
  
  @ClassDiscription(name="客户名称")
  public String getName()
  {
    return this.name;
  }
  
  public void setName(String name)
  {
    this.name = name;
  }
  
  @ClassDiscription(name="证件类型", isStatus=true)
  @ClassStatus(status={@gnnt.MEBS.base.model.inner.StatusDiscription(key="1", value="机构代码"), @gnnt.MEBS.base.model.inner.StatusDiscription(key="2", value="身份证"), @gnnt.MEBS.base.model.inner.StatusDiscription(key="3", value="护照"), @gnnt.MEBS.base.model.inner.StatusDiscription(key="4", value="营业执照")})
  public Integer getPapersType()
  {
    return this.papersType;
  }
  
  public void setPapersType(Integer papersType)
  {
    this.papersType = papersType;
  }
  
  @ClassDiscription(name="地址")
  public String getAddress()
  {
    return this.address;
  }
  
  public void setAddress(String address)
  {
    this.address = address;
  }
  
  @ClassDiscription(name="电话")
  public String getPhone()
  {
    return this.phone;
  }
  
  public void setPhone(String phone)
  {
    this.phone = phone;
  }
  
  @ClassDiscription(name="传真")
  public String getFax()
  {
    return this.fax;
  }
  
  public void setFax(String fax)
  {
    this.fax = fax;
  }
  
  @ClassDiscription(name="邮编")
  public String getPostCode()
  {
    return this.postCode;
  }
  
  public void setPostCode(String postCode)
  {
    this.postCode = postCode;
  }
  
  @ClassDiscription(name="电子邮箱")
  public String getEmail()
  {
    return this.email;
  }
  
  public void setEmail(String email)
  {
    this.email = email;
  }
  
  public String getExtendData()
  {
    return this.extendData;
  }
  
  public void setExtendData(String extendData)
  {
    this.extendData = extendData;
  }
  
  @ClassDiscription(name="会员编号", keyWord=true)
  public String getMemberNo()
  {
    return this.memberNo;
  }
  
  public void setMemberNo(String memberNo)
  {
    this.memberNo = memberNo;
  }
  
  @ClassDiscription(name="会员名称", keyWord=false)
  public String getMemberName()
  {
    return this.memberName;
  }
  
  public void setMemberName(String memberName)
  {
    this.memberName = memberName;
  }
  
  @ClassDiscription(name="证件号码")
  public String getPapersName()
  {
    return this.papersName;
  }
  
  public void setPapersName(String papersName)
  {
    this.papersName = papersName;
  }
  
  public String getPhonePWD()
  {
    return this.phonePWD;
  }
  
  public void setPhonePWD(String phonePWD)
  {
    this.phonePWD = phonePWD;
  }
  
  @ClassDiscription(name="电话密码")
  public String getPhonePWD_v()
  {
    if ((this.phonePWD == null) || ("".equals(this.phonePWD))) {
      return null;
    }
    return MD5.getMD5("******", this.phonePWD);
  }
  
  public String getId()
  {
    return this.customerNo;
  }
  
  public void setPrimary(String primary)
  {
    this.customerNo = primary;
  }
}

package gnnt.MEBS.account.model;

import gnnt.MEBS.base.model.Clone;
import gnnt.MEBS.base.model.inner.ClassDiscription;
import gnnt.MEBS.base.model.inner.ClassStatus;
import java.util.Date;

public class SpecialMember
  extends Clone
{
  private String s_memberNo;
  private String name;
  private Integer papersType;
  private String papersName;
  private String address;
  private String phone;
  private String fax;
  private String postCode;
  private String email;
  private String extendData;
  private String signNo;
  private SpecialMemberStatus specialMemberStatus;
  private Date createTime;
  private String password;
  
  public String getPassword()
  {
    return this.password;
  }
  
  public void setPassword(String password)
  {
    this.password = password;
  }
  
  public Date getCreateTime()
  {
    return this.createTime;
  }
  
  public void setCreateTime(Date createTime)
  {
    this.createTime = createTime;
  }
  
  public SpecialMemberStatus getSpecialMemberStatus()
  {
    return this.specialMemberStatus;
  }
  
  public void setSpecialMemberStatus(SpecialMemberStatus specialMemberStatus)
  {
    this.specialMemberStatus = specialMemberStatus;
  }
  
  public String getId()
  {
    return this.s_memberNo;
  }
  
  @ClassDiscription(name="特别会员名称", keyWord=true)
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
  
  @ClassDiscription(name="XML扩展信息")
  public String getExtendData()
  {
    return this.extendData;
  }
  
  public void setExtendData(String extendData)
  {
    this.extendData = extendData;
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
  
  @ClassDiscription(name="特别会员编号", key=true, keyWord=true)
  public String getS_memberNo()
  {
    return this.s_memberNo;
  }
  
  public void setS_memberNo(String s_memberNo)
  {
    this.s_memberNo = s_memberNo;
  }
  
  public void setId(String id)
  {
    this.s_memberNo = id;
  }
  
  public String getSignNo()
  {
    return this.signNo;
  }
  
  public void setSignNo(String signNo)
  {
    this.signNo = signNo;
  }
  
  @ClassDiscription(name="特别会员状态", isStatus=true)
  @ClassStatus(status={@gnnt.MEBS.base.model.inner.StatusDiscription(key="C", value="已入会"), @gnnt.MEBS.base.model.inner.StatusDiscription(key="U", value="未激活"), @gnnt.MEBS.base.model.inner.StatusDiscription(key="N", value="正常"), @gnnt.MEBS.base.model.inner.StatusDiscription(key="F", value="冻结"), @gnnt.MEBS.base.model.inner.StatusDiscription(key="D", value="终止")})
  public String getStatus()
  {
    if (this.specialMemberStatus == null) {
      return null;
    }
    return this.specialMemberStatus.getStatus();
  }
  
  public void setPrimary(String primary)
  {
    this.s_memberNo = primary;
  }
  
  public String forTree()
  {
    String treeString = "<li><input type=\"checkbox\" name=\"checks\" id=\"memberInfo\" value=\"" + 
      this.s_memberNo + "\"> " + 
      " <img src=\"../../tree1/memberInfo.gif\" align=\"absmiddle\"/><label id=\"" + this.s_memberNo + "_memberInfo\" value=\"" + this.s_memberNo + "\">" + this.name + "</label>";
    treeString = treeString + "</li>";
    return treeString;
  }
}

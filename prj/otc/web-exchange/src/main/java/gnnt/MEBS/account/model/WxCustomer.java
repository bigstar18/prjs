package gnnt.MEBS.account.model;

import gnnt.MEBS.base.model.Clone;
import gnnt.MEBS.base.model.inner.ClassDiscription;
import gnnt.MEBS.base.model.inner.ClassStatus;
import gnnt.MEBS.member.ActiveUser.MD5;
import java.util.Date;

public class WxCustomer
  extends Clone
{
  private String customerno;
  private String name;
  private String memberno;
  private String memberName;
  private String paperscode;
  private String phonepwd;
  private String address;
  private String contactman;
  private String phone;
  private String fax;
  private String postcode;
  private String email;
  private String note;
  private String extenddata;
  private String wxno;
  private String wxnickname;
  private int paperstype;
  private Date createtime;
  private Date signtime;
  private Date activatetime;
  private Date freezetime;
  private Date demisetime;
  private String status;
  private CustomerStatus customerStatus;
  
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
  
  public WxCustomer() {}
  
  public WxCustomer(String customerno, String name, String memberno, String memberName, String paperscode, String phonepwd, String address, String contactman, String phone, String fax, String postcode, String email, String note, String extenddata, String wxno, String wxnickname, int paperstype, Date createtime, Date signtime, Date activatetime, Date freezetime, Date demisetime, String status)
  {
    this.customerno = customerno;
    this.name = name;
    this.memberno = memberno;
    this.memberName = memberName;
    this.paperscode = paperscode;
    this.phonepwd = phonepwd;
    this.address = address;
    this.contactman = contactman;
    this.phone = phone;
    this.fax = fax;
    this.postcode = postcode;
    this.email = email;
    this.note = note;
    this.extenddata = extenddata;
    this.wxno = wxno;
    this.wxnickname = wxnickname;
    this.paperstype = paperstype;
    this.createtime = createtime;
    this.signtime = signtime;
    this.activatetime = activatetime;
    this.freezetime = freezetime;
    this.demisetime = demisetime;
    this.status = status;
  }
  
  @ClassDiscription(name="客户号")
  public String getCustomerno()
  {
    return this.customerno;
  }
  
  public void setCustomerno(String customerno)
  {
    this.customerno = customerno;
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
  
  @ClassDiscription(name="会员编号")
  public String getMemberno()
  {
    return this.memberno;
  }
  
  public void setMemberno(String memberno)
  {
    this.memberno = memberno;
  }
  
  @ClassDiscription(name="会员名称")
  public String getMemberName()
  {
    return this.memberName;
  }
  
  public void setMemberName(String memberName)
  {
    this.memberName = memberName;
  }
  
  @ClassDiscription(name="证件号")
  public String getPaperscode()
  {
    return this.paperscode;
  }
  
  public void setPaperscode(String paperscode)
  {
    this.paperscode = paperscode;
  }
  
  public String getPhonepwd()
  {
    return this.phonepwd;
  }
  
  public void setPhonepwd(String phonepwd)
  {
    this.phonepwd = phonepwd;
  }
  
  @ClassDiscription(name="电话密码")
  public String getPhonepwd_v()
  {
    if ((this.phonepwd == null) || ("".equals(this.phonepwd))) {
      return null;
    }
    return MD5.getMD5("******", this.phonepwd);
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
  
  @ClassDiscription(name="联系人")
  public String getContactman()
  {
    return this.contactman;
  }
  
  public void setContactman(String contactman)
  {
    this.contactman = contactman;
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
  public String getPostcode()
  {
    return this.postcode;
  }
  
  public void setPostcode(String postcode)
  {
    this.postcode = postcode;
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
  
  @ClassDiscription(name="备注")
  public String getNote()
  {
    return this.note;
  }
  
  public void setNote(String note)
  {
    this.note = note;
  }
  
  @ClassDiscription(name="xml扩展信息")
  public String getExtenddata()
  {
    return this.extenddata;
  }
  
  public void setExtenddata(String extenddata)
  {
    this.extenddata = extenddata;
  }
  
  @ClassDiscription(name="微信号")
  public String getWxno()
  {
    return this.wxno;
  }
  
  public void setWxno(String wxno)
  {
    this.wxno = wxno;
  }
  
  @ClassDiscription(name="微信昵称")
  public String getWxnickname()
  {
    return this.wxnickname;
  }
  
  public void setWxnickname(String wxnickname)
  {
    this.wxnickname = wxnickname;
  }
  
  @ClassDiscription(name="证件类型")
  public int getPaperstype()
  {
    return this.paperstype;
  }
  
  public void setPaperstype(int paperstype)
  {
    this.paperstype = paperstype;
  }
  
  @ClassDiscription(name="创建时间")
  public Date getCreatetime()
  {
    return this.createtime;
  }
  
  public void setCreatetime(Date createtime)
  {
    this.createtime = createtime;
  }
  
  @ClassDiscription(name="签约时间")
  public Date getSigntime()
  {
    return this.signtime;
  }
  
  public void setSigntime(Date signtime)
  {
    this.signtime = signtime;
  }
  
  @ClassDiscription(name="激活时间")
  public Date getActivatetime()
  {
    return this.activatetime;
  }
  
  public void setActivatetime(Date activatetime)
  {
    this.activatetime = activatetime;
  }
  
  @ClassDiscription(name="冻结时间")
  public Date getFreezetime()
  {
    return this.freezetime;
  }
  
  public void setFreezetime(Date freezetime)
  {
    this.freezetime = freezetime;
  }
  
  @ClassDiscription(name="注销时间")
  public Date getDemisetime()
  {
    return this.demisetime;
  }
  
  public void setDemisetime(Date demisetime)
  {
    this.demisetime = demisetime;
  }
  
  public String getId()
  {
    return this.customerno;
  }
  
  public void setPrimary(String primary)
  {
    this.customerno = primary;
  }
}

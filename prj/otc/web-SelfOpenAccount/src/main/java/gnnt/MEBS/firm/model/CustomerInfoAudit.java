package gnnt.MEBS.firm.model;

import java.util.Date;

public class CustomerInfoAudit
  extends Clone
{
  private String customerNo;
  private String name;
  private String fullName;
  private String memberNo;
  private Integer papersType;
  private String papersName;
  private String status;
  private String bank;
  private String bankAccount;
  private String contactman;
  private String contactsPhone;
  private String phonePwd;
  private String address;
  private String phone;
  private String fax;
  private String postCode;
  private String email;
  private String note;
  private Date createTime;
  private Date modifyTime;
  
  public String toString()
  {
    return 
    








      "CustomerInfoAudit [address=" + this.address + ", status=" + this.status + ", bank=" + this.bank + ", bankAccount=" + this.bankAccount + ", contactman=" + this.contactman + ", contactsPhone=" + this.contactsPhone + ", createTime=" + this.createTime + ", customerId=" + this.customerNo + ", email=" + this.email + ", fax=" + this.fax + ", fullName=" + this.fullName + ", memberNo=" + this.memberNo + ", modifyTime=" + this.modifyTime + ", name=" + this.name + ", note=" + this.note + ", papersName=" + this.papersName + ", papersType=" + this.papersType + ", phone=" + this.phone + ", phonePwd=" + this.phonePwd + ", postCode=" + this.postCode + "]";
  }
  
  public String getCustomerNo()
  {
    return this.customerNo;
  }
  
  public void setCustomerNo(String customerNo)
  {
    this.customerNo = customerNo;
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
  
  public String getMemberNo()
  {
    return this.memberNo;
  }
  
  public void setMemberNo(String memberNo)
  {
    this.memberNo = memberNo;
  }
  
  public Integer getPapersType()
  {
    return this.papersType;
  }
  
  public void setPapersType(Integer papersType)
  {
    this.papersType = papersType;
  }
  
  public String getPapersName()
  {
    return this.papersName;
  }
  
  public void setPapersName(String papersName)
  {
    this.papersName = papersName;
  }
  
  public String getStatus()
  {
    return this.status;
  }
  
  public void setStatus(String status)
  {
    this.status = status;
  }
  
  public String getBank()
  {
    return this.bank;
  }
  
  public void setBank(String bank)
  {
    this.bank = bank;
  }
  
  public String getBankAccount()
  {
    return this.bankAccount;
  }
  
  public void setBankAccount(String bankAccount)
  {
    this.bankAccount = bankAccount;
  }
  
  public String getContactman()
  {
    return this.contactman;
  }
  
  public void setContactman(String contactman)
  {
    this.contactman = contactman;
  }
  
  public String getContactsPhone()
  {
    return this.contactsPhone;
  }
  
  public void setContactsPhone(String contactsPhone)
  {
    this.contactsPhone = contactsPhone;
  }
  
  public String getPhonePwd()
  {
    return this.phonePwd;
  }
  
  public void setPhonePwd(String phonePwd)
  {
    this.phonePwd = phonePwd;
  }
  
  public String getAddress()
  {
    return this.address;
  }
  
  public void setAddress(String address)
  {
    this.address = address;
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
}

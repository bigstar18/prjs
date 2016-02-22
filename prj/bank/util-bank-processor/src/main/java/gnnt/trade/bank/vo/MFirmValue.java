package gnnt.trade.bank.vo;

import java.io.Serializable;

public class MFirmValue
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  public String firmID;
  public String name;
  public String fullName;
  public long type = 1L;
  public String bankName;
  public String status = "N";
  public String bankAccount;
  public String address;
  public String contactMan;
  public String phone;
  public String fax;
  public String postCode;
  public String email;
  public String note;
  public String zonecode;
  public String INDUSTRYCODE;
  public String EXTENDDATA;
  public java.sql.Date createTime = new java.sql.Date(new java.util.Date().getTime());

  public java.sql.Date MODIFYTIME = new java.sql.Date(new java.util.Date().getTime());

  public String TARIFFID = "none";
  public String FIRMCATEGORYNAME;
  public String BROKERID;

  public String getFirmID()
  {
    return this.firmID;
  }

  public void setFirmID(String firmID) {
    this.firmID = firmID;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getFullName() {
    return this.fullName;
  }

  public void setFullName(String fullName) {
    this.fullName = fullName;
  }

  public long getType() {
    return this.type;
  }

  public void setType(long type) {
    this.type = type;
  }

  public String getBankName() {
    return this.bankName;
  }

  public void setBankName(String bankName) {
    this.bankName = bankName;
  }

  public String getStatus() {
    return this.status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getBankAccount() {
    return this.bankAccount;
  }

  public void setBankAccount(String bankAccount) {
    this.bankAccount = bankAccount;
  }

  public String getAddress() {
    return this.address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public String getContactMan() {
    return this.contactMan;
  }

  public void setContactMan(String contactMan) {
    this.contactMan = contactMan;
  }

  public String getPhone() {
    return this.phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public String getFax() {
    return this.fax;
  }

  public void setFax(String fax) {
    this.fax = fax;
  }

  public String getPostCode() {
    return this.postCode;
  }

  public void setPostCode(String postCode) {
    this.postCode = postCode;
  }

  public String getEmail() {
    return this.email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getNote() {
    return this.note;
  }

  public void setNote(String note) {
    this.note = note;
  }

  public String getZonecode() {
    return this.zonecode;
  }

  public void setZonecode(String zonecode) {
    this.zonecode = zonecode;
  }

  public String getINDUSTRYCODE() {
    return this.INDUSTRYCODE;
  }

  public void setINDUSTRYCODE(String iNDUSTRYCODE) {
    this.INDUSTRYCODE = iNDUSTRYCODE;
  }

  public String getEXTENDDATA() {
    return this.EXTENDDATA;
  }

  public void setEXTENDDATA(String eXTENDDATA) {
    this.EXTENDDATA = eXTENDDATA;
  }

  public java.sql.Date getCreateTime() {
    return this.createTime;
  }

  public void setCreateTime(java.sql.Date createTime) {
    this.createTime = createTime;
  }

  public java.sql.Date getMODIFYTIME() {
    return this.MODIFYTIME;
  }

  public void setMODIFYTIME(java.sql.Date mODIFYTIME) {
    this.MODIFYTIME = mODIFYTIME;
  }

  public String getTARIFFID() {
    return this.TARIFFID;
  }

  public void setTARIFFID(String tARIFFID) {
    this.TARIFFID = tARIFFID;
  }

  public String getFIRMCATEGORYNAME() {
    return this.FIRMCATEGORYNAME;
  }

  public void setFIRMCATEGORYNAME(String fIRMCATEGORYNAME) {
    this.FIRMCATEGORYNAME = fIRMCATEGORYNAME;
  }

  public String getBROKERID() {
    return this.BROKERID;
  }

  public void setBROKERID(String bROKERID) {
    this.BROKERID = bROKERID;
  }
}
package gnnt.MEBS.checkLogon.po.broker;

import gnnt.MEBS.checkLogon.po.Clone;
import java.util.Date;

public class BrokerPO extends Clone
{
  private String brokerID;
  private transient String password;
  private String name;
  private String telephone;
  private String mobile;
  private String email;
  private String address;
  private String note;
  private String firmID;
  private Integer areaID;
  private Integer memberType;
  private Date timeLimit;

  public String getBrokerID()
  {
    return this.brokerID;
  }

  public void setBrokerID(String brokerID)
  {
    this.brokerID = brokerID;
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

  public String getTelephone()
  {
    return this.telephone;
  }

  public void setTelephone(String telephone)
  {
    this.telephone = telephone;
  }

  public String getMobile()
  {
    return this.mobile;
  }

  public void setMobile(String mobile)
  {
    this.mobile = mobile;
  }

  public String getEmail()
  {
    return this.email;
  }

  public void setEmail(String email)
  {
    this.email = email;
  }

  public String getAddress()
  {
    return this.address;
  }

  public void setAddress(String address)
  {
    this.address = address;
  }

  public String getNote()
  {
    return this.note;
  }

  public void setNote(String note)
  {
    this.note = note;
  }

  public String getFirmID()
  {
    return this.firmID;
  }

  public void setFirmID(String firmID)
  {
    this.firmID = firmID;
  }

  public Integer getAreaID()
  {
    return this.areaID;
  }

  public void setAreaID(Integer areaID)
  {
    this.areaID = areaID;
  }

  public Integer getMemberType()
  {
    return this.memberType;
  }

  public void setMemberType(Integer memberType)
  {
    this.memberType = memberType;
  }

  public Date getTimeLimit()
  {
    return this.timeLimit;
  }

  public void setTimeLimit(Date timeLimit)
  {
    this.timeLimit = timeLimit;
  }
}
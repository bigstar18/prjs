package gnnt.MEBS.checkLogon.po.broker;

import gnnt.MEBS.checkLogon.po.Clone;
import java.util.Date;

public class BrokerAgePO extends Clone
{
  private String brokerAgeID;
  private transient String password;
  private String name;
  private String brokerID;
  private String pbrokerAgeID;
  private Date creatTime;

  public String getBrokerAgeID()
  {
    return this.brokerAgeID;
  }

  public void setBrokerAgeID(String brokerAgeID)
  {
    this.brokerAgeID = brokerAgeID;
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

  public String getBrokerID()
  {
    return this.brokerID;
  }

  public void setBrokerID(String brokerID)
  {
    this.brokerID = brokerID;
  }

  public String getPbrokerAgeID()
  {
    return this.pbrokerAgeID;
  }

  public void setPbrokerAgeID(String pbrokerAgeID)
  {
    this.pbrokerAgeID = pbrokerAgeID;
  }

  public Date getCreatTime()
  {
    return this.creatTime;
  }

  public void setCreatTime(Date creatTime)
  {
    this.creatTime = creatTime;
  }
}
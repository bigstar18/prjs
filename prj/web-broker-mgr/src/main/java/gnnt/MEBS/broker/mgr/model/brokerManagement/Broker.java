package gnnt.MEBS.broker.mgr.model.brokerManagement;

import gnnt.MEBS.common.mgr.model.StandardModel;
import gnnt.MEBS.common.mgr.model.StandardModel.PrimaryInfo;
import java.util.Date;
import java.util.Set;

public class Broker extends StandardModel
{
  private static final long serialVersionUID = 8980067108621178867L;
  private String brokerId;
  private String password;
  private String name;
  private String telephone;
  private String mobile;
  private String email;
  private String address;
  private String note;
  private String firmId;
  private Integer areaIds;
  private Integer borkerType;
  private Date timeLimit;
  private String marketManager;
  private Date modifyTime;
  private Set<BrokerMenu> rightSet;
  private BrokerType brokerType;
  private BrokerArea brokerArea;

  public BrokerType getBrokerType()
  {
    return this.brokerType;
  }

  public void setBrokerType(BrokerType paramBrokerType)
  {
    this.brokerType = paramBrokerType;
  }

  public String getBrokerId()
  {
    return this.brokerId;
  }

  public void setBrokerId(String paramString)
  {
    this.brokerId = paramString;
  }

  public String getPassword()
  {
    return this.password;
  }

  public void setPassword(String paramString)
  {
    this.password = paramString;
  }

  public String getName()
  {
    return this.name;
  }

  public void setName(String paramString)
  {
    this.name = paramString;
  }

  public String getTelephone()
  {
    return this.telephone;
  }

  public void setTelephone(String paramString)
  {
    this.telephone = paramString;
  }

  public String getMobile()
  {
    return this.mobile;
  }

  public void setMobile(String paramString)
  {
    this.mobile = paramString;
  }

  public String getEmail()
  {
    return this.email;
  }

  public void setEmail(String paramString)
  {
    this.email = paramString;
  }

  public String getAddress()
  {
    return this.address;
  }

  public void setAddress(String paramString)
  {
    this.address = paramString;
  }

  public String getNote()
  {
    return this.note;
  }

  public void setNote(String paramString)
  {
    this.note = paramString;
  }

  public String getFirmId()
  {
    return this.firmId;
  }

  public void setFirmId(String paramString)
  {
    this.firmId = paramString;
  }

  public Integer getAreaIds()
  {
    return this.areaIds;
  }

  public void setAreaIds(Integer paramInteger)
  {
    this.areaIds = paramInteger;
  }

  public Date getTimeLimit()
  {
    return this.timeLimit;
  }

  public void setTimeLimit(Date paramDate)
  {
    this.timeLimit = paramDate;
  }

  public String getMarketManager()
  {
    return this.marketManager;
  }

  public void setMarketManager(String paramString)
  {
    this.marketManager = paramString;
  }

  public Date getModifyTime()
  {
    return this.modifyTime;
  }

  public void setModifyTime(Date paramDate)
  {
    this.modifyTime = paramDate;
  }

  public BrokerArea getBrokerArea()
  {
    return this.brokerArea;
  }

  public void setBrokerArea(BrokerArea paramBrokerArea)
  {
    this.brokerArea = paramBrokerArea;
  }

  public Set<BrokerMenu> getRightSet()
  {
    return this.rightSet;
  }

  public void setRightSet(Set<BrokerMenu> paramSet)
  {
    this.rightSet = paramSet;
  }

  public StandardModel.PrimaryInfo fetchPKey()
  {
    return new StandardModel.PrimaryInfo( "brokerId", this.brokerId);
  }

  public Integer getBorkerType()
  {
    return this.borkerType;
  }

  public void setBorkerType(Integer paramInteger)
  {
    this.borkerType = paramInteger;
  }
}
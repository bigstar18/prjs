package gnnt.MEBS.common.broker.model;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class BrokerAge extends StandardModel
{
  private static final long serialVersionUID = -1185871234812304202L;
  private String brokerAgeId;
  private String name;
  private transient String password;
  private String idCard;
  private String telephone;
  private String mobile;
  private String email;
  private String address;
  private String postCode;
  private String note;
  private String brokerId;
  private Broker broker;
  private String pbrokerAgeId;
  private BrokerAge brokerAge;
  private Date creatTime;
  private Map<Long, Right> rightMap;
  private String ipAddress;
  private long sessionId;

  public String getBrokerAgeId()
  {
    return this.brokerAgeId;
  }

  public void setBrokerAgeId(String paramString)
  {
    this.brokerAgeId = paramString;
  }

  public String getName()
  {
    return this.name;
  }

  public void setName(String paramString)
  {
    this.name = paramString;
  }

  public String getPassword()
  {
    return this.password;
  }

  public void setPassword(String paramString)
  {
    this.password = paramString;
  }

  public String getIdCard()
  {
    return this.idCard;
  }

  public void setIdCard(String paramString)
  {
    this.idCard = paramString;
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

  public String getPostCode()
  {
    return this.postCode;
  }

  public void setPostCode(String paramString)
  {
    this.postCode = paramString;
  }

  public String getNote()
  {
    return this.note;
  }

  public void setNote(String paramString)
  {
    this.note = paramString;
  }

  public String getBrokerId()
  {
    return this.brokerId;
  }

  public void setBrokerId(String paramString)
  {
    this.brokerId = paramString;
  }

  public Broker getBroker()
  {
    return this.broker;
  }

  public void setBroker(Broker paramBroker)
  {
    this.broker = paramBroker;
  }

  public String getPbrokerAgeId()
  {
    return this.pbrokerAgeId;
  }

  public void setPbrokerAgeId(String paramString)
  {
    this.pbrokerAgeId = paramString;
  }

  public BrokerAge getBrokerAge()
  {
    return this.brokerAge;
  }

  public void setBrokerAge(BrokerAge paramBrokerAge)
  {
    this.brokerAge = paramBrokerAge;
  }

  public Date getCreatTime()
  {
    return this.creatTime;
  }

  public void setCreatTime(Date paramDate)
  {
    this.creatTime = paramDate;
  }

  public String getIpAddress()
  {
    return this.ipAddress;
  }

  public void setIpAddress(String paramString)
  {
    this.ipAddress = paramString;
  }

  public long getSessionId()
  {
    return this.sessionId;
  }

  public void setSessionId(long paramLong)
  {
    this.sessionId = paramLong;
  }

  public Map<Long, Right> getRightMap()
  {
    if (this.rightMap == null)
    {
      this.rightMap = new HashMap();
      Set localSet = this.broker.getRightSet();
      if (localSet != null)
      {
        Iterator localIterator = localSet.iterator();
        while (localIterator.hasNext())
        {
          Right localRight = (Right)localIterator.next();
          if ((!this.rightMap.containsKey(localRight.getId())) && (localRight.getOnlyMember().equals("0")))
            this.rightMap.put(localRight.getId(), localRight);
        }
      }
    }
    return this.rightMap;
  }

  public boolean equals(Object paramObject)
  {
    boolean bool = true;
    if (((paramObject instanceof BrokerAge)) && (paramObject != null))
    {
      BrokerAge localBrokerAge = (BrokerAge)paramObject;
      if (!getBrokerId().equals(localBrokerAge.getBrokerId()))
        bool = false;
    }
    else
    {
      bool = false;
    }
    return bool;
  }

  public StandardModel.PrimaryInfo fetchPKey()
  {
    return new StandardModel.PrimaryInfo("brokerAgeId", this.brokerAgeId);
  }
}
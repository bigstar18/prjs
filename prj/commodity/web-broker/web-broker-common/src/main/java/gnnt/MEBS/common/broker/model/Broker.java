package gnnt.MEBS.common.broker.model;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class Broker extends StandardModel
{
  private static final long serialVersionUID = -1185871234800004202L;
  private String brokerId;
  private transient String password;
  private String name;
  private String telephone;
  private String mobile;
  private String email;
  private String address;
  private String note;
  private String firmId;
  private String areaId;
  private String memberType;
  private Date timeLimit;
  private Set<Right> rightSet;
  private Map<Long, Right> rightMap;
  private String ipAddress;
  private long sessionId;

  public String getBrokerId()
  {
    return this.brokerId;
  }

  public String getPassword()
  {
    return this.password;
  }

  public String getName()
  {
    return this.name;
  }

  public String getTelephone()
  {
    return this.telephone;
  }

  public String getMobile()
  {
    return this.mobile;
  }

  public String getEmail()
  {
    return this.email;
  }

  public String getAddress()
  {
    return this.address;
  }

  public String getNote()
  {
    return this.note;
  }

  public String getFirmId()
  {
    return this.firmId;
  }

  public String getAreaId()
  {
    return this.areaId;
  }

  public String getMemberType()
  {
    return this.memberType;
  }

  public Date getTimeLimit()
  {
    return this.timeLimit;
  }

  public void setBrokerId(String paramString)
  {
    this.brokerId = paramString;
  }

  public void setPassword(String paramString)
  {
    this.password = paramString;
  }

  public void setName(String paramString)
  {
    this.name = paramString;
  }

  public void setTelephone(String paramString)
  {
    this.telephone = paramString;
  }

  public void setMobile(String paramString)
  {
    this.mobile = paramString;
  }

  public void setEmail(String paramString)
  {
    this.email = paramString;
  }

  public void setAddress(String paramString)
  {
    this.address = paramString;
  }

  public void setNote(String paramString)
  {
    this.note = paramString;
  }

  public void setFirmId(String paramString)
  {
    this.firmId = paramString;
  }

  public void setAreaId(String paramString)
  {
    this.areaId = paramString;
  }

  public void setMemberType(String paramString)
  {
    this.memberType = paramString;
  }

  public void setTimeLimit(Date paramDate)
  {
    this.timeLimit = paramDate;
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

  public Set<Right> getRightSet()
  {
    return this.rightSet;
  }

  public void setRightSet(Set<Right> paramSet)
  {
    this.rightSet = paramSet;
  }

  public Map<Long, Right> getRightMap()
  {
    if (this.rightMap == null)
    {
      this.rightMap = new HashMap();
      Set localSet = this.rightSet;
      if (localSet != null)
      {
        Iterator localIterator = localSet.iterator();
        while (localIterator.hasNext())
        {
          Right localRight = (Right)localIterator.next();
          if (!this.rightMap.containsKey(localRight.getId()))
            this.rightMap.put(localRight.getId(), localRight);
        }
      }
    }
    return this.rightMap;
  }

  public boolean equals(Object paramObject)
  {
    boolean bool = true;
    if (((paramObject instanceof Broker)) && (paramObject != null))
    {
      Broker localBroker = (Broker)paramObject;
      if (!getBrokerId().equals(localBroker.getBrokerId()))
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
    return new StandardModel.PrimaryInfo("brokerId", this.brokerId);
  }
}
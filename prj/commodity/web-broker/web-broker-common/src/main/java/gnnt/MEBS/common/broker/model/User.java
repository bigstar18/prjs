package gnnt.MEBS.common.broker.model;

public class User
{
  private static final long serialVersionUID = -1185871234800004202L;
  private String userId;
  private Broker broker;
  private BrokerAge brokerAge;
  private String type;
  private String sql;
  private long sessionId;
  private String logonType;
  private String ipAddress;

  public String getLogonType()
  {
    return this.logonType;
  }

  public void setLogonType(String paramString)
  {
    this.logonType = paramString;
  }

  public String getIpAddress()
  {
    return this.ipAddress;
  }

  public void setIpAddress(String paramString)
  {
    this.ipAddress = paramString;
  }

  public String getUserId()
  {
    return this.userId;
  }

  public void setUserId(String paramString)
  {
    this.userId = paramString;
  }

  public Broker getBroker()
  {
    return this.broker;
  }

  public void setBroker(Broker paramBroker)
  {
    this.broker = paramBroker;
  }

  public BrokerAge getBrokerAge()
  {
    return this.brokerAge;
  }

  public void setBrokerAge(BrokerAge paramBrokerAge)
  {
    this.brokerAge = paramBrokerAge;
  }

  public String getType()
  {
    return this.type;
  }

  public void setType(String paramString)
  {
    this.type = paramString;
  }

  public String getSql()
  {
    return this.sql;
  }

  public void setSql(String paramString)
  {
    this.sql = paramString;
  }

  public long getSessionId()
  {
    return this.sessionId;
  }

  public void setSessionId(long paramLong)
  {
    this.sessionId = paramLong;
  }
}
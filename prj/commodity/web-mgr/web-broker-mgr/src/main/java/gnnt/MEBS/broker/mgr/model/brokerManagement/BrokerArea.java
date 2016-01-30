package gnnt.MEBS.broker.mgr.model.brokerManagement;

import gnnt.MEBS.common.mgr.model.StandardModel;
import gnnt.MEBS.common.mgr.model.StandardModel.PrimaryInfo;

public class BrokerArea extends StandardModel
  implements Comparable
{
  private static final long serialVersionUID = -7090420658163283711L;
  private Integer areaId;
  private String name;
  private Broker broker;

  public Integer getAreaId()
  {
    return this.areaId;
  }

  public void setAreaId(Integer paramInteger)
  {
    this.areaId = paramInteger;
  }

  public String getName()
  {
    return this.name;
  }

  public void setName(String paramString)
  {
    this.name = paramString;
  }

  public Broker getBroker()
  {
    return this.broker;
  }

  public void setBroker(Broker paramBroker)
  {
    this.broker = paramBroker;
  }

  public StandardModel.PrimaryInfo fetchPKey()
  {
    return new StandardModel.PrimaryInfo( "areaId", this.areaId);
  }

  public int compareTo(Object paramObject)
  {
    if (paramObject == null)
      return 0;
    if (!(paramObject instanceof BrokerArea))
      return 0;
    BrokerArea localBrokerArea = (BrokerArea)paramObject;
    if ((this.name == null) || (localBrokerArea.getName() == null))
      return 0;
    return this.name.compareTo(localBrokerArea.getName());
  }
}
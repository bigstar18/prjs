package gnnt.MEBS.broker.mgr.model.brokerManagement;

import gnnt.MEBS.common.mgr.model.StandardModel;
import gnnt.MEBS.common.mgr.model.StandardModel.PrimaryInfo;

public class BrokerType extends StandardModel
{
  private static final long serialVersionUID = 5868147049830203729L;
  private Integer borkerType;
  private String brokerName;
  private Broker broker;

  public Integer getBorkerType()
  {
    return this.borkerType;
  }

  public void setBorkerType(Integer paramInteger)
  {
    this.borkerType = paramInteger;
  }

  public String getBrokerName()
  {
    return this.brokerName;
  }

  public void setBrokerName(String paramString)
  {
    this.brokerName = paramString;
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
    return new StandardModel.PrimaryInfo( "borkerType", this.borkerType);
  }
}
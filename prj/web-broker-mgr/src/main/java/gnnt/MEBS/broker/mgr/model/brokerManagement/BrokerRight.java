package gnnt.MEBS.broker.mgr.model.brokerManagement;

import gnnt.MEBS.common.mgr.model.StandardModel;
import gnnt.MEBS.common.mgr.model.StandardModel.PrimaryInfo;

public class BrokerRight extends StandardModel
{
  private static final long serialVersionUID = -2163861898146463528L;
  private String brokerId;
  private BrokerMenu cright;

  public String getBrokerId()
  {
    return this.brokerId;
  }

  public void setBrokerId(String paramString)
  {
    this.brokerId = paramString;
  }

  public BrokerMenu getCright()
  {
    return this.cright;
  }

  public void setCright(BrokerMenu paramBrokerMenu)
  {
    this.cright = paramBrokerMenu;
  }

  public StandardModel.PrimaryInfo fetchPKey()
  {
    return null;
  }
}
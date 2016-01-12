package gnnt.MEBS.broker.mgr.service;

import gnnt.MEBS.broker.mgr.model.brokerDataquery.BrokerReward;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

public abstract interface BrokerDataqueryService
{
  public abstract List brokerTradeFee(HttpServletRequest paramHttpServletRequest);

  public abstract List breedTradeFee(HttpServletRequest paramHttpServletRequest);

  public abstract List firmTradeFee(HttpServletRequest paramHttpServletRequest);

  public abstract int handleReward(BrokerReward paramBrokerReward, double paramDouble, String paramString);
}
package gnnt.MEBS.broker.mgr.dao;

import gnnt.MEBS.broker.mgr.model.brokerManagement.Broker;
import gnnt.MEBS.common.mgr.dao.StandardDao;
import org.springframework.stereotype.Repository;

@Repository("brokerDAO")
public class BrokerDAO extends StandardDao
{
  public Broker getBrokerById(String paramString)
  {
    Broker localBroker = new Broker();
    localBroker.setBrokerId(paramString);
    return (Broker)super.get(localBroker);
  }
}
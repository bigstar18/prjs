package gnnt.MEBS.common.broker.service;

import gnnt.MEBS.common.broker.model.Broker;
import java.util.Set;
import org.apache.commons.logging.Log;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("com_brokerService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=false, rollbackFor={Exception.class})
public class BrokerService extends StandardService
{
  public Broker getBrokerByID(String paramString)
  {
    return getBrokerByID(paramString, true);
  }

  public Broker getBrokerByID(String paramString, boolean paramBoolean)
  {
    this.logger.debug("enter getBrokerByID");
    Broker localBroker1 = new Broker();
    localBroker1.setBrokerId(paramString);
    Broker localBroker2 = (Broker)super.get(localBroker1);
    if ((localBroker2 != null) && (paramBoolean))
      localBroker2.getRightSet().size();
    return localBroker2;
  }
}
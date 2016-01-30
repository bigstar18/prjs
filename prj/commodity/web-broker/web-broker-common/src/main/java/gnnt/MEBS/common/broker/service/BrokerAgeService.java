package gnnt.MEBS.common.broker.service;

import gnnt.MEBS.common.broker.model.Broker;
import gnnt.MEBS.common.broker.model.BrokerAge;
import java.util.Set;
import org.apache.commons.logging.Log;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("com_brokerAgeService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=false, rollbackFor={Exception.class})
public class BrokerAgeService extends StandardService
{
  public BrokerAge getBrokerAgeByID(String paramString)
  {
    return getBrokerAgeByID(paramString, true);
  }

  public BrokerAge getBrokerAgeByID(String paramString, boolean paramBoolean)
  {
    this.logger.debug("enter getBrokerByID");
    BrokerAge localBrokerAge1 = new BrokerAge();
    localBrokerAge1.setBrokerAgeId(paramString);
    BrokerAge localBrokerAge2 = (BrokerAge)super.get(localBrokerAge1);
    if ((localBrokerAge2 != null) && (paramBoolean))
      localBrokerAge2.getBroker().getRightSet().size();
    return localBrokerAge2;
  }
}
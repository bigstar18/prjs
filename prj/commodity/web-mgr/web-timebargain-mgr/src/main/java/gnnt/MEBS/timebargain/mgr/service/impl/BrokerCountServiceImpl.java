package gnnt.MEBS.timebargain.mgr.service.impl;

import gnnt.MEBS.common.mgr.service.StandardService;
import gnnt.MEBS.timebargain.mgr.dao.BrokerCountDao;
import gnnt.MEBS.timebargain.mgr.service.BrokerCountService;
import java.util.List;
import org.apache.commons.logging.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("brokerCountService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
public class BrokerCountServiceImpl extends StandardService
  implements BrokerCountService
{

  @Autowired
  @Qualifier("brokerCountDao")
  private BrokerCountDao brokerCountDao;

  public BrokerCountDao getBrokerFundsCountDao()
  {
    return this.brokerCountDao;
  }

  public List brokerFundsTable(String brokerID, String brokerName) {
    List list = null;
    list = this.brokerCountDao.brokerFundsTable(brokerID, brokerName);
    this.logger.debug("ServiceImplList=====" + list.size());
    return list;
  }

  public List brokerIndentTable(String brokerID, String commodityId, String flag)
  {
    return this.brokerCountDao.brokerIndentTable(brokerID, commodityId, flag);
  }

  public List historyBrokerIndentTable(String brokerID, String commodityId, String flag, String clearDate)
  {
    return this.brokerCountDao.historyBrokerIndentTable(brokerID, commodityId, flag, clearDate);
  }

  public List brokerTradeTable(String brokerID, String brokerName, String flag, String tradeType, String commodityId, String orderType) {
    return this.brokerCountDao.brokerTradeTable(brokerID, brokerName, flag, tradeType, commodityId, orderType);
  }

  public List historyBrokerTradeTable(String brokerID, String brokerName, String flag, String tradeType, String commodityId, String orderType, String beginDate, String endDate)
  {
    return this.brokerCountDao.historyBrokerTradeTable(brokerID, brokerName, flag, tradeType, commodityId, orderType, beginDate, endDate);
  }
}
package gnnt.MEBS.member.broker.services;

import gnnt.MEBS.base.query.PageInfo;
import gnnt.MEBS.base.query.QueryConditions;
import gnnt.MEBS.base.util.Arith;
import gnnt.MEBS.member.broker.dao.BrokerDao;
import gnnt.MEBS.member.broker.dao.BrokerRewardDao;
import gnnt.MEBS.member.broker.dao.ModuleConfigurationDao;
import gnnt.MEBS.member.broker.model.Broker;
import gnnt.MEBS.member.broker.model.BrokerReward;
import gnnt.MEBS.member.broker.model.ModuleConfiguration;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("m_brokerRewardService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
public class BrokerRewardService
{
  private final transient Log logger = LogFactory.getLog(BrokerRewardService.class);
  @Autowired
  @Qualifier("m_brokerRewardDao")
  public BrokerRewardDao brokerRewardDao;
  @Autowired
  @Qualifier("m_brokerDao")
  private BrokerDao brokerDao;
  @Autowired
  @Qualifier("m_moduleConfigurationDao")
  private ModuleConfigurationDao moduleConfigurationDao;
  
  public void setBrokerRewardDao(BrokerRewardDao paramBrokerRewardDao)
  {
    this.brokerRewardDao = paramBrokerRewardDao;
  }
  
  public List getBrokerRewardList(QueryConditions paramQueryConditions, PageInfo paramPageInfo)
  {
    return this.brokerRewardDao.getBrokerRewardList(paramQueryConditions, paramPageInfo);
  }
  
  public void updateBrokerReward(BrokerReward paramBrokerReward)
  {
    this.brokerRewardDao.updateBrokerReward(paramBrokerReward);
  }
  
  public int payCommission(BrokerReward paramBrokerReward, double paramDouble)
  {
    int i = 1;
    paramBrokerReward = this.brokerRewardDao.getObjectLock(paramBrokerReward);
    if (Arith.compareTo(paramBrokerReward.getAmount(), paramDouble) >= 0)
    {
      Broker localBroker = this.brokerDao.getBrokerById(paramBrokerReward.getBrokerId());
      ModuleConfiguration localModuleConfiguration = this.moduleConfigurationDao.getObject(paramBrokerReward.getModuleId());
      this.logger.debug("moduleId:" + paramBrokerReward.getModuleId());
      this.logger.debug("firmId:" + localBroker.getFirmId());
      this.logger.debug("code:" + localModuleConfiguration.getOprcode());
      this.brokerRewardDao.updateFunds(localBroker.getFirmId(), localModuleConfiguration.getOprcode(), paramDouble);
      paramBrokerReward.setAmount(paramBrokerReward.getAmount() - paramDouble);
      paramBrokerReward.setPaidAmount(paramBrokerReward.getPaidAmount() + paramDouble);
      this.brokerRewardDao.updateBrokerReward(paramBrokerReward);
    }
    else
    {
      i = -1;
    }
    return i;
  }
  
  public BrokerReward getObject(BrokerReward paramBrokerReward)
  {
    return this.brokerRewardDao.getObject(paramBrokerReward);
  }
}

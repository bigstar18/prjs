package gnnt.MEBS.timebargain.mgr.service.impl;

import gnnt.MEBS.common.mgr.service.StandardService;
import gnnt.MEBS.timebargain.mgr.dao.BrokerRewardDao;
import gnnt.MEBS.timebargain.mgr.service.BrokerRewardService;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("brokerRewardService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
public class BrokerRewardServiceImpl extends StandardService
  implements BrokerRewardService
{

  @Autowired
  @Qualifier("brokerRewardDao")
  private BrokerRewardDao brokerRewardDao;

  public List getBreedStartList()
  {
    return this.brokerRewardDao.getBreedStartList();
  }

  public List getBreedEndList()
  {
    return this.brokerRewardDao.getBreedEndList();
  }

  public List getBrokerStartList()
  {
    return this.brokerRewardDao.getBrokerStartList();
  }

  public List getBrokerEndList()
  {
    return this.brokerRewardDao.getBrokerEndList();
  }

  public List getFirmRewardSum(String isPartition, String startBreed, String endBreed, String startFirm, String endFirm, String beginDate, String endDate)
  {
    return this.brokerRewardDao.getFirmRewardSum(isPartition, startBreed, endBreed, startFirm, endFirm, beginDate, endDate);
  }

  public List getBrokerRewardSum(String startBroker, String endBroker, String beginDate, String endDate)
  {
    return this.brokerRewardDao.getBrokerRewardSum(startBroker, endBroker, beginDate, endDate);
  }

  public List getBreedRewardSum(String startBreed, String endBreed, String beginDate, String endDate)
  {
    return this.brokerRewardDao.getBreedRewardSum(startBreed, endBreed, beginDate, endDate);
  }

  public List brokerTradeFee(HttpServletRequest request)
  {
    return this.brokerRewardDao.getBrokerTradeFee(request);
  }

  public List breedTradeFee(HttpServletRequest request)
  {
    return this.brokerRewardDao.getBreedTradeFee(request);
  }

  public List firmTradeFee(HttpServletRequest request) {
    return this.brokerRewardDao.getFirmTradeFee(request);
  }

  public BrokerRewardDao getBrokerRewardDao()
  {
    return this.brokerRewardDao;
  }

  public List getBrokerUserFeeList(String startFirm, String endFirm, String beginDate, String endDate, String summarizingWay, String brokerid, String breedId)
  {
    return this.brokerRewardDao.getBrokerUserFeeList(startFirm, endFirm, beginDate, endDate, summarizingWay, brokerid, breedId);
  }

  public List getHisDealDetailList(String brokerId, String breedId, String firmId, String bsFlag, String beginDate, String endDate)
  {
    return this.brokerRewardDao.getHisDealDetailList(brokerId, breedId, firmId, bsFlag, beginDate, endDate);
  }
}
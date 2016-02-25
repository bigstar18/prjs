package gnnt.MEBS.member.broker.services;

import gnnt.MEBS.base.query.PageInfo;
import gnnt.MEBS.base.query.QueryConditions;
import gnnt.MEBS.member.broker.dao.BrokerReportDao;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("m_brokerReportService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
public class BrokerReportService
{
  @Autowired
  @Qualifier("m_brokerReportDao")
  private BrokerReportDao brokerReportDao;
  
  public List getBrokerFeeDetailByCondition(QueryConditions paramQueryConditions, PageInfo paramPageInfo, String paramString)
  {
    return this.brokerReportDao.getBrokerFeeDetailByCondition(paramQueryConditions, paramPageInfo, paramString);
  }
  
  public List getFirmFeeDetailByCondition(QueryConditions paramQueryConditions, int paramInt1, int paramInt2, String paramString)
  {
    return this.brokerReportDao.getFirmFeeDetailByCondition(paramQueryConditions, paramInt1, paramInt2, paramString);
  }
  
  public int getFirmFeeDetailByConditionCount(QueryConditions paramQueryConditions, String paramString)
  {
    return this.brokerReportDao.getFirmFeeDetailByConditionCount(paramQueryConditions, paramString);
  }
  
  public List getCommodityFeeDetailByCondition(QueryConditions paramQueryConditions, PageInfo paramPageInfo, String paramString)
  {
    return this.brokerReportDao.getCommodityFeeDetailByCondition(paramQueryConditions, paramPageInfo, paramString);
  }
  
  public List tradeFeeList(QueryConditions paramQueryConditions)
  {
    return this.brokerReportDao.tradeFeeList(paramQueryConditions);
  }
  
  public List getDingDanBreeds(boolean paramBoolean)
  {
    return this.brokerReportDao.getDingDanBreeds(paramBoolean);
  }
  
  public List getGuaPaiCommoditys(boolean paramBoolean)
  {
    return this.brokerReportDao.getGuaPaiCommoditys(paramBoolean);
  }
  
  public List getJingJiaBreeds(boolean paramBoolean)
  {
    return this.brokerReportDao.getJingJiaBreeds(paramBoolean);
  }
}

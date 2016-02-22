package gnnt.MEBS.settlement.service;

import gnnt.MEBS.base.query.hibernate.PageInfo;
import gnnt.MEBS.base.query.hibernate.QueryConditions;
import gnnt.MEBS.packaging.dao.BaseDao;
import gnnt.MEBS.packaging.service.BaseService;
import gnnt.MEBS.settlement.dao.AvgPriceDao;
import gnnt.MEBS.settlement.model.AvgPrice;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("avgPriceService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
public class AvgPriceService
  extends BaseService
{
  @Autowired
  @Qualifier("avgPriceDao")
  private AvgPriceDao avgPriceDao;
  
  public BaseDao getDao()
  {
    return this.avgPriceDao;
  }
  
  public Object[] getAvg(QueryConditions conditions, PageInfo pageInfo)
  {
    Object[] aa = this.avgPriceDao.getAvg(conditions, pageInfo);
    return aa;
  }
  
  public List<AvgPrice> getList(QueryConditions conditions, PageInfo pageInfo)
  {
    return this.avgPriceDao.getList(conditions, pageInfo);
  }
}

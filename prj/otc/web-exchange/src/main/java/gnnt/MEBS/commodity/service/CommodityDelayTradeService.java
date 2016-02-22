package gnnt.MEBS.commodity.service;

import gnnt.MEBS.base.query.hibernate.QueryConditions;
import gnnt.MEBS.commodity.dao.CommodityDelayTradeDao;
import gnnt.MEBS.commodity.model.CommodityDelayTrade;
import gnnt.MEBS.packaging.dao.BaseDao;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("commodityDelayTradeService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
public class CommodityDelayTradeService
  extends SpecialSetService<CommodityDelayTrade>
{
  private final transient Log logger = LogFactory.getLog(CommodityDelayTradeService.class);
  @Autowired
  @Qualifier("commodityDelayTradeDao")
  private CommodityDelayTradeDao commodityDelayTradeDao;
  
  public BaseDao getDao()
  {
    return this.commodityDelayTradeDao;
  }
  
  public CommodityDelayTrade get(CommodityDelayTrade commodityDelayTrade)
  {
    QueryConditions qc = new QueryConditions();
    qc.addCondition("primary.f_FirmId", "=", commodityDelayTrade.getF_FirmId());
    qc.addCondition("primary.commodityId", "=", commodityDelayTrade.getCommodityId());
    List<CommodityDelayTrade> list = this.commodityDelayTradeDao.getList(qc, null);
    if ((list != null) && (list.size() > 0))
    {
      commodityDelayTrade = new CommodityDelayTrade();
      commodityDelayTrade = (CommodityDelayTrade)list.get(0);
    }
    else
    {
      commodityDelayTrade = null;
    }
    return commodityDelayTrade;
  }
}

package gnnt.MEBS.commodity.service;

import gnnt.MEBS.base.query.hibernate.QueryConditions;
import gnnt.MEBS.commodity.dao.TradeAuthDao;
import gnnt.MEBS.commodity.model.TradeAuth;
import gnnt.MEBS.packaging.dao.BaseDao;
import gnnt.MEBS.packaging.service.BaseService;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("tradeAuthService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
public class TradeAuthService
  extends BaseService<TradeAuth>
{
  private final transient Log logger = LogFactory.getLog(TradeAuthService.class);
  @Autowired
  @Qualifier("tradeAuthDao")
  private TradeAuthDao tradeAuthDao;
  
  public BaseDao getDao()
  {
    return this.tradeAuthDao;
  }
  
  public TradeAuth get(TradeAuth tradeAuth)
  {
    this.logger.debug("tradeAuth");
    QueryConditions qc = new QueryConditions();
    qc.addCondition("primary.firmId", "=", tradeAuth.getFirmId());
    qc.addCondition("primary.commodityId", "=", tradeAuth.getCommodityId());
    List<TradeAuth> list = this.tradeAuthDao.getList(qc, null);
    TradeAuth returnMargin = null;
    if ((list != null) && (list.size() > 0))
    {
      returnMargin = new TradeAuth();
      returnMargin = (TradeAuth)list.get(0);
    }
    else
    {
      returnMargin = null;
    }
    return returnMargin;
  }
}

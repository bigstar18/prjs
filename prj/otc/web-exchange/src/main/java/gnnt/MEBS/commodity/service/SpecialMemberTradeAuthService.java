package gnnt.MEBS.commodity.service;

import gnnt.MEBS.base.query.hibernate.QueryConditions;
import gnnt.MEBS.commodity.dao.SpecialMemberTradeAuthDao;
import gnnt.MEBS.commodity.model.TradeAuth;
import gnnt.MEBS.packaging.dao.BaseDao;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("specialMemberTradeAuthService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
public class SpecialMemberTradeAuthService
  extends SpecialSetService<TradeAuth>
{
  private final transient Log logger = LogFactory.getLog(SpecialMemberTradeAuthService.class);
  @Autowired
  @Qualifier("specialMemberTradeAuthDao")
  private SpecialMemberTradeAuthDao specialMemberTradeAuthDao;
  
  public BaseDao getDao()
  {
    return this.specialMemberTradeAuthDao;
  }
  
  public TradeAuth get(TradeAuth tradeAuth)
  {
    this.logger.debug("tradeAuth");
    QueryConditions qc = new QueryConditions();
    qc.addCondition("primary.firmId", "=", tradeAuth.getFirmId());
    qc.addCondition("primary.commodityId", "=", tradeAuth.getCommodityId());
    List<TradeAuth> list = this.specialMemberTradeAuthDao.getList(qc, null);
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

package gnnt.MEBS.zcjs.services;

import gnnt.MEBS.zcjs.dao.MarketConfigDao;
import gnnt.MEBS.zcjs.model.MarketConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("z_financeStatusService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
public class FinanceStatusService
{
  @Autowired
  @Qualifier("z_marketConfigDao")
  private MarketConfigDao marketConfigDao;
  
  public MarketConfig getFinanceStatus(String paramString)
  {
    MarketConfig localMarketConfig = new MarketConfig();
    localMarketConfig = this.marketConfigDao.getObject(paramString);
    return localMarketConfig;
  }
}

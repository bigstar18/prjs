package gnnt.MEBS.zcjs.services;

import gnnt.MEBS.zcjs.dao.MarketConfigDao;
import gnnt.MEBS.zcjs.model.MarketConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("marketconfigservice")
@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
public class MarketConfigService
{
  @Autowired
  @Qualifier("z_marketConfigDao")
  private MarketConfigDao mcDao;
  
  public MarketConfig getMarketConfig(String paramString)
  {
    return this.mcDao.getObject(paramString);
  }
  
  public void update(MarketConfig paramMarketConfig)
  {
    this.mcDao.update(paramMarketConfig);
  }
}

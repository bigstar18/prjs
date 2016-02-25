package gnnt.MEBS.zcjs.services;

import gnnt.MEBS.zcjs.dao.MarketStatusDao;
import gnnt.MEBS.zcjs.model.MarketStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("z_marketStatusService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
public class MarketStatusService
{
  @Autowired
  @Qualifier("z_marketStatusDao")
  private MarketStatusDao marketStatusDao;
  
  public void update(MarketStatus paramMarketStatus)
  {
    this.marketStatusDao.update(paramMarketStatus);
  }
  
  public MarketStatus getObject()
  {
    return this.marketStatusDao.getObject("0001");
  }
}

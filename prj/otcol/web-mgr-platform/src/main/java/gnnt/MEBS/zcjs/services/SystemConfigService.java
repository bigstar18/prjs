package gnnt.MEBS.zcjs.services;

import gnnt.MEBS.zcjs.dao.MarketConfigDao;
import gnnt.MEBS.zcjs.dao.MarketStatusDao;
import gnnt.MEBS.zcjs.dao.TradeRestDateDao;
import gnnt.MEBS.zcjs.model.MarketConfig;
import gnnt.MEBS.zcjs.model.MarketStatus;
import gnnt.MEBS.zcjs.model.TradeRestDate;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("z_systemConfigService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
public class SystemConfigService
{
  @Autowired
  @Qualifier("z_tradeRestDateDao")
  private TradeRestDateDao tradeRestDateDao;
  @Autowired
  @Qualifier("z_marketConfigDao")
  private MarketConfigDao marketConfigDao;
  @Autowired
  @Qualifier("z_marketStatusDao")
  private MarketStatusDao marketStatusDao;
  
  public List<Object> systemManagerView()
  {
    ArrayList localArrayList = new ArrayList();
    localArrayList.add(this.tradeRestDateDao.getObject("0001"));
    localArrayList.add(this.marketConfigDao.getObject("0001"));
    localArrayList.add(this.marketStatusDao.getObject("0001"));
    return localArrayList;
  }
  
  public void systemManagerUpdate(List paramList)
  {
    TradeRestDate localTradeRestDate = (TradeRestDate)paramList.get(0);
    MarketConfig localMarketConfig = (MarketConfig)paramList.get(1);
    MarketStatus localMarketStatus1 = (MarketStatus)paramList.get(2);
    MarketStatus localMarketStatus2 = this.marketStatusDao.getObject("0001");
    localMarketStatus2.setIsAuto(localMarketStatus1.getIsAuto());
    this.tradeRestDateDao.update(localTradeRestDate);
    this.marketConfigDao.update(localMarketConfig);
    this.marketStatusDao.update(localMarketStatus2);
  }
}

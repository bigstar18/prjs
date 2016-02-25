package gnnt.MEBS.zcjs.services;

import gnnt.MEBS.zcjs.dao.TradeModuleDao;
import gnnt.MEBS.zcjs.model.TradeModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("z_tradeModuleService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
public class TradeModuleService
{
  @Autowired
  @Qualifier("z_tradeModuleDao")
  private TradeModuleDao tradeModuleDao;
  
  public TradeModule getObject(String paramString)
  {
    return this.tradeModuleDao.getObject(paramString);
  }
}

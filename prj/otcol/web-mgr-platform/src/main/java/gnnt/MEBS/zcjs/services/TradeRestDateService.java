package gnnt.MEBS.zcjs.services;

import gnnt.MEBS.zcjs.dao.TradeRestDateDao;
import gnnt.MEBS.zcjs.model.TradeRestDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("z_tradeRestDateService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
public class TradeRestDateService
{
  @Autowired
  @Qualifier("z_tradeRestDateDao")
  private TradeRestDateDao tradeRestDateDao;
  
  public TradeRestDate getTradeRestDate()
  {
    return this.tradeRestDateDao.getObject("0001");
  }
}

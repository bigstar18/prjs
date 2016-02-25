package gnnt.MEBS.zcjs.services;

import gnnt.MEBS.zcjs.dao.HisTradeDao;
import gnnt.MEBS.zcjs.model.HisTrade;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("z_hisTradeService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
public class HisTradeService
{
  @Autowired
  @Qualifier("z_hisTradeDao")
  private HisTradeDao hisTradeDao;
  
  public void add(HisTrade paramHisTrade)
  {
    paramHisTrade.setClearDate(new Date());
    this.hisTradeDao.add(paramHisTrade);
  }
  
  public HisTrade getObject(long paramLong)
  {
    return this.hisTradeDao.getObject(paramLong);
  }
}

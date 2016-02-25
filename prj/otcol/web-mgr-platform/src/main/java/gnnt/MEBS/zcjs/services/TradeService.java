package gnnt.MEBS.zcjs.services;

import gnnt.MEBS.base.query.QueryConditions;
import gnnt.MEBS.zcjs.dao.DeliveryDao;
import gnnt.MEBS.zcjs.dao.TradeDao;
import gnnt.MEBS.zcjs.model.Delivery;
import gnnt.MEBS.zcjs.model.Trade;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("z_tradeService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
public class TradeService
{
  @Autowired
  @Qualifier("z_tradedao")
  private TradeDao tradedao;
  @Autowired
  @Qualifier("z_deliveryDao")
  private DeliveryDao deliveryDao;
  
  @Transactional(propagation=Propagation.REQUIRED, readOnly=true)
  public void add(Trade paramTrade)
  {
    paramTrade.setTradeNo(this.tradedao.getId());
    this.tradedao.add(paramTrade);
  }
  
  public List<Trade> getList(QueryConditions paramQueryConditions)
  {
    return this.tradedao.getObjectList(paramQueryConditions);
  }
  
  public void deleteTrade(long paramLong)
  {
    this.tradedao.delete(paramLong);
  }
  
  public void addDelivery(Delivery paramDelivery)
  {
    long l = this.deliveryDao.getId();
    paramDelivery.setDeliveryId(l);
    this.deliveryDao.insert(paramDelivery);
  }
  
  public Trade getObject(long paramLong)
  {
    return this.tradedao.getObject(paramLong);
  }
}

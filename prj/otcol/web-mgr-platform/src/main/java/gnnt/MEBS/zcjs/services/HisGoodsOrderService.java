package gnnt.MEBS.zcjs.services;

import gnnt.MEBS.zcjs.dao.HisGoodsOrderDao;
import gnnt.MEBS.zcjs.model.HisGoodsOrder;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("z_hisGoodsOrderService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
public class HisGoodsOrderService
{
  @Autowired
  @Qualifier("z_hisGoodsOrderDao")
  private HisGoodsOrderDao hisGoodsOrderDao;
  
  public void add(HisGoodsOrder paramHisGoodsOrder)
  {
    paramHisGoodsOrder.setClearDate(new Date());
    this.hisGoodsOrderDao.add(paramHisGoodsOrder);
  }
  
  @Transactional(propagation=Propagation.REQUIRED, readOnly=true)
  public HisGoodsOrder getObject(long paramLong)
  {
    return this.hisGoodsOrderDao.getObject(paramLong);
  }
}

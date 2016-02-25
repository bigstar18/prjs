package gnnt.MEBS.zcjs.services;

import gnnt.MEBS.base.query.QueryConditions;
import gnnt.MEBS.zcjs.dao.GoodsOrderDao;
import gnnt.MEBS.zcjs.model.GoodsOrder;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("z_goodsOrderService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
public class GoodsOrderService
{
  @Autowired
  @Qualifier("z_goodsOrderDao")
  private GoodsOrderDao goodsOrderDao;
  
  public void add(GoodsOrder paramGoodsOrder)
  {
    long l = this.goodsOrderDao.getId();
    if (paramGoodsOrder.getGoodsOrderNo() == 0L) {
      paramGoodsOrder.setGoodsOrderNo(l);
    } else {
      paramGoodsOrder.setGoodsOrderNo(paramGoodsOrder.getGoodsOrderNo());
    }
    paramGoodsOrder.setModifyDate(new Date());
    paramGoodsOrder.setGoodsOrderId(l);
    this.goodsOrderDao.add(paramGoodsOrder);
  }
  
  public void delete(long paramLong)
  {
    this.goodsOrderDao.delete(paramLong);
  }
  
  @Transactional(propagation=Propagation.REQUIRED, readOnly=true)
  public List<GoodsOrder> getObjectList(QueryConditions paramQueryConditions)
  {
    return this.goodsOrderDao.getObjectList(paramQueryConditions);
  }
  
  @Transactional(propagation=Propagation.REQUIRED, readOnly=true)
  public GoodsOrder getObject(long paramLong)
  {
    return this.goodsOrderDao.getObject(paramLong);
  }
  
  public void update(GoodsOrder paramGoodsOrder)
  {
    paramGoodsOrder.setModifyDate(new Date());
    this.goodsOrderDao.update(paramGoodsOrder);
  }
  
  public void updateStatus(GoodsOrder paramGoodsOrder)
  {
    paramGoodsOrder.setModifyDate(new Date());
    this.goodsOrderDao.updateStatus(paramGoodsOrder);
  }
  
  public List<GoodsOrder> getExpireList()
  {
    QueryConditions localQueryConditions = new QueryConditions();
    localQueryConditions.addCondition("trunc(g.GoodsOrderDate+t.EffectiveDays-1)", "<=", new Date(), "date");
    localQueryConditions.addCondition("g.status", "=", Integer.valueOf(1));
    return this.goodsOrderDao.getListAssociateCommodity(localQueryConditions);
  }
}

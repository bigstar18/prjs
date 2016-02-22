package gnnt.MEBS.commodity.service;

import gnnt.MEBS.base.query.hibernate.QueryConditions;
import gnnt.MEBS.commodity.dao.OrderPointDao;
import gnnt.MEBS.commodity.model.OrderPoint;
import gnnt.MEBS.packaging.dao.BaseDao;
import gnnt.MEBS.packaging.service.BaseService;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("orderPointService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
public class OrderPointService
  extends BaseService<OrderPoint>
{
  private final transient Log logger = LogFactory.getLog(OrderPointService.class);
  @Autowired
  @Qualifier("orderPointDao")
  private OrderPointDao orderPointDao;
  
  public BaseDao getDao()
  {
    return this.orderPointDao;
  }
  
  public OrderPoint get(OrderPoint orderPoint)
  {
    QueryConditions qc = new QueryConditions();
    qc.addCondition("primary.commodityId", "=", orderPoint.getCommodityId());
    qc.addCondition("primary.memberFirmId", "=", orderPoint.getMemberFirmId());
    List<OrderPoint> list = this.orderPointDao.getList(qc, null);
    if ((list != null) && (list.size() > 0))
    {
      orderPoint = new OrderPoint();
      orderPoint = (OrderPoint)list.get(0);
    }
    else
    {
      orderPoint = null;
    }
    return orderPoint;
  }
}

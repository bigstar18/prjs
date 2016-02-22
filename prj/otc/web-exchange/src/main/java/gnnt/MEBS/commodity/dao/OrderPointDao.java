package gnnt.MEBS.commodity.dao;

import gnnt.MEBS.base.query.hibernate.PageInfo;
import gnnt.MEBS.base.query.hibernate.QueryConditions;
import gnnt.MEBS.commodity.model.OrderPoint;
import gnnt.MEBS.packaging.dao.BaseDao;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

@Repository("orderPointDao")
public class OrderPointDao
  extends BaseDao
{
  private final transient Log logger = LogFactory.getLog(OrderPointDao.class);
  
  public Class getEntityClass()
  {
    return new OrderPoint().getClass();
  }
  
  public List<OrderPoint> getList(QueryConditions conditions, PageInfo pageInfo)
  {
    String hql = "select new OrderPoint(primary.commodityId, commodity.name,primary.memberFirmId, primary.stopLossPoint, primary.stopProfitPoint,primary.l_Open_Point, primary.m_OrderPoint, primary.min_M_OrderPoint,primary.max_M_OrderPoint) from OrderPoint as primary,Commodity as commodity where primary.commodityId=commodity.id";
    Object[] values = (Object[])null;
    String[] names = (String[])null;
    if ((conditions != null) && (conditions.getFieldsSqlClause() != null))
    {
      values = conditions.getValueArray();
      names = conditions.getNameArray();
      hql = hql + " and " + conditions.getFieldsSqlClause();
    }
    return queryByHQL(hql, names, values, pageInfo, null);
  }
}

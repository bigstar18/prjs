package gnnt.MEBS.commodity.dao;

import gnnt.MEBS.base.query.hibernate.PageInfo;
import gnnt.MEBS.base.query.hibernate.QueryConditions;
import gnnt.MEBS.commodity.model.CustomerHoldQty;
import gnnt.MEBS.packaging.dao.BaseDao;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

@Repository("customerHoldQtyDao")
public class CustomerHoldQtyDao
  extends BaseDao
{
  private final transient Log logger = LogFactory.getLog(CustomerHoldQtyDao.class);
  
  public Class getEntityClass()
  {
    return new CustomerHoldQty().getClass();
  }
  
  public List<CustomerHoldQty> getList(QueryConditions conditions, PageInfo pageInfo)
  {
    String hql = "select new CustomerHoldQty(primary.commodityId,commodity.name,primary.customerNo,customer.memberNo,primary.oneMaxOrderQty,primary.oneMinOrderQty,primary.maxCleanQty,primary.maxHoldQty,customer.name) from CustomerHoldQty as primary,Commodity as commodity,Customer as customer where primary.commodityId=commodity.id and customer.customerNo=primary.customerNo";
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

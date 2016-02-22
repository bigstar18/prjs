package gnnt.MEBS.commodity.dao;

import gnnt.MEBS.base.query.hibernate.PageInfo;
import gnnt.MEBS.base.query.hibernate.QueryConditions;
import gnnt.MEBS.commodity.model.HoldQty;
import gnnt.MEBS.packaging.dao.BaseDao;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

@Repository("holdQtyDao")
public class HoldQtyDao
  extends BaseDao
{
  private final transient Log logger = LogFactory.getLog(HoldQtyDao.class);
  
  public Class getEntityClass()
  {
    return new HoldQty().getClass();
  }
  
  public List<HoldQty> getList(QueryConditions conditions, PageInfo pageInfo)
  {
    String hql = "select new HoldQty(primary.commodityId,commodity.name,primary.firmId,primary.oneMaxOrderQty,primary.oneMinOrderQty,primary.maxCleanQty,primary.maxHoldQty) from HoldQty as primary,Commodity as commodity where primary.commodityId=commodity.id";
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

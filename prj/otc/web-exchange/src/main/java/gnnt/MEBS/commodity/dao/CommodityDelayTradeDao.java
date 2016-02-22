package gnnt.MEBS.commodity.dao;

import gnnt.MEBS.base.query.hibernate.PageInfo;
import gnnt.MEBS.base.query.hibernate.QueryConditions;
import gnnt.MEBS.commodity.model.CommodityDelayTrade;
import gnnt.MEBS.packaging.dao.BaseDao;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

@Repository("commodityDelayTradeDao")
public class CommodityDelayTradeDao
  extends BaseDao
{
  private final transient Log logger = LogFactory.getLog(CommodityDelayTradeDao.class);
  
  public Class getEntityClass()
  {
    return new CommodityDelayTrade().getClass();
  }
  
  public List<CommodityDelayTrade> getList(QueryConditions conditions, PageInfo pageInfo)
  {
    String hql = "select new CommodityDelayTrade(primary.commodityId,commodity.name,primary.f_FirmId,primary.delayTradeType,primary.delayTradeTime,primary.isslipPoint ) from CommodityDelayTrade as primary ,Commodity commodity where primary.commodityId=commodity.id";
    
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

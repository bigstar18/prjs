package gnnt.MEBS.commodity.dao;

import gnnt.MEBS.base.query.hibernate.PageInfo;
import gnnt.MEBS.base.query.hibernate.QueryConditions;
import gnnt.MEBS.commodity.model.CommodityPriceProtect;
import gnnt.MEBS.packaging.dao.BaseDao;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

@Repository("commodityPriceProtectDao")
public class CommodityPriceProtectDao
  extends BaseDao
{
  private final transient Log logger = LogFactory.getLog(CommodityPriceProtectDao.class);
  
  public Class getEntityClass()
  {
    return new CommodityPriceProtect().getClass();
  }
  
  public List<CommodityPriceProtect> getList(QueryConditions conditions, PageInfo pageInfo)
  {
    String hql = "select new CommodityPriceProtect(primary.commodityId,commodity.name,primary.screeningPricePoint,primary.timeoutInterval) from CommodityPriceProtect as primary,Commodity as commodity where primary.commodityId=commodity.id";
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

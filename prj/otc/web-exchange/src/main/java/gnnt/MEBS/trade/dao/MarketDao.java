package gnnt.MEBS.trade.dao;

import gnnt.MEBS.base.model.Clone;
import gnnt.MEBS.base.query.hibernate.PageInfo;
import gnnt.MEBS.base.query.hibernate.QueryConditions;
import gnnt.MEBS.packaging.dao.BaseDao;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

@Repository("marketDao")
public class MarketDao
  extends BaseDao
{
  private final transient Log logger = LogFactory.getLog(MarketDao.class);
  
  public Class getEntityClass()
  {
    return null;
  }
  
  public List<Clone> getList(QueryConditions conditions, PageInfo pageInfo)
  {
    this.logger.debug("enter getList");
    String hql = "from gnnt.MEBS.trade.model.Market where 1=1 ";
    Object[] values = (Object[])null;
    String[] names = (String[])null;
    if ((conditions != null) && (conditions.getFieldsSqlClause() != null))
    {
      values = conditions.getValueArray();
      names = conditions.getNameArray();
      hql = hql + " and " + conditions.getFieldsSqlClause();
    }
    List<Clone> list = queryByHQL(hql, names, values, pageInfo, null);
    return list;
  }
}

package gnnt.MEBS.query.dao;

import gnnt.MEBS.base.query.hibernate.PageInfo;
import gnnt.MEBS.base.query.hibernate.QueryConditions;
import gnnt.MEBS.query.model.QutationDetailSearch;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

@Repository("qutationDetailSearchDao")
public class QutationDetailSearchDao
  extends QueryBaseDao
{
  private final transient Log logger = LogFactory.getLog(QutationDetailSearchDao.class);
  
  public Class getEntityClass()
  {
    return new QutationDetailSearch().getClass();
  }
  
  public List<QutationDetailSearch> getList(QueryConditions conditions, PageInfo pageInfo)
  {
    String hql = "select new QutationDetailSearch(primary.clearDate, primary.commodityId,primary.price,commodity.name,primary.occurTime,primary.detailId)from QutationDetailSearch as primary,Commodity as commodity where primary.commodityId = commodity.id ";
    Object[] values = (Object[])null;
    String[] names = (String[])null;
    if ((conditions != null) && (conditions.getFieldsSqlClause() != null) && (!"".equals(conditions.getFieldsSqlClause())))
    {
      values = conditions.getValueArray();
      names = conditions.getNameArray();
      hql = hql + " and " + conditions.getFieldsSqlClause();
    }
    return queryByHQL(hql, names, values, pageInfo, null);
  }
}

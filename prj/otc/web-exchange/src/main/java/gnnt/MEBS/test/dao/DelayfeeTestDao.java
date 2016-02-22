package gnnt.MEBS.test.dao;

import gnnt.MEBS.base.query.hibernate.PageInfo;
import gnnt.MEBS.base.query.hibernate.QueryConditions;
import gnnt.MEBS.packaging.dao.BaseDao;
import gnnt.MEBS.test.model.DelayfeeTest;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository("delayfeeTestDao")
public class DelayfeeTestDao
  extends BaseDao
{
  public Class getEntityClass()
  {
    return new DelayfeeTest().getClass();
  }
  
  public List getEntityList(QueryConditions conditions, PageInfo pageInfo)
  {
    String hql = "select distinct primary.commodityId from " + getEntityClass().getName() + " as primary where 1=1 ";
    Object[] values = (Object[])null;
    String[] names = (String[])null;
    if ((conditions != null) && (conditions.getFieldsSqlClause() != null))
    {
      values = conditions.getValueArray();
      names = conditions.getNameArray();
      hql = hql + " and " + conditions.getFieldsSqlClause();
    }
    List list = queryByHQL(hql, names, values, pageInfo, null);
    return list;
  }
}

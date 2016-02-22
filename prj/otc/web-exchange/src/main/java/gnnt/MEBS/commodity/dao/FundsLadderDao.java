package gnnt.MEBS.commodity.dao;

import gnnt.MEBS.base.query.hibernate.PageInfo;
import gnnt.MEBS.base.query.hibernate.QueryConditions;
import gnnt.MEBS.commodity.model.FundsLadder;
import gnnt.MEBS.packaging.dao.BaseDao;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

@Repository("fundsLadderDao")
public class FundsLadderDao
  extends BaseDao
{
  private final transient Log logger = LogFactory.getLog(FundsLadderDao.class);
  
  public Class getEntityClass()
  {
    return new FundsLadder().getClass();
  }
  
  public List getFundsLadderList(QueryConditions conditions, PageInfo pageInfo)
  {
    this.logger.debug("enter getList");
    String hql = "select distinct primary.memberNo from " + getEntityClass().getName() + " as primary where 1=1 ";
    Object[] values = (Object[])null;
    String[] names = (String[])null;
    this.logger.debug("hql:" + hql);
    if ((conditions != null) && (conditions.getFieldsSqlClause() != null))
    {
      values = conditions.getValueArray();
      names = conditions.getNameArray();
      hql = hql + " and " + conditions.getFieldsSqlClause();
    }
    this.logger.debug("names:" + names + "   values:" + values + "  pageInfo:" + pageInfo);
    List list = queryByHQL(hql, names, values, pageInfo, null);
    this.logger.debug("hql:" + hql);
    return list;
  }
}

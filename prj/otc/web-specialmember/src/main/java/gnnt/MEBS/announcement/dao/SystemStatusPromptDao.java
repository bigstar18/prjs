package gnnt.MEBS.announcement.dao;

import gnnt.MEBS.base.query.hibernate.CacheSet;
import gnnt.MEBS.base.query.hibernate.PageInfo;
import gnnt.MEBS.base.query.hibernate.QueryConditions;
import gnnt.MEBS.packaging.dao.BaseDao;
import gnnt.MEBS.trade.model.SystemStatus;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

@Repository("systemStatusPromptDao")
public class SystemStatusPromptDao
  extends BaseDao
{
  private final transient Log logger = LogFactory.getLog(SystemStatusPromptDao.class);
  
  public Class getEntityClass()
  {
    return new SystemStatus().getClass();
  }
  
  public List<SystemStatus> getList(QueryConditions conditions, PageInfo pageInfo)
  {
    this.logger.debug("enter getList");
    String hql = "from " + getEntityClass().getName() + " as primary where 1=1 ";
    Object[] values = (Object[])null;
    String[] names = (String[])null;
    this.logger.debug("hql:" + hql);
    if ((conditions != null) && (conditions.getFieldsSqlClause() != null) && (!"".equals(conditions.getFieldsSqlClause())))
    {
      this.logger.debug("conditions size:" + conditions.getConditionList().size());
      
      this.logger.debug("conditions fieldsSqlClause:" + conditions.getFieldsSqlClause());
      values = conditions.getValueArray();
      names = conditions.getNameArray();
      hql = hql + " and " + conditions.getFieldsSqlClause();
    }
    CacheSet cacheSet = new CacheSet();
    cacheSet.setRegion("queryCache");
    List<SystemStatus> list = queryByHQL(hql, names, values, pageInfo, cacheSet);
    this.logger.debug("list:" + list);
    this.logger.debug("hql:" + hql);
    return list;
  }
}

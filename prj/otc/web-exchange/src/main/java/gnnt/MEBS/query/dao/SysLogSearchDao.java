package gnnt.MEBS.query.dao;

import gnnt.MEBS.base.query.hibernate.PageInfo;
import gnnt.MEBS.base.query.hibernate.QueryConditions;
import gnnt.MEBS.query.model.SysLogSearch;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository("sysLogSearchDao")
public class SysLogSearchDao
  extends QueryBaseDao
{
  public Class getEntityClass()
  {
    return new SysLogSearch().getClass();
  }
  
  public List getList(QueryConditions conditions, PageInfo pageInfo)
  {
    String hql = "select new SysLogSearch(primary.id,primary.operator,primary.operateType,logCataLog.catalogName,primary.operateTime,primary.operateIp,primary.operateContent,primary.operateResult,primary.operatorType) from SysLogSearch as primary,LogCataLogSearch as logCataLog where primary.operateType=logCataLog.cataLogId ";
    



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

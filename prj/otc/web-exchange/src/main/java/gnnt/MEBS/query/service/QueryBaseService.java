package gnnt.MEBS.query.service;

import gnnt.MEBS.base.query.hibernate.PageInfo;
import gnnt.MEBS.base.query.hibernate.QueryConditions;
import gnnt.MEBS.query.dao.QueryBaseDao;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Transactional(propagation=Propagation.REQUIRED, readOnly=true)
public class QueryBaseService
{
  private final transient Log logger = LogFactory.getLog(QueryBaseService.class);
  private QueryBaseDao queryBaseDDao;
  private QueryBaseDao queryBaseHDao;
  
  public QueryBaseDao getQueryBaseDDao()
  {
    return this.queryBaseDDao;
  }
  
  public void setQueryBaseDDao(QueryBaseDao queryBaseDDao)
  {
    this.queryBaseDDao = queryBaseDDao;
  }
  
  public QueryBaseDao getQueryBaseHDao()
  {
    return this.queryBaseHDao;
  }
  
  public void setQueryBaseHDao(QueryBaseDao queryBaseHDao)
  {
    this.queryBaseHDao = queryBaseHDao;
  }
  
  @Transactional(propagation=Propagation.REQUIRED, readOnly=true)
  public List getList(QueryConditions conditions, PageInfo pageInfo, String queryType)
  {
    this.logger.debug("enter getList");
    List list = null;
    if ("D".equals(queryType)) {
      list = this.queryBaseDDao.getList(conditions, pageInfo);
    } else if ("H".equals(queryType)) {
      list = this.queryBaseHDao.getList(conditions, pageInfo);
    }
    return list;
  }
}

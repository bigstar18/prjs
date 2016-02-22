package gnnt.MEBS.report.service;

import gnnt.MEBS.base.query.hibernate.PageInfo;
import gnnt.MEBS.base.query.hibernate.QueryConditions;
import gnnt.MEBS.report.dao.QueryReportDao;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Transactional(propagation=Propagation.REQUIRED, readOnly=true)
public class QueryReportService
{
  private final transient Log logger = LogFactory.getLog(QueryReportService.class);
  private QueryReportDao queryReportDDao;
  private QueryReportDao queryReportHDao;
  
  public QueryReportDao getQueryReportDDao()
  {
    return this.queryReportDDao;
  }
  
  public void setQueryReportDDao(QueryReportDao queryReportDDao)
  {
    this.queryReportDDao = queryReportDDao;
  }
  
  public QueryReportDao getQueryReportHDao()
  {
    return this.queryReportHDao;
  }
  
  public void setQueryReportHDao(QueryReportDao queryReportHDao)
  {
    this.queryReportHDao = queryReportHDao;
  }
  
  @Transactional(propagation=Propagation.REQUIRED, readOnly=true)
  public List getList(QueryConditions conditions, PageInfo pageInfo, String queryType)
  {
    this.logger.debug("enter getList");
    List list = null;
    if ("D".equals(queryType)) {
      list = this.queryReportDDao.getList(conditions, pageInfo);
    } else if ("H".equals(queryType)) {
      list = this.queryReportHDao.getList(conditions, pageInfo);
    }
    return list;
  }
}

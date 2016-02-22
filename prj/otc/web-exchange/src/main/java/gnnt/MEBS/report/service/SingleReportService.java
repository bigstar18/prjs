package gnnt.MEBS.report.service;

import gnnt.MEBS.base.query.jdbc.PageInfo;
import gnnt.MEBS.base.query.jdbc.QueryConditions;
import gnnt.MEBS.report.dao.CommonReportDao;
import java.util.List;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Transactional(propagation=Propagation.REQUIRED, readOnly=true)
public class SingleReportService
{
  private CommonReportDao commonReportDao;
  
  public CommonReportDao getCommonReportDao()
  {
    return this.commonReportDao;
  }
  
  public void setCommonReportDao(CommonReportDao commonReportDao)
  {
    this.commonReportDao = commonReportDao;
  }
  
  public List getTraderList(QueryConditions qc)
  {
    return this.commonReportDao.getData(qc);
  }
  
  public List getJDBCList(QueryConditions qc, PageInfo pageInfo)
  {
    return this.commonReportDao.getListData(qc, pageInfo);
  }
}

package gnnt.MEBS.report.service;

import gnnt.MEBS.base.query.jdbc.QueryConditions;
import gnnt.MEBS.report.dao.ReportDao;
import gnnt.MEBS.report.model.DataTable;

public class ReportServiceImpl
  implements ReportService
{
  private ReportDao reportDao;
  private String template;
  
  public void setReportDao(ReportDao reportDao)
  {
    this.reportDao = reportDao;
  }
  
  public void setTemplate(String template)
  {
    this.template = template;
  }
  
  public DataTable getDataObject(QueryConditions qc)
  {
    DataTable dataTable = new DataTable();
    dataTable.setTemplate(this.template);
    dataTable.setDataList(this.reportDao.getData(qc));
    return dataTable;
  }
}

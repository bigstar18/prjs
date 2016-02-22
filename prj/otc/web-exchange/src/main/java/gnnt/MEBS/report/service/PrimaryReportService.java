package gnnt.MEBS.report.service;

import gnnt.MEBS.base.query.jdbc.QueryConditions;
import gnnt.MEBS.report.dao.ReportDao;
import gnnt.MEBS.report.model.DataTable;
import java.util.List;
import java.util.Map;

public class PrimaryReportService
  implements ReportService
{
  private String template;
  private ReportDao reportDao;
  
  public void setReportDao(ReportDao reportDao)
  {
    this.reportDao = reportDao;
  }
  
  public void setTemplate(String template)
  {
    this.template = template;
  }
  
  public DataTable getDataObject(QueryConditions conditions)
  {
    DataTable dataTable = new DataTable();
    dataTable.setTemplate(this.template);
    List<Map<String, Object>> primaryDataList = this.reportDao.getData(conditions);
    dataTable.setDataList(primaryDataList);
    return dataTable;
  }
}

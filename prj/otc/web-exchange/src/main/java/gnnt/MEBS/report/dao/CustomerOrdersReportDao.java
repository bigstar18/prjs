package gnnt.MEBS.report.dao;

import gnnt.MEBS.report.model.CustomerOrdersReport;
import org.springframework.stereotype.Repository;

@Repository("customerOrdersReportDao")
public class CustomerOrdersReportDao
  extends QueryReportDao
{
  public Class getEntityClass()
  {
    return new CustomerOrdersReport().getClass();
  }
}

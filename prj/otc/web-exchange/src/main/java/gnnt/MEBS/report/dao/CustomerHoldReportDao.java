package gnnt.MEBS.report.dao;

import gnnt.MEBS.report.model.CustomerHoldReport;
import org.springframework.stereotype.Repository;

@Repository("customerHoldReportDao")
public class CustomerHoldReportDao
  extends QueryReportDao
{
  public Class getEntityClass()
  {
    return new CustomerHoldReport().getClass();
  }
}

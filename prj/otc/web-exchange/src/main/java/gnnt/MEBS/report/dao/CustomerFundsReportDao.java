package gnnt.MEBS.report.dao;

import gnnt.MEBS.report.model.CustomerFundsReport;
import org.springframework.stereotype.Repository;

@Repository("customerFundsReportDao")
public class CustomerFundsReportDao
  extends QueryReportDao
{
  public Class getEntityClass()
  {
    return new CustomerFundsReport().getClass();
  }
}

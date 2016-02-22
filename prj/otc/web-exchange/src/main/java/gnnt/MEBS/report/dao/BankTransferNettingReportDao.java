package gnnt.MEBS.report.dao;

import gnnt.MEBS.report.model.BankTransferNettingReport;
import org.springframework.stereotype.Repository;

@Repository("bankTransferNettingReportDao")
public class BankTransferNettingReportDao
  extends QueryReportDao
{
  public Class getEntityClass()
  {
    return new BankTransferNettingReport().getClass();
  }
}

package gnnt.MEBS.report.dao;

import gnnt.MEBS.report.model.FirmBankReport;
import org.springframework.stereotype.Repository;

@Repository("firmBankReportDao")
public class FirmBankReportDao
  extends QueryReportDao
{
  public Class getEntityClass()
  {
    return new FirmBankReport().getClass();
  }
}

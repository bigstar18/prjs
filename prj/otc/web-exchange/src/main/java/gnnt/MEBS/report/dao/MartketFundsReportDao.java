package gnnt.MEBS.report.dao;

import gnnt.MEBS.report.model.MartketFundsReport;
import org.springframework.stereotype.Repository;

@Repository("martketFundsReportDao")
public class MartketFundsReportDao
  extends QueryReportDao
{
  public Class getEntityClass()
  {
    return new MartketFundsReport().getClass();
  }
}

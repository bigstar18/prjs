package gnnt.MEBS.report.dao;

import gnnt.MEBS.report.model.MarketFeeReport;
import org.springframework.stereotype.Repository;

@Repository("marketFeeReportDao")
public class MarketFeeReportDao
  extends QueryReportDao
{
  public Class getEntityClass()
  {
    return new MarketFeeReport().getClass();
  }
}

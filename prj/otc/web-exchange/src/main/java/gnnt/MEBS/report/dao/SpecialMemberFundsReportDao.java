package gnnt.MEBS.report.dao;

import gnnt.MEBS.report.model.SpecialMemberFundsReport;
import org.springframework.stereotype.Repository;

@Repository("specialMemberFundsReportDao")
public class SpecialMemberFundsReportDao
  extends QueryReportDao
{
  public Class getEntityClass()
  {
    return new SpecialMemberFundsReport().getClass();
  }
}

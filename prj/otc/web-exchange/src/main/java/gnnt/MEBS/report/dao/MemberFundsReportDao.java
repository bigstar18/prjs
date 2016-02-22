package gnnt.MEBS.report.dao;

import gnnt.MEBS.report.model.MemberFundsReport;
import org.springframework.stereotype.Repository;

@Repository("memberFundsReportDao")
public class MemberFundsReportDao
  extends QueryReportDao
{
  public Class getEntityClass()
  {
    return new MemberFundsReport().getClass();
  }
}

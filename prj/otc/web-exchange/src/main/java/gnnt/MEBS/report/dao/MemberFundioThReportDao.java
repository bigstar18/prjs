package gnnt.MEBS.report.dao;

import gnnt.MEBS.report.model.MemberFundioThReport;
import org.springframework.stereotype.Repository;

@Repository("memberFundioThReportDao")
public class MemberFundioThReportDao
  extends QueryReportDao
{
  public Class getEntityClass()
  {
    return new MemberFundioThReport().getClass();
  }
}

package gnnt.MEBS.report.dao;

import gnnt.MEBS.report.model.MemberHoldReport;
import org.springframework.stereotype.Repository;

@Repository("memberHoldReportDao")
public class MemberHoldReportDao
  extends QueryReportDao
{
  public Class getEntityClass()
  {
    return new MemberHoldReport().getClass();
  }
}

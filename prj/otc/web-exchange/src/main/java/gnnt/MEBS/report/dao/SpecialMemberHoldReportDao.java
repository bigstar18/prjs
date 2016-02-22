package gnnt.MEBS.report.dao;

import gnnt.MEBS.report.model.SpecialMemberHoldReport;
import org.springframework.stereotype.Repository;

@Repository("specialMemberHoldReportDao")
public class SpecialMemberHoldReportDao
  extends QueryReportDao
{
  public Class getEntityClass()
  {
    return new SpecialMemberHoldReport().getClass();
  }
}

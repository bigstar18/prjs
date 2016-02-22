package gnnt.MEBS.report.dao;

import gnnt.MEBS.report.model.SpecialMemberFundioThReport;
import org.springframework.stereotype.Repository;

@Repository("speicalMemberFundioThReportDao")
public class SpeicalMemberFundioThReportDao
  extends QueryReportDao
{
  public Class getEntityClass()
  {
    return new SpecialMemberFundioThReport().getClass();
  }
}

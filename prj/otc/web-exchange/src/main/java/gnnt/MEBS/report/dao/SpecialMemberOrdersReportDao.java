package gnnt.MEBS.report.dao;

import gnnt.MEBS.report.model.SpecialMemberOrdersReport;
import org.springframework.stereotype.Repository;

@Repository("specialMemberOrdersReportDao")
public class SpecialMemberOrdersReportDao
  extends QueryReportDao
{
  public Class getEntityClass()
  {
    return new SpecialMemberOrdersReport().getClass();
  }
}

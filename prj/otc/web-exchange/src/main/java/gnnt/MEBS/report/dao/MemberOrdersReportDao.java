package gnnt.MEBS.report.dao;

import gnnt.MEBS.report.model.MemberOrdersReport;
import org.springframework.stereotype.Repository;

@Repository("memberOrdersReportDao")
public class MemberOrdersReportDao
  extends QueryReportDao
{
  public Class getEntityClass()
  {
    return new MemberOrdersReport().getClass();
  }
}

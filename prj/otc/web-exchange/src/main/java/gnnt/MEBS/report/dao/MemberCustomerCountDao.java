package gnnt.MEBS.report.dao;

import gnnt.MEBS.report.model.MemberCustomerCount;
import org.springframework.stereotype.Repository;

@Repository("memberCustomerCountDao")
public class MemberCustomerCountDao
  extends QueryReportDao
{
  public Class getEntityClass()
  {
    return new MemberCustomerCount().getClass();
  }
}

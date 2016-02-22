package gnnt.MEBS.report.dao;

import gnnt.MEBS.report.model.MemberCustomerSign;
import org.springframework.stereotype.Repository;

@Repository("memberCustomerSignDao")
public class MemberCustomerSignDao
  extends QueryReportDao
{
  public Class getEntityClass()
  {
    return new MemberCustomerSign().getClass();
  }
}

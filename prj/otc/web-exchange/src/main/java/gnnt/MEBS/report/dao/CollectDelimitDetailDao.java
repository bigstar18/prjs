package gnnt.MEBS.report.dao;

import gnnt.MEBS.report.model.CollectDelimitDetail;
import org.springframework.stereotype.Repository;

@Repository("collectDelimitDetailDao")
public class CollectDelimitDetailDao
  extends QueryReportDao
{
  public Class getEntityClass()
  {
    return new CollectDelimitDetail().getClass();
  }
}

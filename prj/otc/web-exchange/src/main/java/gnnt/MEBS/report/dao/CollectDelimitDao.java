package gnnt.MEBS.report.dao;

import gnnt.MEBS.report.model.CollectDelimit;
import org.springframework.stereotype.Repository;

@Repository("collectDelimitDao")
public class CollectDelimitDao
  extends QueryReportDao
{
  public Class getEntityClass()
  {
    return new CollectDelimit().getClass();
  }
}

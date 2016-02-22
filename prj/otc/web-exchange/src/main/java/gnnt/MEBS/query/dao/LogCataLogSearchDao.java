package gnnt.MEBS.query.dao;

import gnnt.MEBS.query.model.LogCataLogSearch;
import org.springframework.stereotype.Repository;

@Repository("logCataLogSearchDao")
public class LogCataLogSearchDao
  extends QueryBaseDao
{
  public Class getEntityClass()
  {
    return new LogCataLogSearch().getClass();
  }
}

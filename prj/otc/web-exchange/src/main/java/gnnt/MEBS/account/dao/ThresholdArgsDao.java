package gnnt.MEBS.account.dao;

import gnnt.MEBS.account.model.ThresholdArgs;
import gnnt.MEBS.packaging.dao.BaseDao;
import org.springframework.stereotype.Repository;

@Repository("thresholdArgsDao")
public class ThresholdArgsDao
  extends BaseDao
{
  public Class getEntityClass()
  {
    return new ThresholdArgs().getClass();
  }
}

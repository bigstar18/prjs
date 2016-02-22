package gnnt.MEBS.settlement.dao;

import gnnt.MEBS.packaging.dao.BaseDao;
import gnnt.MEBS.settlement.model.ClearStatus;
import org.springframework.stereotype.Repository;

@Repository("clearStatusDao")
public class ClearStatusDao
  extends BaseDao
{
  public Class getEntityClass()
  {
    return new ClearStatus().getClass();
  }
}

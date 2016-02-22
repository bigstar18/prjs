package gnnt.MEBS.settlement.dao;

import gnnt.MEBS.packaging.dao.BaseDao;
import gnnt.MEBS.settlement.model.Firm;
import org.springframework.stereotype.Repository;

@Repository("firmDao")
public class FirmDao
  extends BaseDao
{
  public Class getEntityClass()
  {
    return new Firm().getClass();
  }
}

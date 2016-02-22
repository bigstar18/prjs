package gnnt.MEBS.bankadded.dao;

import gnnt.MEBS.bankadded.model.Banks;
import gnnt.MEBS.packaging.dao.BaseDao;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

@Repository("banksDao")
public class BanksDao
  extends BaseDao
{
  private final transient Log logger = LogFactory.getLog(BanksDao.class);
  
  public Class getEntityClass()
  {
    return new Banks().getClass();
  }
}

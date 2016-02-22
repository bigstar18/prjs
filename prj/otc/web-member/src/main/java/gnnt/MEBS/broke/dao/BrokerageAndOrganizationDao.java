package gnnt.MEBS.broke.dao;

import gnnt.MEBS.broke.model.BrokerageAndOrganization;
import gnnt.MEBS.packaging.dao.BaseDao;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

@Repository("brokerageAndOrganizationDao")
public class BrokerageAndOrganizationDao
  extends BaseDao
{
  private final transient Log logger = LogFactory.getLog(BrokerageAndOrganizationDao.class);
  
  public Class getEntityClass()
  {
    return new BrokerageAndOrganization().getClass();
  }
}

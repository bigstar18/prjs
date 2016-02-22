package gnnt.MEBS.broke.dao;

import gnnt.MEBS.broke.model.CustomerMappingBroker;
import gnnt.MEBS.packaging.dao.BaseDao;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

@Repository("customerMappingDao")
public class CustomerMappingDao
  extends BaseDao
{
  private final transient Log logger = LogFactory.getLog(CustomerMappingDao.class);
  
  public Class getEntityClass()
  {
    return new CustomerMappingBroker().getClass();
  }
}

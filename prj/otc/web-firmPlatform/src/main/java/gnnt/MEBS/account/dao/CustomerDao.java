package gnnt.MEBS.account.dao;

import gnnt.MEBS.account.model.Customer;
import gnnt.MEBS.packaging.dao.BaseDao;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

@Repository("customerDao")
public class CustomerDao
  extends BaseDao
{
  private final transient Log logger = LogFactory.getLog(CustomerDao.class);
  
  public Class getEntityClass()
  {
    return new Customer().getClass();
  }
}

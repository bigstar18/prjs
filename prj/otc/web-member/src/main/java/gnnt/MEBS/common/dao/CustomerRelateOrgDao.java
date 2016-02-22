package gnnt.MEBS.common.dao;

import gnnt.MEBS.common.model.CustomerRelateOrganization;
import gnnt.MEBS.packaging.dao.BaseDao;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

@Repository("customerRelateOrgDao")
public class CustomerRelateOrgDao
  extends BaseDao
{
  private final transient Log logger = LogFactory.getLog(CustomerRelateOrgDao.class);
  private String entityName = "gnnt.MEBS.common.model.CustomerRelateOrganization";
  
  public Class getEntityClass()
  {
    return new CustomerRelateOrganization().getClass();
  }
}

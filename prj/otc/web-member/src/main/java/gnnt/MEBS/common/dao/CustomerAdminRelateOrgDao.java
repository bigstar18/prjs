package gnnt.MEBS.common.dao;

import gnnt.MEBS.common.model.CustomerAdminRelateOrganization;
import gnnt.MEBS.packaging.dao.BaseDao;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

@Repository("customerAdminRelateOrgDao")
public class CustomerAdminRelateOrgDao
  extends BaseDao
{
  private final transient Log logger = LogFactory.getLog(CustomerAdminRelateOrgDao.class);
  private String entityName = "gnnt.MEBS.common.model.CustomerAdminRelateOrganization";
  
  public Class getEntityClass()
  {
    return new CustomerAdminRelateOrganization().getClass();
  }
}

package gnnt.MEBS.broke.service;

import gnnt.MEBS.broke.dao.OrganizationDao;
import gnnt.MEBS.broke.dao.OrganizationProDao;
import gnnt.MEBS.broke.model.Organization;
import gnnt.MEBS.packaging.dao.BaseDao;
import gnnt.MEBS.packaging.service.BaseService;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("organizationChangeService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
public class OrganizationChangeService
  extends BaseService<Organization>
{
  private final transient Log logger = LogFactory.getLog(OrganizationChangeService.class);
  @Autowired
  @Qualifier("organizationDao")
  private OrganizationDao organizationDao;
  @Autowired
  @Qualifier("organizationProDao")
  private OrganizationProDao organizationProDao;
  
  public BaseDao getDao()
  {
    return this.organizationDao;
  }
  
  public List list(String memberNo, String inOrganizationNo, String notInOrgNo)
  {
    return this.organizationDao.findOrganizationList(memberNo, inOrganizationNo, notInOrgNo);
  }
  
  public int update(Organization org)
  {
    int result = this.organizationProDao.organizationChangePro(org.getOrganizationNO(), org.getOrganizationNoChange());
    if (result > 0) {
      result = 3;
    }
    return result;
  }
}

package gnnt.MEBS.broke.service;

import gnnt.MEBS.broke.dao.MOrganizationDao;
import gnnt.MEBS.broke.model.MOrganization;
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

@Service("mOrganizationService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
public class MOrganizationService
  extends BaseService<Organization>
{
  private final transient Log logger = LogFactory.getLog(MOrganizationService.class);
  @Autowired
  @Qualifier("mOrganizationDao")
  private MOrganizationDao mOrganizationDao;
  
  public BaseDao getDao()
  {
    return this.mOrganizationDao;
  }
  
  public MOrganization change(Organization organization)
  {
    MOrganization mOrganization = new MOrganization();
    if (organization != null)
    {
      mOrganization.setAddress(organization.getAddress());
      mOrganization.setOrganizationNO(organization.getOrganizationNO());
      mOrganization.setEmail(organization.getEmail());
      mOrganization.setMemberNo(organization.getMemberNo());
      mOrganization.setMobile(organization.getMobile());
      mOrganization.setName(organization.getName());
      mOrganization.setNote(organization.getNote());
      mOrganization.setPassword(organization.getPassword());
      mOrganization.setTelephone(organization.getTelephone());
    }
    return mOrganization;
  }
  
  public Organization queryByNo(String no, String memberNo)
  {
    this.logger.debug("enter queryByNo.....");
    return this.mOrganizationDao.queryByNo(no, memberNo);
  }
  
  public void updateMOrganization(String memberNo, List<MOrganization> list2)
  {
    this.logger.debug("enter updateMOrganization.....");
    deleteMOrganization(memberNo);
    addMOrganization(list2);
  }
  
  public void addMOrganization(List<MOrganization> list)
  {
    this.logger.debug("enter addMOrganization.....");
    this.mOrganizationDao.addMOrganization(list);
  }
  
  public void deleteMOrganization(String memberNo)
  {
    this.logger.debug("enter deleteBroker.....");
    List list = queryMOrganization(memberNo);
    this.mOrganizationDao.deleteMOrganization(list);
  }
  
  public List<MOrganization> queryMOrganization(String memberNo)
  {
    this.logger.debug("enter queryMOrganization.....");
    return this.mOrganizationDao.queryMOrganization(memberNo);
  }
  
  public List<Organization> queryOrganization(String memberNo)
  {
    this.logger.debug("enter queryOrganization.....");
    return this.mOrganizationDao.queryOrganization(memberNo);
  }
}

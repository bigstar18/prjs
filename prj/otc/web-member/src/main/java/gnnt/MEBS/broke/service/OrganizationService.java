package gnnt.MEBS.broke.service;

import gnnt.MEBS.base.query.hibernate.QueryConditions;
import gnnt.MEBS.broke.dao.OrganizationDao;
import gnnt.MEBS.broke.dao.OrganizationProDao;
import gnnt.MEBS.broke.dao.OrganizationSeqDao;
import gnnt.MEBS.broke.model.Organization;
import gnnt.MEBS.config.constant.PasswordConstant;
import gnnt.MEBS.member.ActiveUser.MD5;
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

@Service("organizationService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
public class OrganizationService
  extends BaseService<Organization>
{
  private final transient Log logger = LogFactory.getLog(OrganizationService.class);
  @Autowired
  @Qualifier("organizationDao")
  private OrganizationDao organizationDao;
  @Autowired
  @Qualifier("organizationProDao")
  private OrganizationProDao organizationProDao;
  @Autowired
  @Qualifier("organizationSeqDao")
  private OrganizationSeqDao organizationSeqDao;
  
  public BaseDao getDao()
  {
    return this.organizationDao;
  }
  
  public List getByMemberNoList(QueryConditions conditions, String memberNo)
  {
    this.logger.debug("enter getList");
    if (conditions == null) {
      conditions = new QueryConditions();
    }
    conditions.addCondition("primary.memberNo", "=", memberNo);
    return getDao().getList(conditions, null);
  }
  
  public int updateCotactBroke(Organization obj)
  {
    int num = 0;
    this.logger.debug("enter update");
    Organization objFor = (Organization)get(obj);
    this.logger.debug(Boolean.valueOf(objFor == null));
    this.logger.debug("objFor:" + objFor.getClass().getName());
    objFor.setBrokerageSet(obj.getBrokerageSet());
    getDao().update(objFor);
    num = 3;
    return num;
  }
  
  public int delete(Organization obj)
  {
    int num = this.organizationProDao.organizationDeletePro(obj.getOrganizationNO());
    if (num > 0)
    {
      getDao().delete(obj);
      num = 4;
    }
    return num;
  }
  
  public int add(Organization obj)
  {
    this.logger.debug("enter add");
    int num = 0;
    Organization organization = (Organization)obj.clone();
    organization.setPassword(MD5.getMD5(organization.getId(), PasswordConstant.PASSWORD));
    getDao().add(organization);
    num = 2;
    return num;
  }
  
  public Long getOrganizationSeq()
  {
    return this.organizationSeqDao.getOrganizationSeq();
  }
  
  public List<Organization> getOrganizationBYOrgNOMemNo(String organizationNo, String memberNo)
  {
    QueryConditions qc = new QueryConditions();
    if ((organizationNo != null) && (!"".equals(organizationNo)))
    {
      String organizationNos = "(select view.actualOrganizationNo from gnnt.MEBS.common.model.OrganizationRelatedSelf  view where view.organizationNo='" + organizationNo + "')";
      qc.addCondition("primary.organizationNO", "in", organizationNos);
    }
    qc.addCondition("primary.memberNo", "=", memberNo);
    return this.organizationDao.getList(qc, null);
  }
}

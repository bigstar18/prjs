package gnnt.MEBS.broke.dao;

import gnnt.MEBS.broke.model.MOrganization;
import gnnt.MEBS.broke.model.Organization;
import gnnt.MEBS.packaging.dao.BaseDao;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

@Repository("mOrganizationDao")
public class MOrganizationDao
  extends BaseDao
{
  private final transient Log logger = LogFactory.getLog(MOrganizationDao.class);
  
  public Class getEntityClass()
  {
    return new MOrganization().getClass();
  }
  
  public Organization queryByNo(String no, String memberNo)
  {
    Organization organization = null;
    

    String[] str = { no, memberNo };
    List list = getHibernateTemplate().find("from Organization t where t.organizationNO=? and t.memberNo=?", str);
    if ((list != null) && (!"".equals(list))) {
      organization = (Organization)list.get(0);
    }
    return organization;
  }
  
  public void addMOrganization(List<MOrganization> list)
  {
    if ((list != null) && (list.size() > 0)) {
      for (MOrganization mOrganization : list) {
        getHibernateTemplate().save(mOrganization);
      }
    }
  }
  
  public void deleteMOrganization(List<MOrganization> list)
  {
    getHibernateTemplate().deleteAll(list);
  }
  
  public List<MOrganization> queryMOrganization(String memberNo)
  {
    List<MOrganization> list = null;
    
    list = getHibernateTemplate().find("from MOrganization t where t.memberNo=?", memberNo);
    return list;
  }
  
  public List<Organization> queryOrganization(String memberNo)
  {
    List<Organization> list = null;
    
    list = getHibernateTemplate().find("from Organization t where t.memberNo=?", memberNo);
    return list;
  }
}

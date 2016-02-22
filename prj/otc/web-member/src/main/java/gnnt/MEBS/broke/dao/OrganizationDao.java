package gnnt.MEBS.broke.dao;

import gnnt.MEBS.broke.model.Organization;
import gnnt.MEBS.packaging.dao.BaseDao;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

@Repository("organizationDao")
public class OrganizationDao
  extends BaseDao
{
  private final transient Log logger = LogFactory.getLog(OrganizationDao.class);
  
  public Class getEntityClass()
  {
    return new Organization().getClass();
  }
  
  public List findOrganizationList(String memberNo, String inOrganizationNo, String notInOrgNo)
  {
    String sql = "select primary.organizationno,primary.name from m_b_organization primary where primary.memberNo='" + memberNo + "'";
    if ((inOrganizationNo != null) && (!"".equals(inOrganizationNo))) {
      sql = 
        sql + " and primary.organizationno in(select t.organizationno from m_b_organization t start with t.organizationno='" + inOrganizationNo + "' connect by prior organizationno=t.parentorganizationno)";
    }
    if ((notInOrgNo != null) && (!"".equals(notInOrgNo))) {
      sql = 
        sql + "and primary.organizationno not in (select t.organizationno from m_b_organization t start with t.organizationno='" + notInOrgNo + "' connect by prior organizationno=t.parentorganizationno)";
    }
    List list = querySql(sql);
    return list;
  }
}

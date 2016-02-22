package gnnt.MEBS.announcement.dao;

import gnnt.MEBS.account.model.SpecialMemberRole;
import gnnt.MEBS.packaging.dao.BaseDao;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

@Repository("specialMemberRoleDao")
public class SpecialMemberRoleDao
  extends BaseDao
{
  private final transient Log logger = LogFactory.getLog(SpecialMemberRoleDao.class);
  
  public Class getEntityClass()
  {
    return new SpecialMemberRole().getClass();
  }
}

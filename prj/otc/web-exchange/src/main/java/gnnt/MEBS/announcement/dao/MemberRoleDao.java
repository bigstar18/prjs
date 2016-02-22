package gnnt.MEBS.announcement.dao;

import gnnt.MEBS.account.model.MemberRole;
import gnnt.MEBS.packaging.dao.BaseDao;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

@Repository("memberRoleDao")
public class MemberRoleDao
  extends BaseDao
{
  private final transient Log logger = LogFactory.getLog(MemberRoleDao.class);
  
  public Class getEntityClass()
  {
    return new MemberRole().getClass();
  }
}

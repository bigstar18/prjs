package gnnt.MEBS.account.dao;

import gnnt.MEBS.account.model.CompMember;
import gnnt.MEBS.packaging.dao.BaseDao;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

@Repository("compMemberDao")
public class CompMemberDao
  extends BaseDao
{
  private final transient Log logger = LogFactory.getLog(CompMemberDao.class);
  
  public Class getEntityClass()
  {
    return new CompMember().getClass();
  }
}

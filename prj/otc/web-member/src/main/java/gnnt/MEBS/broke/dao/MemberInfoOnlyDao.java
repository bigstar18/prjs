package gnnt.MEBS.broke.dao;

import gnnt.MEBS.broke.model.MemberInfoOnly;
import gnnt.MEBS.packaging.dao.BaseDao;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

@Repository("memberInfoOnlyDao")
public class MemberInfoOnlyDao
  extends BaseDao
{
  private final transient Log logger = LogFactory.getLog(MemberInfoOnlyDao.class);
  
  public Class getEntityClass()
  {
    return new MemberInfoOnly().getClass();
  }
}

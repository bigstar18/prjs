package gnnt.MEBS.broke.dao;

import gnnt.MEBS.broke.model.MemberInfoTree;
import gnnt.MEBS.packaging.dao.BaseDao;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

@Repository("memberInfoTreeDao")
public class MemberInfoTreeDao
  extends BaseDao
{
  private final transient Log logger = LogFactory.getLog(MemberInfoTreeDao.class);
  
  public Class getEntityClass()
  {
    return new MemberInfoTree().getClass();
  }
}

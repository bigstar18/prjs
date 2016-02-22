package gnnt.MEBS.account.dao;

import gnnt.MEBS.account.model.MemberRelation;
import gnnt.MEBS.packaging.dao.BaseDao;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

@Repository("memberRelationDao")
public class MemberRelationDao
  extends BaseDao
{
  private final transient Log logger = LogFactory.getLog(MemberRelationDao.class);
  
  public Class getEntityClass()
  {
    return new MemberRelation().getClass();
  }
}

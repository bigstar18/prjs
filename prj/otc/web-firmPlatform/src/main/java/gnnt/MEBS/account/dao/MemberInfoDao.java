package gnnt.MEBS.account.dao;

import gnnt.MEBS.account.model.MemberInfo;
import gnnt.MEBS.packaging.dao.BaseDao;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

@Repository("memberInfoDao")
public class MemberInfoDao
  extends BaseDao
{
  private final transient Log logger = LogFactory.getLog(MemberInfoDao.class);
  
  public Class getEntityClass()
  {
    return new MemberInfo().getClass();
  }
}

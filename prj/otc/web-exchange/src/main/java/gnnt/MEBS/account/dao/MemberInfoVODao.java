package gnnt.MEBS.account.dao;

import gnnt.MEBS.account.model.MemberInfoVO;
import gnnt.MEBS.packaging.dao.BaseDao;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

@Repository("memberInfoVODao")
public class MemberInfoVODao
  extends BaseDao
{
  private final transient Log logger = LogFactory.getLog(MemberInfoVODao.class);
  
  public Class getEntityClass()
  {
    return new MemberInfoVO().getClass();
  }
}

package gnnt.MEBS.account.dao;

import gnnt.MEBS.account.model.SpecialMemberVO;
import gnnt.MEBS.packaging.dao.BaseDao;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

@Repository("specialMemberVODao")
public class SpecialMemberVODao
  extends BaseDao
{
  private final transient Log logger = LogFactory.getLog(SpecialMemberVODao.class);
  
  public Class getEntityClass()
  {
    return new SpecialMemberVO().getClass();
  }
}

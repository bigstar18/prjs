package gnnt.MEBS.account.dao;

import gnnt.MEBS.account.model.SpecialMember;
import gnnt.MEBS.base.model.Clone;
import gnnt.MEBS.packaging.dao.BaseDao;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

@Repository("specialMemberDao")
public class SpecialMemberDao
  extends BaseDao
{
  private final transient Log logger = LogFactory.getLog(SpecialMemberDao.class);
  
  public Class getEntityClass()
  {
    return new SpecialMember().getClass();
  }
  
  public SpecialMember get(Clone obj)
  {
    this.logger.debug("enter get");
    return (SpecialMember)getHibernateTemplate().get(getEntityClass().getName(), obj);
  }
}

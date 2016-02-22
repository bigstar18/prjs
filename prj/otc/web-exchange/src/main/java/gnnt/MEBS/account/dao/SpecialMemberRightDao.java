package gnnt.MEBS.account.dao;

import gnnt.MEBS.account.model.SpecialMemberRight;
import gnnt.MEBS.packaging.dao.BaseDao;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Filter;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

@Repository("specialMemberRightDao")
public class SpecialMemberRightDao
  extends BaseDao
{
  private final transient Log logger = LogFactory.getLog(SpecialMemberRightDao.class);
  
  public Class getEntityClass()
  {
    return new SpecialMemberRight().getClass();
  }
  
  public SpecialMemberRight loadTreeRight(long id, int type, int visible)
  {
    SpecialMemberRight right = null;
    String queryString = "from SpecialMemberRight as model where model.id= ? ";
    getHibernateTemplate().enableFilter("rightTreeFilter")
      .setParameter("type", Integer.valueOf(type))
      .setParameter("visible", Integer.valueOf(visible));
    List<SpecialMemberRight> list = getHibernateTemplate().find(queryString, Long.valueOf(id));
    if (list.size() > 0) {
      right = (SpecialMemberRight)list.get(0);
    }
    return right;
  }
}

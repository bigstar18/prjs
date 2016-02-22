package gnnt.MEBS.account.dao;

import gnnt.MEBS.account.model.MemberRight;
import gnnt.MEBS.packaging.dao.BaseDao;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Filter;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

@Repository("memberRightDao")
public class MemberRightDao
  extends BaseDao
{
  private final transient Log logger = LogFactory.getLog(MemberRightDao.class);
  
  public Class getEntityClass()
  {
    return new MemberRight().getClass();
  }
  
  public MemberRight loadTreeRight(long id, int type, int visible)
  {
    MemberRight right = null;
    try
    {
      String queryString = "from MemberRight as model where model.id= ? ";
      getHibernateTemplate().enableFilter("rightTreeFilter").setParameter("type", Integer.valueOf(type)).setParameter("visible", Integer.valueOf(visible));
      List<MemberRight> list = getHibernateTemplate().find(queryString, Long.valueOf(id));
      if (list.size() > 0) {
        right = (MemberRight)list.get(0);
      }
    }
    catch (Exception re)
    {
      this.logger.error("find by property name failed", re);
    }
    return right;
  }
}

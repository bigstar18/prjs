package gnnt.MEBS.common.dao;

import gnnt.MEBS.base.dao.hibernate.DaoHelper;
import java.util.List;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

@Repository("outSideDao")
public class OutSideDao
  extends DaoHelper
{
  public List getList(String sql)
  {
    return getHibernateTemplate().find(sql);
  }
}

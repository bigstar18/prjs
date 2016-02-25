package gnnt.MEBS.common.dao;

import gnnt.MEBS.common.dao.impl.DaoHelperImpl;
import java.util.List;
import org.springframework.orm.hibernate3.HibernateTemplate;

public class OutSideDao
  extends DaoHelperImpl
{
  public List getList(String paramString)
  {
    return getHibernateTemplate().find(paramString);
  }
}

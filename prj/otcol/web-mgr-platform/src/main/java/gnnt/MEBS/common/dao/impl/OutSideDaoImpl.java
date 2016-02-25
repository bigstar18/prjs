package gnnt.MEBS.common.dao.impl;

import java.util.List;
import org.springframework.orm.hibernate3.HibernateTemplate;

public class OutSideDaoImpl
  extends DaoHelperImpl
{
  public List getList(String paramString)
  {
    return getHibernateTemplate().find(paramString);
  }
}

package gnnt.MEBS.broker.mgr.dao;

import gnnt.MEBS.common.mgr.dao.StandardDao;
import gnnt.MEBS.common.mgr.model.StandardModel;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

@Repository("baseDao")
public class BaseDao extends StandardDao
{
  public void add(StandardModel paramStandardModel)
  {
    getHibernateTemplate().save(paramStandardModel);
    flush();
    getHibernateTemplate().clear();
  }

  public void update(StandardModel paramStandardModel)
  {
    getHibernateTemplate().update(paramStandardModel);
    flush();
    getHibernateTemplate().clear();
  }
}
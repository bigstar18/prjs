package cn.com.agree.eteller.afa.dao.hibernate;

import cn.com.agree.eteller.afa.dao.IAfaAgentadmDao;
import cn.com.agree.eteller.afa.persistence.AfaAgentadm;
import cn.com.agree.eteller.generic.utils.Pagination;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

public class AfaAgentadmDao
  extends HibernateDaoSupport
  implements IAfaAgentadmDao
{
  public boolean addAfaAgentadm(final AfaAgentadm ca)
  {
    boolean isAdded = false;
    try
    {
      getHibernateTemplate().execute(new HibernateCallback()
      {
        public Object doInHibernate(Session session)
          throws HibernateException
        {
          session.save(ca);
          return null;
        }
      });
      isAdded = true;
    }
    catch (Exception ex)
    {
      ex.printStackTrace();
      isAdded = false;
    }
    return isAdded;
  }
  
  public boolean deleteAfaAgentadm(final AfaAgentadm ca)
  {
    boolean isDeleted = false;
    try
    {
      getHibernateTemplate().execute(new HibernateCallback()
      {
        public Object doInHibernate(Session session)
          throws HibernateException, SQLException
        {
          session.delete(ca);
          return null;
        }
      });
      isDeleted = true;
    }
    catch (Exception ex)
    {
      ex.printStackTrace();
      isDeleted = false;
    }
    return isDeleted;
  }
  
  public AfaAgentadm[] getAfaAgentadmBymap(final Map map)
  {
    final List list = new ArrayList();
    getHibernateTemplate().execute(new HibernateCallback()
    {
      public Object doInHibernate(Session session)
        throws HibernateException, SQLException
      {
        Criteria criteria = session
          .createCriteria(AfaAgentadm.class);
        if (!map.get("sysid").toString().equals("00000")) {
          criteria.add(Expression.eq("comp_id.sysid", map.get("sysid")));
        }
        if (!map.get("unitno").toString().equals("00000")) {
          criteria.add(Expression.eq("comp_id.unitno", map.get("unitno")));
        }
        if (!map.get("zoneno").toString().equals("00000")) {
          criteria.add(Expression.eq("comp_id.zoneno", map.get("zoneno")));
        }
        if (!map.get("zhno").toString().equals("00000")) {
          criteria.add(Expression.eq("comp_id.zhno", map.get("zhno")));
        }
        if (!map.get("agentflag").toString().equals("00000")) {
          criteria.add(Expression.eq("comp_id.agentflag", map.get("agentflag")));
        }
        list.addAll(criteria.list());
        return null;
      }
    });
    return (AfaAgentadm[])list.toArray(new AfaAgentadm[0]);
  }
  
  public AfaAgentadm[] getAllAfaAgentadm()
  {
    List ls = new ArrayList();
    try
    {
      ls = getHibernateTemplate().loadAll(AfaAgentadm.class);
    }
    catch (Exception ex)
    {
      ex.printStackTrace();
    }
    return (AfaAgentadm[])ls.toArray(new AfaAgentadm[0]);
  }
  
  public boolean updateAfaAgentadm(AfaAgentadm ca)
  {
    boolean isUpdated = false;
    try
    {
      getHibernateTemplate().update(ca);
      isUpdated = true;
    }
    catch (Exception ex)
    {
      ex.printStackTrace();
      isUpdated = false;
    }
    return isUpdated;
  }
  
  public AfaAgentadm getAfaAgentadmBysql(String sql)
  {
    List list = new ArrayList();
    list = getHibernateTemplate().find(sql);
    return (AfaAgentadm)list.get(0);
  }
  
  public List<AfaAgentadm> getAfaAgentadmByMap(Map<String, String> map, Pagination page)
  {
    Criteria criteria = getSession(true).createCriteria(AfaAgentadm.class);
    if (map.get("sysid") != null) {
      criteria.add(Restrictions.eq("comp_id.sysid", map.get("sysid")));
    }
    if (map.get("unitno") != null) {
      criteria.add(Restrictions.eq("comp_id.unitno", map.get("unitno")));
    }
    if (map.get("subunitno") != null) {
      criteria.add(Restrictions.eq("comp_id.subunitno", map.get("subunitno")));
    }
    if (map.get("agentflag") != null) {
      criteria.add(Restrictions.eq("comp_id.agentflag", map.get("agentflag")));
    }
    if (map.get("zoneno") != null) {
      criteria.add(Restrictions.eq("comp_id.zoneno", map.get("zoneno")));
    }
    if (map.get("zhno") != null) {
      criteria.add(Restrictions.eq("comp_id.zhno", map.get("zhno")));
    }
    criteria.addOrder(Order.asc("comp_id.sysid"));
    criteria.addOrder(Order.asc("comp_id.unitno"));
    criteria.addOrder(Order.asc("comp_id.subunitno"));
    page.setAllRecords(Integer.valueOf(criteria.list().size()));
    criteria.setFirstResult(page.getFirstRecord().intValue());
    criteria.setMaxResults(page.getPerPageRecords().intValue());
    
    return criteria.list();
  }
}

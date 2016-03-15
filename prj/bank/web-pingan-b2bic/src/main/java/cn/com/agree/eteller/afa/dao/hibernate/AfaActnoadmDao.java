package cn.com.agree.eteller.afa.dao.hibernate;

import cn.com.agree.eteller.afa.dao.IAfaActnoadmDao;
import cn.com.agree.eteller.afa.persistence.AfaActnoadm;
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

public class AfaActnoadmDao
  extends HibernateDaoSupport
  implements IAfaActnoadmDao
{
  public AfaActnoadm[] loadAfaActnoadm(final Map map)
  {
    final List list = new ArrayList();
    getHibernateTemplate().execute(new HibernateCallback()
    {
      public Object doInHibernate(Session session)
        throws HibernateException, SQLException
      {
        Criteria criteria = session
          .createCriteria(AfaActnoadm.class);
        if (!map.get("zoneno").toString().equals("xxxxx")) {
          criteria.add(Expression.eq("comp_id.zoneno", map.get("zoneno")));
        }
        if (!map.get("zhno").toString().equals("xxxxx")) {
          criteria.add(Expression.eq("comp_id.zhno", map.get("zhno")));
        }
        if (!map.get("agentflag").toString().equals("00000")) {
          criteria.add(Expression.eq("comp_id.agentflag", map.get("agentflag")));
        }
        if (!map.get("channelcode").toString().equals("00000")) {
          criteria.add(Expression.eq("comp_id.channelcode", map.get("channelcode")));
        }
        if (!map.get("acttypecode").toString().equals("00000")) {
          criteria.add(Expression.eq("comp_id.acttypecode", map.get("acttypecode")));
        }
        list.addAll(criteria.list());
        return null;
      }
    });
    return (AfaActnoadm[])list.toArray(new AfaActnoadm[0]);
  }
  
  public boolean addAfaActnoadm(final AfaActnoadm ca)
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
  
  public boolean deleteAfaActnoadm(final AfaActnoadm ca)
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
  
  public boolean updateAfaActnoadm(AfaActnoadm ca)
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
  
  public AfaActnoadm getAfaActnoadmBysql(String sql)
  {
    List list = new ArrayList();
    list = getHibernateTemplate().find(sql);
    return (AfaActnoadm)list.get(0);
  }
  
  public List<AfaActnoadm> getAfaActnoadmByMap(Map<String, String> map, Pagination page)
  {
    Criteria criteria = getSession(true).createCriteria(AfaActnoadm.class);
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
    if (map.get("channelcode") != null) {
      criteria.add(Restrictions.eq("comp_id.channelcode", map.get("channelcode")));
    }
    if (map.get("acttypecode") != null) {
      criteria.add(Restrictions.eq("comp_id.acttypecode", map.get("acttypecode")));
    }
    criteria.addOrder(Order.asc("comp_id.sysid"));
    criteria.addOrder(Order.asc("comp_id.unitno"));
    page.setAllRecords(Integer.valueOf(criteria.list().size()));
    criteria.setFirstResult(page.getFirstRecord().intValue());
    criteria.setMaxResults(page.getPerPageRecords().intValue());
    
    return criteria.list();
  }
}

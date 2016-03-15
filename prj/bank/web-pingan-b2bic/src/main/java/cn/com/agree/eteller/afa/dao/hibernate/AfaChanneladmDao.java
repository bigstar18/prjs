package cn.com.agree.eteller.afa.dao.hibernate;

import cn.com.agree.eteller.afa.dao.IAfaChanneladmDao;
import cn.com.agree.eteller.afa.persistence.AfaChanneladm;
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

public class AfaChanneladmDao
  extends HibernateDaoSupport
  implements IAfaChanneladmDao
{
  public boolean addAfaChanneladm(final AfaChanneladm ca)
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
  
  public boolean deleteAfaChanneladm(final AfaChanneladm ca)
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
  
  public AfaChanneladm[] getAfaChanneladmBymap(final Map map)
  {
    final List list = new ArrayList();
    getHibernateTemplate().execute(new HibernateCallback()
    {
      public Object doInHibernate(Session session)
        throws HibernateException, SQLException
      {
        Criteria criteria = session.createCriteria(AfaChanneladm.class);
        if (!map.get("zoneno").toString().equals("xxxxx")) {
          criteria.add(Expression.eq("comp_id.zoneno", map
            .get("zoneno")));
        }
        if (!map.get("zhno").toString().equals("xxxxx")) {
          criteria.add(Expression.eq("comp_id.zhno", map.get("zhno")));
        }
        if (!map.get("agentflag").toString().equals("00000")) {
          criteria.add(Expression.eq("comp_id.agentflag", map
            .get("agentflag")));
        }
        if (!map.get("channelcode").toString().equals("00000")) {
          criteria.add(Expression.eq("comp_id.channelcode", map
            .get("channelcode")));
        }
        list.addAll(criteria.list());
        return null;
      }
    });
    return (AfaChanneladm[])list.toArray(new AfaChanneladm[0]);
  }
  
  public AfaChanneladm[] getAfaChanneladmBymap2(final Map map)
  {
    final List list = new ArrayList();
    getHibernateTemplate().execute(new HibernateCallback()
    {
      public Object doInHibernate(Session session)
        throws HibernateException, SQLException
      {
        Criteria criteria = session.createCriteria(AfaChanneladm.class);
        if ((map.get("sysid") != null) && 
          (!map.get("sysid").toString().equals(""))) {
          criteria.add(Expression.eq("comp_id.sysid", map
            .get("sysid")));
        }
        if ((map.get("unitno") != null) && 
          (!map.get("unitno").toString().equals(""))) {
          criteria.add(Expression.eq("comp_id.unitno", map
            .get("unitno")));
        }
        list.addAll(criteria.list());
        return null;
      }
    });
    return (AfaChanneladm[])list.toArray(new AfaChanneladm[0]);
  }
  
  public boolean updateAfaChanneladm(AfaChanneladm ca)
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
  
  public AfaChanneladm getAfaChanneladmBysql(String sql)
  {
    List list = new ArrayList();
    list = getHibernateTemplate().find(sql);
    return (AfaChanneladm)list.get(0);
  }
  
  public List<AfaChanneladm> getAfaChanneladmByMap(Map<String, String> map, Pagination page)
  {
    Criteria criteria = getSession(true).createCriteria(AfaChanneladm.class);
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
    criteria.addOrder(Order.asc("comp_id.sysid"));
    criteria.addOrder(Order.asc("comp_id.unitno"));
    criteria.addOrder(Order.asc("comp_id.subunitno"));
    page.setAllRecords(Integer.valueOf(criteria.list().size()));
    criteria.setFirstResult(page.getFirstRecord().intValue());
    criteria.setMaxResults(page.getPerPageRecords().intValue());
    
    return criteria.list();
  }
}

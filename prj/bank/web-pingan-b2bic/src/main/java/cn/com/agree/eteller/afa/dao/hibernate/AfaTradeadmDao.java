package cn.com.agree.eteller.afa.dao.hibernate;

import cn.com.agree.eteller.afa.dao.IAfaTradeadmDao;
import cn.com.agree.eteller.afa.persistence.AfaTradeadm;
import cn.com.agree.eteller.generic.utils.Pagination;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

public class AfaTradeadmDao
  extends HibernateDaoSupport
  implements IAfaTradeadmDao
{
  public boolean addAfaTradeadm(final AfaTradeadm ca)
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
  
  public boolean deleteAfaTradeadm(final AfaTradeadm ca)
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
  
  public AfaTradeadm[] getAfaTradeadmBymap(final Map map)
  {
    final List list = new ArrayList();
    getHibernateTemplate().execute(new HibernateCallback()
    {
      public Object doInHibernate(Session session)
        throws HibernateException, SQLException
      {
        Criteria criteria = session
          .createCriteria(AfaTradeadm.class);
        if (!map.get("sysid").toString().equals("00000")) {
          criteria.add(Expression.eq("comp_id.sysid", map.get("sysid")));
        }
        if (!map.get("unitno").toString().equals("00000")) {
          criteria.add(Expression.eq("comp_id.unitno", map.get("unitno")));
        }
        if (!map.get("trxcode").toString().equals("00000")) {
          criteria.add(Expression.eq("comp_id.trxcode", map.get("trxcode")));
        }
        if (!map.get("trxname").toString().equals("00000"))
        {
          String tmpStr = "%" + map.get("trxname") + "%";
          criteria.add(Expression.like("trxname", tmpStr));
        }
        list.addAll(criteria.list());
        return null;
      }
    });
    return (AfaTradeadm[])list.toArray(new AfaTradeadm[0]);
  }
  
  public AfaTradeadm[] getAllAfaTradeadm()
  {
    List ls = new ArrayList();
    try
    {
      ls = getHibernateTemplate().loadAll(AfaTradeadm.class);
    }
    catch (Exception ex)
    {
      ex.printStackTrace();
    }
    return (AfaTradeadm[])ls.toArray(new AfaTradeadm[0]);
  }
  
  public boolean updateAfaTradeadm(AfaTradeadm ca)
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
  
  public AfaTradeadm getAfaTradeadmBysql(String sql)
  {
    List list = new ArrayList();
    list = getHibernateTemplate().find(sql);
    return (AfaTradeadm)list.get(0);
  }
  
  public List<AfaTradeadm> getAfaTradeadmByMap(Map<String, String> map, Pagination page)
  {
    Criteria criteria = getSession(true).createCriteria(AfaTradeadm.class);
    if (map.get("sysid") != null) {
      criteria.add(Restrictions.eq("comp_id.sysid", map.get("sysid")));
    }
    if (map.get("unitno") != null) {
      criteria.add(Restrictions.eq("comp_id.unitno", map.get("unitno")));
    }
    if (map.get("subunitno") != null) {
      criteria.add(Restrictions.eq("comp_id.subunitno", map.get("subunitno")));
    }
    if (map.get("trxcode") != null) {
      criteria.add(Restrictions.eq("comp_id.trxcode", map.get("trxcode")));
    }
    if (map.get("trxname") != null) {
      criteria.add(Restrictions.like("trxname", (String)map.get("trxname"), MatchMode.ANYWHERE));
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

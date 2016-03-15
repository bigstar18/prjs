package cn.com.agree.eteller.afa.dao.hibernate;

import cn.com.agree.eteller.afa.dao.IAfaSubunitadmDao;
import cn.com.agree.eteller.afa.persistence.AfaSubunitadm;
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
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

public class AfaSubunitadmDao
  extends HibernateDaoSupport
  implements IAfaSubunitadmDao
{
  public boolean addSubunitadm(final AfaSubunitadm ca)
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
  
  public boolean deleteSubunitadm(final AfaSubunitadm ca)
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
  
  public AfaSubunitadm[] getSubunitadmBymap(final Map map)
  {
    final List list = new ArrayList();
    getHibernateTemplate().execute(new HibernateCallback()
    {
      public Object doInHibernate(Session session)
        throws HibernateException, SQLException
      {
        Criteria criteria = session
          .createCriteria(AfaSubunitadm.class);
        if (!map.get("unitno").toString().equals("00000")) {
          criteria.add(Expression.eq("comp_id.unitno", map.get("unitno")));
        }
        if (!map.get("subunitno").toString().equals("00000")) {
          criteria.add(Expression.eq("comp_id.subunitno", map.get("subunitno")));
        }
        if (!map.get("subunitname").toString().equals("00000"))
        {
          String tmpStr = "%" + map.get("subunitname") + "%";
          criteria.add(Expression.like("subunitname", tmpStr));
        }
        list.addAll(criteria.list());
        return null;
      }
    });
    return (AfaSubunitadm[])list.toArray(new AfaSubunitadm[0]);
  }
  
  public boolean updateSubunitadm(AfaSubunitadm ca)
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
  
  public AfaSubunitadm[] getAllsubunitadm()
  {
    List ls = new ArrayList();
    try
    {
      ls = getHibernateTemplate().loadAll(AfaSubunitadm.class);
    }
    catch (Exception ex)
    {
      ex.printStackTrace();
    }
    return (AfaSubunitadm[])ls.toArray(new AfaSubunitadm[0]);
  }
  
  public AfaSubunitadm[] getSubunitadmByunitno(final String sysid, final String unitno)
  {
    final List list = new ArrayList();
    try
    {
      getHibernateTemplate().execute(new HibernateCallback()
      {
        public Object doInHibernate(Session session)
          throws HibernateException
        {
          List subunitno = AfaSubunitadmDao.this.getHibernateTemplate().find(
            "from AfaSubunitadm sunitno where sunitno.comp_id.sysid=? and sunitno.comp_id.unitno=?", 
            new String[] { sysid, unitno });
          list.addAll(subunitno);
          return null;
        }
      });
    }
    catch (Exception ex)
    {
      ex.printStackTrace();
    }
    return (AfaSubunitadm[])list.toArray(new AfaSubunitadm[0]);
  }
  
  public AfaSubunitadm getAfaSubunitadmBysql(String sql)
  {
    List list = new ArrayList();
    list = getHibernateTemplate().find(sql);
    return (AfaSubunitadm)DataAccessUtils.uniqueResult(list);
  }
  
  public List<AfaSubunitadm> getAfaSubunitadmByMap(Map<String, String> map, Pagination page)
  {
    Criteria criteria = getSession(true).createCriteria(AfaSubunitadm.class);
    if (map.get("sysid") != null) {
      criteria.add(Restrictions.eq("comp_id.sysid", map.get("sysid")));
    }
    if (map.get("unitno") != null) {
      criteria.add(Restrictions.eq("comp_id.unitno", map.get("unitno")));
    }
    if (map.get("subunitno") != null) {
      criteria.add(Restrictions.eq("comp_id.subunitno", map.get("subunitno")));
    }
    if (map.get("subunitname") != null) {
      criteria.add(Restrictions.like("subunitname", (String)map.get("subunitname"), MatchMode.ANYWHERE));
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

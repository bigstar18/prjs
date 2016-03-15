package cn.com.agree.eteller.afa.dao.hibernate;

import cn.com.agree.eteller.afa.dao.IAfaUnitadmDao;
import cn.com.agree.eteller.afa.persistence.AfaUnitadm;
import cn.com.agree.eteller.generic.utils.ComFunction;
import cn.com.agree.eteller.generic.utils.Pagination;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
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

public class AfaUnitadmDao
  extends HibernateDaoSupport
  implements IAfaUnitadmDao
{
  public boolean addAfaUnitadm(final AfaUnitadm ca)
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
  
  public boolean deleteAfaUnitadm(final AfaUnitadm ca)
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
  
  public AfaUnitadm[] getAfaUnitadmBymap(final Map map)
  {
    final List list = new ArrayList();
    getHibernateTemplate().execute(new HibernateCallback()
    {
      public Object doInHibernate(Session session)
        throws HibernateException, SQLException
      {
        Criteria criteria = session.createCriteria(AfaUnitadm.class);
        if ((!ComFunction.isEmpty(map.get("sysid"))) && (!map.get("sysid").toString().equals("00000"))) {
          criteria.add(Expression.eq("comp_id.sysid", map
            .get("sysid")));
        }
        if ((!ComFunction.isEmpty(map.get("unitno"))) && (!map.get("unitno").toString().equals("00000"))) {
          criteria.add(Expression.eq("comp_id.unitno", map
            .get("unitno")));
        }
        if ((!ComFunction.isEmpty(map.get("unitname"))) && (!map.get("unitname").toString().equals("00000")))
        {
          String tmpStr = "%" + map.get("unitname") + "%";
          criteria.add(Expression.like("unitname", tmpStr));
        }
        if (!ComFunction.isEmpty(map.get("name"))) {
          criteria.add(Expression.eq("name", map
            .get("name")));
        }
        list.addAll(criteria.list());
        return null;
      }
    });
    return (AfaUnitadm[])list.toArray(new AfaUnitadm[0]);
  }
  
  public boolean updateAfaUnitadm(AfaUnitadm ca)
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
  
  public AfaUnitadm[] getAllAfaUnitadm()
  {
    List ls = new ArrayList();
    try
    {
      ls = getHibernateTemplate().loadAll(AfaUnitadm.class);
    }
    catch (Exception ex)
    {
      ex.printStackTrace();
    }
    return (AfaUnitadm[])ls.toArray(new AfaUnitadm[0]);
  }
  
  public AfaUnitadm[] getAfaUnitadmBysysid(final String sysid)
  {
    final List list = new ArrayList();
    try
    {
      getHibernateTemplate().execute(new HibernateCallback()
      {
        public Object doInHibernate(Session session)
          throws HibernateException
        {
          List unitno = AfaUnitadmDao.this.getHibernateTemplate().find(
            "from AfaUnitadm t where t.comp_id.sysid=?  order by t.comp_id.unitno asc ", sysid);
          list.addAll(unitno);
          return null;
        }
      });
    }
    catch (Exception ex)
    {
      ex.printStackTrace();
    }
    return (AfaUnitadm[])list.toArray(new AfaUnitadm[0]);
  }
  
  public AfaUnitadm[] getAfaUnitadmBysysid2(final String sysid)
  {
    final List list = new ArrayList();
    try
    {
      getHibernateTemplate().execute(new HibernateCallback()
      {
        public Object doInHibernate(Session session)
          throws HibernateException
        {
          List unitno = AfaUnitadmDao.this.getHibernateTemplate()
            .find(
            "from AfaUnitadm t where t.comp_id.sysid=? and substr(t.comp_id.unitno,5)<>'0000' order by t.comp_id.unitno asc ", 
            sysid);
          list.addAll(unitno);
          return null;
        }
      });
    }
    catch (Exception ex)
    {
      ex.printStackTrace();
    }
    return (AfaUnitadm[])list.toArray(new AfaUnitadm[0]);
  }
  
  public AfaUnitadm[] getAfaUnitadmBysysid3(final String sysid)
  {
    final List list = new ArrayList();
    try
    {
      getHibernateTemplate().execute(new HibernateCallback()
      {
        public Object doInHibernate(Session session)
          throws HibernateException
        {
          List unitno = AfaUnitadmDao.this.getHibernateTemplate()
            .find(
            "from AfaUnitadm t where t.comp_id.sysid=? and substr(t.comp_id.unitno,5)='0000'  order by t.comp_id.unitno asc ", 
            sysid);
          list.addAll(unitno);
          return null;
        }
      });
    }
    catch (Exception ex)
    {
      ex.printStackTrace();
    }
    return (AfaUnitadm[])list.toArray(new AfaUnitadm[0]);
  }
  
  public AfaUnitadm getAfaUnitadmBysql(String sql)
  {
    List list = new ArrayList();
    list = getHibernateTemplate().find(sql);
    return (AfaUnitadm)list.get(0);
  }
  
  public List<AfaUnitadm> getAfaUnitadmByMap(Map<String, String> map, Pagination page)
  {
    Criteria criteria = getSession(true).createCriteria(AfaUnitadm.class);
    if (map.get("sysid") != null) {
      criteria.add(Restrictions.eq("comp_id.sysid", map.get("sysid")));
    }
    if (map.get("unitno") != null) {
      criteria.add(Restrictions.eq("comp_id.unitno", map.get("unitno")));
    }
    if (map.get("unitname") != null) {
      criteria.add(Restrictions.like("unitname", ((String)map.get("unitname")).trim(), MatchMode.ANYWHERE));
    }
    criteria.addOrder(Order.asc("comp_id.sysid"));
    criteria.addOrder(Order.asc("comp_id.unitno"));
    page.setAllRecords(Integer.valueOf(criteria.list().size()));
    criteria.setFirstResult(page.getFirstRecord().intValue());
    criteria.setMaxResults(page.getPerPageRecords().intValue());
    
    return criteria.list();
  }
  
  public AfaUnitadm getAfaUnitadmById(String sysid, String unitno)
  {
    AfaUnitadm unitadm = null;
    Map<String, String> map = new HashMap();
    map.put("sysid", sysid);
    map.put("unitno", unitno);
    List<AfaUnitadm> list = getAfaUnitadmByMap(map, new Pagination());
    if ((list != null) && (list.size() > 0)) {
      unitadm = (AfaUnitadm)list.get(0);
    }
    return unitadm;
  }
}

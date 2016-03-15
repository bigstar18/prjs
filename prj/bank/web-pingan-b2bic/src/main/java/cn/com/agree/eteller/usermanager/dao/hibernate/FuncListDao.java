package cn.com.agree.eteller.usermanager.dao.hibernate;

import cn.com.agree.eteller.generic.utils.Pagination;
import cn.com.agree.eteller.usermanager.dao.IFuncListDao;
import cn.com.agree.eteller.usermanager.persistence.Appinfo;
import cn.com.agree.eteller.usermanager.persistence.Funclist;
import cn.com.agree.eteller.usermanager.persistence.Rolelist;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

public class FuncListDao
  extends HibernateDaoSupport
  implements IFuncListDao
{
  public boolean addFunction(Funclist bf)
  {
    boolean isAdded = false;
    try
    {
      getHibernateTemplate().save(bf);
      isAdded = true;
    }
    catch (Exception ex)
    {
      ex.printStackTrace();
      isAdded = false;
    }
    return isAdded;
  }
  
  public boolean deleteFunction(final long fcid)
  {
    boolean isDeleted = false;
    try
    {
      getHibernateTemplate().execute(new HibernateCallback()
      {
        public Object doInHibernate(Session session)
          throws HibernateException
        {
          Funclist bf = 
          
            (Funclist)FuncListDao.this.getHibernateTemplate().find("from Funclist fc where fc.funcId=?", new Long(fcid)).get(0);
          
          Set roles = bf.getRoles();
          for (Iterator iter = roles.iterator(); iter.hasNext();)
          {
            Rolelist role = (Rolelist)iter.next();
            role.getFunctions().remove(bf);
          }
          session.delete(bf);
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
  
  public Funclist[] getFunctionByAppId(String appid, Pagination page)
  {
    List list = getSession()
      .createQuery("from Funclist fa where fa.appid=?")
      .setString(0, appid)
      .setFirstResult(page.getFirstRecord().intValue())
      .setMaxResults(page.getPerPageRecords().intValue())
      .list();
    return (Funclist[])list.toArray(new Funclist[0]);
  }
  
  public Funclist[] getFunctionBySubAppId(final String appid, final String subappid)
  {
    final List list = new ArrayList();
    try
    {
      getHibernateTemplate().execute(new HibernateCallback()
      {
        public Object doInHibernate(Session session)
          throws HibernateException, SQLException
        {
          Criteria criteria = session
            .createCriteria(Funclist.class);
          if (!appid.equals("00000")) {
            criteria.add(Expression.eq("appid", appid));
          }
          if (!subappid.equals("00000")) {
            criteria.add(Expression.eq("subappid", subappid));
          }
          list.addAll(criteria.list());
          return null;
        }
      });
    }
    catch (Exception localException) {}
    return (Funclist[])list.toArray(new Funclist[0]);
  }
  
  public boolean updateFunction(Funclist mfc)
  {
    boolean isUpdated = false;
    try
    {
      getHibernateTemplate().update(mfc);
      isUpdated = true;
    }
    catch (Exception ex)
    {
      ex.printStackTrace();
      isUpdated = false;
    }
    return isUpdated;
  }
  
  public Funclist[] getFunctionByRoleId(final Serializable roleId)
  {
    final List list = new ArrayList();
    try
    {
      getHibernateTemplate().execute(new HibernateCallback()
      {
        public Object doInHibernate(Session session)
          throws HibernateException
        {
          List cr = FuncListDao.this.getHibernateTemplate().find(
            "from Rolelist cr where cr.roleId=?", roleId);
          list.addAll(((Rolelist)cr.get(0)).getFunctions());
          return null;
        }
      });
    }
    catch (Exception ex)
    {
      ex.printStackTrace();
    }
    return (Funclist[])list.toArray(new Funclist[0]);
  }
  
  public Funclist[] getAllFunction()
  {
    List list = getHibernateTemplate().loadAll(Funclist.class);
    List applist = getHibernateTemplate().loadAll(Appinfo.class);
    for (Iterator iter = list.iterator(); iter.hasNext();)
    {
      Funclist cf = (Funclist)iter.next();
      for (Iterator iterator = applist.iterator(); iterator.hasNext();)
      {
        Appinfo app = (Appinfo)iterator.next();
        if (cf.getAppid().equals(app.getAppid()))
        {
          cf.setAppname(app.getAppname());
          break;
        }
      }
    }
    return (Funclist[])list.toArray(new Funclist[0]);
  }
  
  public Set getRoleFunctions(Serializable roleId)
  {
    Rolelist role = (Rolelist)getHibernateTemplate().get(Rolelist.class, roleId);
    return new HashSet(role.getFunctions());
  }
  
  public Funclist[] getFunctionList(final Funclist chkFunc)
  {
    final List ls = new ArrayList();
    List applist = getHibernateTemplate().loadAll(Appinfo.class);
    try
    {
      getHibernateTemplate().execute(new HibernateCallback()
      {
        public Object doInHibernate(Session session)
          throws HibernateException, SQLException
        {
          Criteria criteria = session
            .createCriteria(Funclist.class);
          if (!chkFunc.getAppid().equals("00000")) {
            criteria.add(Expression.eq("appid", chkFunc.getAppid()));
          }
          if (!chkFunc.getSubappid().equals("00000")) {
            criteria.add(Expression.eq("subappid", chkFunc.getSubappid()));
          }
          if (!chkFunc.getFuncName().equals("00000")) {
            criteria.add(Expression.like("funcName", "%" + chkFunc.getFuncName() + "%"));
          }
          if (!chkFunc.getFuncAddress().equals("00000")) {
            criteria.add(Expression.like("funcAddress", "%" + chkFunc.getFuncAddress() + "%"));
          }
          ls.addAll(criteria.list());
          return null;
        }
      });
    }
    catch (Exception localException) {}
    for (Iterator iter = ls.iterator(); iter.hasNext();)
    {
      Funclist cf = (Funclist)iter.next();
      for (Iterator iterator = applist.iterator(); iterator.hasNext();)
      {
        Appinfo app = (Appinfo)iterator.next();
        if (cf.getAppid().equals(app.getAppid()))
        {
          cf.setAppname(app.getAppname());
          break;
        }
      }
    }
    return (Funclist[])ls.toArray(new Funclist[0]);
  }
  
  public Funclist[] getFunclistByMap(final Map map, final Pagination page)
    throws Exception
  {
    final List list = new ArrayList();
    getHibernateTemplate().execute(new HibernateCallback()
    {
      public Object doInHibernate(Session session)
        throws HibernateException, SQLException
      {
        Criteria criteria = session.createCriteria(Funclist.class);
        if ((map.get("appid") != null) && 
          (!map.get("appid").equals("0000000000")) && 
          (map.get("appid").toString().length() > 0)) {
          criteria.add(Expression.eq("appid", map.get("appid")));
        }
        if ((map.get("subappid") != null) && 
          (!map.get("subappid").equals("00000")) && 
          (map.get("subappid").toString().length() > 0)) {
          criteria.add(Expression.eq("subappid", map.get("subappid")));
        }
        if ((map.get("funcAddress") != null) && 
          (map.get("funcAddress").toString().length() > 0)) {
          criteria.add(Expression.like("funcAddress", (String)map.get("funcAddress"), MatchMode.ANYWHERE));
        }
        page.setAllRecords((Integer)criteria.setProjection(Projections.count("funcId")).uniqueResult());
        criteria.setProjection(null);
        
        criteria = criteria
          .setFirstResult(page.getFirstRecord().intValue())
          .setMaxResults(page.getPerPageRecords().intValue());
        
        list.addAll(criteria.list());
        
        return null;
      }
    });
    return (Funclist[])list.toArray(new Funclist[0]);
  }
  
  public List<Funclist> getFunclistByMap2(Map<String, String> map, Pagination page)
  {
    Criteria criteria = getSession(true).createCriteria(Funclist.class);
    if (map.get("appid") != null) {
      criteria.add(Restrictions.eq("appid", map.get("appid")));
    }
    if (map.get("subappid") != null) {
      criteria.add(Restrictions.eq("subappid", map.get("subappid")));
    }
    if (map.get("funcAddress") != null) {
      criteria.add(Restrictions.like("funcAddress", (String)map.get("funcAddress"), MatchMode.ANYWHERE));
    }
    if (map.get("funcName") != null) {
      criteria.add(Restrictions.like("funcName", (String)map.get("funcName"), MatchMode.ANYWHERE));
    }
    criteria.addOrder(Order.asc("funcId"));
    page.setAllRecords(Integer.valueOf(criteria.list().size()));
    criteria.setFirstResult(page.getFirstRecord().intValue());
    criteria.setMaxResults(page.getPerPageRecords().intValue());
    
    return criteria.list();
  }
}

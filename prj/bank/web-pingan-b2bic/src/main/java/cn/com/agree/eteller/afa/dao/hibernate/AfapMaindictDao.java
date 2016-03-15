package cn.com.agree.eteller.afa.dao.hibernate;

import cn.com.agree.eteller.afa.dao.IAfapMaindictDao;
import cn.com.agree.eteller.afa.persistence.AfapMaindict;
import cn.com.agree.eteller.afa.persistence.AfapSubdict;
import cn.com.agree.eteller.generic.utils.ComFunction;
import cn.com.agree.eteller.generic.utils.Pagination;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
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

public class AfapMaindictDao
  extends HibernateDaoSupport
  implements IAfapMaindictDao
{
  public boolean addAfapMaindict(final AfapMaindict ca)
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
  
  public boolean deleteAfapMaindict(final AfapMaindict ca)
  {
    boolean isDeleted = false;
    try
    {
      getHibernateTemplate().delete(ca);
      getHibernateTemplate().execute(new HibernateCallback()
      {
        public Object doInHibernate(Session session)
          throws HibernateException, SQLException
        {
          List list = AfapMaindictDao.this.getHibernateTemplate().find(
            "from AfapSubdict s where s.comp_id.item=?", ca
            .getItem());
          for (Iterator iter = list.iterator(); iter.hasNext();)
          {
            AfapSubdict sub = (AfapSubdict)iter.next();
            session.delete(sub);
          }
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
  
  public boolean updateAfapMaindict(AfapMaindict ca)
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
  
  public String getNextItem()
  {
    List list = getHibernateTemplate().find(
      "select max(afa.item) from AfapMaindict afa");
    String itemId = (String)list.get(0);
    if (itemId == null) {
      return "00001";
    }
    int id = Integer.parseInt(itemId);
    id++;
    String tmpStr = String.valueOf(id);
    int len = tmpStr.length();
    for (int i = 0; i < 5 - len; i++) {
      tmpStr = "0" + tmpStr;
    }
    return tmpStr;
  }
  
  public AfapMaindict[] getAfapMaindictByMap(final Map map)
  {
    final List list = new ArrayList();
    getHibernateTemplate().execute(new HibernateCallback()
    {
      public Object doInHibernate(Session session)
        throws HibernateException, SQLException
      {
        Criteria criteria = session.createCriteria(AfapMaindict.class);
        if ((!ComFunction.isEmpty(map.get("item"))) && (!"0".equals(map.get("item")))) {
          criteria.add(Expression.eq("item", map.get("item")));
        }
        if ((!ComFunction.isEmpty(map.get("itemename"))) && (!"0".equals(map.get("itemename"))))
        {
          String tmpStr = "%" + map.get("itemename") + "%";
          criteria.add(Expression.like("itemename", tmpStr));
        }
        if ((!ComFunction.isEmpty(map.get("itemcname"))) && (!"0".equals(map.get("itemcname"))))
        {
          String tmpStr = "%" + map.get("itemcname") + "%";
          criteria.add(Expression.like("itemcname", tmpStr));
        }
        list.addAll(criteria.list());
        return null;
      }
    });
    return (AfapMaindict[])list.toArray(new AfapMaindict[0]);
  }
  
  public AfapMaindict[] getAfapMaindictByItem(final String itemename)
  {
    final List list = new ArrayList();
    try
    {
      getHibernateTemplate().execute(new HibernateCallback()
      {
        public Object doInHibernate(Session session)
          throws HibernateException
        {
          List maindict = AfapMaindictDao.this.getHibernateTemplate().find(
            "from AfapMaindict dict where dict.itemename=?", 
            itemename);
          list.addAll(maindict);
          return null;
        }
      });
    }
    catch (Exception ex)
    {
      ex.printStackTrace();
    }
    return (AfapMaindict[])list.toArray(new AfapMaindict[0]);
  }
  
  public AfapMaindict[] getAllAfapMaindict()
  {
    List ls = new ArrayList();
    try
    {
      ls = getHibernateTemplate().loadAll(AfapMaindict.class);
    }
    catch (Exception ex)
    {
      ex.printStackTrace();
    }
    return (AfapMaindict[])ls.toArray(new AfapMaindict[0]);
  }
  
  public AfapMaindict getAfapMaindictBysql(String sql)
  {
    List list = new ArrayList();
    AfapMaindict maindict = null;
    try
    {
      list = getHibernateTemplate().find(sql);
    }
    catch (Exception ex)
    {
      ex.printStackTrace();
    }
    if ((list != null) && (list.size() > 0)) {
      maindict = (AfapMaindict)list.get(0);
    }
    return maindict;
  }
  
  public List<AfapMaindict> getAfapMaindictByMap(Map<String, Object> map, Pagination page)
  {
    Criteria criteria = getSession(true).createCriteria(AfapMaindict.class);
    if (map.get("item") != null) {
      criteria.add(Restrictions.eq("item", (String)map.get("item")));
    }
    if (map.get("itemename") != null) {
      criteria.add(Restrictions.like("itemename", (String)map.get("itemename"), MatchMode.ANYWHERE));
    }
    if (map.get("itemcname") != null) {
      criteria.add(Restrictions.like("itemcname", (String)map.get("itemcname"), MatchMode.ANYWHERE));
    }
    criteria.addOrder(Order.asc("item"));
    page.setAllRecords(Integer.valueOf(criteria.list().size()));
    criteria.setFirstResult(page.getFirstRecord().intValue());
    criteria.setMaxResults(page.getPerPageRecords().intValue());
    
    return criteria.list();
  }
}

package gnnt.MEBS.base.dao.hibernate;

import gnnt.MEBS.base.query.hibernate.CacheSet;
import gnnt.MEBS.base.query.hibernate.OrderField;
import gnnt.MEBS.base.query.hibernate.PageInfo;
import gnnt.MEBS.base.util.StringUtil;
import gnnt.MEBS.base.util.ThreadStore;
import java.sql.SQLException;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.CacheMode;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.ScrollMode;
import org.hibernate.ScrollableResults;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

public class DaoHelper
  extends HibernateDaoSupport
{
  private final transient Log logger = LogFactory.getLog(DaoHelper.class);
  
  @Resource(name="sessionFactory")
  public void setSuperSessionFactory(SessionFactory sessionFactory)
  {
    super.setSessionFactory(sessionFactory);
  }
  
  public List queryByHQL(String hql, String[] names, Object[] values, PageInfo pageInfo, CacheSet cacheSet)
  {
    if (values != null) {
      for (int i = 0; i < values.length; i++)
      {
        Object obj = values[i];
        (obj instanceof Date);
        

        this.logger.debug("param[" + i + "]:" + values[i] + "   " + values[i].getClass().getName());
      }
    }
    this.logger.debug("Query Start!");
    int totalRecords = 0;
    if ((pageInfo != null) && (pageInfo.getTotalRecords() != -1))
    {
      int index = StringUtil.ignoreIndexOf(hql, "from");
      if (index < 0) {
        index = 0;
      }
      String hqlCount = "select count(*) " + hql.substring(index, hql.length());
      totalRecords = Integer.valueOf(totalRow(hqlCount, names, values, cacheSet)).intValue();
    }
    int startCount = 0;
    if (pageInfo != null)
    {
      startCount = pageInfo.getStartCount();
      

      String order = " order by ";
      if ((pageInfo.getOrderFields() != null) && (pageInfo.getOrderString() == null))
      {
        List orderFields = pageInfo.getOrderFields();
        for (int i = 0; i < orderFields.size(); i++)
        {
          OrderField of = (OrderField)orderFields.get(i);
          String fieldName = of.getOrderField();
          boolean isDesc = of.isOrderDesc();
          if (isDesc) {
            order = order + fieldName + " desc";
          } else {
            order = order + fieldName + " asc";
          }
          if (i != orderFields.size() - 1) {
            order = order + ",";
          }
        }
      }
      else if (pageInfo.getOrderString() != null)
      {
        order = order + pageInfo.getOrderString();
      }
      hql = hql + order;
    }
    int pageSize = 100000;
    if ((pageInfo != null) && (pageInfo.getTotalRecords() != -1))
    {
      pageSize = pageInfo.getPageSize();
      int pages;
      int pages;
      if (pageSize > 0)
      {
        int pages;
        if (totalRecords % pageSize == 0)
        {
          pages = totalRecords / pageSize;
        }
        else
        {
          int pages;
          if (totalRecords < pageSize) {
            pages = 1;
          } else {
            pages = totalRecords / pageSize + 1;
          }
        }
      }
      else
      {
        pages = 1;
      }
      pageInfo.setTotalRecords(totalRecords);
      pageInfo.setTotalPages(pages);
    }
    List list = findAllByHQL(startCount, pageSize, hql, names, values, cacheSet);
    return list;
  }
  
  public List findAllByHQL(final int first, final int size, final String hql, final String[] names, final Object[] values, final CacheSet cacheSet)
  {
    if ((ThreadStore.get("exportAll") == null) || (ThreadStore.get("reloadExport") == null)) {
      getHibernateTemplate().executeFind(new HibernateCallback()
      {
        public Object doInHibernate(Session session)
          throws HibernateException, SQLException
        {
          DaoHelper.this.logger.debug("findAll hql:" + hql);
          Query query = session.createQuery(hql);
          if (names != null) {
            for (int i = 0; i < names.length; i++)
            {
              DaoHelper.this.logger.debug("names[i]:" + names[i] + "   values[i]:" + values[i]);
              query.setParameter(names[i], values[i]);
            }
          }
          query.setFirstResult(first);
          query.setMaxResults(size);
          if (cacheSet != null)
          {
            query.setCacheable(cacheSet.getAble());
            query.setCacheRegion(cacheSet.getRegion());
            query.setCacheMode(cacheSet.getMode());
          }
          return query.list();
        }
      });
    }
    List list = new LinkedList();
    Session session = getSession();
    
    Query query = session.createQuery(hql);
    if (names != null) {
      for (int i = 0; i < names.length; i++)
      {
        this.logger.debug("names[i]:" + names[i] + "   values[i]:" + values[i]);
        query.setParameter(names[i], values[i]);
      }
    }
    query.setFirstResult(first);
    query.setMaxResults(size);
    if (cacheSet != null)
    {
      query.setCacheable(cacheSet.getAble());
      query.setCacheRegion(cacheSet.getRegion());
      query.setCacheMode(cacheSet.getMode());
    }
    ScrollableResults cusor = query.setFetchSize(50).setCacheMode(CacheMode.IGNORE).scroll(ScrollMode.FORWARD_ONLY);
    







































    ThreadStore.put("ScrollableResults", cusor);
    ThreadStore.put("hibernateSession", session);
    

    return list;
  }
  
  public int totalRow(final String hql, final String[] names, final Object[] values, final CacheSet cacheSet)
  {
    ((Integer)getHibernateTemplate().execute(
      new HibernateCallback()
      {
        public Object doInHibernate(Session session)
          throws HibernateException, SQLException
        {
          DaoHelper.this.logger.debug("count hql:" + hql);
          Query query = session.createQuery(hql);
          if (names != null) {
            for (int i = 0; i < names.length; i++)
            {
              DaoHelper.this.logger.debug("names[i]:" + names[i] + "   values[i]:" + values[i]);
              query.setParameter(names[i], values[i]);
            }
          }
          if (cacheSet != null)
          {
            query.setCacheable(cacheSet.getAble());
            query.setCacheRegion(cacheSet.getRegion());
            query.setCacheMode(cacheSet.getMode());
          }
          DaoHelper.this.logger.debug("--------------------");
          int count = ((Number)query.uniqueResult()).intValue();
          DaoHelper.this.logger.debug("count:" + count);
          return Integer.valueOf(count);
        }
      })).intValue();
  }
  
  public List querySql(final String sql)
  {
    List list = (List)getHibernateTemplate().execute(new HibernateCallback()
    {
      public Object doInHibernate(Session session)
        throws HibernateException, SQLException
      {
        Query query = session.createSQLQuery(sql);
        return query.list();
      }
    });
    return list;
  }
  
  public int executeProcedure(final String procedureName, final Map<String, Object> map)
  {
    ((Integer)getHibernateTemplate().execute(new HibernateCallback()
    {
      public Object doInHibernate(Session session)
        throws HibernateException, SQLException
      {
        Query query = session.getNamedQuery(procedureName);
        for (String key : map.keySet()) {
          query.setParameter(key, map.get(key));
        }
        int count = ((Number)query.uniqueResult()).intValue();
        return Integer.valueOf(count);
      }
    })).intValue();
  }
}

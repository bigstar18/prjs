package gnnt.MEBS.packaging.dao;

import gnnt.MEBS.base.dao.hibernate.DaoHelper;
import gnnt.MEBS.base.model.Clone;
import gnnt.MEBS.base.query.hibernate.PageInfo;
import gnnt.MEBS.base.query.hibernate.QueryConditions;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.annotation.PostConstruct;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.type.TimestampType;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;

public abstract class BaseDao<T extends Clone>
  extends DaoHelper
{
  private final transient Log logger = LogFactory.getLog(BaseDao.class);
  protected Class<T> type;
  
  public abstract Class getEntityClass();
  
  public void add(T entity)
  {
    this.logger.debug("enter add");
    getHibernateTemplate().save(getEntityClass().getName(), entity);
  }
  
  public void update(T entity)
  {
    this.logger.debug("enter update");
    getHibernateTemplate().update(getEntityClass().getName(), entity);
  }
  
  public void delete(T entity)
  {
    this.logger.debug("enter delete");
    getHibernateTemplate().delete(getEntityClass().getName(), entity);
  }
  
  public T getById(Serializable id)
  {
    this.logger.debug("enter getById");
    return (Clone)getHibernateTemplate().get(getEntityClass().getName(), id);
  }
  
  public T get(T obj)
  {
    this.logger.debug("enter get");
    return (Clone)getHibernateTemplate().get(getEntityClass().getName(), obj);
  }
  
  public int getTotalCount(QueryConditions conditions)
  {
    this.logger.debug("enter getTotalCount");
    String hql = "select count(*) from " + getEntityClass().getName() + " where 1=1 ";
    Object[] values = (Object[])null;
    String[] names = (String[])null;
    if ((conditions != null) && (conditions.getFieldsSqlClause() != null))
    {
      values = conditions.getValueArray();
      names = conditions.getNameArray();
      hql = hql + " and " + conditions.getFieldsSqlClause();
    }
    return totalRow(hql, names, values, null);
  }
  
  public List<T> getList(QueryConditions conditions, PageInfo pageInfo)
  {
    this.logger.debug("enter getList");
    String hql = "from " + getEntityClass().getName() + " as primary where 1=1 ";
    Object[] values = (Object[])null;
    String[] names = (String[])null;
    this.logger.debug("hql:" + hql);
    if ((conditions != null) && (conditions.getFieldsSqlClause() != null) && (!"".equals(conditions.getFieldsSqlClause())))
    {
      this.logger.debug("conditions size:" + conditions.getConditionList().size());
      
      this.logger.debug("conditions fieldsSqlClause:" + conditions.getFieldsSqlClause());
      values = conditions.getValueArray();
      names = conditions.getNameArray();
      hql = hql + " and " + conditions.getFieldsSqlClause();
    }
    List<T> list = queryByHQL(hql, names, values, pageInfo, null);
    this.logger.debug("list:" + list);
    this.logger.debug("hql:" + hql);
    return list;
  }
  
  public List<T> getListByHql(QueryConditions conditions, PageInfo pageInfo, String hql)
  {
    this.logger.debug("enter getList");
    Object[] values = (Object[])null;
    String[] names = (String[])null;
    this.logger.debug("hql:" + hql);
    if ((conditions != null) && (conditions.getFieldsSqlClause() != null))
    {
      values = conditions.getValueArray();
      names = conditions.getNameArray();
      hql = hql + " and " + conditions.getFieldsSqlClause();
    }
    List<T> list = queryByHQL(hql, names, values, pageInfo, null);
    this.logger.debug("hql:" + hql);
    return list;
  }
  
  @PostConstruct
  public void init()
  {
    this.type = getEntityClass();
  }
  
  public void flush()
  {
    getHibernateTemplate().flush();
  }
  
  public Date getSysDate()
  {
    (Date)getHibernateTemplate().execute(
      new HibernateCallback()
      {
        public Object doInHibernate(Session session)
          throws SQLException, HibernateException
        {
          Date sysdate = null;
          SQLQuery query = session.createSQLQuery("select sysdate  CRTDATE from dual");
          query.addScalar("CRTDATE", new TimestampType());
          List children = query.list();
          sysdate = (Date)children.iterator().next();
          BaseDao.this.releaseSession(session);
          return sysdate;
        }
      });
  }
}

package cn.com.agree.eteller.afa.dao.hibernate;

import cn.com.agree.eteller.afa.dao.IAfaBranchcodeDao;
import cn.com.agree.eteller.afa.persistence.AfaBranchcode;
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

public class AfaBranchcodeDao
  extends HibernateDaoSupport
  implements IAfaBranchcodeDao
{
  public boolean addAfabranchcode(final AfaBranchcode ca)
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
  
  public boolean deleteAfabranchcode(final AfaBranchcode ca)
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
  
  public boolean updateAfabranchcode(final AfaBranchcode ca_old, final AfaBranchcode ca)
  {
    getHibernateTemplate().execute(new HibernateCallback()
    {
      public Object doInHibernate(Session session)
        throws HibernateException, SQLException
      {
        session.delete(ca_old);
        session.save(ca);
        return null;
      }
    });
    return true;
  }
  
  public AfaBranchcode[] getAfabranchcodeBymap(final Map map)
  {
    final List list = new ArrayList();
    getHibernateTemplate().execute(new HibernateCallback()
    {
      public Object doInHibernate(Session session)
        throws HibernateException, SQLException
      {
        Criteria criteria = session
          .createCriteria(AfaBranchcode.class);
        if ((map.get("sysid") != null) && (map.get("sysid").toString().length() > 0)) {
          criteria.add(Expression.eq("comp_id.sysid", map.get("sysid")));
        }
        if ((map.get("unitno") != null) && (map.get("unitno").toString().length() > 0)) {
          criteria.add(Expression.eq("comp_id.unitno", map.get("unitno")));
        }
        if ((map.get("zoneno") != null) && (map.get("zoneno").toString().length() > 0)) {
          criteria.add(Expression.eq("comp_id.zoneno", map.get("zoneno")));
        }
        if ((map.get("branchno") != null) && (map.get("branchno").toString().length() > 0)) {
          criteria.add(Expression.eq("comp_id.branchno", map.get("branchno")));
        }
        list.addAll(criteria.list());
        return null;
      }
    });
    return (AfaBranchcode[])list.toArray(new AfaBranchcode[0]);
  }
  
  public AfaBranchcode getAfabranchcodeBysql(String sql)
  {
    List list = new ArrayList();
    list = getHibernateTemplate().find(sql);
    return (AfaBranchcode)list.get(0);
  }
  
  public List<AfaBranchcode> getAfaBranchcodeByMap(Map<String, String> map, Pagination page)
  {
    Criteria criteria = getSession(true).createCriteria(AfaBranchcode.class);
    if (map.get("sysid") != null) {
      criteria.add(Restrictions.eq("comp_id.sysid", map.get("sysid")));
    }
    if (map.get("unitno") != null) {
      criteria.add(Restrictions.eq("comp_id.unitno", map.get("unitno")));
    }
    if (map.get("zoneno") != null) {
      criteria.add(Restrictions.eq("comp_id.zoneno", map.get("zoneno")));
    }
    if (map.get("branchno") != null) {
      criteria.add(Restrictions.eq("comp_id.branchno", map.get("branchno")));
    }
    criteria.addOrder(Order.asc("comp_id.sysid"));
    if (page != null)
    {
      page.setAllRecords(Integer.valueOf(criteria.list().size()));
      criteria.setFirstResult(page.getFirstRecord().intValue());
      criteria.setMaxResults(page.getPerPageRecords().intValue());
    }
    return criteria.list();
  }
}

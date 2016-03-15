package cn.com.agree.eteller.afa.dao.hibernate;

import cn.com.agree.eteller.afa.dao.IAfaFeeinfoDao;
import cn.com.agree.eteller.afa.persistence.AfaFeeinfo;
import cn.com.agree.eteller.generic.utils.Pagination;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

public class AfaFeeinfoDao
  extends HibernateDaoSupport
  implements IAfaFeeinfoDao
{
  public AfaFeeinfo[] getAfaFeeinfoByMap(final Map map)
  {
    final List list = new ArrayList();
    getHibernateTemplate().execute(new HibernateCallback()
    {
      public Object doInHibernate(Session session)
        throws HibernateException, SQLException
      {
        Criteria criteria = session.createCriteria(AfaFeeinfo.class);
        if ((map.get("sysid") != null) && 
          (!map.get("sysid").toString().equals("00000"))) {
          criteria.add(Expression.eq("sysid", map.get("sysid")));
        }
        if ((map.get("unitno") != null) && 
          (!map.get("unitno").toString().equals("00000"))) {
          criteria.add(Expression.eq("unitno", map.get("unitno")));
        }
        if ((map.get("subunitno") != null) && 
          (!map.get("subunitno").toString().equals("00000"))) {
          criteria.add(Expression.eq("subunitno", map
            .get("subunitno")));
        }
        list.addAll(criteria.list());
        return null;
      }
    });
    return (AfaFeeinfo[])list.toArray(new AfaFeeinfo[0]);
  }
  
  public boolean addAfaFeeinfo(final AfaFeeinfo ca)
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
  
  public boolean deleteAfaFeeinfo(AfaFeeinfo ca)
  {
    boolean isDeleted = false;
    try
    {
      getHibernateTemplate().delete(ca);
      isDeleted = true;
    }
    catch (Exception ex)
    {
      ex.printStackTrace();
      isDeleted = false;
    }
    return isDeleted;
  }
  
  public boolean updateAfaFeeinfo(final AfaFeeinfo ca)
  {
    boolean isDeleted = false;
    try
    {
      getHibernateTemplate().execute(new HibernateCallback()
      {
        public Object doInHibernate(Session session)
          throws HibernateException, SQLException
        {
          Transaction tx = session.beginTransaction();
          String sql = "update AfA_Feeinfo c set c.feeflag = '" + 
            ca.getFeeflag().toString() + "' , c.acctype='" + 
            ca.getAcctype().toString() + "' , c.amount='" + 
            ca.getAmount().toString() + "'  where" + 
            " c.sysid = '" + ca.getSysid().toString() + 
            "' and c.unitno='" + ca.getUnitno().toString() + 
            "' and c.subunitno='" + 
            ca.getSubunitno().toString() + "'";
          PreparedStatement psmt = session.connection()
            .prepareStatement(sql);
          psmt.execute();
          tx.commit();
          session.close();
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
  
  public AfaFeeinfo[] getAllAfaFeeinfo()
  {
    List ls = new ArrayList();
    try
    {
      ls = getHibernateTemplate().loadAll(AfaFeeinfo.class);
    }
    catch (Exception ex)
    {
      ex.printStackTrace();
    }
    return (AfaFeeinfo[])ls.toArray(new AfaFeeinfo[0]);
  }
  
  public AfaFeeinfo getAfaFeeinfoBysql(String sql)
  {
    List list = new ArrayList();
    list = getHibernateTemplate().find(sql);
    return (AfaFeeinfo)list.get(0);
  }
  
  public List<AfaFeeinfo> getAfaFeeinfoByMap2(Map<String, String> map, Pagination page)
  {
    Criteria criteria = getSession(true).createCriteria(AfaFeeinfo.class);
    if (map.get("sysid") != null) {
      criteria.add(Restrictions.eq("sysid", map.get("sysid")));
    }
    if (map.get("unitno") != null) {
      criteria.add(Restrictions.eq("unitno", map.get("unitno")));
    }
    if (map.get("subunitno") != null) {
      criteria.add(Restrictions.eq("subunitno", map.get("subunitno")));
    }
    if (map.get("feeflag") != null) {
      criteria.add(Restrictions.eq("feeflag", map.get("feeflag")));
    }
    if (map.get("acctype") != null) {
      criteria.add(Restrictions.eq("acctype", map.get("acctype")));
    }
    if (map.get("amount") != null) {
      criteria.add(Restrictions.eq("amount", map.get("amount")));
    }
    if (map.get("note1") != null) {
      criteria.add(Restrictions.eq("note1", map.get("note1")));
    }
    if (map.get("note2") != null) {
      criteria.add(Restrictions.eq("note2", map.get("note2")));
    }
    criteria.addOrder(Order.asc("sysid"));
    criteria.addOrder(Order.asc("unitno"));
    criteria.addOrder(Order.asc("subunitno"));
    if (page != null)
    {
      page.setAllRecords(Integer.valueOf(criteria.list().size()));
      criteria.setFirstResult(page.getFirstRecord().intValue());
      criteria.setMaxResults(page.getPerPageRecords().intValue());
    }
    return criteria.list();
  }
  
  public boolean updateAfaFeeinfo2(AfaFeeinfo oldFeeinfo, AfaFeeinfo newFeeinfo)
    throws Exception
  {
    boolean isSuccess = true;
    try
    {
      getHibernateTemplate().delete(oldFeeinfo);
      getHibernateTemplate().save(newFeeinfo);
    }
    catch (DataAccessException e)
    {
      e.printStackTrace();
      isSuccess = false;
    }
    return isSuccess;
  }
}

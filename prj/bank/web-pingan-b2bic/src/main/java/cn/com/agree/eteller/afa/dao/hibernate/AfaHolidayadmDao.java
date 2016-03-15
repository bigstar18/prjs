package cn.com.agree.eteller.afa.dao.hibernate;

import cn.com.agree.eteller.afa.dao.IAfaHolidayadmDao;
import cn.com.agree.eteller.afa.persistence.AfaHolidayadm;
import java.io.PrintStream;
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
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

public class AfaHolidayadmDao
  extends HibernateDaoSupport
  implements IAfaHolidayadmDao
{
  public boolean addAfaHolidayadm(final AfaHolidayadm newca)
  {
    boolean isAdded = false;
    try
    {
      getHibernateTemplate().execute(new HibernateCallback()
      {
        public Object doInHibernate(Session session)
          throws HibernateException, SQLException
        {
          session.save(newca);
          

          return null;
        }
      });
      isAdded = true;
    }
    catch (Exception ex)
    {
      ex.printStackTrace();
      System.out.println("123456");
      isAdded = false;
    }
    return isAdded;
  }
  
  public boolean deleteAfaHolidayadm(final AfaHolidayadm ca)
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
  
  public AfaHolidayadm[] getAfaHolidayadmBymap(final Map map)
  {
    final List list = new ArrayList();
    getHibernateTemplate().execute(new HibernateCallback()
    {
      public Object doInHibernate(Session session)
        throws HibernateException, SQLException
      {
        Criteria criteria = session.createCriteria(AfaHolidayadm.class);
        if (!map.get("sysid").toString().equals("00000")) {
          criteria.add(Expression.eq("comp_id.sysid", map
            .get("sysid")));
        }
        if (!map.get("unitno").toString().equals("00000")) {
          criteria.add(Expression.eq("comp_id.unitno", map
            .get("unitno")));
        }
        if (!map.get("holiday").toString().equals("00000")) {
          criteria.add(Expression.like("comp_id.holiday", map
            .get("holiday") + 
            "%"));
        }
        criteria.addOrder(Order.asc("comp_id.holiday"));
        list.addAll(criteria.list());
        return null;
      }
    });
    return (AfaHolidayadm[])list.toArray(new AfaHolidayadm[0]);
  }
  
  public AfaHolidayadm[] getAllAfaHolidayadm()
  {
    List ls = new ArrayList();
    try
    {
      ls = getHibernateTemplate().loadAll(AfaHolidayadm.class);
    }
    catch (Exception ex)
    {
      ex.printStackTrace();
    }
    return (AfaHolidayadm[])ls.toArray(new AfaHolidayadm[0]);
  }
  
  public boolean updateAfaHolidayadm(AfaHolidayadm ca)
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
  
  public AfaHolidayadm getAfaHolidayadmBysql(String sql)
  {
    List list = new ArrayList();
    list = getHibernateTemplate().find(sql);
    return (AfaHolidayadm)list.get(0);
  }
  
  public boolean deleteAfaHolidayadm(final Map map)
  {
    boolean isDeleted = false;
    try
    {
      getHibernateTemplate().execute(new HibernateCallback()
      {
        public Object doInHibernate(Session session)
          throws HibernateException, SQLException
        {
          session.delete(map);
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
  
  public boolean updateAfaHolidayadm(final String start_day, final String end_day, final String holidayname, final String sysid, final String unitno, final String note1)
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
          
          String sql = "update AfA_Holidayadm c set c.holidayname = '" + 
            holidayname + 
            "' , c.note1='" + 
            note1 + 
            "'  where" + 
            " c.sysid = '" + 
            sysid + 
            "' and c.unitno='" + 
            unitno + 
            "' and c.holiday>='" + 
            start_day + 
            "' and c.holiday<='" + end_day + "'";
          

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
}

package cn.com.agree.eteller.afa.dao.hibernate;

import cn.com.agree.eteller.afa.dao.IEtellerExcelPubDao;
import cn.com.agree.eteller.afa.persistence.EtellerExcelPub;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

public class EtellerExcelPubDao
  extends HibernateDaoSupport
  implements IEtellerExcelPubDao
{
  public EtellerExcelPub[] getEtellerExcelPubByMap(final Map map)
  {
    final List list = new ArrayList();
    getHibernateTemplate().execute(new HibernateCallback()
    {
      public Object doInHibernate(Session session)
        throws HibernateException, SQLException
      {
        Criteria criteria = session
          .createCriteria(EtellerExcelPub.class);
        if ((map.get("reportcode") != null) && 
          (map.get("reportcode").toString().length() > 0)) {
          criteria.add(Expression.eq("reportcode", map
            .get("reportcode")));
        }
        if ((map.get("optdate") != null) && 
          (map.get("optdate").toString().length() > 0)) {
          criteria.add(Expression.eq("optdate", map.get("optdate")));
        }
        if ((map.get("tradetype") != null) && 
          (map.get("tradetype").toString().length() > 0)) {
          criteria.add(Expression.eq("tradetype", map
            .get("tradetype")));
        }
        if ((map.get("note1") != null) && 
          (map.get("note1").toString().length() > 0)) {
          criteria.add(Expression.eq("note1", map.get("note1")));
        }
        criteria.addOrder(Order.desc("optdate"));
        list.addAll(criteria.list());
        return null;
      }
    });
    return (EtellerExcelPub[])list.toArray(new EtellerExcelPub[0]);
  }
  
  public boolean deleteEtellerExcelPubOne(final EtellerExcelPub ca)
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
  
  public boolean addEtellerExcelPub(final EtellerExcelPub ca)
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
}

package gnnt.MEBS.timebargain.mgr.dao.impl;

import gnnt.MEBS.common.mgr.dao.StandardDao;
import gnnt.MEBS.timebargain.mgr.dao.AheadSettleDao;
import gnnt.MEBS.timebargain.mgr.model.settle.ApplyAheadSettle;
import gnnt.MEBS.timebargain.mgr.model.settle.BillFrozen;
import gnnt.MEBS.timebargain.mgr.model.settle.HoldFrozen;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

@Repository("aheadSettleDao")
public class AheadSettleDaoImpl extends StandardDao
  implements AheadSettleDao
{
  public List<Map<Object, Object>> queryBySql(final String sql)
  {
    List list = (List)getHibernateTemplate().execute(
      new HibernateCallback()
    {
      public Object doInHibernate(Session session) throws HibernateException, SQLException {
        Query query = session.createSQLQuery(sql)
          .setResultTransformer(
          Transformers.ALIAS_TO_ENTITY_MAP);
        return query.list();
      }
    });
    return list;
  }

  public List<?> getCustomerList()
  {
    String sql = "select t.customerid from t_customer t";
    List list = 
      (List)getHibernateTemplate()
      .execute(new HibernateCallback()
    {
      public Object doInHibernate(Session session) throws HibernateException, SQLException {
        Query query = session.createSQLQuery("select t.customerid from t_customer t");
        return query.list();
      }
    });
    return list;
  }

  public void addApplyAheadSettle(ApplyAheadSettle applyAheadSettle)
  {
    getHibernateTemplate().save(applyAheadSettle);
  }

  public void addHoldFrozen(HoldFrozen holdFrozen)
  {
    getHibernateTemplate().save(holdFrozen);
  }

  public ApplyAheadSettle getApplyAheadSettle(ApplyAheadSettle applyAheadSettle)
  {
    return (ApplyAheadSettle)getHibernateTemplate().get(ApplyAheadSettle.class, applyAheadSettle.getApplyId());
  }

  public void updateApplyAheadSettle(ApplyAheadSettle applyAheadSettle)
  {
    getSession().clear();

    getHibernateTemplate().update(applyAheadSettle);
  }

  public void addBillFrozen(BillFrozen billFrozen)
  {
    getHibernateTemplate().save(billFrozen);
  }
}
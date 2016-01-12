package gnnt.MEBS.timebargain.mgr.dao.impl;

import gnnt.MEBS.common.mgr.dao.StandardDao;
import gnnt.MEBS.timebargain.mgr.dao.BillDao;
import gnnt.MEBS.timebargain.mgr.model.settle.BillFrozen;
import gnnt.MEBS.timebargain.mgr.model.settle.GageBill;
import java.io.PrintStream;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

@Repository("billDao")
public class BillDaoImpl extends StandardDao
  implements BillDao
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

  public String addGageBill(GageBill gageBill)
  {
    this.logger.debug("enter add");

    return getHibernateTemplate().save(gageBill).toString();
  }

  public int existenceOfValidGageBill(String firmId, String commodityId)
  {
    final String sql = "select quantity from T_ValidGageBill where firmId = '" + 
      firmId + "' AND commodityId = '" + commodityId + "'";

    List list = 
      (List)getHibernateTemplate()
      .execute(new HibernateCallback()
    {
      public Object doInHibernate(Session session) throws HibernateException, SQLException {
        Query query = session.createSQLQuery(sql)
          .setResultTransformer(
          Transformers.ALIAS_TO_ENTITY_MAP);
        return query.list();
      }
    });
    if ((list == null) || (list.size() == 0)) {
      return -1;
    }
    return Integer.valueOf(String.valueOf(((Map)list.get(0)).get("QUANTITY"))).intValue();
  }

  public void executeUpdateBySql(String sql)
  {
    super.executeUpdateBySql(sql);
  }

  public void addBillFrozen(BillFrozen billFrozen)
  {
    getHibernateTemplate().save(billFrozen);
  }

  public List<?> getFirmList()
  {
    String sql = "select f.firmId from t_firm f";
    List list = 
      (List)getHibernateTemplate()
      .execute(new HibernateCallback()
    {
      public Object doInHibernate(Session session) throws HibernateException, SQLException {
        Query query = session.createSQLQuery("select f.firmId from t_firm f");
        return query.list();
      }
    });
    return list;
  }

  public void deleteBillFrozen(BillFrozen billFrozen)
  {
    getHibernateTemplate().delete(billFrozen);
  }

  public GageBill getGageBill(Long operation)
  {
    return (GageBill)getHibernateTemplate().get(GageBill.class, operation);
  }

  public void deleteGageBill(GageBill gageBill)
  {
    getHibernateTemplate().delete(gageBill);
  }

  public void updateGageBill(GageBill gageBill)
  {
    getHibernateTemplate().update(gageBill);
  }

  public void addHisGageBill(GageBill gageBill) {
    final String sql = "insert into T_E_HisGageBill (select sysdate,ID, FirmID, CommodityID, Quantity, Remark, CreateTime, ModifyTime from T_E_GageBill where id = " + gageBill.getId() + ")";
    System.out.println(sql);
    getHibernateTemplate()
      .execute(new HibernateCallback()
    {
      public Object doInHibernate(Session session) throws HibernateException, SQLException {
        Query query = session.createSQLQuery(sql);
        return Integer.valueOf(query.executeUpdate());
      }
    });
  }
}
package gnnt.MEBS.broke.dao;

import gnnt.MEBS.account.model.MemberInfo;
import gnnt.MEBS.packaging.dao.BaseDao;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

@Repository("brokerageDivertDao")
public class BrokerageDivertDao
  extends BaseDao
{
  public int checkBrokerageDivert(final String brokerageNo, final String memberid)
  {
    
    











      ((Integer)getHibernateTemplate().execute(new HibernateCallback()
      {
        public Object doInHibernate(Session session)
          throws HibernateException, SQLException
        {
          CallableStatement cs = session.connection().prepareCall("{ ?=call FN_M_B_checkBrokerageDivert(?, ?) }");
          cs.setString(2, brokerageNo);
          cs.setString(3, memberid);
          cs.registerOutParameter(1, 2);
          cs.executeUpdate();
          return Integer.valueOf(cs.getInt(1));
        }
      })).intValue();
  }
  
  public int brokerageDivert(final String brokerageNo, final String memberid)
  {
    
    











      ((Integer)getHibernateTemplate().execute(new HibernateCallback()
      {
        public Object doInHibernate(Session session)
          throws HibernateException, SQLException
        {
          CallableStatement cs = session.connection().prepareCall("{ ?=call FN_M_B_BrokerageDivert(?, ?) }");
          cs.setString(2, brokerageNo);
          cs.setString(3, memberid);
          cs.registerOutParameter(1, 2);
          cs.executeUpdate();
          return Integer.valueOf(cs.getInt(1));
        }
      })).intValue();
  }
  
  public Class getEntityClass()
  {
    return new MemberInfo().getClass();
  }
}

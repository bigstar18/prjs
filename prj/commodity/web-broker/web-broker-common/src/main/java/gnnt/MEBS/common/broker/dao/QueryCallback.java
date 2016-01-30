package gnnt.MEBS.common.broker.dao;

import java.sql.SQLException;
import org.hibernate.HibernateException;
import org.hibernate.Query;

public abstract interface QueryCallback
{
  public abstract void doInQuery(Query paramQuery)
    throws HibernateException, SQLException;
}
package gnnt.MEBS.bill.core.reflect;

import gnnt.MEBS.bill.core.po.Clone;
import java.sql.ResultSet;

public abstract interface IResultSetToBean
{
  public abstract Clone resultSetToBean(Clone paramClone, ResultSet paramResultSet);
}

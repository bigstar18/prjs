package gnnt.MEBS.common.core.relect;

import gnnt.MEBS.common.core.po.Clone;
import java.sql.ResultSet;

public abstract interface IResultSetToBean
{
  public abstract Clone resultSetToBean(Clone paramClone, ResultSet paramResultSet);
}

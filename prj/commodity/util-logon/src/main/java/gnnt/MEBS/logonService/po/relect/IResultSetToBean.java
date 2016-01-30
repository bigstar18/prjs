package gnnt.MEBS.logonService.po.relect;

import gnnt.MEBS.logonService.po.Clone;
import java.sql.ResultSet;

public abstract interface IResultSetToBean
{
  public abstract Clone resultSetToBean(Clone paramClone, ResultSet paramResultSet);
}
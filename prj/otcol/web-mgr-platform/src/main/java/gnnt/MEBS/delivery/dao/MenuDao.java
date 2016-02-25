package gnnt.MEBS.delivery.dao;

import gnnt.MEBS.base.copy.CommonRowMapper;
import gnnt.MEBS.base.query.PageInfo;
import gnnt.MEBS.base.query.QueryConditions;
import gnnt.MEBS.delivery.model.Menu;
import java.util.List;

public class MenuDao
  extends DaoHelperImpl
{
  public List getMenuList(QueryConditions paramQueryConditions, PageInfo paramPageInfo)
  {
    String str = "select * from w_Menu where 1=1 ";
    Object[] arrayOfObject = null;
    if ((paramQueryConditions != null) && (paramQueryConditions.getFieldsSqlClause() != null))
    {
      arrayOfObject = paramQueryConditions.getValueArray();
      str = str + " and " + paramQueryConditions.getFieldsSqlClause();
    }
    return queryBySQL(str, arrayOfObject, paramPageInfo, new CommonRowMapper(new Menu()));
  }
}

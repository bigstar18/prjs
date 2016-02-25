package gnnt.MEBS.zcjs.dao.warehouse;

import gnnt.MEBS.base.copy.CommonRowMapper;
import gnnt.MEBS.base.query.PageInfo;
import gnnt.MEBS.base.query.QueryConditions;
import gnnt.MEBS.zcjs.dao.DaoHelperImpl;
import gnnt.MEBS.zcjs.model.WarehouseCommodity;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class CommodityDao
  extends DaoHelperImpl
{
  public final transient Log logger = LogFactory.getLog(CommodityDao.class);
  
  public List getTableList(QueryConditions paramQueryConditions, PageInfo paramPageInfo)
  {
    String str = "select * from w_commodity";
    Object[] arrayOfObject = null;
    if ((paramQueryConditions != null) && (paramQueryConditions.getFieldsSqlClause() != null))
    {
      arrayOfObject = paramQueryConditions.getValueArray();
      str = str + " where " + paramQueryConditions.getFieldsSqlClause();
    }
    this.logger.debug("sql: " + str);
    return queryBySQL(str, arrayOfObject, null);
  }
  
  public List<WarehouseCommodity> getObjectList(QueryConditions paramQueryConditions, PageInfo paramPageInfo)
  {
    String str = "select * from w_commodity";
    Object[] arrayOfObject = null;
    if ((paramQueryConditions != null) && (paramQueryConditions.getFieldsSqlClause() != null))
    {
      arrayOfObject = paramQueryConditions.getValueArray();
      str = str + " where " + paramQueryConditions.getFieldsSqlClause();
    }
    this.logger.debug("sql: " + str);
    return queryBySQL(str, null, null, new CommonRowMapper(new WarehouseCommodity()));
  }
  
  public WarehouseCommodity getObject(String paramString)
  {
    String str = "select * from w_commodity where id=?";
    Object[] arrayOfObject = { paramString };
    int[] arrayOfInt = { 12 };
    List localList = queryBySQL(str, arrayOfObject, arrayOfInt, null, new CommonRowMapper(new WarehouseCommodity()));
    WarehouseCommodity localWarehouseCommodity = null;
    if ((localList.size() > 0) && (localList != null)) {
      localWarehouseCommodity = (WarehouseCommodity)localList.get(0);
    }
    return localWarehouseCommodity;
  }
}

package gnnt.MEBS.zcjs.dao.warehouse;

import gnnt.MEBS.base.copy.CommonRowMapper;
import gnnt.MEBS.base.query.PageInfo;
import gnnt.MEBS.base.query.QueryConditions;
import gnnt.MEBS.zcjs.dao.DaoHelperImpl;
import gnnt.MEBS.zcjs.model.WarehouseCommodityExpansion;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class CommodityExpansionDao
  extends DaoHelperImpl
{
  public final transient Log logger = LogFactory.getLog(CommodityExpansionDao.class);
  
  public List<WarehouseCommodityExpansion> getObjectList(QueryConditions paramQueryConditions, PageInfo paramPageInfo)
  {
    String str = "select * from w_commodityexpansion";
    Object[] arrayOfObject = null;
    if ((paramQueryConditions != null) && (paramQueryConditions.getFieldsSqlClause() != null))
    {
      arrayOfObject = paramQueryConditions.getValueArray();
      str = str + " where " + paramQueryConditions.getFieldsSqlClause();
    }
    this.logger.debug("sql: " + str);
    return queryBySQL(str, arrayOfObject, paramPageInfo, new CommonRowMapper(new WarehouseCommodityExpansion()));
  }
}

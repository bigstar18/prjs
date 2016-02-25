package gnnt.MEBS.zcjs.dao;

import gnnt.MEBS.base.dao.DaoHelper;
import gnnt.MEBS.base.query.PageInfo;
import gnnt.MEBS.base.query.QueryConditions;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class TradeInfoDao
  extends DaoHelper
{
  public final transient Log logger = LogFactory.getLog(TradeInfoDao.class);
  
  public List getTradeInfoList(String paramString, QueryConditions paramQueryConditions, PageInfo paramPageInfo)
  {
    Object[] arrayOfObject = null;
    if ((paramQueryConditions != null) && (paramQueryConditions.getFieldsSqlClause() != null))
    {
      arrayOfObject = paramQueryConditions.getValueArray();
      paramString = paramString + " where " + paramQueryConditions.getFieldsSqlClause();
    }
    this.logger.debug("sql: " + paramString);
    List localList = null;
    try
    {
      localList = queryBySQL(paramString, arrayOfObject, paramPageInfo);
      this.logger.debug("size" + localList.size());
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return localList;
  }
}

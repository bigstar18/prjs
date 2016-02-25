package gnnt.MEBS.member.firm.dao;

import gnnt.MEBS.base.query.PageInfo;
import gnnt.MEBS.base.query.QueryConditions;
import java.util.List;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ErrorLoginLogDao
  extends DaoHelperImpl
{
  private final transient Log logger = LogFactory.getLog(ErrorLoginLogDao.class);
  
  public List<Map<String, Object>> getErrorLoginTableList(QueryConditions paramQueryConditions, PageInfo paramPageInfo)
  {
    String str = "select * from(select traderid,count(*) counts,trunc(loginDate) loginDate from m_errorloginlog group by traderid,trunc(loginDate))";
    Object[] arrayOfObject = null;
    if ((paramQueryConditions != null) && (paramQueryConditions.getFieldsSqlClause() != null))
    {
      arrayOfObject = paramQueryConditions.getValueArray();
      str = str + " where " + paramQueryConditions.getFieldsSqlClause();
    }
    this.logger.debug("---------sql:-------- " + str);
    return queryBySQL(str, arrayOfObject, paramPageInfo);
  }
  
  public void delete(String paramString)
  {
    String str = "delete from M_ErrorLoginLog ";
    if (paramString == null)
    {
      updateBySQL(str);
    }
    else
    {
      Object[] arrayOfObject = { paramString };
      str = str + " where " + "traderID=?";
      updateBySQL(str, arrayOfObject);
    }
  }
  
  public List<Map<String, Object>> getErrorLoginById(String paramString)
  {
    String str = "select * from M_ErrorLoginLog where traderid=?";
    Object[] arrayOfObject = { paramString };
    return queryBySQL(str, arrayOfObject, null);
  }
}

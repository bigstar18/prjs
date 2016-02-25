package gnnt.MEBS.zcjs.dao;

import gnnt.MEBS.base.copy.CommonRowMapper;
import gnnt.MEBS.base.query.PageInfo;
import gnnt.MEBS.base.query.QueryConditions;
import gnnt.MEBS.zcjs.model.FundFlow;
import java.io.PrintStream;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class FundFlowDao
  extends DaoHelperImpl
{
  public final transient Log logger = LogFactory.getLog(FundFlowDao.class);
  
  public List getTableList(QueryConditions paramQueryConditions, PageInfo paramPageInfo, String paramString)
  {
    String str = "select * from (select * from f_h_fundflow t where t.oprcode like '" + paramString + "%')";
    Object[] arrayOfObject = null;
    if ((paramQueryConditions != null) && (paramQueryConditions.getFieldsSqlClause() != null))
    {
      arrayOfObject = paramQueryConditions.getValueArray();
      str = str + " where " + paramQueryConditions.getFieldsSqlClause();
    }
    System.out.println(str + ":++++++++sql1");
    this.logger.debug("sql: " + str);
    return queryBySQL(str, arrayOfObject, paramPageInfo);
  }
  
  public List<FundFlow> getObjectList(QueryConditions paramQueryConditions, PageInfo paramPageInfo, String paramString)
  {
    String str = "select * from (select * from f_h_fundflow t where t.oprcode like '" + paramString + "%')";
    Object[] arrayOfObject = null;
    if ((paramQueryConditions != null) && (paramQueryConditions.getFieldsSqlClause() != null))
    {
      arrayOfObject = paramQueryConditions.getValueArray();
      str = str + " where " + paramQueryConditions.getFieldsSqlClause();
    }
    System.out.println(str + ":++++++++sql2");
    return queryBySQL(str, arrayOfObject, paramPageInfo, new CommonRowMapper(new FundFlow()));
  }
}

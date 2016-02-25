package gnnt.MEBS.delivery.dao;

import gnnt.MEBS.base.copy.CommonRowMapper;
import gnnt.MEBS.base.query.PageInfo;
import gnnt.MEBS.base.query.QueryConditions;
import gnnt.MEBS.delivery.model.LogValue;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;

public class LogDao
  extends DaoHelperImpl
{
  public LogValue getLogById(String paramString)
  {
    String str = " select s.* from S_log s where s.id = ?";
    Object[] arrayOfObject = { paramString };
    return (LogValue)getJdbcTemplate().queryForObject(str, arrayOfObject, new CommonRowMapper(new LogValue()));
  }
  
  public List getLogList(QueryConditions paramQueryConditions, PageInfo paramPageInfo, String paramString)
  {
    String str1 = "";
    if ((paramString != null) && (!"".equals(paramString))) {
      str1 = " and to_char(s.operatime,'yyyy-mm-dd')='" + paramString + "'";
    }
    String str2 = " select s.* from S_log s where 1=1 " + str1;
    Object[] arrayOfObject = null;
    if ((paramQueryConditions != null) && (paramQueryConditions.getFieldsSqlClause() != null))
    {
      arrayOfObject = paramQueryConditions.getValueArray();
      str2 = str2 + " and " + paramQueryConditions.getFieldsSqlClause();
    }
    str2 = str2 + " order by s.operatime ";
    return queryBySQL(str2, arrayOfObject, paramPageInfo, new CommonRowMapper(new LogValue()));
  }
  
  public void addLog(LogValue paramLogValue)
  {
    String str = " insert into S_log(id,type,operator,operatime,module,Content,matchId) values (SEQ_S_LOG.NEXTVAL,?,?,?,?,?,?) ";
    Object[] arrayOfObject = { Integer.valueOf(paramLogValue.getType()), paramLogValue.getOperator(), paramLogValue.getOperatime(), paramLogValue.getModule(), paramLogValue.getContent(), paramLogValue.getMatchId() };
    int[] arrayOfInt = { 2, 12, 93, 12, 12, 12 };
    updateBySQL(str, arrayOfObject, arrayOfInt);
  }
}

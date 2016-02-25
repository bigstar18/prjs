package gnnt.MEBS.delivery.dao;

import gnnt.MEBS.base.copy.CommonRowMapper;
import gnnt.MEBS.base.query.PageInfo;
import gnnt.MEBS.base.query.QueryConditions;
import gnnt.MEBS.delivery.model.OperateLog;
import java.util.List;
import org.apache.commons.logging.Log;
import org.springframework.jdbc.core.JdbcTemplate;

public class OperateLogDao
  extends DaoHelperImpl
{
  public List getOprLogList(QueryConditions paramQueryConditions, PageInfo paramPageInfo)
  {
    String str = "select * from (select t.*,s.name operationName from w_marketoperate t,w_status s  where s.kind='OperateLog' and s.value=t.operation)";
    Object[] arrayOfObject = null;
    if ((paramQueryConditions != null) && (paramQueryConditions.getFieldsSqlClause() != null))
    {
      arrayOfObject = paramQueryConditions.getValueArray();
      str = str + " where " + paramQueryConditions.getFieldsSqlClause();
    }
    this.logger.debug("sql: " + str);
    return queryBySQL(str, arrayOfObject, paramPageInfo);
  }
  
  public void addOprLog(OperateLog paramOperateLog)
  {
    String str = " insert into w_marketoperate(id,userid,billid,operatetime,operation,content,popedom) values(SEQ_W_MARKETOPERATE_ID.NEXTVAL,?,?,?,?,?,?)";
    Object[] arrayOfObject = { paramOperateLog.getUserId(), paramOperateLog.getBillId(), paramOperateLog.getOperatetime(), paramOperateLog.getOperation(), paramOperateLog.getContent(), Long.valueOf(paramOperateLog.getPopedom()) };
    int[] arrayOfInt = { 12, 12, 93, 12, 12, 4 };
    updateBySQL(str, arrayOfObject, arrayOfInt);
  }
  
  public OperateLog getOprLogById(String paramString)
  {
    String str = "select t.id,t.userid,t.billid,t.operatetime,t.operation,t.content,t.popedom,s.name operationName from w_marketoperate t,w_status s  where s.kind='OperateLog' and s.value=t.operation and t.id=?";
    this.logger.debug("sql: " + str);
    Object[] arrayOfObject = { paramString };
    Object localObject = getJdbcTemplate().queryForObject(str, arrayOfObject, new CommonRowMapper(new OperateLog()));
    return (OperateLog)localObject;
  }
  
  public void addLoginLog(String paramString1, String paramString2, String paramString3)
  {
    String str = "insert into T_SYSLOG(ID,userID,action,note,CreateTime) values(SEQ_T_SYSLOG.Nextval,?,?,?,sysdate)";
    Object[] arrayOfObject = { paramString1, paramString2, paramString3 };
    try
    {
      getJdbcTemplate().update(str, arrayOfObject);
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }
}

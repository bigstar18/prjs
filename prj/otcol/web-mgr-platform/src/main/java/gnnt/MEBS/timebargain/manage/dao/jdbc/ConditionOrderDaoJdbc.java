package gnnt.MEBS.timebargain.manage.dao.jdbc;

import gnnt.MEBS.timebargain.manage.dao.ConditionOrderDao;
import gnnt.MEBS.timebargain.manage.util.QueryConditions;
import java.io.PrintStream;
import java.util.List;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.springframework.jdbc.core.JdbcTemplate;

public class ConditionOrderDaoJdbc
  extends BaseDAOJdbc
  implements ConditionOrderDao
{
  public int getConditionOrderCount(QueryConditions paramQueryConditions)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("select count(*) con from ( select a.*,m.name firmName ");
    String str1 = (String)paramQueryConditions.getConditionValue("isQryHis");
    Object[] arrayOfObject = null;
    paramQueryConditions.removeCondition("isQryHis");
    System.out.println("------- count - 0 " + localStringBuffer.toString());
    String str2;
    if ((str1 != null) && (str1.equalsIgnoreCase("true")))
    {
      localStringBuffer.append(" ,case when a.SendStatus=1 and a.Retcode=0 then '委托成功' when a.SendStatus=1 and a.Retcode!=0 then '委托失败' when a.SendStatus=2 then '已撤单' else '已过期' end as SendStatu ");
      str2 = " from t_h_conditionorder";
      localStringBuffer.append(str2);
    }
    else
    {
      localStringBuffer.append(" ,case when a.SendStatus = 0 and (a.ValidDate < trunc(sysdate)) then '已过期' when (a.SendStatus = 0 and (a.ValidDate >= trunc(sysdate))) then '未委托' when a.SendStatus=1 and a.Retcode=0 then '委托成功' when a.SendStatus=1 and a.Retcode!=0 then '委托失败' when a.SendStatus=2 then '已撤单' end as SendStatu");
      str2 = " from t_conditionorder";
      localStringBuffer.append(str2);
      paramQueryConditions.removeCondition("ClearDate", ">=");
      paramQueryConditions.removeCondition("ClearDate", "<=");
    }
    localStringBuffer.append(" a,M_firm m  ");
    localStringBuffer.append("where m.firmID = a.firmID  ");
    localStringBuffer.append(" )  where 1=1 ");
    String str3 = (String)paramQueryConditions.getConditionValue("SendStatus");
    if (str3 != null) {
      paramQueryConditions.removeCondition("SendStatus");
    }
    if ((str3 != null) && (str3.equals("11")) && (!str3.equals(""))) {
      localStringBuffer.append(" and SendStatus=1 and Retcode=0");
    } else if ((str3 != null) && (str3.equals("12")) && (!str3.equals(""))) {
      localStringBuffer.append(" and SendStatus=1 and Retcode!=0");
    } else if ((str3 != null) && (str3.equals("01")) && (!str3.equals(""))) {
      localStringBuffer.append(" and SendStatus=0 and (ValidDate >= trunc(sysdate))");
    } else if ((str3 != null) && (str3.equals("02")) && (!str3.equals(""))) {
      localStringBuffer.append(" and SendStatus=0 and (ValidDate < trunc(sysdate))");
    } else if ((str3 != null) && (str3.equals("2")) && (!str3.equals(""))) {
      localStringBuffer.append(" and SendStatus=2");
    }
    if ((paramQueryConditions != null) && (paramQueryConditions.getFieldsSqlClause() != null))
    {
      arrayOfObject = paramQueryConditions.getValueArray();
      if (paramQueryConditions.getFieldsSqlClause() != null) {
        localStringBuffer.append(" and " + paramQueryConditions.getFieldsSqlClause());
      }
    }
    this.log.debug("sql: " + localStringBuffer.toString());
    List localList = getJdbcTemplate().queryForList(localStringBuffer.toString(), arrayOfObject);
    if ((localList == null) || (localList.size() == 0)) {
      return 0;
    }
    Map localMap = (Map)localList.get(0);
    return Integer.parseInt(localMap.get("con") + "");
  }
  
  public List getConditionOrder(QueryConditions paramQueryConditions, int paramInt1, int paramInt2)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("select a.*,case when a.ConditionType=1 then '现价' when a.ConditionType=2 then '买1' when a.ConditionType=3 then '卖1' end as ctype,m.name firmName ");
    String str1 = (String)paramQueryConditions.getConditionValue("isQryHis");
    Object[] arrayOfObject = null;
    paramQueryConditions.removeCondition("isQryHis");
    String str2;
    if ((str1 != null) && (str1.equalsIgnoreCase("true")))
    {
      localStringBuffer.append(" ,case when a.SendStatus=1 and a.Retcode=0 then '委托成功' when a.SendStatus=1 and a.Retcode!=0 then '委托失败' when a.SendStatus=2 then '已撤单' else '已过期' end as SendStatu ");
      str2 = " from t_h_conditionorder";
      localStringBuffer.append(str2);
    }
    else
    {
      localStringBuffer.append(" ,case when a.SendStatus = 0 and (a.ValidDate < trunc(sysdate)) then '已过期' when (a.SendStatus = 0 and (a.ValidDate >= trunc(sysdate))) then '未委托' when a.SendStatus=1 and a.Retcode=0 then '委托成功' when a.SendStatus=1 and a.Retcode!=0 then '委托失败' when a.SendStatus=2 then '已撤单' end as SendStatu");
      str2 = " from t_conditionorder";
      localStringBuffer.append(str2);
      paramQueryConditions.removeCondition("ClearDate", ">=");
      paramQueryConditions.removeCondition("ClearDate", "<=");
    }
    localStringBuffer.append(" a,M_firm m ");
    String str3 = "";
    if ((paramQueryConditions != null) && (paramQueryConditions.getFieldsSqlClause() != null) && (paramQueryConditions.getConditionValue("orderby", "=") != null))
    {
      str3 = (String)paramQueryConditions.getConditionValue("orderby", "=");
      paramQueryConditions.removeCondition("orderby", "=");
    }
    localStringBuffer.append("where m.firmID = a.firmID ");
    if (str3.equals("")) {
      localStringBuffer.append(" order by a_orderno ");
    }
    String str4 = "select * from (  select rownum r,Q.* from (" + localStringBuffer.toString() + ") Q  where 1=1 ";
    String str5 = (String)paramQueryConditions.getConditionValue("SendStatus");
    if (str5 != null) {
      paramQueryConditions.removeCondition("SendStatus");
    }
    if ((str5 != null) && (str5.equals("11")) && (!str5.equals(""))) {
      str4 = str4 + " and SendStatus=1 and Retcode=0";
    } else if ((str5 != null) && (str5.equals("12")) && (!str5.equals(""))) {
      str4 = str4 + " and SendStatus=1 and Retcode!=0";
    } else if ((str5 != null) && (str5.equals("01")) && (!str5.equals(""))) {
      str4 = str4 + " and SendStatus=0 and (ValidDate >= trunc(sysdate))";
    } else if ((str5 != null) && (str5.equals("02")) && (!str5.equals(""))) {
      str4 = str4 + " and SendStatus=0 and (ValidDate < trunc(sysdate))";
    } else if ((str5 != null) && (str5.equals("2")) && (!str5.equals(""))) {
      str4 = str4 + " and SendStatus=2";
    }
    if ((paramQueryConditions != null) && (paramQueryConditions.getFieldsSqlClause() != null))
    {
      arrayOfObject = paramQueryConditions.getValueArray();
      if (paramQueryConditions.getFieldsSqlClause() != null) {
        str4 = str4 + " and " + paramQueryConditions.getFieldsSqlClause();
      }
    }
    str4 = str4 + ") where r between " + paramInt1 + " and " + paramInt2 + " ";
    System.out.println("==00  " + str4);
    this.log.debug("sql: " + localStringBuffer.toString());
    if (arrayOfObject != null) {
      for (int i = 0; i < arrayOfObject.length; i++) {
        this.log.debug("params[" + i + "]: " + arrayOfObject[i]);
      }
    }
    return getJdbcTemplate().queryForList(str4, arrayOfObject);
  }
}

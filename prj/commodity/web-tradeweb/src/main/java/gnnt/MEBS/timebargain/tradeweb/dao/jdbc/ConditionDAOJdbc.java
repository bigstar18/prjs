package gnnt.MEBS.timebargain.tradeweb.dao.jdbc;

import gnnt.MEBS.timebargain.plugin.condition.model.ConditionOrder;
import gnnt.MEBS.timebargain.tradeweb.dao.ConditionDAO;
import gnnt.MEBS.timebargain.tradeweb.model.Privilege;
import gnnt.MEBS.timebargain.tradeweb.webapp.util.SortCondition;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

public class ConditionDAOJdbc
  extends BaseDAOJdbc
  implements ConditionDAO
{
  private Log log = LogFactory.getLog(ConditionDAOJdbc.class);
  
  public List<?> conditionOrder_query(Privilege paramPrivilege, String paramString1, String paramString2, String paramString3, String paramString4, String paramString5)
  {
    StringBuilder localStringBuilder1 = new StringBuilder();
    String str1 = "_SendStatus_";
    String str2 = " case when SendStatus = 0 and (ValidDate < trunc(sysdate)) then '已过期' when (SendStatus = 0 and (ValidDate >= trunc(sysdate))) then '未委托' when SendStatus=1 and Retcode=0 then '委托成功' when SendStatus=1 and Retcode!=0 then '委托失败' when SendStatus=2 then '已撤单' end as SendStatus, ";
    String str3 = " case when SendStatus=1 and Retcode=0 then '委托成功' when SendStatus=1 and Retcode!=0 then '委托失败' when SendStatus=2 then '已撤单' else '已过期' end as SendStatus, ";
    localStringBuilder1.append("select A_OrderNo,CommodityID,CustomerID, ").append("case when BS_Flag=1 then '买入' when BS_Flag=2 then '卖出' end as BS_Flag, ").append("case when OrderType=1 then '订立' when OrderType=2 then '转让' end as OrderType, ").append("Price,Quantity, ").append("BS_Flag,OrderType, ConditionType,ConditionOperation, ").append("case when ConditionType=1 then '现价' when ConditionType=2 then '买1' when ConditionType=3 then '卖1' end as ConditionType, ").append("case when ConditionOperation=-2 then '≤' when ConditionOperation=-1 then '＜' when ConditionOperation=0 then '＝' ").append("when ConditionOperation=1 then '＞' when ConditionOperation=2 then '≥' end as ConditionOperation, ").append("ConditionPrice,ConditionCommodityID,Retcode,to_char(ValidDate,'yyyy-MM-dd') as ValidDate, ").append(str1).append("to_char(UpdateTime,'yyyy-MM-dd') as UpdateTime, ").append("to_char(SuccessTime,'yyyy-MM-dd') as SuccessTime ").append("from T_ConditionOrder where CustomerID = '").append(paramPrivilege.getFirmId()).append("00' ");
    if ((paramString1 != null) && (!paramString1.equals("0"))) {
      localStringBuilder1.append(" and CommodityID='").append(paramString1).append("'");
    }
    if ((paramString2 != null) && (!paramString2.equals("0"))) {
      localStringBuilder1.append(" and BS_Flag=").append(Short.parseShort(paramString2));
    }
    if ((paramString3 != null) && (!paramString3.equals("0"))) {
      localStringBuilder1.append(" and OrderType=").append(Short.parseShort(paramString3));
    }
    if ((paramString4 != null) && (!paramString4.equals("0"))) {
      localStringBuilder1.append(" and ConditionType=").append(Integer.parseInt(paramString4));
    }
    if ((paramString5 != null) && (paramString5.equals("11"))) {
      localStringBuilder1.append(" and SendStatus=1 and Retcode=0");
    } else if ((paramString5 != null) && (paramString5.equals("12"))) {
      localStringBuilder1.append(" and SendStatus=1 and Retcode!=0");
    } else if ((paramString5 != null) && (paramString5.equals("01"))) {
      localStringBuilder1.append(" and SendStatus=0 and (ValidDate >= trunc(sysdate))");
    } else if ((paramString5 != null) && (paramString5.equals("02"))) {
      localStringBuilder1.append(" and SendStatus=0 and (ValidDate < trunc(sysdate))");
    } else if ((paramString5 != null) && (paramString5.equals("2"))) {
      localStringBuilder1.append(" and SendStatus=2");
    }
    String str4 = localStringBuilder1.toString();
    str4 = str4.replace(str1, str2);
    String str5 = localStringBuilder1.toString();
    str5 = str5.replace("T_ConditionOrder", "T_H_ConditionOrder");
    str5 = str5.replace(str1, str3);
    StringBuilder localStringBuilder2 = new StringBuilder();
    localStringBuilder2.append(str4).append(" union ").append(str5).append(" order by A_OrderNo desc");
    this.log.debug("conditionOrder_query:" + localStringBuilder2.toString());
    return getJdbcTemplate().queryForList(localStringBuilder2.toString());
  }
  
  public List<?> conditionOrder_query(Privilege paramPrivilege, String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6)
  {
    StringBuilder localStringBuilder1 = new StringBuilder();
    String str1 = "_SendStatus_";
    String str2 = " case when SendStatus = 0 and (ValidDate < trunc(sysdate)) then '已过期' when (SendStatus = 0 and (ValidDate >= trunc(sysdate))) then '未委托' when SendStatus=1 and Retcode=0 then '委托成功' when SendStatus=1 and Retcode!=0 then '委托失败' when SendStatus=2 then '已撤单' end as SendStatus, ";
    String str3 = " case when SendStatus=1 and Retcode=0 then '委托成功' when SendStatus=1 and Retcode!=0 then '委托失败' when SendStatus=2 then '已撤单' else '已过期' end as SendStatus, ";
    localStringBuilder1.append("select A_OrderNo,CommodityID,CustomerID, ").append("Price,Quantity, ").append("BS_Flag,OrderType, ConditionType,ConditionOperation, ").append("ConditionPrice,ConditionCommodityID,Retcode,to_char(ValidDate,'yyyy-MM-dd') as ValidDate, ").append(str1).append("UpdateTime, ").append("to_char(SuccessTime,'yyyy-MM-dd') as SuccessTime ").append("from T_ConditionOrder where CustomerID = '").append(paramPrivilege.getFirmId()).append("00' ");
    if ((paramString1 != null) && (!paramString1.equals("0"))) {
      localStringBuilder1.append(" and CommodityID='").append(paramString1).append("'");
    }
    if ((paramString2 != null) && (!paramString2.equals("0"))) {
      localStringBuilder1.append(" and BS_Flag=").append(Short.parseShort(paramString2));
    }
    if ((paramString3 != null) && (!paramString3.equals("0"))) {
      localStringBuilder1.append(" and OrderType=").append(Short.parseShort(paramString3));
    }
    if ((paramString4 != null) && (!paramString4.equals("0"))) {
      localStringBuilder1.append(" and ConditionType=").append(Integer.parseInt(paramString4));
    }
    if ((paramString5 != null) && (paramString5.equals("11"))) {
      localStringBuilder1.append(" and SendStatus=1 and Retcode=0");
    } else if ((paramString5 != null) && (paramString5.equals("12"))) {
      localStringBuilder1.append(" and SendStatus=1 and Retcode!=0");
    } else if ((paramString5 != null) && (paramString5.equals("01"))) {
      localStringBuilder1.append(" and SendStatus=0 and (ValidDate >= trunc(sysdate))");
    } else if ((paramString5 != null) && (paramString5.equals("02"))) {
      localStringBuilder1.append(" and SendStatus=0 and (ValidDate < trunc(sysdate))");
    } else if ((paramString5 != null) && (paramString5.equals("2"))) {
      localStringBuilder1.append(" and SendStatus=2");
    } else if (!"queryAll".equalsIgnoreCase(paramString6)) {
      localStringBuilder1.append(" and UpdateTime > to_timestamp('" + new Timestamp(Long.parseLong(paramString6)) + "','yyyy-mm-dd hh24:mi:ss.ff')");
    }
    String str4 = localStringBuilder1.toString();
    str4 = str4.replace(str1, str2);
    String str5 = localStringBuilder1.toString();
    str5 = str5.replace("T_ConditionOrder", "T_H_ConditionOrder");
    str5 = str5.replace(str1, str3);
    StringBuilder localStringBuilder2 = new StringBuilder();
    localStringBuilder2.append(str4).append(" union ").append(str5).append(" order by A_OrderNo desc");
    this.log.debug("conditionOrder_query:" + localStringBuilder2.toString());
    return getJdbcTemplate().queryForList(localStringBuilder2.toString());
  }
  
  public List<?> conditionOrderPageQuery(Privilege paramPrivilege, SortCondition paramSortCondition, Map paramMap)
  {
    StringBuffer localStringBuffer1 = new StringBuffer();
    StringBuffer localStringBuffer2 = new StringBuffer();
    StringBuffer localStringBuffer3 = new StringBuffer();
    StringBuilder localStringBuilder = new StringBuilder();
    try
    {
      String str1 = paramPrivilege.getFirmId();
      String str2 = paramSortCondition.getReccnt();
      String str3 = paramSortCondition.getSortfLD();
      String str4 = paramSortCondition.getIsdesc() == 0 ? "asc" : "desc";
      int i = Integer.valueOf(paramMap.get("startPagingNum").toString()).intValue();
      int j = Integer.valueOf(paramMap.get("endPagingNum").toString()).intValue();
      String str5 = (String)paramMap.get("UpdateTime");
      if (str5.equals("1"))
      {
        localStringBuffer3.append(" where tt.r> ").append(i).append("  and tt.r<= ").append(j);
        str5 = "queryAll";
      }
      String str6 = "_SendStatus_";
      String str7 = " case when SendStatus = 0 and (ValidDate < trunc(sysdate)) then '已过期' when (SendStatus = 0 and (ValidDate >= trunc(sysdate))) then '未委托' when SendStatus=1 and Retcode=0 then '委托成功' when SendStatus=1 and Retcode!=0 then '委托失败' when SendStatus=2 then '已撤单' end as SendStatus, ";
      String str8 = " case when SendStatus=1 and Retcode=0 then '委托成功' when SendStatus=1 and Retcode!=0 then '委托失败' when SendStatus=2 then '已撤单' else '已过期' end as SendStatus, ";
      String str9 = "select tt.* from (select rownum r,t.* from ( ";
      localStringBuffer1.append("select A_OrderNo,CommodityID,CustomerID, ").append("Price,Quantity, ").append("BS_Flag,OrderType, ConditionType,ConditionOperation, ").append("ConditionPrice,ConditionCommodityID,Retcode,to_char(ValidDate,'yyyy-MM-dd') as ValidDate, ").append(str6).append("UpdateTime, ").append("SuccessTime ").append("from T_ConditionOrder where CustomerID = '").append(paramPrivilege.getFirmId()).append("00' ");
      String str10 = (String)paramMap.get("CommodityID");
      String str11 = (String)paramMap.get("BS_Flag");
      String str12 = (String)paramMap.get("OrderType");
      String str13 = (String)paramMap.get("conditionType");
      String str14 = (String)paramMap.get("SendStatus");
      if ((str10 != null) && (!str10.equals("0")) && (!str10.equals(""))) {
        localStringBuffer1.append(" and CommodityID='").append(str10).append("'");
      }
      if ((str11 != null) && (!str11.equals("0")) && (!str11.equals(""))) {
        localStringBuffer1.append(" and BS_Flag=").append(Short.parseShort(str11));
      }
      if ((str12 != null) && (!str12.equals("0")) && (!str12.equals(""))) {
        localStringBuffer1.append(" and OrderType=").append(Short.parseShort(str12));
      }
      if ((str13 != null) && (!str13.equals("0")) && (!str13.equals(""))) {
        localStringBuffer1.append(" and ConditionType=").append(Integer.parseInt(str13));
      }
      if ((str14 != null) && (str14.equals("11")) && (!str14.equals("")))
      {
        localStringBuffer1.append(" and SendStatus=1 and Retcode=0");
      }
      else if ((str14 != null) && (str14.equals("12")) && (!str14.equals("")))
      {
        localStringBuffer1.append(" and SendStatus=1 and Retcode!=0");
      }
      else if ((str14 != null) && (str14.equals("01")) && (!str14.equals("")))
      {
        localStringBuffer1.append(" and SendStatus=0 and (ValidDate >= trunc(sysdate))");
      }
      else if ((str14 != null) && (str14.equals("02")) && (!str14.equals("")))
      {
        localStringBuffer1.append(" and SendStatus=0 and (ValidDate < trunc(sysdate))");
      }
      else if ((str14 != null) && (str14.equals("2")) && (!str14.equals("")))
      {
        localStringBuffer1.append(" and SendStatus=2");
      }
      else if (!"queryAll".equalsIgnoreCase(str5))
      {
        localStringBuffer1.append(" and ( UpdateTime > to_timestamp('" + new Timestamp(Long.parseLong(str5)) + "','yyyy-mm-dd hh24:mi:ss.ff')");
        localStringBuffer1.append(" or SuccessTime > to_timestamp('" + new Timestamp(Long.parseLong(str5)) + "','yyyy-mm-dd hh24:mi:ss.ff') )");
      }
      String str15 = str9 + localStringBuffer1.toString();
      str15 = str15.replace(str6, str7);
      String str16 = localStringBuffer1.toString();
      str16 = str16.replace("T_ConditionOrder", "T_H_ConditionOrder");
      str16 = str16.replace(str6, str8);
      localStringBuilder.append(str15).append(" union ").append(str16).append(" order by  ").append(str3).append(" " + str4).append(" ) t ) tt ").append(localStringBuffer3);
      this.log.debug("conditionOrder_query:" + localStringBuilder.toString());
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return getJdbcTemplate().queryForList(localStringBuilder.toString());
  }
  
  public List<?> comty_code_query(String paramString)
  {
    String str = null;
    if (paramString != null) {
      str = "select MinQuantityMove,OneMaxHoldQty,MinPriceMove from T_Commodity where CommodityID='" + paramString + "'";
    } else {
      str = "select CommodityID,Name from T_Commodity";
    }
    this.log.info("-->sql:" + str);
    return getJdbcTemplate().queryForList(str);
  }
  
  public int getFirmType(String paramString)
  {
    String str = "select TYPE from M_FIRM where firmID=?";
    this.log.info("sql:" + str);
    return getJdbcTemplate().queryForInt(str, new Object[] { paramString });
  }
  
  public ConditionOrder singl_order_query(String paramString)
  {
    String str = "select * from T_ConditionOrder where A_OrderNo=?";
    RowMapper local1 = new RowMapper()
    {
      public Object mapRow(ResultSet paramAnonymousResultSet, int paramAnonymousInt)
        throws SQLException
      {
        ConditionOrder localConditionOrder = new ConditionOrder();
        localConditionOrder.setOrderNo(Long.valueOf(paramAnonymousResultSet.getLong("A_ORDERNO")));
        localConditionOrder.setConditionCmtyID(paramAnonymousResultSet.getString("conditionCommodityId"));
        localConditionOrder.setFirmID(paramAnonymousResultSet.getString("firmID"));
        ConditionDAOJdbc.this.log.info("-->conditionOrder.getFirmID():" + localConditionOrder.getFirmID());
        return localConditionOrder;
      }
    };
    return (ConditionOrder)getJdbcTemplate().queryForObject(str, new Object[] { paramString }, local1);
  }
  
  public List<?> getTradeDate()
  {
    String str = "select to_char(tradedate,'yyyy-MM-dd') tradeDate from t_systemstatus";
    return getJdbcTemplate().queryForList(str);
  }
  
  public int selectConditionOrdeCount(String paramString1, String paramString2)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    String str = "select count(*) from T_ConditionOrder where (UpdateTime > to_timestamp('" + new Timestamp(Long.parseLong(paramString1)) + "','yyyy-mm-dd hh24:mi:ss.ff') or SuccessTime > to_timestamp('" + new Timestamp(Long.parseLong(paramString1)) + "','yyyy-mm-dd hh24:mi:ss.ff') ) and firmid= '" + paramString2 + "'";
    return getJdbcTemplate().queryForInt(str);
  }
  
  public List<?> selectCondittion()
  {
    String str = "select * from t_conditionorder";
    return getJdbcTemplate().queryForList(str);
  }
  
  public List queryConditionOrderUpdateTime(String paramString)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    String str = "select t.firmid, max(t.updatetime) updatetime, max(t.successtime) successtime from T_ConditionOrder t where (UpdateTime > to_timestamp('" + new Timestamp(Long.parseLong(paramString)) + "','yyyy-mm-dd hh24:mi:ss.ff') or SuccessTime > to_timestamp('" + new Timestamp(Long.parseLong(paramString)) + "','yyyy-mm-dd hh24:mi:ss.ff') ) " + " group by firmid ";
    return getJdbcTemplate().queryForList(str);
  }
  
  public List<?> getRmiConf(int paramInt)
  {
    String str = "select * from T_RmiConf where serviceId = " + paramInt + " and enabled = 'Y'";
    return getJdbcTemplate().queryForList(str);
  }
}

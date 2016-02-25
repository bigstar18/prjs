package gnnt.MEBS.timebargain.manage.dao.jdbc;

import gnnt.MEBS.timebargain.manage.dao.StatQueryDAO;
import gnnt.MEBS.timebargain.manage.model.Commodity;
import gnnt.MEBS.timebargain.manage.model.StatQuery;
import gnnt.MEBS.timebargain.manage.util.QueryConditions;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.springframework.jdbc.core.JdbcTemplate;

public class StatQueryDAOJdbc
  extends BaseDAOJdbc
  implements StatQueryDAO
{
  public List getCustomerFundss(QueryConditions paramQueryConditions, int paramInt1, int paramInt2)
  {
    StringBuffer localStringBuffer1 = new StringBuffer();
    Object[] arrayOfObject = null;
    StringBuffer localStringBuffer2 = new StringBuffer();
    localStringBuffer1.append("select a.*,f.balance,f.frozenfunds,f.lastbalance,m.name FirmName,m.firmCategoryId firmCategoryId,nvl(tt.TradeFee,0) TradeFee,nvl(nvl(balance,0)+nvl((-1)*FrozenFunds,0), 0) nowLeaveBalance,nvl(d.close_PL,0) close_PL from T_Firm a,(select sum(t.TradeFee) TradeFee,t.firmID firmID from T_Trade t group by firmID) tt,F_FirmFunds f,M_firm m,(select firmID, nvl(sum(Close_PL),0) close_PL from T_Trade group by firmID ) d ").append(" where a.FirmID=tt.FirmID(+) and a.FirmID=f.FirmID and m.firmID = a.firmID and a.firmid = d.firmID(+) ");
    String str1 = "";
    if ((paramQueryConditions != null) && (paramQueryConditions.getFieldsSqlClause() != null))
    {
      if (paramQueryConditions.getConditionValue("orderby", "=") != null)
      {
        str1 = (String)paramQueryConditions.getConditionValue("orderby", "=");
        paramQueryConditions.removeCondition("orderby", "=");
      }
      if ((paramQueryConditions != null) && (paramQueryConditions.getFieldsSqlClause() != null))
      {
        arrayOfObject = paramQueryConditions.getValueArray();
        localStringBuffer1.append(" and ").append(paramQueryConditions.getFieldsSqlClause());
      }
    }
    if (str1.equals("")) {
      localStringBuffer1.append(" order by nowLeaveBalance ASC");
    } else {
      localStringBuffer1.append(str1);
    }
    this.log.debug("sql: " + localStringBuffer1.toString());
    if (arrayOfObject != null) {
      for (int i = 0; i < arrayOfObject.length; i++) {
        this.log.debug("params[" + i + "]: " + arrayOfObject[i]);
      }
    }
    String str2 = "select * from (select rownum as r,Q.* from(" + localStringBuffer1.toString() + ")Q )where r between " + paramInt1 + " and " + paramInt2;
    return getJdbcTemplate().queryForList(str2, arrayOfObject);
  }
  
  public int getCustomerFundssCount(QueryConditions paramQueryConditions)
  {
    StringBuffer localStringBuffer1 = new StringBuffer();
    Object[] arrayOfObject = null;
    StringBuffer localStringBuffer2 = new StringBuffer();
    localStringBuffer1.append("select Count(*) con from (select a.*,f.balance,f.frozenfunds,f.lastbalance,m.name FirmName,tt.TradeFee,nvl(nvl(balance,0)+nvl((-1)*FrozenFunds,0), 0) nowLeaveBalance,nvl(d.close_PL,0) close_PL from T_Firm a,(select sum(t.TradeFee) TradeFee,t.firmID firmID from T_Trade t group by firmID) tt,F_FirmFunds f,M_firm m,(select firmID, nvl(sum(Close_PL),0) close_PL from T_Trade group by firmID ) d ").append(" where a.FirmID=tt.FirmID(+) and a.FirmID=f.FirmID and m.firmID = a.firmID and a.firmid = d.firmID(+) ");
    String str = "";
    if ((paramQueryConditions != null) && (paramQueryConditions.getFieldsSqlClause() != null))
    {
      if (paramQueryConditions.getConditionValue("orderby", "=") != null)
      {
        str = (String)paramQueryConditions.getConditionValue("orderby", "=");
        paramQueryConditions.removeCondition("orderby", "=");
      }
      arrayOfObject = paramQueryConditions.getValueArray();
      localStringBuffer1.append(" and ").append(paramQueryConditions.getFieldsSqlClause());
    }
    localStringBuffer1.append(" order by nowLeaveBalance ASC )");
    this.log.debug("sql: " + localStringBuffer1.toString());
    if (arrayOfObject != null) {
      for (int i = 0; i < arrayOfObject.length; i++) {
        this.log.debug("params[" + i + "]: " + arrayOfObject[i]);
      }
    }
    List localList = getJdbcTemplate().queryForList(localStringBuffer1.toString(), arrayOfObject);
    if ((localList == null) || (localList.size() == 0)) {
      return 0;
    }
    Map localMap = (Map)localList.get(0);
    return Integer.parseInt(localMap.get("con") + "");
  }
  
  public List getCustomerFundsTable(StatQuery paramStatQuery)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("select a.lastbalance,a.balance,").append("nvl((select sum(amount) from f_fundflow where firmid=b.firmid and oprcode in ('101','103')),0) inAmount,").append("nvl((select sum(amount) from f_fundflow where firmid=b.firmid and oprcode in ('102','104')),0) outAmount,").append("nvl((select sum(close_pl) from t_trade where firmid=b.firmid and close_pl is not null),0) close_pl,").append("nvl((select sum(tradefee) from t_trade where firmid=b.firmid),0) tradefee,").append("b.ClearMargin,b.clearfl,b.runtimemargin,b.runtimefl,b.ClearAssure,b.MaxOverdraft,b.RuntimeSettleMargin,").append("nvl((select sum(frozenfunds - unfrozenfunds) from t_orders where firmid = b.firmid),0) orderFrozen,").append("(a.frozenfunds-nvl(c.frozenfunds,0)) otherFrozen,(a.balance - a.frozenfunds) usefulFund,b.virtualfunds,nvl((select sum(floatingloss) from t_Firmholdsum where firmid = b.firmid),0) PL ").append("from F_FIRMFUNDS a,T_Firm b, (select firmid,frozenfunds from f_Frozenfunds where moduleid='2' and firmID=?) c ").append("where a.firmid = b.firmid and a.firmid = c.firmid(+) and b.firmID = ? ");
    Object[] arrayOfObject = { paramStatQuery.getFirmID(), paramStatQuery.getFirmID() };
    this.log.debug("sql: " + localStringBuffer.toString());
    if (arrayOfObject != null) {
      for (int i = 0; i < arrayOfObject.length; i++) {
        this.log.debug("params[" + i + "]: " + arrayOfObject[i]);
      }
    }
    List localList = getJdbcTemplate().queryForList(localStringBuffer.toString(), arrayOfObject);
    return localList;
  }
  
  public int getOrderCount(QueryConditions paramQueryConditions)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("select count(*) con from ( select a.*,m.name FirmName ,m.firmcategoryId firmcategoryId from ");
    String str1 = (String)paramQueryConditions.getConditionValue("isQryHis");
    String str2 = (String)paramQueryConditions.getConditionValue("isOne");
    String str3 = (String)paramQueryConditions.getConditionValue("isConsigner");
    Object[] arrayOfObject = null;
    paramQueryConditions.removeCondition("isQryHis");
    paramQueryConditions.removeCondition("isOne");
    paramQueryConditions.removeCondition("isConsigner");
    String str4;
    if ((str1 != null) && (str1.equalsIgnoreCase("true")))
    {
      str4 = "(select t.*, nvl(fb.brokerid, '-') brokerid from T_H_Orders t left join m_b_firmandbroker fb on fb.firmid = t.firmid)a";
      localStringBuffer.append(str4);
    }
    else
    {
      str4 = "(select t.*, nvl(fb.brokerid, '-') brokerid from T_Orders t left join m_b_firmandbroker fb on fb.firmid = t.firmid)a";
      localStringBuffer.append(str4);
      paramQueryConditions.removeCondition("ClearDate", ">=");
      paramQueryConditions.removeCondition("ClearDate", "<=");
    }
    localStringBuffer.append(" ,M_firm m  ");
    localStringBuffer.append("where m.firmID = a.firmID  ");
    if ((str3 != null) && (str3.equalsIgnoreCase("true"))) {
      localStringBuffer.append(" and a.ConsignerID is not null ");
    }
    localStringBuffer.append(" )  where 1=1 ");
    if ((paramQueryConditions != null) && (paramQueryConditions.getFieldsSqlClause() != null))
    {
      localObject = (String)paramQueryConditions.getConditionValue("WithdrawType");
      if ((localObject != null) && (((String)localObject).equals("2"))) {
        paramQueryConditions.removeCondition("WithdrawType");
      }
      arrayOfObject = paramQueryConditions.getValueArray();
      if (paramQueryConditions.getFieldsSqlClause() != null) {
        localStringBuffer.append("and " + paramQueryConditions.getFieldsSqlClause());
      }
      if ((localObject != null) && (((String)localObject).equals("2"))) {
        localStringBuffer.append(" and (Withdrawerid in (select consignerid from t_consigner)) ");
      }
    }
    if ((str2 != null) && (str2.equalsIgnoreCase("true"))) {}
    this.log.debug("sql: " + localStringBuffer.toString());
    if (arrayOfObject != null) {}
    Object localObject = getJdbcTemplate().queryForList(localStringBuffer.toString(), arrayOfObject);
    if ((localObject == null) || (((List)localObject).size() == 0)) {
      return 0;
    }
    Map localMap = (Map)((List)localObject).get(0);
    return Integer.parseInt(localMap.get("con") + "");
  }
  
  public List getOrders(QueryConditions paramQueryConditions, int paramInt1, int paramInt2)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("select a.*,m.name FirmName ,m.firmcategoryId firmcategoryId from ");
    String str1 = (String)paramQueryConditions.getConditionValue("isQryHis");
    String str2 = (String)paramQueryConditions.getConditionValue("isOne");
    String str3 = (String)paramQueryConditions.getConditionValue("isConsigner");
    Object[] arrayOfObject = null;
    paramQueryConditions.removeCondition("isQryHis");
    paramQueryConditions.removeCondition("isOne");
    paramQueryConditions.removeCondition("isConsigner");
    String str4;
    if ((str1 != null) && (str1.equalsIgnoreCase("true")))
    {
      str4 = "(select t.*, nvl(fb.brokerid, '-') brokerid from T_H_Orders t left join m_b_firmandbroker fb on fb.firmid = t.firmid)a";
      localStringBuffer.append(str4);
    }
    else
    {
      str4 = "(select t.*, nvl(fb.brokerid, '-') brokerid from T_Orders t left join m_b_firmandbroker fb on fb.firmid = t.firmid)a";
      localStringBuffer.append(str4);
      paramQueryConditions.removeCondition("ClearDate", ">=");
      paramQueryConditions.removeCondition("ClearDate", "<=");
    }
    localStringBuffer.append(" ,M_firm m ");
    String str5 = "";
    if ((paramQueryConditions != null) && (paramQueryConditions.getFieldsSqlClause() != null) && (paramQueryConditions.getConditionValue("orderby", "=") != null))
    {
      str5 = (String)paramQueryConditions.getConditionValue("orderby", "=");
      paramQueryConditions.removeCondition("orderby", "=");
    }
    localStringBuffer.append("where m.firmID = a.firmID ");
    if ((str3 != null) && (str3.equalsIgnoreCase("true"))) {
      localStringBuffer.append(" and a.ConsignerID is not null ");
    }
    if (str5.equals(""))
    {
      localStringBuffer.append(" order by a.firmID,a_orderno ");
    }
    else
    {
      if ("ORDER BY status asc".equalsIgnoreCase(str5.trim())) {
        str5 = "ORDER BY a.status asc";
      }
      if ("ORDER BY Status desc".equalsIgnoreCase(str5.trim())) {
        str5 = "ORDER BY a.status desc";
      }
      str5 = str5.replace("FirmID", "a.FirmID");
      localStringBuffer.append(str5);
    }
    if ((str2 != null) && (str2.equalsIgnoreCase("true"))) {}
    String str6 = "select * from (  select rownum r,Q.* from (" + localStringBuffer.toString() + ") Q  where 1=1 ";
    if ((str3 != null) && (str3.equalsIgnoreCase("true"))) {
      str6 = str6 + " and ConsignerID is not null ";
    }
    if ((paramQueryConditions != null) && (paramQueryConditions.getFieldsSqlClause() != null))
    {
      String str7 = (String)paramQueryConditions.getConditionValue("WithdrawType");
      if ((str7 != null) && (str7.equals("2"))) {
        paramQueryConditions.removeCondition("WithdrawType");
      }
      arrayOfObject = paramQueryConditions.getValueArray();
      if (paramQueryConditions.getFieldsSqlClause() != null) {
        str6 = str6 + " and " + paramQueryConditions.getFieldsSqlClause();
      }
      if ((str7 != null) && (str7.equals("2")))
      {
        paramQueryConditions.removeCondition("WithdrawType");
        str6 = str6 + " and (Withdrawerid in (select consignerid from t_consigner)) ";
      }
    }
    str6 = str6 + ") where r between " + paramInt1 + " and " + paramInt2 + " ";
    this.log.debug("sql: " + localStringBuffer.toString());
    if (arrayOfObject != null) {
      for (int i = 0; i < arrayOfObject.length; i++) {
        this.log.debug("params[" + i + "]: " + arrayOfObject[i]);
      }
    }
    return getJdbcTemplate().queryForList(str6, arrayOfObject);
  }
  
  public int getTradesCount(QueryConditions paramQueryConditions)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append(" select count(*) con from ( select a.*,m.name FirmName ,m.firmcategoryId firmcategoryId from ");
    String str1 = (String)paramQueryConditions.getConditionValue("isQryHis");
    Object[] arrayOfObject = null;
    paramQueryConditions.removeCondition("isQryHis");
    String str2;
    if ((str1 != null) && (str1.equalsIgnoreCase("true")))
    {
      str2 = "(select t.*, nvl(fb.brokerid, '-') brokerid from T_H_Trade t left join m_b_firmandbroker fb on fb.firmid = t.firmid)a";
      localStringBuffer.append(str2);
      localStringBuffer.append(" ,M_firm m where m.firmID = a.firmID ");
    }
    else
    {
      str2 = "(select t.*, nvl(fb.brokerid, '-') brokerid from T_Trade t left join m_b_firmandbroker fb on fb.firmid = t.firmid)a";
      localStringBuffer.append(str2);
      paramQueryConditions.removeCondition("a.ClearDate", ">=");
      paramQueryConditions.removeCondition("a.ClearDate", "<=");
      localStringBuffer.append(" ,M_firm m where m.firmID = a.firmID  ");
    }
    if ((paramQueryConditions != null) && (paramQueryConditions.getFieldsSqlClause() != null))
    {
      arrayOfObject = paramQueryConditions.getValueArray();
      localStringBuffer.append(" and ").append(paramQueryConditions.getFieldsSqlClause());
    }
    localStringBuffer.append(" ) ");
    this.log.debug("sql: " + localStringBuffer.toString());
    if (arrayOfObject != null) {
      for (int i = 0; i < arrayOfObject.length; i++) {
        this.log.debug("params[" + i + "]: " + arrayOfObject[i]);
      }
    }
    List localList = getJdbcTemplate().queryForList(localStringBuffer.toString(), arrayOfObject);
    if ((localList == null) || (localList.size() == 0)) {
      return 0;
    }
    Map localMap = (Map)localList.get(0);
    return Integer.parseInt(localMap.get("con") + "");
  }
  
  public List getOrderSums(QueryConditions paramQueryConditions)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("select a.*, m.name FirmName, m.firmcategoryId firmcategoryId from ");
    String str1 = (String)paramQueryConditions.getConditionValue("isQryHis");
    String str2 = (String)paramQueryConditions.getConditionValue("isOne");
    String str3 = (String)paramQueryConditions.getConditionValue("isConsigner");
    Object[] arrayOfObject = null;
    paramQueryConditions.removeCondition("isQryHis");
    paramQueryConditions.removeCondition("isOne");
    paramQueryConditions.removeCondition("isConsigner");
    String str4;
    if ((str1 != null) && (str1.equalsIgnoreCase("true")))
    {
      str4 = "(select t.*, nvl(fb.brokerid, '-') brokerid from T_H_Orders t left join m_b_firmandbroker fb on fb.firmid = t.firmid)a";
      localStringBuffer.append(str4);
    }
    else
    {
      str4 = "(select t.*, nvl(fb.brokerid, '-') brokerid from T_Orders t left join m_b_firmandbroker fb on fb.firmid = t.firmid)a";
      localStringBuffer.append(str4);
      paramQueryConditions.removeCondition("ClearDate", ">=");
      paramQueryConditions.removeCondition("ClearDate", "<=");
    }
    localStringBuffer.append(" ,M_firm m ");
    String str5 = "";
    if ((paramQueryConditions != null) && (paramQueryConditions.getFieldsSqlClause() != null) && (paramQueryConditions.getConditionValue("orderby", "=") != null))
    {
      str5 = (String)paramQueryConditions.getConditionValue("orderby", "=");
      paramQueryConditions.removeCondition("orderby", "=");
    }
    localStringBuffer.append("where m.firmID = a.firmID ");
    if ((str3 != null) && (str3.equalsIgnoreCase("true"))) {
      localStringBuffer.append(" and a.ConsignerID is not null ");
    }
    if (str5.equals(""))
    {
      localStringBuffer.append(" order by a.firmID,a_orderno ");
    }
    else
    {
      if ("ORDER BY status asc".equalsIgnoreCase(str5.trim())) {
        str5 = "ORDER BY a.status asc";
      }
      if ("ORDER BY Status desc".equalsIgnoreCase(str5.trim())) {
        str5 = "ORDER BY a.status desc";
      }
      str5 = str5.replace("FirmID", "a.FirmID");
      localStringBuffer.append(str5);
    }
    if ((str2 != null) && (str2.equalsIgnoreCase("true"))) {}
    String str6 = "select * from ( " + localStringBuffer.toString() + ")  where 1=1 ";
    if ((str3 != null) && (str3.equalsIgnoreCase("true"))) {
      str6 = str6 + " and ConsignerID is not null ";
    }
    if ((paramQueryConditions != null) && (paramQueryConditions.getFieldsSqlClause() != null))
    {
      String str7 = (String)paramQueryConditions.getConditionValue("WithdrawType");
      if ((str7 != null) && (str7.equals("2"))) {
        paramQueryConditions.removeCondition("WithdrawType");
      }
      arrayOfObject = paramQueryConditions.getValueArray();
      if (paramQueryConditions.getFieldsSqlClause() != null) {
        str6 = str6 + " and " + paramQueryConditions.getFieldsSqlClause();
      }
      if ((str7 != null) && (str7.equals("2")))
      {
        paramQueryConditions.removeCondition("WithdrawType");
        str6 = str6 + " and (Withdrawerid in (select consignerid from t_consigner)) ";
      }
    }
    str6 = "select nvl(sum(aa.Quantity),0) sumQuantity,nvl(sum(aa.TradeQty),0) sumTradeQty from (" + str6 + ") aa";
    this.log.debug("sql: " + localStringBuffer.toString());
    if (arrayOfObject != null) {
      for (int i = 0; i < arrayOfObject.length; i++) {
        this.log.debug("params[" + i + "]: " + arrayOfObject[i]);
      }
    }
    return getJdbcTemplate().queryForList(str6, arrayOfObject);
  }
  
  public List getOrderSum(QueryConditions paramQueryConditions)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append(" select nvl(sum(Quantity),0) QuantitySum,nvl(sum(TradeQty),0) TradeQtySum from ( select a.*,m.name FirmName ,m.firmcategoryId firmcategoryId from  ");
    String str1 = (String)paramQueryConditions.getConditionValue("isQryHis");
    String str2 = (String)paramQueryConditions.getConditionValue("isOne");
    String str3 = (String)paramQueryConditions.getConditionValue("isConsigner");
    Object[] arrayOfObject = null;
    paramQueryConditions.removeCondition("isQryHis");
    paramQueryConditions.removeCondition("isOne");
    paramQueryConditions.removeCondition("isConsigner");
    String str4;
    if ((str1 != null) && (str1.equalsIgnoreCase("true")))
    {
      str4 = "(select t.*, nvl(fb.brokerid, '-') brokerid from T_H_Orders t left join m_b_firmandbroker fb on fb.firmid = t.firmid)a";
      localStringBuffer.append(str4);
    }
    else
    {
      str4 = "(select t.*, nvl(fb.brokerid, '-') brokerid from T_Orders t left join m_b_firmandbroker fb on fb.firmid = t.firmid)a";
      localStringBuffer.append(str4);
      paramQueryConditions.removeCondition("ClearDate", ">=");
      paramQueryConditions.removeCondition("ClearDate", "<=");
    }
    localStringBuffer.append(" ,M_firm m  ");
    localStringBuffer.append("where m.firmID = a.firmID  ");
    if ((str3 != null) && (str3.equalsIgnoreCase("true"))) {
      localStringBuffer.append(" and a.ConsignerID is not null ");
    }
    localStringBuffer.append(" )  ");
    if ((paramQueryConditions != null) && (paramQueryConditions.getFieldsSqlClause() != null))
    {
      arrayOfObject = paramQueryConditions.getValueArray();
      localStringBuffer.append(" where ").append(paramQueryConditions.getFieldsSqlClause());
    }
    if ((str2 != null) && (str2.equalsIgnoreCase("true"))) {}
    this.log.debug("sql: " + localStringBuffer.toString());
    if (arrayOfObject != null) {}
    return getJdbcTemplate().queryForList(localStringBuffer.toString(), arrayOfObject);
  }
  
  public List getTrades(QueryConditions paramQueryConditions, int paramInt1, int paramInt2)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append(" select * from (  select rownum r,Q.* from (  select a.*,m.name FirmName ,m.firmcategoryId firmcategoryId ,a.oppFirmId oppCustomerID from ");
    String str1 = (String)paramQueryConditions.getConditionValue("isQryHis");
    Object[] arrayOfObject = null;
    paramQueryConditions.removeCondition("isQryHis");
    String str2;
    if ((str1 != null) && (str1.equalsIgnoreCase("true")))
    {
      str2 = "(select t.*, nvl(fb.brokerid, '-') brokerid from T_H_Trade t left join m_b_firmandbroker fb on fb.firmid = t.firmid)a ";
      localStringBuffer.append(str2);
      localStringBuffer.append(" ,M_firm m where m.firmID = a.firmID  ");
    }
    else
    {
      str2 = "(select t.*, nvl(fb.brokerid, '-') brokerid from T_Trade t left join m_b_firmandbroker fb on fb.firmid = t.firmid)a ";
      localStringBuffer.append(str2);
      paramQueryConditions.removeCondition("a.ClearDate", ">=");
      paramQueryConditions.removeCondition("a.ClearDate", "<=");
      localStringBuffer.append(" ,M_firm m where m.firmID = a.firmID   ");
    }
    String str3 = "";
    if ((paramQueryConditions != null) && (paramQueryConditions.getFieldsSqlClause() != null) && (paramQueryConditions.getConditionValue("orderby", "=") != null))
    {
      str3 = (String)paramQueryConditions.getConditionValue("orderby", "=");
      paramQueryConditions.removeCondition("orderby", "=");
    }
    if ((paramQueryConditions != null) && (paramQueryConditions.getFieldsSqlClause() != null))
    {
      arrayOfObject = paramQueryConditions.getValueArray();
      localStringBuffer.append(" and ").append(paramQueryConditions.getFieldsSqlClause());
    }
    if (str3.equals("")) {
      localStringBuffer.append(" order by a_TradeNo desc ");
    } else {
      localStringBuffer.append(str3);
    }
    localStringBuffer.append(" ) Q ) where r between " + paramInt1 + " and " + paramInt2 + " ");
    this.log.debug("sql: " + localStringBuffer.toString());
    if (arrayOfObject != null) {
      for (int i = 0; i < arrayOfObject.length; i++) {
        this.log.debug("params[" + i + "]: " + arrayOfObject[i]);
      }
    }
    return getJdbcTemplate().queryForList(localStringBuffer.toString(), arrayOfObject);
  }
  
  public List getTradesSums(QueryConditions paramQueryConditions)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append(" select nvl(sum(a.Quantity),0) sumQuantity,nvl(sum(a.Close_PL),0) sumClose_PL,nvl(sum(a.TradeFee),0) sumTradeFee,nvl(sum(a.CloseAddedTax),0) sumCloseAddedTax  from ");
    String str1 = (String)paramQueryConditions.getConditionValue("isQryHis");
    Object[] arrayOfObject = null;
    paramQueryConditions.removeCondition("isQryHis");
    String str2;
    if ((str1 != null) && (str1.equalsIgnoreCase("true")))
    {
      str2 = "(select t.*, nvl(fb.brokerid, '-') brokerid from T_H_Trade t left join m_b_firmandbroker fb on fb.firmid = t.firmid)a ";
      localStringBuffer.append(str2);
      localStringBuffer.append(" ,M_firm m where m.firmID = a.firmID  ");
    }
    else
    {
      str2 = "(select t.*, nvl(fb.brokerid, '-') brokerid from T_Trade t left join m_b_firmandbroker fb on fb.firmid = t.firmid)a ";
      localStringBuffer.append(str2);
      paramQueryConditions.removeCondition("a.ClearDate", ">=");
      paramQueryConditions.removeCondition("a.ClearDate", "<=");
      localStringBuffer.append(" ,M_firm m where m.firmID = a.firmID   ");
    }
    String str3 = "";
    if ((paramQueryConditions != null) && (paramQueryConditions.getFieldsSqlClause() != null) && (paramQueryConditions.getConditionValue("orderby", "=") != null))
    {
      str3 = (String)paramQueryConditions.getConditionValue("orderby", "=");
      paramQueryConditions.removeCondition("orderby", "=");
    }
    if ((paramQueryConditions != null) && (paramQueryConditions.getFieldsSqlClause() != null))
    {
      arrayOfObject = paramQueryConditions.getValueArray();
      localStringBuffer.append(" and ").append(paramQueryConditions.getFieldsSqlClause());
    }
    if (str3.equals("")) {
      localStringBuffer.append(" order by a_TradeNo desc ");
    } else {
      localStringBuffer.append(str3);
    }
    this.log.debug("sql: " + localStringBuffer.toString());
    if (arrayOfObject != null) {
      for (int i = 0; i < arrayOfObject.length; i++) {
        this.log.debug("params[" + i + "]: " + arrayOfObject[i]);
      }
    }
    return getJdbcTemplate().queryForList(localStringBuffer.toString(), arrayOfObject);
  }
  
  public List getTradesSum(QueryConditions paramQueryConditions)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append(" select nvl(sum(Quantity),0) QuantitySum,nvl(sum(Close_PL),0) Close_PLSum,nvl(sum(TradeFee),0) TradeFeeSum,nvl(sum(CloseAddedTax),0) CloseAddedTaxSum from ( select a.*,m.name FirmName ,m.firmcategoryId firmcategoryId from ");
    String str1 = (String)paramQueryConditions.getConditionValue("isQryHis");
    Object[] arrayOfObject = null;
    paramQueryConditions.removeCondition("isQryHis");
    String str2;
    if ((str1 != null) && (str1.equalsIgnoreCase("true")))
    {
      str2 = "(select t.*, nvl(fb.brokerid, '-') brokerid from T_H_Trade t left join m_b_firmandbroker fb on fb.firmid = t.firmid)a";
      localStringBuffer.append(str2);
      localStringBuffer.append(" ,M_firm m where m.firmID = a.firmID ");
    }
    else
    {
      str2 = "(select t.*, nvl(fb.brokerid, '-') brokerid from T_Trade t left join m_b_firmandbroker fb on fb.firmid = t.firmid) a ";
      localStringBuffer.append(str2);
      paramQueryConditions.removeCondition("a.ClearDate", ">=");
      paramQueryConditions.removeCondition("a.ClearDate", "<=");
      localStringBuffer.append(" ,M_firm m where m.firmID = a.firmID  ");
    }
    if ((paramQueryConditions != null) && (paramQueryConditions.getFieldsSqlClause() != null))
    {
      arrayOfObject = paramQueryConditions.getValueArray();
      localStringBuffer.append(" and ").append(paramQueryConditions.getFieldsSqlClause());
    }
    localStringBuffer.append(" ) ");
    this.log.debug("sql: " + localStringBuffer.toString());
    if (arrayOfObject != null) {
      for (int i = 0; i < arrayOfObject.length; i++) {
        this.log.debug("params[" + i + "]: " + arrayOfObject[i]);
      }
    }
    List localList = getJdbcTemplate().queryForList(localStringBuffer.toString(), arrayOfObject);
    return localList;
  }
  
  public int getFirmHoldPositionsCount(QueryConditions paramQueryConditions)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("select Count(*) con from (select a.*,m.name FirmName,nvl((a.HoldQty+a.GageQty),0) HoldQtyGageQty, ");
    String str1 = (String)paramQueryConditions.getConditionValue("isQryHis");
    Object[] arrayOfObject = null;
    paramQueryConditions.removeCondition("isQryHis");
    String str2;
    if ((str1 != null) && (str1.equalsIgnoreCase("true")))
    {
      str2 = "a.ClearDate from T_H_FirmHoldSum";
      localStringBuffer.append(str2);
    }
    else
    {
      str2 = "sysdate ClearDate from T_FirmHoldSum";
      localStringBuffer.append(str2);
      paramQueryConditions.removeCondition("a.ClearDate", ">=");
      paramQueryConditions.removeCondition("a.ClearDate", "<=");
    }
    localStringBuffer.append(" a,M_firm m  where m.firmID = a.firmID ");
    if ((paramQueryConditions != null) && (paramQueryConditions.getFieldsSqlClause() != null))
    {
      arrayOfObject = paramQueryConditions.getValueArray();
      localStringBuffer.append(" and ").append(paramQueryConditions.getFieldsSqlClause());
    }
    if ((str1 != null) && (str1.equalsIgnoreCase("true"))) {}
    localStringBuffer.append(" order by a.firmID");
    this.log.debug("sql: " + localStringBuffer.toString());
    if (arrayOfObject != null) {
      for (int i = 0; i < arrayOfObject.length; i++) {
        this.log.debug("params[" + i + "]: " + arrayOfObject[i]);
      }
    }
    localStringBuffer.append(")");
    List localList = getJdbcTemplate().queryForList(localStringBuffer.toString(), arrayOfObject);
    paramQueryConditions.addCondition("isQryHis", "=", str1);
    if ((localList == null) || (localList.size() == 0)) {
      return 0;
    }
    Map localMap = (Map)localList.get(0);
    return Integer.parseInt(localMap.get("con") + "");
  }
  
  public List getFirmHoldPositions(QueryConditions paramQueryConditions, int paramInt1, int paramInt2)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("select a.*,m.name FirmName,m.firmCategoryId firmCategoryId,nvl((a.HoldQty+a.GageQty),0) HoldQtyGageQty ");
    String str1 = (String)paramQueryConditions.getConditionValue("isQryHis");
    Object[] arrayOfObject = null;
    paramQueryConditions.removeCondition("isQryHis");
    String str2;
    if ((str1 != null) && (str1.equalsIgnoreCase("true")))
    {
      str2 = " from T_H_FirmHoldSum";
      localStringBuffer.append(str2);
    }
    else
    {
      str2 = ", sysdate ClearDate from T_FirmHoldSum";
      localStringBuffer.append(str2);
      paramQueryConditions.removeCondition("a.ClearDate", ">=");
      paramQueryConditions.removeCondition("a.ClearDate", "<=");
    }
    localStringBuffer.append(" a,M_firm m  where m.firmID = a.firmID ");
    String str3 = "";
    if ((paramQueryConditions != null) && (paramQueryConditions.getFieldsSqlClause() != null))
    {
      if (paramQueryConditions.getConditionValue("orderby", "=") != null)
      {
        str3 = (String)paramQueryConditions.getConditionValue("orderby", "=");
        paramQueryConditions.removeCondition("orderby", "=");
      }
      if ((paramQueryConditions != null) && (paramQueryConditions.getFieldsSqlClause() != null))
      {
        arrayOfObject = paramQueryConditions.getValueArray();
        localStringBuffer.append(" and ").append(paramQueryConditions.getFieldsSqlClause());
      }
    }
    if (((str1 == null) || (!str1.equalsIgnoreCase("true"))) || (str3.equals(""))) {
      localStringBuffer.append(" order by a.firmID");
    } else {
      localStringBuffer.append(str3);
    }
    this.log.debug("sql: " + localStringBuffer.toString());
    if (arrayOfObject != null) {
      for (int i = 0; i < arrayOfObject.length; i++) {
        this.log.debug("params[" + i + "]: " + arrayOfObject[i]);
      }
    }
    String str4 = "select * from (select rownum as r,Q.* from (" + localStringBuffer.toString() + ") Q )where r between " + paramInt1 + " and " + paramInt2 + " ";
    return getJdbcTemplate().queryForList(str4, arrayOfObject);
  }
  
  public List getFirmHoldPositionsSum(QueryConditions paramQueryConditions)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("select a.GageQty GageQty,a.HoldMargin HoldMargin,a.FloatingLoss FloatingLoss,nvl((a.HoldQty + a.GageQty), 0) HoldQtyGageQty ");
    String str1 = (String)paramQueryConditions.getConditionValue("isQryHis");
    Object[] arrayOfObject = null;
    paramQueryConditions.removeCondition("isQryHis");
    String str2;
    if ((str1 != null) && (str1.equalsIgnoreCase("true")))
    {
      str2 = " from T_H_FirmHoldSum";
      localStringBuffer.append(str2);
    }
    else
    {
      str2 = ", sysdate ClearDate from T_FirmHoldSum";
      localStringBuffer.append(str2);
      paramQueryConditions.removeCondition("a.ClearDate", ">=");
      paramQueryConditions.removeCondition("a.ClearDate", "<=");
    }
    localStringBuffer.append(" a,M_firm m  where m.firmID = a.firmID ");
    String str3 = "";
    if ((paramQueryConditions != null) && (paramQueryConditions.getFieldsSqlClause() != null))
    {
      if (paramQueryConditions.getConditionValue("orderby", "=") != null)
      {
        str3 = (String)paramQueryConditions.getConditionValue("orderby", "=");
        paramQueryConditions.removeCondition("orderby", "=");
      }
      if ((paramQueryConditions != null) && (paramQueryConditions.getFieldsSqlClause() != null))
      {
        arrayOfObject = paramQueryConditions.getValueArray();
        localStringBuffer.append(" and ").append(paramQueryConditions.getFieldsSqlClause());
      }
    }
    if (((str1 == null) || (!str1.equalsIgnoreCase("true"))) || (str3.equals(""))) {
      localStringBuffer.append(" order by a.firmID");
    } else {
      localStringBuffer.append(str3);
    }
    this.log.debug("sql: " + localStringBuffer.toString());
    if (arrayOfObject != null) {
      for (int i = 0; i < arrayOfObject.length; i++) {
        this.log.debug("params[" + i + "]: " + arrayOfObject[i]);
      }
    }
    String str4 = "select nvl(sum(HoldQtyGageQty),0) sumHoldQtyGageQty ,nvl(sum(GageQty),0) sumGageQty,nvl(sum(HoldMargin),0) sumHoldMargin,nvl(sum(FloatingLoss),0) sumFloatingLoss from (" + localStringBuffer.toString() + ")";
    return getJdbcTemplate().queryForList(str4, arrayOfObject);
  }
  
  public int getHoldPositionCount(QueryConditions paramQueryConditions)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("select count(*) con from (select a.*,b.Name,m.name FirmName,'0' didingType, '0' quantity,nvl((a.HoldQty+a.GageQty),0) HoldQtyGageQty ");
    String str1 = (String)paramQueryConditions.getConditionValue("isQryHis");
    Object[] arrayOfObject = null;
    paramQueryConditions.removeCondition("isQryHis");
    String str2;
    if ((str1 != null) && (str1.equalsIgnoreCase("true")))
    {
      str2 = " from T_H_CustomerHoldSum";
      localStringBuffer.append(str2);
    }
    else
    {
      str2 = ", sysdate ClearDate from T_CustomerHoldSum";
      localStringBuffer.append(str2);
      paramQueryConditions.removeCondition("ClearDate", ">=");
      paramQueryConditions.removeCondition("ClearDate", "<=");
    }
    localStringBuffer.append(" a,T_Customer b,M_firm m  where a.firmID = m.firmID and b.CustomerID = a.CustomerID ");
    if ((paramQueryConditions != null) && (paramQueryConditions.getFieldsSqlClause() != null))
    {
      arrayOfObject = paramQueryConditions.getValueArray();
      localStringBuffer.append(" and ").append(paramQueryConditions.getFieldsSqlClause());
    }
    localStringBuffer.append(" )  ");
    if ((str1 != null) && (str1.equalsIgnoreCase("true"))) {}
    this.log.debug("sql: " + localStringBuffer.toString());
    if (arrayOfObject != null) {
      for (int i = 0; i < arrayOfObject.length; i++) {
        this.log.debug("params[" + i + "]: " + arrayOfObject[i]);
      }
    }
    List localList = getJdbcTemplate().queryForList(localStringBuffer.toString(), arrayOfObject);
    paramQueryConditions.addCondition("isQryHis", "=", str1);
    if ((localList == null) || (localList.size() == 0)) {
      return 0;
    }
    Map localMap = (Map)localList.get(0);
    return Integer.parseInt(localMap.get("con") + "");
  }
  
  public List getHoldPositions(QueryConditions paramQueryConditions, int paramInt1, int paramInt2)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("select a.*,b.Name,m.name FirmName,m.firmCategoryId firmCategoryId,'0' didingType, '0' quantity,nvl((a.HoldQty+a.GageQty),0) HoldQtyGageQty ");
    String str1 = (String)paramQueryConditions.getConditionValue("isQryHis");
    Object[] arrayOfObject = null;
    paramQueryConditions.removeCondition("isQryHis");
    String str2;
    if ((str1 != null) && (str1.equalsIgnoreCase("true")))
    {
      str2 = " from T_H_CustomerHoldSum";
      localStringBuffer.append(str2);
    }
    else
    {
      str2 = ", sysdate ClearDate from T_CustomerHoldSum";
      localStringBuffer.append(str2);
      paramQueryConditions.removeCondition("ClearDate", ">=");
      paramQueryConditions.removeCondition("ClearDate", "<=");
    }
    localStringBuffer.append(" a,T_Customer b,M_firm m  where a.firmID = m.firmID and b.CustomerID = a.CustomerID ");
    String str3 = "";
    if ((paramQueryConditions != null) && (paramQueryConditions.getConditionValue("orderby", "=") != null))
    {
      str3 = (String)paramQueryConditions.getConditionValue("orderby", "=");
      paramQueryConditions.removeCondition("orderby", "=");
    }
    if ((paramQueryConditions != null) && (paramQueryConditions.getFieldsSqlClause() != null))
    {
      arrayOfObject = paramQueryConditions.getValueArray();
      localStringBuffer.append(" and ").append(paramQueryConditions.getFieldsSqlClause());
    }
    if (((str1 == null) || (!str1.equalsIgnoreCase("true"))) || (str3.equals(""))) {
      localStringBuffer.append(" order by a.firmID");
    } else {
      localStringBuffer.append(str3);
    }
    String str4 = "select * from (  select rownum r,Q.* from (" + localStringBuffer.toString() + ") Q  where 1=1 ";
    str4 = str4 + ") where r between " + paramInt1 + " and " + paramInt2 + " ";
    this.log.debug("sql: " + localStringBuffer.toString());
    if (arrayOfObject != null) {
      for (int i = 0; i < arrayOfObject.length; i++) {
        this.log.debug("params[" + i + "]: " + arrayOfObject[i]);
      }
    }
    return getJdbcTemplate().queryForList(str4, arrayOfObject);
  }
  
  public int getQuotationCount(QueryConditions paramQueryConditions)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("select count(*) con from (select a.*,case when a.curPrice =0 then 0 else a.spread end as spreadNow from ");
    String str1 = (String)paramQueryConditions.getConditionValue("isQryHis");
    Object[] arrayOfObject = null;
    paramQueryConditions.removeCondition("isQryHis");
    String str2;
    if ((str1 != null) && (str1.equalsIgnoreCase("true")))
    {
      str2 = "T_H_Quotation";
      localStringBuffer.append(str2);
    }
    else
    {
      str2 = "T_Quotation";
      localStringBuffer.append(str2);
      paramQueryConditions.removeCondition("a.ClearDate", ">=");
      paramQueryConditions.removeCondition("a.ClearDate", "<=");
    }
    localStringBuffer.append(" a where 1=1  ");
    if ((paramQueryConditions != null) && (paramQueryConditions.getFieldsSqlClause() != null))
    {
      arrayOfObject = paramQueryConditions.getValueArray();
      localStringBuffer.append(" and ").append(paramQueryConditions.getFieldsSqlClause());
    }
    localStringBuffer.append(" )  ");
    if ((str1 != null) && (str1.equalsIgnoreCase("true"))) {}
    this.log.debug("sql: " + localStringBuffer.toString());
    if (arrayOfObject != null) {
      for (int i = 0; i < arrayOfObject.length; i++) {
        this.log.debug("params[" + i + "]: " + arrayOfObject[i]);
      }
    }
    List localList = getJdbcTemplate().queryForList(localStringBuffer.toString(), arrayOfObject);
    if ((localList == null) || (localList.size() == 0)) {
      return 0;
    }
    Map localMap = (Map)localList.get(0);
    return Integer.parseInt(localMap.get("con") + "");
  }
  
  public List getQuotations(QueryConditions paramQueryConditions, int paramInt1, int paramInt2)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("select a.*,case when a.curPrice =0 then 0 else a.spread end as spreadNow from  ");
    String str1 = (String)paramQueryConditions.getConditionValue("isQryHisHidd");
    Object[] arrayOfObject = null;
    paramQueryConditions.removeCondition("isQryHisHidd");
    paramQueryConditions.removeCondition("isQryHis");
    String str2;
    if ((str1 != null) && (str1.equalsIgnoreCase("true")))
    {
      str2 = "T_H_Quotation";
      localStringBuffer.append(str2);
    }
    else
    {
      str2 = "T_Quotation";
      localStringBuffer.append(str2);
      paramQueryConditions.removeCondition("a.ClearDate", ">=");
      paramQueryConditions.removeCondition("a.ClearDate", "<=");
    }
    localStringBuffer.append(" a where 1=1  ");
    if ((str1 != null) && (str1.equalsIgnoreCase("true"))) {}
    String str3 = "";
    if ((paramQueryConditions != null) && (paramQueryConditions.getFieldsSqlClause() != null) && (paramQueryConditions.getConditionValue("orderby", "=") != null))
    {
      str3 = (String)paramQueryConditions.getConditionValue("orderby", "=");
      paramQueryConditions.removeCondition("orderby", "=");
    }
    if ((paramQueryConditions != null) && (paramQueryConditions.getFieldsSqlClause() != null))
    {
      arrayOfObject = paramQueryConditions.getValueArray();
      localStringBuffer.append(" and ").append(paramQueryConditions.getFieldsSqlClause());
    }
    if (str3.equals("")) {
      localStringBuffer.append(" order by a.price desc");
    } else {
      localStringBuffer.append(str3);
    }
    String str4 = "select * from (  select rownum r,Q.* from (" + localStringBuffer.toString() + ") Q  where 1=1 ";
    str4 = str4 + ") where r between " + paramInt1 + " and " + paramInt2 + " ";
    this.log.debug("sql: " + localStringBuffer.toString());
    if (arrayOfObject != null) {
      for (int i = 0; i < arrayOfObject.length; i++) {
        this.log.debug("params[" + i + "]: " + arrayOfObject[i]);
      }
    }
    return getJdbcTemplate().queryForList(str4, arrayOfObject);
  }
  
  public int getSpecFrozenHoldsCount()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("select count(*)  specCount").append(" from T_SpecFrozenHold a ,T_Orders o where a.A_OrderNo = o.A_OrderNo").append(" order by a.A_OrderNo");
    this.log.debug("sql: " + localStringBuffer.toString());
    List localList = getJdbcTemplate().queryForList(localStringBuffer.toString());
    Map localMap = (Map)localList.get(0);
    String str = String.valueOf(localMap.get("specCount"));
    System.out.println("指定平仓的总数量为：" + str);
    return Integer.parseInt(str);
  }
  
  public List getSpecFrozenHolds(QueryConditions paramQueryConditions, int paramInt1, int paramInt2)
  {
    Object[] arrayOfObject = null;
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("select * from ( ").append("select rownum r, o.ConsignerID,o.commodityid,o.price,a.a_orderno,a.a_holdno,a.frozenqty ").append(" from T_SpecFrozenHold a ,T_Orders o where a.A_OrderNo = o.A_OrderNo");
    String str = "";
    if ((paramQueryConditions != null) && (paramQueryConditions.getFieldsSqlClause() != null) && (paramQueryConditions.getConditionValue("orderby", "=") != null))
    {
      str = (String)paramQueryConditions.getConditionValue("orderby", "=");
      paramQueryConditions.removeCondition("orderby", "=");
    }
    localStringBuffer.append(str).append("  ) where r between " + paramInt1 + " and " + paramInt2 + " ");
    this.log.debug("sql: " + localStringBuffer.toString());
    System.out.println(localStringBuffer.toString());
    return getJdbcTemplate().queryForList(localStringBuffer.toString(), arrayOfObject);
  }
  
  public int getHoldsCount(QueryConditions paramQueryConditions)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("select count(*) sum  ");
    String str1 = (String)paramQueryConditions.getConditionValue("isQryHis");
    Object[] arrayOfObject = null;
    paramQueryConditions.removeCondition("isQryHis");
    String str2;
    if ((str1 != null) && (str1.equalsIgnoreCase("true")))
    {
      str2 = "  from T_H_HoldPosition  ";
      localStringBuffer.append(str2);
    }
    else
    {
      str2 = "   from T_HoldPosition  ";
      localStringBuffer.append(str2);
      paramQueryConditions.removeCondition("a.cleardate", ">=");
      paramQueryConditions.removeCondition("a.cleardate", "<=");
    }
    localStringBuffer.append(" a,M_firm m where m.firmID = a.firmID");
    if ((paramQueryConditions != null) && (paramQueryConditions.getFieldsSqlClause() != null))
    {
      arrayOfObject = paramQueryConditions.getValueArray();
      localStringBuffer.append(" and ").append(paramQueryConditions.getFieldsSqlClause());
    }
    if ((str1 != null) && (str1.equalsIgnoreCase("true"))) {}
    localStringBuffer.append(" order by a.firmID");
    this.log.debug("sql: " + localStringBuffer.toString());
    if (arrayOfObject != null) {
      for (int i = 0; i < arrayOfObject.length; i++) {
        this.log.debug("params[" + i + "]: " + arrayOfObject[i]);
      }
    }
    List localList = getJdbcTemplate().queryForList(localStringBuffer.toString(), arrayOfObject);
    if ((localList == null) || (localList.size() == 0)) {
      return 0;
    }
    Map localMap = (Map)localList.get(0);
    return Integer.parseInt(localMap.get("sum") + "");
  }
  
  public List getHoldPositionsDetail(QueryConditions paramQueryConditions, int paramInt1, int paramInt2)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append(" select * from (  select rownum r,Q.* from (select a.*,m.name firmName,m.firmCategoryId firmCategoryId,(a.GageQty+a.HoldQty) as dh ");
    String str1 = (String)paramQueryConditions.getConditionValue("isQryHis");
    Object[] arrayOfObject = null;
    paramQueryConditions.removeCondition("isQryHis");
    String str2;
    if ((str1 != null) && (str1.equalsIgnoreCase("true")))
    {
      str2 = "  from T_H_HoldPosition  ";
      localStringBuffer.append(str2);
    }
    else
    {
      str2 = "  from T_HoldPosition  ";
      localStringBuffer.append(str2);
      paramQueryConditions.removeCondition("a.cleardate", ">=");
      paramQueryConditions.removeCondition("a.cleardate", "<=");
    }
    localStringBuffer.append(" a,M_firm m where m.firmID = a.firmID");
    String str3 = "";
    if ((paramQueryConditions != null) && (paramQueryConditions.getFieldsSqlClause() != null) && (paramQueryConditions.getConditionValue("orderby", "=") != null))
    {
      str3 = (String)paramQueryConditions.getConditionValue("orderby", "=");
      paramQueryConditions.removeCondition("orderby", "=");
    }
    if ((paramQueryConditions != null) && (paramQueryConditions.getFieldsSqlClause() != null))
    {
      arrayOfObject = paramQueryConditions.getValueArray();
      localStringBuffer.append(" and ").append(paramQueryConditions.getFieldsSqlClause());
    }
    if (str3.equals("")) {
      localStringBuffer.append(" order by a.remainDay asc ,a.a_holdNo asc");
    } else {
      localStringBuffer.append(str3);
    }
    localStringBuffer.append(" ) Q ) where r between " + paramInt1 + " and " + paramInt2 + " ");
    this.log.debug("sql:" + localStringBuffer.toString());
    if (arrayOfObject != null) {
      for (int i = 0; i < arrayOfObject.length; i++) {
        this.log.debug("params[" + i + "]: " + arrayOfObject[i]);
      }
    }
    return getJdbcTemplate().queryForList(localStringBuffer.toString(), arrayOfObject);
  }
  
  public int queryCommodityCount()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("select count(*)  cmdtyCount ").append("from T_COMMODITY a where 1=1 ");
    localStringBuffer.append(" order by a.commodityID");
    this.log.debug("sql: " + localStringBuffer.toString());
    Map localMap = (Map)getJdbcTemplate().queryForList(localStringBuffer.toString()).get(0);
    String str = String.valueOf(localMap.get("cmdtyCount"));
    return Integer.parseInt(str);
  }
  
  public int queryHisCommodityCount()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("select count(*) cmdtyCount ").append("from T_SETTLECOMMODITY a, t_Systemstatus b where 1=1 ").append("and to_char(settleDate,'yyyyMMdd') < to_char(b.tradedate,'yyyyMMdd') ");
    Map localMap = (Map)getJdbcTemplate().queryForList(localStringBuffer.toString()).get(0);
    String str = String.valueOf(localMap.get("cmdtyCount"));
    return Integer.parseInt(str);
  }
  
  public List getCurCommoditys(Commodity paramCommodity, int paramInt1, int paramInt2)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("select * from (").append("select  rownum r,a.name,a.breedID,a.commodityid,a.status,a.marketDate,a.settleDate,decode(a.marginalgr,1,a.marginrate_b*100||'%',2,a.marginrate_b) marginrate_b,decode(a.marginalgr,1,a.marginrate_s*100||'%',2,a.marginrate_s) marginrate_s ").append("from T_COMMODITY a where 1=1 ");
    Object[] arrayOfObject = null;
    ArrayList localArrayList = new ArrayList();
    if (paramCommodity != null) {
      if ((paramCommodity.getYear() != null) && (!"".equals(paramCommodity.getYear())) && (paramCommodity.getMonth() != null) && (!"".equals(paramCommodity.getMonth())))
      {
        localStringBuffer.append("and to_char(settleDate,'yyyyMM')=? ");
        localArrayList.add(paramCommodity.getYear().trim() + paramCommodity.getMonth().trim());
      }
      else if ((paramCommodity.getYear() != null) && (!"".equals(paramCommodity.getYear())))
      {
        localStringBuffer.append("and to_char(settleDate,'yyyy')=? ");
        localArrayList.add(paramCommodity.getYear().trim());
      }
      else if ((paramCommodity.getMonth() != null) && (!"".equals(paramCommodity.getMonth())))
      {
        localStringBuffer.append("and to_char(settleDate,'MM')=? ");
        localArrayList.add(paramCommodity.getMonth().trim());
      }
    }
    localStringBuffer.append(" order by a.commodityID ").append(") where r between " + paramInt1 + " and " + paramInt2 + " ");
    arrayOfObject = localArrayList.toArray();
    this.log.debug("sql: " + localStringBuffer.toString());
    if (arrayOfObject != null)
    {
      for (int i = 0; i < arrayOfObject.length; i++) {
        this.log.debug("params[" + i + "]: " + arrayOfObject[i]);
      }
      return getJdbcTemplate().queryForList(localStringBuffer.toString(), arrayOfObject);
    }
    return getJdbcTemplate().queryForList(localStringBuffer.toString());
  }
  
  public List getHisCommoditys(Commodity paramCommodity, int paramInt1, int paramInt2)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("select * from (").append("select rownum r,a.name,a.breedID,a.commodityid,a.status,a.marketDate,a.settleDate,decode(a.marginalgr,1,a.marginrate_b*100||'%',2,a.marginrate_b) marginrate_b,decode(a.marginalgr,1,a.marginrate_s*100||'%',2,a.marginrate_s) marginrate_s ").append("from T_SETTLECOMMODITY a, t_Systemstatus b where 1=1 ").append("and to_char(settleDate,'yyyyMMdd') < to_char(b.tradedate,'yyyyMMdd') ");
    Object[] arrayOfObject = null;
    ArrayList localArrayList = new ArrayList();
    if (paramCommodity != null) {
      if ((paramCommodity.getYear() != null) && (!"".equals(paramCommodity.getYear())) && (paramCommodity.getMonth() != null) && (!"".equals(paramCommodity.getMonth())))
      {
        localStringBuffer.append("and to_char(settleDate,'yyyyMM')=? ");
        localArrayList.add(paramCommodity.getYear().trim() + paramCommodity.getMonth().trim());
      }
      else if ((paramCommodity.getYear() != null) && (!"".equals(paramCommodity.getYear())))
      {
        localStringBuffer.append("and to_char(settleDate,'yyyy')=? ");
        localArrayList.add(paramCommodity.getYear().trim());
      }
      else if ((paramCommodity.getMonth() != null) && (!"".equals(paramCommodity.getMonth())))
      {
        localStringBuffer.append("and to_char(settleDate,'MM')=? ");
        localArrayList.add(paramCommodity.getMonth().trim());
      }
    }
    localStringBuffer.append(") where r between " + paramInt1 + " and " + paramInt2 + " ");
    arrayOfObject = localArrayList.toArray();
    this.log.debug("sql: " + localStringBuffer.toString());
    if (arrayOfObject != null)
    {
      for (int i = 0; i < arrayOfObject.length; i++) {
        this.log.debug("params[" + i + "]: " + arrayOfObject[i]);
      }
      return getJdbcTemplate().queryForList(localStringBuffer.toString(), arrayOfObject);
    }
    return getJdbcTemplate().queryForList(localStringBuffer.toString());
  }
  
  public String getSysdate1()
  {
    String str = "select to_char(sysdate,'yyyy/MM/dd hh24:mi:ss') from dual";
    return (String)getJdbcTemplate().queryForObject(str, String.class);
  }
  
  public List getBrokerFunds(QueryConditions paramQueryConditions)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    Object[] arrayOfObject = null;
    localStringBuffer.append("select br1.brokerid,br1.name,sum(a.VirtualFunds) VirtualFunds,sum(a.runtimefl) runtimefl,sum(a.clearfl) clearfl,sum(a.runtimemargin) runtimemargin,sum(a.clearmargin) clearmargin,sum(a.runtimeassure) runtimeassure ,sum(a.clearassure) clearassure,sum(a.runtimesettlemargin) runtimesettlemargin,sum(a.clearsettlemargin) clearsettlemargin ,sum(f.balance) balance,sum(f.frozenfunds) frozenfunds,sum(f.lastbalance) lastbalance,sum(nvl(tt.TradeFee,0)) TradeFee, sum(nvl(t_Firmholdsum.PL,0)) PL, sum(nvl(nvl(balance,0)+nvl((-1)*FrozenFunds,0), 0)) nowLeaveBalance,sum(nvl(d.close_PL,0)) close_PL,sum(a.MaxOverdraft) MaxOverdraft  from m_b_broker br1,m_b_firmandbroker br2,T_Firm a,F_FirmFunds f,M_firm m,(select sum(t.TradeFee) TradeFee,t.firmID firmID from T_Trade t group by firmID) tt,(select firmID, nvl(sum(Close_PL),0) close_PL from T_Trade group by firmID ) d  ,(select firmID,nvl(sum(floatingloss),0) PL from t_Firmholdsum group by firmID) t_Firmholdsum ").append(" where br1.brokerid = br2.brokerid and br2.firmid = a.firmid and br2.firmid = f.firmid and br2.firmid = m.firmid and br2.firmid = tt.FirmID(+) and br2.firmid = d.firmID(+) and br2.firmid = t_Firmholdsum.firmID(+) ");
    if ((paramQueryConditions != null) && (paramQueryConditions.getFieldsSqlClause() != null))
    {
      arrayOfObject = paramQueryConditions.getValueArray();
      localStringBuffer.append(" and ").append(paramQueryConditions.getFieldsSqlClause());
    }
    localStringBuffer.append("  group by br1.brokerid,br1.name order by nowLeaveBalance ASC");
    this.log.debug("sql: " + localStringBuffer.toString());
    if (arrayOfObject != null) {
      for (int i = 0; i < arrayOfObject.length; i++) {
        this.log.debug("params[" + i + "]: " + arrayOfObject[i]);
      }
    }
    return getJdbcTemplate().queryForList(localStringBuffer.toString(), arrayOfObject);
  }
  
  public List getBrokerHoldPositions(QueryConditions paramQueryConditions)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("select a.CommodityID,a.BS_Flag,sum(a.HoldQty) HoldQty ,sum(a.HoldFunds) HoldFunds,sum(a.FloatingLoss) FloatingLoss,sum(a.HoldMargin) HoldMargin,sum(a.HoldAssure) HoldAssure,sum(a.gageqty) GageQty,m.BrokerID BrokerID,m.name name,sum(nvl((a.HoldQty+a.GageQty),0)) HoldQtyGageQty, ");
    String str1 = "select  m.BrokerID, a.commodityid,a.BS_Flag,nvl(sum(a.HoldQty), 0) lastholeqty from T_H_FIRMHOLDSUM  a, M_B_FirmAndBroker m where a.firmID = m.firmID";
    String str2 = (String)paramQueryConditions.getConditionValue("isQryHis");
    String str4 = "";
    Object[] arrayOfObject = null;
    paramQueryConditions.removeCondition("isQryHis");
    String str3;
    if ((str2 != null) && (str2.equalsIgnoreCase("true")))
    {
      str3 = "a.ClearDate from T_H_FIRMHOLDSUM  a,";
      str4 = "select a.ClearDate,m.BrokerID,a.commodityid,a.BS_Flag, nvl(sum(a.HoldQty * a.Price),0) hp1,nvl( sum(a.HoldQty),0) hp from T_H_HOLDPOSITION  a,M_B_FirmAndBroker m where a.firmID = m.firmID ";
      localStringBuffer.append(str3);
      localObject = (Date)paramQueryConditions.getConditionValue("a.ClearDate", "=");
      SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
      String str5 = localSimpleDateFormat.format((Date)localObject);
      str1 = str1 + " and a.cleardate=(select max(cleardate) from T_H_FIRMHOLDSUM  where cleardate<to_date('" + str5 + "','yyyy-MM-dd'))";
    }
    else
    {
      str3 = "to_char(sysdate,'yyyy-MM-dd') ClearDate from T_FirmHoldSum a,";
      str4 = "select to_char(sysdate,'yyyy-MM-dd') ClearDate,m.BrokerID,a.commodityid,a.BS_Flag, nvl(sum(a.HoldQty * a.Price),0) hp1,nvl( sum(a.HoldQty),0) hp from T_HoldPosition a,M_B_FirmAndBroker m where a.firmID = m.firmID ";
      localStringBuffer.append(str3);
      paramQueryConditions.removeCondition("a.ClearDate", "=");
      str1 = str1 + " and a.cleardate=(select max(cleardate) from T_H_FIRMHOLDSUM )";
    }
    str1 = str1 + " group by m.BrokerID, a.commodityid, a.BS_Flag";
    localStringBuffer.append(" M_B_Broker m ,M_B_FirmAndBroker t where t.firmID = a.firmID and t.BrokerID=m.BrokerID ");
    int i;
    if ((paramQueryConditions != null) && (paramQueryConditions.getFieldsSqlClause() != null))
    {
      localObject = paramQueryConditions.getValueArray();
      if (localObject != null)
      {
        arrayOfObject = new Object[localObject.length * 2];
        for (i = 0; i < localObject.length; i++) {
          arrayOfObject[i] = localObject[i];
        }
        for (i = 0; i < localObject.length; i++) {
          arrayOfObject[(i + localObject.length)] = localObject[i];
        }
      }
      localStringBuffer.append(" and ").append(paramQueryConditions.getFieldsSqlClause());
      str4 = str4 + " and " + paramQueryConditions.getFieldsSqlClause();
    }
    if ((str2 != null) && (str2.equalsIgnoreCase("true")))
    {
      localStringBuffer.append(" group by m.BrokerID,m.name,a.CommodityID,a.BS_Flag,a.ClearDate order by m.BrokerID");
      str4 = str4 + " group by a.ClearDate,m.BrokerID,a.commodityid,a.BS_Flag";
    }
    else
    {
      localStringBuffer.append(" group by m.BrokerID,m.name,a.CommodityID,a.BS_Flag order by m.BrokerID");
      str4 = str4 + " group by m.BrokerID,a.commodityid,a.BS_Flag";
    }
    Object localObject = " select z.*,(case when z1.hp>0 then round(z1.hp1 / z1.hp,2) else 0 end) hp,z2.lastholeqty,(z.HoldQty-z2.lastholeqty) holdce from (" + localStringBuffer.toString() + ") z,(" + str4 + ") z1,(" + str1 + ") z2 " + "where z.BrokerID=z1.BrokerID(+) and z.commodityid=z1.CommodityID(+) and z.BS_Flag=z1.BS_Flag(+) and z.ClearDate=z1.ClearDate(+)" + "  and  z.BrokerID = z2.BrokerID(+) and z.commodityid = z2.CommodityID(+)  and z.BS_Flag = z2.BS_Flag(+) ";
    this.log.debug("sql: " + localStringBuffer.toString());
    if (arrayOfObject != null) {
      for (i = 0; i < arrayOfObject.length; i++) {
        this.log.debug("params[" + i + "]: " + arrayOfObject[i]);
      }
    }
    System.out.println((String)localObject);
    return getJdbcTemplate().queryForList((String)localObject, arrayOfObject);
  }
  
  public List getFirmHoldPositions(QueryConditions paramQueryConditions)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("select a.*,m.name FirmName,nvl((a.HoldQty+a.GageQty),0) HoldQtyGageQty, ");
    String str1 = (String)paramQueryConditions.getConditionValue("isQryHis");
    Object[] arrayOfObject = null;
    paramQueryConditions.removeCondition("isQryHis");
    String str2;
    if ((str1 != null) && (str1.equalsIgnoreCase("true")))
    {
      str2 = "a.ClearDate from T_H_FIRMHOLDSUM ";
      localStringBuffer.append(str2);
    }
    else
    {
      str2 = "sysdate ClearDate from T_FirmHoldSum";
      localStringBuffer.append(str2);
      paramQueryConditions.removeCondition("a.ClearDate", ">=");
      paramQueryConditions.removeCondition("a.ClearDate", "<=");
    }
    localStringBuffer.append(" a,M_firm m  where m.firmID = a.firmID ");
    if ((paramQueryConditions != null) && (paramQueryConditions.getFieldsSqlClause() != null))
    {
      arrayOfObject = paramQueryConditions.getValueArray();
      localStringBuffer.append(" and ").append(paramQueryConditions.getFieldsSqlClause());
    }
    if ((str1 != null) && (str1.equalsIgnoreCase("true"))) {}
    localStringBuffer.append(" order by a.firmID");
    this.log.debug("sql: " + localStringBuffer.toString());
    if (arrayOfObject != null) {
      for (int i = 0; i < arrayOfObject.length; i++) {
        this.log.debug("params[" + i + "]: " + arrayOfObject[i]);
      }
    }
    return getJdbcTemplate().queryForList(localStringBuffer.toString(), arrayOfObject);
  }
  
  public List getBrokerTrades(QueryConditions paramQueryConditions)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    Object[] arrayOfObject = null;
    localStringBuffer.append("select a.brokerid,a.name,t.commodityid,t.BS_Flag,t.OrderType,t.TradeType,nvl(sum(t.quantity),0) quantity,nvl(sum(t.Close_PL),0) Close_PL,nvl(sum(t.TradeFee),0) TradeFee,nvl(sum(t.CloseAddedTax),0) CloseAddedTax from m_b_broker a,m_b_firmandbroker b ");
    String str = (String)paramQueryConditions.getConditionValue("isQryHis");
    paramQueryConditions.removeCondition("isQryHis");
    if ((str != null) && (str.equalsIgnoreCase("true")))
    {
      localStringBuffer.append(", T_H_TRADE  t ");
    }
    else
    {
      localStringBuffer.append(", t_trade t ");
      paramQueryConditions.removeCondition("t.ClearDate", ">=");
      paramQueryConditions.removeCondition("t.ClearDate", "<=");
    }
    localStringBuffer.append(" where a.brokerid=b.brokerid and b.firmid=t.firmid(+) and t.quantity>0");
    if ((paramQueryConditions != null) && (paramQueryConditions.getFieldsSqlClause() != null))
    {
      arrayOfObject = paramQueryConditions.getValueArray();
      localStringBuffer.append(" and ").append(paramQueryConditions.getFieldsSqlClause());
    }
    localStringBuffer.append("  group by a.brokerid,a.name,t.commodityid,t.BS_Flag,t.OrderType,t.TradeType order by a.brokerid ");
    this.log.debug("sql: " + localStringBuffer.toString());
    if (arrayOfObject != null) {
      for (int i = 0; i < arrayOfObject.length; i++) {
        this.log.debug("params[" + i + "]: " + arrayOfObject[i]);
      }
    }
    return getJdbcTemplate().queryForList(localStringBuffer.toString(), arrayOfObject);
  }
  
  public List getFirmCategory()
  {
    String str = "select  m.* from m_firmcategory m  ";
    this.logger.debug("---------sql:-------- " + str);
    return getJdbcTemplate().queryForList(str);
  }
}

package gnnt.MEBS.timebargain.mgr.util;

import java.io.PrintStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("f_viewService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
public class ViewService
{
  private static final transient Log logger = LogFactory.getLog(ViewService.class);
  private ViewDao viewDao;

  public ViewDao getViewDao()
  {
    return this.viewDao;
  }

  public void setViewDao(ViewDao viewDao) {
    this.viewDao = viewDao;
  }

  public List getVoucherBase(QueryConditions conditions, PageInfo pageInfo)
  {
    String sql = "select * from f_Voucher where status='accounted'";
    Object[] params = null;
    if ((conditions != null) && (conditions.getFieldsSqlClause() != null)) {
      params = conditions.getValueArray();
      sql = sql + " and " + conditions.getFieldsSqlClause();
    }
    return this.viewDao.queryBySQL(sql, params, pageInfo);
  }

  public List queryAccountBook(QueryConditions conditions, PageInfo pageInfo)
  {
    String sql = "select t.*,(select name from f_account where code=t.debitcode) debitname,(select name from f_account where code=t.creditcode) creditname from f_AccountBook t";
    Object[] params = null;
    if ((conditions != null) && (conditions.getFieldsSqlClause() != null)) {
      params = conditions.getValueArray();
      sql = sql + " where " + conditions.getFieldsSqlClause();
    }
    return this.viewDao.queryBySQL(sql, params, pageInfo);
  }

  public BigDecimal getBeginBalance(String accountCode, Date date, boolean nearGet)
  {
    QueryConditions cond = new QueryConditions();
    cond.addCondition("accountCode", "=", accountCode);
    cond.addCondition("b_date", "=", date, "date");
    List rl = queryDailyBalance(cond, null);
    if ((rl != null) && (rl.size() > 0))
      return (BigDecimal)((Map)rl.get(0)).get("lastDayBalance");
    if (nearGet) {
      cond = new QueryConditions();
      cond.addCondition("accountCode", "=", accountCode);
      cond.addCondition("b_date", "<", date, "date");
      PageInfo pageInfo = new PageInfo();
      pageInfo.addOrderField("b_date", true);
      pageInfo.setPageSize(1);
      pageInfo.setPageNo(1);
      rl = queryDailyBalance(cond, pageInfo);
      if ((rl != null) && (rl.size() > 0)) {
        return (BigDecimal)((Map)rl.get(0)).get("todayBalance");
      }
    }
    return null;
  }

  public List queryDailyBalance(QueryConditions conditions, PageInfo pageInfo)
  {
    String sql = "select * from (select a.b_date,a.accountCode,b.name,a.lastDayBalance,a.DebitAmount,a.CreditAmount,a.todayBalance,b.accountLevel,b.dCFlag from f_DailyBalance a,f_Account b where a.accountCode=b.Code) db ";
    Object[] params = null;
    if ((conditions != null) && (conditions.getFieldsSqlClause() != null)) {
      params = conditions.getValueArray();
      sql = sql + " where " + conditions.getFieldsSqlClause();
    }
    return this.viewDao.queryBySQL(sql, params, pageInfo);
  }

  public List queryMarketBalance(Date occurDate)
  {
    String sql = "select u.marketid,m.name,sum(bailB) bailB,sum(feeB) feeB,sum(bailB)+sum(feeB) balance from userinfo u,marketinfo m,(select substr(d.accountcode,5) firmid,d.todaybalance bailB,d2.todaybalance feeB from dailybalance d,dailybalance d2 where d.occurdate=d2.occurdate and d.accountcode like '201-%' and d2.accountcode like '206-%' and substr(d.accountcode,5)=substr(d2.accountcode,5) and d.occurdate=?) subv where u.userid=subv.firmid and u.marketid=m.id group by u.marketid,m.name order by marketid";
    Object[] params = { occurDate };
    int[] dataTypes = { 91 };
    return this.viewDao.queryBySQL(sql, params, dataTypes, null);
  }

  public Map queryClientLedgerOutside(QueryConditions conditions, PageInfo pageInfo, String filter)
  {
    String sql = "select * from f_ledgerfield t where " + filter + " and ModuleID in (select moduleid from v_c_trademodule ) order by t.ordernum";
    Object[] params = null;
    List paramsList = new ArrayList();
    List listFiled = this.viewDao.queryBySQL(sql, params, null);
    String[] filed = null;
    String[] fieldSign = null;
    List fileds = null;
    if ((listFiled != null) && (listFiled.size() > 0))
    {
      filed = new String[listFiled.size()];
      fieldSign = new String[listFiled.size()];
      fileds = new ArrayList();
      fileds.add("b_date");
      fileds.add("firmId");
      fileds.add("lastBalance");
      for (int i = 0; i < listFiled.size(); i++)
      {
        String code = (String)((Map)listFiled.get(i)).get("CODE");
        String name = (String)((Map)listFiled.get(i)).get("NAME");
        String sign = ((BigDecimal)((Map)listFiled.get(i)).get("FIELDSIGN")).toString();
        filed[i] = code;
        fieldSign[i] = sign;

        fileds.add(code);
      }
      if (!" 1=1 ".equals(filter))
      {
        fileds.add("other");
      }
      fileds.add("todayBalance");
      fileds.add("lastWarranty");
      fileds.add("todayWarranty");
    }

    List listValue = null;
    if (filed != null)
    {
      String sql1 = ", sum(decode(code,'e_x_t',value,0)) e_x_t";

      String innerSql = "";
      for (int i = 0; i < filed.length; i++)
      {
        String field = filed[i];
        innerSql = innerSql + "nvl(ledgerSum." + field + ",0) " + field + ",";
      }
      innerSql = innerSql.substring(0, innerSql.lastIndexOf(","));

      sql = "select lastBalance,todayBalance,lastWarranty,todayWarranty,firmDate.firmId,firmDate.b_date," + 
        innerSql + " from (select to_char(b_date,'yyyy-MM-dd') b_date,firmId,lastBalance,todayBalance,lastWarranty,todayWarranty " + 
        "from f_firmbalance t ";
      if ((conditions != null) && (conditions.getFieldsSqlClause() != null)) {
        params = conditions.getValueArray();
        for (int ii = 0; ii < params.length; ii++)
        {
          paramsList.add(params[ii]);
        }
        sql = sql + " where " + conditions.getFieldsSqlClause();
      }

      sql = sql + " ) firmDate,(select firmid,to_char(b_date,'yyyy-MM-dd') b_date ";

      String otherSql = "todayBalance-lastBalance-(";
      for (int i = 0; i < filed.length; i++)
      {
        String field = filed[i];

        String sign = fieldSign[i];

        otherSql = otherSql + "+" + sign + "*" + field;
        String sql2 = sql1.replaceAll("e_x_t", field);
        sql = sql + sql2;
      }

      sql = sql + " from f_clientledger f ";
      if ((conditions != null) && (conditions.getFieldsSqlClause() != null)) {
        params = conditions.getValueArray();
        for (int ii = 0; ii < params.length; ii++)
        {
          paramsList.add(params[ii]);
        }
        sql = sql + " where " + conditions.getFieldsSqlClause();
      }
      sql = sql + " group by firmid,b_date) ledgerSum where firmDate.firmid=ledgerSum.firmid(+) and  firmDate.b_date=ledgerSum.b_date(+)";

      params = paramsList.toArray();
      String a = "select a.*";
      if ((filter != null) && (!" 1=1 ".equals(filter)))
      {
        a = a + ",(" + otherSql + ")) other";
      }
      a = a + " from (" + sql + ") a order by b_date,firmId";

      System.out.println(a);
      logger.debug("sql:" + sql);
      if (params != null)
      {
        for (int i = 0; i < params.length; i++) {
          logger.debug("params[" + i + "]: " + params[i]);
        }
      }
      listValue = this.viewDao.queryBySQL(a, params, pageInfo);
    }

    Map all = new HashMap();
    all.put("filed", fileds);
    all.put("value", listValue);

    return all;
  }

  public Map queryClientLedgerOutside2(QueryConditions conditions, PageInfo pageInfo, String filter, String cateGoryId, String brokerId)
  {
    String sql = "select * from f_ledgerfield t where " + filter + " and ModuleID in (select moduleid from M_tradermodule where Enabled='Y') order by t.ordernum";
    Object[] params = null;
    List paramsList = new ArrayList();
    List listFiled = this.viewDao.queryBySQL(sql, params, null);
    String[] filed = null;
    String[] fieldSign = null;
    List fileds = null;
    if ((listFiled != null) && (listFiled.size() > 0))
    {
      filed = new String[listFiled.size()];
      fieldSign = new String[listFiled.size()];
      fileds = new ArrayList();
      fileds.add("b_date");
      fileds.add("firmId");
      fileds.add("lastBalance");
      for (int i = 0; i < listFiled.size(); i++)
      {
        String code = (String)((Map)listFiled.get(i)).get("CODE");
        String name = (String)((Map)listFiled.get(i)).get("NAME");
        String sign = ((BigDecimal)((Map)listFiled.get(i)).get("FIELDSIGN")).toString();
        filed[i] = code;
        fieldSign[i] = sign;

        fileds.add(code);
      }
      if (!" 1=1 ".equals(filter))
      {
        fileds.add("other");
      }
      fileds.add("todayBalance");
      fileds.add("lastWarranty");
      fileds.add("todayWarranty");
    }

    List listValue = null;
    if (filed != null)
    {
      String sql1 = ", sum(decode(code,'e_x_t',value,0)) e_x_t";

      String innerSql = "";
      for (int i = 0; i < filed.length; i++)
      {
        String field = filed[i];
        innerSql = innerSql + "nvl(ledgerSum." + field + ",0) " + field + ",";
      }
      innerSql = innerSql.substring(0, innerSql.lastIndexOf(","));

      sql = "select lastBalance,todayBalance,lastWarranty,todayWarranty,firmDate.firmId,firmDate.b_date," + 
        innerSql + " from (select to_char(b_date,'yyyy-MM-dd') b_date,firmId,lastBalance,todayBalance,lastWarranty,todayWarranty " + 
        "from f_firmbalance t ";
      if ((conditions != null) && (conditions.getFieldsSqlClause() != null)) {
        params = conditions.getValueArray();
        for (int ii = 0; ii < params.length; ii++)
        {
          paramsList.add(params[ii]);
        }
        sql = sql + " where 1=1 and " + conditions.getFieldsSqlClause();
      }
      if (!cateGoryId.equals("null")) {
        sql = sql + " and firmid in (select firmid from m_firm f where  f.firmcategoryId in (select id from m_firmcategory where id='" + cateGoryId + "')) ";
      }
      if (brokerId != null) {
        sql = sql + " and firmId in (select firmId from BR_Broker where brokerId ='" + brokerId + "')";
      }
      if (brokerId != null) {
        sql = sql + " and firmId in (select firmId from BR_Broker where brokerId ='" + brokerId + "')";
      }
      sql = sql + " ) firmDate,(select firmid,to_char(b_date,'yyyy-MM-dd') b_date ";

      String otherSql = "todayBalance-lastBalance-(";
      for (int i = 0; i < filed.length; i++)
      {
        String field = filed[i];

        String sign = fieldSign[i];

        otherSql = otherSql + "+" + sign + "*" + field;
        String sql2 = sql1.replaceAll("e_x_t", field);
        sql = sql + sql2;
      }

      sql = sql + " from f_clientledger f ";
      if ((conditions != null) && (conditions.getFieldsSqlClause() != null)) {
        params = conditions.getValueArray();
        for (int ii = 0; ii < params.length; ii++)
        {
          paramsList.add(params[ii]);
        }
        sql = sql + " where 1=1 and " + conditions.getFieldsSqlClause();
      }
      if (!cateGoryId.equals("null")) {
        sql = sql + " and firmid in (select firmid from m_firm f where  f.firmcategoryId in (select id from m_firmcategory where id='" + cateGoryId + "')) ";
      }
      sql = sql + " group by firmid,b_date) ledgerSum where firmDate.firmid=ledgerSum.firmid(+) and  firmDate.b_date=ledgerSum.b_date(+)";

      params = paramsList.toArray();
      String a = "select a.*";
      if ((filter != null) && (!" 1=1 ".equals(filter)))
      {
        a = a + ",(" + otherSql + ")) other";
      }
      a = a + " from (" + sql + ") a order by b_date,firmId";

      logger.debug("sql:" + sql);
      System.out.println(a);
      if (params != null)
      {
        for (int i = 0; i < params.length; i++) {
          logger.debug("params[" + i + "]: " + params[i]);
        }
      }
      listValue = this.viewDao.queryBySQL(a, params, pageInfo);
    }

    Map all = new HashMap();
    all.put("filed", fileds);
    all.put("value", listValue);

    return all;
  }

  public List queryFiledMap(String type)
  {
    String fliter = "";
    if ((type != null) && (!"0".equals(type)) && (!"1".equals(type)))
    {
      fliter = " ModuleID in ('11','" + type + "') ";
    }
    else if ("1".equals(type))
    {
      fliter = " ModuleID='11' ";
    }
    else
    {
      fliter = " 1=1 ";
    }
    String sql = "select * from f_ledgerfield t where " + fliter + " and ModuleID in (select moduleid from v_c_trademodule ) and t.code <> 'OtherItem' order by t.ordernum";
    Object[] params = null;
    List listFiled = this.viewDao.queryBySQL(sql, params, null);
    List fileds = null;
    Map map = null;
    if ((listFiled != null) && (listFiled.size() > 0))
    {
      fileds = new ArrayList();
      map = new HashMap();
      map.put("name", SysData.getConfig("b_date"));
      map.put("code", "b_date");
      fileds.add(map);
      map = new HashMap();
      map.put("name", SysData.getConfig("firmId"));
      map.put("code", "firmId");
      fileds.add(map);
      map = new HashMap();
      map.put("name", SysData.getConfig("lastBalance"));
      map.put("code", "lastBalance");
      fileds.add(map);
      for (int i = 0; i < listFiled.size(); i++)
      {
        String code = (String)((Map)listFiled.get(i)).get("code");
        String name = (String)((Map)listFiled.get(i)).get("name");
        int sign = ((BigDecimal)((Map)listFiled.get(i)).get("FieldSign")).intValue();
        if (sign == 1)
        {
          name = "+" + name;
        }
        else
        {
          name = "-" + name;
        }
        map = new HashMap();
        map.put("name", name);
        map.put("code", code);
        fileds.add(map);
      }

      if (!fliter.equals(" 1=1 "))
      {
        map = new HashMap();
        map.put("name", SysData.getConfig("other"));
        map.put("code", "other");
        fileds.add(map);
      }
      map = new HashMap();
      map.put("name", SysData.getConfig("todayBalance"));
      map.put("code", "todayBalance");
      fileds.add(map);
      map = new HashMap();
      map.put("name", SysData.getConfig("lastWarranty"));
      map.put("code", "lastWarranty");
      fileds.add(map);
      map = new HashMap();
      map.put("name", SysData.getConfig("todayWarranty"));
      map.put("code", "todayWarranty");
      fileds.add(map);
    }
    return fileds;
  }

  public Map queryClientLedgerSumOutside(QueryConditions conditions, PageInfo pageInfo, String filter) {
    String sql = "select * from f_ledgerfield t where " + filter + " and ModuleID in (select moduleid from M_tradermodule where Enabled='Y') order by t.ordernum";
    Object[] params = null;
    List paramsList = new ArrayList();
    List listFiled = this.viewDao.queryBySQL(sql, params, null);
    String[] fieldSign = null;
    String[] filed = null;
    List fileds = null;
    if ((listFiled != null) && (listFiled.size() > 0))
    {
      filed = new String[listFiled.size()];
      fieldSign = new String[listFiled.size()];
      fileds = new ArrayList();
      fileds.add("firmId");
      fileds.add("lastBalance");
      for (int i = 0; i < listFiled.size(); i++)
      {
        String code = (String)((Map)listFiled.get(i)).get("CODE");
        String name = (String)((Map)listFiled.get(i)).get("name");
        String sign = ((BigDecimal)((Map)listFiled.get(i)).get("FIELDSIGN")).toString();
        filed[i] = code;
        fieldSign[i] = sign;
        fileds.add(code);
      }
      if (!" 1=1 ".equals(filter))
      {
        fileds.add("other");
      }
      fileds.add("todayBalance");
      fileds.add("lastWarranty");
      fileds.add("todayWarranty");
    }

    List listValue = null;
    if (filed != null)
    {
      String fiter = "";
      String otherSql = "todayBalance-lastBalance-(";
      String sql1 = ", sum(decode(code,'e_x_t',value,0)) e_x_t";
      sql = "select lastBalance,todayBalance,lastWarranty,todayWarranty,lastData.firmid ";

      for (int i = 0; i < filed.length; i++) {
        String field = filed[i];
        sql = sql + " ,nvl(ledgerSum." + field + ",0) " + field + " ";
      }
      sql = sql + " from (select lastBalance,lastWarranty,firmId,b_date from f_firmbalance ";

      if ((conditions != null) && (conditions.getFieldsSqlClause() != null)) {
        params = conditions.getValueArray();
        for (int ii = 0; ii < params.length; ii++)
        {
          paramsList.add(params[ii]);
        }
        sql = sql + " where " + conditions.getFieldsSqlClause();
      }
      sql = sql + ") lastData,(select todayBalance,todayWarranty,firmId,b_date from f_firmbalance ";
      if ((conditions != null) && (conditions.getFieldsSqlClause() != null)) {
        params = conditions.getValueArray();
        for (int ii = 0; ii < params.length; ii++)
        {
          paramsList.add(params[ii]);
        }
        sql = sql + " where " + conditions.getFieldsSqlClause();
      }
      sql = sql + ") todayData,(select  firmId,max(b_date) maxDate,min(b_date) minDate from f_firmbalance ";
      if ((conditions != null) && (conditions.getFieldsSqlClause() != null)) {
        params = conditions.getValueArray();
        for (int ii = 0; ii < params.length; ii++)
        {
          paramsList.add(params[ii]);
        }
        sql = sql + " where " + conditions.getFieldsSqlClause();
      }
      sql = sql + " group by firmId) firmDate,(select firmid lfirmid";
      for (int i = 0; i < filed.length; i++)
      {
        String field = filed[i];
        String sign = fieldSign[i];
        String sql2 = sql1.replaceAll("e_x_t", field);
        sql = sql + sql2;
        otherSql = otherSql + "+" + sign + "*" + field;
      }

      sql = sql + " from f_clientledger f ";
      if ((conditions != null) && (conditions.getFieldsSqlClause() != null)) {
        params = conditions.getValueArray();
        for (int ii = 0; ii < params.length; ii++)
        {
          paramsList.add(params[ii]);
        }
        sql = sql + " where " + conditions.getFieldsSqlClause();
      }

      sql = sql + " group by firmid) ledgerSum where firmDate.firmid=ledgerSum.lfirmid(+)  and lastData.firmId=firmDate.firmId and firmDate.minDate=lastData.b_Date and firmDate.firmId=todayData.Firmid and firmDate.maxDate=todayData.b_Date";

      params = paramsList.toArray();
      String a = "select a.*";
      if ((filter != null) && (!" 1=1 ".equals(filter)))
      {
        a = a + ",(" + otherSql + ")) other";
      }
      a = a + " from (" + sql + ") a order by firmId";

      logger.debug(a);
      System.out.println(a);
      if (params != null)
      {
        for (int i = 0; i < params.length; i++)
          logger.debug("params[" + i + "]: " + params[i]);
      }
      listValue = this.viewDao.queryBySQL(a, params, pageInfo);
      logger.debug("size:" + listValue.size());
    }

    Map all = new HashMap();
    all.put("filed", fileds);
    all.put("value", listValue);

    return all;
  }

  public Map queryClientLedgerSumOutside2(QueryConditions conditions, PageInfo pageInfo, String filter, String cateGoryId, String brokerId)
  {
    String sql = "select * from f_ledgerfield t where " + filter + " and ModuleID in (select moduleid from v_c_trademodule ) and t.code <> 'OtherItem' order by t.ordernum";
    Object[] params = null;
    List paramsList = new ArrayList();
    List listFiled = this.viewDao.queryBySQL(sql, params, null);
    String[] fieldSign = null;
    String[] filed = null;
    List fileds = null;
    if ((listFiled != null) && (listFiled.size() > 0))
    {
      filed = new String[listFiled.size()];
      fieldSign = new String[listFiled.size()];
      fileds = new ArrayList();
      fileds.add("b_date");
      fileds.add("firmId");
      fileds.add("lastBalance");
      for (int i = 0; i < listFiled.size(); i++)
      {
        String code = (String)((Map)listFiled.get(i)).get("CODE");
        String name = (String)((Map)listFiled.get(i)).get("name");
        String sign = ((BigDecimal)((Map)listFiled.get(i)).get("FIELDSIGN")).toString();
        filed[i] = code;
        fieldSign[i] = sign;
        fileds.add(code);
      }
      if (!" 1=1 ".equals(filter))
      {
        fileds.add("other");
      }
      fileds.add("todayBalance");
      fileds.add("lastWarranty");
      fileds.add("todayWarranty");
    }

    List listValue = null;
    if (filed != null)
    {
      String fiter = "";
      String otherSql = "todayBalance-lastBalance-(";
      String sql1 = ", sum(decode(code,'e_x_t',value,0)) e_x_t";
      sql = "select lastBalance,todayBalance,lastWarranty,todayWarranty,lastData.firmid ";

      for (int i = 0; i < filed.length; i++) {
        String field = filed[i];
        sql = sql + " ,nvl(ledgerSum." + field + ",0) " + field + " ";
      }
      sql = sql + " from (select lastBalance,lastWarranty,firmId,b_date from f_firmbalance ";

      if ((conditions != null) && (conditions.getFieldsSqlClause() != null)) {
        params = conditions.getValueArray();
        for (int ii = 0; ii < params.length; ii++)
        {
          paramsList.add(params[ii]);
        }
        sql = sql + " where " + conditions.getFieldsSqlClause();
      }
      if (cateGoryId != null) {
        sql = sql + " and firmid in (select firmid from m_firm f where  f.firmcategoryId in (select id from m_firmcategory where id='" + cateGoryId + "')) ";
      }
      if (brokerId != null) {
        sql = sql + " and firmId in (select firmId from BR_FirmAndBroker where brokerId ='" + brokerId + "')";
      }
      sql = sql + ") lastData,(select todayBalance,todayWarranty,firmId,b_date from f_firmbalance ";
      if ((conditions != null) && (conditions.getFieldsSqlClause() != null)) {
        params = conditions.getValueArray();
        for (int ii = 0; ii < params.length; ii++)
        {
          paramsList.add(params[ii]);
        }
        sql = sql + " where " + conditions.getFieldsSqlClause();
      }
      if (cateGoryId != null) {
        sql = sql + " and firmid in (select firmid from m_firm f where  f.firmcategoryId in (select id from m_firmcategory where id='" + cateGoryId + "')) ";
      }
      sql = sql + ") todayData,(select  firmId,max(b_date) maxDate,min(b_date) minDate from f_firmbalance ";
      if ((conditions != null) && (conditions.getFieldsSqlClause() != null)) {
        params = conditions.getValueArray();
        for (int ii = 0; ii < params.length; ii++)
        {
          paramsList.add(params[ii]);
        }
        sql = sql + " where " + conditions.getFieldsSqlClause();
      }
      if (cateGoryId != null) {
        sql = sql + " and firmid in (select firmid from m_firm f where  f.firmcategoryId in (select id from m_firmcategory where id='" + cateGoryId + "')) ";
      }
      sql = sql + " group by firmId) firmDate,(select firmid lfirmid";
      for (int i = 0; i < filed.length; i++)
      {
        String field = filed[i];
        String sign = fieldSign[i];
        String sql2 = sql1.replaceAll("e_x_t", field);
        sql = sql + sql2;
        otherSql = otherSql + "+" + sign + "*" + field;
      }

      sql = sql + " from f_clientledger f ";
      if ((conditions != null) && (conditions.getFieldsSqlClause() != null)) {
        params = conditions.getValueArray();
        for (int ii = 0; ii < params.length; ii++)
        {
          paramsList.add(params[ii]);
        }
        sql = sql + " where " + conditions.getFieldsSqlClause();
      }
      if (cateGoryId != null) {
        sql = sql + " and firmid in (select firmid from m_firm f where  f.firmcategoryId in (select id from m_firmcategory where id='" + cateGoryId + "')) ";
      }

      sql = sql + " group by firmid) ledgerSum where firmDate.firmid=ledgerSum.lfirmid(+)  and lastData.firmId=firmDate.firmId and firmDate.minDate=lastData.b_Date and firmDate.firmId=todayData.Firmid and firmDate.maxDate=todayData.b_Date";

      params = paramsList.toArray();
      String a = "select a.*";
      if ((filter != null) && (!" 1=1 ".equals(filter)))
      {
        a = a + ",(" + otherSql + ")) other";
      }
      a = a + " from (" + sql + ") a order by firmId";

      logger.debug(a);
      if (params != null)
      {
        for (int i = 0; i < params.length; i++)
          logger.debug("params[" + i + "]: " + params[i]);
      }
      listValue = this.viewDao.queryBySQL(a, params, pageInfo);
      logger.debug("size:" + listValue.size());
    }

    Map all = new HashMap();
    all.put("filed", fileds);
    all.put("value", listValue);

    return all;
  }

  public List queryContractAccount(String accountCode, String contractNo)
  {
    String sql = "select * from (select id,voucherDate,voucherNo,voucherSummary,creditAccountCode accountCode,creditAccountName accountName,amount debitAmount,0 creditAmount,debitAccountDCFlag dCFlag from accountbook where debitaccountcode like ? and contractNo=? union select id,voucherDate,voucherNo,voucherSummary,debitaccountcode accountCode,debitAccountName accountName,0 debitAmount,amount creditAmount,creditAccountDCFlag dCFlag from accountbook where creditaccountcode like ? and contractNo=?) queryView order by id";
    Object[] params = { accountCode + "%", contractNo, accountCode + "%", contractNo };
    return this.viewDao.queryBySQL(sql, params, null);
  }

  public List queryContractBalance(QueryConditions conditions, PageInfo pageInfo)
  {
    String sql = "select contractno,dSum,cSum,dSum-cSum balance from (select a.contractno,sum(b.debitamount) dSum,sum(b.creditamount) cSum from voucher a,voucherentry b where a.voucherno=b.voucherno and a.voucherstatus='accounted' and b.accountcode in ('202','205')";

    Object[] params = null;
    if ((conditions != null) && (conditions.getFieldsSqlClause() != null)) {
      params = conditions.getValueArray();
      sql = sql + " and " + conditions.getFieldsSqlClause();
    }
    sql = sql + " group by a.contractno ) queryView";
    return this.viewDao.queryBySQL(sql, params, pageInfo);
  }

  public List queryFirmBanlance(QueryConditions conditions, PageInfo pageInfo)
  {
    String sql = null;

    String systemType = SysData.getConfig("systemType");
    sql = "select * from (select z.*,f_balance-l_balance-y_balance balanceSubtract,f_balance+FrozenFunds user_balance from (select f.firmid,(select name from m_firm where firmId=f.firmId) name,f.balance f_balance,nvl(h.todaybalance,0) l_balance,nvl(e.y_balance,0) y_balance,nvl(f.lastwarranty,0) lastwarranty,nvl(-1*FrozenFunds,0) FrozenFunds from F_FirmFunds f,(select h1.firmid,h1.todaybalance from f_firmbalance h1 where h1.b_date =(select nvl(max(b_date),to_date('2009-01-01','yyyy-MM-dd')) from f_firmbalance)) h,(select d.firmid,0+nvl(b.c_balance,0)-nvl(c.d_balance,0) y_balance from F_FirmFunds d,(select a.firmid,sum(a.amount) c_balance from f_fundflow a where a.oprcode in (select t.summaryno from f_summary t where t.funddcflag='C') group by firmid) b,(select a.firmid,sum(a.amount) d_balance from f_fundflow a where a.oprcode in (select t.summaryno from f_summary t where t.funddcflag='D') group by firmid) c where d.firmid=b.firmid(+) and d.firmid=c.firmid(+)) e where f.firmid=h.firmid(+) and f.firmid=e.firmid(+)) z ) ";

    Object[] params = null;
    if ((conditions != null) && (conditions.getFieldsSqlClause() != null)) {
      params = conditions.getValueArray();
      sql = sql + " where " + conditions.getFieldsSqlClause();
    }
    return this.viewDao.queryBySQL(sql, params, pageInfo);
  }

  public List getTrademoduleList()
  {
    String sql = "select moduleid,name from M_tradermodule where Enabled='Y' order by to_number(moduleid)";
    String fliter = "";
    Object[] params = null;
    return this.viewDao.queryBySQL(sql, params, null);
  }

  public List getLedgerFieldList()
  {
    String sql = "select code,name from F_LedgerField order by OrderNum";
    String fliter = "";
    Object[] params = null;
    return this.viewDao.queryBySQL(sql, params, null);
  }

  public List queryFirmFunds(String firmId)
  {
    List list = null;
    String sql = "select (case when s.funddcflag='C' then '+' when s.funddcflag='D' then '-' end)||s.summary name,a.money value from (select t.oprcode,sum(t.amount) money from f_fundflow t where firmID='" + firmId + "' group by t.oprcode ) a,f_summary s where a.oprcode=SummaryNo";
    Object[] params = null;
    List listResult = this.viewDao.queryBySQL(sql, params, null);

    sql = "select firmId,lastBalance,balance from f_firmfunds t where firmId='" + firmId + "'";
    List listFunds = this.viewDao.queryBySQL(sql, params, null);

    Map map = null;
    if ((listFunds != null) && (listFunds.size() > 0)) {
      map = (Map)listFunds.get(0);
    }

    list = new ArrayList();
    list.add(map);
    list.add(listResult);

    return list;
  }

  public List queryFundflow(String type, QueryConditions conditions, PageInfo pageInfo)
  {
    List list = null;
    String sql = "";
    if ("h".equals(type))
    {
      sql = "select FundFlowID,FirmID,(select Summary from F_Summary where SummaryNo=OprCode) OprCode,ContractNo,CommodityID,Amount,Balance,AppendAmount,VoucherNo,CreateTime,B_Date from F_H_FundFlow";
    }
    else if ("d".equals(type))
    {
      sql = "select FundFlowID,FirmID,(select Summary from F_Summary where SummaryNo=OprCode) OprCode,ContractNo,CommodityID,Amount,Balance,AppendAmount,VoucherNo,CreateTime,B_Date from F_FundFlow";
    }
    else if ("hd".equals(type))
    {
      sql = "(select FundFlowID,FirmID,(select Summary from F_Summary where SummaryNo=OprCode) OprCode,ContractNo,CommodityID,Amount,Balance,AppendAmount,VoucherNo,CreateTime,B_Date from F_FundFlow union select FundFlowID,FirmID,(select Summary from F_Summary where SummaryNo=OprCode) OprCode,ContractNo,CommodityID,Amount,Balance,AppendAmount,VoucherNo,CreateTime,B_Date from F_H_FundFlow)";
    }
    else
    {
      return null;
    }

    Object[] params = null;
    if ((conditions != null) && (conditions.getFieldsSqlClause() != null)) {
      params = conditions.getValueArray();

      if (conditions.getFieldsSqlClause().contains("OprCode like  '%' || ? || '%'")) {
        StringBuffer newConditions = new StringBuffer(" ");
        String[] tempConditionsArr = conditions.getFieldsSqlClause().split("and");
        for (int i = 0; i < tempConditionsArr.length; i++) {
          if (tempConditionsArr[i].trim().equals("OprCode like  '%' || ? || '%'"))
            newConditions.append(" OprCode like ? and ");
          else {
            newConditions.append(tempConditionsArr[i] + " and ");
          }
        }
        sql = sql + " where " + newConditions.toString().substring(0, newConditions.toString().length() - 4);
      } else {
        sql = sql + " where " + conditions.getFieldsSqlClause();
      }
    }
    list = this.viewDao.queryBySQL(sql, params, pageInfo);
    return list;
  }

  public List queryLog(QueryConditions conditions, PageInfo pageInfo)
  {
    List list = null;
    String sql = "select * from f_log";
    Object[] params = null;
    if ((conditions != null) && (conditions.getFieldsSqlClause() != null)) {
      params = conditions.getValueArray();
      sql = sql + " where " + conditions.getFieldsSqlClause();
    }
    list = this.viewDao.queryBySQL(sql, params, pageInfo);
    return list;
  }

  public Map queryAccountBalance(String accountCode, String beginDate, String endDate)
  {
    Map map = null;
    String sql = "select b4.code,b4.name,b4.dcflag,nvl(b2.lastdaybalance,0) lastdaybalance,nvl(b3.todaybalance,0) todaybalance,nvl(b3.debitAmount,0) debitAmount,nvl(b3.creditAmount,0) creditAmount from (select min(t.b_date) mindate,max(b_date) maxdate,accountcode from f_dailybalance t where t.b_date>=to_date('" + 
      beginDate + "','yyyy-MM-dd') and t.b_date<=to_date('" + endDate + "','yyyy-MM-dd') and t.accountcode='" + accountCode + "' group by accountcode) b1," + 
      "(select lastdaybalance,b_date from f_dailybalance where accountcode='" + accountCode + "' ) b2," + 
      "(select todaybalance,debitAmount,creditAmount,b_date from f_dailybalance where accountcode='" + accountCode + "')b3, " + 
      "(select * from f_account where code='" + accountCode + "' ) b4" + 
      " where b1.mindate=b2.b_date and b1.maxdate=b3.b_date and b1.accountcode=b4.code";
    List list = this.viewDao.queryBySQL(sql);
    if ((list != null) && (list.size() > 0))
    {
      map = (Map)list.get(0);
    }
    return map;
  }

  public Map queryClientLedgerTotal(QueryConditions conditions, PageInfo pageInfo, String type)
  {
    Map all = new HashMap();
    String fiter = "";
    if ((type != null) && (!"0".equals(type)) && (!"1".equals(type)))
    {
      fiter = " ModuleID in ('11','" + type + "')";
    }
    else if ("1".equals(type))
    {
      fiter = " ModuleID='11'";
    }
    else
    {
      fiter = " 1=1 ";
    }
    all = queryClientLedgerTotal(conditions, pageInfo, fiter);
    return all;
  }

  public Map queryClientLedger(QueryConditions conditions, PageInfo pageInfo, String type)
  {
    Map all = new HashMap();
    String fiter = "";
    if ((type != null) && (!"0".equals(type)) && (!"1".equals(type)))
    {
      fiter = " ModuleID in ('11','" + type + "')";
    }
    else if ("1".equals(type))
    {
      fiter = " ModuleID='11'";
    }
    else
    {
      fiter = " 1=1 ";
    }
    all = queryClientLedgerOutside(conditions, pageInfo, fiter);
    return all;
  }
}
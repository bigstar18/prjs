package gnnt.MEBS.timebargain.manage.dao.jdbc;

import gnnt.MEBS.timebargain.manage.dao.StatQueryUseMainDSDao;
import gnnt.MEBS.timebargain.manage.model.Apply;
import gnnt.MEBS.timebargain.manage.model.StatQuery;
import gnnt.MEBS.timebargain.manage.util.QueryConditions;
import gnnt.MEBS.timebargain.server.model.Settle;
import gnnt.MEBS.timebargain.server.model.SystemStatus;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

public class StatQueryUseMainDSDaoJdbc
  extends BaseDAOJdbc
  implements StatQueryUseMainDSDao
{
  private Log log = LogFactory.getLog(StatQueryDAOJdbc.class);
  
  public List getHoldPositions1(QueryConditions conditions)
  {
    StringBuffer sb = new StringBuffer();
    sb.append("select t.firmid,t.customerid,t.commodityid,t.bs_flag,(t.HoldQty + t.GageQty) HoldQty,t.gageqty ");
    

    Object[] params = (Object[])null;
    

    sb.append("from t_customerholdsum t where t.BS_Flag=2 and t.GageQty > 0");
    if ((conditions != null) && (conditions.getFieldsSqlClause() != null))
    {
      params = conditions.getValueArray();
      sb.append(" and ").append(conditions.getFieldsSqlClause());
    }
    this.log.debug("sql: " + sb.toString());
    if (params != null) {
      for (int i = 0; i < params.length; i++) {
        this.log.debug("params[" + i + "]: " + params[i]);
      }
    }
    return getJdbcTemplate().queryForList(sb.toString(), params);
  }
  
  public List getHandSettle(QueryConditions conditions)
  {
    StringBuffer sb = new StringBuffer();
    Object[] params = (Object[])null;
    sb.append("select c.* from T_commodity c where c.status = 1 ");
    if ((conditions != null) && (conditions.getFieldsSqlClause() != null))
    {
      sb.append(" and ").append(conditions.getFieldsSqlClause());
      params = conditions.getValueArray();
    }
    this.log.debug("sql: " + sb.toString());
    if (params != null) {
      for (int i = 0; i < params.length; i++) {
        this.log.debug("params[" + i + "]: " + params[i]);
      }
    }
    return getJdbcTemplate().queryForList(sb.toString(), params);
  }
  
  public List getSettle(StatQuery statQuery)
  {
    StringBuffer sb = new StringBuffer();
    sb.append("select shp.settleprocessdate,shp.firmid,shp.commodityid,shp.bs_flag,(shp.HoldQty + shp.GageQty) settleQty,shp.a_HoldNo,shp.openQty,shp.settleMargin,shp.payout,shp.settleFee,shp.settle_PL,shp.SettleAddedTax,shp.Price,shp.SettlePrice,shp.SettleType  from T_SettleHoldPosition shp where 1=1 ");
    List list = new ArrayList();
    if (statQuery != null)
    {
      if ((statQuery.getCommodityID() != null) && (!"".equals(statQuery.getCommodityID())))
      {
        sb.append(" and shp.commodityid like ?");
        list.add("%" + statQuery.getCommodityID() + "%");
      }
      if ((statQuery.getFirmID() != null) && (!"".equals(statQuery.getFirmID())))
      {
        sb.append(" and shp.firmID like ?");
        list.add("%" + statQuery.getFirmID() + "%");
      }
      if (statQuery.getBS_Flag() != null)
      {
        sb.append(" and shp.BS_Flag like ?");
        list.add(statQuery.getBS_Flag());
      }
      if ((statQuery.getSettleProcessDate() != null) && (!"".equals(statQuery.getSettleProcessDate())))
      {
        sb.append(" and shp.SettleProcessDate = to_date(?,'yyyy-MM-dd')");
        list.add(statQuery.getSettleProcessDate());
      }
      if (statQuery.getSettleType() != null)
      {
        sb.append(" and shp.SettleType = ?");
        list.add(statQuery.getSettleType());
      }
      Object[] params = list.toArray();
      
      this.log.debug("sql: " + sb.toString());
      for (int i = 0; i < params.length; i++) {
        this.log.debug("params[" + i + "]: " + params[i]);
      }
      return getJdbcTemplate().queryForList(sb.toString(), params);
    }
    this.log.debug("sql: " + sb.toString());
    return getJdbcTemplate().queryForList(sb.toString());
  }
  
  public void updateSettle(StatQuery stat)
  {
    String sql = "update T_SettleHoldPosition set SettleQty=?,FellbackQty=? where ID=?";
    Object[] params = {
      stat.getSettleQty(), 
      stat.getFellbackQty(), 
      stat.getId() };
    
    this.log.debug("sql: " + sql);
    for (int i = 0; i < params.length; i++) {
      this.log.debug("params[" + i + "]: " + params[i]);
    }
    getJdbcTemplate().update(sql, params);
  }
  
  public List getSettleSum()
  {
    String sql = "select (one.holdqty + one.gageqty - sum2.s - sum2.f) replaceSettle,sum2.s settleQtys,sum2.f fellbackQtys from (select sum(settleQty) s, sum(fellbackQty) f from T_SettleCustomerHoldSum group by commodityid) sum2, (select holdqty holdQty, gageqty gageQty from T_SettleCustomerHoldSum) one";
    

    this.log.debug("sql: " + sql);
    return getJdbcTemplate().queryForList(sql);
  }
  
  public List getSettleTogether(String flag)
  {
    int f = 0;
    if ((flag != null) && (!"".equals(flag))) {
      f = Integer.parseInt(flag);
    }
    String sql = "select shp.firmid, shp.commodityid, sum((shp.holdqty + shp.gageqty)) relHoldQty from t_settleholdposition shp where shp.bs_flag = " + 
    
      f + " " + 
      "group by shp.firmid,shp.commodityid " + 
      "order by shp.firmid";
    
    this.log.debug("sql: " + sql);
    return getJdbcTemplate().queryForList(sql);
  }
  
  public List getCustomerFund(QueryConditions conditions, String CustomerID, String date)
  {
    List list = null;
    StringBuffer sb = new StringBuffer();
    sb.append(" select a.*,b.* from F_LedgerField a,F_ClientLedger b,F_Firm c  where a.code=b.code(+)  and c.firmid=b.firmid and (ModuleID=1 or ModuleID=2) ");
    if (!CustomerID.equals("")) {
      sb.append(" and b.FirmID='" + CustomerID + "'");
    }
    return list;
  }
  
  public List getFundTransfer(QueryConditions conditions)
  {
    StringBuffer sb = new StringBuffer();
    Object[] params = (Object[])null;
    StringBuffer temp = new StringBuffer();
    temp.append("select a.* from T_E_FundTransfer a where 1=1 ");
    sb = temp;
    if ((conditions != null) && (conditions.getFieldsSqlClause() != null))
    {
      params = conditions.getValueArray();
      sb.append(" and ").append(conditions.getFieldsSqlClause());
    }
    this.log.debug("sql: " + sb.toString());
    if (params != null) {
      for (int i = 0; i < params.length; i++) {
        this.log.debug("params[" + i + "]: " + params[i]);
      }
    }
    sb.append(" order by CreateTime desc");
    return getJdbcTemplate().queryForList(sb.toString(), params);
  }
  
  public List getDailyMoneys(QueryConditions conditions)
  {
    StringBuffer sb = new StringBuffer();
    sb.append("select a.*,b.*,c.Summary from  ");
    
    String isQryHis = (String)conditions.getConditionValue("isQryHis");
    
    Object[] params = (Object[])null;
    conditions.removeCondition("isQryHis");
    if ((isQryHis != null) && (isQryHis.equalsIgnoreCase("true")))
    {
      String table = "F_H_FundFlow";
      sb.append(table);
    }
    else
    {
      String table = "F_FundFlow";
      sb.append(table);
      conditions.removeCondition("a.CreateTime", ">=");
      conditions.removeCondition("a.CreateTime", "<=");
    }
    sb.append(" a, T_Firm b,F_Summary c where b.FirmID = a.FirmID and a.OprCode=c.SummaryNo ");
    if ((conditions != null) && (conditions.getFieldsSqlClause() != null))
    {
      params = conditions.getValueArray();
      sb.append(" and ").append(conditions.getFieldsSqlClause());
    }
    sb.append(" order by a.CreateTime");
    this.log.debug("sql: " + sb.toString());
    if (params != null) {
      for (int i = 0; i < params.length; i++) {
        this.log.debug("params[" + i + "]: " + params[i]);
      }
    }
    return getJdbcTemplate().queryForList(sb.toString(), params);
  }
  
  public void updateFundTransferStatusById(String id)
  {
    String sql = "update T_E_fundtransfer set status=1 where id=?";
    Object[] params = { id };
    this.log.debug("sql: " + sql);
    this.logger.debug("BreedID: " + params[0]);
    
    getJdbcTemplate().update(sql, params);
  }
  
  public String getConferClose(Settle settle)
  {
    String sql = "select count(*) from T_Commodity where commodityid='" + settle.getCommodityID() + "'";
    try
    {
      if (getJdbcTemplate().queryForInt(sql) < 1) {
        return "0";
      }
      return "1";
    }
    catch (Exception e)
    {
      throw new RuntimeException("商品代码不存在！");
    }
  }
  
  public String getConferClose2(Settle settle)
  {
    String sql2 = "select count(*) from T_Customer where CustomerID='" + settle.getBCustomerID() + "'";
    try
    {
      if (getJdbcTemplate().queryForInt(sql2) < 1) {
        return "2";
      }
      return "3";
    }
    catch (Exception e)
    {
      throw new RuntimeException("买客户代码不存在！");
    }
  }
  
  public String getConferClose3(Settle settle)
  {
    String sql3 = "select count(*) from T_Customer where CustomerID='" + settle.getSCustomerID() + "'";
    try
    {
      if (getJdbcTemplate().queryForInt(sql3) < 1) {
        return "4";
      }
      return "5";
    }
    catch (Exception e)
    {
      throw new RuntimeException("卖客户代码不存在！");
    }
  }
  
  public List getMarketCode()
  {
    String sql = "select marketCode from T_Commodity";
    return getJdbcTemplate().queryForList(sql);
  }
  
  public void insertSettle(StatQuery stat)
  {
    String sql = "insert into T_E_SETTLELOG(ID,CommodityID,Price,CustomerID_B,Quantity_B,GageQty_B,CustomerID_S,Quantity_S,GageQty_S,UserID,LogType,CreateTime) values (SEQ_T_E_SETTLELOG.nextval,?,?,?,?,?,?,?,?,?,?,sysdate)";
    
    Object[] params = {
      stat.getCommodityID(), 
      stat.getPrice(), 
      stat.getBCustomerID(), 
      stat.getBHoldQty(), 
      stat.getBGageQty(), 
      stat.getSCustomerID(), 
      stat.getSHoldQty(), 
      stat.getBGageQty(), 
      stat.getUserID(), 
      stat.getLogType() };
    
    this.log.debug("sql: " + sql);
    for (int i = 0; i < params.length; i++) {
      this.log.debug("params[" + i + "]: " + params[i]);
    }
    getJdbcTemplate().update(sql, params);
  }
  
  public List getAheadSettleLog()
  {
    String sql = "select s.*,c.commodityID from T_SettleLog s,T_commodity c where s.CommodityID=c.CommodityID and s.LogType=0";
    this.log.debug("sql: " + sql);
    return getJdbcTemplate().queryForList(sql);
  }
  
  public List getConferCloseLog()
  {
    String sql = "select s.*,c.commodityID from T_E_SETTLELOG s,T_COMMODITY c where s.CommodityID=c.CommodityID and s.LogType=1";
    this.log.debug("sql: " + sql);
    return getJdbcTemplate().queryForList(sql);
  }
  
  public List getApplyWaitCD(Apply app)
  {
    StringBuffer sb = new StringBuffer();
    sb.append("select a.* from T_E_APPLYBILL a where a.Status = 1 ");
    List list = new ArrayList();
    try
    {
      if (app != null)
      {
        if ((app.getFirmID_S() != null) && (!"".equals(app.getFirmID_S())))
        {
          sb.append(" and a.FirmID_S like ?");
          list.add(app.getFirmID_S());
        }
        if ((app.getCommodityID() != null) && (!"".equals(app.getCommodityID())))
        {
          sb.append(" and a.CommodityID like ?");
          list.add(app.getCommodityID());
        }
        Object[] params = list.toArray();
        for (int i = 0; i < params.length; i++) {
          this.log.debug("params[" + i + "]: " + params[i]);
        }
        return getJdbcTemplate().queryForList(sb.toString(), params);
      }
      return getJdbcTemplate().queryForList(sb.toString());
    }
    catch (Exception e)
    {
      throw new RuntimeException("查询记录不存在！");
    }
  }
  
  public List getApplyAlreadyCD(Apply app)
  {
    StringBuffer sb = new StringBuffer();
    sb.append("select a.* from T_E_APPLYBILL a where a.Status <> 1 ");
    List list = new ArrayList();
    try
    {
      if (app != null)
      {
        if ((app.getFirmID_S() != null) && (!"".equals(app.getFirmID_S())))
        {
          sb.append(" and a.FirmID_S like ?");
          list.add(app.getFirmID_S());
        }
        if ((app.getCommodityID() != null) && (!"".equals(app.getCommodityID())))
        {
          sb.append(" and a.CommodityID like ?");
          list.add(app.getCommodityID());
        }
        Object[] params = list.toArray();
        for (int i = 0; i < params.length; i++) {
          this.log.debug("params[" + i + "]: " + params[i]);
        }
        return getJdbcTemplate().queryForList(sb.toString(), params);
      }
      return getJdbcTemplate().queryForList(sb.toString());
    }
    catch (Exception e)
    {
      throw new RuntimeException("查询记录不存在！");
    }
  }
  
  public List getApplyLiveInfo(Apply app)
  {
    StringBuffer sb = new StringBuffer();
    List list = new ArrayList();
    sb.append("select v.* from T_VALIDBILL v where v.status = 1 ");
    if (app.getBillType() != null)
    {
      sb.append(" and v.BillType like ?");
      list.add(app.getBillType());
    }
    if ((app.getFirmID_S() != null) && (!"".equals(app.getFirmID_S())))
    {
      sb.append(" and v.FirmID_S like ?");
      list.add(app.getFirmID_S());
    }
    if ((app.getCommodityID() != null) && (!"".equals(app.getCommodityID())))
    {
      sb.append(" and v.CommodityID like ?");
      list.add(app.getCommodityID());
    }
    sb.append(" and (v.BillType = 1 or v.BillType = 5)");
    
    Object[] params = list.toArray();
    

    this.log.debug("sql: " + sb.toString());
    try
    {
      if (params != null) {
        return getJdbcTemplate().queryForList(sb.toString(), params);
      }
      return getJdbcTemplate().queryForList(sb.toString());
    }
    catch (Exception e)
    {
      throw new RuntimeException("查询记录不存在！");
    }
  }
  
  public Apply getGiveUpDD(String billID)
  {
    String sql = "select a.* from T_VALIDBILL a where a.Status = 1 and a.BillType = 1 and a.BillID = '" + billID + "'";
    
    this.log.debug("sql: " + sql);
    try
    {
      return (Apply)getJdbcTemplate().queryForObject(sql, new ApplyBillRowMapper());
    }
    catch (Exception e)
    {
      e.printStackTrace();
      throw new RuntimeException("查询记录不存在！");
    }
  }
  
  public Apply getGiveUpDelaySettle(String billID)
  {
    String sql = "select a.* from T_VALIDBILL a where a.Status = 1 and a.BillType = 5 and a.BillID = '" + billID + "'";
    
    this.log.debug("sql: " + sql);
    try
    {
      return (Apply)getJdbcTemplate().queryForObject(sql, new ApplyBillRowMapper());
    }
    catch (Exception e)
    {
      e.printStackTrace();
      throw new RuntimeException("查询记录不存在！");
    }
  }
  
  public Apply getAlreadySettle(String billID)
  {
    String sql = "select a.* from T_VALIDBILL a where a.status = 1 and a.BillType in(1,3) and a.BillID = '" + billID + "'";
    
    this.log.debug("sql: " + sql);
    try
    {
      return (Apply)getJdbcTemplate().queryForObject(sql, new ApplyBillRowMapper());
    }
    catch (Exception e)
    {
      e.printStackTrace();
      throw new RuntimeException("查询记录不存在！");
    }
  }
  
  public Apply getGiveUpWaitSettle(String billID)
  {
    String sql = "select a.* from T_VALIDBILL a where a.Status = 1 and a.BillType = 3 and a.BillID = '" + billID + "'";
    
    this.log.debug("sql: " + sql);
    try
    {
      return (Apply)getJdbcTemplate().queryForObject(sql, new ApplyBillRowMapper());
    }
    catch (Exception e)
    {
      e.printStackTrace();
      throw new RuntimeException("查询记录不存在！");
    }
  }
  
  class ApplyBillRowMapper
    implements RowMapper
  {
    ApplyBillRowMapper() {}
    
    public Object mapRow(ResultSet rs, int rowNum)
      throws SQLException
    {
      return rsToApplyBill(rs);
    }
    
    private Apply rsToApplyBill(ResultSet rs)
      throws SQLException
    {
      Apply m = new Apply();
      m.setValidID(new Short(rs.getShort("ValidID")));
      m.setApplyID(new Long(rs.getLong("ApplyID")));
      m.setCommodityID(rs.getString("commodityID"));
      m.setFirmID_S(rs.getString("FirmID_S"));
      m.setCustomerID_S(rs.getString("CustomerID_S"));
      m.setBillID(rs.getString("BillID"));
      m.setQuantity(new Long(rs.getLong("Quantity")));
      m.setStatus(new Short(rs.getShort("Status")));
      m.setBillType(new Short(rs.getShort("BillType")));
      return m;
    }
  }
  
  public void insertApplyGiveUpDD(Apply app)
  {
    String sqlStatus = "select count(*) from T_E_APPLYBILL a where a.billid = ? and a.ApplyType in (1,2,3) and (a.status = 1 or a.status = 2)";
    Object[] paramsStatus = {
      app.getBillID() };
    
    this.log.debug("sql: " + sqlStatus);
    for (int i = 0; i < paramsStatus.length; i++) {
      this.log.debug("params[" + i + "]: " + paramsStatus[i]);
    }
    if (getJdbcTemplate().queryForInt(sqlStatus, paramsStatus) > 0) {
      throw new RuntimeException("已有此记录，不能插入！");
    }
    sqlStatus = "select count(*) from T_E_Pledge where BillID = '" + app.getBillID() + "'";
    this.log.debug("sql: " + sqlStatus);
    if (getJdbcTemplate().queryForInt(sqlStatus) > 0) {
      throw new RuntimeException("已有此记录，不能插入！");
    }
    String sqlF = "select firmID from T_CUSTOMER where customerID = '" + app.getCustomerID_S() + "'";
    String firmID_S = (String)getJdbcTemplate().queryForObject(sqlF, String.class);
    
    String seq = "select SEQ_T_E_APPLYBILL.nextval from dual";
    Long relSeq = (Long)getJdbcTemplate().queryForObject(seq, Long.class);
    
    String sql = "insert into T_E_APPLYBILL (ApplyID,CommodityID,FirmID_S,CustomerID_S,BillID,Quantity,ApplyType,Status,Creator,Remark1,CreateTime) values (?,?,?,?,?,?,?,?,?,?,sysdate)";
    
    Object[] params = {
      relSeq, 
      app.getCommodityID(), 
      firmID_S, 
      app.getCustomerID_S(), 
      app.getBillID(), 
      app.getQuantity(), 
      app.getApplyType(), 
      app.getStatus(), 
      app.getCreator(), 
      app.getRemark1() };
    
    this.log.debug("sql: " + sql);
    for (int i = 0; i < params.length; i++) {
      this.log.debug("params[" + i + "]: " + params[i]);
    }
    try
    {
      getJdbcTemplate().update(sql, params);
    }
    catch (Exception e)
    {
      throw new RuntimeException("添加失败！");
    }
    sql = "insert into T_E_ApplyBillLog (ApplyID,CommodityID,FirmID_S,CustomerID_S,BillID,Quantity,ApplyType,Status,Creator,Remark1,CreateTime) values (?,?,?,?,?,?,?,?,?,?,sysdate)";
    
    params = new Object[] {
      relSeq, 
      app.getCommodityID(), 
      firmID_S, 
      app.getCustomerID_S(), 
      app.getBillID(), 
      app.getQuantity(), 
      app.getApplyType(), 
      app.getStatus(), 
      app.getCreator(), 
      app.getRemark1() };
    
    this.log.debug("sql: " + sql);
    for (int i = 0; i < params.length; i++) {
      this.log.debug("params[" + i + "]: " + params[i]);
    }
    try
    {
      getJdbcTemplate().update(sql, params);
    }
    catch (Exception e)
    {
      throw new RuntimeException("添加日志表失败！");
    }
  }
  
  public void insertApplyGiveUpDelaySettle(Apply app)
  {
    String sqlStatus = "select count(*) from T_E_APPLYBILL a where a.billid = ? and a.ApplyType = 9 and (a.status = 1 or a.status = 2)";
    Object[] paramsStatus = {
      app.getBillID() };
    
    this.log.debug("sql: " + sqlStatus);
    for (int i = 0; i < paramsStatus.length; i++) {
      this.log.debug("params[" + i + "]: " + paramsStatus[i]);
    }
    if (getJdbcTemplate().queryForInt(sqlStatus, paramsStatus) > 0) {
      throw new RuntimeException("已有此记录，不能插入！");
    }
    sqlStatus = "select count(*) from T_E_Pledge where BillID = '" + app.getBillID() + "'";
    this.log.debug("sql: " + sqlStatus);
    if (getJdbcTemplate().queryForInt(sqlStatus) > 0) {
      throw new RuntimeException("已有此记录，不能插入！");
    }
    String seq = "select SEQ_T_E_APPLYBILL.nextval from dual";
    Long relSeq = (Long)getJdbcTemplate().queryForObject(seq, Long.class);
    
    String sql = "insert into T_E_APPLYBILL (ApplyID,CommodityID,FirmID_S,CustomerID_S,BillID,Quantity,ApplyType,Status,Creator,Remark1,CreateTime) values (?,?,?,?,?,?,?,?,?,?,sysdate)";
    
    Object[] params = {
      relSeq, 
      app.getCommodityID(), 
      app.getFirmID_S(), 
      app.getCustomerID_S(), 
      app.getBillID(), 
      app.getQuantity(), 
      app.getApplyType(), 
      app.getStatus(), 
      app.getCreator(), 
      app.getRemark1() };
    
    this.log.debug("sql: " + sql);
    for (int i = 0; i < params.length; i++) {
      this.log.debug("params[" + i + "]: " + params[i]);
    }
    try
    {
      getJdbcTemplate().update(sql, params);
    }
    catch (Exception e)
    {
      throw new RuntimeException("添加失败！");
    }
    sql = "insert into T_E_ApplyBillLog (ApplyID,CommodityID,FirmID_S,CustomerID_S,BillID,Quantity,ApplyType,Status,Creator,Remark1,CreateTime) values (?,?,?,?,?,?,?,?,?,?,sysdate)";
    
    params = new Object[] {
      relSeq, 
      app.getCommodityID(), 
      app.getFirmID_S(), 
      app.getCustomerID_S(), 
      app.getBillID(), 
      app.getQuantity(), 
      app.getApplyType(), 
      app.getStatus(), 
      app.getCreator(), 
      app.getRemark1() };
    
    this.log.debug("sql: " + sql);
    for (int i = 0; i < params.length; i++) {
      this.log.debug("params[" + i + "]: " + params[i]);
    }
    try
    {
      getJdbcTemplate().update(sql, params);
    }
    catch (Exception e)
    {
      throw new RuntimeException("添加日志表失败！");
    }
  }
  
  public void inserApplyGiveUpWaitSettle(Apply app)
  {
    String sqlStatus = "select count(*) from T_E_APPLYBILL a where a.billid = ? and a.ApplyType in (3,7) and (a.status = 1 or a.status = 2)";
    Object[] paramsStatus = {
      app.getBillID() };
    
    this.log.debug("sql: " + sqlStatus);
    for (int i = 0; i < paramsStatus.length; i++) {
      this.log.debug("params[" + i + "]: " + paramsStatus[i]);
    }
    if (getJdbcTemplate().queryForInt(sqlStatus, paramsStatus) > 0) {
      throw new RuntimeException("已有此记录，不能插入！");
    }
    String firmID_S = "";
    try
    {
      String sqlF = "select firmID from T_FIRM where firmID = '" + app.getFirmID_S() + "'";
      firmID_S = (String)getJdbcTemplate().queryForObject(sqlF, String.class);
    }
    catch (Exception e)
    {
      throw new RuntimeException("对应交易商代码不存在！");
    }
    String seq = "select SEQ_T_E_APPLYBILL.nextval from dual";
    Long relSeq = (Long)getJdbcTemplate().queryForObject(seq, Long.class);
    
    String sql = "insert into T_E_APPLYBILL (ApplyID,CommodityID,FirmID_S,BillID,Quantity,ApplyType,Status,Creator,Remark1,CreateTime) values (?,?,?,?,?,?,?,?,?,sysdate)";
    
    Object[] params = {
      relSeq, 
      app.getCommodityID(), 
      app.getFirmID_S(), 
      app.getBillID(), 
      app.getQuantity(), 
      app.getApplyType(), 
      app.getStatus(), 
      app.getCreator(), 
      app.getRemark1() };
    
    this.log.debug("sql: " + sql);
    for (int i = 0; i < params.length; i++) {
      this.log.debug("params[" + i + "]: " + params[i]);
    }
    try
    {
      getJdbcTemplate().update(sql, params);
    }
    catch (Exception e)
    {
      throw new RuntimeException("添加失败！");
    }
    sql = "insert into T_E_ApplyBillLog (ApplyID,CommodityID,FirmID_S,BillID,Quantity,ApplyType,Status,Creator,Remark1,CreateTime) values (?,?,?,?,?,?,?,?,?,sysdate)";
    
    params = new Object[] {
      relSeq, 
      app.getCommodityID(), 
      app.getFirmID_S(), 
      app.getBillID(), 
      app.getQuantity(), 
      app.getApplyType(), 
      app.getStatus(), 
      app.getCreator(), 
      app.getRemark1() };
    
    this.log.debug("sql: " + sql);
    for (int i = 0; i < params.length; i++) {
      this.log.debug("params[" + i + "]: " + params[i]);
    }
    try
    {
      getJdbcTemplate().update(sql, params);
    }
    catch (Exception e)
    {
      throw new RuntimeException("添加日志表失败！");
    }
  }
  
  public void insertApplyAlreadySettle(Apply app)
  {
    String sqlStatus = "select count(*) from T_E_APPLYBILL a where a.billid = ? and a.ApplyType in (1,2,3,7) and (a.status = 1 or a.status = 2)";
    Object[] paramsStatus = {
      app.getBillID() };
    
    this.log.debug("sql: " + sqlStatus);
    for (int i = 0; i < paramsStatus.length; i++) {
      this.log.debug("params[" + i + "]: " + paramsStatus[i]);
    }
    if (getJdbcTemplate().queryForInt(sqlStatus, paramsStatus) > 0) {
      throw new RuntimeException("已有此记录，不能插入！");
    }
    sqlStatus = "select count(*) from T_E_Pledge where BillID = '" + app.getBillID() + "'";
    this.log.debug("sql: " + sqlStatus);
    if (getJdbcTemplate().queryForInt(sqlStatus) > 0) {
      throw new RuntimeException("已有此记录，不能插入！");
    }
    String firmID_S = "";
    try
    {
      String sqlF = "select firmID from T_CUSTOMER where customerID = '" + app.getCustomerID_S() + "'";
      firmID_S = (String)getJdbcTemplate().queryForObject(sqlF, String.class);
    }
    catch (Exception e)
    {
      throw new RuntimeException("对应卖交易商代码不存在！");
    }
    String firmID_B = "";
    try
    {
      String sqlFB = "select firmID from T_CUSTOMER where customerID = '" + app.getCustomerID_B() + "'";
      firmID_B = (String)getJdbcTemplate().queryForObject(sqlFB, String.class);
    }
    catch (Exception e)
    {
      throw new RuntimeException("对应买交易商代码不存在！");
    }
    String seq = "select SEQ_T_E_APPLYBILL.nextval from dual";
    Long relSeq = (Long)getJdbcTemplate().queryForObject(seq, Long.class);
    
    String sql = "insert into T_E_APPLYBILL (ApplyID,CommodityID,FirmID_S,CustomerID_S,BillID,Quantity,ApplyType,Status,Creator,Remark1,CreateTime,FirmID_B,CustomerID_B,Price) values (?,?,?,?,?,?,?,?,?,?,sysdate,?,?,?)";
    

    Object[] params = {
      relSeq, 
      app.getCommodityID(), 
      firmID_S, 
      app.getCustomerID_S(), 
      app.getBillID(), 
      app.getQuantity(), 
      app.getApplyType(), 
      app.getStatus(), 
      app.getCreator(), 
      app.getRemark1(), 
      firmID_B, 
      app.getCustomerID_B(), 
      app.getPrice() };
    
    this.log.debug("sql: " + sql);
    for (int i = 0; i < params.length; i++) {
      this.log.debug("params[" + i + "]: " + params[i]);
    }
    try
    {
      getJdbcTemplate().update(sql, params);
    }
    catch (Exception e)
    {
      throw new RuntimeException("添加失败！");
    }
    sql = "insert into T_E_ApplyBillLog (ApplyID,CommodityID,FirmID_S,CustomerID_S,BillID,Quantity,ApplyType,Status,Creator,Remark1,CreateTime,FirmID_B,CustomerID_B,Price) values (?,?,?,?,?,?,?,?,?,?,sysdate,?,?,?)";
    
    params = new Object[] {
      relSeq, 
      app.getCommodityID(), 
      firmID_S, 
      app.getCustomerID_S(), 
      app.getBillID(), 
      app.getQuantity(), 
      app.getApplyType(), 
      app.getStatus(), 
      app.getCreator(), 
      app.getRemark1(), 
      firmID_B, 
      app.getCustomerID_B(), 
      app.getPrice() };
    
    this.log.debug("sql: " + sql);
    for (int i = 0; i < params.length; i++) {
      this.log.debug("params[" + i + "]: " + params[i]);
    }
    try
    {
      getJdbcTemplate().update(sql, params);
    }
    catch (Exception e)
    {
      throw new RuntimeException("添加日志表失败！");
    }
  }
  
  public void insertWaitSettle(Apply app)
  {
    String sqlStatus = "select count(*) from T_E_APPLYBILL a where a.billid = ? and a.ApplyType in (4,5,6) and (a.status = 1 or a.status = 2)";
    Object[] paramsStatus = {
      app.getBillID() };
    
    this.log.debug("sql: " + sqlStatus);
    for (int i = 0; i < paramsStatus.length; i++) {
      this.log.debug("params[" + i + "]: " + paramsStatus[i]);
    }
    if (getJdbcTemplate().queryForInt(sqlStatus, paramsStatus) > 0) {
      throw new RuntimeException("已有此记录，不能插入！");
    }
    String seq = "select SEQ_T_E_APPLYBILL.nextval from dual";
    Long relSeq = (Long)getJdbcTemplate().queryForObject(seq, Long.class);
    
    String sql = "insert into T_E_APPLYBILL (ApplyID,CommodityID,FirmID_S,BillID,Quantity,ApplyType,Status,Creator,Remark1,CreateTime) values (?,?,?,?,?,?,?,?,?,sysdate)";
    
    Object[] params = {
      relSeq, 
      app.getCommodityID(), 
      app.getFirmID_S(), 
      app.getBillID(), 
      app.getQuantity(), 
      app.getApplyType(), 
      app.getStatus(), 
      app.getCreator(), 
      app.getRemark1() };
    
    this.log.debug("sql: " + sql);
    for (int i = 0; i < params.length; i++) {
      this.log.debug("params[" + i + "]: " + params[i]);
    }
    try
    {
      getJdbcTemplate().update(sql, params);
    }
    catch (Exception e)
    {
      throw new RuntimeException("添加失败！");
    }
    sql = "insert into T_E_ApplyBillLog (ApplyID,CommodityID,FirmID_S,BillID,Quantity,ApplyType,Status,Creator,Remark1,CreateTime) values (?,?,?,?,?,?,?,?,?,sysdate)";
    
    params = new Object[] {
      relSeq, 
      app.getCommodityID(), 
      app.getFirmID_S(), 
      app.getBillID(), 
      app.getQuantity(), 
      app.getApplyType(), 
      app.getStatus(), 
      app.getCreator(), 
      app.getRemark1() };
    
    this.log.debug("sql: " + sql);
    for (int i = 0; i < params.length; i++) {
      this.log.debug("params[" + i + "]: " + params[i]);
    }
    try
    {
      getJdbcTemplate().update(sql, params);
    }
    catch (Exception e)
    {
      throw new RuntimeException("添加日志表失败！");
    }
  }
  
  public void insertDD(Apply app)
  {
    String sqlStatus = "select count(*) from T_E_APPLYBILL a where a.billid = ? and a.ApplyType in (4,5,6,8) and (a.status = 1 or a.status = 2)";
    Object[] paramsStatus = {
      app.getBillID() };
    
    this.log.debug("sql: " + sqlStatus);
    for (int i = 0; i < paramsStatus.length; i++) {
      this.log.debug("params[" + i + "]: " + paramsStatus[i]);
    }
    if (getJdbcTemplate().queryForInt(sqlStatus, paramsStatus) > 0) {
      throw new RuntimeException("已有此记录，不能插入！");
    }
    sqlStatus = "select count(*) from T_E_Pledge where BillID = '" + app.getBillID() + "'";
    this.log.debug("sql: " + sqlStatus);
    if (getJdbcTemplate().queryForInt(sqlStatus) > 0) {
      throw new RuntimeException("已有此记录，不能插入！");
    }
    String firmID_S = "";
    try
    {
      String sqlF = "select firmID from T_CUSTOMER where customerID = '" + app.getCustomerID_S() + "'";
      firmID_S = (String)getJdbcTemplate().queryForObject(sqlF, String.class);
    }
    catch (Exception e)
    {
      e.printStackTrace();
      throw new RuntimeException("对应交易商代码不存在！");
    }
    String seq = "select SEQ_T_E_APPLYBILL.nextval from dual";
    Long relSeq = (Long)getJdbcTemplate().queryForObject(seq, Long.class);
    
    String sql = "insert into T_E_APPLYBILL (ApplyID,CommodityID,FirmID_S,BillID,Quantity,CustomerID_S,Remark1,ApplyType,Status,Creator,CreateTime) values (?,?,?,?,?,?,?,?,?,?,sysdate)";
    
    Object[] params = {
      relSeq, 
      app.getCommodityID(), 
      firmID_S, 
      app.getBillID(), 
      app.getQuantity(), 
      app.getCustomerID_S(), 
      app.getRemark1(), 
      app.getApplyType(), 
      app.getStatus(), 
      app.getCreator() };
    
    this.log.debug("sql: " + sql);
    for (int i = 0; i < params.length; i++) {
      this.log.debug("params[" + i + "]: " + params[i]);
    }
    try
    {
      getJdbcTemplate().update(sql, params);
    }
    catch (Exception e)
    {
      e.printStackTrace();
      throw new RuntimeException("添加失败！");
    }
    sql = "insert into T_E_ApplyBillLog (ApplyID,CommodityID,FirmID_S,BillID,Quantity,CustomerID_S,Remark1,ApplyType,Status,Creator,CreateTime) values (?,?,?,?,?,?,?,?,?,?,sysdate)";
    
    params = new Object[] {
      relSeq, 
      app.getCommodityID(), 
      firmID_S, 
      app.getBillID(), 
      app.getQuantity(), 
      app.getCustomerID_S(), 
      app.getRemark1(), 
      app.getApplyType(), 
      app.getStatus(), 
      app.getCreator() };
    
    this.log.debug("sql: " + sql);
    for (int i = 0; i < params.length; i++) {
      this.log.debug("params[" + i + "]: " + params[i]);
    }
    try
    {
      getJdbcTemplate().update(sql, params);
    }
    catch (Exception e)
    {
      e.printStackTrace();
      throw new RuntimeException("添加日志表失败！");
    }
  }
  
  public void insertBeforeSettle(Apply app)
  {
    String sqlStatus = "select count(*) from T_E_APPLYBILL a where a.billid = ? and a.ApplyType in (4,5,6,8) and (a.status = 1 or a.status = 2)";
    Object[] paramsStatus = {
      app.getBillID() };
    
    this.log.debug("sql: " + sqlStatus);
    for (int i = 0; i < paramsStatus.length; i++) {
      this.log.debug("params[" + i + "]: " + paramsStatus[i]);
    }
    if (getJdbcTemplate().queryForInt(sqlStatus, paramsStatus) > 0) {
      throw new RuntimeException("已有此记录，不能插入！");
    }
    sqlStatus = "select count(*) from T_E_Pledge where BillID = '" + app.getBillID() + "'";
    this.log.debug("sql: " + sqlStatus);
    if (getJdbcTemplate().queryForInt(sqlStatus) > 0) {
      throw new RuntimeException("已有此记录，不能插入！");
    }
    String firmID_B = "";
    String firmID_S = "";
    try
    {
      String sqlB = "select firmID from T_CUSTOMER where customerID = '" + app.getCustomerID_B() + "'";
      firmID_B = (String)getJdbcTemplate().queryForObject(sqlB, String.class);
      String sqlS = "select firmID from T_CUSTOMER where customerID = '" + app.getCustomerID_S() + "'";
      firmID_S = (String)getJdbcTemplate().queryForObject(sqlS, String.class);
    }
    catch (Exception e)
    {
      e.printStackTrace();
      throw new RuntimeException("对应交易商代码不存在！");
    }
    String seq = "select SEQ_T_E_APPLYBILL.nextval from dual";
    Long relSeq = (Long)getJdbcTemplate().queryForObject(seq, Long.class);
    
    String sql = "insert into T_E_APPLYBILL (ApplyID,CommodityID,Price,CustomerID_B,CustomerID_S,BillID,Quantity,ApplyType,Status,Creator,Remark1,FirmID_S,FirmID_B,CreateTime) values (?,?,?,?,?,?,?,?,?,?,?,?,?,sysdate)";
    

    Object[] params = {
      relSeq, 
      app.getCommodityID(), 
      app.getPrice(), 
      app.getCustomerID_B(), 
      app.getCustomerID_S(), 
      app.getBillID(), 
      app.getQuantity(), 
      app.getApplyType(), 
      app.getStatus(), 
      app.getCreator(), 
      app.getRemark1(), 
      firmID_S, 
      firmID_B };
    

    this.log.debug("sql: " + sql);
    for (int i = 0; i < params.length; i++) {
      this.log.debug("params[" + i + "]: " + params[i]);
    }
    try
    {
      getJdbcTemplate().update(sql, params);
    }
    catch (Exception e)
    {
      throw new RuntimeException("添加失败！");
    }
    sql = "insert into T_E_ApplyBillLog (ApplyID,CommodityID,Price,CustomerID_B,CustomerID_S,BillID,Quantity,ApplyType,Status,Creator,Remark1,FirmID_S,FirmID_B,CreateTime) values (?,?,?,?,?,?,?,?,?,?,?,?,?,sysdate)";
    
    params = new Object[] {
      relSeq, 
      app.getCommodityID(), 
      app.getPrice(), 
      app.getCustomerID_B(), 
      app.getCustomerID_S(), 
      app.getBillID(), 
      app.getQuantity(), 
      app.getApplyType(), 
      app.getStatus(), 
      app.getCreator(), 
      app.getRemark1(), 
      firmID_S, 
      firmID_B };
    

    this.log.debug("sql: " + sql);
    for (int i = 0; i < params.length; i++) {
      this.log.debug("params[" + i + "]: " + params[i]);
    }
    try
    {
      getJdbcTemplate().update(sql, params);
    }
    catch (Exception e)
    {
      throw new RuntimeException("添加日志表失败！");
    }
  }
  
  public void insertDelaySettle(Apply app)
  {
    String sqlF = "select count(*) from t_firm where firmID = '" + app.getFirmID_S() + "'";
    if (getJdbcTemplate().queryForInt(sqlF) < 0) {
      throw new RuntimeException("此交易商不存在！");
    }
    String sqlStatus = "select count(*) from T_E_APPLYBILL a where a.billid = ? and a.ApplyType in (4,5,6,8) and (a.status = 1 or a.status = 2)";
    Object[] paramsStatus = {
      app.getBillID() };
    
    this.log.debug("sql: " + sqlStatus);
    for (int i = 0; i < paramsStatus.length; i++) {
      this.log.debug("params[" + i + "]: " + paramsStatus[i]);
    }
    if (getJdbcTemplate().queryForInt(sqlStatus, paramsStatus) > 0) {
      throw new RuntimeException("已有此记录，不能插入！");
    }
    sqlStatus = "select count(*) from T_E_Pledge where BillID = '" + app.getBillID() + "'";
    this.log.debug("sql: " + sqlStatus);
    if (getJdbcTemplate().queryForInt(sqlStatus) > 0) {
      throw new RuntimeException("已有此记录，不能插入！");
    }
    String seq = "select SEQ_T_E_APPLYBILL.nextval from dual";
    Long relSeq = (Long)getJdbcTemplate().queryForObject(seq, Long.class);
    
    String sql = "insert into T_E_APPLYBILL (ApplyID,CommodityID,FirmID_S,BillID,Quantity,CustomerID_S,Remark1,ApplyType,Status,Creator,CreateTime) values (?,?,?,?,?,?,?,?,?,?,sysdate)";
    
    Object[] params = {
      relSeq, 
      app.getCommodityID(), 
      app.getFirmID_S(), 
      app.getBillID(), 
      app.getQuantity(), 
      app.getCustomerID_S(), 
      app.getRemark1(), 
      app.getApplyType(), 
      app.getStatus(), 
      app.getCreator() };
    
    this.log.debug("sql: " + sql);
    for (int i = 0; i < params.length; i++) {
      this.log.debug("params[" + i + "]: " + params[i]);
    }
    try
    {
      getJdbcTemplate().update(sql, params);
    }
    catch (Exception e)
    {
      e.printStackTrace();
      throw new RuntimeException("添加失败！");
    }
    sql = "insert into T_E_ApplyBillLog (ApplyID,CommodityID,FirmID_S,BillID,Quantity,CustomerID_S,Remark1,ApplyType,Status,Creator,CreateTime) values (?,?,?,?,?,?,?,?,?,?,sysdate)";
    
    params = new Object[] {
      relSeq, 
      app.getCommodityID(), 
      app.getFirmID_S(), 
      app.getBillID(), 
      app.getQuantity(), 
      app.getCustomerID_S(), 
      app.getRemark1(), 
      app.getApplyType(), 
      app.getStatus(), 
      app.getCreator() };
    
    this.log.debug("sql: " + sql);
    for (int i = 0; i < params.length; i++) {
      this.log.debug("params[" + i + "]: " + params[i]);
    }
    try
    {
      getJdbcTemplate().update(sql, params);
    }
    catch (Exception e)
    {
      e.printStackTrace();
      throw new RuntimeException("添加日志表失败！");
    }
  }
  
  public Apply getApplyBillById(String applyID)
  {
    String sql = "select a.* from T_E_APPLYBILL a where applyID = '" + applyID + "'";
    this.log.debug("sql: " + sql);
    try
    {
      return (Apply)getJdbcTemplate().queryForObject(sql, new ApplyBillRowMapper2());
    }
    catch (Exception e)
    {
      throw new RuntimeException("查询记录不存在！");
    }
  }
  
  class ApplyBillRowMapper2
    implements RowMapper
  {
    ApplyBillRowMapper2() {}
    
    public Object mapRow(ResultSet rs, int rowNum)
      throws SQLException
    {
      return rsToApplyBill(rs);
    }
    
    private Apply rsToApplyBill(ResultSet rs)
      throws SQLException
    {
      Apply m = new Apply();
      m.setApplyID(new Long(rs.getLong("ApplyID")));
      m.setCommodityID(rs.getString("commodityID"));
      m.setFirmID_S(rs.getString("FirmID_S"));
      m.setCustomerID_S(rs.getString("CustomerID_S"));
      m.setBillID(rs.getString("BillID"));
      m.setQuantity(new Long(rs.getLong("Quantity")));
      m.setApplyType(new Short(rs.getShort("ApplyType")));
      m.setStatus(new Short(rs.getShort("Status")));
      m.setRemark1(rs.getString("Remark1"));
      m.setCustomerID_B(rs.getString("CustomerID_B"));
      m.setPrice(new Double(rs.getDouble("Price")));
      m.setModifier(rs.getString("Modifier"));
      m.setRemark2(rs.getString("Remark2"));
      return m;
    }
  }
  
  public void updateApplyCheckFail(Apply app)
  {
    String sql = "update T_E_APPLYBILL set Status = 3 where ApplyID = " + app.getApplyID();
    this.log.debug("sql: " + sql);
    try
    {
      getJdbcTemplate().update(sql);
    }
    catch (Exception e)
    {
      throw new RuntimeException("修改失败！");
    }
    sql = "update T_E_ApplyBillLog set Status = 3 where ApplyID = " + app.getApplyID();
    this.log.debug("sql: " + sql);
    try
    {
      getJdbcTemplate().update(sql);
    }
    catch (Exception e)
    {
      throw new RuntimeException("修改日志表失败！");
    }
  }
  
  public void updateApplyCheckSuccess(Apply app)
  {
    String sql = "update T_E_APPLYBILL set Status = 2 where ApplyID = " + app.getApplyID();
    this.log.debug("sql: " + sql);
    try
    {
      getJdbcTemplate().update(sql);
    }
    catch (Exception e)
    {
      throw new RuntimeException("修改失败！");
    }
    sql = "update T_E_ApplyBillLog set Status = 2 where ApplyID = " + app.getApplyID();
    this.log.debug("sql: " + sql);
    try
    {
      getJdbcTemplate().update(sql);
    }
    catch (Exception e)
    {
      throw new RuntimeException("修改日志表失败！");
    }
  }
  
  public Apply getValidIDByApplyID(String billID)
  {
    String sql = "select v.* from T_VALIDBILL v where v.Status = 1 and v.BillType = 3 and v.billID = '" + billID + "'";
    this.log.debug("sql" + sql);
    try
    {
      return (Apply)getJdbcTemplate().queryForObject(sql, new ValidBillBackRowMapper());
    }
    catch (Exception e)
    {
      throw new RuntimeException("查询记录不存在！");
    }
  }
  
  class ValidBillBackRowMapper
    implements RowMapper
  {
    ValidBillBackRowMapper() {}
    
    public Object mapRow(ResultSet rs, int rowNum)
      throws SQLException
    {
      return rsToApplyBill(rs);
    }
    
    private Apply rsToApplyBill(ResultSet rs)
      throws SQLException
    {
      Apply m = new Apply();
      m.setValidID(new Short(rs.getShort("ValidID")));
      m.setBillType(new Short(rs.getShort("BillType")));
      m.setStatus(new Short(rs.getShort("Status")));
      m.setApplyID(new Long(rs.getLong("ApplyID")));
      m.setCommodityID(rs.getString("commodityID"));
      m.setCustomerID_S(rs.getString("CustomerID_S"));
      m.setBillID(rs.getString("BillID"));
      m.setQuantity(new Long(rs.getLong("Quantity")));
      

      return m;
    }
  }
  
  public Apply getValidIDByApplyID2(String billID)
  {
    String sql = "select v.* from T_VALIDBILL v where v.Status = 1  and v.billID = '" + billID + "'";
    this.log.debug("sql" + sql);
    try
    {
      return (Apply)getJdbcTemplate().queryForObject(sql, new ValidBillOnlyRowMapper());
    }
    catch (Exception e)
    {
      throw new RuntimeException("查询记录不存在！");
    }
  }
  
  class ValidBillOnlyRowMapper
    implements RowMapper
  {
    ValidBillOnlyRowMapper() {}
    
    public Object mapRow(ResultSet rs, int rowNum)
      throws SQLException
    {
      return rsToApplyBill(rs);
    }
    
    private Apply rsToApplyBill(ResultSet rs)
      throws SQLException
    {
      Apply m = new Apply();
      m.setValidID(new Short(rs.getShort("ValidID")));
      m.setBillType(new Short(rs.getShort("BillType")));
      m.setStatus(new Short(rs.getShort("Status")));
      m.setApplyID(new Long(rs.getLong("ApplyID")));
      m.setCommodityID(rs.getString("commodityID"));
      m.setCustomerID_S(rs.getString("CustomerID_S"));
      m.setBillID(rs.getString("BillID"));
      m.setQuantity(new Long(rs.getLong("Quantity")));
      m.setFirmID_S(rs.getString("FirmID_S"));
      

      return m;
    }
  }
  
  public Apply getValidIDByApplyID2LiveinfoBefore(String billID)
  {
    String sql = "select v.* from T_VALIDBILL v where v.Status = 1  and v.billID = '" + billID + "'";
    this.log.debug("sql" + sql);
    try
    {
      return (Apply)getJdbcTemplate().queryForObject(sql, new ValidBillOnlyRowMapper());
    }
    catch (Exception e)
    {
      throw new RuntimeException("查询记录不存在！");
    }
  }
  
  class ValidBillRowMapper
    implements RowMapper
  {
    ValidBillRowMapper() {}
    
    public Object mapRow(ResultSet rs, int rowNum)
      throws SQLException
    {
      return rsToApplyBill(rs);
    }
    
    private Apply rsToApplyBill(ResultSet rs)
      throws SQLException
    {
      Apply m = new Apply();
      m.setValidID(new Short(rs.getShort("ValidID")));
      m.setBillType(new Short(rs.getShort("BillType")));
      m.setStatus(new Short(rs.getShort("Status")));
      m.setApplyID(new Long(rs.getLong("ApplyID")));
      m.setCommodityID(rs.getString("commodityID"));
      m.setCustomerID_S(rs.getString("CustomerID_S"));
      m.setBillID(rs.getString("BillID"));
      m.setQuantity(new Long(rs.getLong("Quantity")));
      m.setCustomerID_B(rs.getString("CustomerID_B"));
      m.setPrice(new Double(rs.getDouble("Price")));
      return m;
    }
  }
  
  public List getQuantity(String applyID, String applyType)
  {
    int relApplyID = 0;
    int relApplyType = 0;
    if ((applyID != null) && (!"".equals(applyID))) {
      relApplyID = Integer.parseInt(applyID);
    }
    try
    {
      String sql = "select nvl(sum(Quantity),0) Quantity from T_E_APPLYBILL where ApplyID = ?";
      Object[] params = {
        Integer.valueOf(relApplyID) };
      
      this.log.debug("sql: " + sql);
      for (int i = 0; i < params.length; i++) {
        this.log.debug("params[" + i + "]: " + params[i]);
      }
      return getJdbcTemplate().queryForList(sql, params);
    }
    catch (Exception e)
    {
      throw new RuntimeException("查询记录不存在！");
    }
  }
  
  public List getHoldQtyDD(Apply app)
  {
    String sql = "select nvl(HoldQty,0) holdQty from T_CUSTOMERHOLDSUM where CustomerID = ? and CommodityID = ? and BS_Flag = 2";
    Object[] params = {
      app.getCustomerID_S(), 
      app.getCommodityID() };
    

    this.log.debug("sql: " + sql);
    for (int i = 0; i < params.length; i++) {
      this.log.debug("params[" + i + "]: " + params[i]);
    }
    return getJdbcTemplate().queryForList(sql, params);
  }
  
  public List getHoldQtyAheadSettleB(Apply app)
  {
    String sql = "select nvl(HoldQty,0) holdQty from T_CUSTOMERHOLDSUM where CustomerID = ? and CommodityID = ? and BS_Flag = 1";
    Object[] params = {
      app.getCustomerID_B(), 
      app.getCommodityID() };
    
    this.log.debug("sql: " + sql);
    for (int i = 0; i < params.length; i++) {
      this.log.debug("params[" + i + "]: " + params[i]);
    }
    return getJdbcTemplate().queryForList(sql, params);
  }
  
  public List getHoldQtyAheadSettleS(Apply app)
  {
    String sql = "select nvl(HoldQty,0) holdQty from T_CUSTOMERHOLDSUM where CustomerID = ? and CommodityID = ? and BS_Flag = 2";
    Object[] params = {
      app.getCustomerID_S(), 
      app.getCommodityID() };
    

    this.log.debug("sql: " + sql);
    for (int i = 0; i < params.length; i++) {
      this.log.debug("params[" + i + "]: " + params[i]);
    }
    return getJdbcTemplate().queryForList(sql, params);
  }
  
  public List getHoldQtyWaitSettle(Apply app)
  {
    String sql = "select nvl(HoldQty,0) holdQty from T_FIRMHOLDSUM where FirmID = ? and CommodityID = ? and BS_Flag = 2";
    Object[] params = {
      app.getFirmID_S(), 
      app.getCommodityID() };
    
    this.log.debug("sql: " + sql);
    for (int i = 0; i < params.length; i++) {
      this.log.debug("params[" + i + "]: " + params[i]);
    }
    return getJdbcTemplate().queryForList(sql, params);
  }
  
  public List getGageQty(Apply app)
  {
    String sql = "select nvl(GageQty,0) gageQty from T_CUSTOMERHOLDSUM where CustomerID = ? and CommodityID = ? and BS_Flag = 2";
    Object[] params = {
      app.getCustomerID_S(), 
      app.getCommodityID() };
    
    this.log.debug("sql: " + sql);
    for (int i = 0; i < params.length; i++) {
      this.log.debug("params[" + i + "]: " + params[i]);
    }
    return getJdbcTemplate().queryForList(sql, params);
  }
  
  public List getSettlePairsB(StatQuery stat)
  {
    StringBuffer sb = new StringBuffer();
    sb.append("select a.firmID,a.commodityid,sum(a.holdqty) holdqty,sum(a.gageqty) gageqty ")
    
      .append(" from t_settleholdposition a where BS_Flag = 1 ");
    List list = new ArrayList();
    if ((stat.getYear() != null) && (!"".equals(stat.getYear())))
    {
      sb.append(" and to_char(SettleProcessDate,'yyyy') = ?");
      list.add(stat.getYear());
    }
    if ((stat.getMonth() != null) && (!"".equals(stat.getMonth())))
    {
      sb.append(" and to_char(SettleProcessDate,'MM') = ?");
      list.add(stat.getMonth());
    }
    if ((stat.getDay() != null) && (!"".equals(stat.getDay())))
    {
      sb.append(" and to_char(SettleProcessDate,'dd') = ?");
      list.add(stat.getDay());
    }
    if ((stat.getCommodityID() != null) && (!"".equals(stat.getCommodityID())))
    {
      sb.append(" and commodityID like ?");
      list.add(stat.getCommodityID());
    }
    sb.append(" group by a.firmid,a.commodityid order by a.firmid");
    Object[] params = (Object[])null;
    if ((list != null) && (list.size() > 0)) {
      params = list.toArray();
    }
    this.log.debug("sql: " + sb.toString());
    if (params != null) {
      for (int i = 0; i < params.length; i++) {
        this.log.debug("params[" + i + "]: " + params[i]);
      }
    }
    return getJdbcTemplate().queryForList(sb.toString(), params);
  }
  
  public List getSettlePairS(StatQuery stat)
  {
    StringBuffer sb = new StringBuffer();
    sb.append("select a.firmID,a.commodityid,sum(a.holdqty) holdqty,sum(a.gageqty) gageqty ")
    
      .append(" from t_settleholdposition a where BS_Flag = 2 ");
    List list = new ArrayList();
    if ((stat.getYear() != null) && (!"".equals(stat.getYear())))
    {
      sb.append(" and to_char(SettleProcessDate,'yyyy') = ?");
      list.add(stat.getYear());
    }
    if ((stat.getMonth() != null) && (!"".equals(stat.getMonth())))
    {
      sb.append(" and to_char(SettleProcessDate,'MM') = ?");
      list.add(stat.getMonth());
    }
    if ((stat.getDay() != null) && (!"".equals(stat.getDay())))
    {
      sb.append(" and to_char(SettleProcessDate,'dd') = ?");
      list.add(stat.getDay());
    }
    if ((stat.getCommodityID() != null) && (!"".equals(stat.getCommodityID())))
    {
      sb.append(" and commodityID like ?");
      list.add(stat.getCommodityID());
    }
    sb.append(" group by a.firmid,a.commodityid order by a.firmid");
    Object[] params = (Object[])null;
    if ((list != null) && (list.size() > 0)) {
      params = list.toArray();
    }
    this.log.debug("sql: " + sb.toString());
    if (params != null) {
      for (int i = 0; i < params.length; i++) {
        this.log.debug("params[" + i + "]: " + params[i]);
      }
    }
    return getJdbcTemplate().queryForList(sb.toString(), params);
  }
  
  public Apply getValidIDByApplyIDGiveup(String billID)
  {
    String sql = "select v.* from T_VALIDBILL v where v.Status = 1 and v.BillType = 1 and v.billID = '" + billID + "'";
    this.log.debug("sql" + sql);
    try
    {
      return (Apply)getJdbcTemplate().queryForObject(sql, new ValidBillOnlyRowMapper());
    }
    catch (Exception e)
    {
      throw new RuntimeException("查询记录不存在！");
    }
  }
  
  public Apply getValidIDByApplyIDGiveupDelaySettle(String billID)
  {
    String sql = "select v.* from T_VALIDBILL v where v.Status = 1 and v.BillType = 5 and v.billID = '" + billID + "'";
    this.log.debug("sql" + sql);
    try
    {
      return (Apply)getJdbcTemplate().queryForObject(sql, new ValidBillOnlyRowMapper());
    }
    catch (Exception e)
    {
      throw new RuntimeException("查询记录不存在！");
    }
  }
  
  public void deleteApplyGiveupDD(Apply apply)
  {
    String sql = "delete from T_E_ApplyBill where ApplyID = ?";
    Object[] params = {
      apply.getApplyID() };
    try
    {
      getJdbcTemplate().update(sql, params);
    }
    catch (Exception e)
    {
      throw new RuntimeException("删除撤销抵顶记录失败！");
    }
    sql = "delete from T_E_ApplyBill where BillID = ? and ApplyType = 5 and Status = 2";
    params = new Object[] {
      apply.getBillID() };
    try
    {
      getJdbcTemplate().update(sql, params);
    }
    catch (Exception e)
    {
      throw new RuntimeException("删除对应抵顶记录失败！");
    }
  }
  
  public void deleteApplyGiveupDelaySettle(Apply apply)
  {
    String sql = "delete from T_E_ApplyBill where ApplyID = ?";
    Object[] params = {
      apply.getApplyID() };
    try
    {
      getJdbcTemplate().update(sql, params);
    }
    catch (Exception e)
    {
      throw new RuntimeException("删除撤销延期交收记录失败！");
    }
    sql = "delete from T_E_ApplyBill where BillID = ? and ApplyType = 8 and Status = 2";
    params = new Object[] {
      apply.getBillID() };
    try
    {
      getJdbcTemplate().update(sql, params);
    }
    catch (Exception e)
    {
      throw new RuntimeException("删除对应延期交收记录失败！");
    }
  }
  
  public void deleteApplyWaitSettle(Apply apply)
  {
    String sql = "delete from T_E_ApplyBill where ApplyID = ?";
    Object[] params = {
      apply.getApplyID() };
    try
    {
      getJdbcTemplate().update(sql, params);
    }
    catch (Exception e)
    {
      throw new RuntimeException("删除撤销等待交收记录失败！");
    }
    sql = "delete from T_E_ApplyBill where BillID = ? and ApplyType = 4 and Status = 2";
    params = new Object[] {
      apply.getBillID() };
    try
    {
      getJdbcTemplate().update(sql, params);
    }
    catch (Exception e)
    {
      throw new RuntimeException("删除对应等待交收记录失败！");
    }
  }
  
  public String getSysdate()
  {
    String sql = "select to_char(sysdate,'yyyy-MM-dd hh24:mi:ss') from dual";
    return (String)getJdbcTemplate().queryForObject(sql, String.class);
  }
  
  public String validatorCustomer_S(Apply app)
  {
    String sql = "select a.firmID from T_customer a where a.customerID = ?";
    Object[] params = {
      app.getCustomerID_S() };
    
    this.log.debug("sql: " + sql);
    for (int i = 0; i < params.length; i++) {
      this.log.debug("params[" + i + "]: " + params[i]);
    }
    try
    {
      return (String)getJdbcTemplate().queryForObject(sql, params, String.class);
    }
    catch (Exception e)
    {
      throw new RuntimeException("对应交易商代码不存在！");
    }
  }
  
  public List getPledgeList(StatQuery stat)
  {
    StringBuffer sb = new StringBuffer();
    sb.append("select p.* from T_E_Pledge p where 1=1 ");
    List list = new ArrayList();
    if (stat != null)
    {
      if ((stat.getBillID() != null) && (!"".equals(stat.getBillID())))
      {
        sb.append(" and p.billID like ?");
        list.add("%" + stat.getBillID() + "%");
      }
      if ((stat.getCommodityName() != null) && (!"".equals(stat.getCommodityName())))
      {
        sb.append(" and p.CommodityName like ?");
        list.add("%" + stat.getCommodityName() + "%");
      }
    }
    Object[] params = (Object[])null;
    if ((list != null) && (list.size() > 0)) {
      params = list.toArray();
    }
    this.log.debug("sql: " + sb.toString());
    if (params != null) {
      for (int i = 0; i < params.length; i++) {
        this.log.debug("params[" + i + "]: " + params[i]);
      }
    }
    return getJdbcTemplate().queryForList(sb.toString(), params);
  }
  
  public void insertPledge(StatQuery stat)
  {
    String sql = "select count(*) from t_firm where firmID = '" + stat.getFirmID() + "'";
    if (getJdbcTemplate().queryForInt(sql) < 1) {
      throw new RuntimeException("此交易商不存在！");
    }
    Long relQuantity = new Long(0L);
    if ((stat.getQuantity() != null) && (!"".equals(stat.getQuantity()))) {
      relQuantity = Long.valueOf(Long.parseLong(stat.getQuantity()));
    }
    sql = "insert into T_E_Pledge (ID,BillID,BillFund,FirmID,CommodityName,Quantity,CreateTime,Creator,ModifyTime,Modifier) values (SEQ_T_E_PLEDGE.nextval,?,?,?,?,?,sysdate,?,sysdate,?)";
    
    Object[] params = {
      stat.getBillID(), 
      stat.getBillFund(), 
      stat.getFirmID(), 
      stat.getCommodityName(), 
      relQuantity, 
      stat.getCreator(), 
      stat.getCreator() };
    

    this.log.debug("sql: " + sql);
    for (int i = 0; i < params.length; i++) {
      this.log.debug("params[" + i + "]: " + params[i]);
    }
    try
    {
      getJdbcTemplate().update(sql, params);
    }
    catch (Exception e)
    {
      e.printStackTrace();
      throw new RuntimeException("添加失败！");
    }
    sql = "update T_firm set MaxOverdraft = (MaxOverdraft + " + stat.getBillFund() + ") where firmID = '" + stat.getFirmID() + "'";
    this.log.debug("sql: " + sql);
    try
    {
      getJdbcTemplate().update(sql);
    }
    catch (Exception e)
    {
      e.printStackTrace();
      throw new RuntimeException("添加失败！");
    }
  }
  
  public StatQuery getPledgeById(String id)
  {
    Integer relID = new Integer(0);
    if ((id != null) && (!"".equals(id))) {
      relID = Integer.valueOf(Integer.parseInt(id));
    }
    String sql = "select p.* from T_E_Pledge p where p.id = ?";
    Object[] params = {
      relID };
    
    this.log.debug("sql: " + sql);
    for (int i = 0; i < params.length; i++) {
      this.log.debug("params[" + i + "]: " + params[i]);
    }
    try
    {
      List list = getJdbcTemplate().queryForList(sql, params);
      StatQuery stat = new StatQuery();
      if ((list != null) && (list.size() > 0))
      {
        Map map = (Map)list.get(0);
        stat.setId(relID);
        if (map.get("BillID") != null) {
          stat.setBillID(map.get("BillID").toString());
        }
        if (map.get("BillFund") != null) {
          stat.setBillFund(Double.valueOf(Double.parseDouble(map.get("BillFund").toString())));
        }
        if (map.get("FirmID") != null) {
          stat.setFirmID(map.get("FirmID").toString());
        }
        if (map.get("CommodityName") != null) {
          stat.setCommodityName(map.get("CommodityName").toString());
        }
        if (map.get("Quantity") != null) {
          stat.setQuantity(map.get("Quantity").toString());
        }
        if (map.get("CreateTime") != null) {
          stat.setCreateTime(map.get("CreateTime").toString());
        }
        if (map.get("Creator") != null) {
          stat.setCreator(map.get("Creator").toString());
        }
        if (map.get("ModifyTime") != null) {
          stat.setModifyTime(map.get("ModifyTime").toString());
        }
        if (map.get("Modifier") != null) {
          stat.setModifier(map.get("Modifier").toString());
        }
      }
      return stat;
    }
    catch (Exception e)
    {
      e.printStackTrace();
      throw new RuntimeException("此记录不存在！");
    }
  }
  
  public void updatePledge(StatQuery stat)
  {
    String sql = "select count(*) from t_firm where firmID = '" + stat.getFirmID() + "'";
    if (getJdbcTemplate().queryForInt(sql) < 1) {
      throw new RuntimeException("此交易商不存在！");
    }
    Long relQuantity = new Long(0L);
    if ((stat.getQuantity() != null) && (!"".equals(stat.getQuantity()))) {
      relQuantity = Long.valueOf(Long.parseLong(stat.getQuantity()));
    }
    sql = "select nvl(BillFund,0) from T_E_Pledge where id = " + stat.getId();
    Object obj = getJdbcTemplate().queryForObject(sql, Object.class);
    Double billFund = new Double(0.0D);
    if (obj != null) {
      billFund = Double.valueOf(Double.parseDouble(obj.toString()));
    }
    Double relBillFund = new Double(0.0D);
    relBillFund = Double.valueOf(stat.getBillFund().doubleValue() - billFund.doubleValue());
    
    sql = "update T_firm set MaxOverdraft = (" + relBillFund + " + MaxOverdraft) where firmID = '" + stat.getFirmID() + "'";
    this.log.debug("sql: " + sql);
    try
    {
      getJdbcTemplate().update(sql);
    }
    catch (Exception e)
    {
      e.printStackTrace();
      throw new RuntimeException("修改最大透支额度失败！");
    }
    sql = "update T_E_Pledge set billID=?,billFund=?,firmID=?,commodityName=?,quantity=?,ModifyTime=sysdate,Modifier=? where id = ?";
    
    Object[] params = {
      stat.getBillID(), 
      stat.getBillFund(), 
      stat.getFirmID(), 
      stat.getCommodityName(), 
      relQuantity, 
      stat.getModifier(), 
      stat.getId() };
    
    this.log.debug("sql: " + sql);
    for (int i = 0; i < params.length; i++) {
      this.log.debug("params[" + i + "]: " + params[i]);
    }
    try
    {
      getJdbcTemplate().update(sql, params);
    }
    catch (Exception e)
    {
      e.printStackTrace();
      throw new RuntimeException("修改失败！");
    }
  }
  
  public void deletePledgeById(String id)
  {
    Long relID = new Long(0L);
    if ((id != null) && (!"".equals(id))) {
      relID = Long.valueOf(Long.parseLong(id));
    }
    String sql = "select nvl(BillFund,0) from T_E_Pledge where id = " + relID;
    Object obj = getJdbcTemplate().queryForObject(sql, Object.class);
    Double billFund = new Double(0.0D);
    if (obj != null) {
      billFund = Double.valueOf(Double.parseDouble(obj.toString()));
    }
    sql = "select firmID from T_E_Pledge where id = " + relID;
    Object objF = getJdbcTemplate().queryForObject(sql, Object.class);
    String relFirmID = "";
    if (objF != null) {
      relFirmID = objF.toString();
    }
    sql = "update T_firm set MaxOverdraft = (MaxOverdraft - " + billFund + " ) where firmID = '" + relFirmID + "'";
    try
    {
      getJdbcTemplate().update(sql);
    }
    catch (Exception e)
    {
      e.printStackTrace();
      throw new RuntimeException("修改交易商最大透支额度失败！");
    }
    sql = "delete from T_E_Pledge where id = ?";
    Object[] params = {
      relID };
    

    this.log.debug("sql: " + sql);
    for (int i = 0; i < params.length; i++) {
      this.log.debug("params[" + i + "]: " + params[i]);
    }
    try
    {
      getJdbcTemplate().update(sql, params);
    }
    catch (Exception e)
    {
      e.printStackTrace();
      throw new RuntimeException("删除失败！");
    }
  }
  
  public String getSystemStatus()
  {
    String sql = "select status from t_systemstatus";
    Object obj = getJdbcTemplate().queryForObject(sql, Object.class);
    String status = "";
    if (obj != null) {
      status = obj.toString();
    }
    return status;
  }
  
  public SystemStatus getSystemStatusObject()
  {
    SystemStatus systemStatus = null;
    String sql = "select * from T_SystemStatus";
    this.log.debug("sql:" + sql);
    List lst = getJdbcTemplate().queryForList(sql);
    if ((lst != null) && (lst.size() > 0))
    {
      Map map = (Map)lst.get(0);
      systemStatus = new SystemStatus();
      systemStatus.setTradeDate((Date)map.get("TradeDate"));
      systemStatus.setStatus(((BigDecimal)map.get("Status")).intValue());
      systemStatus.setSectionID(map.get("SectionID") == null ? null : new Integer(((BigDecimal)map.get("SectionID")).intValue()));
      systemStatus.setNote((String)map.get("Note"));
      systemStatus.setRecoverTime((String)map.get("RecoverTime"));
    }
    return systemStatus;
  }
  
  public Boolean getHandSettleCnt(String commodityID)
  {
    String sql = "select count(*) from T_SettleHoldPosition where SettleProcessDate = (select TradeDate from T_SystemStatus) and CommodityID='" + commodityID + "' and SettleType in(0,1)";
    Object obj = getJdbcTemplate().queryForObject(sql, Object.class);
    int cnt = 0;
    if (obj != null) {
      cnt = Integer.parseInt(obj.toString());
    }
    Boolean isok = Boolean.valueOf(true);
    if (cnt > 0) {
      isok = Boolean.valueOf(false);
    }
    return isok;
  }
  
  public String getQuotationPrice(String commodityID)
  {
    String status = getSystemStatus();
    String sql = "";
    if ("3".equals(status)) {
      sql = "select price from t_quotation where commodityID = '" + commodityID + "'";
    } else {
      sql = "select yesterBalancePrice from t_quotation where commodityID = '" + commodityID + "'";
    }
    Object obj = getJdbcTemplate().queryForObject(sql, Object.class);
    String price = "";
    if (obj != null) {
      price = obj.toString();
    }
    return price;
  }
}

package gnnt.MEBS.member.firm.dao;

import gnnt.MEBS.base.copy.CommonRowMapper;
import gnnt.MEBS.base.query.PageInfo;
import gnnt.MEBS.base.query.QueryConditions;
import gnnt.MEBS.common.util.query.Utils;
import gnnt.MEBS.member.firm.unit.FirmLog;
import gnnt.MEBS.member.firm.unit.Trader;
import gnnt.MEBS.member.firm.unit.TraderModule;
import java.util.Date;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class TraderDao
  extends DaoHelperImpl
{
  private final transient Log logger = LogFactory.getLog(TraderDao.class);
  
  public List getTraders(QueryConditions conditions)
  {
    List rl = null;
    String sql = "select * from m_trader where 1=1 ";
    Object[] params = (Object[])null;
    if ((conditions != null) && (conditions.getFieldsSqlClause() != null))
    {
      params = conditions.getValueArray();
      sql = sql + " and " + conditions.getFieldsSqlClause();
    }
    this.logger.debug("sql: " + sql);
    rl = queryBySQL(sql, params, null, new CommonRowMapper(new Trader()));
    
    return rl;
  }
  
  public List getTraderList(QueryConditions conditions, PageInfo pageInfo)
  {
    String sql = "select * from m_trader where 1=1 ";
    if (conditions != null)
    {
      if ((conditions.getConditionValue("createTime", ">=") != null) && (conditions.getConditionValue("createTime", "<=") != null))
      {
        String beginDate = Utils.formatDate("yyyy-MM-dd", (Date)conditions.getConditionValue("createTime", ">="));
        String endDate = Utils.formatDate("yyyy-MM-dd", (Date)conditions.getConditionValue("createTime", "<="));
        

        sql = sql + " and to_char(createTime,'yyyy-MM-dd') >= '" + beginDate + "' and to_char(createTime,'yyyy-MM-dd') <= '" + endDate + "' ";
      }
      conditions.removeCondition("createTime", ">=");
      conditions.removeCondition("createTime", "<=");
    }
    Object[] params = (Object[])null;
    if ((conditions != null) && (conditions.getFieldsSqlClause() != null))
    {
      params = conditions.getValueArray();
      sql = sql + " and " + conditions.getFieldsSqlClause();
    }
    this.logger.debug("---------sql:-------- " + sql);
    return queryBySQL(sql, params, pageInfo);
  }
  
  public boolean querySign(Trader trader, String filter)
  {
    boolean sign = true;
    String sql = "select * from m_firmmodule t,m_firm m where t.firmid=m.firmid and t.moduleid in (" + filter + ") and m.firmID='" + trader.getFirmId() + "' and enabled='N'";
    List aList = queryBySQL(sql);
    if ((aList != null) && (aList.size() > 0)) {
      sign = false;
    }
    return sign;
  }
  
  public void createTrader(Trader trader)
  {
    String sql = "insert into m_trader (traderId,name,password,firmId,status,createTime,modifyTime,keyCode,enableKey,type)  values(?,?,?,?,?,sysdate,sysdate,?,?,?)";
    
    Object[] params = {
      trader.getTraderId(), 
      trader.getName(), 
      trader.getPassword(), 
      trader.getFirmId(), 
      trader.getStatus(), 
      trader.getKeyCode(), 
      trader.getEnableKey(), 
      trader.getType() };
    

    int[] dataTypes = {
      12, 
      12, 
      12, 
      12, 
      12, 
      12, 
      12, 
      1 };
    

    updateBySQL(sql, params);
  }
  
  public void addTraderModule(TraderModule module)
  {
    String sql = "insert into m_TraderModule values(?,?,?)";
    Object[] params = {
      module.getModuleId(), 
      module.getTraderId(), 
      module.getEnabled() };
    
    updateBySQL(sql, params);
  }
  
  public List<TraderModule> getTraderModuleByTraderId(String traderId)
  {
    String sql = "select * from m_tradermodule t where t.enabled = 'Y' and t.traderid = '" + traderId + "'";
    return queryBySQL(sql, null, null, new CommonRowMapper(new TraderModule()));
  }
  
  public void updateTrader(Trader trader)
  {
    String sql = "update m_trader set name=?,firmId=?,modifyTime=sysdate,keyCode=?,enableKey=?,type=? where traderId=?";
    Object[] params = {
      trader.getName(), 
      trader.getFirmId(), 
      trader.getKeyCode(), 
      trader.getEnableKey(), 
      trader.getType(), 
      trader.getTraderId() };
    

    int[] dataTypes = {
      12, 
      12, 
      12, 
      12, 
      1, 
      12 };
    

    this.logger.debug("sql: " + sql);
    for (int i = 0; i < params.length; i++) {
      this.logger.debug("params[" + i + "]: " + params[i]);
    }
    updateBySQL(sql, params, dataTypes);
  }
  
  public void initTraderModule(String traderId)
  {
    String sql = "update m_TraderModule set enabled='N' where traderId='" + traderId + "'";
    updateBySQL(sql);
  }
  
  public void updateTraderModule(TraderModule module)
  {
    String sql = "update m_TraderModule set enabled='Y' where traderId='" + module.getTraderId() + "' and moduleID='" + module.getModuleId() + "'";
    this.logger.debug(sql);
    updateBySQL(sql);
  }
  
  public void setStatusTrader(Trader trader)
  {
    String sql = "update m_Trader set status='" + trader.getStatus() + "' where traderId='" + trader.getTraderId() + "'";
    updateBySQL(sql);
  }
  
  public void addTraderlog(FirmLog firmLog)
  {
    String sql = "insert into m_firmLog (occurTime,userId,firmId,action) values (sysdate,?,?,?)";
    Object[] params = {
      firmLog.getUserId(), 
      firmLog.getFirmId(), 
      firmLog.getAction() };
    
    this.logger.debug(sql);
    updateBySQL(sql, params);
  }
  
  public void deleteTrader(String traderId)
  {
    String sql = "delete from m_tradermodule where traderId='" + traderId + "'";
    this.logger.debug(sql);
    updateBySQL(sql);
    
    sql = "delete from m_trader where traderId='" + traderId + "'";
    this.logger.debug(sql);
    updateBySQL(sql);
  }
  
  public void changePwdTrader(String traderId, String password)
  {
    String sql = "update m_trader set password=?,FORCECHANGEPWD=1 where traderId=?";
    
    Object[] params = {
      password, 
      traderId };
    
    this.logger.debug("sql: " + sql);
    for (int i = 0; i < params.length; i++) {
      this.logger.debug("params[" + i + "]: " + params[i]);
    }
    updateBySQL(sql, params);
  }
}

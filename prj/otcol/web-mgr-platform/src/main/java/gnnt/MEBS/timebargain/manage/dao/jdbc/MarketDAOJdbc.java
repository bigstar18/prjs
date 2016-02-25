package gnnt.MEBS.timebargain.manage.dao.jdbc;

import gnnt.MEBS.timebargain.manage.dao.MarketDAO;
import gnnt.MEBS.timebargain.manage.model.Market;
import gnnt.MEBS.timebargain.manage.util.StringUtil;
import gnnt.MEBS.timebargain.server.model.SystemStatus;
import java.io.PrintStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.util.Assert;

public class MarketDAOJdbc
  extends BaseDAOJdbc
  implements MarketDAO
{
  private Log log = LogFactory.getLog(MarketDAOJdbc.class);
  
  public List getMarketById(String marketCode)
  {
    String sql = "select * from T_A_Market";
    
    Object[] params = { marketCode };
    
    String sql2 = "select count(*) from T_A_Market";
    Object[] params2 = { marketCode };
    



    List list = null;
    try
    {
      System.out.print("--------------------------这里：");
      if (getJdbcTemplate().queryForInt(sql2) > 0) {
        list = getJdbcTemplate().queryForList(sql);
      }
    }
    catch (IncorrectResultSizeDataAccessException e)
    {
      e.printStackTrace();
    }
    return list;
  }
  
  public List getMarkets(Market market)
  {
    String sql = "select * from T_A_Market";
    
    this.log.debug("sql: " + sql);
    
    return getJdbcTemplate().queryForList(sql);
  }
  
  public void insertMarket(Market market)
  {
    String sql = "insert into T_A_Market(MarketCode,Status,MarginFBFlag,ShortName,FloatingLossComputeType,RunMode,SettleMode,TradePriceAlgr,FloatingProfitSubTax,GageMode,TradeTimeType,DelayQuoShowType,NeutralFeeWay,ASMarginType,DelayOrderIsPure,ChargeDelayFeeType,IsCPriceCpFloatingLoss) values('99',1,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
    


































    Object[] params = {market.getMarginFBFlag(), market.getShortName(), market.getFloatingLossComputeType(), market.getRunMode(), market.getSettleMode(), market.getTradePriceAlgr(), market.getFloatingProfitSubTax(), market.getGageMode(), market.getTradeTimeType(), market.getDelayQuoShowType(), market.getNeutralFeeWay(), market.getAsMarginType(), market.getDelayOrderIsPure(), market.getChargeDelayFeeType(), market.getIsCPriceCpFloatingLoss() };
    

    this.log.debug("sql: " + sql);
    for (int i = 0; i < params.length; i++) {
      this.log.debug("params[" + i + "]: " + params[i]);
    }
    try
    {
      getJdbcTemplate().update(sql, params);
    }
    catch (DataIntegrityViolationException err)
    {
      err.printStackTrace();
      throw new RuntimeException("主键重复，不能插入相同的记录！");
    }
  }
  
  public void updateMarket(Market market)
  {
    Long date;
    Long date;
    if (market.getBeforeDays() != null) {
      date = market.getBeforeDays();
    } else {
      date = Long.valueOf(Long.parseLong("0"));
    }
    String sql = "update T_A_Market set Status=1,MarginFBFlag=?,FloatingLossComputeType=?,RunMode=?,ShortName=?,SettleMode=?,TradePriceAlgr=?,FloatingProfitSubTax=?,GageMode=?,TradeTimeType=?,DelayQuoShowType=?,NeutralFeeWay=?,ASMarginType=?,DelayOrderIsPure=?,ChargeDelayFeeType=?,IsCPriceCpFloatingLoss=? where MarketCode=?";
    







    Object[] params = {
    
      market.getMarginFBFlag(), 
      
      market.getFloatingLossComputeType(), 
      

      market.getRunMode(), 
      


      market.getShortName(), 
      

      market.getSettleMode(), 
      
      market.getTradePriceAlgr(), 
      market.getFloatingProfitSubTax(), 
      market.getGageMode(), 
      market.getTradeTimeType(), 
      market.getDelayQuoShowType(), 
      market.getNeutralFeeWay(), 
      market.getAsMarginType(), 
      market.getDelayOrderIsPure(), 
      market.getChargeDelayFeeType(), 
      market.getIsCPriceCpFloatingLoss(), 
      market.getMarketCode() };
    

    this.log.debug("sql: " + sql);
    for (int i = 0; i < params.length; i++) {
      this.log.debug("params[" + i + "]: " + params[i]);
    }
    getJdbcTemplate().update(sql, params);
  }
  
  public void updateMarketEndTime(Market market)
  {
    String sql = "update T_A_Market set endtime=?,IssueDate=?,VerifyStr=?,traderId=? where MarketCode=?";
    Object[] params = {
      market.getUpToTime(), 
      market.getIssueDate(), 
      market.getVerifyStr(), 
      market.getTraderId(), 
      market.getMarketCode() };
    


    this.log.debug("sql: " + sql);
    for (int i = 0; i < params.length; i++) {
      this.log.debug("params[" + i + "]: " + params[i]);
    }
    getJdbcTemplate().update(sql, params);
  }
  
  public void updateTradePassword(String oldPwd, String newPwd, String marketCode, String type)
  {
    if ("2".equals(type))
    {
      String sql = "select TradePassword from T_A_Market where trim(MarketCode)='" + marketCode + "'";
      String tradePassword = (String)getJdbcTemplate().queryForObject(sql, String.class);
      if ((tradePassword == null) || (!tradePassword.equals(StringUtil.encodeString(oldPwd)))) {
        throw new RuntimeException("原口令输入错误！");
      }
    }
    String sql = "update T_A_Market set TradePassword=? where MarketCode=?";
    Object[] params = {
      StringUtil.encodeString(newPwd), marketCode };
    
    this.log.debug("sql: " + sql);
    for (int i = 0; i < params.length; i++) {
      this.log.debug("params[" + i + "]: " + params[i]);
    }
    getJdbcTemplate().update(sql, params);
  }
  
  public void deleteMarketById(String marketCode)
  {
    Assert.hasText(marketCode);
    Object[] params = { marketCode };
    String sql = "select count(*) from Commodity where trim(MarketCode)=?";
    if (getJdbcTemplate().queryForInt(sql, params) > 0) {
      throw new RuntimeException("此市场还有商品，不能删除！");
    }
    sql = "select count(*) from CM_CustomerMap where trim(MarketCode)=?";
    if (getJdbcTemplate().queryForInt(sql, params) > 0) {
      throw new RuntimeException("此市场还有客户映射关系，不能删除！");
    }
    sql = "select count(*) from Suffix where trim(MarketCode)=?";
    if (getJdbcTemplate().queryForInt(sql, params) > 0) {
      throw new RuntimeException("此市场还有客户后缀，不能删除！");
    }
    sql = "select count(*) from CM_TradeRule where trim(MarketCode)=?";
    if (getJdbcTemplate().queryForInt(sql, params) > 0) {
      throw new RuntimeException("此市场还有客户交易规则，不能删除！");
    }
    sql = "select count(*) from GM_TradeRule where trim(MarketCode)=?";
    if (getJdbcTemplate().queryForInt(sql, params) > 0) {
      throw new RuntimeException("此市场还有客户组交易规则，不能删除！");
    }
    sql = "delete from Market where trim(MarketCode)=?";
    
    this.log.debug("sql: " + sql);
    this.logger.debug("marketCode: " + params[0]);
    
    getJdbcTemplate().update(sql, params);
  }
  
  public String getMarketName(String marketID)
  {
    String sql = "select MarketName from marketcomparison where marketcode=?";
    
    Object[] params = { marketID };
    
    this.log.debug("sql: " + sql);
    this.log.debug("marketcode:" + params[0]);
    try
    {
      return (String)getJdbcTemplate().queryForObject(sql, params, String.class);
    }
    catch (IncorrectResultSizeDataAccessException e)
    {
      this.log.error("--marketid err:" + e.getMessage());
    }
    return null;
  }
  
  public String getMarketName()
  {
    String sql = "select MarketName from T_A_Market";
    
    Object[] params = new Object[0];
    
    this.log.debug("sql: " + sql);
    try
    {
      return (String)getJdbcTemplate().queryForObject(sql, params, String.class);
    }
    catch (IncorrectResultSizeDataAccessException e)
    {
      this.log.error("--marketid err:" + e.getMessage());
    }
    return null;
  }
  
  public void deleteMarket()
  {
    Object[] params = new Object[0];
    String sql = "select count(*) from Commodity";
    if (getJdbcTemplate().queryForInt(sql, params) > 0) {
      throw new RuntimeException("此市场还有商品，不能删除！");
    }
    sql = "select count(*) from CM_CustomerMap";
    if (getJdbcTemplate().queryForInt(sql, params) > 0) {
      throw new RuntimeException("此市场还有客户映射关系，不能删除！");
    }
    sql = "select count(*) from Suffix";
    if (getJdbcTemplate().queryForInt(sql, params) > 0) {
      throw new RuntimeException("此市场还有客户后缀，不能删除！");
    }
    sql = "select count(*) from CM_TradeRule";
    if (getJdbcTemplate().queryForInt(sql, params) > 0) {
      throw new RuntimeException("此市场还有客户交易规则，不能删除！");
    }
    sql = "select count(*) from GM_TradeRule";
    if (getJdbcTemplate().queryForInt(sql, params) > 0) {
      throw new RuntimeException("此市场还有客户组交易规则，不能删除！");
    }
    sql = "delete from Market";
    
    this.log.debug("sql: " + sql);
    
    getJdbcTemplate().update(sql, params);
  }
  
  public int checkMarket()
  {
    int result = 0;
    Object[] params = new Object[0];
    String sql = "select count(*) from Commodity";
    if (getJdbcTemplate().queryForInt(sql, params) > 0) {
      result = -1;
    }
    sql = "select count(*) from CM_CustomerMap";
    if (getJdbcTemplate().queryForInt(sql, params) > 0) {
      result = -2;
    }
    sql = "select count(*) from Suffix";
    if (getJdbcTemplate().queryForInt(sql, params) > 0) {
      result = -3;
    }
    sql = "select count(*) from CM_TradeRule";
    if (getJdbcTemplate().queryForInt(sql, params) > 0) {
      result = -4;
    }
    sql = "select count(*) from GM_TradeRule";
    if (getJdbcTemplate().queryForInt(sql, params) > 0) {
      result = -5;
    }
    return result;
  }
  
  class MarketRowMapper
    implements RowMapper
  {
    MarketRowMapper() {}
    
    public Object mapRow(ResultSet rs, int rowNum)
      throws SQLException
    {
      return rsToMarket(rs);
    }
    
    private Market rsToMarket(ResultSet rs)
      throws SQLException
    {
      Market m = new Market();
      m.setMarketCode(rs.getString("MarketCode"));
      m.setFirmID(rs.getString("FirmID"));
      m.setTradePassword(rs.getString("TradePassword"));
      m.setMarketName(rs.getString("MarketName"));
      m.setStatus(new Short(rs.getShort("Status")));
      m.setMarginFBFlag(new Short(rs.getShort("MarginFBFlag")));
      m.setShortName(rs.getString("ShortName"));
      m.setMarginPriceType(new Short(rs.getShort("MarginPriceType")));
      m.setFloatingLossComputeType(new Short(rs.getShort("FloatingLossComputeType")));
      m.setCommoditySetType(new Short(rs.getShort("CommoditySetType")));
      m.setUpToTime(rs.getString("endtime"));
      m.setIssueDate(rs.getString("issueDate"));
      m.setVerifyStr(rs.getString("verifyStr"));
      m.setRunMode(Short.valueOf(rs.getShort("RunMode")));
      m.setCloseAlgr(Short.valueOf(rs.getShort("CloseAlgr")));
      m.setAddedTax(Double.valueOf(rs.getDouble("AddedTax")));
      m.setSettlePriceType(new Short(rs.getShort("SettlePriceType")));
      m.setSettleMode(new Short(rs.getShort("SettleMode")));
      m.setBeforeDays(new Long(rs.getLong("BeforeDays")));
      return m;
    }
  }
  
  public SystemStatus getSystemStatus()
  {
    String sql = "select * from T_SYSTEMSTATUS";
    this.log.debug("sql: " + sql);
    List list = getJdbcTemplate().queryForList(sql);
    SystemStatus sys = new SystemStatus();
    String recoverTime = "";
    if ((list != null) && (list.size() > 0))
    {
      Map map = (Map)list.get(0);
      if (map.get("RecoverTime") != null)
      {
        recoverTime = map.get("RecoverTime").toString();
        sys.setRecoverTime(recoverTime);
      }
    }
    return sys;
  }
}

package gnnt.MEBS.timebargain.manage.dao.jdbc;

import gnnt.MEBS.delivery.model.Dealer;
import gnnt.MEBS.timebargain.manage.dao.TradeRuleDAO;
import gnnt.MEBS.timebargain.manage.model.TradeRule;
import gnnt.MEBS.timebargain.manage.model.TradeRuleGC;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.util.Assert;

public class TradeRuleDAOJdbc
  extends BaseDAOJdbc
  implements TradeRuleDAO
{
  private Log log = LogFactory.getLog(TradeRuleDAOJdbc.class);
  
  public TradeRuleGC getGM_TradeRuleById(Integer paramInteger, String paramString)
  {
    Assert.hasText(paramString);
    Assert.notNull(paramInteger);
    String str = "select * from T_A_gc_traderule where CommodityID=? and GroupID=?";
    Object[] arrayOfObject = { paramString, paramInteger };
    this.log.debug("sql: " + str);
    this.log.debug("marketCode:" + arrayOfObject[0]);
    this.log.debug("groupID:" + arrayOfObject[1]);
    TradeRuleGC localTradeRuleGC = null;
    try
    {
      localTradeRuleGC = (TradeRuleGC)getJdbcTemplate().queryForObject(str, arrayOfObject, new GM_TradeRuleRowMapper());
      return localTradeRuleGC;
    }
    catch (IncorrectResultSizeDataAccessException localIncorrectResultSizeDataAccessException)
    {
      throw new RuntimeException("GM_TradeRule表中商品ID[" + paramString + "]，客户组ID[" + paramInteger + "]不存在！");
    }
  }
  
  public List getGM_TradeRules(TradeRuleGC paramTradeRuleGC)
  {
    String str = "select a.GroupID,a.CommodityID,(case when a.FeeAlgr=2 then '绝对值' else '按百分比' end) FeeAlgr,a.FeeRate_B,a.FeeRate_S,(case when a.MarginAlgr=2 then '绝对值' else '按百分比' end) MarginAlgr,a.MarginRate_B,a.MarginRate_S,a.MarginAssure_B,a.MarginAssure_S,b.groupname,c.name from T_A_GC_TradeRule a,T_A_Firmgroup b,T_commodity c where a.groupID=b.groupID and c.CommodityID=a.CommodityID";
    this.log.debug("sql: " + str);
    return getJdbcTemplate().queryForList(str);
  }
  
  public void insertGM_TradeRule(TradeRuleGC paramTradeRuleGC)
  {
    String str = "insert into T_A_GC_TradeRule(GroupID,CommodityID,FeeAlgr,FeeRate_B,FeeRate_S,MarginAlgr,MarginRate_B,MarginRate_S,MarginItem1,MarginItem2,MarginItem3,MarginItem4,MarginItem1_S,MarginItem2_S,MarginItem3_S,MarginItem4_S,MarginAssure_B,MarginAssure_S,MarginItemAssure1,MarginItemAssure2,MarginItemAssure3,MarginItemAssure4,MarginItemAssure1_S,MarginItemAssure2_S,MarginItemAssure3_S,MarginItemAssure4_S,TodayCloseFeeRate_B,TodayCloseFeeRate_S,HistoryCloseFeeRate_B,HistoryCloseFeeRate_S,SettleFeeAlgr,SettleFeeRate_B,SettleFeeRate_S,ForceCloseFeeAlgr,ForceCloseFeeRate_B,ForceCloseFeeRate_S,SettleMarginRate_B,SettleMarginRate_S,settleMarginAlgr_B,settleMarginAlgr_S,PayoutAlgr,PayoutRate) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
    Object[] arrayOfObject = { paramTradeRuleGC.getGroupID(), paramTradeRuleGC.getCommodityID(), paramTradeRuleGC.getFeeAlgr(), paramTradeRuleGC.getFeeRate_B() == null ? new Double(0.0D) : paramTradeRuleGC.getFeeRate_B(), paramTradeRuleGC.getFeeRate_S() == null ? new Double(0.0D) : paramTradeRuleGC.getFeeRate_S(), paramTradeRuleGC.getMarginAlgr(), paramTradeRuleGC.getMarginRate_B() == null ? new Double(0.0D) : paramTradeRuleGC.getMarginRate_B(), paramTradeRuleGC.getMarginRate_S() == null ? new Double(0.0D) : paramTradeRuleGC.getMarginRate_S(), paramTradeRuleGC.getMarginItem1() == null ? new Double(0.0D) : paramTradeRuleGC.getMarginItem1(), paramTradeRuleGC.getMarginItem2() == null ? new Double(0.0D) : paramTradeRuleGC.getMarginItem2(), paramTradeRuleGC.getMarginItem3() == null ? new Double(0.0D) : paramTradeRuleGC.getMarginItem3(), paramTradeRuleGC.getMarginItem4() == null ? new Double(0.0D) : paramTradeRuleGC.getMarginItem4(), paramTradeRuleGC.getMarginItem1_S() == null ? new Double(0.0D) : paramTradeRuleGC.getMarginItem1_S(), paramTradeRuleGC.getMarginItem2_S() == null ? new Double(0.0D) : paramTradeRuleGC.getMarginItem2_S(), paramTradeRuleGC.getMarginItem3_S() == null ? new Double(0.0D) : paramTradeRuleGC.getMarginItem3_S(), paramTradeRuleGC.getMarginItem4_S() == null ? new Double(0.0D) : paramTradeRuleGC.getMarginItem4_S(), paramTradeRuleGC.getMarginAssure_B() == null ? new Double(0.0D) : paramTradeRuleGC.getMarginAssure_B(), paramTradeRuleGC.getMarginAssure_S() == null ? new Double(0.0D) : paramTradeRuleGC.getMarginAssure_S(), paramTradeRuleGC.getMarginItemAssure1() == null ? new Double(0.0D) : paramTradeRuleGC.getMarginItemAssure1(), paramTradeRuleGC.getMarginItemAssure2() == null ? new Double(0.0D) : paramTradeRuleGC.getMarginItemAssure2(), paramTradeRuleGC.getMarginItemAssure3() == null ? new Double(0.0D) : paramTradeRuleGC.getMarginItemAssure3(), paramTradeRuleGC.getMarginItemAssure4() == null ? new Double(0.0D) : paramTradeRuleGC.getMarginItemAssure4(), paramTradeRuleGC.getMarginItemAssure1_S() == null ? new Double(0.0D) : paramTradeRuleGC.getMarginItemAssure1_S(), paramTradeRuleGC.getMarginItemAssure2_S() == null ? new Double(0.0D) : paramTradeRuleGC.getMarginItemAssure2_S(), paramTradeRuleGC.getMarginItemAssure3_S() == null ? new Double(0.0D) : paramTradeRuleGC.getMarginItemAssure3_S(), paramTradeRuleGC.getMarginItemAssure4_S() == null ? new Double(0.0D) : paramTradeRuleGC.getMarginItemAssure4_S(), paramTradeRuleGC.getTodayCloseFeeRate_B() == null ? new Double(0.0D) : paramTradeRuleGC.getTodayCloseFeeRate_B(), paramTradeRuleGC.getTodayCloseFeeRate_S() == null ? new Double(0.0D) : paramTradeRuleGC.getTodayCloseFeeRate_S(), paramTradeRuleGC.getHistoryCloseFeeRate_B() == null ? new Double(0.0D) : paramTradeRuleGC.getHistoryCloseFeeRate_B(), paramTradeRuleGC.getHistoryCloseFeeRate_S() == null ? new Double(0.0D) : paramTradeRuleGC.getHistoryCloseFeeRate_S(), paramTradeRuleGC.getSettleFeeAlgr(), paramTradeRuleGC.getSettleFeeRate_B() == null ? new Double(0.0D) : paramTradeRuleGC.getSettleFeeRate_B(), paramTradeRuleGC.getSettleFeeRate_S() == null ? new Double(0.0D) : paramTradeRuleGC.getSettleFeeRate_S(), paramTradeRuleGC.getForceCloseFeeAlgr(), paramTradeRuleGC.getForceCloseFeeRate_B() == null ? new Double(0.0D) : paramTradeRuleGC.getForceCloseFeeRate_B(), paramTradeRuleGC.getForceCloseFeeRate_S() == null ? new Double(0.0D) : paramTradeRuleGC.getForceCloseFeeRate_S(), paramTradeRuleGC.getSettleMarginRate_B() == null ? new Double(0.0D) : paramTradeRuleGC.getSettleMarginRate_B(), paramTradeRuleGC.getSettleMarginRate_S() == null ? new Double(0.0D) : paramTradeRuleGC.getSettleMarginRate_S(), paramTradeRuleGC.getSettleMarginAlgr_B(), paramTradeRuleGC.getSettleMarginAlgr_S(), paramTradeRuleGC.getPayoutAlgr(), paramTradeRuleGC.getPayoutRate() == null ? new Double(0.0D) : paramTradeRuleGC.getPayoutRate() };
    this.log.debug("sql: " + str);
    for (int i = 0; i < arrayOfObject.length; i++) {
      this.log.debug("params[" + i + "]: " + arrayOfObject[i]);
    }
    try
    {
      getJdbcTemplate().update(str, arrayOfObject);
    }
    catch (DataIntegrityViolationException localDataIntegrityViolationException)
    {
      localDataIntegrityViolationException.printStackTrace();
      throw new RuntimeException("主键重复，不能插入相同的记录！");
    }
  }
  
  public void updateGM_TradeRule(TradeRuleGC paramTradeRuleGC)
  {
    String str = "update T_A_GC_TradeRule set FeeAlgr=?,FeeRate_B=?,FeeRate_S=?,MarginAlgr=?,MarginRate_B=?,MarginRate_S=?,MarginItem1=?,MarginItem2=?,MarginItem3=?,MarginItem4=?,MarginItem1_S=?,MarginItem2_S=?,MarginItem3_S=?,MarginItem4_S=?,MarginAssure_B=?,MarginAssure_S=?,MarginItemAssure1=?,MarginItemAssure2=?,MarginItemAssure3=?,MarginItemAssure4=?,MarginItemAssure1_S=?,MarginItemAssure2_S=?,MarginItemAssure3_S=?,MarginItemAssure4_S=?,TodayCloseFeeRate_B=?,TodayCloseFeeRate_S=?,HistoryCloseFeeRate_B=?,HistoryCloseFeeRate_S=?,SettleFeeAlgr=?,SettleFeeRate_B=?,SettleFeeRate_S=?,ForceCloseFeeAlgr=?,ForceCloseFeeRate_B=?,ForceCloseFeeRate_S=?,SettleMarginRate_B=?,SettleMarginRate_S=?,settleMarginAlgr_B=?,settleMarginAlgr_S=?,PayoutAlgr=?,PayoutRate=?  where CommodityID=? and GroupID=?";
    Object[] arrayOfObject = { paramTradeRuleGC.getFeeAlgr(), paramTradeRuleGC.getFeeRate_B() == null ? new Double(0.0D) : paramTradeRuleGC.getFeeRate_B(), paramTradeRuleGC.getFeeRate_S() == null ? new Double(0.0D) : paramTradeRuleGC.getFeeRate_S(), paramTradeRuleGC.getMarginAlgr(), paramTradeRuleGC.getMarginRate_B() == null ? new Double(0.0D) : paramTradeRuleGC.getMarginRate_B(), paramTradeRuleGC.getMarginRate_S() == null ? new Double(0.0D) : paramTradeRuleGC.getMarginRate_S(), paramTradeRuleGC.getMarginItem1() == null ? new Double(0.0D) : paramTradeRuleGC.getMarginItem1(), paramTradeRuleGC.getMarginItem2() == null ? new Double(0.0D) : paramTradeRuleGC.getMarginItem2(), paramTradeRuleGC.getMarginItem3() == null ? new Double(0.0D) : paramTradeRuleGC.getMarginItem3(), paramTradeRuleGC.getMarginItem4() == null ? new Double(0.0D) : paramTradeRuleGC.getMarginItem4(), paramTradeRuleGC.getMarginItem1_S() == null ? new Double(0.0D) : paramTradeRuleGC.getMarginItem1_S(), paramTradeRuleGC.getMarginItem2_S() == null ? new Double(0.0D) : paramTradeRuleGC.getMarginItem2_S(), paramTradeRuleGC.getMarginItem3_S() == null ? new Double(0.0D) : paramTradeRuleGC.getMarginItem3_S(), paramTradeRuleGC.getMarginItem4_S() == null ? new Double(0.0D) : paramTradeRuleGC.getMarginItem4_S(), paramTradeRuleGC.getMarginAssure_B() == null ? new Double(0.0D) : paramTradeRuleGC.getMarginAssure_B(), paramTradeRuleGC.getMarginAssure_S() == null ? new Double(0.0D) : paramTradeRuleGC.getMarginAssure_S(), paramTradeRuleGC.getMarginItemAssure1() == null ? new Double(0.0D) : paramTradeRuleGC.getMarginItemAssure1(), paramTradeRuleGC.getMarginItemAssure2() == null ? new Double(0.0D) : paramTradeRuleGC.getMarginItemAssure2(), paramTradeRuleGC.getMarginItemAssure3() == null ? new Double(0.0D) : paramTradeRuleGC.getMarginItemAssure3(), paramTradeRuleGC.getMarginItemAssure4() == null ? new Double(0.0D) : paramTradeRuleGC.getMarginItemAssure4(), paramTradeRuleGC.getMarginItemAssure1_S() == null ? new Double(0.0D) : paramTradeRuleGC.getMarginItemAssure1_S(), paramTradeRuleGC.getMarginItemAssure2_S() == null ? new Double(0.0D) : paramTradeRuleGC.getMarginItemAssure2_S(), paramTradeRuleGC.getMarginItemAssure3_S() == null ? new Double(0.0D) : paramTradeRuleGC.getMarginItemAssure3_S(), paramTradeRuleGC.getMarginItemAssure4_S() == null ? new Double(0.0D) : paramTradeRuleGC.getMarginItemAssure4_S(), paramTradeRuleGC.getTodayCloseFeeRate_B() == null ? new Double(0.0D) : paramTradeRuleGC.getTodayCloseFeeRate_B(), paramTradeRuleGC.getTodayCloseFeeRate_S() == null ? new Double(0.0D) : paramTradeRuleGC.getTodayCloseFeeRate_S(), paramTradeRuleGC.getHistoryCloseFeeRate_B() == null ? new Double(0.0D) : paramTradeRuleGC.getHistoryCloseFeeRate_B(), paramTradeRuleGC.getHistoryCloseFeeRate_S() == null ? new Double(0.0D) : paramTradeRuleGC.getHistoryCloseFeeRate_S(), paramTradeRuleGC.getSettleFeeAlgr(), paramTradeRuleGC.getSettleFeeRate_B() == null ? new Double(0.0D) : paramTradeRuleGC.getSettleFeeRate_B(), paramTradeRuleGC.getSettleFeeRate_S() == null ? new Double(0.0D) : paramTradeRuleGC.getSettleFeeRate_S(), paramTradeRuleGC.getForceCloseFeeAlgr(), paramTradeRuleGC.getForceCloseFeeRate_B() == null ? new Double(0.0D) : paramTradeRuleGC.getForceCloseFeeRate_B(), paramTradeRuleGC.getForceCloseFeeRate_S() == null ? new Double(0.0D) : paramTradeRuleGC.getForceCloseFeeRate_S(), paramTradeRuleGC.getSettleMarginRate_B() == null ? new Double(0.0D) : paramTradeRuleGC.getSettleMarginRate_B(), paramTradeRuleGC.getSettleMarginRate_S() == null ? new Double(0.0D) : paramTradeRuleGC.getSettleMarginRate_S(), paramTradeRuleGC.getSettleMarginAlgr_B(), paramTradeRuleGC.getSettleMarginAlgr_S(), paramTradeRuleGC.getPayoutAlgr(), paramTradeRuleGC.getPayoutRate() == null ? new Double(0.0D) : paramTradeRuleGC.getPayoutRate(), paramTradeRuleGC.getCommodityID(), paramTradeRuleGC.getGroupID() };
    this.log.debug("sql: " + str);
    for (int i = 0; i < arrayOfObject.length; i++) {
      this.log.debug("params[" + i + "]: " + arrayOfObject[i]);
    }
    try
    {
      getJdbcTemplate().update(str, arrayOfObject);
    }
    catch (DataIntegrityViolationException localDataIntegrityViolationException)
    {
      throw new RuntimeException("主键重复，不能插入相同的记录！");
    }
  }
  
  public void deleteGM_TradeRuleById(String paramString, Integer paramInteger)
  {
    Assert.hasText(paramString);
    Assert.hasText(paramInteger.toString());
    String str = "delete from T_A_GC_TradeRule where CommodityID=? and groupID=?";
    Object[] arrayOfObject = { paramString, paramInteger };
    this.log.debug("sql: " + str);
    this.log.debug("uni_Cmdty_Code:" + arrayOfObject[0]);
    this.log.debug("groupID:" + arrayOfObject[1]);
    getJdbcTemplate().update(str, arrayOfObject);
  }
  
  public void updateCM_TradeRule(TradeRule paramTradeRule)
  {
    String str = "update T_CM_TradeRule set FeeDiscountRate=?,MarginDiscountRate=? where  CustomerID=?";
    Object[] arrayOfObject = { paramTradeRule.getFeeDiscountRate() == null ? new Double(0.0D) : paramTradeRule.getFeeDiscountRate(), paramTradeRule.getMarginDiscountRate() == null ? new Double(0.0D) : paramTradeRule.getMarginDiscountRate(), paramTradeRule.getCustomerID() };
    this.log.debug("sql: " + str);
    for (int i = 0; i < arrayOfObject.length; i++) {
      this.log.debug("params[" + i + "]: " + arrayOfObject[i]);
    }
    getJdbcTemplate().update(str, arrayOfObject);
  }
  
  public void deleteCM_TradeRuleById(String paramString1, String paramString2)
  {
    Assert.hasText(paramString1);
    Assert.hasText(paramString2);
    String str = "delete from CM_TradeRule where  CustomerID=?";
    Object[] arrayOfObject = { paramString2 };
    this.log.debug("sql: " + str);
    this.log.debug("marketCode:" + arrayOfObject[0]);
    this.log.debug("customerID:" + arrayOfObject[1]);
    getJdbcTemplate().update(str, arrayOfObject);
  }
  
  public List getFirmMargin()
  {
    String str = "select a.modifyTime,a.firmid,a.commodityid,a.marginalgr,decode(a.marginalgr,1,a.marginrate_b*100||'%',2,a.marginrate_b) marginrate_b,decode(a.marginalgr,1,a.marginrate_s*100||'%',2,a.marginrate_s) marginrate_s,decode(a.marginalgr,1,a.marginAssure_B*100||'%',2,a.marginAssure_B) marginAssure_B,decode(a.marginalgr,1,a.marginAssure_S*100||'%',2,a.marginAssure_S) marginAssure_S from T_A_FirmMargin a";
    return getJdbcTemplate().queryForList(str);
  }
  
  public TradeRuleGC getFirmMarginById(String paramString1, String paramString2)
  {
    String str = "select a.* from T_A_FirmMargin a where a.firmID = ? and a.commodityID = ?";
    Object[] arrayOfObject = { paramString1, paramString2 };
    this.log.debug("sql: " + str);
    for (int i = 0; i < arrayOfObject.length; i++) {
      this.log.debug("params[" + i + "]: " + arrayOfObject[i]);
    }
    return (TradeRuleGC)getJdbcTemplate().queryForObject(str, arrayOfObject, new FirmMarginRowMapper());
  }
  
  public void insertFirmMargin(TradeRuleGC paramTradeRuleGC)
  {
    String str = "insert into T_A_FirmMargin (FirmID,CommodityID,MarginAlgr,MarginItem1,MarginItem2,MarginItem3,MarginItem4,MarginItem1_S,MarginItem2_S,MarginItem3_S,MarginItem4_S,MarginItemAssure1,MarginItemAssure2,MarginItemAssure3,MarginItemAssure4,MarginItemAssure1_S,MarginItemAssure2_S,MarginItemAssure3_S,MarginItemAssure4_S,SettleMarginAlgr_B,SettleMarginAlgr_S,SettleMarginRate_B,SettleMarginRate_S,PayoutAlgr,PayoutRate,MarginItem5,MarginItem5_S,MarginItemAssure5,MarginItemAssure5_S,ModifyTime) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,sysdate)";
    Object[] arrayOfObject = { paramTradeRuleGC.getFirmID(), paramTradeRuleGC.getCommodityID(), paramTradeRuleGC.getMarginAlgr(), paramTradeRuleGC.getMarginItem1() == null ? new Double(0.0D) : paramTradeRuleGC.getMarginItem1(), paramTradeRuleGC.getMarginItem2() == null ? new Double(0.0D) : paramTradeRuleGC.getMarginItem2(), paramTradeRuleGC.getMarginItem3() == null ? new Double(0.0D) : paramTradeRuleGC.getMarginItem3(), paramTradeRuleGC.getMarginItem4() == null ? new Double(0.0D) : paramTradeRuleGC.getMarginItem4(), paramTradeRuleGC.getMarginItem1_S() == null ? new Double(0.0D) : paramTradeRuleGC.getMarginItem1_S(), paramTradeRuleGC.getMarginItem2_S() == null ? new Double(0.0D) : paramTradeRuleGC.getMarginItem2_S(), paramTradeRuleGC.getMarginItem3_S() == null ? new Double(0.0D) : paramTradeRuleGC.getMarginItem3_S(), paramTradeRuleGC.getMarginItem4_S() == null ? new Double(0.0D) : paramTradeRuleGC.getMarginItem4_S(), paramTradeRuleGC.getMarginItemAssure1() == null ? new Double(0.0D) : paramTradeRuleGC.getMarginItemAssure1(), paramTradeRuleGC.getMarginItemAssure2() == null ? new Double(0.0D) : paramTradeRuleGC.getMarginItemAssure2(), paramTradeRuleGC.getMarginItemAssure3() == null ? new Double(0.0D) : paramTradeRuleGC.getMarginItemAssure3(), paramTradeRuleGC.getMarginItemAssure4() == null ? new Double(0.0D) : paramTradeRuleGC.getMarginItemAssure4(), paramTradeRuleGC.getMarginItemAssure1_S() == null ? new Double(0.0D) : paramTradeRuleGC.getMarginItemAssure1_S(), paramTradeRuleGC.getMarginItemAssure2_S() == null ? new Double(0.0D) : paramTradeRuleGC.getMarginItemAssure2_S(), paramTradeRuleGC.getMarginItemAssure3_S() == null ? new Double(0.0D) : paramTradeRuleGC.getMarginItemAssure3_S(), paramTradeRuleGC.getMarginItemAssure4_S() == null ? new Double(0.0D) : paramTradeRuleGC.getMarginItemAssure4_S(), paramTradeRuleGC.getSettleMarginAlgr_B(), paramTradeRuleGC.getSettleMarginAlgr_S(), paramTradeRuleGC.getSettleMarginRate_B() == null ? new Double(0.0D) : paramTradeRuleGC.getSettleMarginRate_B(), paramTradeRuleGC.getSettleMarginRate_S() == null ? new Double(0.0D) : paramTradeRuleGC.getSettleMarginRate_S(), paramTradeRuleGC.getPayoutAlgr(), paramTradeRuleGC.getPayoutRate() == null ? new Double(0.0D) : paramTradeRuleGC.getPayoutRate(), paramTradeRuleGC.getMarginItem5() == null ? new Double(0.0D) : paramTradeRuleGC.getMarginItem5(), paramTradeRuleGC.getMarginItem5_S() == null ? new Double(0.0D) : paramTradeRuleGC.getMarginItem5_S(), paramTradeRuleGC.getMarginItemAssure5() == null ? new Double(0.0D) : paramTradeRuleGC.getMarginItemAssure5(), paramTradeRuleGC.getMarginItemAssure5_S() == null ? new Double(0.0D) : paramTradeRuleGC.getMarginItemAssure5_S() };
    this.log.debug("sql: " + str);
    for (int i = 0; i < arrayOfObject.length; i++) {
      this.log.debug("params[" + i + "]: " + arrayOfObject[i]);
    }
    try
    {
      getJdbcTemplate().update(str, arrayOfObject);
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      throw new RuntimeException("添加失败！");
    }
  }
  
  public void updateFirmMargin(TradeRuleGC paramTradeRuleGC)
  {
    String str = "update T_A_FirmMargin set MarginAlgr=?,MarginItem1=?,MarginItem2=?,MarginItem3=?,MarginItem4=?,MarginItem1_S=?,MarginItem2_S=?,MarginItem3_S=?,MarginItem4_S=?,MarginItemAssure1=?,MarginItemAssure2=?,MarginItemAssure3=?,MarginItemAssure4=?,MarginItemAssure1_S=?,MarginItemAssure2_S=?,MarginItemAssure3_S=?,MarginItemAssure4_S=?,SettleMarginAlgr_B=?,SettleMarginAlgr_S=?,SettleMarginRate_B=?,SettleMarginRate_S=?,PayoutAlgr=?,PayoutRate=?,ModifyTime=sysdate,MarginItem5=?,MarginItem5_S=?,MarginItemAssure5=?,MarginItemAssure5_S=? where FirmID = ? and CommodityID = ?";
    Object[] arrayOfObject = { paramTradeRuleGC.getMarginAlgr(), paramTradeRuleGC.getMarginItem1() == null ? new Double(0.0D) : paramTradeRuleGC.getMarginItem1(), paramTradeRuleGC.getMarginItem2() == null ? new Double(0.0D) : paramTradeRuleGC.getMarginItem2(), paramTradeRuleGC.getMarginItem3() == null ? new Double(0.0D) : paramTradeRuleGC.getMarginItem3(), paramTradeRuleGC.getMarginItem4() == null ? new Double(0.0D) : paramTradeRuleGC.getMarginItem4(), paramTradeRuleGC.getMarginItem1_S() == null ? new Double(0.0D) : paramTradeRuleGC.getMarginItem1_S(), paramTradeRuleGC.getMarginItem2_S() == null ? new Double(0.0D) : paramTradeRuleGC.getMarginItem2_S(), paramTradeRuleGC.getMarginItem3_S() == null ? new Double(0.0D) : paramTradeRuleGC.getMarginItem3_S(), paramTradeRuleGC.getMarginItem4_S() == null ? new Double(0.0D) : paramTradeRuleGC.getMarginItem4_S(), paramTradeRuleGC.getMarginItemAssure1() == null ? new Double(0.0D) : paramTradeRuleGC.getMarginItemAssure1(), paramTradeRuleGC.getMarginItemAssure2() == null ? new Double(0.0D) : paramTradeRuleGC.getMarginItemAssure2(), paramTradeRuleGC.getMarginItemAssure3() == null ? new Double(0.0D) : paramTradeRuleGC.getMarginItemAssure3(), paramTradeRuleGC.getMarginItemAssure4() == null ? new Double(0.0D) : paramTradeRuleGC.getMarginItemAssure4(), paramTradeRuleGC.getMarginItemAssure1_S() == null ? new Double(0.0D) : paramTradeRuleGC.getMarginItemAssure1_S(), paramTradeRuleGC.getMarginItemAssure2_S() == null ? new Double(0.0D) : paramTradeRuleGC.getMarginItemAssure2_S(), paramTradeRuleGC.getMarginItemAssure3_S() == null ? new Double(0.0D) : paramTradeRuleGC.getMarginItemAssure3_S(), paramTradeRuleGC.getMarginItemAssure4_S() == null ? new Double(0.0D) : paramTradeRuleGC.getMarginItemAssure4_S(), paramTradeRuleGC.getSettleMarginAlgr_B(), paramTradeRuleGC.getSettleMarginAlgr_S(), paramTradeRuleGC.getSettleMarginRate_B() == null ? new Double(0.0D) : paramTradeRuleGC.getSettleMarginRate_B(), paramTradeRuleGC.getSettleMarginRate_S() == null ? new Double(0.0D) : paramTradeRuleGC.getSettleMarginRate_S(), paramTradeRuleGC.getPayoutAlgr(), paramTradeRuleGC.getPayoutRate() == null ? new Double(0.0D) : paramTradeRuleGC.getPayoutRate(), paramTradeRuleGC.getMarginItem5() == null ? new Double(0.0D) : paramTradeRuleGC.getMarginItem5(), paramTradeRuleGC.getMarginItem5_S() == null ? new Double(0.0D) : paramTradeRuleGC.getMarginItem5_S(), paramTradeRuleGC.getMarginItemAssure5() == null ? new Double(0.0D) : paramTradeRuleGC.getMarginItemAssure5(), paramTradeRuleGC.getMarginItemAssure5_S() == null ? new Double(0.0D) : paramTradeRuleGC.getMarginItemAssure5_S(), paramTradeRuleGC.getFirmID(), paramTradeRuleGC.getCommodityID() };
    this.log.debug("sql: " + str);
    for (int i = 0; i < arrayOfObject.length; i++) {
      this.log.debug("params[" + i + "]: " + arrayOfObject[i]);
    }
    try
    {
      getJdbcTemplate().update(str, arrayOfObject);
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      throw new RuntimeException("修改失败！");
    }
  }
  
  public void deleteFirmMarginById(String paramString1, String paramString2)
  {
    String str = "delete from T_A_FirmMargin where firmID = ? and commodityID = ?";
    Object[] arrayOfObject = { paramString1, paramString2 };
    this.log.debug("sql: " + str);
    for (int i = 0; i < arrayOfObject.length; i++) {
      this.log.debug("params[" + i + "]: " + arrayOfObject[i]);
    }
    try
    {
      getJdbcTemplate().update(str, arrayOfObject);
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      throw new RuntimeException("删除失败！");
    }
  }
  
  public List getFirmFee()
  {
    String str = "select a.* from T_A_FirmFee a";
    return getJdbcTemplate().queryForList(str);
  }
  
  public TradeRuleGC getFirmFeeById(String paramString1, String paramString2)
  {
    String str = "select a.* from T_A_FirmFee a where firmID = ? and CommodityID = ?";
    Object[] arrayOfObject = { paramString1, paramString2 };
    this.log.debug("sql: " + str);
    for (int i = 0; i < arrayOfObject.length; i++) {
      this.log.debug("params[" + i + "]: " + arrayOfObject[i]);
    }
    try
    {
      return (TradeRuleGC)getJdbcTemplate().queryForObject(str, arrayOfObject, new FirmFeeRowMapper());
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      throw new RuntimeException("查询对象失败！");
    }
  }
  
  public void insertFirmFee(TradeRuleGC paramTradeRuleGC)
  {
    String str = "insert into T_A_FirmFee (FirmID,CommodityID,FeeAlgr,FeeRate_B,FeeRate_S,TodayCloseFeeRate_B,TodayCloseFeeRate_S,HistoryCloseFeeRate_B,HistoryCloseFeeRate_S,SettleFeeAlgr,SettleFeeRate_B,SettleFeeRate_S,ForceCloseFeeAlgr,ForceCloseFeeRate_B,ForceCloseFeeRate_S,ModifyTime) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,sysdate)";
    Object[] arrayOfObject = { paramTradeRuleGC.getFirmID(), paramTradeRuleGC.getCommodityID(), paramTradeRuleGC.getFeeAlgr(), paramTradeRuleGC.getFeeRate_B() == null ? new Double(0.0D) : paramTradeRuleGC.getFeeRate_B(), paramTradeRuleGC.getFeeRate_S() == null ? new Double(0.0D) : paramTradeRuleGC.getFeeRate_S(), paramTradeRuleGC.getTodayCloseFeeRate_B() == null ? new Double(0.0D) : paramTradeRuleGC.getTodayCloseFeeRate_B(), paramTradeRuleGC.getTodayCloseFeeRate_S() == null ? new Double(0.0D) : paramTradeRuleGC.getTodayCloseFeeRate_S(), paramTradeRuleGC.getHistoryCloseFeeRate_B() == null ? new Double(0.0D) : paramTradeRuleGC.getHistoryCloseFeeRate_B(), paramTradeRuleGC.getHistoryCloseFeeRate_S() == null ? new Double(0.0D) : paramTradeRuleGC.getHistoryCloseFeeRate_S(), paramTradeRuleGC.getSettleFeeAlgr(), paramTradeRuleGC.getSettleFeeRate_B() == null ? new Double(0.0D) : paramTradeRuleGC.getSettleFeeRate_B(), paramTradeRuleGC.getSettleFeeRate_S() == null ? new Double(0.0D) : paramTradeRuleGC.getSettleFeeRate_S(), paramTradeRuleGC.getForceCloseFeeAlgr(), paramTradeRuleGC.getForceCloseFeeRate_B() == null ? new Double(0.0D) : paramTradeRuleGC.getForceCloseFeeRate_B(), paramTradeRuleGC.getForceCloseFeeRate_S() == null ? new Double(0.0D) : paramTradeRuleGC.getForceCloseFeeRate_S() };
    this.log.debug("sql: " + str);
    for (int i = 0; i < arrayOfObject.length; i++) {
      this.log.debug("params[" + i + "]: " + arrayOfObject[i]);
    }
    try
    {
      getJdbcTemplate().update(str, arrayOfObject);
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      throw new RuntimeException("添加特殊手续费失败！");
    }
  }
  
  public void updateFirmFee(TradeRuleGC paramTradeRuleGC)
  {
    String str = "update T_A_FirmFee set FeeAlgr=?,FeeRate_B=?,FeeRate_S=?,TodayCloseFeeRate_B=?,TodayCloseFeeRate_S=?,HistoryCloseFeeRate_B=?,HistoryCloseFeeRate_S=?,SettleFeeAlgr=?,SettleFeeRate_B=?,SettleFeeRate_S=?,ForceCloseFeeAlgr=?,ForceCloseFeeRate_B=?,ForceCloseFeeRate_S=?,ModifyTime=sysdate where FirmID = ? and CommodityID = ?";
    Object[] arrayOfObject = { paramTradeRuleGC.getFeeAlgr(), paramTradeRuleGC.getFeeRate_B() == null ? new Double(0.0D) : paramTradeRuleGC.getFeeRate_B(), paramTradeRuleGC.getFeeRate_S() == null ? new Double(0.0D) : paramTradeRuleGC.getFeeRate_S(), paramTradeRuleGC.getTodayCloseFeeRate_B() == null ? new Double(0.0D) : paramTradeRuleGC.getTodayCloseFeeRate_B(), paramTradeRuleGC.getTodayCloseFeeRate_S() == null ? new Double(0.0D) : paramTradeRuleGC.getTodayCloseFeeRate_S(), paramTradeRuleGC.getHistoryCloseFeeRate_B() == null ? new Double(0.0D) : paramTradeRuleGC.getHistoryCloseFeeRate_B(), paramTradeRuleGC.getHistoryCloseFeeRate_S() == null ? new Double(0.0D) : paramTradeRuleGC.getHistoryCloseFeeRate_S(), paramTradeRuleGC.getSettleFeeAlgr(), paramTradeRuleGC.getSettleFeeRate_B() == null ? new Double(0.0D) : paramTradeRuleGC.getSettleFeeRate_B(), paramTradeRuleGC.getSettleFeeRate_S() == null ? new Double(0.0D) : paramTradeRuleGC.getSettleFeeRate_S(), paramTradeRuleGC.getForceCloseFeeAlgr(), paramTradeRuleGC.getForceCloseFeeRate_B() == null ? new Double(0.0D) : paramTradeRuleGC.getForceCloseFeeRate_B(), paramTradeRuleGC.getForceCloseFeeRate_S() == null ? new Double(0.0D) : paramTradeRuleGC.getForceCloseFeeRate_S(), paramTradeRuleGC.getFirmID(), paramTradeRuleGC.getCommodityID() };
    this.log.debug("sql: " + str);
    for (int i = 0; i < arrayOfObject.length; i++) {
      this.log.debug("params[" + i + "]: " + arrayOfObject[i]);
    }
    try
    {
      getJdbcTemplate().update(str, arrayOfObject);
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      throw new RuntimeException("修改特殊手续费失败！");
    }
  }
  
  public void deleteFirmFeeById(String paramString1, String paramString2)
  {
    String str = "delete from T_A_FirmFee where firmID = ? and CommodityID = ?";
    Object[] arrayOfObject = { paramString1, paramString2 };
    this.log.debug("sql: " + str);
    for (int i = 0; i < arrayOfObject.length; i++) {
      this.log.debug("params[" + i + "]: " + arrayOfObject[i]);
    }
    try
    {
      getJdbcTemplate().update(str, arrayOfObject);
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      throw new RuntimeException("删除特殊手续费失败！");
    }
  }
  
  public List getFirmMaxHoldQty()
  {
    String str = "select a.*,case when a.maxholdqty = -1 then '无限制' else a.maxholdqty||'' end as maxholdqty,case when a.cleanMaxHoldQty = -1 then '无限制' else a.cleanMaxHoldQty||'' end as cleanMaxHoldQty from T_A_FirmMaxHoldQty a";
    return getJdbcTemplate().queryForList(str);
  }
  
  public List getFirmMaxHoldQtyByFirmId(String paramString)
  {
    String str = "select a.*,case when a.maxholdqty = -1 then '无限制' else a.maxholdqty||'' end as maxholdqty,case when a.cleanMaxHoldQty = -1 then '无限制' else a.cleanMaxHoldQty||'' end as cleanMaxHoldQty from T_A_FirmMaxHoldQty a where firmid=?";
    Object[] arrayOfObject = { paramString };
    return getJdbcTemplate().queryForList(str, arrayOfObject);
  }
  
  public TradeRuleGC getFirmMaxHoldQtyById(String paramString1, String paramString2)
  {
    String str = "select a.* from T_A_FirmMaxHoldQty a where firmID = ? and CommodityID = ?";
    Object[] arrayOfObject = { paramString1, paramString2 };
    this.log.debug("sql: " + str);
    for (int i = 0; i < arrayOfObject.length; i++) {
      this.log.debug("params[" + i + "]: " + arrayOfObject[i]);
    }
    try
    {
      return (TradeRuleGC)getJdbcTemplate().queryForObject(str, arrayOfObject, new FirmMaxHoldQtyRowMapper());
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      throw new RuntimeException("删除特殊手续费失败！");
    }
  }
  
  public void insertFirmMaxHoldQty(TradeRuleGC paramTradeRuleGC)
  {
    String str = "insert into T_A_FirmMaxHoldQty (FirmID,CommodityID,MaxHoldQty,CleanMaxHoldQty,ModifyTime) values (?,?,?,?,sysdate)";
    Object[] arrayOfObject = { paramTradeRuleGC.getFirmID(), paramTradeRuleGC.getCommodityID(), paramTradeRuleGC.getMaxHoldQty(), paramTradeRuleGC.getCleanMaxHoldQty() };
    this.log.debug("sql: " + str);
    for (int i = 0; i < arrayOfObject.length; i++) {
      this.log.debug("params[" + i + "]: " + arrayOfObject[i]);
    }
    try
    {
      getJdbcTemplate().update(str, arrayOfObject);
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      throw new RuntimeException("添加特殊订货量失败！");
    }
  }
  
  public void updateFirmMaxHoldQty(TradeRuleGC paramTradeRuleGC)
  {
    String str = "update T_A_FirmMaxHoldQty set MaxHoldQty = ?, CleanMaxHoldQty = ?, ModifyTime = sysdate where FirmID = ? and CommodityID = ?";
    Object[] arrayOfObject = { paramTradeRuleGC.getMaxHoldQty(), paramTradeRuleGC.getCleanMaxHoldQty(), paramTradeRuleGC.getFirmID(), paramTradeRuleGC.getCommodityID() };
    this.log.debug("sql: " + str);
    for (int i = 0; i < arrayOfObject.length; i++) {
      this.log.debug("params[" + i + "]: " + arrayOfObject[i]);
    }
    try
    {
      getJdbcTemplate().update(str, arrayOfObject);
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      throw new RuntimeException("修改特殊订货量失败！");
    }
  }
  
  public void deleteFirmMaxHoldQtyById(String paramString1, String paramString2)
  {
    String str = "delete from T_A_FirmMaxHoldQty where firmID = ? and CommodityID = ?";
    Object[] arrayOfObject = { paramString1, paramString2 };
    this.log.debug("sql: " + str);
    for (int i = 0; i < arrayOfObject.length; i++) {
      this.log.debug("params[" + i + "]: " + arrayOfObject[i]);
    }
    try
    {
      getJdbcTemplate().update(str, arrayOfObject);
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      throw new RuntimeException("删除特殊订货量失败！");
    }
  }
  
  public void updateSpacMargin(TradeRuleGC paramTradeRuleGC)
  {
    String str = "select count(*) from T_A_FirmMargin where firmID = '" + paramTradeRuleGC.getFirmID() + "'";
    if (getJdbcTemplate().queryForInt(str) < 1) {
      throw new RuntimeException("此交易商不存在！");
    }
    str = "update T_A_FirmMargin set marginRate_B=?, marginRate_S=?, marginAssure_B=?, marginAssure_S=? where firmID = ? and commodityID = ?";
    Object[] arrayOfObject = { paramTradeRuleGC.getMarginRate_B(), paramTradeRuleGC.getMarginRate_S(), paramTradeRuleGC.getMarginAssure_B(), paramTradeRuleGC.getMarginAssure_S(), paramTradeRuleGC.getFirmID(), paramTradeRuleGC.getCommodityID() };
    try
    {
      getJdbcTemplate().update(str, arrayOfObject);
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      throw new RuntimeException("此交易商不存在！");
    }
  }
  
  public Dealer getDealerByfirmId(String paramString)
  {
    String str = "select t.firmid,t.name,t.contactman linkman,t.phone tel from m_firm t where firmId=? ";
    Object[] arrayOfObject = { paramString };
    Dealer localDealer = null;
    List localList = (List)getJdbcTemplate().queryForObject(str, arrayOfObject, new FirmMarginRowMapper());
    if (localList.size() > 0) {
      localDealer = (Dealer)localList.get(0);
    }
    return localDealer;
  }
  
  class FirmMaxHoldQtyRowMapper
    implements RowMapper
  {
    FirmMaxHoldQtyRowMapper() {}
    
    public Object mapRow(ResultSet paramResultSet, int paramInt)
      throws SQLException
    {
      return rsToTradeRule(paramResultSet);
    }
    
    private TradeRuleGC rsToTradeRule(ResultSet paramResultSet)
      throws SQLException
    {
      TradeRuleGC localTradeRuleGC = new TradeRuleGC();
      localTradeRuleGC.setCommodityID(paramResultSet.getString("CommodityID"));
      localTradeRuleGC.setFirmID(paramResultSet.getString("FirmID"));
      localTradeRuleGC.setMaxHoldQty(Long.valueOf(paramResultSet.getLong("MaxHoldQty")));
      localTradeRuleGC.setCleanMaxHoldQty(Long.valueOf(paramResultSet.getLong("CleanMaxHoldQty")));
      return localTradeRuleGC;
    }
  }
  
  class FirmFeeRowMapper
    implements RowMapper
  {
    FirmFeeRowMapper() {}
    
    public Object mapRow(ResultSet paramResultSet, int paramInt)
      throws SQLException
    {
      return rsToTradeRule(paramResultSet);
    }
    
    private TradeRuleGC rsToTradeRule(ResultSet paramResultSet)
      throws SQLException
    {
      TradeRuleGC localTradeRuleGC = new TradeRuleGC();
      localTradeRuleGC.setCommodityID(paramResultSet.getString("CommodityID"));
      localTradeRuleGC.setFirmID(paramResultSet.getString("FirmID"));
      localTradeRuleGC.setFeeAlgr(new Short(paramResultSet.getShort("FeeAlgr")));
      localTradeRuleGC.setFeeRate_B(new Double(paramResultSet.getDouble("FeeRate_B")));
      localTradeRuleGC.setFeeRate_S(new Double(paramResultSet.getDouble("FeeRate_S")));
      localTradeRuleGC.setTodayCloseFeeRate_B(new Double(paramResultSet.getDouble("TodayCloseFeeRate_B")));
      localTradeRuleGC.setTodayCloseFeeRate_S(new Double(paramResultSet.getDouble("TodayCloseFeeRate_S")));
      localTradeRuleGC.setHistoryCloseFeeRate_B(new Double(paramResultSet.getDouble("HistoryCloseFeeRate_B")));
      localTradeRuleGC.setHistoryCloseFeeRate_S(new Double(paramResultSet.getDouble("HistoryCloseFeeRate_S")));
      localTradeRuleGC.setSettleFeeAlgr(new Short(paramResultSet.getShort("SettleFeeAlgr")));
      localTradeRuleGC.setSettleFeeRate_B(new Double(paramResultSet.getDouble("SettleFeeRate_B")));
      localTradeRuleGC.setSettleFeeRate_S(new Double(paramResultSet.getDouble("SettleFeeRate_S")));
      localTradeRuleGC.setForceCloseFeeAlgr(new Short(paramResultSet.getShort("ForceCloseFeeAlgr")));
      localTradeRuleGC.setForceCloseFeeRate_B(new Double(paramResultSet.getDouble("ForceCloseFeeRate_B")));
      localTradeRuleGC.setForceCloseFeeRate_S(new Double(paramResultSet.getDouble("ForceCloseFeeRate_S")));
      return localTradeRuleGC;
    }
  }
  
  class FirmMarginRowMapper
    implements RowMapper
  {
    FirmMarginRowMapper() {}
    
    public Object mapRow(ResultSet paramResultSet, int paramInt)
      throws SQLException
    {
      return rsToTradeRule(paramResultSet);
    }
    
    private TradeRuleGC rsToTradeRule(ResultSet paramResultSet)
      throws SQLException
    {
      TradeRuleGC localTradeRuleGC = new TradeRuleGC();
      localTradeRuleGC.setCommodityID(paramResultSet.getString("CommodityID"));
      localTradeRuleGC.setFirmID(paramResultSet.getString("FirmID"));
      localTradeRuleGC.setMarginAlgr(new Short(paramResultSet.getShort("MarginAlgr")));
      localTradeRuleGC.setMarginRate_B(new Double(paramResultSet.getDouble("MarginRate_B")));
      localTradeRuleGC.setMarginRate_S(new Double(paramResultSet.getDouble("MarginRate_S")));
      localTradeRuleGC.setMarginItem1(new Double(paramResultSet.getDouble("MarginItem1")));
      localTradeRuleGC.setMarginItem2(new Double(paramResultSet.getDouble("MarginItem2")));
      localTradeRuleGC.setMarginItem3(new Double(paramResultSet.getDouble("MarginItem3")));
      localTradeRuleGC.setMarginItem4(new Double(paramResultSet.getDouble("MarginItem4")));
      localTradeRuleGC.setMarginItem5(new Double(paramResultSet.getDouble("MarginItem5")));
      localTradeRuleGC.setMarginItem1_S(new Double(paramResultSet.getDouble("MarginItem1_S")));
      localTradeRuleGC.setMarginItem2_S(new Double(paramResultSet.getDouble("MarginItem2_S")));
      localTradeRuleGC.setMarginItem3_S(new Double(paramResultSet.getDouble("MarginItem3_S")));
      localTradeRuleGC.setMarginItem4_S(new Double(paramResultSet.getDouble("MarginItem4_S")));
      localTradeRuleGC.setMarginItem5_S(new Double(paramResultSet.getDouble("MarginItem5_S")));
      localTradeRuleGC.setMarginAssure_B(new Double(paramResultSet.getDouble("marginAssure_B")));
      localTradeRuleGC.setMarginAssure_S(new Double(paramResultSet.getDouble("marginAssure_S")));
      localTradeRuleGC.setMarginItemAssure1(new Double(paramResultSet.getDouble("marginItemAssure1")));
      localTradeRuleGC.setMarginItemAssure1_S(new Double(paramResultSet.getDouble("marginItemAssure1_S")));
      localTradeRuleGC.setMarginItemAssure2(new Double(paramResultSet.getDouble("marginItemAssure2")));
      localTradeRuleGC.setMarginItemAssure2_S(new Double(paramResultSet.getDouble("marginItemAssure2_S")));
      localTradeRuleGC.setMarginItemAssure3(new Double(paramResultSet.getDouble("marginItemAssure3")));
      localTradeRuleGC.setMarginItemAssure3_S(new Double(paramResultSet.getDouble("marginItemAssure3_S")));
      localTradeRuleGC.setMarginItemAssure4(new Double(paramResultSet.getDouble("marginItemAssure4")));
      localTradeRuleGC.setMarginItemAssure4_S(new Double(paramResultSet.getDouble("marginItemAssure4_S")));
      localTradeRuleGC.setMarginItemAssure5(new Double(paramResultSet.getDouble("marginItemAssure5")));
      localTradeRuleGC.setMarginItemAssure5_S(new Double(paramResultSet.getDouble("marginItemAssure5_S")));
      localTradeRuleGC.setSettleMarginAlgr_B(new Short(paramResultSet.getShort("SettleMarginAlgr_B")));
      localTradeRuleGC.setSettleMarginAlgr_S(new Short(paramResultSet.getShort("SettleMarginAlgr_S")));
      localTradeRuleGC.setSettleMarginRate_B(new Double(paramResultSet.getDouble("SettleMarginRate_B")));
      localTradeRuleGC.setSettleMarginRate_S(new Double(paramResultSet.getDouble("SettleMarginRate_S")));
      localTradeRuleGC.setPayoutAlgr(new Short(paramResultSet.getShort("PayoutAlgr")));
      localTradeRuleGC.setPayoutRate(new Double(paramResultSet.getDouble("PayoutRate")));
      return localTradeRuleGC;
    }
  }
  
  class CM_TradeRuleRowMapper
    implements RowMapper
  {
    CM_TradeRuleRowMapper() {}
    
    public Object mapRow(ResultSet paramResultSet, int paramInt)
      throws SQLException
    {
      return rsToTradeRule(paramResultSet);
    }
    
    private TradeRule rsToTradeRule(ResultSet paramResultSet)
      throws SQLException
    {
      TradeRule localTradeRule = new TradeRule();
      localTradeRule.setCustomerID(paramResultSet.getString("FirmID"));
      localTradeRule.setFeeDiscountRate(new Double(paramResultSet.getDouble("FeeDiscountRate")));
      localTradeRule.setMarginDiscountRate(new Double(paramResultSet.getDouble("MarginDiscountRate")));
      return localTradeRule;
    }
  }
  
  class GM_TradeRuleRowMapper
    implements RowMapper
  {
    GM_TradeRuleRowMapper() {}
    
    public Object mapRow(ResultSet paramResultSet, int paramInt)
      throws SQLException
    {
      return rsToTradeRule(paramResultSet);
    }
    
    private TradeRuleGC rsToTradeRule(ResultSet paramResultSet)
      throws SQLException
    {
      TradeRuleGC localTradeRuleGC = new TradeRuleGC();
      localTradeRuleGC.setCommodityID(paramResultSet.getString("CommodityID"));
      localTradeRuleGC.setGroupID(paramResultSet.getString("GroupID"));
      localTradeRuleGC.setFeeAlgr(new Short(paramResultSet.getShort("FeeAlgr")));
      localTradeRuleGC.setFeeRate_B(new Double(paramResultSet.getDouble("FeeRate_B")));
      localTradeRuleGC.setFeeRate_S(new Double(paramResultSet.getDouble("FeeRate_S")));
      localTradeRuleGC.setMarginAlgr(new Short(paramResultSet.getShort("MarginAlgr")));
      localTradeRuleGC.setMarginRate_B(new Double(paramResultSet.getDouble("MarginRate_B")));
      localTradeRuleGC.setMarginRate_S(new Double(paramResultSet.getDouble("MarginRate_S")));
      localTradeRuleGC.setMarginItem1(new Double(paramResultSet.getDouble("MarginItem1")));
      localTradeRuleGC.setMarginItem2(new Double(paramResultSet.getDouble("MarginItem2")));
      localTradeRuleGC.setMarginItem3(new Double(paramResultSet.getDouble("MarginItem3")));
      localTradeRuleGC.setMarginItem4(new Double(paramResultSet.getDouble("MarginItem4")));
      localTradeRuleGC.setMarginItem1_S(new Double(paramResultSet.getDouble("MarginItem1_S")));
      localTradeRuleGC.setMarginItem2_S(new Double(paramResultSet.getDouble("MarginItem2_S")));
      localTradeRuleGC.setMarginItem3_S(new Double(paramResultSet.getDouble("MarginItem3_S")));
      localTradeRuleGC.setMarginItem4_S(new Double(paramResultSet.getDouble("MarginItem4_S")));
      localTradeRuleGC.setMarginAssure_B(new Double(paramResultSet.getDouble("marginAssure_B")));
      localTradeRuleGC.setMarginAssure_S(new Double(paramResultSet.getDouble("marginAssure_S")));
      localTradeRuleGC.setMarginItemAssure1(new Double(paramResultSet.getDouble("marginItemAssure1")));
      localTradeRuleGC.setMarginItemAssure1_S(new Double(paramResultSet.getDouble("marginItemAssure1_S")));
      localTradeRuleGC.setMarginItemAssure2(new Double(paramResultSet.getDouble("marginItemAssure2")));
      localTradeRuleGC.setMarginItemAssure2_S(new Double(paramResultSet.getDouble("marginItemAssure2_S")));
      localTradeRuleGC.setMarginItemAssure3(new Double(paramResultSet.getDouble("marginItemAssure3")));
      localTradeRuleGC.setMarginItemAssure3_S(new Double(paramResultSet.getDouble("marginItemAssure3_S")));
      localTradeRuleGC.setMarginItemAssure4(new Double(paramResultSet.getDouble("marginItemAssure4")));
      localTradeRuleGC.setMarginItemAssure4_S(new Double(paramResultSet.getDouble("marginItemAssure4_S")));
      localTradeRuleGC.setTodayCloseFeeRate_B(new Double(paramResultSet.getDouble("TodayCloseFeeRate_B")));
      localTradeRuleGC.setTodayCloseFeeRate_S(new Double(paramResultSet.getDouble("TodayCloseFeeRate_S")));
      localTradeRuleGC.setHistoryCloseFeeRate_B(new Double(paramResultSet.getDouble("HistoryCloseFeeRate_B")));
      localTradeRuleGC.setHistoryCloseFeeRate_S(new Double(paramResultSet.getDouble("HistoryCloseFeeRate_S")));
      localTradeRuleGC.setSettleFeeAlgr(new Short(paramResultSet.getShort("SettleFeeAlgr")));
      localTradeRuleGC.setSettleFeeRate_B(new Double(paramResultSet.getDouble("SettleFeeRate_B")));
      localTradeRuleGC.setSettleFeeRate_S(new Double(paramResultSet.getDouble("SettleFeeRate_S")));
      localTradeRuleGC.setForceCloseFeeAlgr(new Short(paramResultSet.getShort("ForceCloseFeeAlgr")));
      localTradeRuleGC.setForceCloseFeeRate_B(new Double(paramResultSet.getDouble("ForceCloseFeeRate_B")));
      localTradeRuleGC.setForceCloseFeeRate_S(new Double(paramResultSet.getDouble("ForceCloseFeeRate_S")));
      localTradeRuleGC.setSettleMarginRate_B(new Double(paramResultSet.getDouble("SettleMarginRate_B")));
      localTradeRuleGC.setSettleMarginRate_S(new Double(paramResultSet.getDouble("SettleMarginRate_S")));
      localTradeRuleGC.setSettleMarginAlgr_B(new Short(paramResultSet.getShort("SettleMarginAlgr_B")));
      localTradeRuleGC.setSettleMarginAlgr_S(new Short(paramResultSet.getShort("SettleMarginAlgr_S")));
      localTradeRuleGC.setPayoutAlgr(new Short(paramResultSet.getShort("PayoutAlgr")));
      localTradeRuleGC.setPayoutRate(new Double(paramResultSet.getDouble("PayoutRate")));
      return localTradeRuleGC;
    }
  }
}

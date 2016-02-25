package gnnt.MEBS.timebargain.manage.dao.jdbc;

import gnnt.MEBS.timebargain.manage.dao.TradeBreedRuleDAO;
import gnnt.MEBS.timebargain.manage.model.TradeBreedRule;
import gnnt.MEBS.timebargain.manage.model.TradeBreedRuleGC;
import java.io.PrintStream;
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

public class TradeBreedRuleDAOJdbc
  extends BaseDAOJdbc
  implements TradeBreedRuleDAO
{
  private Log log = LogFactory.getLog(TradeBreedRuleDAOJdbc.class);
  
  public TradeBreedRuleGC getGM_TradeBreedRuleById(Integer paramInteger, String paramString)
  {
    Assert.hasText(paramString);
    Assert.notNull(paramInteger);
    String str = "select * from T_A_gc_traderule where CommodityID=? and GroupID=?";
    Object[] arrayOfObject = { paramString, paramInteger };
    this.log.debug("sql: " + str);
    this.log.debug("marketCode:" + arrayOfObject[0]);
    this.log.debug("groupID:" + arrayOfObject[1]);
    TradeBreedRuleGC localTradeBreedRuleGC = null;
    try
    {
      localTradeBreedRuleGC = (TradeBreedRuleGC)getJdbcTemplate().queryForObject(str, arrayOfObject, new GM_TradeBreedRuleRowMapper());
      return localTradeBreedRuleGC;
    }
    catch (IncorrectResultSizeDataAccessException localIncorrectResultSizeDataAccessException)
    {
      throw new RuntimeException("GM_TradeRule表中商品ID[" + paramString + "]，客户组ID[" + paramInteger + "]不存在！");
    }
  }
  
  public List getGM_TradeBreedRules(TradeBreedRuleGC paramTradeBreedRuleGC)
  {
    String str = "select a.GroupID,a.CommodityID,(case when a.FeeAlgr=2 then '绝对值' else '按百分比' end) FeeAlgr,a.FeeRate_B,a.FeeRate_S,(case when a.MarginAlgr=2 then '绝对值' else '按百分比' end) MarginAlgr,a.MarginRate_B,a.MarginRate_S,a.MarginAssure_B,a.MarginAssure_S,b.groupname,c.name from T_A_GC_TradeRule a,T_A_Firmgroup b,T_commodity c where a.groupID=b.groupID and c.CommodityID=a.CommodityID";
    this.log.debug("sql: " + str);
    return getJdbcTemplate().queryForList(str);
  }
  
  public void insertGM_TradeBreedRule(TradeBreedRuleGC paramTradeBreedRuleGC)
  {
    String str = "insert into T_A_GC_TradeRule(GroupID,CommodityID,FeeAlgr,FeeRate_B,FeeRate_S,MarginAlgr,MarginRate_B,MarginRate_S,MarginItem1,MarginItem2,MarginItem3,MarginItem4,MarginItem1_S,MarginItem2_S,MarginItem3_S,MarginItem4_S,MarginAssure_B,MarginAssure_S,MarginItemAssure1,MarginItemAssure2,MarginItemAssure3,MarginItemAssure4,MarginItemAssure1_S,MarginItemAssure2_S,MarginItemAssure3_S,MarginItemAssure4_S,TodayCloseFeeRate_B,TodayCloseFeeRate_S,HistoryCloseFeeRate_B,HistoryCloseFeeRate_S,SettleFeeAlgr,SettleFeeRate_B,SettleFeeRate_S,ForceCloseFeeAlgr,ForceCloseFeeRate_B,ForceCloseFeeRate_S,SettleMarginRate_B,SettleMarginRate_S,settleMarginAlgr_B,settleMarginAlgr_S,PayoutAlgr,PayoutRate) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
    Object[] arrayOfObject = { paramTradeBreedRuleGC.getGroupID(), paramTradeBreedRuleGC.getBreedID(), paramTradeBreedRuleGC.getFeeAlgr(), paramTradeBreedRuleGC.getFeeRate_B() == null ? new Double(0.0D) : paramTradeBreedRuleGC.getFeeRate_B(), paramTradeBreedRuleGC.getFeeRate_S() == null ? new Double(0.0D) : paramTradeBreedRuleGC.getFeeRate_S(), paramTradeBreedRuleGC.getMarginAlgr(), paramTradeBreedRuleGC.getMarginRate_B() == null ? new Double(0.0D) : paramTradeBreedRuleGC.getMarginRate_B(), paramTradeBreedRuleGC.getMarginRate_S() == null ? new Double(0.0D) : paramTradeBreedRuleGC.getMarginRate_S(), paramTradeBreedRuleGC.getMarginItem1() == null ? new Double(0.0D) : paramTradeBreedRuleGC.getMarginItem1(), paramTradeBreedRuleGC.getMarginItem2() == null ? new Double(0.0D) : paramTradeBreedRuleGC.getMarginItem2(), paramTradeBreedRuleGC.getMarginItem3() == null ? new Double(0.0D) : paramTradeBreedRuleGC.getMarginItem3(), paramTradeBreedRuleGC.getMarginItem4() == null ? new Double(0.0D) : paramTradeBreedRuleGC.getMarginItem4(), paramTradeBreedRuleGC.getMarginItem1_S() == null ? new Double(0.0D) : paramTradeBreedRuleGC.getMarginItem1_S(), paramTradeBreedRuleGC.getMarginItem2_S() == null ? new Double(0.0D) : paramTradeBreedRuleGC.getMarginItem2_S(), paramTradeBreedRuleGC.getMarginItem3_S() == null ? new Double(0.0D) : paramTradeBreedRuleGC.getMarginItem3_S(), paramTradeBreedRuleGC.getMarginItem4_S() == null ? new Double(0.0D) : paramTradeBreedRuleGC.getMarginItem4_S(), paramTradeBreedRuleGC.getMarginAssure_B() == null ? new Double(0.0D) : paramTradeBreedRuleGC.getMarginAssure_B(), paramTradeBreedRuleGC.getMarginAssure_S() == null ? new Double(0.0D) : paramTradeBreedRuleGC.getMarginAssure_S(), paramTradeBreedRuleGC.getMarginItemAssure1() == null ? new Double(0.0D) : paramTradeBreedRuleGC.getMarginItemAssure1(), paramTradeBreedRuleGC.getMarginItemAssure2() == null ? new Double(0.0D) : paramTradeBreedRuleGC.getMarginItemAssure2(), paramTradeBreedRuleGC.getMarginItemAssure3() == null ? new Double(0.0D) : paramTradeBreedRuleGC.getMarginItemAssure3(), paramTradeBreedRuleGC.getMarginItemAssure4() == null ? new Double(0.0D) : paramTradeBreedRuleGC.getMarginItemAssure4(), paramTradeBreedRuleGC.getMarginItemAssure1_S() == null ? new Double(0.0D) : paramTradeBreedRuleGC.getMarginItemAssure1_S(), paramTradeBreedRuleGC.getMarginItemAssure2_S() == null ? new Double(0.0D) : paramTradeBreedRuleGC.getMarginItemAssure2_S(), paramTradeBreedRuleGC.getMarginItemAssure3_S() == null ? new Double(0.0D) : paramTradeBreedRuleGC.getMarginItemAssure3_S(), paramTradeBreedRuleGC.getMarginItemAssure4_S() == null ? new Double(0.0D) : paramTradeBreedRuleGC.getMarginItemAssure4_S(), paramTradeBreedRuleGC.getTodayCloseFeeRate_B() == null ? new Double(0.0D) : paramTradeBreedRuleGC.getTodayCloseFeeRate_B(), paramTradeBreedRuleGC.getTodayCloseFeeRate_S() == null ? new Double(0.0D) : paramTradeBreedRuleGC.getTodayCloseFeeRate_S(), paramTradeBreedRuleGC.getHistoryCloseFeeRate_B() == null ? new Double(0.0D) : paramTradeBreedRuleGC.getHistoryCloseFeeRate_B(), paramTradeBreedRuleGC.getHistoryCloseFeeRate_S() == null ? new Double(0.0D) : paramTradeBreedRuleGC.getHistoryCloseFeeRate_S(), paramTradeBreedRuleGC.getSettleFeeAlgr(), paramTradeBreedRuleGC.getSettleFeeRate_B() == null ? new Double(0.0D) : paramTradeBreedRuleGC.getSettleFeeRate_B(), paramTradeBreedRuleGC.getSettleFeeRate_S() == null ? new Double(0.0D) : paramTradeBreedRuleGC.getSettleFeeRate_S(), paramTradeBreedRuleGC.getForceCloseFeeAlgr(), paramTradeBreedRuleGC.getForceCloseFeeRate_B() == null ? new Double(0.0D) : paramTradeBreedRuleGC.getForceCloseFeeRate_B(), paramTradeBreedRuleGC.getForceCloseFeeRate_S() == null ? new Double(0.0D) : paramTradeBreedRuleGC.getForceCloseFeeRate_S(), paramTradeBreedRuleGC.getSettleMarginRate_B() == null ? new Double(0.0D) : paramTradeBreedRuleGC.getSettleMarginRate_B(), paramTradeBreedRuleGC.getSettleMarginRate_S() == null ? new Double(0.0D) : paramTradeBreedRuleGC.getSettleMarginRate_S(), paramTradeBreedRuleGC.getSettleMarginAlgr_B(), paramTradeBreedRuleGC.getSettleMarginAlgr_S(), paramTradeBreedRuleGC.getPayoutAlgr(), paramTradeBreedRuleGC.getPayoutRate() == null ? new Double(0.0D) : paramTradeBreedRuleGC.getPayoutRate() };
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
  
  public void updateGM_TradeBreedRule(TradeBreedRuleGC paramTradeBreedRuleGC)
  {
    String str = "update T_A_GC_TradeRule set FeeAlgr=?,FeeRate_B=?,FeeRate_S=?,MarginAlgr=?,MarginRate_B=?,MarginRate_S=?,MarginItem1=?,MarginItem2=?,MarginItem3=?,MarginItem4=?,MarginItem1_S=?,MarginItem2_S=?,MarginItem3_S=?,MarginItem4_S=?,MarginAssure_B=?,MarginAssure_S=?,MarginItemAssure1=?,MarginItemAssure2=?,MarginItemAssure3=?,MarginItemAssure4=?,MarginItemAssure1_S=?,MarginItemAssure2_S=?,MarginItemAssure3_S=?,MarginItemAssure4_S=?,TodayCloseFeeRate_B=?,TodayCloseFeeRate_S=?,HistoryCloseFeeRate_B=?,HistoryCloseFeeRate_S=?,SettleFeeAlgr=?,SettleFeeRate_B=?,SettleFeeRate_S=?,ForceCloseFeeAlgr=?,ForceCloseFeeRate_B=?,ForceCloseFeeRate_S=?,SettleMarginRate_B=?,SettleMarginRate_S=?,settleMarginAlgr_B=?,settleMarginAlgr_S=?,PayoutAlgr=?,PayoutRate=?  where CommodityID=? and GroupID=?";
    Object[] arrayOfObject = { paramTradeBreedRuleGC.getFeeAlgr(), paramTradeBreedRuleGC.getFeeRate_B() == null ? new Double(0.0D) : paramTradeBreedRuleGC.getFeeRate_B(), paramTradeBreedRuleGC.getFeeRate_S() == null ? new Double(0.0D) : paramTradeBreedRuleGC.getFeeRate_S(), paramTradeBreedRuleGC.getMarginAlgr(), paramTradeBreedRuleGC.getMarginRate_B() == null ? new Double(0.0D) : paramTradeBreedRuleGC.getMarginRate_B(), paramTradeBreedRuleGC.getMarginRate_S() == null ? new Double(0.0D) : paramTradeBreedRuleGC.getMarginRate_S(), paramTradeBreedRuleGC.getMarginItem1() == null ? new Double(0.0D) : paramTradeBreedRuleGC.getMarginItem1(), paramTradeBreedRuleGC.getMarginItem2() == null ? new Double(0.0D) : paramTradeBreedRuleGC.getMarginItem2(), paramTradeBreedRuleGC.getMarginItem3() == null ? new Double(0.0D) : paramTradeBreedRuleGC.getMarginItem3(), paramTradeBreedRuleGC.getMarginItem4() == null ? new Double(0.0D) : paramTradeBreedRuleGC.getMarginItem4(), paramTradeBreedRuleGC.getMarginItem1_S() == null ? new Double(0.0D) : paramTradeBreedRuleGC.getMarginItem1_S(), paramTradeBreedRuleGC.getMarginItem2_S() == null ? new Double(0.0D) : paramTradeBreedRuleGC.getMarginItem2_S(), paramTradeBreedRuleGC.getMarginItem3_S() == null ? new Double(0.0D) : paramTradeBreedRuleGC.getMarginItem3_S(), paramTradeBreedRuleGC.getMarginItem4_S() == null ? new Double(0.0D) : paramTradeBreedRuleGC.getMarginItem4_S(), paramTradeBreedRuleGC.getMarginAssure_B() == null ? new Double(0.0D) : paramTradeBreedRuleGC.getMarginAssure_B(), paramTradeBreedRuleGC.getMarginAssure_S() == null ? new Double(0.0D) : paramTradeBreedRuleGC.getMarginAssure_S(), paramTradeBreedRuleGC.getMarginItemAssure1() == null ? new Double(0.0D) : paramTradeBreedRuleGC.getMarginItemAssure1(), paramTradeBreedRuleGC.getMarginItemAssure2() == null ? new Double(0.0D) : paramTradeBreedRuleGC.getMarginItemAssure2(), paramTradeBreedRuleGC.getMarginItemAssure3() == null ? new Double(0.0D) : paramTradeBreedRuleGC.getMarginItemAssure3(), paramTradeBreedRuleGC.getMarginItemAssure4() == null ? new Double(0.0D) : paramTradeBreedRuleGC.getMarginItemAssure4(), paramTradeBreedRuleGC.getMarginItemAssure1_S() == null ? new Double(0.0D) : paramTradeBreedRuleGC.getMarginItemAssure1_S(), paramTradeBreedRuleGC.getMarginItemAssure2_S() == null ? new Double(0.0D) : paramTradeBreedRuleGC.getMarginItemAssure2_S(), paramTradeBreedRuleGC.getMarginItemAssure3_S() == null ? new Double(0.0D) : paramTradeBreedRuleGC.getMarginItemAssure3_S(), paramTradeBreedRuleGC.getMarginItemAssure4_S() == null ? new Double(0.0D) : paramTradeBreedRuleGC.getMarginItemAssure4_S(), paramTradeBreedRuleGC.getTodayCloseFeeRate_B() == null ? new Double(0.0D) : paramTradeBreedRuleGC.getTodayCloseFeeRate_B(), paramTradeBreedRuleGC.getTodayCloseFeeRate_S() == null ? new Double(0.0D) : paramTradeBreedRuleGC.getTodayCloseFeeRate_S(), paramTradeBreedRuleGC.getHistoryCloseFeeRate_B() == null ? new Double(0.0D) : paramTradeBreedRuleGC.getHistoryCloseFeeRate_B(), paramTradeBreedRuleGC.getHistoryCloseFeeRate_S() == null ? new Double(0.0D) : paramTradeBreedRuleGC.getHistoryCloseFeeRate_S(), paramTradeBreedRuleGC.getSettleFeeAlgr(), paramTradeBreedRuleGC.getSettleFeeRate_B() == null ? new Double(0.0D) : paramTradeBreedRuleGC.getSettleFeeRate_B(), paramTradeBreedRuleGC.getSettleFeeRate_S() == null ? new Double(0.0D) : paramTradeBreedRuleGC.getSettleFeeRate_S(), paramTradeBreedRuleGC.getForceCloseFeeAlgr(), paramTradeBreedRuleGC.getForceCloseFeeRate_B() == null ? new Double(0.0D) : paramTradeBreedRuleGC.getForceCloseFeeRate_B(), paramTradeBreedRuleGC.getForceCloseFeeRate_S() == null ? new Double(0.0D) : paramTradeBreedRuleGC.getForceCloseFeeRate_S(), paramTradeBreedRuleGC.getSettleMarginRate_B() == null ? new Double(0.0D) : paramTradeBreedRuleGC.getSettleMarginRate_B(), paramTradeBreedRuleGC.getSettleMarginRate_S() == null ? new Double(0.0D) : paramTradeBreedRuleGC.getSettleMarginRate_S(), paramTradeBreedRuleGC.getSettleMarginAlgr_B(), paramTradeBreedRuleGC.getSettleMarginAlgr_S(), paramTradeBreedRuleGC.getPayoutAlgr(), paramTradeBreedRuleGC.getPayoutRate() == null ? new Double(0.0D) : paramTradeBreedRuleGC.getPayoutRate(), paramTradeBreedRuleGC.getBreedID(), paramTradeBreedRuleGC.getGroupID() };
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
  
  public void deleteGM_TradeBreedRuleById(String paramString, Integer paramInteger)
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
  
  public void updateCM_TradeBreedRule(TradeBreedRule paramTradeBreedRule)
  {
    String str = "update T_CM_TradeRule set FeeDiscountRate=?,MarginDiscountRate=? where  CustomerID=?";
    Object[] arrayOfObject = { paramTradeBreedRule.getFeeDiscountRate() == null ? new Double(0.0D) : paramTradeBreedRule.getFeeDiscountRate(), paramTradeBreedRule.getMarginDiscountRate() == null ? new Double(0.0D) : paramTradeBreedRule.getMarginDiscountRate(), paramTradeBreedRule.getCustomerID() };
    this.log.debug("sql: " + str);
    for (int i = 0; i < arrayOfObject.length; i++) {
      this.log.debug("params[" + i + "]: " + arrayOfObject[i]);
    }
    getJdbcTemplate().update(str, arrayOfObject);
  }
  
  public void deleteCM_TradeBreedRuleById(String paramString1, String paramString2)
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
  
  public List getFirmCommodityIDFromMargin(String paramString1, String paramString2)
  {
    String str = "select firmid,commodityid from t_a_firmmargin t where firmid = '" + paramString1 + "' and commodityid in (select commodityid from t_commodity where breedid = '" + paramString2 + "')";
    return getJdbcTemplate().queryForList(str);
  }
  
  public List getFirmCommodityIDFromFee(String paramString1, String paramString2)
  {
    String str = "select firmid,commodityid from t_a_firmfee t where firmid = '" + paramString1 + "' and commodityid in (select commodityid from t_commodity where breedid = '" + paramString2 + "')";
    return getJdbcTemplate().queryForList(str);
  }
  
  public List getFirmCommodityIDFromMaxHoldQty(String paramString1, String paramString2)
  {
    String str = "select firmid,commodityid from t_a_firmmaxholdqty t where firmid = '" + paramString1 + "' and commodityid in (select commodityid from t_commodity where breedid = '" + paramString2 + "')";
    return getJdbcTemplate().queryForList(str);
  }
  
  public List getFirmCommodityIDFromMarginForAdd(String paramString1, String paramString2)
  {
    String str = "select commodityid from t_commodity t where breedid = '" + paramString2 + "' and commodityid not in (select commodityid from t_a_firmmargin where firmid = '" + paramString1 + "' and breedid = '" + paramString2 + "')";
    return getJdbcTemplate().queryForList(str);
  }
  
  public List getFirmCommodityIDFromFeeForAdd(String paramString1, String paramString2)
  {
    String str = "select commodityid from t_commodity t where breedid = '" + paramString2 + "' and commodityid not in (select commodityid from t_a_firmfee where firmid = '" + paramString1 + "' and breedid = '" + paramString2 + "')";
    return getJdbcTemplate().queryForList(str);
  }
  
  public List getFirmCommodityIDFromMaxHoldQtyForAdd(String paramString1, String paramString2)
  {
    String str = "select commodityid from t_commodity t where breedid = '" + paramString2 + "' and commodityid not in (select commodityid from t_a_firmmaxholdqty where firmid = '" + paramString1 + "' and breedid = '" + paramString2 + "')";
    return getJdbcTemplate().queryForList(str);
  }
  
  public List getFirmIDFromMarginForAdd(String paramString)
  {
    String str = "select t.firmid from T_A_FirmBreedMargin t where t.breedid = '" + paramString + "'";
    return getJdbcTemplate().queryForList(str);
  }
  
  public List getFirmIDFromFeeForAdd(String paramString)
  {
    String str = "select t.firmid from T_A_FirmBreedFee t where breedid = '" + paramString + "'";
    return getJdbcTemplate().queryForList(str);
  }
  
  public List getFirmIDFromMaxHoldQtyForAdd(String paramString)
  {
    String str = "select t.firmid from T_A_FirmBreedMaxHoldQty t where t.breedid = '" + paramString + "'";
    return getJdbcTemplate().queryForList(str);
  }
  
  public List getFirmBreedMargin()
  {
    String str = "select a.modifyTime,a.firmid,a.breedid,a.marginalgr,decode(a.marginalgr,1,a.marginrate_b*100||'%',2,a.marginrate_b) marginrate_b,decode(a.marginalgr,1,a.marginrate_s*100||'%',2,a.marginrate_s) marginrate_s,decode(a.marginalgr,1,a.marginAssure_B*100||'%',2,a.marginAssure_B) marginAssure_B,decode(a.marginalgr,1,a.marginAssure_S*100||'%',2,a.marginAssure_S) marginAssure_S from T_A_FirmBreedMargin a";
    return getJdbcTemplate().queryForList(str);
  }
  
  public TradeBreedRuleGC getFirmBreedMarginById(String paramString1, String paramString2)
  {
    String str = "select a.* from T_A_FirmBreedMargin a where a.firmID = ? and a.breedID = ?";
    Object[] arrayOfObject = { paramString1, paramString2 };
    this.log.debug("sql: " + str);
    for (int i = 0; i < arrayOfObject.length; i++) {
      this.log.debug("params[" + i + "]: " + arrayOfObject[i]);
    }
    return (TradeBreedRuleGC)getJdbcTemplate().queryForObject(str, arrayOfObject, new FirmBreedMarginRowMapper());
  }
  
  public void insertFirmBreedMargin(TradeBreedRuleGC paramTradeBreedRuleGC)
  {
    String str = "insert into T_A_FirmBreedMargin (FirmID,BreedID,MarginAlgr,MarginItem1,MarginItem2,MarginItem3,MarginItem4,MarginItem1_S,MarginItem2_S,MarginItem3_S,MarginItem4_S,MarginItemAssure1,MarginItemAssure2,MarginItemAssure3,MarginItemAssure4,MarginItemAssure1_S,MarginItemAssure2_S,MarginItemAssure3_S,MarginItemAssure4_S,SettleMarginAlgr_B,SettleMarginAlgr_S,SettleMarginRate_B,SettleMarginRate_S,PayoutAlgr,PayoutRate,MarginItem5,MarginItem5_S,MarginItemAssure5,MarginItemAssure5_S,ModifyTime) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,sysdate)";
    Object[] arrayOfObject = { paramTradeBreedRuleGC.getFirmID(), paramTradeBreedRuleGC.getBreedID(), paramTradeBreedRuleGC.getMarginAlgr(), paramTradeBreedRuleGC.getMarginItem1() == null ? new Double(0.0D) : paramTradeBreedRuleGC.getMarginItem1(), paramTradeBreedRuleGC.getMarginItem2() == null ? new Double(0.0D) : paramTradeBreedRuleGC.getMarginItem2(), paramTradeBreedRuleGC.getMarginItem3() == null ? new Double(0.0D) : paramTradeBreedRuleGC.getMarginItem3(), paramTradeBreedRuleGC.getMarginItem4() == null ? new Double(0.0D) : paramTradeBreedRuleGC.getMarginItem4(), paramTradeBreedRuleGC.getMarginItem1_S() == null ? new Double(0.0D) : paramTradeBreedRuleGC.getMarginItem1_S(), paramTradeBreedRuleGC.getMarginItem2_S() == null ? new Double(0.0D) : paramTradeBreedRuleGC.getMarginItem2_S(), paramTradeBreedRuleGC.getMarginItem3_S() == null ? new Double(0.0D) : paramTradeBreedRuleGC.getMarginItem3_S(), paramTradeBreedRuleGC.getMarginItem4_S() == null ? new Double(0.0D) : paramTradeBreedRuleGC.getMarginItem4_S(), paramTradeBreedRuleGC.getMarginItemAssure1() == null ? new Double(0.0D) : paramTradeBreedRuleGC.getMarginItemAssure1(), paramTradeBreedRuleGC.getMarginItemAssure2() == null ? new Double(0.0D) : paramTradeBreedRuleGC.getMarginItemAssure2(), paramTradeBreedRuleGC.getMarginItemAssure3() == null ? new Double(0.0D) : paramTradeBreedRuleGC.getMarginItemAssure3(), paramTradeBreedRuleGC.getMarginItemAssure4() == null ? new Double(0.0D) : paramTradeBreedRuleGC.getMarginItemAssure4(), paramTradeBreedRuleGC.getMarginItemAssure1_S() == null ? new Double(0.0D) : paramTradeBreedRuleGC.getMarginItemAssure1_S(), paramTradeBreedRuleGC.getMarginItemAssure2_S() == null ? new Double(0.0D) : paramTradeBreedRuleGC.getMarginItemAssure2_S(), paramTradeBreedRuleGC.getMarginItemAssure3_S() == null ? new Double(0.0D) : paramTradeBreedRuleGC.getMarginItemAssure3_S(), paramTradeBreedRuleGC.getMarginItemAssure4_S() == null ? new Double(0.0D) : paramTradeBreedRuleGC.getMarginItemAssure4_S(), paramTradeBreedRuleGC.getSettleMarginAlgr_B(), paramTradeBreedRuleGC.getSettleMarginAlgr_S(), paramTradeBreedRuleGC.getSettleMarginRate_B() == null ? new Double(0.0D) : paramTradeBreedRuleGC.getSettleMarginRate_B(), paramTradeBreedRuleGC.getSettleMarginRate_S() == null ? new Double(0.0D) : paramTradeBreedRuleGC.getSettleMarginRate_S(), paramTradeBreedRuleGC.getPayoutAlgr(), paramTradeBreedRuleGC.getPayoutRate() == null ? new Double(0.0D) : paramTradeBreedRuleGC.getPayoutRate(), paramTradeBreedRuleGC.getMarginItem5() == null ? new Double(0.0D) : paramTradeBreedRuleGC.getMarginItem5(), paramTradeBreedRuleGC.getMarginItem5_S() == null ? new Double(0.0D) : paramTradeBreedRuleGC.getMarginItem5_S(), paramTradeBreedRuleGC.getMarginItemAssure5() == null ? new Double(0.0D) : paramTradeBreedRuleGC.getMarginItemAssure5(), paramTradeBreedRuleGC.getMarginItemAssure5_S() == null ? new Double(0.0D) : paramTradeBreedRuleGC.getMarginItemAssure5_S() };
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
  
  public void updateFirmBreedMargin(TradeBreedRuleGC paramTradeBreedRuleGC)
  {
    String str = "update T_A_FirmBreedMargin set MarginAlgr=?,MarginItem1=?,MarginItem2=?,MarginItem3=?,MarginItem4=?,MarginItem1_S=?,MarginItem2_S=?,MarginItem3_S=?,MarginItem4_S=?,MarginItemAssure1=?,MarginItemAssure2=?,MarginItemAssure3=?,MarginItemAssure4=?,MarginItemAssure1_S=?,MarginItemAssure2_S=?,MarginItemAssure3_S=?,MarginItemAssure4_S=?,SettleMarginAlgr_B=?,SettleMarginAlgr_S=?,SettleMarginRate_B=?,SettleMarginRate_S=?,PayoutAlgr=?,PayoutRate=?,ModifyTime=sysdate,MarginItem5=?,MarginItem5_S=?,MarginItemAssure5=?,MarginItemAssure5_S=? where FirmID = ? and BreedID = ?";
    Object[] arrayOfObject = { paramTradeBreedRuleGC.getMarginAlgr(), paramTradeBreedRuleGC.getMarginItem1() == null ? new Double(0.0D) : paramTradeBreedRuleGC.getMarginItem1(), paramTradeBreedRuleGC.getMarginItem2() == null ? new Double(0.0D) : paramTradeBreedRuleGC.getMarginItem2(), paramTradeBreedRuleGC.getMarginItem3() == null ? new Double(0.0D) : paramTradeBreedRuleGC.getMarginItem3(), paramTradeBreedRuleGC.getMarginItem4() == null ? new Double(0.0D) : paramTradeBreedRuleGC.getMarginItem4(), paramTradeBreedRuleGC.getMarginItem1_S() == null ? new Double(0.0D) : paramTradeBreedRuleGC.getMarginItem1_S(), paramTradeBreedRuleGC.getMarginItem2_S() == null ? new Double(0.0D) : paramTradeBreedRuleGC.getMarginItem2_S(), paramTradeBreedRuleGC.getMarginItem3_S() == null ? new Double(0.0D) : paramTradeBreedRuleGC.getMarginItem3_S(), paramTradeBreedRuleGC.getMarginItem4_S() == null ? new Double(0.0D) : paramTradeBreedRuleGC.getMarginItem4_S(), paramTradeBreedRuleGC.getMarginItemAssure1() == null ? new Double(0.0D) : paramTradeBreedRuleGC.getMarginItemAssure1(), paramTradeBreedRuleGC.getMarginItemAssure2() == null ? new Double(0.0D) : paramTradeBreedRuleGC.getMarginItemAssure2(), paramTradeBreedRuleGC.getMarginItemAssure3() == null ? new Double(0.0D) : paramTradeBreedRuleGC.getMarginItemAssure3(), paramTradeBreedRuleGC.getMarginItemAssure4() == null ? new Double(0.0D) : paramTradeBreedRuleGC.getMarginItemAssure4(), paramTradeBreedRuleGC.getMarginItemAssure1_S() == null ? new Double(0.0D) : paramTradeBreedRuleGC.getMarginItemAssure1_S(), paramTradeBreedRuleGC.getMarginItemAssure2_S() == null ? new Double(0.0D) : paramTradeBreedRuleGC.getMarginItemAssure2_S(), paramTradeBreedRuleGC.getMarginItemAssure3_S() == null ? new Double(0.0D) : paramTradeBreedRuleGC.getMarginItemAssure3_S(), paramTradeBreedRuleGC.getMarginItemAssure4_S() == null ? new Double(0.0D) : paramTradeBreedRuleGC.getMarginItemAssure4_S(), paramTradeBreedRuleGC.getSettleMarginAlgr_B(), paramTradeBreedRuleGC.getSettleMarginAlgr_S(), paramTradeBreedRuleGC.getSettleMarginRate_B() == null ? new Double(0.0D) : paramTradeBreedRuleGC.getSettleMarginRate_B(), paramTradeBreedRuleGC.getSettleMarginRate_S() == null ? new Double(0.0D) : paramTradeBreedRuleGC.getSettleMarginRate_S(), paramTradeBreedRuleGC.getPayoutAlgr(), paramTradeBreedRuleGC.getPayoutRate() == null ? new Double(0.0D) : paramTradeBreedRuleGC.getPayoutRate(), paramTradeBreedRuleGC.getMarginItem5() == null ? new Double(0.0D) : paramTradeBreedRuleGC.getMarginItem5(), paramTradeBreedRuleGC.getMarginItem5_S() == null ? new Double(0.0D) : paramTradeBreedRuleGC.getMarginItem5_S(), paramTradeBreedRuleGC.getMarginItemAssure5() == null ? new Double(0.0D) : paramTradeBreedRuleGC.getMarginItemAssure5(), paramTradeBreedRuleGC.getMarginItemAssure5_S() == null ? new Double(0.0D) : paramTradeBreedRuleGC.getMarginItemAssure5_S(), paramTradeBreedRuleGC.getFirmID(), paramTradeBreedRuleGC.getBreedID() };
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
  
  public void deleteFirmBreedMarginById(String paramString1, String paramString2)
  {
    String str = "delete from T_A_FirmBreedMargin where firmID = ? and breedID = ?";
    Object[] arrayOfObject = { paramString1, paramString2 };
    this.log.debug("sql: " + str);
    for (int i = 0; i < arrayOfObject.length; i++) {
      this.log.debug("params[" + i + "]: " + arrayOfObject[i]);
    }
    try
    {
      System.out.println("==========" + str);
      getJdbcTemplate().update(str, arrayOfObject);
      System.out.println("123456");
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      throw new RuntimeException("删除失败！");
    }
  }
  
  public List getFirmBreedFee()
  {
    String str = "select a.* from T_A_FirmBreedFee a";
    return getJdbcTemplate().queryForList(str);
  }
  
  public TradeBreedRuleGC getFirmBreedFeeById(String paramString1, String paramString2)
  {
    String str = "select a.* from T_A_FirmBreedFee a where firmID = ? and BreedID = ?";
    Object[] arrayOfObject = { paramString1, paramString2 };
    this.log.debug("sql: " + str);
    for (int i = 0; i < arrayOfObject.length; i++) {
      this.log.debug("params[" + i + "]: " + arrayOfObject[i]);
    }
    try
    {
      return (TradeBreedRuleGC)getJdbcTemplate().queryForObject(str, arrayOfObject, new FirmBreedFeeRowMapper());
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      throw new RuntimeException("查询对象失败！");
    }
  }
  
  public void insertFirmBreedFee(TradeBreedRuleGC paramTradeBreedRuleGC)
  {
    String str = "insert into T_A_FirmBreedFee (FirmID,BreedID,FeeAlgr,FeeRate_B,FeeRate_S,TodayCloseFeeRate_B,TodayCloseFeeRate_S,HistoryCloseFeeRate_B,HistoryCloseFeeRate_S,SettleFeeAlgr,SettleFeeRate_B,SettleFeeRate_S,ForceCloseFeeAlgr,ForceCloseFeeRate_B,ForceCloseFeeRate_S,ModifyTime) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,sysdate)";
    Object[] arrayOfObject = { paramTradeBreedRuleGC.getFirmID(), paramTradeBreedRuleGC.getBreedID(), paramTradeBreedRuleGC.getFeeAlgr(), paramTradeBreedRuleGC.getFeeRate_B() == null ? new Double(0.0D) : paramTradeBreedRuleGC.getFeeRate_B(), paramTradeBreedRuleGC.getFeeRate_S() == null ? new Double(0.0D) : paramTradeBreedRuleGC.getFeeRate_S(), paramTradeBreedRuleGC.getTodayCloseFeeRate_B() == null ? new Double(0.0D) : paramTradeBreedRuleGC.getTodayCloseFeeRate_B(), paramTradeBreedRuleGC.getTodayCloseFeeRate_S() == null ? new Double(0.0D) : paramTradeBreedRuleGC.getTodayCloseFeeRate_S(), paramTradeBreedRuleGC.getHistoryCloseFeeRate_B() == null ? new Double(0.0D) : paramTradeBreedRuleGC.getHistoryCloseFeeRate_B(), paramTradeBreedRuleGC.getHistoryCloseFeeRate_S() == null ? new Double(0.0D) : paramTradeBreedRuleGC.getHistoryCloseFeeRate_S(), paramTradeBreedRuleGC.getSettleFeeAlgr(), paramTradeBreedRuleGC.getSettleFeeRate_B() == null ? new Double(0.0D) : paramTradeBreedRuleGC.getSettleFeeRate_B(), paramTradeBreedRuleGC.getSettleFeeRate_S() == null ? new Double(0.0D) : paramTradeBreedRuleGC.getSettleFeeRate_S(), paramTradeBreedRuleGC.getForceCloseFeeAlgr(), paramTradeBreedRuleGC.getForceCloseFeeRate_B() == null ? new Double(0.0D) : paramTradeBreedRuleGC.getForceCloseFeeRate_B(), paramTradeBreedRuleGC.getForceCloseFeeRate_S() == null ? new Double(0.0D) : paramTradeBreedRuleGC.getForceCloseFeeRate_S() };
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
  
  public void updateFirmBreedFee(TradeBreedRuleGC paramTradeBreedRuleGC)
  {
    String str = "update T_A_FirmBreedFee set FeeAlgr=?,FeeRate_B=?,FeeRate_S=?,TodayCloseFeeRate_B=?,TodayCloseFeeRate_S=?,HistoryCloseFeeRate_B=?,HistoryCloseFeeRate_S=?,SettleFeeAlgr=?,SettleFeeRate_B=?,SettleFeeRate_S=?,ForceCloseFeeAlgr=?,ForceCloseFeeRate_B=?,ForceCloseFeeRate_S=?,ModifyTime=sysdate where FirmID = ? and BreedID = ?";
    Object[] arrayOfObject = { paramTradeBreedRuleGC.getFeeAlgr(), paramTradeBreedRuleGC.getFeeRate_B() == null ? new Double(0.0D) : paramTradeBreedRuleGC.getFeeRate_B(), paramTradeBreedRuleGC.getFeeRate_S() == null ? new Double(0.0D) : paramTradeBreedRuleGC.getFeeRate_S(), paramTradeBreedRuleGC.getTodayCloseFeeRate_B() == null ? new Double(0.0D) : paramTradeBreedRuleGC.getTodayCloseFeeRate_B(), paramTradeBreedRuleGC.getTodayCloseFeeRate_S() == null ? new Double(0.0D) : paramTradeBreedRuleGC.getTodayCloseFeeRate_S(), paramTradeBreedRuleGC.getHistoryCloseFeeRate_B() == null ? new Double(0.0D) : paramTradeBreedRuleGC.getHistoryCloseFeeRate_B(), paramTradeBreedRuleGC.getHistoryCloseFeeRate_S() == null ? new Double(0.0D) : paramTradeBreedRuleGC.getHistoryCloseFeeRate_S(), paramTradeBreedRuleGC.getSettleFeeAlgr(), paramTradeBreedRuleGC.getSettleFeeRate_B() == null ? new Double(0.0D) : paramTradeBreedRuleGC.getSettleFeeRate_B(), paramTradeBreedRuleGC.getSettleFeeRate_S() == null ? new Double(0.0D) : paramTradeBreedRuleGC.getSettleFeeRate_S(), paramTradeBreedRuleGC.getForceCloseFeeAlgr(), paramTradeBreedRuleGC.getForceCloseFeeRate_B() == null ? new Double(0.0D) : paramTradeBreedRuleGC.getForceCloseFeeRate_B(), paramTradeBreedRuleGC.getForceCloseFeeRate_S() == null ? new Double(0.0D) : paramTradeBreedRuleGC.getForceCloseFeeRate_S(), paramTradeBreedRuleGC.getFirmID(), paramTradeBreedRuleGC.getBreedID() };
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
  
  public void deleteFirmBreedFeeById(String paramString1, String paramString2)
  {
    String str = "delete from T_A_FirmBreedFee where firmID = ? and BreedID = ?";
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
  
  public List getFirmBreedMaxHoldQty()
  {
    String str = "select a.*,case when a.maxholdqty = -1 then '无限制' else a.maxholdqty||'' end as maxholdqty,case when a.cleanMaxHoldQty = -1 then '无限制' else a.cleanMaxHoldQty||'' end as cleanMaxHoldQty from T_A_FirmBreedMaxHoldQty a";
    return getJdbcTemplate().queryForList(str);
  }
  
  public TradeBreedRuleGC getFirmBreedMaxHoldQtyById(String paramString1, String paramString2)
  {
    String str = "select a.* from T_A_FirmBreedMaxHoldQty a where firmID = ? and BreedID = ?";
    Object[] arrayOfObject = { paramString1, paramString2 };
    this.log.debug("sql: " + str);
    for (int i = 0; i < arrayOfObject.length; i++) {
      this.log.debug("params[" + i + "]: " + arrayOfObject[i]);
    }
    try
    {
      return (TradeBreedRuleGC)getJdbcTemplate().queryForObject(str, arrayOfObject, new FirmBreedMaxHoldQtyRowMapper());
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      throw new RuntimeException("删除特殊手续费失败！");
    }
  }
  
  public void insertFirmBreedMaxHoldQty(TradeBreedRuleGC paramTradeBreedRuleGC)
  {
    String str = "insert into T_A_FirmBreedMaxHoldQty (FirmID,BreedID,MaxHoldQty,CleanMaxHoldQty,ModifyTime) values (?,?,?,?,sysdate)";
    Object[] arrayOfObject = { paramTradeBreedRuleGC.getFirmID(), paramTradeBreedRuleGC.getBreedID(), paramTradeBreedRuleGC.getMaxHoldQty(), paramTradeBreedRuleGC.getCleanMaxHoldQty() };
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
  
  public void updateFirmBreedMaxHoldQty(TradeBreedRuleGC paramTradeBreedRuleGC)
  {
    String str = "update T_A_FirmMaxHoldQty set MaxHoldQty = ?, CleanMaxHoldQty = ?, ModifyTime = sysdate where FirmID = ? and CommodityID = ?";
    Object[] arrayOfObject = { paramTradeBreedRuleGC.getMaxHoldQty(), paramTradeBreedRuleGC.getCleanMaxHoldQty(), paramTradeBreedRuleGC.getFirmID(), paramTradeBreedRuleGC.getBreedID() };
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
  
  public void deleteFirmBreedMaxHoldQtyById(String paramString1, String paramString2)
  {
    String str = "delete from T_A_FirmBreedMaxHoldQty where firmID = ? and BreedID = ?";
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
  
  public void updateSpacMargin(TradeBreedRuleGC paramTradeBreedRuleGC)
  {
    String str = "select count(*) from T_A_FirmMargin where firmID = '" + paramTradeBreedRuleGC.getFirmID() + "'";
    if (getJdbcTemplate().queryForInt(str) < 1) {
      throw new RuntimeException("此交易商不存在！");
    }
    str = "update T_A_FirmMargin set marginRate_B=?, marginRate_S=?, marginAssure_B=?, marginAssure_S=? where firmID = ? and commodityID = ?";
    Object[] arrayOfObject = { paramTradeBreedRuleGC.getMarginRate_B(), paramTradeBreedRuleGC.getMarginRate_S(), paramTradeBreedRuleGC.getMarginAssure_B(), paramTradeBreedRuleGC.getMarginAssure_S(), paramTradeBreedRuleGC.getFirmID(), paramTradeBreedRuleGC.getBreedID() };
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
  
  class FirmBreedMaxHoldQtyRowMapper
    implements RowMapper
  {
    FirmBreedMaxHoldQtyRowMapper() {}
    
    public Object mapRow(ResultSet paramResultSet, int paramInt)
      throws SQLException
    {
      return rsToTradeBreedRule(paramResultSet);
    }
    
    private TradeBreedRuleGC rsToTradeBreedRule(ResultSet paramResultSet)
      throws SQLException
    {
      TradeBreedRuleGC localTradeBreedRuleGC = new TradeBreedRuleGC();
      localTradeBreedRuleGC.setBreedID(paramResultSet.getString("BreedID"));
      localTradeBreedRuleGC.setFirmID(paramResultSet.getString("FirmID"));
      localTradeBreedRuleGC.setMaxHoldQty(Long.valueOf(paramResultSet.getLong("MaxHoldQty")));
      localTradeBreedRuleGC.setCleanMaxHoldQty(Long.valueOf(paramResultSet.getLong("CleanMaxHoldQty")));
      return localTradeBreedRuleGC;
    }
  }
  
  class FirmBreedFeeRowMapper
    implements RowMapper
  {
    FirmBreedFeeRowMapper() {}
    
    public Object mapRow(ResultSet paramResultSet, int paramInt)
      throws SQLException
    {
      return rsToTradeBreedRule(paramResultSet);
    }
    
    private TradeBreedRuleGC rsToTradeBreedRule(ResultSet paramResultSet)
      throws SQLException
    {
      TradeBreedRuleGC localTradeBreedRuleGC = new TradeBreedRuleGC();
      localTradeBreedRuleGC.setBreedID(paramResultSet.getString("BreedID"));
      localTradeBreedRuleGC.setFirmID(paramResultSet.getString("FirmID"));
      localTradeBreedRuleGC.setFeeAlgr(new Short(paramResultSet.getShort("FeeAlgr")));
      localTradeBreedRuleGC.setFeeRate_B(new Double(paramResultSet.getDouble("FeeRate_B")));
      localTradeBreedRuleGC.setFeeRate_S(new Double(paramResultSet.getDouble("FeeRate_S")));
      localTradeBreedRuleGC.setTodayCloseFeeRate_B(new Double(paramResultSet.getDouble("TodayCloseFeeRate_B")));
      localTradeBreedRuleGC.setTodayCloseFeeRate_S(new Double(paramResultSet.getDouble("TodayCloseFeeRate_S")));
      localTradeBreedRuleGC.setHistoryCloseFeeRate_B(new Double(paramResultSet.getDouble("HistoryCloseFeeRate_B")));
      localTradeBreedRuleGC.setHistoryCloseFeeRate_S(new Double(paramResultSet.getDouble("HistoryCloseFeeRate_S")));
      localTradeBreedRuleGC.setSettleFeeAlgr(new Short(paramResultSet.getShort("SettleFeeAlgr")));
      localTradeBreedRuleGC.setSettleFeeRate_B(new Double(paramResultSet.getDouble("SettleFeeRate_B")));
      localTradeBreedRuleGC.setSettleFeeRate_S(new Double(paramResultSet.getDouble("SettleFeeRate_S")));
      localTradeBreedRuleGC.setForceCloseFeeAlgr(new Short(paramResultSet.getShort("ForceCloseFeeAlgr")));
      localTradeBreedRuleGC.setForceCloseFeeRate_B(new Double(paramResultSet.getDouble("ForceCloseFeeRate_B")));
      localTradeBreedRuleGC.setForceCloseFeeRate_S(new Double(paramResultSet.getDouble("ForceCloseFeeRate_S")));
      return localTradeBreedRuleGC;
    }
  }
  
  class FirmBreedMarginRowMapper
    implements RowMapper
  {
    FirmBreedMarginRowMapper() {}
    
    public Object mapRow(ResultSet paramResultSet, int paramInt)
      throws SQLException
    {
      return rsToTradeBreedRule(paramResultSet);
    }
    
    private TradeBreedRuleGC rsToTradeBreedRule(ResultSet paramResultSet)
      throws SQLException
    {
      TradeBreedRuleGC localTradeBreedRuleGC = new TradeBreedRuleGC();
      localTradeBreedRuleGC.setBreedID(paramResultSet.getString("BreedID"));
      localTradeBreedRuleGC.setFirmID(paramResultSet.getString("FirmID"));
      localTradeBreedRuleGC.setMarginAlgr(new Short(paramResultSet.getShort("MarginAlgr")));
      localTradeBreedRuleGC.setMarginRate_B(new Double(paramResultSet.getDouble("MarginRate_B")));
      localTradeBreedRuleGC.setMarginRate_S(new Double(paramResultSet.getDouble("MarginRate_S")));
      localTradeBreedRuleGC.setMarginItem1(new Double(paramResultSet.getDouble("MarginItem1")));
      localTradeBreedRuleGC.setMarginItem2(new Double(paramResultSet.getDouble("MarginItem2")));
      localTradeBreedRuleGC.setMarginItem3(new Double(paramResultSet.getDouble("MarginItem3")));
      localTradeBreedRuleGC.setMarginItem4(new Double(paramResultSet.getDouble("MarginItem4")));
      localTradeBreedRuleGC.setMarginItem5(new Double(paramResultSet.getDouble("MarginItem5")));
      localTradeBreedRuleGC.setMarginItem1_S(new Double(paramResultSet.getDouble("MarginItem1_S")));
      localTradeBreedRuleGC.setMarginItem2_S(new Double(paramResultSet.getDouble("MarginItem2_S")));
      localTradeBreedRuleGC.setMarginItem3_S(new Double(paramResultSet.getDouble("MarginItem3_S")));
      localTradeBreedRuleGC.setMarginItem4_S(new Double(paramResultSet.getDouble("MarginItem4_S")));
      localTradeBreedRuleGC.setMarginItem5_S(new Double(paramResultSet.getDouble("MarginItem5_S")));
      localTradeBreedRuleGC.setMarginAssure_B(new Double(paramResultSet.getDouble("marginAssure_B")));
      localTradeBreedRuleGC.setMarginAssure_S(new Double(paramResultSet.getDouble("marginAssure_S")));
      localTradeBreedRuleGC.setMarginItemAssure1(new Double(paramResultSet.getDouble("marginItemAssure1")));
      localTradeBreedRuleGC.setMarginItemAssure1_S(new Double(paramResultSet.getDouble("marginItemAssure1_S")));
      localTradeBreedRuleGC.setMarginItemAssure2(new Double(paramResultSet.getDouble("marginItemAssure2")));
      localTradeBreedRuleGC.setMarginItemAssure2_S(new Double(paramResultSet.getDouble("marginItemAssure2_S")));
      localTradeBreedRuleGC.setMarginItemAssure3(new Double(paramResultSet.getDouble("marginItemAssure3")));
      localTradeBreedRuleGC.setMarginItemAssure3_S(new Double(paramResultSet.getDouble("marginItemAssure3_S")));
      localTradeBreedRuleGC.setMarginItemAssure4(new Double(paramResultSet.getDouble("marginItemAssure4")));
      localTradeBreedRuleGC.setMarginItemAssure4_S(new Double(paramResultSet.getDouble("marginItemAssure4_S")));
      localTradeBreedRuleGC.setMarginItemAssure5(new Double(paramResultSet.getDouble("marginItemAssure5")));
      localTradeBreedRuleGC.setMarginItemAssure5_S(new Double(paramResultSet.getDouble("marginItemAssure5_S")));
      localTradeBreedRuleGC.setSettleMarginAlgr_B(new Short(paramResultSet.getShort("SettleMarginAlgr_B")));
      localTradeBreedRuleGC.setSettleMarginAlgr_S(new Short(paramResultSet.getShort("SettleMarginAlgr_S")));
      localTradeBreedRuleGC.setSettleMarginRate_B(new Double(paramResultSet.getDouble("SettleMarginRate_B")));
      localTradeBreedRuleGC.setSettleMarginRate_S(new Double(paramResultSet.getDouble("SettleMarginRate_S")));
      localTradeBreedRuleGC.setPayoutAlgr(new Short(paramResultSet.getShort("PayoutAlgr")));
      localTradeBreedRuleGC.setPayoutRate(new Double(paramResultSet.getDouble("PayoutRate")));
      return localTradeBreedRuleGC;
    }
  }
  
  class CM_TradeBreedRuleRowMapper
    implements RowMapper
  {
    CM_TradeBreedRuleRowMapper() {}
    
    public Object mapRow(ResultSet paramResultSet, int paramInt)
      throws SQLException
    {
      return rsToTradeBreedRule(paramResultSet);
    }
    
    private TradeBreedRule rsToTradeBreedRule(ResultSet paramResultSet)
      throws SQLException
    {
      TradeBreedRule localTradeBreedRule = new TradeBreedRule();
      localTradeBreedRule.setCustomerID(paramResultSet.getString("FirmID"));
      localTradeBreedRule.setFeeDiscountRate(new Double(paramResultSet.getDouble("FeeDiscountRate")));
      localTradeBreedRule.setMarginDiscountRate(new Double(paramResultSet.getDouble("MarginDiscountRate")));
      return localTradeBreedRule;
    }
  }
  
  class GM_TradeBreedRuleRowMapper
    implements RowMapper
  {
    GM_TradeBreedRuleRowMapper() {}
    
    public Object mapRow(ResultSet paramResultSet, int paramInt)
      throws SQLException
    {
      return rsToTradeBreedRule(paramResultSet);
    }
    
    private TradeBreedRuleGC rsToTradeBreedRule(ResultSet paramResultSet)
      throws SQLException
    {
      TradeBreedRuleGC localTradeBreedRuleGC = new TradeBreedRuleGC();
      localTradeBreedRuleGC.setBreedID(paramResultSet.getString("CommodityID"));
      localTradeBreedRuleGC.setGroupID(paramResultSet.getString("GroupID"));
      localTradeBreedRuleGC.setFeeAlgr(new Short(paramResultSet.getShort("FeeAlgr")));
      localTradeBreedRuleGC.setFeeRate_B(new Double(paramResultSet.getDouble("FeeRate_B")));
      localTradeBreedRuleGC.setFeeRate_S(new Double(paramResultSet.getDouble("FeeRate_S")));
      localTradeBreedRuleGC.setMarginAlgr(new Short(paramResultSet.getShort("MarginAlgr")));
      localTradeBreedRuleGC.setMarginRate_B(new Double(paramResultSet.getDouble("MarginRate_B")));
      localTradeBreedRuleGC.setMarginRate_S(new Double(paramResultSet.getDouble("MarginRate_S")));
      localTradeBreedRuleGC.setMarginItem1(new Double(paramResultSet.getDouble("MarginItem1")));
      localTradeBreedRuleGC.setMarginItem2(new Double(paramResultSet.getDouble("MarginItem2")));
      localTradeBreedRuleGC.setMarginItem3(new Double(paramResultSet.getDouble("MarginItem3")));
      localTradeBreedRuleGC.setMarginItem4(new Double(paramResultSet.getDouble("MarginItem4")));
      localTradeBreedRuleGC.setMarginItem1_S(new Double(paramResultSet.getDouble("MarginItem1_S")));
      localTradeBreedRuleGC.setMarginItem2_S(new Double(paramResultSet.getDouble("MarginItem2_S")));
      localTradeBreedRuleGC.setMarginItem3_S(new Double(paramResultSet.getDouble("MarginItem3_S")));
      localTradeBreedRuleGC.setMarginItem4_S(new Double(paramResultSet.getDouble("MarginItem4_S")));
      localTradeBreedRuleGC.setMarginAssure_B(new Double(paramResultSet.getDouble("marginAssure_B")));
      localTradeBreedRuleGC.setMarginAssure_S(new Double(paramResultSet.getDouble("marginAssure_S")));
      localTradeBreedRuleGC.setMarginItemAssure1(new Double(paramResultSet.getDouble("marginItemAssure1")));
      localTradeBreedRuleGC.setMarginItemAssure1_S(new Double(paramResultSet.getDouble("marginItemAssure1_S")));
      localTradeBreedRuleGC.setMarginItemAssure2(new Double(paramResultSet.getDouble("marginItemAssure2")));
      localTradeBreedRuleGC.setMarginItemAssure2_S(new Double(paramResultSet.getDouble("marginItemAssure2_S")));
      localTradeBreedRuleGC.setMarginItemAssure3(new Double(paramResultSet.getDouble("marginItemAssure3")));
      localTradeBreedRuleGC.setMarginItemAssure3_S(new Double(paramResultSet.getDouble("marginItemAssure3_S")));
      localTradeBreedRuleGC.setMarginItemAssure4(new Double(paramResultSet.getDouble("marginItemAssure4")));
      localTradeBreedRuleGC.setMarginItemAssure4_S(new Double(paramResultSet.getDouble("marginItemAssure4_S")));
      localTradeBreedRuleGC.setTodayCloseFeeRate_B(new Double(paramResultSet.getDouble("TodayCloseFeeRate_B")));
      localTradeBreedRuleGC.setTodayCloseFeeRate_S(new Double(paramResultSet.getDouble("TodayCloseFeeRate_S")));
      localTradeBreedRuleGC.setHistoryCloseFeeRate_B(new Double(paramResultSet.getDouble("HistoryCloseFeeRate_B")));
      localTradeBreedRuleGC.setHistoryCloseFeeRate_S(new Double(paramResultSet.getDouble("HistoryCloseFeeRate_S")));
      localTradeBreedRuleGC.setSettleFeeAlgr(new Short(paramResultSet.getShort("SettleFeeAlgr")));
      localTradeBreedRuleGC.setSettleFeeRate_B(new Double(paramResultSet.getDouble("SettleFeeRate_B")));
      localTradeBreedRuleGC.setSettleFeeRate_S(new Double(paramResultSet.getDouble("SettleFeeRate_S")));
      localTradeBreedRuleGC.setForceCloseFeeAlgr(new Short(paramResultSet.getShort("ForceCloseFeeAlgr")));
      localTradeBreedRuleGC.setForceCloseFeeRate_B(new Double(paramResultSet.getDouble("ForceCloseFeeRate_B")));
      localTradeBreedRuleGC.setForceCloseFeeRate_S(new Double(paramResultSet.getDouble("ForceCloseFeeRate_S")));
      localTradeBreedRuleGC.setSettleMarginRate_B(new Double(paramResultSet.getDouble("SettleMarginRate_B")));
      localTradeBreedRuleGC.setSettleMarginRate_S(new Double(paramResultSet.getDouble("SettleMarginRate_S")));
      localTradeBreedRuleGC.setSettleMarginAlgr_B(new Short(paramResultSet.getShort("SettleMarginAlgr_B")));
      localTradeBreedRuleGC.setSettleMarginAlgr_S(new Short(paramResultSet.getShort("SettleMarginAlgr_S")));
      localTradeBreedRuleGC.setPayoutAlgr(new Short(paramResultSet.getShort("PayoutAlgr")));
      localTradeBreedRuleGC.setPayoutRate(new Double(paramResultSet.getDouble("PayoutRate")));
      return localTradeBreedRuleGC;
    }
  }
}

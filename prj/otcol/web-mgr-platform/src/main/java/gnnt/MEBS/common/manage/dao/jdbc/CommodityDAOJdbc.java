package gnnt.MEBS.common.manage.dao.jdbc;

import gnnt.MEBS.common.manage.dao.CommodityDAO;
import gnnt.MEBS.common.manage.model.Apply_T_CommodityFee;
import gnnt.MEBS.common.manage.model.Apply_T_CommodityMargin;
import java.io.PrintStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.commons.logging.Log;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

public class CommodityDAOJdbc
  extends JdbcDaoSupport
  implements CommodityDAO
{
  public void updateCommodityFee(Apply_T_CommodityFee paramApply_T_CommodityFee)
  {
    String str = "update t_commodity set feeAlgr=?,feeRate_B=?,feeRate_S=?,historyCloseFeeRate_B=?,historyCloseFeeRate_S=?,todayCloseFeeRate_B=?,todayCloseFeeRate_S=?,forceCloseFeeRate_B=?,forceCloseFeeRate_S=?,settleFeeAlgr=?,settleFeeRate_B=?,settleFeeRate_S=?,lowestSettleFee=? where commodityID = ?";
    Object[] arrayOfObject = { paramApply_T_CommodityFee.getFeeAlgr(), paramApply_T_CommodityFee.getFeeRate_B(), paramApply_T_CommodityFee.getFeeRate_S(), paramApply_T_CommodityFee.getHistoryCloseFeeRate_B(), paramApply_T_CommodityFee.getHistoryCloseFeeRate_S(), paramApply_T_CommodityFee.getTodayCloseFeeRate_B(), paramApply_T_CommodityFee.getTodayCloseFeeRate_S(), paramApply_T_CommodityFee.getForceCloseFeeRate_B(), paramApply_T_CommodityFee.getForceCloseFeeRate_S(), paramApply_T_CommodityFee.getSettleFeeAlgr(), paramApply_T_CommodityFee.getSettleFeeRate_B(), paramApply_T_CommodityFee.getSettleFeeRate_S(), paramApply_T_CommodityFee.getLowestSettleFee(), paramApply_T_CommodityFee.getCommodityID() };
    this.logger.debug("sql: " + str);
    getJdbcTemplate().update(str, arrayOfObject);
  }
  
  public void updateCommodityMargin(Apply_T_CommodityMargin paramApply_T_CommodityMargin)
  {
    SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    Date localDate1 = null;
    Date localDate2 = null;
    Date localDate3 = null;
    Date localDate4 = null;
    Date localDate5 = null;
    Date localDate6 = null;
    try
    {
      if ((paramApply_T_CommodityMargin.getSettleDate() != "null") && (!"".equals(paramApply_T_CommodityMargin.getSettleDate()))) {
        localDate1 = localSimpleDateFormat.parse(paramApply_T_CommodityMargin.getSettleDate());
      }
      if ((paramApply_T_CommodityMargin.getSettleDate1() != "null") && (!"".equals(paramApply_T_CommodityMargin.getSettleDate1()))) {
        localDate2 = localSimpleDateFormat.parse(paramApply_T_CommodityMargin.getSettleDate1());
      }
      System.out.println("app.getSettleDate2(): " + paramApply_T_CommodityMargin.getSettleDate2());
      if ((paramApply_T_CommodityMargin.getSettleDate2() != null) && (paramApply_T_CommodityMargin.getSettleDate2() != "null") && (!"".equals(paramApply_T_CommodityMargin.getSettleDate2())))
      {
        System.out.println("app.getSettleDate2() in: " + paramApply_T_CommodityMargin.getSettleDate2());
        localDate3 = localSimpleDateFormat.parse(paramApply_T_CommodityMargin.getSettleDate2());
      }
      if ((paramApply_T_CommodityMargin.getSettleDate3() != "null") && (!"".equals(paramApply_T_CommodityMargin.getSettleDate3()))) {
        localDate4 = localSimpleDateFormat.parse(paramApply_T_CommodityMargin.getSettleDate3());
      }
      if ((paramApply_T_CommodityMargin.getSettleDate4() != "null") && (!"".equals(paramApply_T_CommodityMargin.getSettleDate4()))) {
        localDate5 = localSimpleDateFormat.parse(paramApply_T_CommodityMargin.getSettleDate4());
      }
      if ((paramApply_T_CommodityMargin.getSettleDate5() != "null") && (!"".equals(paramApply_T_CommodityMargin.getSettleDate5()))) {
        localDate6 = localSimpleDateFormat.parse(paramApply_T_CommodityMargin.getSettleDate5());
      }
    }
    catch (ParseException localParseException)
    {
      localParseException.printStackTrace();
    }
    String str = "update t_commodity set marginAlgr=?,marginPriceType=?,settleDate1=?,marketDate=?,settleDate2=?,settleDate3=?,settleDate4=?,settleDate5=?,settleDate=?,marginItem1=?,marginItem2=?,marginItem3=?,marginItem4=?,marginItem5=?,marginItem1_S=?,marginItem2_S=?,marginItem3_S=?,marginItem4_S=?,marginItem5_S=?,marginItemAssure1=?,marginItemAssure2=?,marginItemAssure3=?,marginItemAssure4=?,marginItemAssure5=?,marginItemAssure1_S=?,marginItemAssure2_S=?,marginItemAssure3_S=?,marginItemAssure4_S=?,marginItemAssure5_S=?,settleMarginAlgr_B=?,settleMarginAlgr_S=?,settleMarginRate_B=?,settleMarginRate_S=?,payoutAlgr=?,payoutRate=? where commodityID = ?";
    Object[] arrayOfObject = { paramApply_T_CommodityMargin.getMarginAlgr(), paramApply_T_CommodityMargin.getMarginPriceType(), localDate2, localDate2, localDate3, localDate4, localDate5, localDate6, localDate1, paramApply_T_CommodityMargin.getMarginItem1(), paramApply_T_CommodityMargin.getMarginItem2(), paramApply_T_CommodityMargin.getMarginItem3(), paramApply_T_CommodityMargin.getMarginItem4(), paramApply_T_CommodityMargin.getMarginItem5(), paramApply_T_CommodityMargin.getMarginItem1_S(), paramApply_T_CommodityMargin.getMarginItem2_S(), paramApply_T_CommodityMargin.getMarginItem3_S(), paramApply_T_CommodityMargin.getMarginItem4_S(), paramApply_T_CommodityMargin.getMarginItem5_S(), paramApply_T_CommodityMargin.getMarginItemAssure1(), paramApply_T_CommodityMargin.getMarginItemAssure2(), paramApply_T_CommodityMargin.getMarginItemAssure3(), paramApply_T_CommodityMargin.getMarginItemAssure4(), paramApply_T_CommodityMargin.getMarginItemAssure5(), paramApply_T_CommodityMargin.getMarginItemAssure1_S(), paramApply_T_CommodityMargin.getMarginItemAssure2_S(), paramApply_T_CommodityMargin.getMarginItemAssure3_S(), paramApply_T_CommodityMargin.getMarginItemAssure4_S(), paramApply_T_CommodityMargin.getMarginItemAssure5_S(), paramApply_T_CommodityMargin.getSettleMarginAlgr_B(), paramApply_T_CommodityMargin.getSettleMarginAlgr_S(), paramApply_T_CommodityMargin.getSettleMarginRate_B(), paramApply_T_CommodityMargin.getSettleMarginRate_S(), paramApply_T_CommodityMargin.getPayoutAlgr(), paramApply_T_CommodityMargin.getPayoutRate(), paramApply_T_CommodityMargin.getCommodityID() };
    this.logger.debug("sql: " + str);
    getJdbcTemplate().update(str, arrayOfObject);
  }
  
  public int checkCommodityID(Apply_T_CommodityFee paramApply_T_CommodityFee)
  {
    int i = 0;
    String str = "select count(*) from t_commodity where commodityID = '" + paramApply_T_CommodityFee.getCommodityID() + "'";
    int j = getJdbcTemplate().queryForInt(str);
    if (j > 0) {
      i = 0;
    } else {
      i = 1;
    }
    return i;
  }
  
  public int checkMarginCommodityID(Apply_T_CommodityMargin paramApply_T_CommodityMargin)
  {
    int i = 0;
    String str = "select count(*) from t_commodity where commodityID = '" + paramApply_T_CommodityMargin.getCommodityID() + "'";
    int j = getJdbcTemplate().queryForInt(str);
    if (j > 0) {
      i = 0;
    } else {
      i = 1;
    }
    return i;
  }
}

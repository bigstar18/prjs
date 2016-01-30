package gnnt.MEBS.xhzc.manage.DAO;

import gnnt.MEBS.util.Configuration;
import gnnt.MEBS.xhzc.manage.CommodityParameterValue;
import gnnt.MEBS.xhzc.manage.CommodityPropertiesValue;
import gnnt.MEBS.xhzc.manage.CommodityValue;
import gnnt.MEBS.xhzc.manage.FirmValue;
import gnnt.MEBS.xhzc.manage.GroupValue;
import gnnt.MEBS.xhzc.manage.ManagerValue;
import gnnt.MEBS.xhzc.manage.ManagerlogValue;
import gnnt.MEBS.xhzc.manage.MarketConfigValue;
import gnnt.MEBS.xhzc.manage.QueryValue;
import gnnt.MEBS.xhzc.manage.TradeDateValue;
import gnnt.MEBS.xhzc.manage.TradeTimeValue;
import gnnt.MEBS.xhzc.manage.UserLogValue;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public abstract class ManageDAO
{
  protected DataSource dataSource = null;

  protected ManageDAO()
    throws NamingException
  {
    InitialContext ic = new InitialContext();
    Properties props = new Configuration().getSection("MEBS.ManageDataSource");
    this.dataSource = ((DataSource)ic.lookup(props.getProperty("JNDIName")));
  }

  protected Connection getConnection()
    throws SQLException
  {
    return this.dataSource.getConnection();
  }

  public void closeStatement(ResultSet rs, Statement state, Connection conn)
  {
    try
    {
      if (rs != null)
        rs.close();
    }
    catch (SQLException ex) {
      ex.printStackTrace();
    }
    try
    {
      if (state != null)
        state.close();
    }
    catch (SQLException ex) {
      ex.printStackTrace();
    }
    try {
      if (conn != null)
        conn.close();
    }
    catch (SQLException ex) {
      ex.printStackTrace();
    }
  }

  public abstract void addCommodity(CommodityValue paramCommodityValue)
    throws SQLException;

  public abstract CommodityValue getCommodity(String paramString)
    throws SQLException;

  public abstract CommodityValue getCommodityInfo(String paramString)
    throws SQLException;

  public abstract void modifyCommodity(CommodityValue paramCommodityValue)
    throws SQLException;

  public abstract void deleteCommodity(String paramString)
    throws SQLException;

  public abstract void deleteCommodity()
    throws SQLException;

  public abstract CommodityValue[] getCommodityList(QueryValue paramQueryValue)
    throws SQLException;

  public abstract int getCommodityCount(QueryValue paramQueryValue)
    throws SQLException;

  public abstract int getCommodityPGCount(QueryValue paramQueryValue)
    throws SQLException;

  public abstract void addFirm(FirmValue paramFirmValue)
    throws SQLException;

  public abstract FirmValue getFirm(String paramString)
    throws SQLException;

  public abstract void modifyFirm(FirmValue paramFirmValue)
    throws SQLException;

  public abstract void deleteFirm(String paramString)
    throws SQLException;

  public abstract void deleteFirm()
    throws SQLException;

  public abstract FirmValue[] getFirmList(QueryValue paramQueryValue)
    throws SQLException;

  public abstract int getFirmCount(QueryValue paramQueryValue)
    throws SQLException;

  public abstract int getFirmPGCount(QueryValue paramQueryValue)
    throws SQLException;

  public abstract void addGroup(GroupValue paramGroupValue)
    throws SQLException;

  public abstract GroupValue getGroup(String paramString)
    throws SQLException;

  public abstract void modifyGroup(GroupValue paramGroupValue)
    throws SQLException;

  public abstract void deleteGroup(String paramString)
    throws SQLException;

  public abstract void deleteGroup()
    throws SQLException;

  public abstract GroupValue[] getGroupList(QueryValue paramQueryValue)
    throws SQLException;

  public abstract int getGroupCount(QueryValue paramQueryValue)
    throws SQLException;

  public abstract int getGroupPGCount(QueryValue paramQueryValue)
    throws SQLException;

  public abstract void addManager(ManagerValue paramManagerValue)
    throws SQLException;

  public abstract ManagerValue getManager(String paramString)
    throws SQLException;

  public abstract void modifyManager(ManagerValue paramManagerValue)
    throws SQLException;

  public abstract void deleteManager(String paramString)
    throws SQLException;

  public abstract void deleteManager()
    throws SQLException;

  public abstract ManagerValue[] getManagerList(QueryValue paramQueryValue)
    throws SQLException;

  public abstract int getManagerCount(QueryValue paramQueryValue)
    throws SQLException;

  public abstract int getManagerPGCount(QueryValue paramQueryValue)
    throws SQLException;

  public abstract boolean checkManager(String paramString)
    throws SQLException;

  public abstract boolean checkManager(String paramString1, String paramString2)
    throws SQLException;

  public abstract boolean checkFirm(String paramString)
    throws SQLException;

  public abstract boolean checkFirm(String paramString1, String paramString2)
    throws SQLException;

  public abstract void addManagerLog(ManagerlogValue paramManagerlogValue)
    throws SQLException;

  public abstract ManagerlogValue getManagerLog(int paramInt)
    throws SQLException;

  public abstract void modifyManagerLog(ManagerlogValue paramManagerlogValue)
    throws SQLException;

  public abstract void deleteManagerLog(int paramInt)
    throws SQLException;

  public abstract void deleteManagerLog()
    throws SQLException;

  public abstract ManagerlogValue[] getManagerLogList(QueryValue paramQueryValue)
    throws SQLException;

  public abstract int getManagerLogCount(QueryValue paramQueryValue)
    throws SQLException;

  public abstract int getManagerLogPGCount(QueryValue paramQueryValue)
    throws SQLException;

  public abstract void addUserLog(UserLogValue paramUserLogValue)
    throws SQLException;

  public abstract UserLogValue getUserLog(int paramInt)
    throws SQLException;

  public abstract void modifyUserLog(UserLogValue paramUserLogValue)
    throws SQLException;

  public abstract void deleteUserLog(int paramInt)
    throws SQLException;

  public abstract void deleteUserLog()
    throws SQLException;

  public abstract UserLogValue[] getUserLogList(QueryValue paramQueryValue)
    throws SQLException;

  public abstract int getUserLogCount(QueryValue paramQueryValue)
    throws SQLException;

  public abstract int getUserLogPGCount(QueryValue paramQueryValue)
    throws SQLException;

  public abstract void addTradeDate(TradeDateValue paramTradeDateValue)
    throws SQLException;

  public abstract TradeDateValue getTradeDate(int paramInt)
    throws SQLException;

  public abstract void modifyTradeDate(TradeDateValue paramTradeDateValue)
    throws SQLException;

  public abstract void deleteTradeDate(int paramInt)
    throws SQLException;

  public abstract void deleteTradeDate()
    throws SQLException;

  public abstract TradeDateValue[] getTradeDateList(QueryValue paramQueryValue)
    throws SQLException;

  public abstract int getTradeDateCount(QueryValue paramQueryValue)
    throws SQLException;

  public abstract int getTradeDatePGCount(QueryValue paramQueryValue)
    throws SQLException;

  public abstract void addTradeTime(TradeTimeValue paramTradeTimeValue)
    throws SQLException;

  public abstract TradeTimeValue getTradeTime(int paramInt)
    throws SQLException;

  public abstract void modifyTradeTime(TradeTimeValue paramTradeTimeValue)
    throws SQLException;

  public abstract void deleteTradeTime(int paramInt)
    throws SQLException;

  public abstract void deleteTradeTime()
    throws SQLException;

  public abstract TradeTimeValue[] getTradeTimeList(QueryValue paramQueryValue)
    throws SQLException;

  public abstract int getTradeTimeCount(QueryValue paramQueryValue)
    throws SQLException;

  public abstract int getTradeTimePGCount(QueryValue paramQueryValue)
    throws SQLException;

  public abstract TradeTimeValue[] getAllTradeTime()
    throws SQLException;

  public abstract void addMarketConfig(MarketConfigValue paramMarketConfigValue)
    throws SQLException;

  public abstract MarketConfigValue getMarketConfig(String paramString)
    throws SQLException;

  public abstract void modifyMarketConfig(MarketConfigValue paramMarketConfigValue)
    throws SQLException;

  public abstract void deleteMarketConfig(String paramString)
    throws SQLException;

  public abstract void deleteMarketConfig()
    throws SQLException;

  public abstract MarketConfigValue[] getMarketConfigList(QueryValue paramQueryValue)
    throws SQLException;

  public abstract int getMarketConfigCount(QueryValue paramQueryValue)
    throws SQLException;

  public abstract int getMarketConfigPGCount(QueryValue paramQueryValue)
    throws SQLException;

  public abstract int[] getWeekRest()
    throws SQLException;

  public abstract int[] getYearRest()
    throws SQLException;

  public abstract void addCommodityProperties(CommodityPropertiesValue paramCommodityPropertiesValue)
    throws SQLException;

  public abstract CommodityPropertiesValue getCommodityProperties(int paramInt)
    throws SQLException;

  public abstract void modifyCommodityProperties(CommodityPropertiesValue paramCommodityPropertiesValue)
    throws SQLException;

  public abstract void deleteCommodityProperties(int paramInt)
    throws SQLException;

  public abstract void deleteCommodityProperties()
    throws SQLException;

  public abstract CommodityPropertiesValue[] getCommodityPropertiesList(QueryValue paramQueryValue)
    throws SQLException;

  public abstract int getCommodityPropertiesCount(QueryValue paramQueryValue)
    throws SQLException;

  public abstract int getCommodityPropertiesPGCount(QueryValue paramQueryValue)
    throws SQLException;

  public abstract void addCommodityParameter(CommodityParameterValue paramCommodityParameterValue)
    throws SQLException;

  public abstract CommodityParameterValue getCommodityParameter(String paramString, int paramInt)
    throws SQLException;

  public abstract void modifyCommodityParameter(CommodityParameterValue paramCommodityParameterValue)
    throws SQLException;

  public abstract void deleteCommodityParameter(String paramString, int paramInt)
    throws SQLException;

  public abstract void deleteCommodityParameter()
    throws SQLException;

  public abstract CommodityParameterValue[] getCommodityParameterList(QueryValue paramQueryValue)
    throws SQLException;

  public abstract int getCommodityParameterCount(QueryValue paramQueryValue)
    throws SQLException;

  public abstract int getCommodityParameterPGCount(QueryValue paramQueryValue)
    throws SQLException;

  public abstract void initCommodityCodeList()
    throws Exception;

  public abstract boolean checkCommodityCode(String paramString)
    throws Exception;

  public abstract boolean checkCommodityFinished()
    throws Exception;
}
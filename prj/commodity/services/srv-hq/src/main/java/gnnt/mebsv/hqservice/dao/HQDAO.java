package gnnt.mebsv.hqservice.dao;

import gnnt.mebsv.hqservice.model.DayDataVO;
import gnnt.mebsv.hqservice.model.MarketInfoVO;
import gnnt.mebsv.hqservice.model.ProductDataVO;
import gnnt.mebsv.hqservice.model.ProductInfoVO;
import gnnt.mebsv.hqservice.model.TradeTimeVO;
import gnnt.mebsv.hqservice.model.dictionary.AddrDictionary;
import gnnt.mebsv.hqservice.model.dictionary.IndustryDictionary;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public abstract class HQDAO
{
  public Properties params = null;
  public Connection conn = null;

  public Connection getConn()
    throws SQLException
  {
    if (this.conn == null)
      createConn();
    return this.conn;
  }

  private synchronized void createConn()
    throws SQLException
  {
    if (this.conn == null)
    {
      try
      {
        Class.forName(this.params.getProperty("DBDriver"));
      }
      catch (ClassNotFoundException localClassNotFoundException)
      {
        localClassNotFoundException.printStackTrace();
        throw new SQLException(localClassNotFoundException.toString());
      }
      String str1 = this.params.getProperty("DBUrl");
      String str2 = this.params.getProperty("DBPassword");
      String str3 = this.params.getProperty("DBUser");
      if ((str3 == null) || (str3.equals("")))
        this.conn = DriverManager.getConnection(str1);
      else
        this.conn = DriverManager.getConnection(str1, str3, str2);
    }
  }

  public void closeStatement(ResultSet paramResultSet, Statement paramStatement, Connection paramConnection)
  {
    try
    {
      if (paramResultSet != null)
        paramResultSet.close();
    }
    catch (SQLException localSQLException1)
    {
      localSQLException1.printStackTrace();
    }
    try
    {
      if (paramStatement != null)
        paramStatement.close();
    }
    catch (SQLException localSQLException2)
    {
      localSQLException2.printStackTrace();
    }
  }

  public abstract long queryQuotationNO()
    throws SQLException;

  public abstract HashMap queryTradeTimes()
    throws SQLException;

  public abstract void insertTradeTime(TradeTimeVO paramTradeTimeVO)
    throws SQLException;

  public abstract void updateTradeTime(int paramInt, TradeTimeVO paramTradeTimeVO)
    throws SQLException;

  public abstract void delTradeTime(int paramInt)
    throws SQLException;

  public abstract ProductInfoVO[] queryProductInfo(Date paramDate)
    throws SQLException;

  public abstract int getAllCurData(Hashtable paramHashtable, String paramString1, String paramString2, int paramInt)
    throws SQLException;

  public abstract Map getHqTime()
    throws SQLException;

  public abstract DayDataVO getPreDayData(String paramString, Date paramDate)
    throws SQLException;

  public abstract void updateRealData(ProductDataVO paramProductDataVO);

  public abstract void updateSeriesData(ProductDataVO paramProductDataVO);

  public abstract void insertSeriesData(ProductDataVO paramProductDataVO);

  public abstract DayDataVO getBaseDayData(String paramString, Date paramDate)
    throws SQLException;

  public abstract ArrayList getCo_class()
    throws SQLException;

  public abstract boolean isDataExist(String paramString, long paramLong, double paramDouble, int paramInt)
    throws SQLException;

  public abstract void initIndexData(ProductDataVO paramProductDataVO)
    throws SQLException;

  public abstract List getAllCommID()
    throws SQLException;

  public abstract float getctrtsize(String paramString);

  public abstract Date getHQDate();

  public abstract AddrDictionary getAddrDictionary(Date paramDate);

  public abstract IndustryDictionary getIndustryDictionary(Date paramDate);

  public abstract MarketInfoVO getMarketInfo(String paramString);
}
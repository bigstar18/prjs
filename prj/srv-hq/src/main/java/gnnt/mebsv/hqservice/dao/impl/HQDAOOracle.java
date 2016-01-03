package gnnt.mebsv.hqservice.dao.impl;

import gnnt.mebsv.hqservice.model.DayDataVO;
import gnnt.mebsv.hqservice.model.co_classVO;
import gnnt.mebsv.hqservice.tools.Util;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

public class HQDAOOracle extends HQDAOImpl
{
  public long getAverAmount5(String paramString)
    throws SQLException
  {
    long l = 0L;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    try
    {
      this.conn = getConn();
      String str = "SELECT MIN(TradeDate) FROM (SELECT TradeDate FROM HistoryDayData WHERE CommodityID = ? ORDER BY TradeDate DESC) WHERE rownum < 6";
      localPreparedStatement = this.conn.prepareStatement(str);
      localPreparedStatement.setString(1, paramString);
      localResultSet = localPreparedStatement.executeQuery();
      Timestamp localTimestamp = null;
      if (localResultSet.next())
        localTimestamp = localResultSet.getTimestamp(1);
      localResultSet.close();
      localPreparedStatement.close();
      str = "SELECT AVG(TotalAmount) FROM HistoryDayData WHERE CommodityID = ? AND TradeDate >= ?";
      localPreparedStatement = this.conn.prepareStatement(str);
      localPreparedStatement.setString(1, paramString);
      localPreparedStatement.setTimestamp(2, localTimestamp);
      localResultSet = localPreparedStatement.executeQuery();
      if (localResultSet.next())
        l = localResultSet.getLong(1);
    }
    catch (SQLException localSQLException)
    {
      localSQLException.printStackTrace();
    }
    finally
    {
      closeStatement(localResultSet, localPreparedStatement, null);
    }
    return l;
  }

  public DayDataVO getPreDayData(String paramString, Date paramDate)
    throws SQLException
  {
    DayDataVO localDayDataVO = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    paramDate.setHours(0);
    paramDate.setMinutes(0);
    paramDate.setSeconds(0);
    try
    {
      this.conn = getConn();
      String str = "SELECT * FROM (SELECT * FROM HistoryDayData WHERE CommodityID = ? AND TradeDate < ?  ORDER BY TradeDate DESC) WHERE rownum =1";
      localPreparedStatement = this.conn.prepareStatement(str);
      localPreparedStatement.setString(1, paramString);
      localPreparedStatement.setTimestamp(2, new Timestamp(paramDate.getTime()));
      localResultSet = localPreparedStatement.executeQuery();
      if (localResultSet.next())
      {
        localDayDataVO = new DayDataVO();
        localDayDataVO.time = localResultSet.getTimestamp("TradeDate");
        localDayDataVO.openPrice = localResultSet.getFloat("OpenPrice");
        localDayDataVO.highPrice = localResultSet.getFloat("HighPrice");
        localDayDataVO.lowPrice = localResultSet.getFloat("LowPrice");
        localDayDataVO.closePrice = localResultSet.getFloat("ClosePrice");
        localDayDataVO.balancePrice = localResultSet.getFloat("BalancePrice");
        localDayDataVO.totalAmount = localResultSet.getLong("TotalAmount");
        localDayDataVO.totalMoney = localResultSet.getFloat("TotalMoney");
        localDayDataVO.reserveCount = localResultSet.getInt("ReserveCount");
      }
    }
    finally
    {
      closeStatement(localResultSet, localPreparedStatement, null);
    }
    return localDayDataVO;
  }

  public DayDataVO getBaseDayData(String paramString, Date paramDate)
    throws SQLException
  {
    DayDataVO localDayDataVO = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    paramDate.setHours(0);
    paramDate.setMinutes(0);
    paramDate.setSeconds(0);
    try
    {
      this.conn = getConn();
      String str = "SELECT * FROM (SELECT * FROM HistoryDayData WHERE CommodityID = ? AND TradeDate >= ?  ORDER BY TradeDate) WHERE rownum =1";
      localPreparedStatement = this.conn.prepareStatement(str);
      localPreparedStatement.setString(1, paramString);
      localPreparedStatement.setTimestamp(2, new Timestamp(paramDate.getTime()));
      localResultSet = localPreparedStatement.executeQuery();
      if (localResultSet.next())
      {
        localDayDataVO = new DayDataVO();
        localDayDataVO.time = localResultSet.getTimestamp("TradeDate");
        localDayDataVO.openPrice = localResultSet.getFloat("OpenPrice");
        localDayDataVO.highPrice = localResultSet.getFloat("HighPrice");
        localDayDataVO.lowPrice = localResultSet.getFloat("LowPrice");
        localDayDataVO.closePrice = localResultSet.getFloat("ClosePrice");
        localDayDataVO.balancePrice = localResultSet.getFloat("BalancePrice");
        localDayDataVO.totalAmount = localResultSet.getLong("TotalAmount");
        localDayDataVO.totalMoney = localResultSet.getFloat("TotalMoney");
        localDayDataVO.reserveCount = localResultSet.getInt("ReserveCount");
      }
    }
    finally
    {
      closeStatement(localResultSet, localPreparedStatement, null);
    }
    return localDayDataVO;
  }

  public ArrayList getCo_class()
    throws SQLException
  {
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    ArrayList localArrayList = new ArrayList();
    try
    {
      this.conn = getConn();
      localPreparedStatement = this.conn.prepareStatement("SELECT nvl(a.CC_CLASSID,'--') as cc_classid,nvl(a.MARKETID,'--') as Market,nvl(a.CC_NAME,'--') as cc_name,nvl(a.CC_FULLNAME,'--') as cc_fullname,nvl(a.CC_REMARK,'--') as cc_remark,nvl(a.CC_PRICETYPE,0) as cc_pricetype,nvl(a.CC_DESC,'--') as cc_desc,nvl(a.CC_COMMODITY_ID,'--') as cc_commodity_id,nvl(a.MARKET_NAME,'--') as Market_name FROM co_class a,commodity b where a.cc_commodity_id=b.commodityid and b.STATUS='N'");
      localResultSet = localPreparedStatement.executeQuery();
      while (localResultSet.next())
      {
        co_classVO localco_classVO = new co_classVO();
        localco_classVO.setCc_classid(localResultSet.getString("cc_classid"));
        localco_classVO.setCc_name(localResultSet.getString("cc_name"));
        localco_classVO.setCc_fullname(Util.convertDBStr(localResultSet.getString("cc_fullname")));
        localco_classVO.setCc_remark(localResultSet.getString("cc_remark"));
        localco_classVO.setCc_pricetype(localResultSet.getInt("cc_pricetype"));
        localco_classVO.setCc_desc(localResultSet.getString("cc_desc"));
        localco_classVO.setMarket(localResultSet.getString("Market"));
        localco_classVO.setCc_commodity_id(localResultSet.getString("cc_commodity_id"));
        localco_classVO.setMarket_name(localResultSet.getString("Market_name"));
        localArrayList.add(localco_classVO);
      }
    }
    catch (SQLException localSQLException)
    {
      localSQLException.printStackTrace();
      throw localSQLException;
    }
    finally
    {
      closeStatement(localResultSet, localPreparedStatement, null);
    }
    return localArrayList;
  }
}
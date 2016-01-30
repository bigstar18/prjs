package gnnt.mebsv.hqtrans.dao.impl;

import gnnt.mebsv.hqtrans.dao.HQTransDAO;
import gnnt.mebsv.hqtrans.model.DayDataFile;
import gnnt.mebsv.hqtrans.tools.TransDate;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class HQTransDAOImpl
  extends HQTransDAO
{
  private static Log log = LogFactory.getLog(HQTransDAOImpl.class);
  TransDate transDate = TransDate.getInstance();
  
  public HashMap getAllProductCode(String paramString)
    throws SQLException
  {
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    HashMap localHashMap = new HashMap();
    try
    {
      localPreparedStatement = this.conn.prepareStatement("SELECT MarketID,commodityID FROM HistoryDayData where marketid=?  ORDER BY MarketID,commodityID ");
      localPreparedStatement.setString(1, paramString);
      localResultSet = localPreparedStatement.executeQuery();
      while (localResultSet.next())
      {
        String str1 = localResultSet.getString(1);
        String str2 = localResultSet.getString(2);
        ArrayList localArrayList = (ArrayList)localHashMap.get(str1);
        if (localArrayList != null)
        {
          localArrayList.add(str2);
          localHashMap.put(str1, localArrayList);
        }
        else
        {
          localArrayList = new ArrayList();
          localArrayList.add(str2);
          localHashMap.put(str1, localArrayList);
        }
      }
    }
    finally
    {
      closeStatement(localResultSet, localPreparedStatement);
    }
    return localHashMap;
  }
  
  public Map<String, Integer> getMarketStatus()
    throws SQLException
  {
    HashMap localHashMap = new HashMap();
    int i = -1;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    try
    {
      localPreparedStatement = this.conn.prepareStatement("SELECT TradeNO,TotalAmount,marketid FROM CurrentData WHERE commodityID = 'SYS' ");
      localResultSet = localPreparedStatement.executeQuery();
      while (localResultSet.next())
      {
        int j = localResultSet.getInt(1);
        int k = localResultSet.getInt(2);
        String str = localResultSet.getString(3);
        if (j >= 0) {
          i = -1;
        } else {
          i = k;
        }
        localHashMap.put(str, Integer.valueOf(i));
      }
    }
    finally
    {
      closeStatement(localResultSet, localPreparedStatement);
    }
    return localHashMap;
  }
  
  public DayDataFile[] getMinData(String paramString1, String paramString2, int paramInt1, int paramInt2)
    throws SQLException
  {
    Object localObject1 = this.transDate.yyyyMMddToDate(paramInt1);
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    String str;
    if (paramInt2 == 1) {
      str = " HistoryMinData ";
    } else if (paramInt2 == 5) {
      str = " History5MinData ";
    } else {
      str = " History5MinData ";
    }
    Vector localVector = new Vector();
    try
    {
      localPreparedStatement = this.conn.prepareStatement("SELECT TradeDate,OpenPrice,HighPrice,LowPrice,ClosePrice,BalancePrice,TotalAmount,TotalMoney,ReserveCount FROM   " + str + "WHERE MarketID = ? and commodityID = ? AND TradeDate >= ? ORDER BY TradeDate,MarketID");
      localPreparedStatement.setString(1, paramString1);
      localPreparedStatement.setString(2, paramString2);
      localPreparedStatement.setTimestamp(3, new Timestamp(((Date)localObject1).getTime()));
      localResultSet = localPreparedStatement.executeQuery();
      while (localResultSet.next())
      {
        DayDataFile localDayDataFile = new DayDataFile();
        localObject1 = localResultSet.getTimestamp("TradeDate");
        localDayDataFile.date = this.transDate.dateToyyMMddHHmm((Date)localObject1);
        localDayDataFile.openPrice = localResultSet.getFloat("OpenPrice");
        localDayDataFile.highPrice = localResultSet.getFloat("HighPrice");
        localDayDataFile.lowPrice = localResultSet.getFloat("LowPrice");
        localDayDataFile.closePrice = localResultSet.getFloat("ClosePrice");
        localDayDataFile.balancePrice = localResultSet.getFloat("BalancePrice");
        localDayDataFile.totalAmount = localResultSet.getLong("TotalAmount");
        localDayDataFile.totalMoney = localResultSet.getFloat("TotalMoney");
        localDayDataFile.reserveCount = localResultSet.getInt("ReserveCount");
        localVector.add(localDayDataFile);
      }
    }
    finally
    {
      closeStatement(localResultSet, localPreparedStatement);
    }
    return (DayDataFile[])localVector.toArray(new DayDataFile[localVector.size()]);
  }
  
  public DayDataFile[] getDayData(String paramString1, String paramString2, int paramInt)
    throws SQLException
  {
    Object localObject1 = this.transDate.yyyyMMddToDate(paramInt);
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    Vector localVector = new Vector();
    try
    {
      localPreparedStatement = this.conn.prepareStatement("SELECT TradeDate,OpenPrice,HighPrice,LowPrice,ClosePrice,BalancePrice,TotalAmount,TotalMoney,ReserveCount FROM HistoryDayData WHERE MarketID = ? AND commodityID = ? AND TradeDate >= ? ORDER BY TradeDate");
      localPreparedStatement.setString(1, paramString1);
      localPreparedStatement.setString(2, paramString2);
      localPreparedStatement.setTimestamp(3, new Timestamp(((Date)localObject1).getTime()));
      localResultSet = localPreparedStatement.executeQuery();
      while (localResultSet.next())
      {
        DayDataFile localDayDataFile = new DayDataFile();
        localObject1 = localResultSet.getTimestamp("TradeDate");
        localDayDataFile.date = this.transDate.dateToyyMMdd((Date)localObject1);
        localDayDataFile.openPrice = localResultSet.getFloat("OpenPrice");
        localDayDataFile.highPrice = localResultSet.getFloat("HighPrice");
        localDayDataFile.lowPrice = localResultSet.getFloat("LowPrice");
        localDayDataFile.closePrice = localResultSet.getFloat("ClosePrice");
        localDayDataFile.balancePrice = localResultSet.getFloat("BalancePrice");
        localDayDataFile.totalAmount = localResultSet.getLong("TotalAmount");
        localDayDataFile.totalMoney = localResultSet.getFloat("TotalMoney");
        localDayDataFile.reserveCount = localResultSet.getInt("ReserveCount");
        if ((localDayDataFile.openPrice > 0.0F) && (localDayDataFile.highPrice > 0.0F) && (localDayDataFile.lowPrice > 0.0F) && (localDayDataFile.closePrice > 0.0F)) {
          localVector.add(localDayDataFile);
        }
      }
    }
    finally
    {
      closeStatement(localResultSet, localPreparedStatement);
    }
    return (DayDataFile[])localVector.toArray(new DayDataFile[localVector.size()]);
  }
}

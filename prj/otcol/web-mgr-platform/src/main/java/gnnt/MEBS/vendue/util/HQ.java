package gnnt.MEBS.vendue.util;

import gnnt.MEBS.vendue.server.vo.QuotationValue;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;
import java.util.Vector;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class HQ
{
  public int PARTITIONID = 3;
  
  public HQ(int paramInt)
  {
    this.PARTITIONID = paramInt;
  }
  
  public Vector getQuotation()
  {
    Vector localVector = new Vector();
    QuotationValue localQuotationValue = null;
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    int i = -1000;
    int j = -1000;
    InitialContext localInitialContext1 = null;
    String str1 = "";
    try
    {
      localInitialContext1 = new InitialContext();
      Properties localProperties = new Configuration().getSection("MEBS.TradeDataSource");
      str1 = localProperties.getProperty("JNDIName");
      InitialContext localInitialContext2 = new InitialContext();
      Context localContext = (Context)localInitialContext2.lookup("java:/comp/env");
      DataSource localDataSource = (DataSource)localInitialContext1.lookup(str1);
      localConnection = localDataSource.getConnection();
      String str2 = "select status,section from v_sysCurStatus where tradePartition=" + this.PARTITIONID;
      localPreparedStatement = localConnection.prepareStatement(str2);
      localResultSet = localPreparedStatement.executeQuery();
      while (localResultSet.next())
      {
        j = localResultSet.getInt(1);
        i = localResultSet.getInt(2);
      }
      localResultSet.close();
      localResultSet = null;
      localPreparedStatement.close();
      localPreparedStatement = null;
      if (j == 2)
      {
        str2 = "select c.code,e.str1,e.str2,d.amount,e.str3,e.str4,d.beginprice,d.stepprice,t.price,t.countdownStart,t.countdownTime,sysdate,t.id,t.vaidAmount,e.str5,e.str6,e.str7,e.str8,e.str9,e.str10,e.str11,e.str12,e.str13,e.str14,e.str15,e.str16,e.str17,e.str18,e.str19,e.str20 from v_tradeQuotation t,v_curCommodity c,v_commodity d,v_commExt e where t.price(+)>=0 and c.bargainFlag=0 and c.section=" + i + " and d.id=e.commID(+) and d.ID=c.commodityID and t.code(+)=c.code and c.tradepartition=" + this.PARTITIONID + " and t.tradepartition(+)=" + this.PARTITIONID + " order by c.code";
        localPreparedStatement = localConnection.prepareStatement(str2);
        localResultSet = localPreparedStatement.executeQuery();
        while (localResultSet.next())
        {
          localQuotationValue = new QuotationValue();
          localQuotationValue.code = localResultSet.getString(1);
          localQuotationValue.str1 = localResultSet.getString(2);
          localQuotationValue.str2 = localResultSet.getString(3);
          localQuotationValue.amount = localResultSet.getLong(4);
          localQuotationValue.str3 = localResultSet.getString(5);
          localQuotationValue.str4 = localResultSet.getString(6);
          localQuotationValue.beginPrice = localResultSet.getDouble(7);
          localQuotationValue.stepPrice = localResultSet.getDouble(8);
          if (localResultSet.getLong(14) > 0L) {
            localQuotationValue.lastPrice = localResultSet.getDouble(9);
          }
          long l1 = 0L;
          if (localResultSet.getTimestamp(10) != null) {
            l1 = localResultSet.getTimestamp(10).getTime();
          }
          int k = localResultSet.getInt(11);
          long l2 = localResultSet.getTimestamp(12).getTime();
          if (k > 0)
          {
            int m = (int)(l1 + k * 1000 - l2) / 1000;
            if (m >= 0) {
              localQuotationValue.countDownTime = m;
            } else {
              localQuotationValue.countDownTime = 0;
            }
          }
          localQuotationValue.hqID = localResultSet.getLong(13);
          localQuotationValue.str5 = localResultSet.getString(15);
          localQuotationValue.str6 = localResultSet.getString(16);
          localQuotationValue.str7 = localResultSet.getString(17);
          localQuotationValue.str8 = localResultSet.getString(18);
          localQuotationValue.str9 = localResultSet.getString(19);
          localQuotationValue.str10 = localResultSet.getString(20);
          localQuotationValue.str11 = localResultSet.getString(21);
          localQuotationValue.str12 = localResultSet.getString(22);
          localQuotationValue.str13 = localResultSet.getString(23);
          localQuotationValue.str14 = localResultSet.getString(24);
          localQuotationValue.str15 = localResultSet.getString(25);
          localQuotationValue.str16 = localResultSet.getString(26);
          localQuotationValue.str17 = localResultSet.getString(27);
          localQuotationValue.str18 = localResultSet.getString(28);
          localQuotationValue.str19 = localResultSet.getString(29);
          localQuotationValue.str20 = localResultSet.getString(30);
          localVector.add(localQuotationValue);
        }
      }
    }
    catch (Exception localException3)
    {
      localException3.printStackTrace();
    }
    finally
    {
      if (localPreparedStatement != null)
      {
        try
        {
          localPreparedStatement.close();
        }
        catch (Exception localException6) {}
        localPreparedStatement = null;
      }
      try
      {
        localConnection.close();
      }
      catch (Exception localException7) {}
      localConnection = null;
    }
    return localVector;
  }
  
  public ObjSet getQuotation(int paramInt1, int paramInt2, String paramString)
  {
    ObjSet localObjSet = null;
    Vector localVector = new Vector();
    QuotationValue localQuotationValue = null;
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    int i = -1000;
    int j = -1000;
    String str1 = "";
    int k = (paramInt1 - 1) * paramInt2 + 1;
    int m = paramInt1 * paramInt2;
    int n = 0;
    try
    {
      Properties localProperties = new Configuration().getSection("MEBS.TradeDataSource");
      str1 = localProperties.getProperty("JNDIName");
      InitialContext localInitialContext = new InitialContext();
      Context localContext = (Context)localInitialContext.lookup("java:/comp/env");
      DataSource localDataSource = (DataSource)localContext.lookup(str1);
      localConnection = localDataSource.getConnection();
      String str2 = "select status,section from v_sysCurStatus where tradePartition=" + this.PARTITIONID;
      localPreparedStatement = localConnection.prepareStatement(str2);
      localResultSet = localPreparedStatement.executeQuery();
      while (localResultSet.next())
      {
        j = localResultSet.getInt(1);
        i = localResultSet.getInt(2);
      }
      localResultSet.close();
      localResultSet = null;
      localPreparedStatement.close();
      localPreparedStatement = null;
      if (j == 2)
      {
        str2 = "select count(*) from v_tradeQuotation t,v_curCommodity c,v_commodity d,v_commExt e where t.price(+)>=0 and c.bargainFlag=0 and c.section=" + i + " and d.id=e.commID(+) and d.ID=c.commodityID and t.code(+)=c.code and c.tradepartition=" + this.PARTITIONID + " and t.tradepartition(+)=" + this.PARTITIONID + " and " + paramString;
        localPreparedStatement = localConnection.prepareStatement(str2);
        localResultSet = localPreparedStatement.executeQuery();
        while (localResultSet.next()) {
          n = localResultSet.getInt(1);
        }
        localResultSet.close();
        localResultSet = null;
        localPreparedStatement.close();
        localPreparedStatement = null;
        str2 = "select code,str1,str2,amount,str3,beginprice,price,rown from (select code,str1,str2,amount,str3,beginprice,price,rownum rown from (select c.code,e.str1,e.str2,d.amount,e.str3,e.str4,d.beginprice,d.stepprice,t.price,t.countdownStart,t.countdownTime,sysdate,t.id,t.vaidAmount,e.str5 from v_tradeQuotation t,v_curCommodity c,v_commodity d,v_commExt e where t.price(+)>=0 and c.bargainFlag=0 and c.section=" + i + " and d.id=e.commID(+) and d.ID=c.commodityID and t.code(+)=c.code and c.tradepartition=" + this.PARTITIONID + " and t.tradepartition(+)=" + this.PARTITIONID + " and " + paramString + " order by c.code)) where rown between " + k + " and " + m + " ";
        localPreparedStatement = localConnection.prepareStatement(str2);
        localResultSet = localPreparedStatement.executeQuery();
        while (localResultSet.next())
        {
          localQuotationValue = new QuotationValue();
          localQuotationValue.code = localResultSet.getString(1);
          localQuotationValue.str1 = localResultSet.getString(2);
          localQuotationValue.str2 = localResultSet.getString(3);
          localQuotationValue.amount = localResultSet.getLong(4);
          localQuotationValue.str3 = localResultSet.getString(5);
          localQuotationValue.str4 = localResultSet.getString(6);
          localQuotationValue.beginPrice = localResultSet.getDouble(7);
          localQuotationValue.stepPrice = localResultSet.getDouble(8);
          if (localResultSet.getLong(14) > 0L) {
            localQuotationValue.lastPrice = localResultSet.getDouble(9);
          }
          long l1 = 0L;
          if (localResultSet.getTimestamp(10) != null) {
            l1 = localResultSet.getTimestamp(10).getTime();
          }
          int i2 = localResultSet.getInt(11);
          long l2 = localResultSet.getTimestamp(12).getTime();
          if (i2 > 0)
          {
            int i3 = (int)(l1 + i2 * 1000 - l2) / 1000;
            if (i3 >= 0) {
              localQuotationValue.countDownTime = i3;
            } else {
              localQuotationValue.countDownTime = 0;
            }
          }
          localQuotationValue.hqID = localResultSet.getLong(13);
          localQuotationValue.str5 = localResultSet.getString(15);
          localVector.add(localQuotationValue);
        }
        QuotationValue[] arrayOfQuotationValue = new QuotationValue[localVector.size()];
        for (int i1 = 0; i1 < localVector.size(); i1++) {
          arrayOfQuotationValue[i1] = ((QuotationValue)localVector.elementAt(i1));
        }
        localObjSet = ObjSet.getInstance(arrayOfQuotationValue, n, paramInt2, paramInt1);
      }
    }
    catch (Exception localException3)
    {
      localException3.printStackTrace();
    }
    finally
    {
      if (localPreparedStatement != null)
      {
        try
        {
          localPreparedStatement.close();
        }
        catch (Exception localException6) {}
        localPreparedStatement = null;
      }
      try
      {
        localConnection.close();
      }
      catch (Exception localException7) {}
      localConnection = null;
    }
    return localObjSet;
  }
  
  public Vector getRealQuotation()
  {
    Vector localVector = new Vector();
    QuotationValue localQuotationValue = null;
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    int i = -1000;
    int j = -1000;
    String str1 = "";
    InitialContext localInitialContext1 = null;
    try
    {
      localInitialContext1 = new InitialContext();
      Properties localProperties = new Configuration().getSection("MEBS.TradeDataSource");
      str1 = localProperties.getProperty("JNDIName");
      InitialContext localInitialContext2 = new InitialContext();
      Context localContext = (Context)localInitialContext2.lookup("java:/comp/env");
      DataSource localDataSource = (DataSource)localInitialContext1.lookup(str1);
      localConnection = localDataSource.getConnection();
      String str2 = "select status,section from v_sysCurStatus where tradePartition=" + this.PARTITIONID;
      localPreparedStatement = localConnection.prepareStatement(str2);
      localResultSet = localPreparedStatement.executeQuery();
      while (localResultSet.next())
      {
        j = localResultSet.getInt(1);
        i = localResultSet.getInt(2);
      }
      localResultSet.close();
      localResultSet = null;
      localPreparedStatement.close();
      localPreparedStatement = null;
      if (j == 2)
      {
        str2 = "select c.code,e.str1,e.str2,d.amount,e.str3,e.str4,d.beginprice,d.stepprice,t.price,t.countdownStart,t.countdownTime,sysdate,t.id,t.vaidAmount,e.str5,e.str6,e.str7,e.str8,e.str9,e.str10,e.str11,e.str12,e.str13,e.str14,e.str15,e.str16,e.str17,e.str18,e.str19,e.str20 from v_tradeQuotation t,v_curCommodity c,v_commodity d,v_commExt e where t.price>=0 and c.bargainFlag=0 and c.section=" + i + " and d.id=e.commID(+) and d.ID=c.commodityID and t.code=c.code and c.tradepartition=" + this.PARTITIONID + " and t.tradepartition=" + this.PARTITIONID + " order by c.code";
        localPreparedStatement = localConnection.prepareStatement(str2);
        localResultSet = localPreparedStatement.executeQuery();
        while (localResultSet.next())
        {
          localQuotationValue = new QuotationValue();
          localQuotationValue.code = localResultSet.getString(1);
          localQuotationValue.str1 = localResultSet.getString(2);
          localQuotationValue.str2 = localResultSet.getString(3);
          localQuotationValue.amount = localResultSet.getLong(4);
          localQuotationValue.str3 = localResultSet.getString(5);
          localQuotationValue.str4 = localResultSet.getString(6);
          localQuotationValue.beginPrice = localResultSet.getDouble(7);
          localQuotationValue.stepPrice = localResultSet.getDouble(8);
          if (localResultSet.getLong(14) > 0L) {
            localQuotationValue.lastPrice = localResultSet.getDouble(9);
          }
          long l1 = 0L;
          if (localResultSet.getTimestamp(10) != null) {
            l1 = localResultSet.getTimestamp(10).getTime();
          }
          int k = localResultSet.getInt(11);
          long l2 = localResultSet.getTimestamp(12).getTime();
          if (k > 0)
          {
            int m = (int)(l1 + k * 1000 - l2) / 1000;
            if (m >= 0) {
              localQuotationValue.countDownTime = m;
            } else {
              localQuotationValue.countDownTime = 0;
            }
          }
          localQuotationValue.hqID = localResultSet.getLong(13);
          localQuotationValue.str5 = localResultSet.getString(15);
          localQuotationValue.str6 = localResultSet.getString(16);
          localQuotationValue.str7 = localResultSet.getString(17);
          localQuotationValue.str8 = localResultSet.getString(18);
          localQuotationValue.str9 = localResultSet.getString(19);
          localQuotationValue.str10 = localResultSet.getString(20);
          localQuotationValue.str11 = localResultSet.getString(21);
          localQuotationValue.str12 = localResultSet.getString(22);
          localQuotationValue.str13 = localResultSet.getString(23);
          localQuotationValue.str14 = localResultSet.getString(24);
          localQuotationValue.str15 = localResultSet.getString(25);
          localQuotationValue.str16 = localResultSet.getString(26);
          localQuotationValue.str17 = localResultSet.getString(27);
          localQuotationValue.str18 = localResultSet.getString(28);
          localQuotationValue.str19 = localResultSet.getString(29);
          localQuotationValue.str20 = localResultSet.getString(30);
          localVector.add(localQuotationValue);
        }
      }
    }
    catch (Exception localException3)
    {
      localException3.printStackTrace();
    }
    finally
    {
      if (localPreparedStatement != null)
      {
        try
        {
          localPreparedStatement.close();
        }
        catch (Exception localException6) {}
        localPreparedStatement = null;
      }
      try
      {
        localConnection.close();
      }
      catch (Exception localException7) {}
      localConnection = null;
    }
    return localVector;
  }
  
  public Vector getDealedQuotation()
  {
    Vector localVector = new Vector();
    QuotationValue localQuotationValue = null;
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    int i = -1000;
    int j = -1000;
    String str1 = "";
    try
    {
      Properties localProperties = new Configuration().getSection("MEBS.TradeDataSource");
      str1 = localProperties.getProperty("JNDIName");
      InitialContext localInitialContext = new InitialContext();
      Context localContext = (Context)localInitialContext.lookup("java:/comp/env");
      DataSource localDataSource = (DataSource)localContext.lookup(str1);
      localConnection = localDataSource.getConnection();
      String str2 = "select status,section from v_sysCurStatus where tradePartition=" + this.PARTITIONID;
      localPreparedStatement = localConnection.prepareStatement(str2);
      localResultSet = localPreparedStatement.executeQuery();
      while (localResultSet.next())
      {
        j = localResultSet.getInt(1);
        i = localResultSet.getInt(2);
      }
      localResultSet.close();
      localResultSet = null;
      localPreparedStatement.close();
      localPreparedStatement = null;
      if (j == 2)
      {
        str2 = "select c.code,e.str1,e.str2,d.amount,e.str3,e.str4,d.beginprice,d.stepprice,e.str5,s.price from v_curCommodity c,v_commodity d,v_commExt e,v_curSubmit s where c.bargainFlag=1 and c.section=" + i + " and d.id=e.commID(+) and d.ID=c.commodityID and s.code=c.code and s.tradeFlag=1 and c.tradepartition=" + this.PARTITIONID + " and s.tradepartition=" + this.PARTITIONID + " order by c.code";
        localPreparedStatement = localConnection.prepareStatement(str2);
        localResultSet = localPreparedStatement.executeQuery();
        while (localResultSet.next())
        {
          localQuotationValue = new QuotationValue();
          localQuotationValue.code = localResultSet.getString(1);
          localQuotationValue.str1 = localResultSet.getString(2);
          localQuotationValue.str2 = localResultSet.getString(3);
          localQuotationValue.amount = localResultSet.getLong(4);
          localQuotationValue.str3 = localResultSet.getString(5);
          localQuotationValue.str4 = localResultSet.getString(6);
          localQuotationValue.beginPrice = localResultSet.getDouble(7);
          localQuotationValue.stepPrice = localResultSet.getDouble(8);
          localQuotationValue.str5 = localResultSet.getString(9);
          localQuotationValue.lastPrice = localResultSet.getDouble(10);
          localVector.add(localQuotationValue);
        }
      }
    }
    catch (Exception localException3)
    {
      localException3.printStackTrace();
    }
    finally
    {
      if (localPreparedStatement != null)
      {
        try
        {
          localPreparedStatement.close();
        }
        catch (Exception localException6) {}
        localPreparedStatement = null;
      }
      try
      {
        localConnection.close();
      }
      catch (Exception localException7) {}
      localConnection = null;
    }
    return localVector;
  }
}

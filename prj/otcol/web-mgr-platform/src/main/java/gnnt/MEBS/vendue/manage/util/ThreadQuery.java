package gnnt.MEBS.vendue.manage.util;

import gnnt.MEBS.vendue.manage.report.TraderDebt;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class ThreadQuery
  extends Thread
{
  private String jndi;
  private String filter;
  private int marketId;
  private String startDate;
  private String endDate;
  private ArrayList result;
  private ArrayList arrayAgr;
  private int startCuror;
  private int endCuror;
  private Boolean gameOver = Boolean.FALSE;
  
  public Boolean getGameOver()
  {
    return this.gameOver;
  }
  
  public void setJndi(String paramString)
  {
    this.jndi = paramString;
  }
  
  public String getJndi()
  {
    return this.jndi;
  }
  
  public void setFilter(String paramString)
  {
    this.filter = paramString;
  }
  
  public String getFilter()
  {
    return this.filter;
  }
  
  public void setMarketId(int paramInt)
  {
    this.marketId = paramInt;
  }
  
  public int getMarketId()
  {
    return this.marketId;
  }
  
  public void setStartDate(String paramString)
  {
    this.startDate = paramString;
  }
  
  public String getStartDate()
  {
    return this.startDate;
  }
  
  public void setEndDate(String paramString)
  {
    this.endDate = paramString;
  }
  
  public String getEndDate()
  {
    return this.endDate;
  }
  
  public void setArrayAgr(ArrayList paramArrayList)
  {
    this.arrayAgr = paramArrayList;
  }
  
  public ArrayList getArrayAgr()
  {
    return this.arrayAgr;
  }
  
  public void setStartCuror(int paramInt)
  {
    this.startCuror = paramInt;
  }
  
  public int getStartCuror()
  {
    return this.startCuror;
  }
  
  public void setEndCuror(int paramInt)
  {
    this.endCuror = paramInt;
  }
  
  public int getEndCuror()
  {
    return this.endCuror;
  }
  
  public void setResult(ArrayList paramArrayList)
  {
    this.result = paramArrayList;
  }
  
  public ArrayList getResult()
  {
    return this.result;
  }
  
  public void run()
  {
    System.out.println("线程启动......");
    queryTraderDebt();
    this.gameOver = Boolean.TRUE;
  }
  
  public void queryTraderDebt()
  {
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    StringBuffer localStringBuffer = new StringBuffer();
    double d = 0.0D;
    BigDecimal localBigDecimal = null;
    try
    {
      InitialContext localInitialContext = new InitialContext();
      Context localContext = (Context)localInitialContext.lookup("java:/comp/env");
      DataSource localDataSource = (DataSource)localContext.lookup(this.jndi);
      localConnection = localDataSource.getConnection();
      int i = 0;
      localStringBuffer.append("select partitionid from syspartition where rownum=1 and validflag=1");
      localPreparedStatement = localConnection.prepareStatement(localStringBuffer.toString());
      localResultSet = localPreparedStatement.executeQuery();
      if (localResultSet.next()) {
        i = localResultSet.getInt("partitionid");
      }
      localResultSet.close();
      localPreparedStatement.close();
      long l1 = 0L;
      long l2 = 0L;
      long l3 = 0L;
      long l4 = 0L;
      for (int j = this.startCuror; j < this.endCuror; j++)
      {
        l1 = System.currentTimeMillis();
        d = 0.0D;
        localBigDecimal = new BigDecimal(0);
        TraderDebt localTraderDebt = (TraderDebt)this.arrayAgr.get(j);
        l3 = System.currentTimeMillis();
        l4 = System.currentTimeMillis();
        System.out.println(" 查询欠款时间" + (l4 - l3) + "毫秒");
        if (d > 0.0D)
        {
          localTraderDebt.setDebt(d);
          localStringBuffer = new StringBuffer();
          localStringBuffer.append("select sum(t1.amount) v1,sum(t1.amount*t1.price) from hisbargain t1,commodity t2,commext t3 where t1.tradedate>=");
          localStringBuffer.append("to_date('" + this.startDate + " 00:00:00','yyyy-mm-dd hh24:mi:ss') and t1.tradedate<=");
          localStringBuffer.append("to_date('" + this.startDate + " 23:59:59','yyyy-mm-dd  hh24:mi:ss') and t1.userid='" + localTraderDebt.getTrader() + "'");
          localStringBuffer.append(" and t1.commodityid=t2.id and t2.id=t3.commid and t3.str7=" + this.marketId + "");
          localPreparedStatement = localConnection.prepareStatement(localStringBuffer.toString());
          localResultSet = localPreparedStatement.executeQuery();
          if (localResultSet.next())
          {
            localTraderDebt.setTradeAmount(ManaUtil.disBD(localResultSet.getBigDecimal(1)));
            localTraderDebt.setTradeMoney(ManaUtil.disBD(localResultSet.getBigDecimal(2)));
          }
          localResultSet.close();
          localPreparedStatement.close();
          localStringBuffer = new StringBuffer();
          localStringBuffer.append("select sum(t2.amount) v1 from hisbargain t1,outlog t2,commodity t3,commext t4 where t1.tradedate>=");
          localStringBuffer.append("to_date('" + this.startDate + " 00:00:00','yyyy-mm-dd hh24:mi:ss') and t1.tradedate<=");
          localStringBuffer.append("to_date('" + this.startDate + " 23:59:59','yyyy-mm-dd  hh24:mi:ss') and userid=");
          localStringBuffer.append("'" + localTraderDebt.getTrader() + "' and t1.contractid=t2.contractid and t2.finished=2");
          localStringBuffer.append(" and t1.commodityid=t3.id and t3.id=t4.commid and t4.str7=" + this.marketId + "");
          localPreparedStatement = localConnection.prepareStatement(localStringBuffer.toString());
          localResultSet = localPreparedStatement.executeQuery();
          if (localResultSet.next()) {
            localTraderDebt.setOutAmount(ManaUtil.disBD(localResultSet.getBigDecimal(1)));
          }
          localResultSet.close();
          localPreparedStatement.close();
          localStringBuffer = new StringBuffer();
          localStringBuffer.append("select sum(t2.money) v1 from hisbargain t1,hismoney t2 where t1.contractid=");
          localStringBuffer.append("t2.contractno and t2.operation=506 and t2.firmid='" + localTraderDebt.getTrader() + "'");
          localStringBuffer.append(" and t1.tradedate>=to_date('" + this.startDate + " 00:00:00','yyyy-mm-dd hh24:mi:ss')");
          localStringBuffer.append(" and t1.tradedate<=to_date('" + this.startDate + " 23:59:59','yyyy-mm-dd  hh24:mi:ss')");
          localStringBuffer.append(" and t2.market2=" + this.marketId + "");
          localPreparedStatement = localConnection.prepareStatement(localStringBuffer.toString());
          localResultSet = localPreparedStatement.executeQuery();
          if (localResultSet.next()) {
            localBigDecimal = ManaUtil.disBD(localResultSet.getBigDecimal(1));
          }
          localResultSet.close();
          localPreparedStatement.close();
          localTraderDebt.setArrivedPayment(localBigDecimal);
          this.result.add(localTraderDebt);
          l2 = System.currentTimeMillis();
          System.out.println("======>result size" + this.result.size() + " startCuror:" + this.startCuror + " endCuror:" + this.endCuror + " i:" + j + "查询时间:" + (l2 - l1) + "毫秒");
        }
      }
      return;
    }
    catch (Exception localException2)
    {
      localException2.printStackTrace();
    }
    finally
    {
      try
      {
        if (localResultSet != null) {
          localResultSet.close();
        }
        if (localConnection != null) {
          localConnection.close();
        }
        if (localPreparedStatement != null) {
          localPreparedStatement.close();
        }
      }
      catch (Exception localException4) {}
    }
  }
}

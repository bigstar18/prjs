package gnnt.MEBS.vendue.server;

import gnnt.MEBS.vendue.server.dao.TradeDAO;
import gnnt.MEBS.vendue.server.vo.CommodityVO;
import gnnt.MEBS.vendue.server.vo.TradeStatusValue;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Hashtable;

public abstract class KernelEngine
  implements Runnable
{
  protected int PARTITIONID;
  protected TradeStatusValue TRADESTATUS;
  protected Hashtable<String, CommodityVO> COMMODITYTABLE;
  protected TradeDAO TRADEDAO;
  
  public abstract void setPartition(int paramInt);
  
  protected abstract void setTradeStatus()
    throws SQLException;
  
  protected abstract void resumeTradeStatus()
    throws SQLException;
  
  public abstract void setTradeDAO(TradeDAO paramTradeDAO);
  
  protected abstract TradeDAO getTradeDAO();
  
  public abstract int getNewCountdown(String paramString);
  
  public abstract void touch(String paramString1, long paramLong1, double paramDouble, String paramString2, Timestamp paramTimestamp, long paramLong2);
  
  public abstract CommodityVO getCurCommodity(String paramString);
  
  public abstract CommodityVO[] getCurCommodityList();
  
  public abstract void reLoadCommodity()
    throws SQLException;
  
  public abstract TradeStatusValue getTradeStatus()
    throws SQLException;
  
  public abstract void loadCommodity()
    throws SQLException;
  
  public abstract int startTrade();
  
  public abstract int forceStartTrade();
  
  public abstract int manualStartTrade();
  
  public abstract int pauseTrade();
  
  public abstract int closeTrade();
  
  public abstract int continueTrade();
  
  public abstract int optTrade()
    throws SQLException;
  
  public abstract double getBail(String paramString1, double paramDouble, long paramLong, String paramString2, Connection paramConnection)
    throws SQLException;
  
  public abstract double getFee(String paramString1, double paramDouble, long paramLong, String paramString2, Connection paramConnection)
    throws SQLException;
  
  public abstract String getPartitionName()
    throws SQLException;
  
  public abstract int delCommodityCharge(long paramLong, Connection paramConnection)
    throws SQLException;
  
  public abstract int addCommodityCharge(String paramString, long paramLong, Connection paramConnection)
    throws SQLException;
}

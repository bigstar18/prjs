package gnnt.MEBS.vendue.server;

import gnnt.MEBS.vendue.server.dao.TradeDAO;
import gnnt.MEBS.vendue.server.vo.BargainVO;
import gnnt.MEBS.vendue.server.vo.CommodityVO;
import gnnt.MEBS.vendue.server.vo.MoneyVO;
import gnnt.MEBS.vendue.server.vo.TradeUserVO;
import java.sql.Connection;
import java.sql.SQLException;

public abstract class DeliveryAction
{
  protected static TradeDAO TRADEDAO;
  
  public abstract void convertBailToPrepayment(long paramLong)
    throws SQLException;
  
  public abstract void convertBailToPartitionPrePayment(int paramInt)
    throws SQLException;
  
  public abstract int convertPrePaymentToContract(long paramLong, double paramDouble, Connection paramConnection)
    throws SQLException;
  
  public abstract BargainVO getHisBargain(long paramLong)
    throws SQLException;
  
  public abstract CommodityVO getCommodity(long paramLong)
    throws SQLException;
  
  public abstract TradeUserVO getTradeUser(String paramString)
    throws SQLException;
  
  public abstract double getPaidMoneyForContract(long paramLong)
    throws SQLException;
  
  public abstract int addCommodityCharge(String paramString, long paramLong, int paramInt, Connection paramConnection)
    throws Exception;
  
  public abstract int delCommodityCharge(long paramLong, int paramInt, Connection paramConnection)
    throws Exception;
  
  public abstract MoneyVO[] getMoneyList(String paramString)
    throws SQLException;
  
  public abstract String getBuyerID(long paramLong)
    throws SQLException;
  
  public abstract String getSellerID(long paramLong)
    throws SQLException;
  
  public abstract int finishContract(long paramLong, int paramInt, double paramDouble1, double paramDouble2)
    throws SQLException;
}

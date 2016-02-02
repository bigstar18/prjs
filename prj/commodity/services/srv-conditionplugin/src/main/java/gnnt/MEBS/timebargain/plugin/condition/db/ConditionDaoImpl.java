package gnnt.MEBS.timebargain.plugin.condition.db;

import gnnt.MEBS.timebargain.plugin.condition.Config;
import gnnt.MEBS.timebargain.plugin.condition.model.ConditionOrder;
import gnnt.MEBS.timebargain.plugin.condition.model.Quotation;
import gnnt.MEBS.timebargain.plugin.condition.model.RmiConf;
import gnnt.MEBS.timebargain.plugin.condition.model.SystemStatus;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ConditionDaoImpl
  implements ConditionDao
{
  private final transient Log log = LogFactory.getLog(ConditionDaoImpl.class);
  private Connection conn = null;
  
  public ConditionDaoImpl() {}
  
  public ConditionDaoImpl(Config paramConfig)
  {
    getConnection(paramConfig);
  }
  
  public void getConnection(Config paramConfig)
  {
    try
    {
      if ((this.conn == null) || (this.conn.isClosed()))
      {
        String str1 = paramConfig.DBUrl;
        String str2 = paramConfig.DBDriver;
        Class.forName(str2);
        this.conn = DriverManager.getConnection(str1);
      }
    }
    catch (Exception localException)
    {
      this.log.error("DB error!");
      throw new RuntimeException(localException);
    }
  }
  
  public List<Quotation> getRuntimeQuotation(Timestamp paramTimestamp)
  {
    String str = "select * from T_Quotation where createtime > ?";
    ArrayList localArrayList = new ArrayList();
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    try
    {
      localPreparedStatement = this.conn.prepareStatement(str);
      localPreparedStatement.setTimestamp(1, paramTimestamp);
      localResultSet = localPreparedStatement.executeQuery();
      while (localResultSet.next())
      {
        Quotation localQuotation = new Quotation();
        localQuotation.setCommodityID(localResultSet.getString("CommodityID"));
        localQuotation.setPrice(Double.valueOf(localResultSet.getDouble("CurPrice")));
        localQuotation.setBuy1(localResultSet.getDouble("BuyPrice1"));
        localQuotation.setSell1(localResultSet.getDouble("SellPrice1"));
        localQuotation.setCreateTime(localResultSet.getTimestamp("createtime"));
        localArrayList.add(localQuotation);
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    finally
    {
      close(localPreparedStatement, localResultSet);
    }
    return localArrayList;
  }
  
  public long insertOrder(ConditionOrder paramConditionOrder)
  {
    this.log.debug("order:CmtyID=" + paramConditionOrder.getCmtyID() + ",ConditionCmtyID=" + paramConditionOrder.getConditionCmtyID());
    String str1 = "insert into T_ConditionOrder(A_OrderNo,CommodityID,CustomerID,TraderID,BS_Flag,OrderType,Price, Quantity,ConditionType,ConditionOperation,ConditionPrice,ConditionCommodityID,FirmID,UpdateTime,ValidDate) values(?,?,?,?,?,?,?,?,?,?,?,?,?,sysdate,?)";
    String str2 = "select SEQ_T_ConditionOrder.nextval orderNo from dual";
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    long l = 0L;
    try
    {
      localPreparedStatement = this.conn.prepareStatement(str2);
      localResultSet = localPreparedStatement.executeQuery();
      if (localResultSet.next()) {
        l = localResultSet.getLong("orderNo");
      }
      localPreparedStatement.close();
      localPreparedStatement = null;
      localPreparedStatement = this.conn.prepareStatement(str1);
      localPreparedStatement.setLong(1, l);
      localPreparedStatement.setString(2, paramConditionOrder.getCmtyID());
      localPreparedStatement.setString(3, paramConditionOrder.getCustomerID());
      localPreparedStatement.setString(4, paramConditionOrder.getTraderID());
      localPreparedStatement.setShort(5, paramConditionOrder.getBs_flag().shortValue());
      localPreparedStatement.setShort(6, paramConditionOrder.getOrderType().shortValue());
      localPreparedStatement.setDouble(7, paramConditionOrder.getPrice().doubleValue());
      localPreparedStatement.setLong(8, paramConditionOrder.getAmount().longValue());
      localPreparedStatement.setInt(9, paramConditionOrder.getConditionType().intValue());
      localPreparedStatement.setInt(10, paramConditionOrder.getConditionOperation().intValue());
      localPreparedStatement.setDouble(11, paramConditionOrder.getConditionPrice().doubleValue());
      localPreparedStatement.setString(12, paramConditionOrder.getConditionCmtyID());
      localPreparedStatement.setString(13, paramConditionOrder.getFirmID());
      localPreparedStatement.setDate(14, new java.sql.Date(paramConditionOrder.getEndDate().getTime()));
      localPreparedStatement.execute();
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    finally
    {
      close(localPreparedStatement, localResultSet);
    }
    return l;
  }
  
  public void updateOrder(ConditionOrder paramConditionOrder, Integer paramInteger1, Integer paramInteger2)
  {
    String str = "update T_ConditionOrder set  retcode = ?,SuccessTime=sysdate,SendStatus=? where A_OrderNo = ?";
    PreparedStatement localPreparedStatement = null;
    try
    {
      localPreparedStatement = this.conn.prepareStatement(str);
      localPreparedStatement.setInt(1, paramInteger1.intValue());
      localPreparedStatement.setInt(2, paramInteger2.intValue());
      localPreparedStatement.setLong(3, paramConditionOrder.getOrderNo().longValue());
      localPreparedStatement.execute();
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    finally
    {
      close(localPreparedStatement, null);
    }
  }
  
  public void load_CP_orders(HashMap<String, ArrayList<ConditionOrder>> paramHashMap)
  {
    String str1 = "select * from T_ConditionOrder where  ConditionType=1 and trunc(ValidDate)>=(select trunc(tradedate) from t_systemstatus) and SendStatus=0";
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    try
    {
      localPreparedStatement = this.conn.prepareStatement(str1);
      localResultSet = localPreparedStatement.executeQuery();
      while (localResultSet.next())
      {
        String str2 = localResultSet.getString("CommodityID");
        String str3 = localResultSet.getString("ConditionCommodityID");
        ArrayList localArrayList = (ArrayList)paramHashMap.get(str3);
        if (localArrayList == null) {
          localArrayList = new ArrayList();
        }
        ConditionOrder localConditionOrder = new ConditionOrder();
        localConditionOrder.setOrderNo(Long.valueOf(localResultSet.getLong("A_OrderNo")));
        localConditionOrder.setCmtyID(str2);
        localConditionOrder.setCustomerID(localResultSet.getString("CustomerID"));
        localConditionOrder.setTraderID(localResultSet.getString("TraderID"));
        localConditionOrder.setBs_flag(Short.valueOf(localResultSet.getShort("BS_Flag")));
        localConditionOrder.setOrderType(Short.valueOf(localResultSet.getShort("OrderType")));
        localConditionOrder.setPrice(Double.valueOf(localResultSet.getDouble("Price")));
        localConditionOrder.setAmount(Long.valueOf(localResultSet.getLong("Quantity")));
        localConditionOrder.setConditionType(Integer.valueOf(localResultSet.getInt("ConditionType")));
        localConditionOrder.setConditionOperation(Integer.valueOf(localResultSet.getInt("ConditionOperation")));
        localConditionOrder.setConditionPrice(Double.valueOf(localResultSet.getDouble("ConditionPrice")));
        localConditionOrder.setConditionCmtyID(str3);
        localConditionOrder.setFirmID(localResultSet.getString("FirmID"));
        localArrayList.add(localConditionOrder);
        paramHashMap.put(str3, localArrayList);
      }
    }
    catch (SQLException localSQLException)
    {
      localSQLException.printStackTrace();
    }
    finally
    {
      close(localPreparedStatement, localResultSet);
    }
  }
  
  public void load_Buy1_orders(HashMap<String, ArrayList<ConditionOrder>> paramHashMap)
  {
    String str1 = "select * from T_ConditionOrder where   ConditionType=2 and trunc(ValidDate)>=(select trunc(tradedate) from t_systemstatus) and SendStatus=0";
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    try
    {
      localPreparedStatement = this.conn.prepareStatement(str1);
      localResultSet = localPreparedStatement.executeQuery();
      while (localResultSet.next())
      {
        String str2 = localResultSet.getString("CommodityID");
        String str3 = localResultSet.getString("ConditionCommodityID");
        ArrayList localArrayList = (ArrayList)paramHashMap.get(str3);
        if (localArrayList == null) {
          localArrayList = new ArrayList();
        }
        ConditionOrder localConditionOrder = new ConditionOrder();
        localConditionOrder.setOrderNo(Long.valueOf(localResultSet.getLong("A_OrderNo")));
        localConditionOrder.setCmtyID(str2);
        localConditionOrder.setCustomerID(localResultSet.getString("CustomerID"));
        localConditionOrder.setTraderID(localResultSet.getString("TraderID"));
        localConditionOrder.setBs_flag(Short.valueOf(localResultSet.getShort("BS_Flag")));
        localConditionOrder.setOrderType(Short.valueOf(localResultSet.getShort("OrderType")));
        localConditionOrder.setPrice(Double.valueOf(localResultSet.getDouble("Price")));
        localConditionOrder.setAmount(Long.valueOf(localResultSet.getLong("Quantity")));
        localConditionOrder.setConditionType(Integer.valueOf(localResultSet.getInt("ConditionType")));
        localConditionOrder.setConditionOperation(Integer.valueOf(localResultSet.getInt("ConditionOperation")));
        localConditionOrder.setConditionPrice(Double.valueOf(localResultSet.getDouble("ConditionPrice")));
        localConditionOrder.setConditionCmtyID(str3);
        localConditionOrder.setFirmID(localResultSet.getString("FirmID"));
        localArrayList.add(localConditionOrder);
        paramHashMap.put(str3, localArrayList);
      }
    }
    catch (SQLException localSQLException)
    {
      localSQLException.printStackTrace();
    }
    finally
    {
      close(localPreparedStatement, localResultSet);
    }
  }
  
  public void load_Sell1_orders(HashMap<String, ArrayList<ConditionOrder>> paramHashMap)
  {
    String str1 = "select * from T_ConditionOrder where ConditionType=3 and trunc(ValidDate)>=(select trunc(tradedate) from t_systemstatus) and SendStatus=0";
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    try
    {
      localPreparedStatement = this.conn.prepareStatement(str1);
      localResultSet = localPreparedStatement.executeQuery();
      while (localResultSet.next())
      {
        String str2 = localResultSet.getString("CommodityID");
        String str3 = localResultSet.getString("ConditionCommodityID");
        ArrayList localArrayList = (ArrayList)paramHashMap.get(str3);
        if (localArrayList == null) {
          localArrayList = new ArrayList();
        }
        ConditionOrder localConditionOrder = new ConditionOrder();
        localConditionOrder.setOrderNo(Long.valueOf(localResultSet.getLong("A_OrderNo")));
        localConditionOrder.setCmtyID(str2);
        localConditionOrder.setCustomerID(localResultSet.getString("CustomerID"));
        localConditionOrder.setTraderID(localResultSet.getString("TraderID"));
        localConditionOrder.setBs_flag(Short.valueOf(localResultSet.getShort("BS_Flag")));
        localConditionOrder.setOrderType(Short.valueOf(localResultSet.getShort("OrderType")));
        localConditionOrder.setPrice(Double.valueOf(localResultSet.getDouble("Price")));
        localConditionOrder.setAmount(Long.valueOf(localResultSet.getLong("Quantity")));
        localConditionOrder.setConditionType(Integer.valueOf(localResultSet.getInt("ConditionType")));
        localConditionOrder.setConditionOperation(Integer.valueOf(localResultSet.getInt("ConditionOperation")));
        localConditionOrder.setConditionPrice(Double.valueOf(localResultSet.getDouble("ConditionPrice")));
        localConditionOrder.setConditionCmtyID(str3);
        localConditionOrder.setFirmID(localResultSet.getString("FirmID"));
        localArrayList.add(localConditionOrder);
        paramHashMap.put(str3, localArrayList);
      }
    }
    catch (SQLException localSQLException)
    {
      localSQLException.printStackTrace();
    }
    finally
    {
      close(localPreparedStatement, localResultSet);
    }
  }
  
  private void close(PreparedStatement paramPreparedStatement, ResultSet paramResultSet)
  {
    try
    {
      if (paramPreparedStatement != null) {
        paramPreparedStatement.close();
      }
      if (paramResultSet != null) {
        paramResultSet.close();
      }
    }
    catch (SQLException localSQLException)
    {
      localSQLException.printStackTrace();
    }
  }
  
  public SystemStatus getSystemStatus()
  {
    String str = "select * from T_SystemStatus";
    SystemStatus localSystemStatus = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    try
    {
      localPreparedStatement = this.conn.prepareStatement(str);
      localResultSet = localPreparedStatement.executeQuery();
      if (localResultSet.next())
      {
        localSystemStatus = new SystemStatus();
        localSystemStatus.setTradeDate(localResultSet.getDate("TradeDate"));
        localSystemStatus.setStatus(Integer.valueOf(localResultSet.getInt("Status")));
      }
    }
    catch (SQLException localSQLException)
    {
      localSQLException.printStackTrace();
    }
    finally
    {
      close(localPreparedStatement, localResultSet);
    }
    return localSystemStatus;
  }
  
  public void cancelOneOrder(ConditionOrder paramConditionOrder)
  {
    String str = "update T_ConditionOrder set UpdateTime = sysdate, SendStatus= ? where A_OrderNo = ?";
    this.log.debug("deleteOneOrder sql:" + str + "param:" + paramConditionOrder.getOrderNo());
    PreparedStatement localPreparedStatement = null;
    try
    {
      localPreparedStatement = this.conn.prepareStatement(str);
      localPreparedStatement.setInt(1, 2);
      localPreparedStatement.setLong(2, paramConditionOrder.getOrderNo().longValue());
      localPreparedStatement.execute();
    }
    catch (SQLException localSQLException)
    {
      localSQLException.printStackTrace();
    }
    finally
    {
      close(localPreparedStatement, null);
    }
  }
  
  public Quotation getSingleQuotation(String paramString)
  {
    String str = "select * from T_Quotation where CommodityID = ?";
    this.log.debug("sql:" + str + ",param:" + paramString);
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    Quotation localQuotation = null;
    try
    {
      localPreparedStatement = this.conn.prepareStatement(str);
      localPreparedStatement.setString(1, paramString);
      localResultSet = localPreparedStatement.executeQuery();
      if (localResultSet.next())
      {
        localQuotation = new Quotation();
        localQuotation.setCommodityID(localResultSet.getString("CommodityID"));
        localQuotation.setPrice(Double.valueOf(localResultSet.getDouble("CurPrice")));
        localQuotation.setBuy1(localResultSet.getDouble("BuyPrice1"));
        localQuotation.setSell1(localResultSet.getDouble("SellPrice1"));
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    finally
    {
      close(localPreparedStatement, localResultSet);
    }
    return localQuotation;
  }
  
  public void backUpOrder()
  {
    String str1 = "select TradeDate  from T_SystemStatus";
    String str2 = "insert into t_h_conditionorder select  ?,a.*  from t_conditionorder a  where sendstatus<>0 or trunc(validdate)<=trunc(?)";
    String str3 = "delete t_conditionorder where sendstatus<>0 or trunc(validdate)<=trunc(?)";
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    java.sql.Date localDate = null;
    try
    {
      localPreparedStatement = this.conn.prepareStatement(str1);
      localResultSet = localPreparedStatement.executeQuery();
      if (localResultSet.next()) {
        localDate = localResultSet.getDate("TradeDate");
      }
      localPreparedStatement.close();
      localPreparedStatement = null;
      localPreparedStatement = this.conn.prepareStatement(str2);
      localPreparedStatement.setDate(1, localDate);
      localPreparedStatement.setDate(2, localDate);
      localPreparedStatement.execute();
      localPreparedStatement.close();
      localPreparedStatement = null;
      localPreparedStatement = this.conn.prepareStatement(str3);
      localPreparedStatement.setDate(1, localDate);
      localPreparedStatement.execute();
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    finally
    {
      close(localPreparedStatement, localResultSet);
    }
  }
  
  public RmiConf getRmiConf(int paramInt)
  {
    RmiConf localRmiConf = new RmiConf();
    String str = "select * from T_RmiConf where serviceId = ? and enabled = 'Y'";
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    try
    {
      localPreparedStatement = this.conn.prepareStatement(str);
      localPreparedStatement.setInt(1, paramInt);
      localResultSet = localPreparedStatement.executeQuery();
      if (localResultSet.next())
      {
        localRmiConf.setHostIP(localResultSet.getString("HOSTIP"));
        localRmiConf.setPort(localResultSet.getInt("PORT"));
        localRmiConf.setRmiDataPort(localResultSet.getInt("RMIDATAPORT"));
        localRmiConf.setServiceName("ConditionRMI");
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return localRmiConf;
  }
  
  public RmiConf getTradeRmi(int paramInt)
  {
    RmiConf localRmiConf = new RmiConf();
    String str = "select * from c_trademodule where moduleId = ?";
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    try
    {
      localPreparedStatement = this.conn.prepareStatement(str);
      localPreparedStatement.setInt(1, paramInt);
      localResultSet = localPreparedStatement.executeQuery();
      if (localResultSet.next())
      {
        localRmiConf.setModuleID(15);
        localRmiConf.setHostIP(localResultSet.getString("HOSTIP"));
        localRmiConf.setPort(localResultSet.getInt("PORT"));
        System.out.println(localRmiConf.getHostIP() + "  " + localRmiConf.getPort());
        localRmiConf.setServiceName("TradeRMI");
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return localRmiConf;
  }
}

package gnnt.MEBS.vendue.server.dao;

import gnnt.MEBS.vendue.server.vo.BargainVO;
import gnnt.MEBS.vendue.server.vo.BroadcastVO;
import gnnt.MEBS.vendue.server.vo.CommodityPropertyVO;
import gnnt.MEBS.vendue.server.vo.CommodityTypeVO;
import gnnt.MEBS.vendue.server.vo.CommodityVO;
import gnnt.MEBS.vendue.server.vo.CurSubmitVO;
import gnnt.MEBS.vendue.server.vo.FlowControlVO;
import gnnt.MEBS.vendue.server.vo.MoneyVO;
import gnnt.MEBS.vendue.server.vo.QueryValue;
import gnnt.MEBS.vendue.server.vo.SysCurStatusVO;
import gnnt.MEBS.vendue.server.vo.SysPartitionVO;
import gnnt.MEBS.vendue.server.vo.SysPropertyVO;
import gnnt.MEBS.vendue.server.vo.TradeQuotationVO;
import gnnt.MEBS.vendue.server.vo.TradeUserVO;
import gnnt.MEBS.vendue.util.Configuration;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public abstract class TradeDAO
{
  protected String pd = null;
  protected DataSource dataSource = null;
  protected String url = null;
  protected String username = null;
  protected String password = null;
  
  protected TradeDAO()
    throws NamingException
  {
    InitialContext localInitialContext = null;
    String str = "";
    try
    {
      localInitialContext = new InitialContext();
      Properties localProperties = new Configuration().getSection("MEBS.TradeDataSource");
      str = localProperties.getProperty("JNDIName");
      this.dataSource = ((DataSource)localInitialContext.lookup(str));
      localProperties = new Configuration().getSection("MEBS.DBConnection");
      this.url = ("" + localProperties.getProperty("Url"));
      this.username = ("" + localProperties.getProperty("Username"));
      this.password = ("" + localProperties.getProperty("Password"));
      this.pd = new Configuration().getSection("MEBS.TradeParams").getProperty("PD");
    }
    catch (NamingException localNamingException)
    {
      localNamingException.printStackTrace();
      throw localNamingException;
    }
  }
  
  public String getPD()
    throws SQLException
  {
    return this.pd;
  }
  
  public DataSource getDataSource()
  {
    return this.dataSource;
  }
  
  public Connection getConnection()
    throws SQLException
  {
    return this.dataSource.getConnection();
  }
  
  public Connection getJDBCConnection()
    throws SQLException, ClassNotFoundException
  {
    try
    {
      Class.forName("oracle.jdbc.driver.OracleDriver");
    }
    catch (ClassNotFoundException localClassNotFoundException)
    {
      localClassNotFoundException.printStackTrace();
      throw localClassNotFoundException;
    }
    return DriverManager.getConnection(this.url, this.username, this.password);
  }
  
  public void closeStatement(ResultSet paramResultSet, Statement paramStatement, Connection paramConnection)
  {
    try
    {
      if (paramResultSet != null) {
        paramResultSet.close();
      }
    }
    catch (SQLException localSQLException1)
    {
      localSQLException1.printStackTrace();
    }
    try
    {
      if (paramStatement != null) {
        paramStatement.close();
      }
    }
    catch (SQLException localSQLException2)
    {
      localSQLException2.printStackTrace();
    }
    try
    {
      if (paramConnection != null) {
        paramConnection.close();
      }
    }
    catch (SQLException localSQLException3)
    {
      localSQLException3.printStackTrace();
    }
  }
  
  public abstract long addCurSubmit(CurSubmitVO paramCurSubmitVO, Connection paramConnection)
    throws SQLException;
  
  public abstract void modifyCurSubmit(CurSubmitVO paramCurSubmitVO, Connection paramConnection)
    throws SQLException;
  
  public abstract CurSubmitVO getCurSubmit(long paramLong)
    throws SQLException;
  
  public abstract CurSubmitVO[] getCurSubmitList(String paramString)
    throws SQLException;
  
  public abstract CurSubmitVO[] getCurSubmitList(String paramString, Connection paramConnection)
    throws SQLException;
  
  public abstract void delCurSubmit(long paramLong)
    throws SQLException;
  
  public abstract void delCurSubmit(String paramString, Connection paramConnection)
    throws SQLException;
  
  public abstract void addTradeQuotation(TradeQuotationVO paramTradeQuotationVO, Connection paramConnection)
    throws SQLException;
  
  public abstract void addQuotationWithCountdownstart(TradeQuotationVO paramTradeQuotationVO, Connection paramConnection)
    throws SQLException;
  
  public abstract void addTradeQuotation(TradeQuotationVO paramTradeQuotationVO)
    throws SQLException;
  
  public abstract TradeQuotationVO[] getTradeQuotationList(String paramString, Connection paramConnection)
    throws SQLException;
  
  public abstract void delTradeQuotation(String paramString, Connection paramConnection)
    throws SQLException;
  
  public abstract String getContractTemplet(int paramInt, Connection paramConnection)
    throws SQLException;
  
  public abstract long addBargain(BargainVO paramBargainVO, Connection paramConnection)
    throws SQLException;
  
  public abstract void modBargainContent(BargainVO paramBargainVO, Connection paramConnection)
    throws SQLException;
  
  public abstract void delBargain(String paramString, Connection paramConnection)
    throws SQLException;
  
  public abstract BargainVO[] getBargainList(String paramString)
    throws SQLException;
  
  public abstract BargainVO[] getCurBargainList(int paramInt1, int paramInt2, Connection paramConnection)
    throws SQLException;
  
  public abstract BargainVO getCurBargain(long paramLong)
    throws SQLException;
  
  public abstract BargainVO getHisBargain(long paramLong)
    throws SQLException;
  
  public abstract BargainVO[] getHisBargainList(int paramInt)
    throws SQLException;
  
  public abstract BargainVO[] getBargainList(String paramString, Connection paramConnection)
    throws SQLException;
  
  public abstract FlowControlVO[] getFlowControlList(String paramString, Connection paramConnection)
    throws SQLException;
  
  public abstract CommodityVO getCommodity(long paramLong)
    throws SQLException;
  
  public abstract CommodityVO getCommodity(long paramLong, Connection paramConnection)
    throws SQLException;
  
  public abstract CommodityVO[] getCurCommodityList(String paramString, Connection paramConnection)
    throws SQLException;
  
  public abstract void delCurCommodity(String paramString, Connection paramConnection)
    throws SQLException;
  
  public abstract void dealCommodity(long paramLong, Connection paramConnection)
    throws SQLException;
  
  public abstract CommodityVO[] getCurCommodityList(int paramInt)
    throws SQLException;
  
  public abstract SysPropertyVO getSysProperty(int paramInt, Connection paramConnection)
    throws SQLException;
  
  public abstract SysPropertyVO getSysProperty(int paramInt)
    throws SQLException;
  
  public abstract void modifySysCurStatus(SysCurStatusVO paramSysCurStatusVO, Connection paramConnection)
    throws SQLException;
  
  public abstract void modifySysCurStatus(SysCurStatusVO paramSysCurStatusVO)
    throws SQLException;
  
  public abstract SysCurStatusVO getSysCurStatus(int paramInt)
    throws SQLException;
  
  public abstract SysPartitionVO[] getSysPartitionList(String paramString)
    throws SQLException;
  
  public abstract void addHisCommodity(Timestamp paramTimestamp, CommodityVO paramCommodityVO, Connection paramConnection)
    throws SQLException;
  
  public abstract void addHisBargain(BargainVO paramBargainVO, Connection paramConnection)
    throws SQLException;
  
  public abstract void modifyHisBargain(BargainVO paramBargainVO, Connection paramConnection)
    throws SQLException;
  
  public abstract BargainVO getHisBargain(long paramLong, Connection paramConnection)
    throws SQLException;
  
  public abstract void addHisSubmit(Timestamp paramTimestamp, CurSubmitVO paramCurSubmitVO, Connection paramConnection)
    throws SQLException;
  
  public abstract void modifyTradeUser(TradeUserVO paramTradeUserVO, Connection paramConnection)
    throws SQLException;
  
  public abstract void modifyTradeUser(TradeUserVO paramTradeUserVO)
    throws SQLException;
  
  public abstract TradeUserVO getTradeUser(String paramString, Connection paramConnection)
    throws SQLException;
  
  public abstract String getfirmId(String paramString)
    throws SQLException;
  
  public abstract TradeUserVO getTradeUser(String paramString)
    throws SQLException;
  
  public abstract void releaseTraderFrozencapital(Connection paramConnection)
    throws SQLException;
  
  public abstract TradeUserVO getTradeUserForUpdate(String paramString, Connection paramConnection)
    throws SQLException;
  
  public abstract void delTotalSecurity(Connection paramConnection)
    throws SQLException;
  
  public abstract BroadcastVO getBroadcast(long paramLong)
    throws SQLException;
  
  public abstract BroadcastVO[] getBroadcastList(String paramString)
    throws SQLException;
  
  public abstract void addDailymoney(MoneyVO paramMoneyVO, Connection paramConnection)
    throws SQLException;
  
  public abstract void truncateDailymoney(Connection paramConnection)
    throws SQLException;
  
  public abstract void selectIntoHismoney(Connection paramConnection)
    throws SQLException;
  
  public abstract MoneyVO[] getMoneyList(String paramString)
    throws SQLException;
  
  public abstract MoneyVO[] getMoneyList(String paramString, Connection paramConnection)
    throws SQLException;
  
  public abstract CommodityTypeVO[] getCommodityType(QueryValue paramQueryValue)
    throws SQLException;
  
  public abstract int getCommodityTypeCount(QueryValue paramQueryValue)
    throws SQLException;
  
  public abstract int getCommodityTypePGCount(QueryValue paramQueryValue)
    throws SQLException;
  
  public abstract void deleteCommodityType(CommodityTypeVO paramCommodityTypeVO)
    throws SQLException;
  
  public abstract void addCommodityType(CommodityTypeVO paramCommodityTypeVO)
    throws SQLException;
  
  public abstract void modifyCommodityType(CommodityTypeVO paramCommodityTypeVO)
    throws SQLException;
  
  public abstract CommodityPropertyVO[] getCommodityProperty(QueryValue paramQueryValue)
    throws SQLException;
  
  public abstract void deleteCommodityProperty(CommodityPropertyVO paramCommodityPropertyVO)
    throws SQLException;
  
  public abstract void addCommodityProperty(CommodityPropertyVO paramCommodityPropertyVO)
    throws SQLException;
  
  public abstract void modifyCommodityProperty(CommodityPropertyVO paramCommodityPropertyVO)
    throws SQLException;
  
  public abstract CommodityVO[] getCommodityList(String paramString, int paramInt)
    throws SQLException;
  
  public abstract List makeTradeBID(int paramInt, Connection paramConnection)
    throws SQLException;
  
  public abstract List judgeCommodity(Connection paramConnection, long paramLong1, String paramString, double paramDouble, long paramLong2)
    throws SQLException, ClassNotFoundException, IllegalAccessException, InstantiationException, NamingException;
  
  public abstract double getBargainMoney(long paramLong)
    throws SQLException;
  
  public abstract int bargainMod(String paramString1, String paramString2)
    throws SQLException;
  
  public abstract void addBargainTmp(String paramString, int paramInt)
    throws SQLException;
  
  public abstract Map getRMIUrl()
    throws SQLException;
}

package gnnt.trade.bank.dao;

import gnnt.trade.bank.util.Configuration;
import gnnt.trade.bank.vo.BankValue;
import gnnt.trade.bank.vo.CapitalCompare;
import gnnt.trade.bank.vo.CapitalMoneyVO;
import gnnt.trade.bank.vo.CapitalValue;
import gnnt.trade.bank.vo.CityValue;
import gnnt.trade.bank.vo.ClearingStatusVO;
import gnnt.trade.bank.vo.CompareResult;
import gnnt.trade.bank.vo.CompareSumMoney;
import gnnt.trade.bank.vo.CorrespondValue;
import gnnt.trade.bank.vo.DicValue;
import gnnt.trade.bank.vo.FeeValue;
import gnnt.trade.bank.vo.FirmAuditValue;
import gnnt.trade.bank.vo.FirmBalanceValue;
import gnnt.trade.bank.vo.FirmBankMsg;
import gnnt.trade.bank.vo.FirmFundsBankValue;
import gnnt.trade.bank.vo.FirmFundsValue;
import gnnt.trade.bank.vo.FirmMessageVo;
import gnnt.trade.bank.vo.FirmValue;
import gnnt.trade.bank.vo.InterfaceLog;
import gnnt.trade.bank.vo.LogValue;
import gnnt.trade.bank.vo.MoneyInfoValue;
import gnnt.trade.bank.vo.SystemStatusVO;
import gnnt.trade.bank.vo.bankdz.BankQSVO;
import gnnt.trade.bank.vo.bankdz.jh.sent.CCBQSResult;
import gnnt.trade.bank.vo.bankdz.jh.sent.DateVirtualFunds;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Vector;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public abstract class BankDAO
{
  private String DBUrl;
  private String DBUser;
  private String DBPwd;
  private String JNDIName;
  private String DBConnType;
  protected DataSource dataSource = null;
  public int[] pageinfo = new int[4];
  
  protected BankDAO()
    throws Exception
  {
    try
    {
      Properties props = new Configuration().getSection("BANK.Processor");
      this.DBUrl = props.getProperty("DBUrl");
      this.DBUser = props.getProperty("DBUser");
      this.DBPwd = props.getProperty("DBPassword");
      this.JNDIName = props.getProperty("JNDIName");
      this.DBConnType = props.getProperty("DBConnType");
    }
    catch (Exception e)
    {
      throw e;
    }
    if (this.DBConnType.equalsIgnoreCase("JNDIName"))
    {
      InitialContext ic = null;
      try
      {
        ic = new InitialContext();
        this.dataSource = ((DataSource)ic.lookup(this.JNDIName));
      }
      catch (NamingException e)
      {
        throw e;
      }
    }
  }
  
  public abstract Vector<FirmValue> getFirmList3(String paramString1, String paramString2, String paramString3, String paramString4)
    throws SQLException, ClassNotFoundException;
  
  public DataSource getDataSource()
  {
    return this.dataSource;
  }
  
  public Connection getConnection()
    throws SQLException, ClassNotFoundException
  {
    if (this.DBConnType.equalsIgnoreCase("JNDIName")) {
      return this.dataSource.getConnection();
    }
    try
    {
      Class.forName("oracle.jdbc.driver.OracleDriver");
    }
    catch (ClassNotFoundException e)
    {
      throw e;
    }
    return DriverManager.getConnection(this.DBUrl, this.DBUser, this.DBPwd);
  }
  
  public void closePreparedStatement(ResultSet rs, Vector<PreparedStatement> ve, Connection conn)
  {
    try
    {
      if (rs != null) {
        rs.close();
      }
    }
    catch (SQLException ex)
    {
      ex.printStackTrace();
    }
    for (PreparedStatement state : ve) {
      try
      {
        if (state != null) {
          state.close();
        }
      }
      catch (SQLException ex)
      {
        ex.printStackTrace();
      }
    }
    try
    {
      if (conn != null) {
        conn.close();
      }
    }
    catch (SQLException ex)
    {
      ex.printStackTrace();
    }
  }
  
  public void closeStatement(ResultSet rs, Statement state, Connection conn)
  {
    try
    {
      if (rs != null) {
        rs.close();
      }
    }
    catch (SQLException ex)
    {
      ex.printStackTrace();
    }
    try
    {
      if (state != null) {
        state.close();
      }
    }
    catch (SQLException ex)
    {
      ex.printStackTrace();
    }
    try
    {
      if (conn != null) {
        conn.close();
      }
    }
    catch (SQLException ex)
    {
      ex.printStackTrace();
    }
  }
  
  public abstract long getActionID()
    throws SQLException, ClassNotFoundException;
  
  public abstract long getActionID(Connection paramConnection)
    throws SQLException;
  
  public abstract SystemStatusVO getSystemStatus()
    throws SQLException, ClassNotFoundException;
  
  public abstract SystemStatusVO getSystemStatus(Connection paramConnection)
    throws SQLException;
  
  public abstract int addClearing(ClearingStatusVO paramClearingStatusVO)
    throws SQLException, ClassNotFoundException;
  
  public abstract int modClearing(ClearingStatusVO paramClearingStatusVO)
    throws SQLException, ClassNotFoundException;
  
  public abstract ClearingStatusVO getMaxClearing(String paramString)
    throws SQLException, ClassNotFoundException;
  
  public abstract Vector<DicValue> getDicList(String paramString)
    throws SQLException, ClassNotFoundException;
  
  public abstract Vector<DicValue> getDicList(String paramString, Connection paramConnection)
    throws SQLException;
  
  public abstract Vector<BankValue> getBankList(String paramString)
    throws SQLException, ClassNotFoundException;
  
  public abstract Vector<BankValue> getBankList2(String paramString, int[] paramArrayOfInt)
    throws SQLException, ClassNotFoundException;
  
  public abstract Vector<BankValue> getBankList(String paramString, Connection paramConnection)
    throws SQLException;
  
  public abstract int modBank(BankValue paramBankValue)
    throws SQLException, ClassNotFoundException;
  
  public abstract int chBankValid(Vector<String> paramVector, int paramInt)
    throws SQLException, ClassNotFoundException;
  
  public abstract int chBankInMoney(Vector<String> paramVector, int paramInt)
    throws SQLException, ClassNotFoundException;
  
  public abstract int chBankOutMoney(Vector<String> paramVector, int paramInt)
    throws SQLException, ClassNotFoundException;
  
  public abstract BankValue getBank(String paramString)
    throws SQLException, ClassNotFoundException;
  
  public abstract BankValue getBank(String paramString, Connection paramConnection)
    throws SQLException;
  
  public abstract Vector<FirmValue> getFirmList(String paramString)
    throws SQLException, ClassNotFoundException;
  
  public abstract Vector<FirmValue> getFirmList2(String paramString)
    throws SQLException, ClassNotFoundException;
  
  public abstract FirmValue getFirm(String paramString)
    throws SQLException, ClassNotFoundException;
  
  public abstract FirmValue getFirm(String paramString, Connection paramConnection)
    throws SQLException;
  
  public abstract int modFirmStatus(FirmValue paramFirmValue)
    throws SQLException, ClassNotFoundException;
  
  public abstract int modFirmStatus(FirmValue paramFirmValue, Connection paramConnection)
    throws SQLException;
  
  public abstract int delFirm(String paramString)
    throws SQLException, ClassNotFoundException;
  
  public abstract int modFirmPassword(FirmValue paramFirmValue)
    throws SQLException, ClassNotFoundException;
  
  public abstract int modFirmPassword(FirmValue paramFirmValue, Connection paramConnection)
    throws SQLException;
  
  public abstract FirmBalanceValue availableBalance(String paramString)
    throws SQLException, ClassNotFoundException;
  
  public abstract FirmBalanceValue availableBalance(String paramString, Connection paramConnection)
    throws SQLException;
  
  public abstract Vector<CorrespondValue> getCorrespondList(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5);
  
  public abstract Vector<CorrespondValue> getCorrespondList(String paramString)
    throws SQLException, ClassNotFoundException;
  
  public abstract Vector<CorrespondValue> getCorrespondList(String paramString, Connection paramConnection)
    throws SQLException;
  
  public abstract Vector<CorrespondValue> getCorrespondList2(String paramString, int[] paramArrayOfInt)
    throws SQLException, ClassNotFoundException;
  
  public abstract Vector<FirmBankMsg> getfirmBankMsg(String paramString)
    throws SQLException, ClassNotFoundException;
  
  public abstract CorrespondValue getCorrespond(String paramString1, String paramString2, String paramString3)
    throws SQLException, ClassNotFoundException;
  
  public abstract CorrespondValue getCorrespond(String paramString1, String paramString2, String paramString3, Connection paramConnection)
    throws SQLException;
  
  public abstract int countFirmAccount(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5)
    throws SQLException, ClassNotFoundException;
  
  public abstract int countFirmAccount(String paramString)
    throws SQLException, ClassNotFoundException;
  
  public abstract int addCorrespond(CorrespondValue paramCorrespondValue)
    throws SQLException, ClassNotFoundException;
  
  public abstract int addCorrespond(CorrespondValue paramCorrespondValue, Connection paramConnection)
    throws SQLException;
  
  public abstract int modCorrespond(CorrespondValue paramCorrespondValue)
    throws SQLException, ClassNotFoundException;
  
  public abstract int modCorrespond(CorrespondValue paramCorrespondValue, Connection paramConnection)
    throws SQLException;
  
  public abstract int openCorrespond(CorrespondValue paramCorrespondValue)
    throws SQLException, ClassNotFoundException;
  
  public abstract int openCorrespond(CorrespondValue paramCorrespondValue, Connection paramConnection)
    throws SQLException;
  
  public abstract int modCorrespondStatus(CorrespondValue paramCorrespondValue)
    throws SQLException, ClassNotFoundException;
  
  public abstract int modBankFrozenFuns(String paramString, double paramDouble, Connection paramConnection)
    throws SQLException;
  
  public abstract int delCorrespond(CorrespondValue paramCorrespondValue)
    throws SQLException, ClassNotFoundException;
  
  public abstract int delCorrespond(CorrespondValue paramCorrespondValue, Connection paramConnection)
    throws SQLException;
  
  public abstract int destroyAccount(CorrespondValue paramCorrespondValue)
    throws SQLException, ClassNotFoundException;
  
  public abstract int destroyAccount(CorrespondValue paramCorrespondValue, Connection paramConnection)
    throws SQLException;
  
  public abstract int useBank(String paramString, int paramInt)
    throws SQLException, ClassNotFoundException;
  
  public abstract boolean getTradeDate(Date paramDate)
    throws SQLException, ClassNotFoundException;
  
  public abstract boolean getTraderStatus()
    throws SQLException, ClassNotFoundException;
  
  public abstract FirmMessageVo getFirmMSG(String paramString)
    throws SQLException, ClassNotFoundException;
  
  public abstract long addCapitalInfo(CapitalValue paramCapitalValue, Connection paramConnection)
    throws SQLException;
  
  public abstract Vector<CapitalValue> getCapitalInfoList(String paramString)
    throws SQLException, ClassNotFoundException;
  
  public abstract Vector<CapitalValue> getCapitalInfoList(String paramString, Connection paramConnection)
    throws SQLException;
  
  public abstract CapitalValue getCapitalInfo(String paramString)
    throws SQLException, ClassNotFoundException;
  
  public abstract int modCapitalInfoStatus(long paramLong, String paramString, int paramInt, Timestamp paramTimestamp, Connection paramConnection)
    throws SQLException;
  
  public abstract int modCapitalInfoNote(long paramLong, String paramString, Connection paramConnection)
    throws SQLException;
  
  public abstract int modCapitalInfoTrader(long paramLong, String paramString, Connection paramConnection)
    throws SQLException;
  
  public abstract Vector<CapitalValue> getCapitalInfoList2(String paramString, int[] paramArrayOfInt)
    throws SQLException, ClassNotFoundException;
  
  public abstract Vector<CapitalMoneyVO> getCapitalInfoMoney(String paramString)
    throws SQLException, ClassNotFoundException;
  
  public abstract Vector<MoneyInfoValue> getMoneyInfoList(String paramString)
    throws SQLException, ClassNotFoundException;
  
  public abstract Vector<MoneyInfoValue> getMoneyInfoList(String paramString, Connection paramConnection)
    throws SQLException;
  
  public abstract Vector<LogValue> logList(String paramString, int[] paramArrayOfInt);
  
  public abstract void log(LogValue paramLogValue);
  
  public abstract Vector<InterfaceLog> interfaceLogList(String paramString, int[] paramArrayOfInt)
    throws SQLException, ClassNotFoundException;
  
  public abstract int interfaceLog(InterfaceLog paramInterfaceLog)
    throws SQLException, ClassNotFoundException;
  
  public abstract double sumCapitalInfo(String paramString, Connection paramConnection)
    throws SQLException;
  
  public abstract long addMoneyInfo(MoneyInfoValue paramMoneyInfoValue)
    throws SQLException, ClassNotFoundException;
  
  public abstract long addMoneyInfo(MoneyInfoValue paramMoneyInfoValue, Connection paramConnection)
    throws SQLException;
  
  public abstract int delMoneyInfo(String paramString, Connection paramConnection)
    throws SQLException;
  
  public abstract Vector<MoneyInfoValue> qureyBankCompareInfo(String paramString)
    throws SQLException, ClassNotFoundException;
  
  public abstract Vector<CompareResult> getBankNoInfo(String paramString, Date paramDate, int[] paramArrayOfInt)
    throws SQLException, ClassNotFoundException;
  
  public abstract Vector<CompareResult> getBankNoInfo(String paramString, Date paramDate, Connection paramConnection, int[] paramArrayOfInt)
    throws SQLException;
  
  public abstract Vector<CompareResult> getMarketNoInfo(String paramString, Date paramDate, int[] paramArrayOfInt)
    throws SQLException, ClassNotFoundException;
  
  public abstract Vector<CompareResult> getMarketNoInfo(String paramString, Date paramDate, Connection paramConnection, int[] paramArrayOfInt)
    throws SQLException;
  
  public abstract Vector<CompareSumMoney> sumCompareMoney(String paramString, Date paramDate)
    throws SQLException, ClassNotFoundException;
  
  public abstract Vector<CapitalCompare> sumResultInfo(String paramString, String[] paramArrayOfString, Date paramDate)
    throws SQLException, ClassNotFoundException;
  
  public abstract Vector<FirmFundsBankValue> getFirmFundsBank(String paramString)
    throws SQLException, ClassNotFoundException;
  
  public abstract FirmFundsValue getFirmFunds(String paramString)
    throws SQLException, ClassNotFoundException;
  
  public abstract int modCapitalInfoStatus(long paramLong, String paramString, Connection paramConnection)
    throws SQLException;
  
  public abstract Vector<CityValue> getCityNames(String paramString)
    throws SQLException, Exception;
  
  public abstract Map<String, Double> getAllVirtualFunds(String paramString, Connection paramConnection)
    throws SQLException;
  
  public abstract Date getMaxvirtualfunds(String paramString, Date paramDate, Connection paramConnection)
    throws SQLException;
  
  public abstract Map<String, Double> getQsdateVirtualFunds(Date paramDate, Connection paramConnection)
    throws SQLException;
  
  public abstract int addDateVirtualFunds(DateVirtualFunds paramDateVirtualFunds, Connection paramConnection)
    throws SQLException;
  
  public abstract int modBankQS(BankQSVO paramBankQSVO, Connection paramConnection)
    throws SQLException;
  
  public abstract long delCCBQSResult(String paramString, Date paramDate, Connection paramConnection)
    throws SQLException;
  
  public abstract long addCCBQSResult(CCBQSResult paramCCBQSResult, Connection paramConnection)
    throws SQLException;
  
  public abstract FirmAuditValue getFirmAuditValue(String paramString, Connection paramConnection)
    throws SQLException;
  
  public abstract FirmAuditValue getFirmAuditValue(String paramString)
    throws SQLException, ClassNotFoundException;
  
  public abstract String getFirmType(String paramString, Connection paramConnection)
    throws SQLException;
  
  public abstract Vector<FirmAuditValue> getFirmAuditValue1(String paramString, int[] paramArrayOfInt)
    throws SQLException, ClassNotFoundException;
  
  public abstract int modFirmAuditValue(FirmAuditValue paramFirmAuditValue, Connection paramConnection)
    throws SQLException;
  
  public abstract int modFirmAuditValue(FirmAuditValue paramFirmAuditValue)
    throws SQLException, ClassNotFoundException;
  
  public abstract List<String> getRights(String paramString)
    throws SQLException;
  
  public abstract Vector<FeeValue> getFeeList(String paramString)
    throws SQLException, ClassNotFoundException;
}

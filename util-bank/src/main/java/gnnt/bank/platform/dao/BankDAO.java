package gnnt.bank.platform.dao;

import gnnt.bank.adapter.bankBusiness.dayData.FCS_10_Result;
import gnnt.bank.adapter.bankBusiness.dayData.FCS_11_Result;
import gnnt.bank.adapter.bankBusiness.dayData.FCS_13_Result;
import gnnt.bank.adapter.bankBusiness.dayData.FCS_99;
import gnnt.bank.adapter.bankBusiness.message.CEB_param;
import gnnt.bank.platform.util.Tool;
import gnnt.bank.platform.vo.CapitalValueMoney;
import gnnt.bank.platform.vo.CheckMessage;
import gnnt.trade.bank.vo.AbcInfoValue;
import gnnt.trade.bank.vo.Account;
import gnnt.trade.bank.vo.BankCode;
import gnnt.trade.bank.vo.BankCompareInfoValue;
import gnnt.trade.bank.vo.BankQSNetChild;
import gnnt.trade.bank.vo.BankSumDate;
import gnnt.trade.bank.vo.BankTransferValue;
import gnnt.trade.bank.vo.BankValue;
import gnnt.trade.bank.vo.CapitalCompare;
import gnnt.trade.bank.vo.CapitalValue;
import gnnt.trade.bank.vo.CityValue;
import gnnt.trade.bank.vo.CompareResult;
import gnnt.trade.bank.vo.CorrespondValue;
import gnnt.trade.bank.vo.DicValue;
import gnnt.trade.bank.vo.FeeInfoVO;
import gnnt.trade.bank.vo.Firm;
import gnnt.trade.bank.vo.FirmBalanceValue;
import gnnt.trade.bank.vo.FirmBalance_CEB;
import gnnt.trade.bank.vo.FirmBankFunds;
import gnnt.trade.bank.vo.FirmFundsValue;
import gnnt.trade.bank.vo.FirmID2SysFirmID;
import gnnt.trade.bank.vo.FirmMessageVo;
import gnnt.trade.bank.vo.FirmOpenCloseBank;
import gnnt.trade.bank.vo.FirmTradeStatus;
import gnnt.trade.bank.vo.FirmValue;
import gnnt.trade.bank.vo.FrozenBalanceVO;
import gnnt.trade.bank.vo.FundsAndInterests;
import gnnt.trade.bank.vo.InterfaceLog;
import gnnt.trade.bank.vo.LogValue;
import gnnt.trade.bank.vo.MFirmValue;
import gnnt.trade.bank.vo.MoneyInfoValue;
import gnnt.trade.bank.vo.QSChangeResult;
import gnnt.trade.bank.vo.QSRresult;
import gnnt.trade.bank.vo.QueryTradeData;
import gnnt.trade.bank.vo.RZQS;
import gnnt.trade.bank.vo.RgstCapitalValue;
import gnnt.trade.bank.vo.SystemMessage;
import gnnt.trade.bank.vo.SystemQSValue;
import gnnt.trade.bank.vo.TradeDetailAccount;
import gnnt.trade.bank.vo.TradeResultValue;
import gnnt.trade.bank.vo.TransMnyObjValue;
import gnnt.trade.bank.vo.bankdz.BankQSVO;
import gnnt.trade.bank.vo.bankdz.TransferBank;
import gnnt.trade.bank.vo.bankdz.boc.AccountStatusReconciliation;
import gnnt.trade.bank.vo.bankdz.boc.ClientState;
import gnnt.trade.bank.vo.bankdz.boc.StorageMoneyLedgerBalanceList;
import gnnt.trade.bank.vo.bankdz.boc.StorageMoneySettlementList;
import gnnt.trade.bank.vo.bankdz.boc.TransferAccountsTransactionDetailed;
import gnnt.trade.bank.vo.bankdz.gs.sent.BankFirmRightValue;
import gnnt.trade.bank.vo.bankdz.gs.sent.FirmRightsValue;
import gnnt.trade.bank.vo.bankdz.gs.sent.ProperBalanceValue;
import gnnt.trade.bank.vo.bankdz.hx.sent.HXSentQSMsgValue;
import gnnt.trade.bank.vo.bankdz.jh.sent.FirmBalance;
import gnnt.trade.bank.vo.bankdz.pf.sent.Margins;
import gnnt.trade.bank.vo.bankdz.pf.sent.TradeList;
import gnnt.trade.bank.vo.bankdz.sfz.resave.child.BatCustDzBChild;
import gnnt.trade.bank.vo.bankdz.sfz.resave.child.BatCustDzFailChild;
import gnnt.trade.bank.vo.bankdz.sfz.resave.child.BatFailResultChild;
import gnnt.trade.bank.vo.bankdz.sfz.resave.child.KXHfailChild;
import gnnt.trade.bank.vo.bankdz.sfz.sent.child.BatQsChild;
import gnnt.trade.bank.vo.bankdz.xy.XYMarketMoney;
import gnnt.trade.bank.vo.bankdz.xy.resave.FFHDValue;
import gnnt.trade.bank.vo.bankdz.xy.resave.ZFPHValue;
import gnnt.trade.bank.vo.bankdz.xy.resave.child.FirmDateValue;
import gnnt.trade.bank.vo.bankdz.xy.sent.RZDZValue;
import gnnt.trade.bank.vo.bankdz.xy.sent.RZQSValue;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Set;
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

  protected BankDAO()
    throws Exception
  {
    try
    {
      this.DBUrl = Tool.getConfig("DBUrl");
      this.DBUser = Tool.getConfig("DBUser");
      this.DBPwd = Tool.getConfig("DBPassword");
      this.JNDIName = Tool.getConfig("JNDIName");
      this.DBConnType = Tool.getConfig("DBConnType");
    }
    catch (Exception e) {
      e.printStackTrace();
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
      catch (NamingException e) {
        e.printStackTrace();
        throw e;
      }
    }
  }

  public DataSource getDataSource()
  {
    return this.dataSource;
  }

  public Connection getConnection()
    throws SQLException, ClassNotFoundException
  {
    if (this.DBConnType.equalsIgnoreCase("JNDIName"))
    {
      return this.dataSource.getConnection();
    }

    try
    {
      Class.forName("oracle.jdbc.driver.OracleDriver");
    }
    catch (ClassNotFoundException e) {
      e.printStackTrace();
      throw e;
    }
    return DriverManager.getConnection(this.DBUrl, this.DBUser, this.DBPwd);
  }

  public void closeStatement(ResultSet rs, Statement state, Connection conn)
  {
    try
    {
      if (rs != null)
        rs.close();
    }
    catch (SQLException ex) {
      ex.printStackTrace();
    }

    try
    {
      if (state != null)
        state.close();
    }
    catch (SQLException ex) {
      ex.printStackTrace();
    }
    try
    {
      if (conn != null)
        conn.close();
    }
    catch (SQLException ex) {
      ex.printStackTrace();
    }
  }

  public abstract long getActionID(Connection paramConnection)
    throws SQLException;

  public abstract long getActionID();

  public abstract long addCapitalInfo(CapitalValue paramCapitalValue, Connection paramConnection)
    throws SQLException;

  public abstract int modCapitalInfoStatus(long paramLong, String paramString, int paramInt, Timestamp paramTimestamp, Connection paramConnection)
    throws SQLException;

  public abstract int modCapitalInfoStatusPT(long paramLong, String paramString, int paramInt, Timestamp paramTimestamp, Connection paramConnection)
    throws SQLException;

  public abstract int modCapitalInfoStatus(long paramLong, String paramString, Connection paramConnection)
    throws SQLException;

  public abstract int useBank(String paramString)
    throws SQLException, ClassNotFoundException;

  public abstract boolean getTradeDate(Date paramDate)
    throws SQLException, ClassNotFoundException;

  public abstract Vector<FirmBalanceValue> getFlote(String[] paramArrayOfString)
    throws SQLException, ClassNotFoundException;

  public abstract Vector<FirmBalanceValue> getFlote(String[] paramArrayOfString, Connection paramConnection)
    throws SQLException, ClassNotFoundException;

  public abstract int modCapitalInfoNote(long paramLong, String paramString, Connection paramConnection)
    throws SQLException;

  public abstract int modCapitalInfoNotePT(long paramLong, String paramString, Connection paramConnection)
    throws SQLException;

  public abstract Vector<CapitalValue> getCapitalInfoList(String paramString, Connection paramConnection)
    throws SQLException;

  public abstract Vector<CapitalValue> getCapitalInfoList(String paramString)
    throws SQLException, ClassNotFoundException;

  public abstract Vector<CapitalValue> getCapitalInfoListPT(String paramString)
    throws SQLException, ClassNotFoundException;

  public abstract Vector<CapitalValue> getCapitalInfoListNotice(String paramString)
    throws SQLException, ClassNotFoundException;

  public abstract Vector<CapitalValue> getCapitalInfoListNotice(String paramString, Connection paramConnection)
    throws SQLException, ClassNotFoundException;

  public abstract Vector<CapitalValue> getCapitalInfoList2(String paramString)
    throws SQLException, ClassNotFoundException;

  public abstract double sumCapitalInfo(String paramString, Connection paramConnection)
    throws SQLException;

  public abstract void addBank(BankValue paramBankValue)
    throws SQLException, ClassNotFoundException;

  public abstract void modBank(BankValue paramBankValue)
    throws SQLException, ClassNotFoundException;

  public abstract void delBank(String paramString)
    throws SQLException, ClassNotFoundException;

  public abstract BankValue getBank(String paramString, Connection paramConnection)
    throws SQLException;

  public abstract BankValue getBank(String paramString)
    throws SQLException, ClassNotFoundException;

  public abstract Vector<BankValue> getBankList(String paramString, Connection paramConnection)
    throws SQLException;

  public abstract Vector<BankValue> getBankList(String paramString)
    throws SQLException, ClassNotFoundException;

  public abstract Firm getMFirmByFirmId(String paramString)
    throws SQLException, ClassNotFoundException;

  public abstract int addFirm(FirmValue paramFirmValue, Connection paramConnection)
    throws SQLException;

  public abstract int addFirm(FirmValue paramFirmValue)
    throws SQLException, ClassNotFoundException;

  public abstract int delFirm(String paramString)
    throws SQLException, ClassNotFoundException;

  public abstract int modFirm(FirmValue paramFirmValue, Connection paramConnection)
    throws SQLException;

  public abstract int modFirm(FirmValue paramFirmValue)
    throws SQLException, ClassNotFoundException;

  public abstract Vector<FirmValue> getFirmList(String paramString)
    throws SQLException, ClassNotFoundException;

  public abstract FirmValue getFirm(String paramString, Connection paramConnection)
    throws SQLException;

  public abstract FirmValue getFirm(String paramString)
    throws SQLException, ClassNotFoundException;

  public abstract void addMoneyInfo(MoneyInfoValue paramMoneyInfoValue, Connection paramConnection)
    throws SQLException;

  public abstract long addMoneyInfo(MoneyInfoValue paramMoneyInfoValue)
    throws SQLException;

  public abstract int delMoneyInfo(String paramString, Connection paramConnection)
    throws SQLException;

  public abstract Vector<MoneyInfoValue> getMoneyInfoList(String paramString, Connection paramConnection)
    throws SQLException;

  public abstract Vector<MoneyInfoValue> getMoneyInfoList(String paramString)
    throws SQLException, ClassNotFoundException;

  public abstract Vector<MoneyInfoValue> getUnionMoneyInfoList(String paramString)
    throws SQLException, ClassNotFoundException;

  public abstract int addCorrespond(CorrespondValue paramCorrespondValue, Connection paramConnection)
    throws SQLException;

  public abstract int delCorrespond(CorrespondValue paramCorrespondValue, Connection paramConnection)
    throws SQLException;

  public abstract int modCorrespond(CorrespondValue paramCorrespondValue, Connection paramConnection)
    throws SQLException;

  public abstract int modCorrespond(CorrespondValue paramCorrespondValue1, CorrespondValue paramCorrespondValue2, Connection paramConnection)
    throws SQLException;

  public abstract int addCorrespond(CorrespondValue paramCorrespondValue)
    throws SQLException, ClassNotFoundException;

  public abstract int delCorrespond(CorrespondValue paramCorrespondValue)
    throws SQLException, ClassNotFoundException;

  public abstract int closeCorrespond(String paramString1, String paramString2, String paramString3, Connection paramConnection)
    throws SQLException;

  public abstract int modCorrespond(CorrespondValue paramCorrespondValue)
    throws SQLException, ClassNotFoundException;

  public abstract int destroyAccount(CorrespondValue paramCorrespondValue)
    throws SQLException, ClassNotFoundException;

  public abstract int destroyAccount(CorrespondValue paramCorrespondValue, Connection paramConnection)
    throws SQLException;

  public abstract Vector<CorrespondValue> getCorrespondList(String paramString, Connection paramConnection)
    throws SQLException;

  public abstract Vector<CorrespondValue> getCorrespondList(String paramString)
    throws SQLException, ClassNotFoundException;

  public abstract CorrespondValue getCorrespond(String paramString1, String paramString2, String paramString3, Connection paramConnection)
    throws SQLException;

  public abstract CorrespondValue getCorrespond(String paramString1, String paramString2, String paramString3)
    throws SQLException, ClassNotFoundException;

  public abstract Vector<DicValue> getDicList(String paramString, Connection paramConnection)
    throws SQLException;

  public abstract Vector<DicValue> getDicList(String paramString)
    throws SQLException, ClassNotFoundException;

  public abstract Vector<TransMnyObjValue> getTransMnyObjList(String paramString)
    throws SQLException, ClassNotFoundException;

  public abstract TransMnyObjValue getTransMnyObj(int paramInt)
    throws SQLException, ClassNotFoundException;

  public abstract Vector<MoneyInfoValue> qureyBankCompareInfo(String paramString)
    throws SQLException, ClassNotFoundException;

  public abstract Vector<CompareResult> compareResultInfo(String paramString1, String paramString2)
    throws SQLException, ClassNotFoundException;

  public abstract Vector<CapitalCompare> sumResultInfo(String paramString, String[] paramArrayOfString, Date paramDate)
    throws SQLException, ClassNotFoundException;

  public abstract CapitalValue getCapitalInfo(String paramString)
    throws SQLException, ClassNotFoundException;

  public abstract long updateOutMoneyCapitalInfo(CapitalValue paramCapitalValue, Connection paramConnection)
    throws SQLException, ClassNotFoundException;

  public abstract int addFeeInfo(FeeInfoVO paramFeeInfoVO, Connection paramConnection)
    throws SQLException, ClassNotFoundException;

  public abstract int addFeeInfo(FeeInfoVO paramFeeInfoVO)
    throws SQLException, ClassNotFoundException;

  public abstract int delFeeInfo(FeeInfoVO paramFeeInfoVO, Connection paramConnection)
    throws SQLException, ClassNotFoundException;

  public abstract int delFeeInfo(FeeInfoVO paramFeeInfoVO)
    throws SQLException, ClassNotFoundException;

  public abstract int delFeeInfo(String paramString)
    throws SQLException, ClassNotFoundException;

  public abstract int modFeeInfo(FeeInfoVO paramFeeInfoVO, Connection paramConnection)
    throws SQLException, ClassNotFoundException;

  public abstract int modFeeInfo(FeeInfoVO paramFeeInfoVO)
    throws SQLException, ClassNotFoundException;

  public abstract Vector<FeeInfoVO> getFeeInfoList(String paramString, Connection paramConnection)
    throws SQLException, ClassNotFoundException;

  public abstract Vector<FeeInfoVO> getFeeInfoList(String paramString)
    throws SQLException, ClassNotFoundException;

  public abstract int countBank(String paramString)
    throws SQLException, ClassNotFoundException;

  public abstract int countCapitalInfo(String paramString, int paramInt)
    throws SQLException, ClassNotFoundException;

  public abstract double countCapitalInfoTotalMoney(String paramString, int paramInt)
    throws SQLException, ClassNotFoundException;

  public abstract int countBankCompareInfo(String paramString, int paramInt)
    throws SQLException, ClassNotFoundException;

  public abstract double countBankCompareInfoTotalMoney(String paramString, int paramInt)
    throws SQLException, ClassNotFoundException;

  public abstract CapitalValue handleOutmoenyFromBank(long paramLong)
    throws SQLException, ClassNotFoundException;

  public abstract boolean getTraderStatus()
    throws SQLException, ClassNotFoundException;

  public abstract List<TradeResultValue> getTradeDataInList(String paramString, int paramInt)
    throws SQLException, ClassNotFoundException;

  public abstract Hashtable<String, TradeResultValue> getTradeDataInHashTable(String paramString, int paramInt)
    throws SQLException, ClassNotFoundException;

  public abstract Hashtable<String, Double> getFundsAndInterests(Date paramDate, int paramInt)
    throws SQLException, ClassNotFoundException;

  public abstract Vector<FundsAndInterests> getFundsAndInterestsInVector(Date paramDate, int paramInt)
    throws SQLException, ClassNotFoundException;

  public abstract List<String> getFirmBankList(String paramString)
    throws SQLException, ClassNotFoundException;

  public abstract void log(LogValue paramLogValue);

  public abstract Vector<LogValue> logList(String paramString);

  public abstract FirmBalanceValue availableBalance(String paramString);

  public abstract FirmBalanceValue availableBalance(String paramString, Connection paramConnection)
    throws SQLException;

  public abstract List<FrozenBalanceVO> frozenBalance(String paramString1, String paramString2);

  public abstract int bankAccountIsOpen(String paramString);

  public abstract Vector<BankCompareInfoValue> getBankCapInfoList(String paramString)
    throws SQLException, ClassNotFoundException;

  public abstract FirmMessageVo getFirmMSG(String paramString);

  public abstract Vector<CorrespondValue> getCorrespondList(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5);

  public abstract int countFirmAccount(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5);

  public abstract int countFirmAccount(String paramString);

  public abstract int modBankFrozenFuns(String paramString, double paramDouble, Connection paramConnection)
    throws SQLException;

  public abstract int openCorrespond(CorrespondValue paramCorrespondValue)
    throws SQLException, ClassNotFoundException;

  public abstract Vector<FirmFundsValue> getFrimFunds(String paramString, Date paramDate)
    throws SQLException, ClassNotFoundException;

  public abstract int addBankQS(BankQSVO paramBankQSVO, Connection paramConnection)
    throws SQLException;

  public abstract Date getMaxBankQSList(String paramString, Date paramDate, Connection paramConnection)
    throws SQLException;

  public abstract int addFirmKXH(KXHfailChild paramKXHfailChild, String paramString)
    throws SQLException, ClassNotFoundException;

  public abstract KXHfailChild getFirmKXH(String paramString)
    throws SQLException, ClassNotFoundException;

  public abstract int addBatCustDz(BatCustDzFailChild paramBatCustDzFailChild, Date paramDate, String paramString)
    throws SQLException, ClassNotFoundException;

  public abstract Vector<BatCustDzFailChild> getBatCustDz(Date paramDate, String paramString)
    throws SQLException, ClassNotFoundException;

  public abstract int delBatCustDz(Date paramDate, String paramString)
    throws SQLException, ClassNotFoundException;

  public abstract int addFirmBalanceFile(BatCustDzBChild paramBatCustDzBChild, Date paramDate, String paramString)
    throws SQLException, ClassNotFoundException;

  public abstract int modFirmBalanceFile(BatCustDzBChild paramBatCustDzBChild, Date paramDate, String paramString)
    throws SQLException, ClassNotFoundException;

  public abstract Vector<BatCustDzBChild> getFirmBalanceFile(String paramString1, String paramString2, Date paramDate)
    throws SQLException, ClassNotFoundException;

  public abstract int addFirmBalanceError(BatFailResultChild paramBatFailResultChild, String paramString)
    throws SQLException, ClassNotFoundException;

  public abstract int delFirmBalanceError(String paramString1, String paramString2)
    throws SQLException, ClassNotFoundException;

  public abstract Vector<FirmOpenCloseBank> getFirmBank(String paramString, Date paramDate)
    throws SQLException, ClassNotFoundException;

  public abstract Map<String, BatQsChild> getQSChild(String paramString, Set<String> paramSet1, Set<String> paramSet2, Date paramDate, Connection paramConnection)
    throws SQLException;

  public abstract Date getlastDate(Date paramDate, Connection paramConnection)
    throws SQLException;

  public abstract Vector<BatFailResultChild> getFirmBalanceError(String[] paramArrayOfString, String paramString, Date paramDate)
    throws SQLException, ClassNotFoundException;

  public abstract Vector<BatCustDzFailChild> getBatCustDz(String[] paramArrayOfString, String paramString, Date paramDate)
    throws SQLException, ClassNotFoundException;

  public abstract Vector<KXHfailChild> getBankOpen(String paramString, String[] paramArrayOfString, int paramInt, Date paramDate)
    throws SQLException, ClassNotFoundException;

  public abstract Vector<FirmBalance> getFirmBalance(String paramString, String[] paramArrayOfString, Date paramDate)
    throws SQLException, Exception;

  public abstract long addQSResult(QSRresult paramQSRresult, Connection paramConnection)
    throws SQLException;

  public abstract long delQSResult(String paramString, Date paramDate, Connection paramConnection)
    throws SQLException;

  public abstract Vector<QSRresult> getQSList(String paramString, String[] paramArrayOfString, int paramInt, Date paramDate)
    throws SQLException, ClassNotFoundException;

  public abstract Vector<HXSentQSMsgValue> getHXQSMsg(String paramString, String[] paramArrayOfString, Date paramDate)
    throws SQLException, ClassNotFoundException;

  public abstract int addQSError(QSChangeResult paramQSChangeResult, Connection paramConnection)
    throws SQLException;

  public abstract Vector<QSChangeResult> getQSError(String paramString, Connection paramConnection)
    throws SQLException;

  public abstract int modQSError(QSChangeResult paramQSChangeResult, Connection paramConnection)
    throws SQLException;

  public abstract int delQSError(QSChangeResult paramQSChangeResult, Connection paramConnection)
    throws SQLException;

  public abstract int addChangeFirmRights(String paramString, String[] paramArrayOfString, Date paramDate, Connection paramConnection)
    throws SQLException;

  public abstract int addChangeFirmFrozen(String paramString, String[] paramArrayOfString, Date paramDate, Connection paramConnection)
    throws SQLException;

  public abstract int delChangeFirmRights(String paramString, Date paramDate, String[] paramArrayOfString, Connection paramConnection)
    throws SQLException;

  public abstract int delChangeFirmFrozen(String paramString, Date paramDate, String[] paramArrayOfString, Connection paramConnection)
    throws SQLException;

  public abstract int modChangeFirmRights(String paramString1, String paramString2, Connection paramConnection)
    throws SQLException;

  public abstract int modChangeFirmFrozen(String paramString1, String paramString2, Connection paramConnection)
    throws SQLException;

  public abstract Vector<TradeList> getChangeFirmRights(String paramString, Connection paramConnection)
    throws SQLException;

  public abstract Vector<Margins> getChangeFirmFrozen(String paramString, Connection paramConnection)
    throws SQLException;

  public abstract Vector<TradeList> getChangeFirmRights(String paramString)
    throws SQLException, ClassNotFoundException;

  public abstract Vector<Margins> getChangeFirmFrozen(String paramString)
    throws SQLException, ClassNotFoundException;

  public abstract Vector<FirmRightsValue> getTradeDataMsg(String paramString1, String paramString2, Date paramDate)
    throws SQLException, ClassNotFoundException;

  public abstract Vector<FirmRightsValue> getTradeDataMsg(String paramString1, String paramString2, String paramString3)
    throws SQLException, ClassNotFoundException;

  public abstract Vector<BankFirmRightValue> getBankCapital(BankFirmRightValue paramBankFirmRightValue);

  public abstract Vector<BankFirmRightValue> getBankCapital(String paramString);

  public abstract long addProperBalance(ProperBalanceValue paramProperBalanceValue);

  public abstract long updateProperBalance(ProperBalanceValue paramProperBalanceValue);

  public abstract long delProperBalance(ProperBalanceValue paramProperBalanceValue);

  public abstract Vector<ProperBalanceValue> getProperBalance(ProperBalanceValue paramProperBalanceValue);

  public abstract Vector<ProperBalanceValue> getProperBalance(String paramString);

  public abstract long updateBankCapital(BankFirmRightValue paramBankFirmRightValue);

  public abstract long addBankCapital(BankFirmRightValue paramBankFirmRightValue);

  public abstract RZQSValue getXYQSValue(String paramString, String[] paramArrayOfString, Date paramDate)
    throws SQLException, ClassNotFoundException;

  public abstract RZDZValue getXYDZValue(String paramString, String[] paramArrayOfString, Date paramDate)
    throws SQLException, ClassNotFoundException;

  public abstract int addZFPH(ZFPHValue paramZFPHValue, Connection paramConnection)
    throws SQLException;

  public abstract Vector<ZFPHValue> getZFPH(String paramString, Date paramDate, int paramInt)
    throws SQLException, ClassNotFoundException;

  public abstract Vector<ZFPHValue> getZFPH(String paramString, Date paramDate, int paramInt, Connection paramConnection)
    throws SQLException;

  public abstract int delZFPH(String paramString, Date paramDate, int paramInt, Connection paramConnection)
    throws SQLException;

  public abstract int addFFHD(FFHDValue paramFFHDValue, Connection paramConnection)
    throws SQLException;

  public abstract Vector<FirmDateValue> getFFHD(String paramString1, String paramString2, Date paramDate)
    throws SQLException, ClassNotFoundException;

  public abstract Vector<FirmDateValue> getFFHD(String paramString, Connection paramConnection)
    throws SQLException;

  public abstract int delFFHD(String paramString, String[] paramArrayOfString, Date paramDate, Connection paramConnection)
    throws SQLException;

  public abstract int addMarketMoney(XYMarketMoney paramXYMarketMoney)
    throws SQLException, ClassNotFoundException;

  public abstract int modMarketMoney(XYMarketMoney paramXYMarketMoney)
    throws SQLException, ClassNotFoundException;

  public abstract Vector<XYMarketMoney> getMarketMoney(String paramString)
    throws SQLException, ClassNotFoundException;

  public abstract long addFirmTradeStatus(FirmTradeStatus paramFirmTradeStatus)
    throws SQLException;

  public abstract long addTradeDetailAccount(TradeDetailAccount paramTradeDetailAccount)
    throws SQLException;

  public abstract Vector<FirmTradeStatus> getFirmTradeStatusList(String paramString)
    throws SQLException, ClassNotFoundException;

  public abstract Vector<TradeDetailAccount> getTradeDetailAccountList(String paramString)
    throws SQLException, ClassNotFoundException;

  public abstract Vector<FirmBalance> getFirmBalance(String paramString, Date paramDate)
    throws SQLException, Exception;

  public abstract Vector<BankQSNetChild> getQSBankDate(String paramString, Date paramDate)
    throws SQLException, Exception;

  public abstract Vector<RgstCapitalValue> getRgstCapitalValue(String paramString, Connection paramConnection)
    throws SQLException;

  public abstract int addRgstCapitalValue(RgstCapitalValue paramRgstCapitalValue, Connection paramConnection)
    throws SQLException;

  public abstract int modRgstCapitalValue(String paramString1, String paramString2, String paramString3, Timestamp paramTimestamp, int paramInt1, long paramLong, int paramInt2, Connection paramConnection)
    throws SQLException;

  public abstract Vector<ClientState> getClientState(String paramString)
    throws SQLException, ClassNotFoundException;

  public abstract int addClientState(ClientState paramClientState)
    throws SQLException, ClassNotFoundException;

  public abstract List<TransferAccountsTransactionDetailed> getZZJYMX(String paramString, String[] paramArrayOfString, Date paramDate, Connection paramConnection)
    throws SQLException;

  public abstract List<AccountStatusReconciliation> getKHZHZT(String paramString, String[] paramArrayOfString, Date paramDate, Connection paramConnection)
    throws SQLException, ClassNotFoundException;

  public abstract List<StorageMoneySettlementList> getCGKHZJJSMX(String paramString, Date paramDate, Connection paramConnection)
    throws SQLException, ClassNotFoundException;

  public abstract List<StorageMoneyLedgerBalanceList> getCGKHZJTZYEMX(String paramString, Date paramDate, Connection paramConnection)
    throws SQLException, ClassNotFoundException;

  public abstract int interfaceLog(InterfaceLog paramInterfaceLog)
    throws SQLException, ClassNotFoundException;

  public abstract Vector<InterfaceLog> interfaceLogList(String paramString)
    throws SQLException, ClassNotFoundException;

  public abstract List<BankQSNetChild> getQSFirmDate(String paramString1, String paramString2, Date paramDate)
    throws SQLException, Exception;

  public abstract Vector<BankTransferValue> getBankTransferList(String paramString)
    throws SQLException, ClassNotFoundException;

  public abstract Vector<BankTransferValue> getBankTransferList(String paramString, Connection paramConnection)
    throws SQLException, ClassNotFoundException;

  public abstract long addBankTransfer(BankTransferValue paramBankTransferValue, Connection paramConnection)
    throws SQLException, ClassNotFoundException;

  public abstract long addBankTransfer(BankTransferValue paramBankTransferValue)
    throws SQLException, ClassNotFoundException;

  public abstract long modBankTransfer(long paramLong, int paramInt, Connection paramConnection)
    throws SQLException, ClassNotFoundException;

  public abstract Vector<Account> getAccList(String paramString)
    throws SQLException, ClassNotFoundException;

  public abstract TransferBank getTransferBank(String paramString)
    throws SQLException, ClassNotFoundException;

  public abstract int addMarketAcount(CorrespondValue paramCorrespondValue)
    throws SQLException, ClassNotFoundException;

  public abstract int modMarketAcount(CorrespondValue paramCorrespondValue)
    throws SQLException, ClassNotFoundException;

  public abstract Vector<CorrespondValue> getMarketAcount(String paramString)
    throws SQLException, ClassNotFoundException;

  public abstract int modCapitalInfoStatus_ceb_f623(String paramString, int paramInt, Timestamp paramTimestamp, Connection paramConnection)
    throws SQLException, Exception;

  public abstract int addTransfer(CEB_param paramCEB_param)
    throws SQLException, ClassNotFoundException;

  public abstract Vector<FirmBalance_CEB> getFirmBalance_CEB(String paramString, Date paramDate)
    throws SQLException, Exception;

  public abstract int addFCS_10(FCS_10_Result paramFCS_10_Result)
    throws SQLException, ClassNotFoundException;

  public abstract int addFCS_11(FCS_11_Result paramFCS_11_Result)
    throws SQLException, ClassNotFoundException;

  public abstract int addFCS_13(FCS_13_Result paramFCS_13_Result)
    throws SQLException, ClassNotFoundException;

  public abstract int addFCS_99(FCS_99 paramFCS_99)
    throws SQLException, ClassNotFoundException;

  public abstract Vector<CorrespondValue> getOpenBankList(String paramString)
    throws SQLException, ClassNotFoundException;

  public abstract int delMarketAcount(String paramString)
    throws SQLException, ClassNotFoundException;

  public abstract Vector<CEB_param> getTransferList(String paramString)
    throws SQLException, ClassNotFoundException;

  public abstract Vector<FCS_10_Result> getFCS_10_List(String paramString)
    throws SQLException, ClassNotFoundException;

  public abstract Vector<FCS_11_Result> getFCS_11_List(String paramString)
    throws SQLException, ClassNotFoundException;

  public abstract Vector<FCS_13_Result> getFCS_13_List(String paramString)
    throws SQLException, ClassNotFoundException;

  public abstract Vector<FCS_99> getFCS_99_List(String paramString)
    throws SQLException, ClassNotFoundException;

  public abstract int modCapitalInfoStatus_ceb(long paramLong, String paramString, int paramInt1, int paramInt2, Timestamp paramTimestamp, Connection paramConnection)
    throws SQLException;

  public abstract Vector<CityValue> getCityNames(String paramString)
    throws SQLException, Exception;

  public abstract int modBankQS(BankQSVO paramBankQSVO, Connection paramConnection)
    throws SQLException;

  public abstract Vector<BankQSVO> getBankQSDate(String paramString)
    throws SQLException, ClassNotFoundException;

  public abstract Date getMaxBankQSSuccessDate(String paramString, Date paramDate, Connection paramConnection)
    throws SQLException;

  public abstract double sumAmount(String paramString, Connection paramConnection)
    throws SQLException;

  public abstract AbcInfoValue getAbcInfo(String paramString, long paramLong, int paramInt, Connection paramConnection)
    throws SQLException;

  public abstract void addAbcInfo(AbcInfoValue paramAbcInfoValue, Connection paramConnection)
    throws SQLException;

  public abstract FirmBalance getFirmBala(String paramString, Date paramDate, Connection paramConnection)
    throws SQLException;

  public abstract Vector<SystemMessage> getSystemMessages(String paramString)
    throws SQLException;

  public abstract long modSystemMessage(SystemMessage paramSystemMessage)
    throws SQLException;

  public abstract long modSystemMessage(String paramString1, String paramString2, String paramString3)
    throws SQLException;

  public abstract Vector<FirmID2SysFirmID> getFirmID2SysFirmID(String paramString)
    throws SQLException;

  public abstract Vector<FirmID2SysFirmID> getFirmID2SysFirmID(String paramString, Connection paramConnection)
    throws SQLException;

  public abstract long addFirmID2SysFirmID(FirmID2SysFirmID paramFirmID2SysFirmID)
    throws SQLException;

  public abstract long addFirmID2SysFirmID(FirmID2SysFirmID paramFirmID2SysFirmID, Connection paramConnection)
    throws SQLException;

  public abstract long addFirmID2SysFirmIDHis(FirmID2SysFirmID paramFirmID2SysFirmID, Connection paramConnection)
    throws SQLException;

  public abstract long modFirmID2SysFirmID(FirmID2SysFirmID paramFirmID2SysFirmID)
    throws SQLException;

  public abstract long modFirmID2SysFirmID2(FirmID2SysFirmID paramFirmID2SysFirmID1, FirmID2SysFirmID paramFirmID2SysFirmID2, Connection paramConnection)
    throws SQLException;

  public abstract long modFirmID2SysFirmID(FirmID2SysFirmID paramFirmID2SysFirmID1, FirmID2SysFirmID paramFirmID2SysFirmID2, Connection paramConnection)
    throws SQLException;

  public abstract long delFirmID2SysFirmID(FirmID2SysFirmID paramFirmID2SysFirmID)
    throws SQLException;

  public abstract long delFirmID2SysFirmID(FirmID2SysFirmID paramFirmID2SysFirmID, Connection paramConnection)
    throws SQLException;

  public abstract long getNewFirmID()
    throws SQLException;

  public abstract long addCapitalInfoPT(CapitalValue paramCapitalValue, Connection paramConnection)
    throws SQLException;

  public abstract long addCapitalInfoNotice(CapitalValue paramCapitalValue, Connection paramConnection)
    throws SQLException;

  public abstract long addMFirm(MFirmValue paramMFirmValue, Connection paramConnection)
    throws SQLException;

  public abstract long addSystemQSData(SystemQSValue paramSystemQSValue, Connection paramConnection)
    throws SQLException;

  public abstract long dellSystemQSData(SystemQSValue paramSystemQSValue, Connection paramConnection)
    throws SQLException;

  public abstract Vector<SystemQSValue> getSystemQSData(String paramString, Connection paramConnection)
    throws SQLException;

  public abstract Vector<CapitalValue> getInMoneyCapitalNeedNotice(Connection paramConnection)
    throws SQLException;

  public abstract boolean cardOpened(String paramString1, String paramString2, String paramString3)
    throws SQLException;

  public abstract Vector<QueryTradeData> marketTradeQuery(String paramString1, String paramString2)
    throws SQLException;

  public abstract Vector<BankSumDate> bankTradeQuery(String paramString1, String paramString2)
    throws SQLException;

  public abstract int addRZQS(Vector<RZQS> paramVector, String paramString)
    throws SQLException, ClassNotFoundException;

  public abstract int addRZQS(Vector<RZQS> paramVector, String paramString, Connection paramConnection)
    throws SQLException;

  public abstract Vector<FirmID2SysFirmID> getFirmMapping(String paramString)
    throws SQLException;

  public abstract Vector<FirmBankFunds> getFirmBankFunds(String paramString)
    throws SQLException;

  public abstract Vector<FirmBankFunds> getFirmBankFunds(String paramString, Connection paramConnection)
    throws SQLException;

  public abstract int addFirmBankFunds(FirmBankFunds paramFirmBankFunds, Connection paramConnection)
    throws SQLException;

  public abstract int delFirmBankFunds(FirmBankFunds paramFirmBankFunds, Connection paramConnection)
    throws SQLException;

  public abstract int modFirmBankFunds(FirmBankFunds paramFirmBankFunds, Connection paramConnection)
    throws SQLException;

  public abstract Vector<FirmBankFunds> getFirmBankFundsHis(String paramString)
    throws SQLException;

  public abstract Vector<FirmID2SysFirmID> getfirmIDMgs(String paramString)
    throws SQLException;

  public abstract Vector<BankCode> getBankCode(String paramString)
    throws SQLException;

  public abstract Date getMaxDate();

  public abstract boolean getSysStatus(Date paramDate);

  public abstract int addPlatRzqs(Date paramDate, Connection paramConnection)
    throws SQLException;

  public abstract int delPlatRzqs(Date paramDate, Connection paramConnection)
    throws SQLException;

  public abstract int modBankAccount(CorrespondValue paramCorrespondValue1, CorrespondValue paramCorrespondValue2, Connection paramConnection)
    throws SQLException;

  public abstract Vector<RZQS> getRZQSData(Date paramDate, String paramString)
    throws SQLException, ClassNotFoundException;

  public abstract Vector<RZQS> checkRZQSData(Date paramDate)
    throws SQLException, ClassNotFoundException;

  public abstract int delCapitalByDateSystem(String paramString, Date paramDate, Connection paramConnection)
    throws SQLException;

  public abstract int addCapitalByDateSystem(String paramString, Vector<CapitalValue> paramVector, Connection paramConnection)
    throws SQLException;

  public abstract Vector<CapitalValueMoney> getMoneyErrorCapital(String paramString, Date paramDate)
    throws SQLException, ClassNotFoundException;

  public abstract Vector<CapitalValue> getSysNoCapital(String paramString, Date paramDate)
    throws SQLException, ClassNotFoundException;

  public abstract Vector<CapitalValue> getPlatNoCapital(String paramString, Date paramDate)
    throws SQLException, ClassNotFoundException;

  public abstract Vector<CapitalValueMoney> getMoneyErrorCapital2(Date paramDate)
    throws SQLException, ClassNotFoundException;

  public abstract Vector<CapitalValue> getToBankNoCapital(Date paramDate)
    throws SQLException, ClassNotFoundException;

  public abstract Vector<CapitalValue> getToSysNoCapital(Date paramDate)
    throws SQLException, ClassNotFoundException;

  public abstract Vector<CheckMessage> getCheckMsg(Map<String, Object> paramMap)
    throws SQLException;

  public abstract int saveAccount1(String paramString1, String paramString2, String paramString3, Connection paramConnection)
    throws SQLException;
}
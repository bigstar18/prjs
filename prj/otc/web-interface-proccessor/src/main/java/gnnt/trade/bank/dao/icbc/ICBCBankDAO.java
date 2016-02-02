package gnnt.trade.bank.dao.icbc;

import gnnt.trade.bank.dao.BankDAOImpl;
import gnnt.trade.bank.data.icbc.vo.Account;
import gnnt.trade.bank.data.icbc.vo.BankFirmRightValue;
import gnnt.trade.bank.data.icbc.vo.ProperBalanceValue;
import gnnt.trade.bank.vo.BankTransferValue;
import gnnt.trade.bank.vo.BankValue;
import gnnt.trade.bank.vo.FirmInfo;
import gnnt.trade.bank.vo.FirmValue;
import gnnt.trade.bank.vo.MarketAcount;
import gnnt.trade.bank.vo.TransferInfo;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Vector;

public abstract class ICBCBankDAO
  extends BankDAOImpl
{
  public ICBCBankDAO()
    throws Exception
  {}
  
  public abstract Vector<BankFirmRightValue> getBankCapital(BankFirmRightValue paramBankFirmRightValue);
  
  public abstract Vector<BankFirmRightValue> getBankCapital(String paramString);
  
  public abstract long addProperBalance(ProperBalanceValue paramProperBalanceValue);
  
  public abstract long updateProperBalance(ProperBalanceValue paramProperBalanceValue);
  
  public abstract long delProperBalance(ProperBalanceValue paramProperBalanceValue);
  
  public abstract Vector<ProperBalanceValue> getProperBalance(ProperBalanceValue paramProperBalanceValue);
  
  public abstract Vector<ProperBalanceValue> getProperBalance(String paramString);
  
  public abstract long updateBankCapital(BankFirmRightValue paramBankFirmRightValue);
  
  public abstract long addBankCapital(BankFirmRightValue paramBankFirmRightValue);
  
  public abstract Vector<FirmInfo> getFirmInfo(String paramString1, String paramString2, String paramString3);
  
  public abstract int insertFirmInfo(FirmValue paramFirmValue, String paramString);
  
  public abstract int modfirmuser(FirmValue paramFirmValue, String paramString)
    throws SQLException;
  
  public abstract Vector<BankTransferValue> getBankTransferList(String paramString)
    throws SQLException, ClassNotFoundException;
  
  public abstract Vector<BankTransferValue> getBankTransferList(String paramString, Connection paramConnection)
    throws SQLException, ClassNotFoundException;
  
  public abstract Vector<BankValue> getBankList(String paramString, Connection paramConnection)
    throws SQLException;
  
  public abstract Vector<BankValue> getBankList(String paramString)
    throws SQLException, ClassNotFoundException;
  
  public abstract Vector<Account> getAccList(String paramString)
    throws SQLException, ClassNotFoundException;
  
  public abstract long addBankTransfer(BankTransferValue paramBankTransferValue, Connection paramConnection)
    throws SQLException, ClassNotFoundException;
  
  public abstract long addBankTransfer(BankTransferValue paramBankTransferValue)
    throws SQLException, ClassNotFoundException;
  
  public abstract long modBankTransfer(long paramLong, int paramInt, Connection paramConnection)
    throws SQLException, ClassNotFoundException;
  
  public abstract FirmValue getFirmValue(FirmValue paramFirmValue);
  
  public abstract Vector<MarketAcount> getMarketAcount(String paramString)
    throws SQLException, ClassNotFoundException;
  
  public abstract int addMarketAcount(MarketAcount paramMarketAcount)
    throws SQLException, ClassNotFoundException;
  
  public abstract int delMarketAcount(String paramString)
    throws SQLException, ClassNotFoundException;
  
  public abstract int modMarketAcount(MarketAcount paramMarketAcount)
    throws SQLException, ClassNotFoundException;
  
  public abstract Vector<TransferInfo> getTransferList(String paramString)
    throws SQLException, ClassNotFoundException;
  
  public abstract int addTransfer(TransferInfo paramTransferInfo)
    throws SQLException, ClassNotFoundException;
  
  public abstract int modTransfer(String paramString1, String paramString2, int paramInt, Timestamp paramTimestamp, Connection paramConnection)
    throws SQLException;
}

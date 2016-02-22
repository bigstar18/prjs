package gnnt.trade.bank.dao.ceb;

import gnnt.bank.adapter.bankBusiness.dayData.FCS_10_Result;
import gnnt.bank.adapter.bankBusiness.dayData.FCS_11_Result;
import gnnt.bank.adapter.bankBusiness.dayData.FCS_13_Result;
import gnnt.bank.adapter.bankBusiness.dayData.FCS_99;
import gnnt.bank.adapter.bankBusiness.message.CEB_param;
import gnnt.trade.bank.dao.BankDAOImpl;
import gnnt.trade.bank.vo.BanksInfoValue;
import gnnt.trade.bank.vo.CitysValue;
import gnnt.trade.bank.vo.CorrespondValue;
import gnnt.trade.bank.vo.FirmBalanceValue;
import gnnt.trade.bank.vo.FirmUserValue;
import gnnt.trade.bank.vo.bankdz.gs.sent.FirmRightsValue;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Vector;

public abstract class CEBBankDao
  extends BankDAOImpl
{
  public CEBBankDao()
    throws Exception
  {}
  
  public abstract int modCapitalInfoStatus_ceb_f623(String paramString, int paramInt, Timestamp paramTimestamp, Connection paramConnection)
    throws SQLException, Exception;
  
  public abstract Vector<CorrespondValue> getMarketAcount(String paramString)
    throws SQLException, ClassNotFoundException;
  
  public abstract int addTransfer(CEB_param paramCEB_param)
    throws SQLException, ClassNotFoundException;
  
  public abstract Vector<FirmBalanceValue> getFlote(String[] paramArrayOfString)
    throws SQLException, ClassNotFoundException;
  
  public abstract int modCapitalInfoStatus_ceb(long paramLong, String paramString, int paramInt1, int paramInt2, Timestamp paramTimestamp, Connection paramConnection)
    throws SQLException;
  
  public abstract Vector<FirmRightsValue> getTradeDataMsg(String paramString1, String paramString2, Date paramDate)
    throws SQLException, ClassNotFoundException;
  
  public abstract Date getlastDate(Date paramDate, Connection paramConnection)
    throws SQLException;
  
  public abstract int addFCS_10(FCS_10_Result paramFCS_10_Result)
    throws SQLException, ClassNotFoundException;
  
  public abstract int addFCS_11(FCS_11_Result paramFCS_11_Result)
    throws SQLException, ClassNotFoundException;
  
  public abstract int addFCS_99(FCS_99 paramFCS_99)
    throws SQLException, ClassNotFoundException;
  
  public abstract int addFCS_13(FCS_13_Result paramFCS_13_Result)
    throws SQLException, ClassNotFoundException;
  
  public abstract FirmBalanceValue availableBalance(String paramString);
  
  public abstract void changeBankID(String paramString1, String paramString2)
    throws SQLException, ClassNotFoundException;
  
  public abstract Vector<BanksInfoValue> getBanksInfo(String paramString)
    throws SQLException, ClassNotFoundException;
  
  public abstract Vector<CitysValue> getCityOfBank(String paramString)
    throws SQLException, ClassNotFoundException;
  
  public abstract FirmUserValue getFirmUserValue(String paramString)
    throws SQLException, ClassNotFoundException;
  
  public abstract Vector<FirmUserValue> getFirmUserList2(String paramString1, int[] paramArrayOfInt, String paramString2, String paramString3)
    throws SQLException, ClassNotFoundException;
  
  public abstract int addMarketAcount(CorrespondValue paramCorrespondValue)
    throws SQLException, ClassNotFoundException;
  
  public abstract int modMarketAcount(CorrespondValue paramCorrespondValue)
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
}

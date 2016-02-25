package gnnt.trade.bank.dao;

import gnnt.trade.bank.data.sfz.vo.BatCustDzBChild;
import gnnt.trade.bank.data.sfz.vo.BatCustDzFailChild;
import gnnt.trade.bank.data.sfz.vo.BatFailResultChild;
import gnnt.trade.bank.data.sfz.vo.BatQsChild;
import gnnt.trade.bank.data.sfz.vo.KXHfailChild;
import gnnt.trade.bank.vo.FirmBalanceValue;
import gnnt.trade.bank.vo.bankdz.BankQSVO;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

public abstract class PABankDAO
  extends BankDAOImpl
{
  public PABankDAO()
    throws Exception
  {}
  
  public abstract Vector<FirmBalanceValue> getFlote(String[] paramArrayOfString)
    throws SQLException, ClassNotFoundException;
  
  public abstract int delFirmBalanceError(String paramString1, String paramString2)
    throws SQLException, ClassNotFoundException;
  
  public abstract int addFirmBalanceError(BatFailResultChild paramBatFailResultChild, String paramString)
    throws SQLException, ClassNotFoundException;
  
  public abstract Vector<BatCustDzBChild> getFirmBalanceFile(String paramString1, String paramString2, Date paramDate)
    throws SQLException, ClassNotFoundException;
  
  public abstract int modFirmBalanceFile(BatCustDzBChild paramBatCustDzBChild, Date paramDate, String paramString)
    throws SQLException, ClassNotFoundException;
  
  public abstract int addFirmBalanceFile(BatCustDzBChild paramBatCustDzBChild, Date paramDate, String paramString)
    throws SQLException, ClassNotFoundException;
  
  public abstract Vector<KXHfailChild> getFirmKXH1(String paramString)
    throws SQLException, ClassNotFoundException;
  
  public abstract KXHfailChild getFirmKXH(String paramString)
    throws SQLException, ClassNotFoundException;
  
  public abstract int addFirmKXH(KXHfailChild paramKXHfailChild, String paramString)
    throws SQLException, ClassNotFoundException;
  
  public abstract int delBatCustDz(Date paramDate, String paramString)
    throws SQLException, ClassNotFoundException;
  
  public abstract int addBatCustDz(BatCustDzFailChild paramBatCustDzFailChild, Date paramDate, String paramString)
    throws SQLException, ClassNotFoundException;
  
  public abstract int addBankQS(BankQSVO paramBankQSVO, Connection paramConnection)
    throws SQLException;
  
  public abstract Map<String, BatQsChild> getQSChild(String paramString, Set<String> paramSet1, Set<String> paramSet2, Date paramDate, Connection paramConnection)
    throws SQLException;
  
  public abstract Date getMaxBankQSList(String paramString, Date paramDate, Connection paramConnection)
    throws SQLException;
  
  public abstract Vector<KXHfailChild> getBankOpen(String paramString, String[] paramArrayOfString, int paramInt, Date paramDate)
    throws SQLException, ClassNotFoundException;
  
  public abstract Date getlastDate(Date paramDate, Connection paramConnection)
    throws SQLException;
  
  public abstract BankQSVO getBankQS(String paramString, Date paramDate, Connection paramConnection)
    throws SQLException;
  
  public abstract Vector<BatFailResultChild> getFirmBalanceError(String[] paramArrayOfString, String paramString, Date paramDate)
    throws SQLException, ClassNotFoundException;
  
  public abstract Vector<BatCustDzFailChild> getBatCustDz(String[] paramArrayOfString, String paramString, Date paramDate)
    throws SQLException, ClassNotFoundException;
  
  public abstract FirmBalanceValue availableBalance(String paramString);
  
  public abstract int modBankQS(BankQSVO paramBankQSVO, Connection paramConnection)
    throws SQLException;
}

package gnnt.trade.bank.dao;

import gnnt.trade.bank.vo.FirmInfo;
import gnnt.trade.bank.vo.FirmValue;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Vector;

public abstract class CCBBankDAO
  extends BankDAOImpl
{
  public int[] pageinfo = new int[4];
  
  public CCBBankDAO()
    throws Exception
  {}
  
  public abstract int modfirmuser(FirmValue paramFirmValue, String paramString)
    throws SQLException;
  
  public abstract Vector<FirmValue> getFirmList3(String paramString1, String paramString2, String paramString3, String paramString4)
    throws SQLException, ClassNotFoundException;
  
  public abstract int modCapitalInfoStatus(long paramLong, String paramString1, int paramInt, Timestamp paramTimestamp, String paramString2, Connection paramConnection)
    throws SQLException;
  
  public abstract Vector<FirmInfo> getFirmInfo(String paramString1, String paramString2, String paramString3);
  
  public abstract int insertFirmInfo(FirmValue paramFirmValue, String paramString);
  
  public abstract int updateFirmInfo(String paramString1, String paramString2, String paramString3, String paramString4);
  
  public abstract FirmValue getFirmValue(FirmValue paramFirmValue);
}

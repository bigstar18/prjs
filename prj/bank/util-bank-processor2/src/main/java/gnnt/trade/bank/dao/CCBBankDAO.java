package gnnt.trade.bank.dao;

import gnnt.trade.bank.vo.FirmInfo;
import gnnt.trade.bank.vo.FirmValue;
import gnnt.trade.bank.vo.bankdz.jh.sent.FirmBalance;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;
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
  
  public abstract long addCCBQS(Vector<FirmBalance> paramVector, Date paramDate, Connection paramConnection)
    throws SQLException;
  
  public abstract Vector<FirmBalance> getCCBQS(Connection paramConnection, String paramString)
    throws SQLException;
  
  public abstract long updateCCBQS(String paramString1, String paramString2, Connection paramConnection)
    throws SQLException;
  
  public abstract long delCCBQS(Date paramDate, Connection paramConnection)
    throws SQLException;
  
  public Vector<FirmBalance> getCCBQS(Connection conn, List<String> list1)
    throws SQLException
  {
    return null;
  }
  
  public abstract Map<String, Double> getFirmBanlanceMap(Connection paramConnection, String paramString)
    throws SQLException;
}

package gnnt.trade.bank.dao;

import gnnt.trade.bank.vo.FirmInfo;
import gnnt.trade.bank.vo.FirmValue;
import gnnt.trade.bank.vo.bankdz.xy.resave.FFHDValue;
import gnnt.trade.bank.vo.bankdz.xy.resave.ZFPHValue;
import gnnt.trade.bank.vo.bankdz.xy.resave.child.FirmDateValue;
import gnnt.trade.bank.vo.bankdz.xy.sent.RZDZValue;
import gnnt.trade.bank.vo.bankdz.xy.sent.RZQSValue;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.Vector;

public abstract class CIBBankDAO
  extends BankDAOImpl
{
  public CIBBankDAO()
    throws Exception
  {}
  
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
  
  public abstract RZQSValue getXYQSValue(String paramString, String[] paramArrayOfString, Date paramDate)
    throws SQLException, ClassNotFoundException;
  
  public abstract RZDZValue getXYDZValue(String paramString, String[] paramArrayOfString, Date paramDate)
    throws SQLException, ClassNotFoundException;
  
  public abstract Vector<FirmInfo> getFirmInfo(String paramString1, String paramString2, String paramString3);
  
  public abstract int insertFirmInfo(FirmValue paramFirmValue, String paramString);
  
  public abstract int modfirmuser(FirmValue paramFirmValue, String paramString)
    throws SQLException;
}

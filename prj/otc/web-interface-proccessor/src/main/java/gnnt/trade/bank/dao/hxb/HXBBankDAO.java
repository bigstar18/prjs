package gnnt.trade.bank.dao.hxb;

import gnnt.trade.bank.dao.BankDAOImpl;
import gnnt.trade.bank.vo.BanksInfoValue;
import gnnt.trade.bank.vo.CitysValue;
import gnnt.trade.bank.vo.FirmUserValue;
import java.sql.SQLException;
import java.util.Vector;

public abstract class HXBBankDAO
  extends BankDAOImpl
{
  public HXBBankDAO()
    throws Exception
  {}
  
  public abstract Vector<BanksInfoValue> getBanksInfo(String paramString)
    throws SQLException, ClassNotFoundException;
  
  public abstract Vector<CitysValue> getCityOfBank(String paramString)
    throws SQLException, ClassNotFoundException;
  
  public abstract Vector<FirmUserValue> getFirmUserList2(String paramString1, int[] paramArrayOfInt, String paramString2, String paramString3)
    throws SQLException, ClassNotFoundException;
  
  public abstract FirmUserValue getFirmUserValue(String paramString)
    throws SQLException, ClassNotFoundException;
  
  public abstract void changeBankID(String paramString1, String paramString2)
    throws SQLException, ClassNotFoundException;
}

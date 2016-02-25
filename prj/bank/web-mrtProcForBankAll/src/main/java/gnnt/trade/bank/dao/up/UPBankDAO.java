package gnnt.trade.bank.dao.up;

import gnnt.trade.bank.dao.BankDAOImpl;
import gnnt.trade.bank.vo.BankCodes;
import java.sql.SQLException;
import java.util.Vector;

public abstract class UPBankDAO
  extends BankDAOImpl
{
  public UPBankDAO()
    throws Exception
  {}
  
  public abstract Vector<BankCodes> getBankCodeList(String paramString)
    throws SQLException, ClassNotFoundException;
}

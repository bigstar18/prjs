package gnnt.trade.bank.dao;

import java.sql.Connection;
import java.sql.SQLException;

public abstract class GFBBankDAO
  extends BankDAOImpl
{
  public GFBBankDAO()
    throws Exception
  {}
  
  public abstract String getFirmStatus(String paramString, Connection paramConnection)
    throws SQLException;
}

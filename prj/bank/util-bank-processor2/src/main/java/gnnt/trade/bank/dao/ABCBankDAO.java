package gnnt.trade.bank.dao;

import gnnt.trade.bank.vo.AbcInfoValue;
import java.sql.Connection;
import java.sql.SQLException;

public abstract class ABCBankDAO
  extends BankDAOImpl
{
  public ABCBankDAO()
    throws Exception
  {}
  
  public abstract AbcInfoValue getAbcInfo(String paramString, long paramLong, int paramInt, Connection paramConnection)
    throws SQLException;
  
  public abstract void addAbcInfo(AbcInfoValue paramAbcInfoValue, Connection paramConnection)
    throws SQLException;
}

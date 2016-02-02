package gnnt.trade.bank.dao.jsb;

import gnnt.trade.bank.dao.BankDAOImpl;
import gnnt.trade.bank.vo.MarketAcount;
import gnnt.trade.bank.vo.MarketCashBalance;
import gnnt.trade.bank.vo.TransferInfo;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Vector;

public abstract class JSBBankDAO
  extends BankDAOImpl
{
  public JSBBankDAO()
    throws Exception
  {}
  
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
  
  public abstract Vector<MarketCashBalance> getMarketCashBalance(String paramString)
    throws SQLException, ClassNotFoundException;
}

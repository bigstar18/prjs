package gnnt.trade.bank.dao;

import gnnt.trade.bank.vo.FirmTradeStatus;
import gnnt.trade.bank.vo.TradeDetailAccount;
import java.sql.SQLException;
import java.util.Vector;

public abstract class CMBBankDAO
  extends BankDAOImpl
{
  public CMBBankDAO()
    throws Exception
  {}
  
  public abstract Vector<TradeDetailAccount> getTradeDetailAccountList(String paramString)
    throws SQLException, ClassNotFoundException;
  
  public abstract long addTradeDetailAccount(TradeDetailAccount paramTradeDetailAccount)
    throws SQLException;
  
  public abstract Vector<FirmTradeStatus> getFirmTradeStatusList(String paramString)
    throws SQLException, ClassNotFoundException;
  
  public abstract long addFirmTradeStatus(FirmTradeStatus paramFirmTradeStatus)
    throws SQLException;
}

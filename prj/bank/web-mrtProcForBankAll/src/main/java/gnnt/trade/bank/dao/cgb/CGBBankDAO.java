package gnnt.trade.bank.dao.cgb;

import gnnt.trade.bank.dao.BankDAOImpl;
import gnnt.trade.bank.data.cgb.vo.AccountStatusReconciliation;
import gnnt.trade.bank.data.cgb.vo.StorageMoneyLedgerBalanceList;
import gnnt.trade.bank.data.cgb.vo.StorageMoneySettlementList;
import gnnt.trade.bank.data.cgb.vo.TransferAccountsTransactionDetailed;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public abstract class CGBBankDAO
  extends BankDAOImpl
{
  public CGBBankDAO()
    throws Exception
  {}
  
  public abstract List<TransferAccountsTransactionDetailed> getZZJYMX(String paramString, Date paramDate, Connection paramConnection)
    throws SQLException;
  
  public abstract List<AccountStatusReconciliation> getKHZHZT(String paramString, Date paramDate, Connection paramConnection)
    throws SQLException, ClassNotFoundException;
  
  public abstract List<StorageMoneySettlementList> getCGKHZJJSMX(String paramString, Date paramDate, Connection paramConnection)
    throws SQLException, ClassNotFoundException;
  
  public abstract List<StorageMoneyLedgerBalanceList> getCGKHZJTZYEMX(String paramString, Date paramDate, Connection paramConnection)
    throws SQLException, ClassNotFoundException;
  
  public abstract double getfirmQY(String paramString1, String paramString2, Connection paramConnection)
    throws SQLException, ClassNotFoundException;
}

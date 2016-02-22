package gnnt.trade.bank.dao;

import gnnt.trade.bank.data.boc.vo.AccountStatusReconciliation;
import gnnt.trade.bank.data.boc.vo.StorageMoneyLedgerBalanceList;
import gnnt.trade.bank.data.boc.vo.StorageMoneySettlementList;
import gnnt.trade.bank.data.boc.vo.TransferAccountsTransactionDetailed;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public abstract class BOCBankDAO
  extends BankDAOImpl
{
  public BOCBankDAO()
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
}

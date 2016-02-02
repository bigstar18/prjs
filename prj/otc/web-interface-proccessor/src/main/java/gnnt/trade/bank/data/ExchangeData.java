package gnnt.trade.bank.data;

import gnnt.bank.adapter.rmi.BankAdapterRMI;
import java.util.Date;

public abstract interface ExchangeData
{
  public abstract int getDataFile(BankAdapterRMI paramBankAdapterRMI, Date paramDate);
  
  public abstract int createDataFile(BankAdapterRMI paramBankAdapterRMI, Date paramDate);
}

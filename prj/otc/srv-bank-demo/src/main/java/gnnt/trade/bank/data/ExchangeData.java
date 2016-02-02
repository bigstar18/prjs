package gnnt.trade.bank.data;

import gnnt.bank.adapter.rmi.BankAdapterRMI;
import java.util.Date;

public interface ExchangeData {
	
	
	public int getDataFile(BankAdapterRMI bankadapter,Date date);
	
	public int createDataFile(BankAdapterRMI bankadapter,Date date);
}

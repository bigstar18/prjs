package gnnt.MEBS.timebargain.tradeweb.dao;

import gnnt.MEBS.timebargain.tradeweb.model.Customer;
import gnnt.MEBS.timebargain.tradeweb.model.CustomerFunds;
import gnnt.MEBS.timebargain.tradeweb.model.Trader;

public abstract interface CustomerDAO
  extends DAO
{
  public abstract Customer getCustomerById(String paramString);
  
  public abstract Trader getTraderById(String paramString);
  
  public abstract CustomerFunds getCustomerFundsById(String paramString);
  
  public abstract void updateCustomerPassword(Customer paramCustomer);
  
  public abstract void updateTraderPassword(Trader paramTrader);
}

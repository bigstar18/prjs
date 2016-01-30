package gnnt.MEBS.timebargain.tradeweb.service.impl;

import gnnt.MEBS.timebargain.tradeweb.dao.CustomerDAO;
import gnnt.MEBS.timebargain.tradeweb.model.Customer;
import gnnt.MEBS.timebargain.tradeweb.model.CustomerFunds;
import gnnt.MEBS.timebargain.tradeweb.model.Trader;
import gnnt.MEBS.timebargain.tradeweb.service.CustomerManager;

public class CustomerManagerImpl
  extends BaseManager
  implements CustomerManager
{
  private CustomerDAO dao;
  
  public void setCustomerDAO(CustomerDAO paramCustomerDAO)
  {
    this.dao = paramCustomerDAO;
  }
  
  public Customer getCustomerById(String paramString)
  {
    return this.dao.getCustomerById(paramString);
  }
  
  public Trader getTraderById(String paramString)
  {
    return this.dao.getTraderById(paramString);
  }
  
  public CustomerFunds getCustomerFundsById(String paramString)
  {
    return this.dao.getCustomerFundsById(paramString);
  }
  
  public void updateCustomerPassword(Customer paramCustomer)
  {
    this.dao.updateCustomerPassword(paramCustomer);
  }
  
  public void updateTraderPassword(Trader paramTrader)
  {
    this.dao.updateTraderPassword(paramTrader);
  }
}

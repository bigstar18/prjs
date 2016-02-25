package gnnt.MEBS.auto.dwr.bean;

import gnnt.MEBS.timebargain.manage.service.CustomerManager;
import gnnt.MEBS.timebargain.manage.util.SysData;

public class TimebargainCustomer
{
  private CustomerManager customerManager = (CustomerManager)SysData.getBean("customerManager");
  
  public String getCustomerName(String paramString)
  {
    String str = this.customerManager.getCustomerName(paramString);
    return str;
  }
}

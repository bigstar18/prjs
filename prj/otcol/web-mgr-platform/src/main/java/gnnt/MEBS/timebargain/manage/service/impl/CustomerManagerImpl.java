package gnnt.MEBS.timebargain.manage.service.impl;

import gnnt.MEBS.timebargain.manage.dao.CustomerDAO;
import gnnt.MEBS.timebargain.manage.model.Customer;
import gnnt.MEBS.timebargain.manage.model.CustomerFunds;
import gnnt.MEBS.timebargain.manage.service.CustomerManager;
import java.util.List;
import java.util.Map;

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
  
  public String getCustomerName(String paramString)
  {
    return this.dao.getCustomerName(paramString);
  }
  
  public String getMaxCustomerID(String paramString)
  {
    return this.dao.getMaxCustomerID(paramString);
  }
  
  public String getMaxCustomerID(String paramString, Long paramLong)
  {
    return this.dao.getMaxCustomerID(paramString, paramLong);
  }
  
  public String getMinCustomerID(String paramString)
  {
    return this.dao.getMinCustomerID(paramString);
  }
  
  public String getMinCustomerID(String paramString, Long paramLong)
  {
    return this.dao.getMinCustomerID(paramString, paramLong);
  }
  
  public List getCustomers(Customer paramCustomer)
  {
    return this.dao.getCustomers(paramCustomer);
  }
  
  public void insertCustomer(Customer paramCustomer)
  {
    this.dao.insertCustomer(paramCustomer);
  }
  
  public Map insertCustomerRegister(Customer paramCustomer)
  {
    return this.dao.insertCustomerRegister(paramCustomer);
  }
  
  public void updateCustomer(Customer paramCustomer)
  {
    this.dao.updateCustomer(paramCustomer);
  }
  
  public void updateCustomerPassword(String paramString1, String paramString2, String paramString3)
  {
    this.dao.updateCustomerPassword(paramString1, paramString2, paramString3);
  }
  
  public void deleteCustomerById(String paramString)
  {
    this.dao.deleteCustomerById(paramString);
  }
  
  public void chgGroupById(Long paramLong, String paramString)
  {
    this.dao.chgGroupById(paramLong, paramString);
  }
  
  public void CustomerRemapById(String paramString)
  {
    this.dao.CustomerRemapById(paramString);
  }
  
  public CustomerFunds getCustomerFundsById(String paramString)
  {
    return this.dao.getCustomerFundsById(paramString);
  }
  
  public void addCustomerBalance(String paramString, Double paramDouble1, Double paramDouble2)
  {
    this.dao.addCustomerBalance(paramString, paramDouble1, paramDouble2);
  }
  
  public void addCustomerVirtualFunds(String paramString, Double paramDouble)
  {
    this.dao.addCustomerVirtualFunds(paramString, paramDouble);
  }
  
  public List customerQuery(Customer paramCustomer)
  {
    return this.dao.customerQuery(paramCustomer);
  }
  
  public void exitMarketCheck(String paramString)
  {
    this.dao.exitMarketCheck(paramString);
  }
  
  public void addCustomerBalance(String paramString, Double paramDouble)
  {
    this.dao.addCustomerBalance(paramString, paramDouble);
  }
  
  public List getKHCustomers(Customer paramCustomer)
  {
    return this.dao.getKHCustomers(paramCustomer);
  }
  
  public Customer getKHCustomerById(String paramString)
  {
    return this.dao.getKHCustomerById(paramString);
  }
  
  public void updateKHCustomer(Customer paramCustomer)
  {
    this.dao.updateKHCustomer(paramCustomer);
  }
  
  public void deleteKHCustomerById(String paramString)
  {
    this.dao.deleteKHCustomerById(paramString);
  }
  
  public List getFirmAndCustomer(Customer paramCustomer)
  {
    return this.dao.getFirmAndCustomer(paramCustomer);
  }
  
  public List getCustomerCounts(String paramString)
  {
    return this.dao.getCustomerCounts(paramString);
  }
  
  public void insertKHCustomer(Customer paramCustomer)
  {
    this.dao.insertKHCustomer(paramCustomer);
  }
  
  public void updateStatusFirm(Customer paramCustomer)
  {
    this.dao.updateStatusFirm(paramCustomer);
  }
  
  public void updateStatusCustomer(Customer paramCustomer)
  {
    this.dao.updateStatusCustomer(paramCustomer);
  }
  
  public void updateBack(Customer paramCustomer)
  {
    this.dao.updateBack(paramCustomer);
  }
  
  public void updateGoBack(Customer paramCustomer)
  {
    this.dao.updateGoBack(paramCustomer);
  }
  
  public List getApplyWaitList(Customer paramCustomer)
  {
    return this.dao.getApplyWaitList(paramCustomer);
  }
  
  public List getApplyAlreadyList(Customer paramCustomer)
  {
    return this.dao.getApplyAlreadyList(paramCustomer);
  }
  
  public void insertNewApp(Customer paramCustomer)
  {
    this.dao.insertNewApp(paramCustomer);
  }
  
  public List getVirtualFundsApplyById(String paramString)
  {
    return this.dao.getVirtualFundsApplyById(paramString);
  }
  
  public void CheckVirtual(Customer paramCustomer)
  {
    this.dao.CheckVirtual(paramCustomer);
  }
  
  public List getFirmPrivilege(Customer paramCustomer)
  {
    return this.dao.getFirmPrivilege(paramCustomer);
  }
  
  public Customer getFirmPrivilegeById(Customer paramCustomer)
  {
    return this.dao.getFirmPrivilegeById(paramCustomer);
  }
  
  public void updateFirmPrivilege(Customer paramCustomer)
  {
    this.dao.updateFirmPrivilege(paramCustomer);
  }
  
  public void insertFirmPrivilege(Customer paramCustomer)
  {
    this.dao.insertFirmPrivilege(paramCustomer);
  }
  
  public void deleteFirmPrivilegeById(String paramString)
  {
    this.dao.deleteFirmPrivilegeById(paramString);
  }
  
  public List getKHCustomerPrivilege(Customer paramCustomer)
  {
    return this.dao.getKHCustomerPrivilege(paramCustomer);
  }
  
  public Customer getCustomerPrivilegeById(Customer paramCustomer)
  {
    return this.dao.getCustomerPrivilegeById(paramCustomer);
  }
  
  public void insertCustomerPrivilege(Customer paramCustomer)
  {
    this.dao.insertCustomerPrivilege(paramCustomer);
  }
  
  public void updateCustomerPrivilege(Customer paramCustomer)
  {
    this.dao.updateCustomerPrivilege(paramCustomer);
  }
  
  public void deleteCustomerPrivilegeById(String paramString)
  {
    this.dao.deleteCustomerPrivilegeById(paramString);
  }
  
  public List getTypePrivilege(Customer paramCustomer)
  {
    return this.dao.getTypePrivilege(paramCustomer);
  }
  
  public List getFirmGroup(Customer paramCustomer)
  {
    return this.dao.getFirmGroup(paramCustomer);
  }
  
  public void batchSetInsertFirmPrivilege(int paramInt1, String paramString1, int paramInt2, String paramString2, int paramInt3, int paramInt4)
  {
    this.dao.batchSetInsertFirmPrivilege(paramInt1, paramString1, paramInt2, paramString2, paramInt3, paramInt4);
  }
  
  public void batchEmptyDeleteFirmPrivilege(int paramInt1, String paramString1, int paramInt2, String paramString2)
  {
    this.dao.batchEmptyDeleteFirmPrivilege(paramInt1, paramString1, paramInt2, paramString2);
  }
}

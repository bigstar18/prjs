package gnnt.MEBS.timebargain.manage.dao;

import gnnt.MEBS.timebargain.manage.model.Customer;
import gnnt.MEBS.timebargain.manage.model.CustomerFunds;
import java.util.List;
import java.util.Map;

public abstract interface CustomerDAO
  extends DAO
{
  public abstract Customer getCustomerById(String paramString);
  
  public abstract Customer getKHCustomerById(String paramString);
  
  public abstract String getCustomerName(String paramString);
  
  public abstract String getMaxCustomerID(String paramString);
  
  public abstract String getMaxCustomerID(String paramString, Long paramLong);
  
  public abstract String getMinCustomerID(String paramString);
  
  public abstract String getMinCustomerID(String paramString, Long paramLong);
  
  public abstract List getCustomers(Customer paramCustomer);
  
  public abstract List getKHCustomers(Customer paramCustomer);
  
  public abstract void insertCustomer(Customer paramCustomer);
  
  public abstract Map insertCustomerRegister(Customer paramCustomer);
  
  public abstract void updateCustomer(Customer paramCustomer);
  
  public abstract void updateKHCustomer(Customer paramCustomer);
  
  public abstract void updateCustomerPassword(String paramString1, String paramString2, String paramString3);
  
  public abstract void deleteCustomerById(String paramString);
  
  public abstract void deleteKHCustomerById(String paramString);
  
  public abstract void chgGroupById(Long paramLong, String paramString);
  
  public abstract void CustomerRemapById(String paramString);
  
  public abstract CustomerFunds getCustomerFundsById(String paramString);
  
  public abstract void addCustomerBalance(String paramString, Double paramDouble1, Double paramDouble2);
  
  public abstract void addCustomerVirtualFunds(String paramString, Double paramDouble);
  
  public abstract List customerQuery(Customer paramCustomer);
  
  public abstract void exitMarketCheck(String paramString);
  
  public abstract void addCustomerBalance(String paramString, Double paramDouble);
  
  public abstract List getFirmAndCustomer(Customer paramCustomer);
  
  public abstract List getCustomerCounts(String paramString);
  
  public abstract void insertKHCustomer(Customer paramCustomer);
  
  public abstract void updateStatusFirm(Customer paramCustomer);
  
  public abstract void updateStatusCustomer(Customer paramCustomer);
  
  public abstract void updateBack(Customer paramCustomer);
  
  public abstract void updateGoBack(Customer paramCustomer);
  
  public abstract List getApplyWaitList(Customer paramCustomer);
  
  public abstract List getApplyAlreadyList(Customer paramCustomer);
  
  public abstract void insertNewApp(Customer paramCustomer);
  
  public abstract List getVirtualFundsApplyById(String paramString);
  
  public abstract void CheckVirtual(Customer paramCustomer);
  
  public abstract List getFirmPrivilege(Customer paramCustomer);
  
  public abstract Customer getFirmPrivilegeById(Customer paramCustomer);
  
  public abstract void updateFirmPrivilege(Customer paramCustomer);
  
  public abstract void insertFirmPrivilege(Customer paramCustomer);
  
  public abstract void deleteFirmPrivilegeById(String paramString);
  
  public abstract List getKHCustomerPrivilege(Customer paramCustomer);
  
  public abstract Customer getCustomerPrivilegeById(Customer paramCustomer);
  
  public abstract void insertCustomerPrivilege(Customer paramCustomer);
  
  public abstract void updateCustomerPrivilege(Customer paramCustomer);
  
  public abstract void deleteCustomerPrivilegeById(String paramString);
  
  public abstract List getTypePrivilege(Customer paramCustomer);
  
  public abstract List getFirmGroup(Customer paramCustomer);
  
  public abstract void batchSetInsertFirmPrivilege(int paramInt1, String paramString1, int paramInt2, String paramString2, int paramInt3, int paramInt4);
  
  public abstract void batchEmptyDeleteFirmPrivilege(int paramInt1, String paramString1, int paramInt2, String paramString2);
}

package gnnt.MEBS.timebargain.manage.test;

import gnnt.MEBS.timebargain.manage.service.GageWarehouseManager;

public class GagewarehouseTest
  extends AbstractTest
{
  private GageWarehouseManager gageWarehouseManager;
  
  public void setGageWarehouseManager(GageWarehouseManager paramGageWarehouseManager)
  {
    this.gageWarehouseManager = paramGageWarehouseManager;
  }
  
  public void testAddGageWarehouse()
    throws Exception
  {
    String str1 = "1";
    String str2 = "2";
    String str3 = "3";
    String str4 = "4";
    String str5 = "5";
    String str6 = "6";
    long l = this.gageWarehouseManager.addGageWarehouse(str1, str2, str3, str4, str5, str6);
    assertEquals(1L, l);
  }
  
  public void testRevocation()
    throws Exception
  {
    String str1 = "1";
    String str2 = "2";
    String str3 = "3";
    String str4 = "4";
    String str5 = "5";
    String str6 = "6";
    long l1 = this.gageWarehouseManager.addGageWarehouse(str1, str2, str3, str4, str5, str6);
    assertEquals(1L, l1);
    long l2 = this.gageWarehouseManager.revocation("1", "2", "3", "4", "5");
    assertEquals(1L, l2);
  }
  
  public void testRevocation1()
    throws Exception
  {
    String str1 = "1";
    String str2 = "2";
    String str3 = "3";
    String str4 = "4";
    String str5 = "5";
    String str6 = "6";
    long l1 = this.gageWarehouseManager.addGageWarehouse(str1, str2, str3, str4, str5, str6);
    assertEquals(1L, l1);
    long l2 = this.gageWarehouseManager.revocation("1", "2", "3", "1000000", "5");
    assertEquals(-1L, l2);
  }
}

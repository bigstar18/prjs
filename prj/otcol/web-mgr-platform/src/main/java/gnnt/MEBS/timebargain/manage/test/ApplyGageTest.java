package gnnt.MEBS.timebargain.manage.test;

import gnnt.MEBS.timebargain.manage.dao.ApplyGageDAO;
import gnnt.MEBS.timebargain.manage.model.ApplyGage;
import gnnt.MEBS.timebargain.manage.service.ApplyGageManager;
import gnnt.MEBS.timebargain.manage.service.GageWarehouseManager;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class ApplyGageTest
  extends AbstractTest
{
  private ApplyGageManager applyGageManager;
  private ApplyGageDAO applyGageDAO;
  private GageWarehouseManager gageWarehouseManager;
  
  public void setGageWarehouseManager(GageWarehouseManager paramGageWarehouseManager)
  {
    this.gageWarehouseManager = paramGageWarehouseManager;
  }
  
  public void setApplyGageDAO(ApplyGageDAO paramApplyGageDAO)
  {
    this.applyGageDAO = paramApplyGageDAO;
  }
  
  public void setApplyGageManager(ApplyGageManager paramApplyGageManager)
  {
    this.applyGageManager = paramApplyGageManager;
  }
  
  public void testApplyGage()
  {
    ApplyGage localApplyGage = new ApplyGage();
    localApplyGage.setCustomerID("lw00");
    localApplyGage.setCommodityID("DM2010");
    localApplyGage.setQuantity(100L);
    localApplyGage.setApplyType(1);
    localApplyGage.setCreator("admin");
    localApplyGage.setRemark1("liwei");
    int i = this.applyGageManager.saveApplyGage(localApplyGage);
    assertEquals(0, i);
  }
  
  public void testCancelApplyGage()
  {
    ApplyGage localApplyGage = new ApplyGage();
    localApplyGage.setCustomerID("lw00");
    localApplyGage.setCommodityID("DM2010");
    localApplyGage.setQuantity(100L);
    localApplyGage.setApplyType(2);
    localApplyGage.setCreator("admin");
    localApplyGage.setRemark1("liwei");
    List localList = this.applyGageDAO.getResultListBySQL(localApplyGage);
    if (localList.size() > 0)
    {
      Map localMap = (Map)localList.get(0);
      long l1 = ((BigDecimal)localMap.get("GAGEQTY")).longValue();
      long l2 = ((BigDecimal)localMap.get("GAGEFROZENQTY")).longValue();
      localApplyGage.setQuantity(l1 - l2 + 1L);
    }
    int i = this.applyGageManager.saveApplyGage(localApplyGage);
    assertEquals(4, i);
  }
  
  public void testAuditApplyGage1()
  {
    ApplyGage localApplyGage = new ApplyGage();
    localApplyGage.setCustomerID("lw00");
    localApplyGage.setCommodityID("W1011");
    localApplyGage.setQuantity(10L);
    localApplyGage.setApplyType(1);
    localApplyGage.setCreator("admin");
    localApplyGage.setRemark1("liwei");
    localApplyGage.setCreateTime(new Date());
    localApplyGage.setModifyTime(new Date());
    int i = this.applyGageManager.saveApplyGage(localApplyGage);
    assertEquals(0, i);
    int j = this.applyGageManager.gageProcessorAudit(localApplyGage, "2");
    assertEquals(-4, j);
  }
  
  public void testAuditApplyGage2()
  {
    ApplyGage localApplyGage = new ApplyGage();
    localApplyGage.setCustomerID("lw00");
    localApplyGage.setCommodityID("DM2010");
    localApplyGage.setQuantity(100L);
    localApplyGage.setApplyType(1);
    localApplyGage.setCreator("admin");
    localApplyGage.setRemark1("liwei");
    localApplyGage.setCreateTime(new Date());
    localApplyGage.setModifyTime(new Date());
    int i = this.applyGageManager.saveApplyGage(localApplyGage);
    assertEquals(0, i);
    int j = this.applyGageManager.gageProcessorAudit(localApplyGage, "3");
    assertEquals(1, j);
    int k = this.applyGageManager.gageProcessorAudit(localApplyGage, "3");
    assertEquals(2, k);
  }
}

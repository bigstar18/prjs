package gnnt.MEBS.timebargain.manage.test;

import gnnt.MEBS.timebargain.manage.model.AheadSettle;
import gnnt.MEBS.timebargain.manage.service.AheadSettleManager;

public class AheadSettleTest
  extends AbstractTest
{
  private AheadSettleManager aheadSettleManager;
  
  public void setAheadSettleManager(AheadSettleManager paramAheadSettleManager)
  {
    this.aheadSettleManager = paramAheadSettleManager;
  }
  
  public void testAheadSettleAudit()
  {
    AheadSettle localAheadSettle = new AheadSettle();
    localAheadSettle.setApplyType(1);
    localAheadSettle.setCustomerID_B("liwei00");
    localAheadSettle.setCustomerID_S("majing00");
    localAheadSettle.setCommodityID("801");
    localAheadSettle.setPrice(100.0D);
    localAheadSettle.setQuantity(100L);
    localAheadSettle.setGageQty(100L);
    localAheadSettle.setCreator("admin");
    localAheadSettle.setRemark1("liwei");
    int i = this.aheadSettleManager.saveAheadSettle(localAheadSettle);
    assertEquals(0, i);
  }
}

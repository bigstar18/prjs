package gnnt.MEBS.delivery.command.testing;

import gnnt.MEBS.delivery.command.Information;
import gnnt.MEBS.delivery.command.PolicySuperior;
import gnnt.MEBS.delivery.command.model.SettleObject;
import gnnt.MEBS.delivery.model.settle.SettleMatch;
import gnnt.MEBS.delivery.model.workflow.RegStock;
import gnnt.MEBS.delivery.services.RegStockService;
import gnnt.MEBS.delivery.services.SettleMatchService;
import java.util.Map;

public class ChangeStatusTest
  extends AbstractTest
{
  public void testChangeStatusForCompliance()
  {
    String str1 = "liwei";
    String str2 = "fanzh";
    String str3 = "2";
    RegStock localRegStock = getRegStock(str2, 0);
    double d = localRegStock.getFrozenWeight();
    long l = addSettleMatch(10000.0D, 1000.0D, str1, str2, str3, 100.0D, 1, localRegStock.getRegStockId(), null);
    SettleMatch localSettleMatch1 = getSettleMatch(l);
    localRegStock = this.regStockService.getRegStockById(localRegStock.getRegStockId());
    assertEquals(Double.valueOf(localRegStock.getFrozenWeight()), Double.valueOf(d + 100.0D));
    SettleObject localSettleObject = getSettleObject(localSettleMatch1.getMatchId(), null, 2, 0.0D, false);
    Information localInformation = new Information();
    localInformation.setObject(localSettleObject);
    localInformation.setCommandName("settleCommand");
    localInformation.setReceiveName("changeStatus");
    Map localMap = this.policySuperior.policyExecuteCommand(localInformation);
    int i = ((Integer)localMap.get("result")).intValue();
    assertEquals(1, i);
    localRegStock = this.regStockService.getRegStockById(localRegStock.getRegStockId());
    assertEquals(Double.valueOf(localRegStock.getFrozenWeight()), Double.valueOf(d));
    SettleMatch localSettleMatch2 = this.settleMatchService.getSettleMatchById(localSettleObject.getMatchId());
    assertEquals(2, localSettleMatch2.getResult());
    assertEquals(null, localSettleMatch2.getRegStockId());
  }
  
  public void testChangeStatusForDefault()
  {
    String str1 = "liwei";
    String str2 = "fanzh";
    String str3 = "2";
    long l = addSettleMatch(10000.0D, 1000.0D, str1, str2, str3, 100.0D, 2, null, null);
    SettleMatch localSettleMatch1 = getSettleMatch(l);
    assertEquals(true, l > 0L);
    SettleObject localSettleObject = getSettleObject(localSettleMatch1.getMatchId(), null, 4, 0.0D, false);
    Information localInformation = new Information();
    localInformation.setObject(localSettleObject);
    localInformation.setCommandName("settleCommand");
    localInformation.setReceiveName("changeStatus");
    Map localMap = this.policySuperior.policyExecuteCommand(localInformation);
    int i = ((Integer)localMap.get("result")).intValue();
    assertEquals(1, i);
    SettleMatch localSettleMatch2 = this.settleMatchService.getSettleMatchById(localSettleObject.getMatchId());
    assertEquals(4, localSettleMatch2.getResult());
    assertEquals(null, localSettleMatch2.getRegStockId());
  }
  
  public void testChangeStatusForDefault2()
  {
    String str1 = "liwei";
    String str2 = "fanzh";
    String str3 = "2";
    long l = addSettleMatch(10000.0D, 1000.0D, str1, str2, str3, 100.0D, 2, null, null);
    SettleMatch localSettleMatch = getSettleMatch(l);
    assertEquals(true, l > 0L);
    RegStock localRegStock = getRegStock(str2, 0);
    double d = localRegStock.getFrozenWeight();
    SettleObject localSettleObject = getSettleObject(localSettleMatch.getMatchId(), localRegStock.getRegStockId(), 1, 0.0D, false);
    Information localInformation = new Information();
    localInformation.setObject(localSettleObject);
    localInformation.setCommandName("settleCommand");
    localInformation.setReceiveName("changeStatus");
    Map localMap = this.policySuperior.policyExecuteCommand(localInformation);
    int i = ((Integer)localMap.get("result")).intValue();
    assertEquals(1, i);
    localRegStock = this.regStockService.getRegStockById(localRegStock.getRegStockId());
    assertEquals(Double.valueOf(localRegStock.getFrozenWeight()), Double.valueOf(d + 100.0D));
  }
}

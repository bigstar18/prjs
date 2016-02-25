package gnnt.MEBS.delivery.command.testing;

import gnnt.MEBS.delivery.command.Information;
import gnnt.MEBS.delivery.command.PolicySuperior;
import gnnt.MEBS.delivery.command.model.SettleObject;
import gnnt.MEBS.delivery.model.settle.SettleMatch;
import gnnt.MEBS.delivery.model.workflow.RegStock;
import gnnt.MEBS.delivery.services.RegStockService;
import gnnt.MEBS.delivery.services.SettleMatchService;
import java.util.Map;

public class SettleRestoreTest
  extends AbstractTest
{
  public void testSettleRestoreForCompliance()
  {
    String str1 = "liwei";
    String str2 = "fanzh";
    String str3 = "2";
    RegStock localRegStock1 = getRegStock(str2, 0);
    double d = localRegStock1.getFrozenWeight();
    long l = addSettleMatch(10000.0D, 1000.0D, str1, str2, str3, 100.0D, 1, localRegStock1.getRegStockId(), null);
    SettleMatch localSettleMatch = getSettleMatch(l);
    localSettleMatch.setBuyMargin(500.0D);
    localSettleMatch.setBuyPayout(1000000.0D);
    localSettleMatch.setHL_Amount(500.0D);
    this.settleMatchService.updateSettleMatch(localSettleMatch);
    RegStock localRegStock2 = getRegStock(str2, 0);
    SettleObject localSettleObject = getSettleObject(localSettleMatch.getMatchId(), localRegStock2.getRegStockId(), 0, 0.0D, false);
    Information localInformation = new Information();
    localInformation.setObject(localSettleObject);
    localInformation.setCommandName("settleCommand");
    localInformation.setReceiveName("changeRegStock");
    Map localMap = this.policySuperior.policyExecuteCommand(localInformation);
    int i = ((Integer)localMap.get("result")).intValue();
    assertEquals(1, i);
    localRegStock2 = this.regStockService.getRegStockById(localRegStock2.getRegStockId());
    assertEquals(Double.valueOf(localRegStock2.getFrozenWeight()), Double.valueOf(d + 100.0D));
    localRegStock1 = this.regStockService.getRegStockById(localRegStock1.getRegStockId());
    assertEquals(Double.valueOf(localRegStock1.getFrozenWeight()), Double.valueOf(d));
    localSettleObject = getSettleObject(localSettleMatch.getMatchId(), null, 0, 0.0D, false);
    localInformation = new Information();
    localInformation.setObject(localSettleObject);
    localInformation.setCommandName("settleCommand");
    localInformation.setReceiveName("settleRestore");
    localMap = this.policySuperior.policyExecuteCommand(localInformation);
    i = ((Integer)localMap.get("result")).intValue();
    assertEquals(1, i);
    localRegStock2 = this.regStockService.getRegStockById(localRegStock2.getRegStockId());
    assertEquals(Double.valueOf(localRegStock2.getFrozenWeight()), Double.valueOf(d));
    localRegStock1 = this.regStockService.getRegStockById(localRegStock1.getRegStockId());
    assertEquals(Double.valueOf(localRegStock1.getFrozenWeight()), Double.valueOf(d + 100.0D));
    localSettleMatch = getSettleMatch(l);
    assertEquals(Double.valueOf(10000.0D), Double.valueOf(localSettleMatch.getBuyMargin()));
    assertEquals(Double.valueOf(0.0D), Double.valueOf(localSettleMatch.getBuyPayout()));
    assertEquals(Double.valueOf(0.0D), Double.valueOf(localSettleMatch.getHL_Amount()));
  }
  
  public void testSettleRestoreForDefault()
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
    localSettleObject = getSettleObject(localSettleMatch1.getMatchId(), null, 0, 0.0D, false);
    localInformation = new Information();
    localInformation.setObject(localSettleObject);
    localInformation.setCommandName("settleCommand");
    localInformation.setReceiveName("settleRestore");
    localMap = this.policySuperior.policyExecuteCommand(localInformation);
    i = ((Integer)localMap.get("result")).intValue();
    assertEquals(1, i);
    localSettleMatch2 = this.settleMatchService.getSettleMatchById(localSettleObject.getMatchId());
    assertEquals(2, localSettleMatch2.getResult());
  }
}

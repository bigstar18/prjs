package gnnt.MEBS.delivery.command.testing;

import gnnt.MEBS.base.util.Arith;
import gnnt.MEBS.delivery.command.Information;
import gnnt.MEBS.delivery.command.PolicySuperior;
import gnnt.MEBS.delivery.command.model.SettleObject;
import gnnt.MEBS.delivery.model.settle.SettleMatch;
import gnnt.MEBS.delivery.model.workflow.RegStock;
import gnnt.MEBS.delivery.services.RegStockService;
import java.util.Map;

public class ChangeRegStockTest
  extends AbstractTest
{
  public void testChangeRegStock1()
  {
    String str1 = "liwei";
    String str2 = "fanzh";
    String str3 = "2";
    RegStock localRegStock1 = getRegStock(str2, 0);
    double d1 = localRegStock1.getWeight() - localRegStock1.getFrozenWeight();
    double d2 = d1;
    long l = addSettleMatch(10000.0D, 1000.0D, str1, str2, str3, d2, 1, localRegStock1.getRegStockId(), null);
    SettleMatch localSettleMatch = getSettleMatch(l);
    RegStock localRegStock2 = getRegStock("hanwei", 0);
    SettleObject localSettleObject = getSettleObject(localSettleMatch.getMatchId(), localRegStock2.getRegStockId(), 0, 0.0D, false);
    Information localInformation = new Information();
    localInformation.setObject(localSettleObject);
    localInformation.setCommandName("settleCommand");
    localInformation.setReceiveName("changeRegStock");
    Map localMap = this.policySuperior.policyExecuteCommand(localInformation);
    int i = ((Integer)localMap.get("result")).intValue();
    assertEquals(-16, i);
  }
  
  public void testChangeRegStock2()
  {
    String str1 = "hanwei";
    String str2 = "fanzh";
    String str3 = "2";
    RegStock localRegStock1 = getRegStock(str2, 0);
    double d1 = localRegStock1.getWeight() - localRegStock1.getFrozenWeight();
    double d2 = d1;
    long l = addSettleMatch(10000.0D, 1000.0D, str1, str2, str3, d2, 1, localRegStock1.getRegStockId(), null);
    SettleMatch localSettleMatch = getSettleMatch(l);
    RegStock localRegStock2 = getRegStock(str2, 0);
    localRegStock2.setWeight(localRegStock2.getFrozenWeight() + d2 - 10.0D);
    this.regStockService.updateRegStock(localRegStock2);
    SettleObject localSettleObject = getSettleObject(localSettleMatch.getMatchId(), localRegStock2.getRegStockId(), 0, 0.0D, false);
    Information localInformation = new Information();
    localInformation.setObject(localSettleObject);
    localInformation.setCommandName("settleCommand");
    localInformation.setReceiveName("changeRegStock");
    Map localMap = this.policySuperior.policyExecuteCommand(localInformation);
    int i = ((Integer)localMap.get("result")).intValue();
    assertEquals(-16, i);
  }
  
  public void testChangeRegStock3()
  {
    String str1 = "liwei";
    String str2 = "fanzh";
    String str3 = "2";
    RegStock localRegStock1 = getRegStock(str2, 0);
    double d1 = localRegStock1.getFrozenWeight();
    double d2 = localRegStock1.getWeight();
    double d3 = d2 - d1;
    double d4 = d3;
    long l = addSettleMatch(10000.0D, 1000.0D, str1, str2, str3, d4, 1, localRegStock1.getRegStockId(), null);
    SettleMatch localSettleMatch1 = getSettleMatch(l);
    RegStock localRegStock2 = getRegStock(str2, 0);
    double d5 = localRegStock2.getFrozenWeight();
    SettleObject localSettleObject = getSettleObject(localSettleMatch1.getMatchId(), localRegStock2.getRegStockId(), 0, 0.0D, false);
    Information localInformation = new Information();
    localInformation.setObject(localSettleObject);
    localInformation.setCommandName("settleCommand");
    localInformation.setReceiveName("changeRegStock");
    Map localMap = this.policySuperior.policyExecuteCommand(localInformation);
    int i = ((Integer)localMap.get("result")).intValue();
    assertEquals(1, i);
    RegStock localRegStock3 = this.regStockService.getRegStockById(localRegStock2.getRegStockId());
    assertTrue(Arith.compareTo(localRegStock3.getFrozenWeight(), Arith.add(d4, d5)) == 0);
    RegStock localRegStock4 = this.regStockService.getRegStockById(localRegStock1.getRegStockId());
    assertTrue(Arith.compareTo(localRegStock4.getFrozenWeight(), localRegStock1.getFrozenWeight()) == 0);
    SettleMatch localSettleMatch2 = localSettleObject.getSettleMatch();
    assertEquals(localRegStock2.getRegStockId(), localSettleMatch2.getRegStockId());
  }
}

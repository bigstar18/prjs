package gnnt.MEBS.delivery.command.testing;

import gnnt.MEBS.base.util.Arith;
import gnnt.MEBS.delivery.command.Information;
import gnnt.MEBS.delivery.command.PolicySuperior;
import gnnt.MEBS.delivery.command.model.SettleObject;
import gnnt.MEBS.delivery.model.settle.SettleMatch;
import gnnt.MEBS.delivery.model.workflow.RegStock;
import gnnt.MEBS.delivery.services.MoneyDoService;
import gnnt.MEBS.delivery.services.RegStockService;
import gnnt.MEBS.delivery.services.SettleMatchService;
import java.util.Map;

public class ChangeRegStockThenReturn
  extends AbstractTest
{
  public void testChangeRegStockThenReturn()
  {
    String str1 = "liwei";
    String str2 = "fanzh";
    String str3 = "2";
    RegStock localRegStock1 = getRegStock(str2, 0);
    RegStock localRegStock2 = getRegStock(str2, 0);
    double d1 = localRegStock1.getFrozenWeight();
    double d2 = localRegStock1.getFrozenWeight();
    double d3 = localRegStock1.getWeight();
    double d4 = d3 - d2;
    double d5 = d4;
    long l = addSettleMatch(10000.0D, 1000.0D, str1, str2, str3, d5, 1, localRegStock1.getRegStockId(), null);
    SettleMatch localSettleMatch1 = getSettleMatch(l);
    RegStock localRegStock3 = getRegStock(str2, 0);
    double d6 = localRegStock3.getFrozenWeight();
    SettleObject localSettleObject = getSettleObject(localSettleMatch1.getMatchId(), localRegStock3.getRegStockId(), 0, 0.0D, false);
    Information localInformation = new Information();
    localInformation.setObject(localSettleObject);
    localInformation.setCommandName("settleCommand");
    localInformation.setReceiveName("changeRegStock");
    Map localMap = this.policySuperior.policyExecuteCommand(localInformation);
    int i = ((Integer)localMap.get("result")).intValue();
    assertEquals(1, i);
    double d7 = 100.0D;
    double d8 = this.moneyDoService.getFirmFunds(str1);
    double d9 = localSettleObject.getAmount();
    double d10 = localSettleMatch1.getSellIncome_Ref();
    double d11 = localSettleMatch1.getBuyPayout_Ref();
    double d12 = d10 - d11;
    double d13 = localSettleMatch1.getBuyPayout();
    localSettleMatch1.setSellIncome(d13 + d7 + d12);
    this.settleMatchService.updateSettleMatch(localSettleMatch1);
    localInformation.setObject(localSettleObject);
    localInformation.setCommandName("settleCommand");
    localInformation.setReceiveName("payout");
    double d14 = d13 + d9;
    SettleMatch localSettleMatch2 = this.settleMatchService.getSettleMatchById(localSettleObject.getMatchId());
    double d15 = localSettleMatch2.getBuyPayout();
    assertEquals(Double.valueOf(d14), Double.valueOf(d15));
    double d16 = this.moneyDoService.getFirmFunds(str1);
    assertTrue(Arith.compareTo(d16, Arith.sub(d8, d9)) == 0);
    RegStock localRegStock4 = this.regStockService.getRegStockById(localRegStock3.getRegStockId());
    assertTrue(Arith.compareTo(localRegStock4.getFrozenWeight(), Arith.add(d5, d6)) == 0);
    RegStock localRegStock5 = this.regStockService.getRegStockById(localRegStock1.getRegStockId());
    assertTrue(Arith.compareTo(localRegStock5.getFrozenWeight(), localRegStock1.getFrozenWeight()) == 0);
    SettleMatch localSettleMatch3 = localSettleObject.getSettleMatch();
    assertEquals(localRegStock3.getRegStockId(), localSettleMatch3.getRegStockId());
    localInformation = new Information();
    localInformation.setObject(localSettleObject);
    localInformation.setCommandName("settleCommand");
    localInformation.setReceiveName("settleRestore");
    localMap = this.policySuperior.policyExecuteCommand(localInformation);
    i = ((Integer)localMap.get("result")).intValue();
    assertEquals(1, i);
    localRegStock2 = this.regStockService.getRegStockById(localRegStock2.getRegStockId());
    assertEquals(Double.valueOf(localRegStock2.getFrozenWeight()), Double.valueOf(d1));
    localRegStock1 = this.regStockService.getRegStockById(localRegStock1.getRegStockId());
    assertEquals(Double.valueOf(localRegStock1.getFrozenWeight()), Double.valueOf(d1));
    localSettleMatch1 = getSettleMatch(l);
    assertEquals(Double.valueOf(10000.0D), Double.valueOf(localSettleMatch1.getBuyMargin()));
    assertEquals(Double.valueOf(0.0D), Double.valueOf(localSettleMatch1.getBuyPayout()));
    assertEquals(Double.valueOf(0.0D), Double.valueOf(localSettleMatch1.getHL_Amount()));
  }
}

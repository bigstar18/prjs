package gnnt.MEBS.delivery.command.testing;

import gnnt.MEBS.base.util.Arith;
import gnnt.MEBS.delivery.command.Information;
import gnnt.MEBS.delivery.command.PolicySuperior;
import gnnt.MEBS.delivery.command.model.SettleObject;
import gnnt.MEBS.delivery.model.settle.SettleMatch;
import gnnt.MEBS.delivery.model.workflow.RegStock;
import gnnt.MEBS.delivery.services.MoneyDoService;
import java.util.Map;

public class ReturnMoneyForSellTest
  extends AbstractTest
{
  public void testReturnMoneyForSell1()
  {
    String str1 = "liwei";
    String str2 = "fanzh";
    String str3 = "3";
    RegStock localRegStock = getRegStock(str2, 0);
    long l = addSettleMatch(10000.0D, 1000.0D, str1, str2, str3, 100.0D, 1, localRegStock.getRegStockId(), null);
    SettleMatch localSettleMatch = getSettleMatch(l);
    double d1 = -10.0D;
    SettleObject localSettleObject = getSettleObject(localSettleMatch.getMatchId(), localRegStock.getRegStockId(), 0, d1, true);
    double d2 = this.moneyDoService.getFirmFunds(str2);
    this.moneyDoService.updateFirmFunds(str2, "101", -d2 + 9.0D);
    Information localInformation = new Information();
    localInformation.setObject(localSettleObject);
    localInformation.setCommandName("settleCommand");
    localInformation.setReceiveName("returnMarginForSell");
    Map localMap = this.policySuperior.policyExecuteCommand(localInformation);
    int i = ((Integer)localMap.get("result")).intValue();
    assertEquals(-14, i);
  }
  
  public void testReturnMoneyForSell2()
  {
    String str1 = "liwei";
    String str2 = "fanzh";
    String str3 = "3";
    RegStock localRegStock = getRegStock(str2, 0);
    long l = addSettleMatch(10000.0D, 1000.0D, str1, str2, str3, 100.0D, 1, localRegStock.getRegStockId(), null);
    SettleMatch localSettleMatch = getSettleMatch(l);
    double d = 10001.0D;
    SettleObject localSettleObject = getSettleObject(localSettleMatch.getMatchId(), localRegStock.getRegStockId(), 0, d, true);
    Information localInformation = new Information();
    localInformation.setObject(localSettleObject);
    localInformation.setCommandName("settleCommand");
    localInformation.setReceiveName("returnMarginForSell");
    Map localMap = this.policySuperior.policyExecuteCommand(localInformation);
    int i = ((Integer)localMap.get("result")).intValue();
    assertEquals(-3, i);
  }
  
  public void testReturnMoneyForSell3()
  {
    String str1 = "liwei";
    String str2 = "fanzh";
    String str3 = "3";
    RegStock localRegStock = getRegStock(str2, 0);
    long l = addSettleMatch(10000.0D, 1000.0D, str1, str2, str3, 100.0D, 1, localRegStock.getRegStockId(), null);
    SettleMatch localSettleMatch1 = getSettleMatch(l);
    double d1 = 1000.0D;
    SettleObject localSettleObject = getSettleObject(localSettleMatch1.getMatchId(), localRegStock.getRegStockId(), 0, d1, true);
    double d2 = this.moneyDoService.getFirmFunds(str2);
    double d3 = localSettleMatch1.getSellMargin();
    Information localInformation = new Information();
    localInformation.setObject(localSettleObject);
    localInformation.setCommandName("settleCommand");
    localInformation.setReceiveName("returnMarginForSell");
    Map localMap = this.policySuperior.policyExecuteCommand(localInformation);
    int i = ((Integer)localMap.get("result")).intValue();
    assertEquals(1, i);
    SettleMatch localSettleMatch2 = localSettleObject.getSettleMatch();
    double d4 = localSettleMatch2.getSellMargin();
    assertTrue(Arith.compareTo(d4, Arith.sub(d3, d1)) == 0);
    double d5 = this.moneyDoService.getFirmFunds(str2);
    assertTrue(Arith.compareTo(d5, Arith.add(d2, d1)) == 0);
  }
}

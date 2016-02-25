package gnnt.MEBS.delivery.command.testing;

import gnnt.MEBS.delivery.command.Information;
import gnnt.MEBS.delivery.command.PolicySuperior;
import gnnt.MEBS.delivery.command.model.SettleObject;
import gnnt.MEBS.delivery.model.settle.SettleMatch;
import gnnt.MEBS.delivery.model.workflow.RegStock;
import gnnt.MEBS.delivery.services.MoneyDoService;
import gnnt.MEBS.delivery.services.SettleMatchService;
import java.util.Map;

public class SettleTransferTest
  extends AbstractTest
{
  public void testSettleTransfer1()
  {
    String str1 = "liwei";
    String str2 = "fanzh";
    String str3 = "2";
    RegStock localRegStock = getRegStock(str2, 0);
    long l = addSettleMatch(10000.0D, 1000.0D, str1, str2, str3, 100.0D, 1, localRegStock.getRegStockId(), null);
    SettleMatch localSettleMatch = getSettleMatch(l);
    localSettleMatch.setIsChangeOwner(1);
    this.settleMatchService.updateSettleMatch(localSettleMatch);
    SettleObject localSettleObject = getSettleObject(localSettleMatch.getMatchId(), null, 0, 0.0D, true);
    Information localInformation = new Information();
    localInformation = new Information();
    localInformation.setObject(localSettleObject);
    localInformation.setCommandName("settleCommand");
    localInformation.setReceiveName("settleTransfer");
    Map localMap = this.policySuperior.policyExecuteCommand(localInformation);
    int i = ((Integer)localMap.get("result")).intValue();
    assertEquals(-6, i);
  }
  
  public void testSettleTransfer2()
  {
    String str1 = "liwei";
    String str2 = "fanzh";
    String str3 = "2";
    RegStock localRegStock = getRegStock(str2, 0);
    long l = addSettleMatch(10000.0D, 1000.0D, str1, str2, str3, 100.0D, 1, localRegStock.getRegStockId(), null);
    SettleMatch localSettleMatch = getSettleMatch(l);
    double d1 = localSettleMatch.getSellIncome_Ref();
    double d2 = localSettleMatch.getHL_Amount();
    double d3 = d1 + d2 + 1.0D;
    localSettleMatch.setSellIncome(d3);
    this.settleMatchService.updateSettleMatch(localSettleMatch);
    SettleObject localSettleObject = getSettleObject(localSettleMatch.getMatchId(), null, 0, 0.0D, true);
    Information localInformation = new Information();
    localInformation = new Information();
    localInformation.setObject(localSettleObject);
    localInformation.setCommandName("settleCommand");
    localInformation.setReceiveName("settleTransfer");
    Map localMap = this.policySuperior.policyExecuteCommand(localInformation);
    int i = ((Integer)localMap.get("result")).intValue();
    assertEquals(-8, i);
  }
  
  public void testSettleTransfer3()
  {
    String str1 = "liwei";
    String str2 = "fanzh";
    String str3 = "2";
    RegStock localRegStock = getRegStock(str2, 0);
    double d1 = localRegStock.getFrozenWeight();
    double d2 = localRegStock.getWeight();
    long l = addSettleMatch(10000.0D, 1000.0D, str1, str2, str3, 100.0D, 1, localRegStock.getRegStockId(), null);
    SettleMatch localSettleMatch1 = getSettleMatch(l);
    double d3 = this.moneyDoService.getFirmFunds(str1);
    double d4 = this.moneyDoService.getFirmFunds(str2);
    SettleObject localSettleObject = getSettleObject(localSettleMatch1.getMatchId(), null, 0, 100000.0D, true);
    Information localInformation = new Information();
    localInformation.setObject(localSettleObject);
    localInformation.setCommandName("settleCommand");
    localInformation.setReceiveName("payout");
    Map localMap = this.policySuperior.policyExecuteCommand(localInformation);
    int i = ((Integer)localMap.get("result")).intValue();
    assertEquals(1, i);
    double d5 = this.moneyDoService.getFirmFunds(str1);
    double d6 = this.moneyDoService.getFirmFunds(str2);
    assertEquals(Double.valueOf(d3), Double.valueOf(d5 + 100000.0D));
    assertEquals(Double.valueOf(d4), Double.valueOf(d6));
    localSettleObject = getSettleObject(localSettleMatch1.getMatchId(), null, 0, 100000.0D, true);
    localInformation = new Information();
    localInformation.setObject(localSettleObject);
    localInformation.setCommandName("settleCommand");
    localInformation.setReceiveName("income");
    localMap = this.policySuperior.policyExecuteCommand(localInformation);
    i = ((Integer)localMap.get("result")).intValue();
    assertEquals(1, i);
    double d7 = this.moneyDoService.getFirmFunds(str1);
    double d8 = this.moneyDoService.getFirmFunds(str2);
    assertEquals(Double.valueOf(d5), Double.valueOf(d7));
    assertEquals(Double.valueOf(d6), Double.valueOf(d8 - 100000.0D));
    localSettleObject = getSettleObject(localSettleMatch1.getMatchId(), null, 0, 0.0D, true);
    localInformation = new Information();
    localInformation = new Information();
    localInformation.setObject(localSettleObject);
    localInformation.setCommandName("settleCommand");
    localInformation.setReceiveName("settleTransfer");
    localMap = this.policySuperior.policyExecuteCommand(localInformation);
    i = ((Integer)localMap.get("result")).intValue();
    assertEquals(1, i);
    SettleMatch localSettleMatch2 = this.settleMatchService.getSettleMatchById(localSettleMatch1.getMatchId());
    assertEquals(1, localSettleMatch2.getIsChangeOwner());
  }
}

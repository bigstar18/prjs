package gnnt.MEBS.delivery.command.testing;

import gnnt.MEBS.delivery.command.Information;
import gnnt.MEBS.delivery.command.PolicySuperior;
import gnnt.MEBS.delivery.command.model.SettleObject;
import gnnt.MEBS.delivery.model.settle.SettleMatch;
import gnnt.MEBS.delivery.services.MoneyDoService;
import gnnt.MEBS.delivery.services.SettleMatchService;
import java.util.Map;

public class ReceivePenaltyTest
  extends AbstractTest
{
  public void testReceivePenalty1()
  {
    String str1 = "liwei";
    String str2 = "fanzh";
    String str3 = "2";
    long l = addSettleMatch(10000.0D, 1000.0D, str1, str2, str3, 100.0D, 2, null, null);
    SettleMatch localSettleMatch = getSettleMatch(l);
    localSettleMatch.setSellIncome(100.0D);
    localSettleMatch.setSellIncome_Ref(50.0D);
    localSettleMatch.setHL_Amount(20.0D);
    this.settleMatchService.updateSettleMatch(localSettleMatch);
    SettleObject localSettleObject = getSettleObject(localSettleMatch.getMatchId(), null, 0, 20.0D, false);
    Information localInformation = new Information();
    localInformation.setObject(localSettleObject);
    localInformation.setCommandName("settleCommand");
    localInformation.setReceiveName("receivePenalty");
    Map localMap1 = this.policySuperior.policyExecuteCommand(localInformation);
    int i = ((Integer)localMap1.get("result")).intValue();
    assertEquals(-2, i);
    localSettleMatch.setResult(3);
    this.settleMatchService.updateSettleMatch(localSettleMatch);
    localSettleObject.setSign(true);
    Map localMap2 = this.policySuperior.policyExecuteCommand(localInformation);
    int j = ((Integer)localMap2.get("result")).intValue();
    assertEquals(-2, j);
  }
  
  public void testReceivePenalty2()
  {
    String str1 = "liwei";
    String str2 = "fanzh";
    String str3 = "2";
    long l = addSettleMatch(10.0D, 1000.0D, str1, str2, str3, 100.0D, 2, null, null);
    SettleMatch localSettleMatch = getSettleMatch(l);
    localSettleMatch.setSellIncome(100.0D);
    localSettleMatch.setSellIncome_Ref(50.0D);
    localSettleMatch.setHL_Amount(20.0D);
    this.settleMatchService.updateSettleMatch(localSettleMatch);
    double d1 = this.moneyDoService.getFirmFunds(str1);
    this.moneyDoService.updateFirmFunds(str1, "101", -d1 + 1.0D);
    double d2 = this.moneyDoService.getFirmFunds(str2);
    this.moneyDoService.updateFirmFunds(str2, "101", -d2 + 1.0D);
    SettleObject localSettleObject = getSettleObject(localSettleMatch.getMatchId(), null, 0, 20.0D, true);
    Information localInformation = new Information();
    localInformation.setObject(localSettleObject);
    localInformation.setCommandName("settleCommand");
    localInformation.setReceiveName("receivePenalty");
    Map localMap1 = this.policySuperior.policyExecuteCommand(localInformation);
    int i = ((Integer)localMap1.get("result")).intValue();
    assertEquals(-14, i);
    localSettleMatch.setResult(3);
    this.settleMatchService.updateSettleMatch(localSettleMatch);
    localSettleObject.setSign(false);
    Map localMap2 = this.policySuperior.policyExecuteCommand(localInformation);
    int j = ((Integer)localMap2.get("result")).intValue();
    assertEquals(-14, j);
  }
  
  public void testReceivePenalty3()
  {
    String str1 = "liwei";
    String str2 = "fanzh";
    String str3 = "2";
    long l = addSettleMatch(10000.0D, 1000.0D, str1, str2, str3, 100.0D, 2, null, null);
    SettleMatch localSettleMatch = getSettleMatch(l);
    localSettleMatch.setSellIncome(100.0D);
    localSettleMatch.setSellIncome_Ref(50.0D);
    localSettleMatch.setHL_Amount(20.0D);
    localSettleMatch.setPenalty_B(-10.0D);
    this.settleMatchService.updateSettleMatch(localSettleMatch);
    SettleObject localSettleObject = getSettleObject(localSettleMatch.getMatchId(), null, 0, 5.0D, true);
    Information localInformation = new Information();
    localInformation.setObject(localSettleObject);
    localInformation.setCommandName("settleCommand");
    localInformation.setReceiveName("receivePenalty");
    Map localMap1 = this.policySuperior.policyExecuteCommand(localInformation);
    int i = ((Integer)localMap1.get("result")).intValue();
    assertEquals(-5, i);
    localSettleMatch.setResult(3);
    localSettleMatch.setPenalty_S(-10.0D);
    this.settleMatchService.updateSettleMatch(localSettleMatch);
    localSettleObject.setSign(false);
    Map localMap2 = this.policySuperior.policyExecuteCommand(localInformation);
    int j = ((Integer)localMap2.get("result")).intValue();
    assertEquals(-5, j);
  }
  
  public void testReceivePenalty4()
  {
    String str1 = "liwei";
    String str2 = "fanzh";
    String str3 = "2";
    long l = addSettleMatch(10000.0D, 1000.0D, str1, str2, str3, 100.0D, 2, null, null);
    SettleMatch localSettleMatch1 = getSettleMatch(l);
    localSettleMatch1.setSellIncome(100.0D);
    localSettleMatch1.setSellIncome_Ref(50.0D);
    localSettleMatch1.setHL_Amount(20.0D);
    localSettleMatch1.setBuyMargin(110.0D);
    this.settleMatchService.updateSettleMatch(localSettleMatch1);
    SettleObject localSettleObject = getSettleObject(localSettleMatch1.getMatchId(), null, 0, 20.0D, true);
    Information localInformation = new Information();
    localInformation.setObject(localSettleObject);
    localInformation.setCommandName("settleCommand");
    localInformation.setReceiveName("receivePenalty");
    Map localMap = this.policySuperior.policyExecuteCommand(localInformation);
    int i = ((Integer)localMap.get("result")).intValue();
    assertEquals(1, i);
    SettleMatch localSettleMatch2 = getSettleMatch(l);
    double d = localSettleMatch2.getBuyMargin();
    assertEquals(Double.valueOf(0.0D), Double.valueOf(d));
  }
}

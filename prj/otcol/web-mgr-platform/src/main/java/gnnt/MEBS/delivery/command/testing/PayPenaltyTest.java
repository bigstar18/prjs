package gnnt.MEBS.delivery.command.testing;

import gnnt.MEBS.base.util.Arith;
import gnnt.MEBS.delivery.command.Information;
import gnnt.MEBS.delivery.command.PolicySuperior;
import gnnt.MEBS.delivery.command.model.SettleObject;
import gnnt.MEBS.delivery.model.settle.SettleMatch;
import gnnt.MEBS.delivery.services.MoneyDoService;
import gnnt.MEBS.delivery.services.SettleMatchService;
import java.util.Map;

public class PayPenaltyTest
  extends AbstractTest
{
  public void testPayPenalty1()
  {
    String str1 = "liwei";
    String str2 = "fanzh";
    String str3 = "2";
    double d = 100.0D;
    int i = 2;
    long l = addSettleMatch(10000.0D, 1000.0D, str1, str2, str3, 100.0D, i, null, null);
    SettleMatch localSettleMatch = getSettleMatch(l);
    SettleObject localSettleObject = getSettleObject(localSettleMatch.getMatchId(), null, 0, d, true);
    Information localInformation = new Information();
    localInformation.setObject(localSettleObject);
    localInformation.setCommandName("settleCommand");
    localInformation.setReceiveName("payPenalty");
    Map localMap = this.policySuperior.policyExecuteCommand(localInformation);
    int j = ((Integer)localMap.get("result")).intValue();
    assertEquals(-2, j);
  }
  
  public void testPayPenalty2()
  {
    String str1 = "liwei";
    String str2 = "fanzh";
    String str3 = "2";
    double d1 = 100.0D;
    int i = 3;
    long l = addSettleMatch(10000.0D, 1000.0D, str1, str2, str3, 100.0D, i, null, null);
    SettleMatch localSettleMatch = getSettleMatch(l);
    SettleObject localSettleObject = getSettleObject(localSettleMatch.getMatchId(), null, 0, d1, true);
    double d2 = this.moneyDoService.getFirmFunds(str1);
    this.moneyDoService.updateFirmFunds(str1, "101", -d2 - 200.0D);
    Information localInformation = new Information();
    localInformation.setObject(localSettleObject);
    localInformation.setCommandName("settleCommand");
    localInformation.setReceiveName("payPenalty");
    Map localMap = this.policySuperior.policyExecuteCommand(localInformation);
    int j = ((Integer)localMap.get("result")).intValue();
    assertEquals(-14, j);
  }
  
  public void testPayPenalty3()
  {
    String str1 = "liwei";
    String str2 = "fanzh";
    String str3 = "2";
    double d = -200.0D;
    int i = 3;
    long l = addSettleMatch(10000.0D, 1000.0D, str1, str2, str3, 100.0D, i, null, null);
    SettleMatch localSettleMatch = getSettleMatch(l);
    localSettleMatch.setPenalty_B(100.0D);
    this.settleMatchService.updateSettleMatch(localSettleMatch);
    SettleObject localSettleObject = getSettleObject(localSettleMatch.getMatchId(), null, 0, d, true);
    Information localInformation = new Information();
    localInformation.setObject(localSettleObject);
    localInformation.setCommandName("settleCommand");
    localInformation.setReceiveName("payPenalty");
    Map localMap = this.policySuperior.policyExecuteCommand(localInformation);
    int j = ((Integer)localMap.get("result")).intValue();
    assertEquals(-5, j);
  }
  
  public void testPayPenalty4()
  {
    String str1 = "liwei";
    String str2 = "fanzh";
    String str3 = "2";
    double d1 = 100.0D;
    int i = 3;
    long l = addSettleMatch(10000.0D, 1000.0D, str1, str2, str3, 100.0D, i, null, null);
    SettleMatch localSettleMatch1 = getSettleMatch(l);
    double d2 = localSettleMatch1.getPenalty_B();
    SettleObject localSettleObject = getSettleObject(localSettleMatch1.getMatchId(), null, 0, d1, true);
    Information localInformation = new Information();
    localInformation.setObject(localSettleObject);
    localInformation.setCommandName("settleCommand");
    localInformation.setReceiveName("payPenalty");
    Map localMap = this.policySuperior.policyExecuteCommand(localInformation);
    int j = ((Integer)localMap.get("result")).intValue();
    assertEquals(1, j);
    SettleMatch localSettleMatch2 = localSettleObject.getSettleMatch();
    double d3 = localSettleMatch2.getPenalty_B();
    assertTrue(Arith.compareTo(d3, Arith.add(d2, d1)) == 0);
  }
}

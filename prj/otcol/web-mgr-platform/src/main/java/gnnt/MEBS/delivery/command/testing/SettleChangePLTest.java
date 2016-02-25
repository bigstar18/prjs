package gnnt.MEBS.delivery.command.testing;

import gnnt.MEBS.delivery.command.Information;
import gnnt.MEBS.delivery.command.PolicySuperior;
import gnnt.MEBS.delivery.command.model.SettleObject;
import gnnt.MEBS.delivery.model.settle.SettleMatch;
import gnnt.MEBS.delivery.services.MoneyDoService;
import gnnt.MEBS.delivery.services.SettleMatchService;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class SettleChangePLTest
  extends AbstractTest
{
  @Autowired
  @Qualifier("w_settleMatchService")
  private SettleMatchService settleMatchService;
  
  public void testSettleChangePL1()
  {
    String str1 = "liwei";
    String str2 = "fanzh";
    String str3 = "2";
    long l = addSettleMatch(10000.0D, 1000.0D, str1, str2, str3, 100.0D, 2, null, null);
    SettleMatch localSettleMatch = getSettleMatch(l);
    this.settleMatchService.updateSettleMatch(localSettleMatch);
    double d = this.moneyDoService.getFirmFunds(str1);
    this.moneyDoService.updateFirmFunds(str1, "101", -d + 10.0D);
    SettleObject localSettleObject = new SettleObject();
    localSettleObject.setMatchId(localSettleMatch.getMatchId());
    localSettleObject.setSign(true);
    localSettleObject.setAmount(-20.0D);
    Information localInformation = new Information();
    localInformation.setObject(localSettleObject);
    localInformation.setCommandName("settleCommand");
    localInformation.setReceiveName("settleChangePL");
    Map localMap = this.policySuperior.policyExecuteCommand(localInformation);
    int i = ((Integer)localMap.get("result")).intValue();
    assertEquals(-14, i);
  }
  
  public void testSettleChangePL2()
  {
    String str1 = "liwei";
    String str2 = "fanzh";
    String str3 = "2";
    long l = addSettleMatch(10000.0D, 1000.0D, str1, str2, str3, 100.0D, 2, null, null);
    SettleMatch localSettleMatch1 = getSettleMatch(l);
    localSettleMatch1.setSettlePL_B(101.0D);
    this.settleMatchService.updateSettleMatch(localSettleMatch1);
    SettleObject localSettleObject = new SettleObject();
    localSettleObject.setMatchId(localSettleMatch1.getMatchId());
    localSettleObject.setSign(true);
    localSettleObject.setAmount(100.0D);
    Information localInformation = new Information();
    localInformation.setObject(localSettleObject);
    localInformation.setCommandName("settleCommand");
    localInformation.setReceiveName("settleChangePL");
    Map localMap = this.policySuperior.policyExecuteCommand(localInformation);
    int i = ((Integer)localMap.get("result")).intValue();
    assertEquals(1, i);
    SettleMatch localSettleMatch2 = getSettleMatch(l);
    double d = localSettleMatch2.getSettlePL_B();
    assertEquals(Double.valueOf(201.0D), Double.valueOf(d));
  }
}

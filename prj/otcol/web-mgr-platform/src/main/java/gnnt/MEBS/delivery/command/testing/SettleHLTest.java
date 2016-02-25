package gnnt.MEBS.delivery.command.testing;

import gnnt.MEBS.delivery.command.Information;
import gnnt.MEBS.delivery.command.PolicySuperior;
import gnnt.MEBS.delivery.command.model.SettleObject;
import gnnt.MEBS.delivery.model.settle.SettleMatch;
import gnnt.MEBS.delivery.model.workflow.RegStock;
import gnnt.MEBS.delivery.services.SettleMatchService;
import java.util.Map;

public class SettleHLTest
  extends AbstractTest
{
  public void testSettleHL1()
  {
    String str1 = "liwei";
    String str2 = "fanzh";
    String str3 = "2";
    RegStock localRegStock1 = getRegStock(str2, 0);
    long l = addSettleMatch(10000.0D, 1000.0D, str1, str2, str3, 100.0D, 1, localRegStock1.getRegStockId(), null);
    SettleMatch localSettleMatch = getSettleMatch(l);
    localSettleMatch.setSellIncome(100.0D);
    localSettleMatch.setSellIncome_Ref(50.0D);
    localSettleMatch.setHL_Amount(20.0D);
    this.settleMatchService.updateSettleMatch(localSettleMatch);
    RegStock localRegStock2 = getRegStock("hanwei", 0);
    SettleObject localSettleObject = getSettleObject(localSettleMatch, 20.0D, localRegStock2.getRegStockId());
    Information localInformation = new Information();
    localInformation.setObject(localSettleObject);
    localInformation.setCommandName("settleCommand");
    localInformation.setReceiveName("settleHL");
    Map localMap = this.policySuperior.policyExecuteCommand(localInformation);
    int i = ((Integer)localMap.get("result")).intValue();
    assertEquals(-7, i);
  }
  
  public void testSettleHL2()
  {
    String str1 = "liwei";
    String str2 = "fanzh";
    String str3 = "2";
    RegStock localRegStock1 = getRegStock(str2, 0);
    long l = addSettleMatch(10000.0D, 1000.0D, str1, str2, str3, 100.0D, 1, localRegStock1.getRegStockId(), null);
    SettleMatch localSettleMatch1 = getSettleMatch(l);
    localSettleMatch1.setSellIncome(100.0D);
    localSettleMatch1.setSellIncome_Ref(50.0D);
    localSettleMatch1.setHL_Amount(20.0D);
    this.settleMatchService.updateSettleMatch(localSettleMatch1);
    RegStock localRegStock2 = getRegStock("hanwei", 0);
    SettleObject localSettleObject = getSettleObject(localSettleMatch1, 30.0D, localRegStock2.getRegStockId());
    localSettleObject.setSign(true);
    Information localInformation = new Information();
    localInformation.setObject(localSettleObject);
    localInformation.setCommandName("settleCommand");
    localInformation.setReceiveName("settleHL");
    Map localMap = this.policySuperior.policyExecuteCommand(localInformation);
    SettleMatch localSettleMatch2 = getSettleMatch(l);
    double d = localSettleMatch2.getHL_Amount();
    assertEquals(Double.valueOf(50.0D), Double.valueOf(d));
    int i = localSettleMatch2.getStatus();
    assertEquals(1, i);
    int j = ((Integer)localMap.get("result")).intValue();
    assertEquals(1, j);
  }
  
  public void testSettleHL3()
  {
    String str1 = "liwei";
    String str2 = "fanzh";
    String str3 = "2";
    RegStock localRegStock1 = getRegStock(str2, 0);
    long l = addSettleMatch(10000.0D, 1000.0D, str1, str2, str3, 100.0D, 1, localRegStock1.getRegStockId(), null);
    SettleMatch localSettleMatch1 = getSettleMatch(l);
    localSettleMatch1.setSellIncome(100.0D);
    localSettleMatch1.setSellIncome_Ref(50.0D);
    localSettleMatch1.setHL_Amount(100.0D);
    this.settleMatchService.updateSettleMatch(localSettleMatch1);
    RegStock localRegStock2 = getRegStock("hanwei", 0);
    SettleObject localSettleObject = getSettleObject(localSettleMatch1, 30.0D, localRegStock2.getRegStockId());
    localSettleObject.setSign(false);
    Information localInformation = new Information();
    localInformation.setObject(localSettleObject);
    localInformation.setCommandName("settleCommand");
    localInformation.setReceiveName("settleHL");
    Map localMap = this.policySuperior.policyExecuteCommand(localInformation);
    SettleMatch localSettleMatch2 = getSettleMatch(l);
    double d = localSettleMatch2.getHL_Amount();
    assertEquals(Double.valueOf(70.0D), Double.valueOf(d));
    int i = localSettleMatch2.getStatus();
    assertEquals(1, i);
    int j = ((Integer)localMap.get("result")).intValue();
    assertEquals(1, j);
  }
  
  public SettleObject getSettleObject(SettleMatch paramSettleMatch, double paramDouble, String paramString)
  {
    SettleObject localSettleObject = new SettleObject();
    localSettleObject.setChangeRegStockId(paramString);
    localSettleObject.setAmount(paramDouble);
    localSettleObject.setType(0);
    localSettleObject.setMatchId(paramSettleMatch.getMatchId());
    return localSettleObject;
  }
}

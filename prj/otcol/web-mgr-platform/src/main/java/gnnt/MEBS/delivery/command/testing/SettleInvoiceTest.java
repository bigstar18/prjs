package gnnt.MEBS.delivery.command.testing;

import gnnt.MEBS.delivery.command.Information;
import gnnt.MEBS.delivery.command.PolicySuperior;
import gnnt.MEBS.delivery.command.model.SettleObject;
import gnnt.MEBS.delivery.model.settle.SettleMatch;
import gnnt.MEBS.delivery.model.workflow.RegStock;
import gnnt.MEBS.delivery.services.SettleMatchService;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class SettleInvoiceTest
  extends AbstractTest
{
  @Autowired
  @Qualifier("w_settleMatchService")
  private SettleMatchService settleMatchService;
  
  public void testSettleInvoice1()
  {
    String str1 = "liwei";
    String str2 = "fanzh";
    String str3 = "2";
    RegStock localRegStock = getRegStock(str2, 0);
    long l = addSettleMatch(10000.0D, 1000.0D, str1, str2, str3, 100.0D, 1, localRegStock.getRegStockId(), null);
    SettleMatch localSettleMatch = getSettleMatch(l);
    localSettleMatch.setRecvInvoice(3);
    this.settleMatchService.updateSettleMatch(localSettleMatch);
    SettleObject localSettleObject = new SettleObject();
    localSettleObject.setMatchId(localSettleMatch.getMatchId());
    Information localInformation = new Information();
    localInformation.setObject(localSettleObject);
    localInformation.setCommandName("settleCommand");
    localInformation.setReceiveName("settleInvoice");
    Map localMap = this.policySuperior.policyExecuteCommand(localInformation);
    int i = ((Integer)localMap.get("result")).intValue();
    assertEquals(-10, i);
  }
  
  public void testSettleInvoice2()
  {
    String str1 = "liwei";
    String str2 = "fanzh";
    String str3 = "2";
    RegStock localRegStock = getRegStock(str2, 0);
    long l = addSettleMatch(10000.0D, 1000.0D, str1, str2, str3, 100.0D, 1, localRegStock.getRegStockId(), null);
    SettleMatch localSettleMatch1 = getSettleMatch(l);
    this.settleMatchService.updateSettleMatch(localSettleMatch1);
    SettleObject localSettleObject = new SettleObject();
    localSettleObject.setMatchId(localSettleMatch1.getMatchId());
    Information localInformation = new Information();
    localInformation.setObject(localSettleObject);
    localInformation.setCommandName("settleCommand");
    localInformation.setReceiveName("settleInvoice");
    Map localMap = this.policySuperior.policyExecuteCommand(localInformation);
    int i = ((Integer)localMap.get("result")).intValue();
    assertEquals(1, i);
    SettleMatch localSettleMatch2 = getSettleMatch(l);
    int j = localSettleMatch2.getRecvInvoice();
    assertEquals(1, j);
  }
}

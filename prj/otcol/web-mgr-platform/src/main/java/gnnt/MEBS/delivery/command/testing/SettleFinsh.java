package gnnt.MEBS.delivery.command.testing;

import gnnt.MEBS.base.query.QueryConditions;
import gnnt.MEBS.delivery.command.Information;
import gnnt.MEBS.delivery.command.PolicySuperior;
import gnnt.MEBS.delivery.command.model.SettleObject;
import gnnt.MEBS.delivery.model.settle.SettleMatch;
import gnnt.MEBS.delivery.model.workflow.RegStock;
import gnnt.MEBS.delivery.services.MoneyDoService;
import gnnt.MEBS.delivery.services.RegStockService;
import gnnt.MEBS.delivery.services.SettleMatchService;
import java.util.List;
import java.util.Map;

public class SettleFinsh
  extends AbstractTest
{
  public void testSettleFinish()
  {
    String str1 = "liwei";
    String str2 = "fanzh";
    String str3 = "2";
    RegStock localRegStock = getRegStock(str2, 0);
    long l = addSettleMatch(10000.0D, 1000.0D, str1, str2, str3, 100.0D, 1, localRegStock.getRegStockId(), null);
    SettleMatch localSettleMatch = getSettleMatch(l);
    SettleObject localSettleObject = getSettleObject(localSettleMatch.getMatchId(), null, 0, 0.0D, true);
    Information localInformation = new Information();
    localInformation = new Information();
    localInformation.setObject(localSettleObject);
    localInformation.setCommandName("settleCommand");
    localInformation.setReceiveName("settleFinish");
    Map localMap = this.policySuperior.policyExecuteCommand(localInformation);
    int i = ((Integer)localMap.get("result")).intValue();
    assertEquals(-4, i);
  }
  
  public void testForCompliance()
  {
    String str1 = "liwei";
    String str2 = "fanzh";
    String str3 = "2";
    RegStock localRegStock1 = getRegStock(str2, 0);
    double d1 = localRegStock1.getFrozenWeight();
    double d2 = localRegStock1.getWeight();
    long l = addSettleMatch(10000.0D, 1000.0D, str1, str2, str3, 100.0D, 1, localRegStock1.getRegStockId(), null);
    SettleMatch localSettleMatch = getSettleMatch(l);
    double d3 = this.moneyDoService.getFirmFunds(str1);
    double d4 = this.moneyDoService.getFirmFunds(str2);
    SettleObject localSettleObject = getSettleObject(localSettleMatch.getMatchId(), null, 0, 100000.0D, true);
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
    localSettleObject = getSettleObject(localSettleMatch.getMatchId(), null, 0, 100000.0D, true);
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
    localSettleObject = getSettleObject(localSettleMatch.getMatchId(), null, 0, 0.0D, true);
    localInformation = new Information();
    localInformation.setObject(localSettleObject);
    localInformation.setCommandName("settleCommand");
    localInformation.setReceiveName("settleTransfer");
    localMap = this.policySuperior.policyExecuteCommand(localInformation);
    i = ((Integer)localMap.get("result")).intValue();
    assertEquals(1, i);
    localSettleObject = getSettleObject(localSettleMatch.getMatchId(), null, 0, 0.0D, true);
    localInformation = new Information();
    localInformation.setObject(localSettleObject);
    localInformation.setCommandName("settleCommand");
    localInformation.setReceiveName("settleFinish");
    localMap = this.policySuperior.policyExecuteCommand(localInformation);
    i = ((Integer)localMap.get("result")).intValue();
    assertEquals(1, i);
    double d9 = this.moneyDoService.getFirmFunds(str1);
    double d10 = this.moneyDoService.getFirmFunds(str2);
    assertEquals(Double.valueOf(d7), Double.valueOf(d9 - 10000.0D));
    assertEquals(Double.valueOf(d8), Double.valueOf(d10 - 10000.0D));
    RegStock localRegStock2 = this.regStockService.getRegStockById(localSettleMatch.getRegStockId());
    assertEquals(Double.valueOf(d2), Double.valueOf(localRegStock2.getWeight() + 100.0D));
    assertEquals(Double.valueOf(d1), Double.valueOf(localRegStock2.getFrozenWeight()));
    QueryConditions localQueryConditions = new QueryConditions();
    localQueryConditions.addCondition("oldRegStockId", "=", localRegStock2.getRegStockId());
    List localList = this.regStockService.getRegStocks(localQueryConditions);
    assertEquals(1, localList.size());
    RegStock localRegStock3 = (RegStock)localList.get(0);
    assertEquals(str1, localRegStock3.getFirmId());
    assertEquals(Double.valueOf(100.0D), Double.valueOf(localRegStock3.getWeight()));
    assertEquals(Double.valueOf(0.0D), Double.valueOf(localRegStock3.getFrozenWeight()));
    localSettleMatch = this.settleMatchService.getSettleMatchById(localSettleMatch.getMatchId());
    assertEquals(2, localSettleMatch.getStatus());
  }
  
  public void testForDefault()
  {
    String str1 = "liwei";
    String str2 = "fanzh";
    String str3 = "2";
    RegStock localRegStock = getRegStock(str2, 0);
    double d1 = localRegStock.getFrozenWeight();
    double d2 = localRegStock.getWeight();
    long l = addSettleMatch(10000.0D, 1000.0D, str1, str2, str3, 100.0D, 2, null, null);
    SettleMatch localSettleMatch = getSettleMatch(l);
    double d3 = this.moneyDoService.getFirmFunds(str1);
    double d4 = this.moneyDoService.getFirmFunds(str2);
    SettleObject localSettleObject = getSettleObject(localSettleMatch.getMatchId(), null, 0, 100000.0D, true);
    Information localInformation = new Information();
    localInformation.setObject(localSettleObject);
    localInformation.setCommandName("settleCommand");
    localInformation.setReceiveName("payout");
    Map localMap = this.policySuperior.policyExecuteCommand(localInformation);
    int i = ((Integer)localMap.get("result")).intValue();
    assertEquals(-12, i);
    localSettleObject = getSettleObject(localSettleMatch.getMatchId(), null, 0, 5000.0D, true);
    localInformation = new Information();
    localInformation.setObject(localSettleObject);
    localInformation.setCommandName("settleCommand");
    localInformation.setReceiveName("receivePenalty");
    localMap = this.policySuperior.policyExecuteCommand(localInformation);
    i = ((Integer)localMap.get("result")).intValue();
    assertEquals(1, i);
    double d5 = this.moneyDoService.getFirmFunds(str1);
    double d6 = this.moneyDoService.getFirmFunds(str2);
    assertEquals(Double.valueOf(d3), Double.valueOf(d5 + 5000.0D - 10000.0D));
    assertEquals(Double.valueOf(d4), Double.valueOf(d6));
    localSettleObject = getSettleObject(localSettleMatch.getMatchId(), null, 0, 5000.0D, false);
    localInformation = new Information();
    localInformation.setObject(localSettleObject);
    localInformation.setCommandName("settleCommand");
    localInformation.setReceiveName("receivePenalty");
    localMap = this.policySuperior.policyExecuteCommand(localInformation);
    i = ((Integer)localMap.get("result")).intValue();
    assertEquals(-2, i);
    localSettleObject = getSettleObject(localSettleMatch.getMatchId(), null, 0, 0.0D, true);
    localInformation = new Information();
    localInformation.setObject(localSettleObject);
    localInformation.setCommandName("settleCommand");
    localInformation.setReceiveName("settleFinish");
    localMap = this.policySuperior.policyExecuteCommand(localInformation);
    i = ((Integer)localMap.get("result")).intValue();
    assertEquals(1, i);
    double d7 = this.moneyDoService.getFirmFunds(str1);
    double d8 = this.moneyDoService.getFirmFunds(str2);
    assertEquals(Double.valueOf(d7), Double.valueOf(d5));
    assertEquals(Double.valueOf(d8), Double.valueOf(d6 + 10000.0D));
  }
}

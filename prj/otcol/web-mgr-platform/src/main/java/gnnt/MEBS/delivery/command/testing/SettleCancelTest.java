package gnnt.MEBS.delivery.command.testing;

import gnnt.MEBS.delivery.command.Information;
import gnnt.MEBS.delivery.command.PolicySuperior;
import gnnt.MEBS.delivery.command.model.SettleObject;
import gnnt.MEBS.delivery.model.settle.SettleMatch;
import gnnt.MEBS.delivery.model.workflow.RegStock;
import gnnt.MEBS.delivery.services.MoneyDoService;
import gnnt.MEBS.delivery.services.RegStockService;
import java.util.Map;
import org.springframework.jdbc.core.JdbcTemplate;

public class SettleCancelTest
  extends AbstractTest
{
  public void testSettleCancelForComplianceForBidAndSpot()
  {
    String str1 = "liwei";
    String str2 = "fanzh";
    String str3 = "3";
    RegStock localRegStock1 = getRegStock(str2, 0);
    double d1 = localRegStock1.getFrozenWeight();
    long l = addSettleMatch(10000.0D, 1000.0D, str1, str2, str3, 100.0D, 1, localRegStock1.getRegStockId(), null);
    SettleMatch localSettleMatch = getSettleMatch(l);
    RegStock localRegStock2 = getRegStock(str2, 0);
    SettleObject localSettleObject = getSettleObject(localSettleMatch.getMatchId(), localRegStock2.getRegStockId(), 0, 0.0D, false);
    Information localInformation = new Information();
    localInformation.setObject(localSettleObject);
    localInformation.setCommandName("settleCommand");
    localInformation.setReceiveName("changeRegStock");
    Map localMap = this.policySuperior.policyExecuteCommand(localInformation);
    int i = ((Integer)localMap.get("result")).intValue();
    assertEquals(1, i);
    localRegStock2 = this.regStockService.getRegStockById(localRegStock2.getRegStockId());
    assertEquals(Double.valueOf(localRegStock2.getFrozenWeight()), Double.valueOf(d1 + 100.0D));
    localRegStock1 = this.regStockService.getRegStockById(localRegStock1.getRegStockId());
    assertEquals(Double.valueOf(localRegStock1.getFrozenWeight()), Double.valueOf(d1));
    double d2 = this.moneyDoService.getFirmFunds(str1);
    double d3 = this.moneyDoService.getFirmFunds(str2);
    double d4 = 100.0D;
    localSettleObject = getSettleObject(localSettleMatch.getMatchId(), null, 0, d4, true);
    localInformation = new Information();
    localInformation.setObject(localSettleObject);
    localInformation.setCommandName("settleCommand");
    localInformation.setReceiveName("payout");
    localMap = this.policySuperior.policyExecuteCommand(localInformation);
    i = ((Integer)localMap.get("result")).intValue();
    assertEquals(1, i);
    double d5 = this.moneyDoService.getFirmFunds(str1);
    assertEquals(Double.valueOf(d2), Double.valueOf(d5 + d4));
    localSettleObject = getSettleObject(localSettleMatch.getMatchId(), null, 0, 0.0D, false);
    localInformation = new Information();
    localInformation.setObject(localSettleObject);
    localInformation.setCommandName("settleCommand");
    localInformation.setReceiveName("settleCancel");
    localMap = this.policySuperior.policyExecuteCommand(localInformation);
    i = ((Integer)localMap.get("result")).intValue();
    assertEquals(1, i);
    localSettleMatch = getSettleMatch(l);
    assertEquals(3, localSettleMatch.getStatus());
    double d6 = this.moneyDoService.getFirmFunds(str1);
    assertEquals(Double.valueOf(d6), Double.valueOf(d2));
    localRegStock2 = this.regStockService.getRegStockById(localRegStock2.getRegStockId());
    assertEquals(Double.valueOf(d1), Double.valueOf(localRegStock2.getFrozenWeight()));
  }
  
  public void testSettleCancelForFutures()
  {
    String str1 = "liwei";
    String str2 = "fanzh";
    String str3 = "2";
    RegStock localRegStock = getRegStock(str2, 0);
    double d = localRegStock.getFrozenWeight();
    long l = addSettleMatch(10000.0D, 1000.0D, str1, str2, str3, 100.0D, 1, localRegStock.getRegStockId(), "<?xml version=\"1.0\" encoding=\"GBK\"?><root><MATCHID>ATS10</MATCHID></root>");
    SettleMatch localSettleMatch = getSettleMatch(l);
    SettleObject localSettleObject = getSettleObject(localSettleMatch.getMatchId(), null, 0, 0.0D, false);
    Information localInformation = new Information();
    localInformation.setObject(localSettleObject);
    localInformation.setCommandName("settleCommand");
    localInformation.setReceiveName("settleCancel");
    Map localMap = this.policySuperior.policyExecuteCommand(localInformation);
    int i = ((Integer)localMap.get("result")).intValue();
    assertEquals(-15, i);
  }
  
  public void testSettleCancelForFutures1()
  {
    String str1 = "liwei";
    String str2 = "fanzh";
    String str3 = "2";
    RegStock localRegStock = getRegStock(str2, 0);
    double d = localRegStock.getFrozenWeight();
    long l = addSettleMatch(10000.0D, 1000.0D, str1, str2, str3, 100.0D, 1, localRegStock.getRegStockId(), "<?xml version=\"1.0\" encoding=\"GBK\"?><root><MATCHID>TS_1000</MATCHID></root>");
    SettleMatch localSettleMatch = getSettleMatch(l);
    String str4 = "insert into T_SETTLEMATCH (MATCHID, COMMODITYID, CONTRACTFACTOR, QUANTITY, HL_AMOUNT, STATUS, RESULT, FIRMID_B, BUYPRICE, BUYPAYOUT_REF, BUYPAYOUT, BUYMARGIN, TAKEPENALTY_B, PAYPENALTY_B, SETTLEPL_B, FIRMID_S, SELLPRICE, SELLINCOME_REF, SELLINCOME, SELLMARGIN, TAKEPENALTY_S, PAYPENALTY_S, SETTLEPL_S, CREATETIME, MODIFYTIME, SETTLEDATE, REGSTOCKID, MODIFIER) values ('TS_1000', 'DC100608', 1, 1, 0, 1, 3, 'chenke', 4890, 0, 4890, 978, 0, 0, 0, 'tangzy', 4890, 0, 0, 2445, 0, 0, 0, null, to_date('17-06-2010 11:16:36', 'dd-mm-yyyy hh24:mi:ss'), null, null, 'adminTest')";
    this.jdbcTemplate.update(str4);
    SettleObject localSettleObject = getSettleObject(localSettleMatch.getMatchId(), null, 0, 0.0D, false);
    Information localInformation = new Information();
    localInformation.setObject(localSettleObject);
    localInformation.setCommandName("settleCommand");
    localInformation.setReceiveName("settleCancel");
    Map localMap = this.policySuperior.policyExecuteCommand(localInformation);
    int i = ((Integer)localMap.get("result")).intValue();
    assertEquals(1, i);
    str4 = "select status from T_SETTLEMATCH where MATCHID='TS_1000'";
    int j = this.jdbcTemplate.queryForInt(str4);
    assertEquals(j, -2);
  }
  
  public void testSettleCancelForDefaultForFutures()
  {
    String str1 = "liwei";
    String str2 = "fanzh";
    String str3 = "2";
    RegStock localRegStock = getRegStock(str2, 0);
    double d1 = localRegStock.getFrozenWeight();
    long l = addSettleMatch(10000.0D, 1000.0D, str1, str2, str3, 100.0D, 4, null, "<?xml version=\"1.0\" encoding=\"GBK\"?><root><MATCHID>TS_1000</MATCHID></root>");
    SettleMatch localSettleMatch = getSettleMatch(l);
    String str4 = "insert into T_SETTLEMATCH (MATCHID, COMMODITYID, CONTRACTFACTOR, QUANTITY, HL_AMOUNT, STATUS, RESULT, FIRMID_B, BUYPRICE, BUYPAYOUT_REF, BUYPAYOUT, BUYMARGIN, TAKEPENALTY_B, PAYPENALTY_B, SETTLEPL_B, FIRMID_S, SELLPRICE, SELLINCOME_REF, SELLINCOME, SELLMARGIN, TAKEPENALTY_S, PAYPENALTY_S, SETTLEPL_S, CREATETIME, MODIFYTIME, SETTLEDATE, REGSTOCKID, MODIFIER) values ('TS_1000', 'DC100608', 1, 1, 0, 1, 3, 'chenke', 4890, 0, 4890, 978, 0, 0, 0, 'tangzy', 4890, 0, 0, 2445, 0, 0, 0, null, to_date('17-06-2010 11:16:36', 'dd-mm-yyyy hh24:mi:ss'), null, null, 'adminTest')";
    this.jdbcTemplate.update(str4);
    double d2 = this.moneyDoService.getFirmFunds(str1);
    double d3 = this.moneyDoService.getFirmFunds(str2);
    SettleObject localSettleObject = getSettleObject(localSettleMatch.getMatchId(), null, 0, 500.0D, true);
    Information localInformation = new Information();
    localInformation.setObject(localSettleObject);
    localInformation.setCommandName("settleCommand");
    localInformation.setReceiveName("receivePenalty");
    Map localMap = this.policySuperior.policyExecuteCommand(localInformation);
    int i = ((Integer)localMap.get("result")).intValue();
    assertEquals(1, i);
    double d4 = this.moneyDoService.getFirmFunds(str1);
    double d5 = this.moneyDoService.getFirmFunds(str2);
    assertEquals(Double.valueOf(d2), Double.valueOf(d4 + 500.0D - 10000.0D));
    assertEquals(Double.valueOf(d3), Double.valueOf(d5));
    localSettleObject = getSettleObject(localSettleMatch.getMatchId(), null, 0, 0.0D, false);
    localInformation = new Information();
    localInformation.setObject(localSettleObject);
    localInformation.setCommandName("settleCommand");
    localInformation.setReceiveName("settleCancel");
    localMap = this.policySuperior.policyExecuteCommand(localInformation);
    i = ((Integer)localMap.get("result")).intValue();
    assertEquals(1, i);
    str4 = "select status from T_SETTLEMATCH where MATCHID='TS_1000'";
    int j = this.jdbcTemplate.queryForInt(str4);
    assertEquals(j, -2);
    double d6 = this.moneyDoService.getFirmFunds(str1);
    double d7 = this.moneyDoService.getFirmFunds(str2);
    assertEquals(Double.valueOf(d2), Double.valueOf(d6));
    assertEquals(Double.valueOf(d3), Double.valueOf(d7));
  }
}

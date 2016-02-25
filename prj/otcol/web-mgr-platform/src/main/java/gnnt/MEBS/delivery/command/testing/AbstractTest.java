package gnnt.MEBS.delivery.command.testing;

import gnnt.MEBS.delivery.command.PolicySuperior;
import gnnt.MEBS.delivery.command.model.SettleObject;
import gnnt.MEBS.delivery.model.settle.SettleMatch;
import gnnt.MEBS.delivery.model.workflow.RegStock;
import gnnt.MEBS.delivery.services.MoneyDoService;
import gnnt.MEBS.delivery.services.RegStockApplyService;
import gnnt.MEBS.delivery.services.RegStockService;
import gnnt.MEBS.delivery.services.SettleMatchService;
import java.io.File;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.AbstractTransactionalDataSourceSpringContextTests;

public abstract class AbstractTest
  extends AbstractTransactionalDataSourceSpringContextTests
{
  @Autowired
  @Qualifier("w_policySuperior")
  protected PolicySuperior policySuperior;
  @Autowired
  @Qualifier("w_settleMatchService")
  protected SettleMatchService settleMatchService;
  @Autowired
  @Qualifier("w_regStockApplyService")
  protected RegStockApplyService regStockApplyService;
  @Autowired
  @Qualifier("w_regStockService")
  protected RegStockService regStockService;
  @Autowired
  @Qualifier("w_moneyDoService")
  protected MoneyDoService moneyDoService;
  
  protected String[] getConfigLocations()
  {
    File localFile = new File(".");
    String str = "classpath:wareHouseBeansTest.xml";
    setAutowireMode(1);
    return new String[] { str };
  }
  
  protected SettleMatch getSettleMatch(long paramLong)
  {
    String str = "S" + paramLong;
    SettleMatch localSettleMatch = this.settleMatchService.getSettleMatchById(str);
    return localSettleMatch;
  }
  
  protected long addSettleMatch(double paramDouble1, double paramDouble2, String paramString1, String paramString2, String paramString3, double paramDouble3, int paramInt, String paramString4, String paramString5)
  {
    SettleMatch localSettleMatch = new SettleMatch();
    localSettleMatch.setBreedId(1L);
    localSettleMatch.setBuyMargin(paramDouble1);
    localSettleMatch.setBuyPrice(paramDouble2);
    localSettleMatch.setSellPrice(paramDouble2);
    localSettleMatch.setSellMargin(paramDouble1);
    localSettleMatch.setCommodityId("1");
    localSettleMatch.setContractId(1L);
    localSettleMatch.setFirmID_B(paramString1);
    localSettleMatch.setFirmID_S(paramString2);
    localSettleMatch.setModuleId(paramString3);
    localSettleMatch.setRegStockId(paramString4);
    localSettleMatch.setWeight(paramDouble3);
    localSettleMatch.setResult(paramInt);
    localSettleMatch.setXml(paramString5);
    long l = this.settleMatchService.createSettleMatch(localSettleMatch, "test");
    return l;
  }
  
  protected RegStock getRegStock(String paramString, int paramInt)
  {
    RegStock localRegStock = new RegStock();
    localRegStock.setBreedId(1L);
    localRegStock.setCreateTime(new Date());
    localRegStock.setFirmId(paramString);
    localRegStock.setFrozenWeight(100.0D);
    localRegStock.setInitWeight(1000.0D);
    localRegStock.setModifyTime(new Date());
    localRegStock.setStockId("10");
    localRegStock.setType(paramInt);
    localRegStock.setUnitWeight(10.0D);
    localRegStock.setWarehouseId("BJCK");
    localRegStock.setWeight(1000.0D);
    this.regStockService.addRegStock(localRegStock);
    return localRegStock;
  }
  
  public SettleObject getSettleObject(String paramString1, String paramString2, int paramInt, double paramDouble, boolean paramBoolean)
  {
    SettleObject localSettleObject = new SettleObject();
    localSettleObject.setChangeRegStockId(paramString2);
    localSettleObject.setType(paramInt);
    localSettleObject.setAmount(paramDouble);
    localSettleObject.setMatchId(paramString1);
    localSettleObject.setSign(paramBoolean);
    return localSettleObject;
  }
}

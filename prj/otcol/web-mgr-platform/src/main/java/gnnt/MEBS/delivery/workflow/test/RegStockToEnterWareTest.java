package gnnt.MEBS.delivery.workflow.test;

import gnnt.MEBS.delivery.model.workflow.RegStock;
import gnnt.MEBS.delivery.model.workflow.RegStockToEnterWare;
import gnnt.MEBS.delivery.model.workflow.WorkFlowClone;
import gnnt.MEBS.delivery.services.RegStockApplyService;
import gnnt.MEBS.delivery.services.RegStockService;
import gnnt.MEBS.delivery.services.RegStockToEnterWareService;
import gnnt.MEBS.delivery.workflow.OriginalModel;
import gnnt.MEBS.delivery.workflow.WorkflowFacade;
import java.io.PrintStream;
import java.util.Date;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;

public class RegStockToEnterWareTest
  extends AbstractTest
{
  @Autowired
  @Qualifier("w_regStockService")
  private RegStockService regStockService;
  @Autowired
  @Qualifier("w_regStockApplyService")
  private RegStockApplyService regStockApplyService;
  @Autowired
  @Qualifier("w_regStockToEnterWareService")
  private RegStockToEnterWareService regStockToEnterWareService;
  
  public void testAdd()
  {
    RegStock localRegStock = new RegStock();
    localRegStock.setFirmId("hanwei");
    localRegStock.setBreedId(1L);
    localRegStock.setWeight(10.0D);
    localRegStock.setWarehouseId("1421");
    localRegStock.setType(2);
    this.regStockService.addRegStock(localRegStock);
    RegStockToEnterWare localRegStockToEnterWare = new RegStockToEnterWare();
    localRegStockToEnterWare.setFirmId("hanwei");
    localRegStockToEnterWare.setBreedId("1");
    localRegStockToEnterWare.setRegStockId(localRegStock.getRegStockId());
    localRegStockToEnterWare.setRelTurnToWeight(1.0D);
    localRegStockToEnterWare.setWarehouseId("1421");
    localRegStockToEnterWare.setCreateDate(new Date());
    this.regStockToEnterWareService.addRegStockToEnterWare(localRegStockToEnterWare);
    OriginalModel localOriginalModel = new OriginalModel();
    localOriginalModel.setObject(localRegStockToEnterWare);
    localOriginalModel.setHoldAuthority(9);
    localOriginalModel.setOperate("add");
    localRegStockToEnterWare.setRelTurnToWeight(2.0D);
    this.regStockToEnterWareService.updateRegStockToEnterWare(localRegStockToEnterWare);
    localOriginalModel.setObject(localRegStockToEnterWare);
    Map localMap = this.workflow.dealWorkflow(localOriginalModel, "regStockToEnterWare");
    int i = ((Integer)localMap.get("result")).intValue();
    assertEquals(-11, i);
  }
  
  public void testMarketAffirm()
  {
    RegStock localRegStock = this.regStockService.getRegStockById("5");
    double d1 = localRegStock.getWeight();
    OriginalModel localOriginalModel = getOriginalModel(1, 0, "市场审核", "approve");
    Map localMap = this.workflow.dealWorkflow(localOriginalModel, "regStockToEnterWare");
    int i = ((Integer)localMap.get("result")).intValue();
    assertEquals(1, i);
    localRegStock = this.regStockService.getRegStockById("5");
    double d2 = localRegStock.getWeight();
    assertEquals(Double.valueOf(d1 - 1.0D), Double.valueOf(d2));
    String str = "select Status from s_regstockrollback t where t.Id=" + localOriginalModel.getObject().getBillid();
    System.out.println("sql:" + str);
    int j = this.jdbcTemplate.queryForInt(str);
    assertEquals(2, j);
  }
  
  public void testMarketAffirm2()
  {
    RegStock localRegStock = this.regStockService.getRegStockById("2");
    double d1 = localRegStock.getWeight();
    OriginalModel localOriginalModel = getOriginalModel2(1, 0, "市场审核", "approve");
    Map localMap = this.workflow.dealWorkflow(localOriginalModel, "regStockToEnterWare");
    int i = ((Integer)localMap.get("result")).intValue();
    assertEquals(1, i);
    localRegStock = this.regStockService.getRegStockById("2");
    double d2 = localRegStock.getWeight();
    assertEquals(Double.valueOf(d1 - 1.0D), Double.valueOf(d2));
    String str = "select Status from s_regstockrollback t where t.Id=" + localOriginalModel.getObject().getBillid();
    System.out.println("sql:" + str);
    int j = this.jdbcTemplate.queryForInt(str);
    assertEquals(2, j);
  }
  
  public void testMarketRevocation()
  {
    RegStock localRegStock = this.regStockService.getRegStockById("5");
    double d = localRegStock.getFrozenWeight();
    OriginalModel localOriginalModel = getOriginalModel(1, 0, "市场驳回", "overrule");
    Map localMap = this.workflow.dealWorkflow(localOriginalModel, "regStockToEnterWare");
    int i = ((Integer)localMap.get("result")).intValue();
    assertEquals(1, i);
    String str = "select Status from s_regstockrollback t where t.Id=" + localOriginalModel.getObject().getBillid();
    int j = this.jdbcTemplate.queryForInt(str);
    localRegStock = this.regStockService.getRegStockById("5");
    assertEquals(-2, j);
  }
  
  public void testMarketRevocation2()
  {
    RegStock localRegStock = this.regStockService.getRegStockById("2");
    double d = localRegStock.getFrozenWeight();
    OriginalModel localOriginalModel = getOriginalModel2(1, 0, "市场驳回", "overrule");
    Map localMap = this.workflow.dealWorkflow(localOriginalModel, "regStockToEnterWare");
    int i = ((Integer)localMap.get("result")).intValue();
    assertEquals(1, i);
    String str = "select Status from s_regstockrollback t where t.Id=" + localOriginalModel.getObject().getBillid();
    int j = this.jdbcTemplate.queryForInt(str);
    localRegStock = this.regStockService.getRegStockById("2");
    assertEquals(-2, j);
  }
  
  public OriginalModel getOriginalModel(int paramInt1, int paramInt2, String paramString1, String paramString2)
  {
    RegStockToEnterWare localRegStockToEnterWare = new RegStockToEnterWare();
    localRegStockToEnterWare.setFirmId("hanwei");
    localRegStockToEnterWare.setBreedId("1");
    localRegStockToEnterWare.setRegStockId("5");
    localRegStockToEnterWare.setRelTurnToWeight(1.0D);
    localRegStockToEnterWare.setWarehouseId("1421");
    localRegStockToEnterWare.setCreateDate(new Date());
    localRegStockToEnterWare.setStatus(paramInt1);
    this.regStockToEnterWareService.addRegStockToEnterWare(localRegStockToEnterWare);
    OriginalModel localOriginalModel = new OriginalModel();
    localOriginalModel.setObject(localRegStockToEnterWare);
    localOriginalModel.setHoldAuthority(paramInt2);
    localOriginalModel.setOperate(paramString2);
    return localOriginalModel;
  }
  
  public OriginalModel getOriginalModel2(int paramInt1, int paramInt2, String paramString1, String paramString2)
  {
    RegStockToEnterWare localRegStockToEnterWare = new RegStockToEnterWare();
    localRegStockToEnterWare.setFirmId("hanwei");
    localRegStockToEnterWare.setBreedId("1");
    localRegStockToEnterWare.setRegStockId("2");
    localRegStockToEnterWare.setRelTurnToWeight(1.0D);
    localRegStockToEnterWare.setWarehouseId("1421");
    localRegStockToEnterWare.setCreateDate(new Date());
    localRegStockToEnterWare.setStatus(paramInt1);
    this.regStockToEnterWareService.addRegStockToEnterWare(localRegStockToEnterWare);
    OriginalModel localOriginalModel = new OriginalModel();
    localOriginalModel.setObject(localRegStockToEnterWare);
    localOriginalModel.setHoldAuthority(paramInt2);
    localOriginalModel.setOperate(paramString2);
    return localOriginalModel;
  }
}

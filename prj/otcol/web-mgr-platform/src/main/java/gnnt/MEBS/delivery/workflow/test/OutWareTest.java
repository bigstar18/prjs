package gnnt.MEBS.delivery.workflow.test;

import gnnt.MEBS.delivery.model.workflow.EnterWare;
import gnnt.MEBS.delivery.model.workflow.OutWare;
import gnnt.MEBS.delivery.model.workflow.WorkFlowClone;
import gnnt.MEBS.delivery.services.EnterWareService;
import gnnt.MEBS.delivery.services.OutWareService;
import gnnt.MEBS.delivery.workflow.OriginalModel;
import gnnt.MEBS.delivery.workflow.WorkflowFacade;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;

public class OutWareTest
  extends AbstractTest
{
  @Autowired
  @Qualifier("w_outWareService")
  private OutWareService outWareService;
  @Autowired
  @Qualifier("w_enterWareService")
  private EnterWareService enterWareService;
  
  public void testAdd1()
  {
    EnterWare localEnterWare = this.enterWareService.getEnterWareById("31");
    double d1 = localEnterWare.getFrozenAmount();
    OutWare localOutWare = new OutWare();
    localOutWare.setFirmId("zhulh");
    localOutWare.setEnterWareId("31");
    localOutWare.setWarehouseId("1234");
    localOutWare.setWeight(1.0D);
    OriginalModel localOriginalModel = new OriginalModel();
    localOriginalModel.setObject(localOutWare);
    localOriginalModel.setHoldAuthority(9);
    localOriginalModel.setOperate("add");
    Map localMap = this.workflow.dealWorkflow(localOriginalModel, "outWare");
    int i = ((Integer)localMap.get("result")).intValue();
    assertEquals(1, i);
    localEnterWare = this.enterWareService.getEnterWareById("31");
    double d2 = localEnterWare.getFrozenAmount();
    double d3 = localOutWare.getWeight();
    assertEquals(Double.valueOf(d1 + d3), Double.valueOf(d2));
  }
  
  public void testAdd2()
  {
    OutWare localOutWare = new OutWare();
    localOutWare.setFirmId("zhulh");
    localOutWare.setEnterWareId("31");
    localOutWare.setWarehouseId("1234");
    localOutWare.setWeight(11.0D);
    OriginalModel localOriginalModel = new OriginalModel();
    localOriginalModel.setObject(localOutWare);
    localOriginalModel.setHoldAuthority(9);
    localOriginalModel.setOperate("add");
    localOutWare.setWeight(11222.0D);
    this.outWareService.updateOutEnter(localOutWare);
    localOriginalModel.setObject(localOutWare);
    Map localMap = this.workflow.dealWorkflow(localOriginalModel, "outWare");
    int i = ((Integer)localMap.get("result")).intValue();
    assertEquals(-8, i);
  }
  
  public void testAdd3()
  {
    OutWare localOutWare = new OutWare();
    localOutWare.setFirmId("zhulh");
    localOutWare.setEnterWareId("31");
    localOutWare.setWarehouseId("1234");
    localOutWare.setWeight(11.0D);
    OriginalModel localOriginalModel = new OriginalModel();
    localOriginalModel.setObject(localOutWare);
    localOriginalModel.setHoldAuthority(9);
    localOriginalModel.setOperate("add");
    localOutWare.setWeight(11.0D);
    this.outWareService.updateOutEnter(localOutWare);
    localOriginalModel.setObject(localOutWare);
    localOutWare.setAbility(-1);
    Map localMap = this.workflow.dealWorkflow(localOriginalModel, "outWare");
    int i = ((Integer)localMap.get("result")).intValue();
    assertEquals(-1, i);
    localOutWare.setAbility(0);
    localOriginalModel.setHoldAuthority(3);
    localMap = this.workflow.dealWorkflow(localOriginalModel, "outWare");
    i = ((Integer)localMap.get("result")).intValue();
    assertEquals(-2, i);
    localOriginalModel.setObject(null);
    localMap = this.workflow.dealWorkflow(localOriginalModel, "outWare");
    i = ((Integer)localMap.get("result")).intValue();
    assertEquals(-100, i);
  }
  
  public void testMarketAffirm()
  {
    OriginalModel localOriginalModel = getOriginalModel(1, 0, "市场审核", "approve");
    Map localMap = this.workflow.dealWorkflow(localOriginalModel, "outWare");
    int i = ((Integer)localMap.get("result")).intValue();
    assertEquals(1, i);
    String str = "select ability from w_out_ware t where t.Id=" + localOriginalModel.getObject().getBillid();
    int j = this.jdbcTemplate.queryForInt(str);
    assertEquals(2, j);
  }
  
  public void testMarketRevocation()
  {
    EnterWare localEnterWare = this.enterWareService.getEnterWareById("31");
    double d1 = localEnterWare.getFrozenAmount();
    OriginalModel localOriginalModel = getOriginalModel(1, 0, "市场驳回", "overrule");
    Map localMap = this.workflow.dealWorkflow(localOriginalModel, "outWare");
    int i = ((Integer)localMap.get("result")).intValue();
    assertEquals(1, i);
    String str = "select ability from w_out_ware t where t.Id=" + localOriginalModel.getObject().getBillid();
    int j = this.jdbcTemplate.queryForInt(str);
    assertEquals(-2, j);
    localEnterWare = this.enterWareService.getEnterWareById("31");
    double d2 = localEnterWare.getFrozenAmount();
    assertEquals(Double.valueOf(d1 - 1.0D), Double.valueOf(d2));
  }
  
  public void testFirmRevocation()
  {
    EnterWare localEnterWare = this.enterWareService.getEnterWareById("31");
    double d1 = localEnterWare.getFrozenAmount();
    OriginalModel localOriginalModel = getOriginalModel(1, 9, "交易商撤消", "cancel");
    Map localMap = this.workflow.dealWorkflow(localOriginalModel, "outWare");
    int i = ((Integer)localMap.get("result")).intValue();
    assertEquals(1, i);
    String str = "select ability from w_out_ware t where t.Id=" + localOriginalModel.getObject().getBillid();
    int j = this.jdbcTemplate.queryForInt(str);
    assertEquals(-1, j);
    localEnterWare = this.enterWareService.getEnterWareById("31");
    double d2 = localEnterWare.getFrozenAmount();
    assertEquals(Double.valueOf(d1 - 1.0D), Double.valueOf(d2));
  }
  
  public void testWarehouseVerify()
  {
    EnterWare localEnterWare = this.enterWareService.getEnterWareById("31");
    double d1 = localEnterWare.getFrozenAmount();
    OriginalModel localOriginalModel = getOriginalModel(2, 1, "仓库驳回", "overrule");
    Map localMap = this.workflow.dealWorkflow(localOriginalModel, "outWare");
    int i = ((Integer)localMap.get("result")).intValue();
    assertEquals(1, i);
    String str = "select ability from w_out_ware t where t.Id=" + localOriginalModel.getObject().getBillid();
    int j = this.jdbcTemplate.queryForInt(str);
    assertEquals(-3, j);
    localEnterWare = this.enterWareService.getEnterWareById("31");
    double d2 = localEnterWare.getFrozenAmount();
    assertEquals(Double.valueOf(d1 - 1.0D), Double.valueOf(d2));
  }
  
  public void testWarehouseRevocation()
  {
    EnterWare localEnterWare = this.enterWareService.getEnterWareById("31");
    double d1 = localEnterWare.getFrozenAmount();
    double d2 = localEnterWare.getExistAmount();
    OriginalModel localOriginalModel = getOriginalModel(2, 1, "仓库审核", "approve");
    Map localMap = this.workflow.dealWorkflow(localOriginalModel, "outWare");
    int i = ((Integer)localMap.get("result")).intValue();
    assertEquals(1, i);
    String str = "select ability from w_out_ware t where t.Id=" + localOriginalModel.getObject().getBillid();
    int j = this.jdbcTemplate.queryForInt(str);
    assertEquals(3, j);
    localEnterWare = this.enterWareService.getEnterWareById("31");
    double d3 = localEnterWare.getFrozenAmount();
    double d4 = localEnterWare.getExistAmount();
    assertEquals(Double.valueOf(d1 - 1.0D), Double.valueOf(d3));
    assertEquals(Double.valueOf(d2 - 1.0D), Double.valueOf(d4));
  }
  
  public OriginalModel getOriginalModel(int paramInt1, int paramInt2, String paramString1, String paramString2)
  {
    OutWare localOutWare = new OutWare();
    localOutWare.setFirmId("zhulh");
    localOutWare.setEnterWareId("31");
    localOutWare.setWarehouseId("1234");
    localOutWare.setWeight(1.0D);
    localOutWare.setAbility(paramInt1);
    this.outWareService.addOutEnter(localOutWare);
    OriginalModel localOriginalModel = new OriginalModel();
    localOriginalModel.setObject(localOutWare);
    localOriginalModel.setHoldAuthority(paramInt2);
    localOriginalModel.setOperate(paramString2);
    return localOriginalModel;
  }
}

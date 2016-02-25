package gnnt.MEBS.delivery.workflow.test;

import gnnt.MEBS.delivery.model.workflow.EnterWare;
import gnnt.MEBS.delivery.model.workflow.RegStockApply;
import gnnt.MEBS.delivery.model.workflow.WorkFlowClone;
import gnnt.MEBS.delivery.services.EnterWareService;
import gnnt.MEBS.delivery.services.RegStockApplyService;
import gnnt.MEBS.delivery.workflow.OriginalModel;
import gnnt.MEBS.delivery.workflow.WorkflowFacade;
import java.io.PrintStream;
import java.util.Date;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;

public class RegStockApplyTest
  extends AbstractTest
{
  @Autowired
  @Qualifier("w_regStockApplyService")
  private RegStockApplyService regStockApplyService;
  @Autowired
  @Qualifier("w_enterWareService")
  private EnterWareService enterWareService;
  
  public void testAdd1()
  {
    EnterWare localEnterWare = this.enterWareService.getEnterWareById("31");
    double d1 = localEnterWare.getFrozenAmount();
    RegStockApply localRegStockApply = new RegStockApply();
    localRegStockApply.setBreedId(1L);
    localRegStockApply.setFirmId("1");
    localRegStockApply.setType(0);
    localRegStockApply.setWeight(1.0D);
    localRegStockApply.setUnitWeight(1.0D);
    localRegStockApply.setStatus(0);
    localRegStockApply.setApplicant("fanzh");
    localRegStockApply.setApplyTime(new Date());
    localRegStockApply.setStockId("31");
    OriginalModel localOriginalModel = new OriginalModel();
    localOriginalModel.setObject(localRegStockApply);
    localOriginalModel.setHoldAuthority(0);
    localOriginalModel.setOperate("add");
    Map localMap = this.workflow.dealWorkflow(localOriginalModel, "regStockApply");
    int i = ((Integer)localMap.get("result")).intValue();
    assertEquals(1, i);
    localEnterWare = this.enterWareService.getEnterWareById("31");
    double d2 = localEnterWare.getFrozenAmount();
    double d3 = localRegStockApply.getWeight();
    assertEquals(Double.valueOf(d1 + d3), Double.valueOf(d2));
  }
  
  public void testAdd2()
  {
    RegStockApply localRegStockApply = new RegStockApply();
    localRegStockApply.setBreedId(1L);
    localRegStockApply.setFirmId("1");
    localRegStockApply.setType(0);
    localRegStockApply.setWeight(2222222.0D);
    localRegStockApply.setUnitWeight(2222222.0D);
    localRegStockApply.setStatus(0);
    localRegStockApply.setApplicant("11");
    localRegStockApply.setStockId("37");
    localRegStockApply.setApplyTime(new Date());
    OriginalModel localOriginalModel = new OriginalModel();
    localOriginalModel.setObject(localRegStockApply);
    localOriginalModel.setHoldAuthority(0);
    localOriginalModel.setOperate("add");
    Map localMap = this.workflow.dealWorkflow(localOriginalModel, "regStockApply");
    int i = ((Integer)localMap.get("result")).intValue();
    assertEquals(-12, i);
  }
  
  public void testAdd3()
  {
    RegStockApply localRegStockApply = new RegStockApply();
    localRegStockApply.setBreedId(1L);
    localRegStockApply.setFirmId("1");
    localRegStockApply.setType(0);
    localRegStockApply.setWeight(2222222.0D);
    localRegStockApply.setUnitWeight(2222222.0D);
    localRegStockApply.setStatus(0);
    localRegStockApply.setApplicant("11");
    localRegStockApply.setStockId("37");
    localRegStockApply.setApplyTime(new Date());
    OriginalModel localOriginalModel = new OriginalModel();
    localOriginalModel.setObject(localRegStockApply);
    localOriginalModel.setHoldAuthority(0);
    localOriginalModel.setOperate("add");
    localRegStockApply.setStatus(-1);
    Map localMap = this.workflow.dealWorkflow(localOriginalModel, "regStockApply");
    int i = ((Integer)localMap.get("result")).intValue();
    assertEquals(-1, i);
    localRegStockApply.setStatus(0);
    localOriginalModel.setHoldAuthority(3);
    localMap = this.workflow.dealWorkflow(localOriginalModel, "regStockApply");
    i = ((Integer)localMap.get("result")).intValue();
    assertEquals(-2, i);
    localOriginalModel.setObject(null);
    localMap = this.workflow.dealWorkflow(localOriginalModel, "regStockApply");
    i = ((Integer)localMap.get("result")).intValue();
    assertEquals(-100, i);
  }
  
  public void testMarketAffirm()
  {
    EnterWare localEnterWare = this.enterWareService.getEnterWareById("31");
    double d1 = localEnterWare.getFrozenAmount();
    double d2 = localEnterWare.getExistAmount();
    OriginalModel localOriginalModel = getOriginalModel(1, 0, "市场审核", "approve");
    Map localMap = this.workflow.dealWorkflow(localOriginalModel, "regStockApply");
    int i = ((Integer)localMap.get("result")).intValue();
    assertEquals(1, i);
    localEnterWare = this.enterWareService.getEnterWareById("31");
    double d3 = localEnterWare.getFrozenAmount();
    double d4 = localEnterWare.getExistAmount();
    assertEquals(Double.valueOf(d1), Double.valueOf(d3));
    assertEquals(Double.valueOf(d2), Double.valueOf(d4));
    String str = "select Status from S_RegStockApply t where t.applyId=" + localOriginalModel.getObject().getBillid();
    System.out.println("sql:" + str);
    int j = this.jdbcTemplate.queryForInt(str);
    assertEquals(2, j);
  }
  
  public void testMarketRevocation()
  {
    EnterWare localEnterWare = this.enterWareService.getEnterWareById("31");
    double d1 = localEnterWare.getFrozenAmount();
    OriginalModel localOriginalModel = getOriginalModel(1, 0, "市场驳回", "overrule");
    Map localMap = this.workflow.dealWorkflow(localOriginalModel, "regStockApply");
    int i = ((Integer)localMap.get("result")).intValue();
    assertEquals(1, i);
    String str = "select Status from S_RegStockApply t where t.applyId=" + localOriginalModel.getObject().getBillid();
    int j = this.jdbcTemplate.queryForInt(str);
    localEnterWare = this.enterWareService.getEnterWareById("31");
    double d2 = localEnterWare.getFrozenAmount();
    assertEquals(Double.valueOf(d1 - 1.0D), Double.valueOf(d2));
    assertEquals(-2, j);
  }
  
  public OriginalModel getOriginalModel(int paramInt1, int paramInt2, String paramString1, String paramString2)
  {
    RegStockApply localRegStockApply = new RegStockApply();
    localRegStockApply.setBreedId(1L);
    localRegStockApply.setFirmId("1");
    localRegStockApply.setType(0);
    localRegStockApply.setWeight(1.0D);
    localRegStockApply.setUnitWeight(1.0D);
    localRegStockApply.setStatus(paramInt1);
    localRegStockApply.setApplicant("fanzh");
    localRegStockApply.setApplyTime(new Date());
    localRegStockApply.setStockId("31");
    this.regStockApplyService.addRegStockApply(localRegStockApply);
    OriginalModel localOriginalModel = new OriginalModel();
    localOriginalModel.setObject(localRegStockApply);
    localOriginalModel.setHoldAuthority(paramInt2);
    localOriginalModel.setOperate(paramString2);
    return localOriginalModel;
  }
}

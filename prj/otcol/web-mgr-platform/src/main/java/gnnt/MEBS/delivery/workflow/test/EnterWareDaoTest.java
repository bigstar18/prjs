package gnnt.MEBS.delivery.workflow.test;

import gnnt.MEBS.delivery.model.workflow.EnterWare;
import gnnt.MEBS.delivery.model.workflow.WorkFlowClone;
import gnnt.MEBS.delivery.services.EnterApplyService;
import gnnt.MEBS.delivery.services.EnterWareService;
import gnnt.MEBS.delivery.workflow.OriginalModel;
import gnnt.MEBS.delivery.workflow.WorkflowFacade;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;

public class EnterWareDaoTest
  extends AbstractTest
{
  @Autowired
  @Qualifier("w_enterApplyService")
  private EnterApplyService enterApplyService;
  @Autowired
  @Qualifier("w_enterWareService")
  private EnterWareService enterWareService;
  
  public void testAdd1()
  {
    EnterWare localEnterWare = new EnterWare();
    localEnterWare.setFirmId("heihei");
    localEnterWare.setWarehouseId("111111");
    localEnterWare.setEnterInformId("37");
    localEnterWare.setAbility(0);
    localEnterWare.setWeight(1111.0D);
    localEnterWare.setUnitWeight(1.0D);
    this.enterWareService.addEnterWare(localEnterWare);
    OriginalModel localOriginalModel = new OriginalModel();
    localOriginalModel.setObject(localEnterWare);
    localOriginalModel.setHoldAuthority(0);
    localOriginalModel.setOperate("add");
    Map localMap = this.workflow.dealWorkflow(localOriginalModel, "enterWare");
    int i = ((Integer)localMap.get("result")).intValue();
    assertEquals(-8, i);
  }
  
  public void testAdd2()
  {
    EnterWare localEnterWare = new EnterWare();
    localEnterWare.setId("1");
    localEnterWare.setFirmId("heihei");
    localEnterWare.setWarehouseId("111111");
    localEnterWare.setEnterInformId("12");
    localEnterWare.setAbility(0);
    localEnterWare.setWeight(1111.0D);
    localEnterWare.setUnitWeight(1.0D);
    this.enterWareService.addEnterWare(localEnterWare);
    OriginalModel localOriginalModel = new OriginalModel();
    localOriginalModel.setObject(localEnterWare);
    localOriginalModel.setHoldAuthority(1);
    localOriginalModel.setOperate("add");
    localEnterWare.setAbility(-1);
    Map localMap = this.workflow.dealWorkflow(localOriginalModel, "enterWare");
    int i = ((Integer)localMap.get("result")).intValue();
    assertEquals(-1, i);
    localEnterWare.setAbility(1);
    localOriginalModel.setHoldAuthority(3);
    localMap = this.workflow.dealWorkflow(localOriginalModel, "enterWare");
    i = ((Integer)localMap.get("result")).intValue();
    assertEquals(-2, i);
    localOriginalModel.setObject(null);
    localMap = this.workflow.dealWorkflow(localOriginalModel, "enterWare");
    i = ((Integer)localMap.get("result")).intValue();
    assertEquals(-100, i);
  }
  
  public void testFirmRevocation()
  {
    OriginalModel localOriginalModel = getOriginalModel(1, 9, "交易商撤消", "cancel");
    Map localMap = this.workflow.dealWorkflow(localOriginalModel, "enterWare");
    int i = ((Integer)localMap.get("result")).intValue();
    assertEquals(1, i);
    String str = "select ability from w_enter_ware t where t.Id=" + localOriginalModel.getObject().getBillid();
    int j = this.jdbcTemplate.queryForInt(str);
    assertEquals(-1, j);
  }
  
  public void testWarehouseRevocation()
  {
    OriginalModel localOriginalModel = getOriginalModel(1, 1, "仓库审核", "approve");
    Map localMap = this.workflow.dealWorkflow(localOriginalModel, "enterWare");
    int i = ((Integer)localMap.get("result")).intValue();
    assertEquals(1, i);
    String str = "select ability from w_enter_ware t where t.Id=" + localOriginalModel.getObject().getBillid();
    int j = this.jdbcTemplate.queryForInt(str);
    assertEquals(2, j);
  }
  
  public void testWarehouseVerify()
  {
    OriginalModel localOriginalModel = getOriginalModel(1, 1, "仓库驳回", "overrule");
    Map localMap = this.workflow.dealWorkflow(localOriginalModel, "enterWare");
    int i = ((Integer)localMap.get("result")).intValue();
    assertEquals(1, i);
    String str = "select ability from w_enter_ware t where t.Id=" + localOriginalModel.getObject().getBillid();
    int j = this.jdbcTemplate.queryForInt(str);
    assertEquals(-2, j);
  }
  
  public void testMarketAffirm()
  {
    OriginalModel localOriginalModel = getOriginalModel(2, 0, "市场审核", "approve");
    Map localMap = this.workflow.dealWorkflow(localOriginalModel, "enterWare");
    int i = ((Integer)localMap.get("result")).intValue();
    assertEquals(1, i);
    String str = "select ability from w_enter_ware t where t.Id=" + localOriginalModel.getObject().getBillid();
    int j = this.jdbcTemplate.queryForInt(str);
    assertEquals(3, j);
  }
  
  public void testMarketRevocation()
  {
    OriginalModel localOriginalModel = getOriginalModel(2, 0, "市场驳回", "overrule");
    Map localMap = this.workflow.dealWorkflow(localOriginalModel, "enterWare");
    int i = ((Integer)localMap.get("result")).intValue();
    assertEquals(1, i);
    String str = "select ability from w_enter_ware t where t.Id=" + localOriginalModel.getObject().getBillid();
    int j = this.jdbcTemplate.queryForInt(str);
    assertEquals(-3, j);
  }
  
  public OriginalModel getOriginalModel(int paramInt1, int paramInt2, String paramString1, String paramString2)
  {
    EnterWare localEnterWare = new EnterWare();
    localEnterWare.setFirmId("heihei");
    localEnterWare.setWarehouseId("111111");
    localEnterWare.setEnterInformId("12");
    localEnterWare.setWeight(11.0D);
    localEnterWare.setAbility(paramInt1);
    this.enterWareService.addEnterWare(localEnterWare);
    OriginalModel localOriginalModel = new OriginalModel();
    localOriginalModel.setObject(localEnterWare);
    localOriginalModel.setHoldAuthority(paramInt2);
    localOriginalModel.setOperate(paramString2);
    return localOriginalModel;
  }
}

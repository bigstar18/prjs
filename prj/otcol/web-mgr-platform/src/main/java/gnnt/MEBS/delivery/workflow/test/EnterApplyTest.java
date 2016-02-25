package gnnt.MEBS.delivery.workflow.test;

import gnnt.MEBS.delivery.model.OperateLog;
import gnnt.MEBS.delivery.model.workflow.EnterApply;
import gnnt.MEBS.delivery.model.workflow.WorkFlowClone;
import gnnt.MEBS.delivery.services.EnterApplyService;
import gnnt.MEBS.delivery.workflow.OriginalModel;
import gnnt.MEBS.delivery.workflow.WorkflowFacade;
import gnnt.MEBS.delivery.workflow.common.Translate;
import java.util.Date;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;

public class EnterApplyTest
  extends AbstractTest
{
  @Autowired
  @Qualifier("w_enterApplyService")
  private EnterApplyService enterApplyService;
  private String addApplyKey = "add";
  @Autowired
  @Qualifier("w_translate")
  private Translate translate;
  
  public void testAdd1()
  {
    String str = this.workflow.getLogContent(this.addApplyKey);
    OriginalModel localOriginalModel = getOriginalModel(0, 9, str, this.addApplyKey, "添加入库申请");
    Map localMap = this.workflow.dealWorkflow(localOriginalModel, "enterApply");
    int i = ((Integer)localMap.get("result")).intValue();
    assertEquals(1, i);
  }
  
  public void testAdd2()
  {
    String str = this.workflow.getLogContent(this.addApplyKey);
    OriginalModel localOriginalModel = getOriginalModel(0, 9, str, this.addApplyKey, "添加入库申请");
    EnterApply localEnterApply = (EnterApply)localOriginalModel.getObject();
    localEnterApply.setWeight(11.0D);
    this.enterApplyService.updateEnterApply(localEnterApply);
    localOriginalModel.setObject(localEnterApply);
    localEnterApply.setAbility(-1);
    Map localMap = this.workflow.dealWorkflow(localOriginalModel, "enterApply");
    int i = ((Integer)localMap.get("result")).intValue();
    assertEquals(-1, i);
    localEnterApply.setAbility(0);
    localOriginalModel.setHoldAuthority(3);
    localMap = this.workflow.dealWorkflow(localOriginalModel, "enterApply");
    i = ((Integer)localMap.get("result")).intValue();
    assertEquals(-2, i);
    localEnterApply.setAbility(6);
    localOriginalModel.setHoldAuthority(9);
    localMap = this.workflow.dealWorkflow(localOriginalModel, "enterApply");
    i = ((Integer)localMap.get("result")).intValue();
    assertEquals(-1, i);
    localOriginalModel.setObject(null);
    localMap = this.workflow.dealWorkflow(localOriginalModel, "enterApply");
    i = ((Integer)localMap.get("result")).intValue();
    assertEquals(-100, i);
  }
  
  public void testFirmRevocation()
  {
    String str1 = "cancel";
    String str2 = this.workflow.getLogContent(str1);
    OriginalModel localOriginalModel = getOriginalModel(1, 9, str2, str1, "处理入库申请");
    EnterApply localEnterApply = (EnterApply)localOriginalModel.getObject();
    this.enterApplyService.addEnterApply(localEnterApply);
    String str3 = localOriginalModel.getObject().getBillid();
    localOriginalModel.getLog().setBillId(str3);
    Map localMap = this.workflow.dealWorkflow(localOriginalModel, "enterApply");
    int i = ((Integer)localMap.get("result")).intValue();
    assertEquals(1, i);
    String str4 = "select ability from w_Enter_Apply t where t.Id=" + str3;
    int j = this.jdbcTemplate.queryForInt(str4);
    assertEquals(-1, j);
  }
  
  public void testMarketAffirm()
  {
    String str1 = "approve";
    String str2 = this.workflow.getLogContent(str1);
    OriginalModel localOriginalModel = getOriginalModel(1, 0, str2, str1, "处理入库申请");
    EnterApply localEnterApply = (EnterApply)localOriginalModel.getObject();
    this.enterApplyService.addEnterApply(localEnterApply);
    String str3 = localOriginalModel.getObject().getBillid();
    localOriginalModel.getLog().setBillId(str3);
    Map localMap = this.workflow.dealWorkflow(localOriginalModel, "enterApply");
    int i = ((Integer)localMap.get("result")).intValue();
    assertEquals(1, i);
    String str4 = "select ability from w_Enter_Apply t where t.Id=" + str3;
    int j = this.jdbcTemplate.queryForInt(str4);
    assertEquals(2, j);
  }
  
  public void testMarketRevocation()
  {
    String str1 = "overrule";
    String str2 = this.workflow.getLogContent(str1);
    OriginalModel localOriginalModel = getOriginalModel(1, 0, str2, str1, "处理入库申请");
    EnterApply localEnterApply = (EnterApply)localOriginalModel.getObject();
    this.enterApplyService.addEnterApply(localEnterApply);
    String str3 = localOriginalModel.getObject().getBillid();
    localOriginalModel.getLog().setBillId(str3);
    Map localMap = this.workflow.dealWorkflow(localOriginalModel, "enterApply");
    int i = ((Integer)localMap.get("result")).intValue();
    assertEquals(1, i);
    String str4 = "select ability from w_Enter_Apply t where t.Id=" + str3;
    int j = this.jdbcTemplate.queryForInt(str4);
    assertEquals(-2, j);
  }
  
  public void testWarehouseRevocation()
  {
    String str1 = "approve";
    String str2 = this.workflow.getLogContent(str1);
    OriginalModel localOriginalModel = getOriginalModel(2, 1, str2, str1, "处理入库申请");
    EnterApply localEnterApply = (EnterApply)localOriginalModel.getObject();
    this.enterApplyService.addEnterApply(localEnterApply);
    String str3 = localOriginalModel.getObject().getBillid();
    localOriginalModel.getLog().setBillId(str3);
    Map localMap = this.workflow.dealWorkflow(localOriginalModel, "enterApply");
    int i = ((Integer)localMap.get("result")).intValue();
    assertEquals(1, i);
    String str4 = "select ability from w_Enter_Apply t where t.Id=" + str3;
    int j = this.jdbcTemplate.queryForInt(str4);
    assertEquals(3, j);
  }
  
  public void testWarehouseVerify()
  {
    String str1 = "overrule";
    String str2 = this.workflow.getLogContent(str1);
    OriginalModel localOriginalModel = getOriginalModel(2, 1, str2, str1, "处理入库申请");
    EnterApply localEnterApply = (EnterApply)localOriginalModel.getObject();
    this.enterApplyService.addEnterApply(localEnterApply);
    String str3 = localOriginalModel.getObject().getBillid();
    localOriginalModel.getLog().setBillId(str3);
    Map localMap = this.workflow.dealWorkflow(localOriginalModel, "enterApply");
    int i = ((Integer)localMap.get("result")).intValue();
    assertEquals(1, i);
    String str4 = "select ability from w_Enter_Apply t where t.Id=" + str3;
    int j = this.jdbcTemplate.queryForInt(str4);
    assertEquals(-3, j);
  }
  
  public OriginalModel getOriginalModel(int paramInt1, int paramInt2, String paramString1, String paramString2, String paramString3)
  {
    EnterApply localEnterApply = new EnterApply();
    localEnterApply.setFirmId("zhangcc");
    localEnterApply.setWarehouseId("1234");
    localEnterApply.setAbility(paramInt1);
    localEnterApply.setWeight(1111.0D);
    localEnterApply.setUnitWeight(1.0D);
    localEnterApply.setCommodityId("4");
    OriginalModel localOriginalModel = new OriginalModel();
    localOriginalModel.setObject(localEnterApply);
    localOriginalModel.setHoldAuthority(paramInt2);
    localOriginalModel.setOperate(paramString2);
    String str = "zhangcc";
    OperateLog localOperateLog = new OperateLog();
    assertEquals(this.translate.transLogContent(paramString2), paramString1);
    localOperateLog.setContent(paramString1);
    localOperateLog.setOperatetime(new Date());
    localOperateLog.setOperation("1");
    localOperateLog.setPopedom(0L);
    localOperateLog.setUserId(str);
    localOriginalModel.setLog(localOperateLog);
    return localOriginalModel;
  }
}

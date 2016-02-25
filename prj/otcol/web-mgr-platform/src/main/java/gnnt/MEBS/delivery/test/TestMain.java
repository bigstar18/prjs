package gnnt.MEBS.delivery.test;

import gnnt.MEBS.delivery.model.OperateLog;
import gnnt.MEBS.delivery.model.workflow.EnterApply;
import gnnt.MEBS.delivery.workflow.OriginalModel;
import gnnt.MEBS.delivery.workflow.WorkflowFacade;
import java.io.PrintStream;
import java.util.Date;
import java.util.Map;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestMain
{
  public static void main(String[] paramArrayOfString)
  {
    String str1 = "addEnterApply";
    ClassPathXmlApplicationContext localClassPathXmlApplicationContext = new ClassPathXmlApplicationContext("wareHouseBeansTest.xml");
    WorkflowFacade localWorkflowFacade = (WorkflowFacade)localClassPathXmlApplicationContext.getBean("w_workflowFacade");
    String str2 = localWorkflowFacade.getLogContent(str1);
    OriginalModel localOriginalModel = getOriginalModel(0, 9, str2, str1, "添加入库申请");
    Map localMap = localWorkflowFacade.dealWorkflow(localOriginalModel, "enterApply");
    int i = ((Integer)localMap.get("result")).intValue();
    System.out.println(i);
  }
  
  public static OriginalModel getOriginalModel(int paramInt1, int paramInt2, String paramString1, String paramString2, String paramString3)
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
    localOperateLog.setContent(paramString1);
    localOperateLog.setOperatetime(new Date());
    localOperateLog.setOperation("1");
    localOperateLog.setPopedom(0L);
    localOperateLog.setUserId(str);
    localOriginalModel.setLog(localOperateLog);
    return localOriginalModel;
  }
}

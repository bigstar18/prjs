package gnnt.MEBS.delivery.workflow.example.testing;

import gnnt.MEBS.delivery.workflow.OriginalModel;
import gnnt.MEBS.delivery.workflow.WorkflowFacade;
import gnnt.MEBS.delivery.workflow.example.model.Test;
import java.io.PrintStream;
import java.util.Map;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestAdd
{
  public static void main(String[] paramArrayOfString)
  {
    Test localTest = new Test();
    localTest.setId(1L);
    localTest.setName("test");
    OriginalModel localOriginalModel = new OriginalModel();
    localOriginalModel.setObject(localTest);
    localOriginalModel.setOperate("addTest");
    localOriginalModel.setHoldAuthority(9);
    ClassPathXmlApplicationContext localClassPathXmlApplicationContext = new ClassPathXmlApplicationContext("warehouseBeansTest.xml");
    WorkflowFacade localWorkflowFacade = (WorkflowFacade)localClassPathXmlApplicationContext.getBean("workflow");
    Map localMap = localWorkflowFacade.dealWorkflow(localOriginalModel, "test");
    System.out.println(localMap.get("result"));
  }
}

package gnnt.MEBS.audit.test;

import gnnt.MEBS.audit.flowService.WorkflowFacade;
import gnnt.MEBS.audit.util.SysDataTest;
import java.io.PrintStream;
import java.util.List;
import java.util.Map;

public class TestGetOperateMsg
{
  public static void main(String[] args)
  {
    WorkflowFacade workflowFacade = (WorkflowFacade)SysDataTest.getBean("workflowFacade");
    List<Map<String, String>> list = workflowFacade.getOperateMsg("teacherAdd", 1, 1);
    for (Map<String, String> map : list)
    {
      System.out.println((String)map.get("operateName"));
      System.out.println((String)map.get("operateKey"));
    }
  }
}

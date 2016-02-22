package gnnt.MEBS.audit.test;

import gnnt.MEBS.audit.flowService.OriginalModel;
import gnnt.MEBS.audit.flowService.WorkflowFacade;
import gnnt.MEBS.audit.model.Apply;
import gnnt.MEBS.audit.model.AuditStatus;
import gnnt.MEBS.audit.service.ApplyService;
import gnnt.MEBS.audit.util.SysDataTest;
import java.io.PrintStream;
import java.util.Date;

public class TestTeacherAddAudit
{
  public static void main(String[] args)
  {
    try
    {
      AuditStatus log = new AuditStatus();
      log.setModTime(new Date());
      log.setProposer("admin");
      ApplyService applyService = (ApplyService)SysDataTest.getBean("applyService");
      Apply auditObject = new Apply();
      auditObject.setId(Long.valueOf(3L));
      auditObject = (Apply)applyService.get(auditObject);
      System.out.println("status:" + auditObject);
      OriginalModel o = new OriginalModel();
      o.setAuditObject(auditObject);
      o.setLog(log);
      o.setKey("audit");
      WorkflowFacade workflowFacade = (WorkflowFacade)SysDataTest.getBean("workflowFacade");
      int result = workflowFacade.dealWorkflow(o, "teacherAdd");
      System.out.println("result:" + result);
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
  }
}

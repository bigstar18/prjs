package gnnt.MEBS.delivery.workflow.example.handle;

import gnnt.MEBS.delivery.model.workflow.WorkFlowClone;
import gnnt.MEBS.delivery.workflow.StatusOperate;
import gnnt.MEBS.delivery.workflow.example.model.Test;
import java.io.PrintStream;

public class StatusOperateImpl
  implements StatusOperate
{
  public void updateStatus(WorkFlowClone paramWorkFlowClone, int paramInt)
  {
    Test localTest = (Test)paramWorkFlowClone;
    System.out.println("更新test的" + localTest.getId() + "的状态" + paramInt);
  }
}

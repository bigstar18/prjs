package gnnt.MEBS.delivery.workflow.example.handle.behaviour;

import gnnt.MEBS.delivery.model.workflow.WorkFlowClone;
import gnnt.MEBS.delivery.workflow.Behaviour;
import java.io.PrintStream;

public class AddBehaviour
  implements Behaviour
{
  public void deal(WorkFlowClone paramWorkFlowClone)
  {
    System.out.println("添加test信息");
  }
}

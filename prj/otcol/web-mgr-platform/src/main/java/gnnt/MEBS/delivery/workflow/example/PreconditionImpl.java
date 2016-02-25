package gnnt.MEBS.delivery.workflow.example;

import gnnt.MEBS.delivery.workflow.OriginalModel;
import gnnt.MEBS.delivery.workflow.Precondition;
import gnnt.MEBS.delivery.workflow.example.model.Test;
import java.io.PrintStream;

public class PreconditionImpl
  implements Precondition
{
  public int doPrecondition(OriginalModel paramOriginalModel)
  {
    Test localTest = (Test)paramOriginalModel.getObject();
    System.out.println("加锁test的" + localTest.getId());
    return 1;
  }
}

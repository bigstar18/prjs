package gnnt.MEBS.delivery.workflow.example.handle.filter;

import gnnt.MEBS.delivery.workflow.Filtering;
import gnnt.MEBS.delivery.workflow.OriginalModel;
import java.io.PrintStream;

public class FilteringImpl
  implements Filtering
{
  public int checkFiler(OriginalModel paramOriginalModel)
  {
    System.out.println("检查是否允许");
    return 1;
  }
}

package gnnt.MEBS.delivery.workflow.common;

import gnnt.MEBS.delivery.workflow.Filtering;
import gnnt.MEBS.delivery.workflow.OriginalModel;

public class InvalidFilterIngImpl
  implements Filtering
{
  public int checkFiler(OriginalModel paramOriginalModel)
  {
    return -6;
  }
}

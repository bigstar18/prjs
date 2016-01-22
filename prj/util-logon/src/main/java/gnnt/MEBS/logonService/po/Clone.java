package gnnt.MEBS.logonService.po;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public abstract class Clone
  implements Cloneable
{
  protected transient Log logger = LogFactory.getLog(getClass());

  public Object clone()
  {
    Object localObject = null;
    try
    {
      localObject = super.clone();
    }
    catch (Exception localException)
    {
    }
    return localObject;
  }
}
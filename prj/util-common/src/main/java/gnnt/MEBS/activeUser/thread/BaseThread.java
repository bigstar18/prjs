package gnnt.MEBS.activeUser.thread;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public abstract class BaseThread extends Thread
{
  protected final transient Log logger = LogFactory.getLog(getClass());

  protected volatile boolean stop = false;

  protected long timeSpace = 200L;

  public void pleaseStop()
  {
    this.stop = true;
    try {
      interrupt();
    }
    catch (Exception localException)
    {
    }
  }
}
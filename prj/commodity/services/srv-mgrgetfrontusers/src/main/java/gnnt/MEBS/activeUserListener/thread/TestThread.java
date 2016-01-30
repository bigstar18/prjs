package gnnt.MEBS.activeUserListener.thread;

import gnnt.MEBS.activeUserListener.actualize.LogonUserActualize;
import java.io.PrintStream;
import java.util.List;
import org.apache.commons.logging.Log;

public class TestThread
  extends BaseThread
{
  public TestThread(String paramString)
  {
    this.sysname = paramString;
    this.timeSpace = 30000L;
  }
  
  public void run()
  {
    this.stop = false;
    for (;;)
    {
      if (!this.stop) {
        try
        {
          List localList = LogonUserActualize.getInstance(this.sysname).getOnLineUserList(null, null, null);
          if (localList.size() > 0) {
            System.out.println(localList);
          }
          try
          {
            Thread.sleep(this.timeSpace);
          }
          catch (Exception localException1) {}
        }
        catch (Exception localException2)
        {
          this.logger.error("测试查询异常", localException2);
        }
        finally
        {
          try
          {
            Thread.sleep(this.timeSpace);
          }
          catch (Exception localException4) {}
        }
      }
    }
  }
}

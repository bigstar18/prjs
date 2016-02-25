package gnnt.MEBS.timebargain.manage.util;

import gnnt.MEBS.timebargain.manage.service.BroadcastManager;
import java.sql.SQLException;
import java.util.HashMap;
import javax.naming.NamingException;

public class HisBroadcast_Thread
  extends Thread
{
  private static HisBroadcast_Thread bt = null;
  private static BroadcastManager mgr;
  
  public static HisBroadcast_Thread getInstance(BroadcastManager paramBroadcastManager)
    throws ClassNotFoundException, IllegalAccessException, InstantiationException, SQLException, NamingException
  {
    mgr = paramBroadcastManager;
    if (bt == null) {
      synchronized (HisBroadcast_Thread.class)
      {
        if (bt == null)
        {
          bt = new HisBroadcast_Thread();
          bt.start();
        }
      }
    }
    return bt;
  }
  
  public void run()
  {
    try
    {
      HashMap localHashMap = (HashMap)SysData.getBean("broadcastInterval");
      String str = (String)localHashMap.get("interval");
      int i = 0;
      if ((str != null) && (!"".equals(str))) {
        i = Integer.parseInt(str);
      }
      for (;;)
      {
        mgr.hisBroadcast();
        sleep(i);
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }
}

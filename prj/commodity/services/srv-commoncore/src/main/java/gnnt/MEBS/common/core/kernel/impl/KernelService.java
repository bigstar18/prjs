package gnnt.MEBS.common.core.kernel.impl;

import gnnt.MEBS.common.core.Server;
import gnnt.MEBS.common.core.jms.MsgTask;
import gnnt.MEBS.common.core.jms.StaticThreadPool;
import gnnt.MEBS.common.core.kernel.IKernelService;
import java.rmi.RemoteException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class KernelService
  implements IKernelService
{
  Log log = LogFactory.getLog(getClass());
  
  public void checkStart() {}
  
  public boolean shutdown()
  {
    Server localServer = Server.getInstance();
    localServer.stop();
    return true;
  }
  
  public void sendFrontQueueMsg(String paramString1, String paramString2)
    throws RemoteException
  {
    MsgTask localMsgTask = new MsgTask(3, paramString1, paramString2);
    StaticThreadPool.getInstance().addTask(localMsgTask);
  }
  
  public void sendFrontTopicMsg(String paramString)
    throws RemoteException
  {
    MsgTask localMsgTask = new MsgTask(1, "", paramString);
    StaticThreadPool.getInstance().addTask(localMsgTask);
  }
  
  public void sendMgrQueueMsg(String paramString1, String paramString2)
    throws RemoteException
  {
    MsgTask localMsgTask = new MsgTask(4, paramString1, paramString2);
    StaticThreadPool.getInstance().addTask(localMsgTask);
  }
  
  public void sendMgrTopicMsg(String paramString)
    throws RemoteException
  {
    MsgTask localMsgTask = new MsgTask(2, "", paramString);
    StaticThreadPool.getInstance().addTask(localMsgTask);
  }
}

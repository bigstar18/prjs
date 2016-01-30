package gnnt.MEBS.common.communicate;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.Naming;
import java.rmi.RMISecurityManager;
import java.rmi.Remote;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.RMISocketFactory;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class RMIServer
{
  private Log log = LogFactory.getLog(getClass());
  
  public void startRMI(String paramString1, int paramInt1, int paramInt2, String paramString2, Remote paramRemote)
    throws Exception
  {
    if (System.getSecurityManager() != null) {
      System.setSecurityManager(new RMISecurityManager());
    }
    try
    {
      if (paramInt2 > 0) {
        RMISocketFactory.setSocketFactory(new SMRMISocket(paramInt2));
      }
      Socket localSocket = null;
      try
      {
        InetAddress localInetAddress = InetAddress.getLocalHost();
        localSocket = new Socket(localInetAddress, paramInt1);
      }
      catch (Exception localException2)
      {
        this.log.error("启动服务前测试连接端口 " + paramInt1 + "，发现连接失败，需要进行端口绑定，下面执行端口绑定。");
      }
      if (localSocket == null) {
        try
        {
          LocateRegistry.createRegistry(paramInt1);
        }
        catch (Exception localException3)
        {
          throw localException3;
        }
      }
      String str = "rmi://" + paramString1 + ":" + paramInt1 + "/" + paramString2;
      this.log.debug("启动 RMI 服务：" + str);
      Naming.bind(str, paramRemote);
      this.log.debug("服务 " + str + " 启动成功!");
    }
    catch (Exception localException1)
    {
      throw localException1;
    }
  }
  
  class SMRMISocket
    extends RMISocketFactory
  {
    private int port = 1099;
    
    public SMRMISocket(int paramInt)
    {
      if (paramInt > 0) {
        this.port = paramInt;
      }
    }
    
    public ServerSocket createServerSocket(int paramInt)
      throws IOException
    {
      if (paramInt <= 0) {
        paramInt = this.port;
      }
      return new ServerSocket(paramInt);
    }
    
    public Socket createSocket(String paramString, int paramInt)
      throws IOException
    {
      return new Socket(paramString, paramInt);
    }
  }
}

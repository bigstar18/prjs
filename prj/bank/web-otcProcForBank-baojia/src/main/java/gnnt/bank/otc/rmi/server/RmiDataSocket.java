package gnnt.bank.otc.rmi.server;

import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.server.RMISocketFactory;

public class RmiDataSocket
  extends RMISocketFactory
{
  private int nDataPort = -1;
  
  public RmiDataSocket() {}
  
  public RmiDataSocket(int nDataPort)
  {
    this.nDataPort = nDataPort;
  }
  
  public Socket createSocket(String host, int port)
    throws IOException
  {
    return new Socket(host, port);
  }
  
  public ServerSocket createServerSocket(int port)
    throws IOException
  {
    if (port != 0) {
      System.out.println("RMI服务器: 监听端口(程序指定) = " + port);
    }
    if ((port == 0) && (this.nDataPort != -1))
    {
      port = this.nDataPort;
      System.out.println("RMI服务器: 数据端口(程序指定) = " + port);
    }
    ServerSocket server = new ServerSocket(port);
    if (port == 0) {
      System.out.println("RMI服务器: 数据端口(系统自动分配) = " + server.getLocalPort());
    }
    return server;
  }
}

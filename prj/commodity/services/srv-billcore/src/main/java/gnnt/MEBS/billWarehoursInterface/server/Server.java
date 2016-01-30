package gnnt.MEBS.billWarehoursInterface.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class Server
  extends Thread
{
  protected final transient Log logger = LogFactory.getLog(Server.class);
  private volatile boolean stop = false;
  ServerSocket serverSocket = null;
  
  public Server(String ip, int port)
    throws IOException
  {
    try
    {
      this.serverSocket = new ServerSocket();
      this.serverSocket.bind(new InetSocketAddress(ip, port));
      this.logger.info("服务端启动成功");
    }
    catch (IOException ioe)
    {
      throw ioe;
    }
  }
  
  public void run()
  {
    while (!this.stop)
    {
      Receiver receiver = null;
      try
      {
        Socket socket = this.serverSocket.accept();
        receiver = new Receiver(socket);
      }
      catch (IOException e)
      {
        e.printStackTrace();
      }
      receiver.start();
    }
  }
  
  public void shutdown()
  {
    this.logger.info("*** Stop Server.");
    this.stop = true;
    try
    {
      this.serverSocket.close();
      this.serverSocket = null;
      
      interrupt();
    }
    catch (Exception localException) {}
  }
}

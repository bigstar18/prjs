package gnnt.mebsv.hqservice.service.communication;

import gnnt.mebsv.hqservice.service.ServiceManagerX;
import java.io.IOException;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class AppServerX extends Thread
{
  private String serverName;
  private int port;
  private int poollength;
  protected ServiceManagerX manager = null;
  protected ServerSocket listener = null;
  private InetAddress bindAddr = null;

  public AppServerX(String paramString1, int paramInt1, int paramInt2, String paramString2)
  {
    this.serverName = paramString1;
    this.port = paramInt1;
    this.poollength = paramInt2;
    try
    {
      if (paramString2 != null)
        this.bindAddr = InetAddress.getByName(paramString2);
    }
    catch (UnknownHostException localUnknownHostException)
    {
      System.out.println("Error: The binding IP is invalid ! ");
      this.bindAddr = null;
    }
  }

  private boolean initAppServer()
  {
    try
    {
      this.manager = new ServiceManagerX(this.serverName, this.poollength);
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      return false;
    }
    try
    {
      if (this.bindAddr == null)
        this.listener = new ServerSocket(this.port, 618);
      else
        this.listener = new ServerSocket(this.port, 618, this.bindAddr);
    }
    catch (IOException localIOException)
    {
      System.out.println("Error:Create ServerSocket fail.");
      localIOException.printStackTrace();
      return false;
    }
    return true;
  }

  public void run()
  {
    if (initAppServer())
    {
      System.out.println(this.serverName + " is running!");
      System.out.println("The port is " + this.port);
    }
    else
    {
      System.out.println(this.serverName + "has failed!");
      return;
    }
    try
    {
      while (true)
      {
        Socket localSocket = this.listener.accept();
        this.manager.dealRequest(localSocket);
      }
    }
    catch (IOException localIOException)
    {
      System.out.println("AppServer Exception while listening for connections");
      localIOException.printStackTrace();
    }
  }

  public int getThreadCount()
  {
    return this.manager.getThreadCount();
  }

  public int getSocketCount()
  {
    return this.manager.getSocketCount();
  }

  public int getPoolCount()
  {
    return this.manager.getPoolCount();
  }
}
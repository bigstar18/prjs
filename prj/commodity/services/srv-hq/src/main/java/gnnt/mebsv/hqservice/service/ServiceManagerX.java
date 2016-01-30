package gnnt.mebsv.hqservice.service;

import gnnt.mebsv.hqservice.model.ClientSocket;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Vector;

public class ServiceManagerX
{
  private String serverName;
  private int poollength;
  public Vector socketContainer;
  public Vector threadPool;
  private Object threadLock = new Object();

  public ServiceManagerX(String paramString, int paramInt)
  {
    this.serverName = paramString;
    this.poollength = paramInt;
    this.socketContainer = new Vector();
    this.threadPool = new Vector();
    try
    {
      ResponderX localResponderX = (ResponderX)Class.forName(this.serverName).newInstance();
      localResponderX.setManager(this);
      localResponderX.initResponder();
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }

  public synchronized void dealRequest(Socket paramSocket)
  {
    Vector localVector = null;
    int i = 0;
    int j = this.socketContainer.size();
    for (int k = j - 1; k >= 0; k--)
    {
      localVector = (Vector)this.socketContainer.elementAt(k);
      synchronized (this.threadLock)
      {
        if (localVector.size() == 0)
        {
          this.socketContainer.removeElement(localVector);
        }
        else if ((i == 0) && (localVector.size() < this.poollength - 1))
        {
          addClient(localVector, paramSocket);
          i = 1;
        }
      }
    }
    if (i == 0)
    {
      localVector = new Vector();
      addClient(localVector, paramSocket);
      this.socketContainer.addElement(localVector);
      try
      {
        ResponderX localResponderX = (ResponderX)Class.forName(this.serverName).newInstance();
        localResponderX.setManager(this);
        localResponderX.setSocketPool(localVector);
        localResponderX.initResponder();
        localResponderX.start();
        this.threadPool.addElement(localResponderX);
      }
      catch (InstantiationException localInstantiationException)
      {
        System.out.println("AppServer Instance fail!");
        localInstantiationException.printStackTrace();
      }
      catch (ClassNotFoundException localClassNotFoundException)
      {
        System.out.println("AppServer Instance fail!");
        localClassNotFoundException.printStackTrace();
      }
      catch (IllegalAccessException localIllegalAccessException)
      {
        System.out.println("AppServer Instance fail!");
        localIllegalAccessException.printStackTrace();
      }
    }
  }

  public void removeClient(Vector paramVector, ClientSocket paramClientSocket)
  {
    synchronized (paramVector)
    {
      paramVector.removeElement(paramClientSocket);
    }
  }

  public boolean removeThread(ResponderX paramResponderX)
  {
    synchronized (this.threadLock)
    {
      if (paramResponderX.clientPool.size() == 0)
      {
        this.threadPool.removeElement(paramResponderX);
        return true;
      }
      return false;
    }
  }

  private void addClient(Vector paramVector, Socket paramSocket)
  {
    synchronized (paramVector)
    {
      ClientSocket localClientSocket = new ClientSocket();
      localClientSocket.socket = paramSocket;
      paramVector.addElement(localClientSocket);
    }
  }

  public int getSocketCount()
  {
    int i = 0;
    for (int j = 0; j < this.socketContainer.size(); j++)
      i += ((Vector)this.socketContainer.elementAt(j)).size();
    return i;
  }

  public int getThreadCount()
  {
    return this.threadPool.size();
  }

  public int getPoolCount()
  {
    return this.socketContainer.size();
  }
}
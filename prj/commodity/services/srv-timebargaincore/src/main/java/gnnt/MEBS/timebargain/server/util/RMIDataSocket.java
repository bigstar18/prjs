package gnnt.MEBS.timebargain.server.util;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.server.RMISocketFactory;

public class RMIDataSocket
  extends RMISocketFactory
{
  private static int nDataPort;
  
  public RMIDataSocket()
  {
    nDataPort = -1;
  }
  
  public RMIDataSocket(int paramInt)
  {
    nDataPort = paramInt;
  }
  
  public Socket createSocket(String paramString, int paramInt)
    throws IOException
  {
    return new Socket(paramString, paramInt);
  }
  
  public ServerSocket createServerSocket(int paramInt)
    throws IOException
  {
    if ((paramInt == 0) && (nDataPort != -1)) {
      paramInt = nDataPort;
    }
    ServerSocket localServerSocket = new ServerSocket(paramInt);
    return localServerSocket;
  }
}

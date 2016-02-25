package gnnt.MEBS.member.ActiveUser;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.server.RMISocketFactory;

public class RmiDataSocket
  extends RMISocketFactory
{
  private static int nDataPort;
  
  public RmiDataSocket()
  {
    nDataPort = -1;
  }
  
  public RmiDataSocket(int paramInt)
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

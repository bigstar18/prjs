package gnnt.mebsv.hqservice.service.communication;

import java.net.Socket;
import java.util.Date;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class SocketTimeOutThread extends Thread
{
  boolean isRun = true;
  private Log log = LogFactory.getLog(SocketTimeOutThread.class);
  Socket socket = null;
  Date setDate = null;

  public void setSocket(Socket paramSocket)
  {
    this.socket = paramSocket;
    this.setDate = new Date();
  }

  public void removeSocket(Socket paramSocket)
  {
    if (paramSocket.equals(this.socket))
      this.socket = null;
  }

  public void run()
  {
    while (this.isRun)
      try
      {
        if ((this.socket != null) && (System.currentTimeMillis() - this.setDate.getTime() > 5000L))
        {
          this.socket.close();
          this.socket = null;
        }
        sleep(1000L);
      }
      catch (Exception localException)
      {
        this.log.debug("close date:" + new Date().toLocaleString() + "e:" + localException.getMessage());
        localException.printStackTrace();
      }
    this.socket = null;
  }

  public void end()
  {
    this.isRun = false;
  }
}
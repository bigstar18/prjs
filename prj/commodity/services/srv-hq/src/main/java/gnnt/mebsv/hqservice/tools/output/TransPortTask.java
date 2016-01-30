package gnnt.mebsv.hqservice.tools.output;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.net.SocketException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class TransPortTask
  implements Runnable
{
  private Log log = LogFactory.getLog(TransPortTask.class);
  Socket socket;
  DataOutputStream outputStream;
  byte[] data;

  public TransPortTask(Socket paramSocket, byte[] paramArrayOfByte)
  {
    this.socket = paramSocket;
    this.data = paramArrayOfByte;
  }

  public void printStackTrace(Exception paramException)
  {
    paramException.printStackTrace();
    try
    {
      this.socket.close();
      this.log.debug(this.socket + "       " + "     " + new String(this.data, "UTF-8"));
    }
    catch (UnsupportedEncodingException localUnsupportedEncodingException)
    {
      localUnsupportedEncodingException.printStackTrace();
    }
    catch (IOException localIOException)
    {
      localIOException.printStackTrace();
    }
  }

  public void run()
  {
    try
    {
      this.outputStream = new DataOutputStream(new BufferedOutputStream(this.socket.getOutputStream()));
      synchronized (this.outputStream)
      {
        this.outputStream.write(this.data);
        this.outputStream.flush();
        this.log.debug("推送数据到当前客户端" + this.socket + "   " + this.data.toString());
      }
    }
    catch (SocketException localSocketException)
    {
      printStackTrace(localSocketException);
    }
    catch (Exception localException)
    {
      printStackTrace(localException);
    }
  }
}
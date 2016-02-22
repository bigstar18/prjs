package gnnt.MEBS.common.filter;

import gnnt.MEBS.trade.model.vo.HQServerInfoVO;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Hashtable;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class HQServerInfoThread
  extends Thread
{
  protected final transient Log logger = LogFactory.getLog(HQServerInfoThread.class);
  private Socket serverSocket;
  private DataInputStream input;
  private String serverInfoID;
  private HQServerInfoFilter hqServerInfoFilter;
  
  public HQServerInfoThread(String serverInfoID, HQServerInfoFilter hqServerInfoFilter)
  {
    if (serverInfoID == null) {
      throw new IllegalArgumentException("serverInfoID can't be null");
    }
    this.serverInfoID = serverInfoID;
    this.hqServerInfoFilter = hqServerInfoFilter;
  }
  
  public void run()
  {
    for (;;)
    {
      if ((this.serverSocket == null) || (this.serverSocket.isClosed()))
      {
        HQServerInfoVO hqServerInfoVO = (HQServerInfoVO)HQServerInfoFilter.serverInfoTable.get(this.serverInfoID);
        if (!connectServer(hqServerInfoVO)) {
          try
          {
            Thread.sleep(500L);
          }
          catch (Exception localException1) {}
        }
      }
      else
      {
        String msg = receive(this.input);
        if (msg != null) {
          try
          {
            this.hqServerInfoFilter.processMsg(this.serverInfoID, msg);
          }
          catch (Exception e)
          {
            this.logger.error("processMsg occur Error,Error info=" + 
              e.getMessage());
            e.printStackTrace();
          }
        }
      }
    }
  }
  
  public boolean connectServer(HQServerInfoVO hqServerInfoVO)
  {
    this.logger.info("connectServer............");
    boolean result = false;
    try
    {
      this.serverSocket = new Socket(hqServerInfoVO.getServerAddr(), hqServerInfoVO.getServerPort().intValue());
      this.input = new DataInputStream(new BufferedInputStream(this.serverSocket
        .getInputStream()));
      this.logger.info("connectServer success");
    }
    catch (Exception e)
    {
      this.logger.error("connectServer Exception", e);
      closeSocket();
    }
    return result;
  }
  
  private String receive(DataInputStream input)
  {
    String msg = "";
    ByteArrayOutputStream array = null;
    DataOutputStream outputArray = null;
    try
    {
      array = new ByteArrayOutputStream();
      outputArray = new DataOutputStream(array);
      byte b = input.readByte();
      while ((b = input.readByte()) != 0) {
        outputArray.write(b);
      }
      outputArray.flush();
      byte[] buf = array.toByteArray();
      msg = new String(buf, "GBK");
    }
    catch (IOException e)
    {
      this.logger.error("receive  occur Error,Error info=" + e.getMessage());
      e.printStackTrace();
      msg = null;
      closeSocket();
      if (outputArray != null) {
        try
        {
          outputArray.close();
        }
        catch (IOException e)
        {
          e.printStackTrace();
          this.logger.warn("outputArray.close occur IOException:" + 
            e.getMessage());
        }
      }
      if (array != null) {
        try
        {
          array.close();
        }
        catch (IOException e)
        {
          e.printStackTrace();
          this.logger.warn("array.close occur IOException:" + 
            e.getMessage());
        }
      }
    }
    catch (Exception e)
    {
      this.logger.error("receive occur Error,Error info=" + e.getMessage());
      e.printStackTrace();
      msg = null;
      closeSocket();
      if (outputArray != null) {
        try
        {
          outputArray.close();
        }
        catch (IOException e)
        {
          e.printStackTrace();
          this.logger.warn("outputArray.close occur IOException:" + 
            e.getMessage());
        }
      }
      if (array != null) {
        try
        {
          array.close();
        }
        catch (IOException e)
        {
          e.printStackTrace();
          this.logger.warn("array.close occur IOException:" + 
            e.getMessage());
        }
      }
    }
    finally
    {
      if (outputArray != null) {
        try
        {
          outputArray.close();
        }
        catch (IOException e)
        {
          e.printStackTrace();
          this.logger.warn("outputArray.close occur IOException:" + 
            e.getMessage());
        }
      }
      if (array != null) {
        try
        {
          array.close();
        }
        catch (IOException e)
        {
          e.printStackTrace();
          this.logger.warn("array.close occur IOException:" + 
            e.getMessage());
        }
      }
    }
    return msg;
  }
  
  private void closeSocket()
  {
    this.logger.info("close socket");
    if (this.serverSocket != null)
    {
      if (!this.serverSocket.isClosed()) {
        try
        {
          this.serverSocket.close();
        }
        catch (IOException e1)
        {
          this.logger.warn("serverSocket.close occur Error,Error info=" + 
            e1.getMessage());
          e1.printStackTrace();
        }
      }
      this.serverSocket = null;
    }
    if (this.input != null)
    {
      try
      {
        this.input.close();
      }
      catch (IOException e1)
      {
        this.logger.warn("input.close() occur Error,Error info=" + 
          e1.getMessage());
        e1.printStackTrace();
      }
      this.input = null;
    }
    HQServerInfoVO hqServerInfoVO = (HQServerInfoVO)HQServerInfoFilter.serverInfoTable.get(this.serverInfoID);
    hqServerInfoVO.setStatus(Integer.valueOf(1));
  }
  
  public static void main(String[] args) {}
}

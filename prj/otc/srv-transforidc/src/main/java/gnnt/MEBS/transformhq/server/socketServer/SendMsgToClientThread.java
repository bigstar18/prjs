package gnnt.MEBS.transformhq.server.socketServer;

import gnnt.MEBS.transformhq.server.model.HQBean;
import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class SendMsgToClientThread
  extends Thread
{
  private static Log log = LogFactory.getLog(SendMsgToClientThread.class);
  private boolean isRun = true;
  private Socket socket;
  private BlockingQueue<HQBean> hqBeanQue;
  
  public SendMsgToClientThread(Socket socket)
  {
    this.socket = socket;
    this.hqBeanQue = new LinkedBlockingQueue();
  }
  
  public void run()
  {
    while (this.isRun) {
      try
      {
        HQBean hqBean = (HQBean)this.hqBeanQue.take();
        sendMsgToSingleClient(hqBean);
      }
      catch (InterruptedException e)
      {
        e.printStackTrace();
        log.error("send msg to client error ：" + e);
        this.isRun = false;
      }
    }
  }
  
  public boolean getStatus()
  {
    return this.isRun;
  }
  
  public void addHQBean(HQBean hqBean)
  {
    this.hqBeanQue.offer(hqBean);
  }
  
  public DataOutputStream getWriter(Socket socket)
    throws IOException
  {
    OutputStream OutputStream = socket.getOutputStream();
    return new DataOutputStream(new BufferedOutputStream(OutputStream));
  }
  
  public void sendMsgToSingleClient(HQBean hqBean)
  {
    String msg = "";
    String dateType = "";
    DataOutputStream socketStream = null;
    try
    {
      msg = hqBean.getServerContents();
      dateType = "|" + hqBean.getDateType() + "|";
      byte[] buf = msg.getBytes();
      String pHeader = "|" + buf.length + dateType;
      socketStream = getWriter(this.socket);
      socketStream.writeByte(-1);
      socketStream.write(pHeader.getBytes());
      socketStream.write(buf);
      socketStream.writeByte(0);
      socketStream.flush();
      log.info("send msg success :" + hqBean.toString() + " to ip:" + this.socket.getInetAddress() + ":" + this.socket.getPort());
    }
    catch (Exception e)
    {
      log.warn("send msg warn :" + msg + " ip:" + this.socket.getInetAddress() + ":" + this.socket.getPort());
      destroy(this.socket, socketStream);
    }
  }
  
  public void destroy(Socket socket, DataOutputStream out)
  {
    this.isRun = false;
    try
    {
      if (out != null)
      {
        out.close();
        out = null;
      }
    }
    catch (Exception e)
    {
      e.printStackTrace();
      log.error("close DataOutputStream error : ip:" + socket.getInetAddress() + ":" + socket.getPort());
    }
    try
    {
      if (socket != null)
      {
        socket.close();
        socket = null;
      }
    }
    catch (Exception e)
    {
      e.printStackTrace();
      log.error("close socket error : ip:" + socket.getInetAddress() + ":" + socket.getPort());
    }
  }
  
  public String toString()
  {
    return this.socket.getLocalAddress() + ":" + this.socket.getPort();
  }
}

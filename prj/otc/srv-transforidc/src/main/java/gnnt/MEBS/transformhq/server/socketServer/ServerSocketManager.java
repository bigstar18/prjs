package gnnt.MEBS.transformhq.server.socketServer;

import gnnt.MEBS.transformhq.server.model.HQBean;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.BlockingQueue;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ServerSocketManager
  extends Thread
{
  private static Log log = LogFactory.getLog(ServerSocketManager.class);
  private ServerSocket serverSocket;
  private BlockingQueue<HQBean> hqBeanQue;
  private Vector<SendMsgToClientThread> newAcceptClientList = new Vector();
  private List<SendMsgToClientThread> socketClientList = new ArrayList();
  private List<SendMsgToClientThread> invalidClientList = new ArrayList();
  private boolean isRun = true;
  
  public ServerSocketManager(int port, InetAddress localIpConfig)
  {
    try
    {
      this.serverSocket = new ServerSocket(port, 10, localIpConfig);
      log.info("serverSocket startUp ...");
    }
    catch (IOException e)
    {
      e.printStackTrace();
      log.error("serverSocket startUp error :" + e);
    }
  }
  
  public void init(BlockingQueue<HQBean> hqBeanQue)
  {
    this.hqBeanQue = hqBeanQue;
    
    new SendMsg().start();
    
    start();
  }
  
  public void run()
  {
    for (;;)
    {
      Socket socket = null;
      try
      {
        socket = this.serverSocket.accept();
        SendMsgToClientThread sendMsg = new SendMsgToClientThread(socket);
        sendMsg.start();
        this.newAcceptClientList.add(sendMsg);
        log.info("new connection accepted : " + socket.getInetAddress() + ":" + socket.getPort());
      }
      catch (IOException e)
      {
        e.printStackTrace();
        log.error("new connection accepted error :" + e);
      }
    }
  }
  
  class SendMsg
    extends Thread
  {
    SendMsg() {}
    
    public void run()
    {
      while (ServerSocketManager.this.isRun) {
        try
        {
          HQBean hqBean = (HQBean)ServerSocketManager.this.hqBeanQue.take();
          if (ServerSocketManager.this.newAcceptClientList.size() > 0)
          {
            ServerSocketManager.this.socketClientList.addAll(ServerSocketManager.this.newAcceptClientList);
            ServerSocketManager.this.newAcceptClientList.clear();
            ServerSocketManager.log.info("socketClientList list size:" + ServerSocketManager.this.socketClientList.size());
          }
          if (ServerSocketManager.this.socketClientList.size() > 0) {
            ServerSocketManager.this.sendMsgToAllClient(hqBean);
          } else {
            ServerSocketManager.log.info("drop msg : " + hqBean.toString());
          }
        }
        catch (InterruptedException e)
        {
          e.printStackTrace();
          ServerSocketManager.log.warn("send msg warn:" + e);
        }
      }
    }
  }
  
  public void sendMsgToAllClient(HQBean hqBean)
  {
    try
    {
      for (SendMsgToClientThread sendMsg : this.socketClientList) {
        if (sendMsg.getStatus())
        {
          sendMsg.addHQBean(hqBean);
        }
        else
        {
          this.invalidClientList.add(sendMsg);
          log.info("drop socketClient:" + sendMsg.toString());
        }
      }
      if (this.invalidClientList.size() > 0)
      {
        this.socketClientList.removeAll(this.invalidClientList);
        this.invalidClientList.clear();
        log.info("socketClientList size :" + this.socketClientList.size());
      }
    }
    catch (Exception e)
    {
      e.printStackTrace();
      log.error("sendMsgToAllClient error:" + e);
    }
  }
}

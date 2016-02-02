package gnnt.MEBS.transformhq.server.clientManager;

import gnnt.MEBS.transformhq.server.DaemonTread.ClientDaemonThread;
import gnnt.MEBS.transformhq.server.DaemonTread.ReconnectionInterFace;
import gnnt.MEBS.transformhq.server.model.HQBean;
import gnnt.MEBS.transformhq.server.model.IPConfig;
import gnnt.MEBS.transformhq.server.model.InCommodity;
import gnnt.MEBS.transformhq.server.quotation.QuotationInterFace;
import gnnt.MEBS.transformhq.server.util.ForeignDSUtil;
import gnnt.MEBS.transformhq.server.util.HQBeanFactory;
import gnnt.MEBS.transformhq.server.util.TransformForInteractive;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class SocketClient
  extends Thread
  implements ReconnectionInterFace
{
  protected final transient Log logger = LogFactory.getLog(SocketClient.class);
  private IPConfig ipConfig;
  private Socket clientSocket;
  private boolean isRun = true;
  private DataInputStream serverStr;
  private DataOutputStream serverOut;
  private QuotationInterFace quoInterFace;
  private Map<String, InCommodity> inCommodity;
  private ClientDaemonThread daemonThread;
  private Map<String, Character> marketMap = new HashMap();
  private boolean isLogin = false;
  
  public SocketClient(IPConfig ipConfig, QuotationInterFace quoInterFace)
  {
    this.ipConfig = ipConfig;
    this.quoInterFace = quoInterFace;
    this.inCommodity = quoInterFace.getInCommodity();
    this.marketMap = ((Map)HQBeanFactory.getBean("marketMap"));
    this.daemonThread = new ClientDaemonThread(ipConfig, this.inCommodity, this);
    this.daemonThread.start();
  }
  
  public void initStream()
  {
    try
    {
      this.serverStr = new DataInputStream(this.clientSocket.getInputStream());
      this.serverOut = new DataOutputStream(this.clientSocket.getOutputStream());
    }
    catch (Exception e)
    {
      e.printStackTrace();
      this.logger.error("exception in initStream..." + e.toString());
    }
  }
  
  public void run()
  {
    this.logger.info("Thread socketClient start....... " + this.ipConfig.toString());
    while (this.isRun) {
      try
      {
        if ((this.clientSocket != null) && (!this.clientSocket.isClosed()))
        {
          process();
        }
        else
        {
          this.clientSocket = new Socket(this.ipConfig.getiP(), Integer.parseInt(this.ipConfig.getPort()));
          
          this.clientSocket.setSoTimeout(30000);
          initStream();
        }
      }
      catch (Exception e)
      {
        e.printStackTrace();
        this.logger.error("exception in Thread socketClient..." + e.toString());
        closeSocket();
        try
        {
          sleep(1000L);
        }
        catch (InterruptedException e1)
        {
          e1.printStackTrace();
          this.logger.error("error :" + e1);
        }
      }
    }
  }
  
  public void process()
  {
    try
    {
      while (!this.isLogin)
      {
        login();
        sendCommodity();
      }
      for (;;)
      {
        String hqServerString = ForeignDSUtil.readResponse(this.serverStr).trim();
        this.logger.debug("=================>" + hqServerString);
        if (!"".equals(hqServerString)) {
          if (("5026=1|5001=0".endsWith(hqServerString)) || ("5026=2|5001=0".endsWith(hqServerString)))
          {
            this.logger.debug("接收到的数据为：" + hqServerString);
          }
          else
          {
            if ("5026=1|5001=-175".equals(hqServerString))
            {
              this.isLogin = false;
              break;
            }
            if (hqServerString.contains("|14="))
            {
              this.logger.debug("刷新数据：" + hqServerString);
            }
            else if ((hqServerString.contains("8=")) || (hqServerString.contains("12=")))
            {
              HQBean hqBean = TransformForInteractive.transServerHQ(hqServerString);
              hqBean.setHqBeanInetAddress(this.ipConfig.toString());
              if (checkHQBean(hqBean))
              {
                this.daemonThread.resetHQTime(hqBean);
                this.quoInterFace.setHQBean(hqBean);
              }
            }
          }
        }
      }
    }
    catch (Exception e)
    {
      e.printStackTrace();
      closeSocket();
      this.logger
        .debug("exception in ProcessServerDataForeignDS  method process " + 
        e.toString());
    }
  }
  
  private boolean checkHQBean(HQBean hqBean)
  {
    if (!this.inCommodity.containsKey(hqBean.getCommodityID())) {
      return false;
    }
    if (((InCommodity)this.inCommodity.get(hqBean.getCommodityID())).getQuoSupplier()) {
      return true;
    }
    if (this.marketMap.containsKey(ForeignDSUtil.parseResponse(hqBean.getClientContents()).get("248"))) {
      return true;
    }
    return false;
  }
  
  public void login()
    throws Exception
  {
    String userName = this.ipConfig.getName();
    String userPsw = this.ipConfig.getPassword();
    StringBuffer loginSB = new StringBuffer();
    loginSB.append("5022=LoginUser|")
      .append("5028=").append(userName).append("|")
      .append("5029=").append(userPsw).append("|")
      .append("5026=7");
    
    byte[] ls = loginSB.toString().getBytes();
    this.serverOut.write(ForeignDSUtil.assembleCMD(ls));
    this.serverOut.flush();
    
    String res = ForeignDSUtil.readResponse(this.serverStr);
    if (res.equals("5026=7|5001=0"))
    {
      this.isLogin = true;
      this.logger.info("登录成功");
    }
  }
  
  public void sendCommodity()
    throws Exception
  {
    Iterator<String> iterator = this.inCommodity.keySet().iterator();
    while (iterator.hasNext())
    {
      String commodityId = (String)iterator.next();
      StringBuffer message = new StringBuffer();
      message.append("5022=Subscribe|");
      
      message.append(((InCommodity)this.inCommodity.get(commodityId)).getRequestType());
      message.append("5=").append(commodityId).append("|");
      message.append("5026=2");
      System.out.println("发送商品代码" + message.toString());
      byte[] ms = message.toString().getBytes();
      this.serverOut.write(ForeignDSUtil.assembleCMD(ms));
      this.serverOut.flush();
    }
  }
  
  public void closeSocket()
  {
    this.logger.info(" close clientSocket ...." + this.ipConfig.toString());
    this.isLogin = false;
    try
    {
      if (this.clientSocket != null)
      {
        this.clientSocket.close();
        this.clientSocket = null;
      }
      if (this.serverStr != null)
      {
        this.serverStr.close();
        this.serverStr = null;
      }
      if (this.serverOut != null)
      {
        this.serverOut.close();
        this.serverOut = null;
      }
    }
    catch (Exception e)
    {
      this.logger.error(" excpetion in clientSocket ClientManager..." + e);
    }
  }
  
  public static void main(String[] args)
  {
    IPConfig ipConfig = new IPConfig("127.0.0.1", "7022", "WESTERNC", "WESTERNC", 1, 20L);
    SocketClient client = new SocketClient(ipConfig, null);
    client.start();
  }
  
  public void reconnection()
  {
    closeSocket();
  }
}

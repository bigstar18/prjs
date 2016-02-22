package gnnt.quotation;

import gnnt.MEBS.timebargain.server.model.HQServerInfo;
import gnnt.MEBS.timebargain.server.quotation.QuotationInterface;
import gnnt.MEBS.timebargain.server.quotation.QuotationTest;
import gnnt.MEBS.transformhq.server.rmi.TransFormRMI;
import gnnt.quotation.util.DateUtil;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.rmi.Naming;
import java.util.Date;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.locks.ReentrantLock;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ReceiveQuotation
  extends Thread
{
  protected final transient Log logger = LogFactory.getLog(ReceiveQuotation.class);
  private static final ReentrantLock lock = new ReentrantLock();
  private volatile boolean stop = false;
  private int curServerIndex = 0;
  HQServerInfo curServerInfo;
  private Socket serverSocket;
  private DataInputStream input;
  private ProcessQuotationData process;
  private SocketTimeOutThread socketTimeOutThread;
  private TransFormRMI transFormRMI;
  private checkTransFormStatus checkThread;
  private List<HQServerInfo> serverList;
  private QuotationInterface quotation;
  
  public static ReceiveQuotation Start(QuotationInterface quotation)
    throws Exception
  {
    ReceiveQuotation receiveQuotation = null;
    lock.lock();
    try
    {
      receiveQuotation = new ReceiveQuotation(quotation);
      receiveQuotation.start();
    }
    catch (Exception e)
    {
      throw e;
    }
    finally
    {
      lock.unlock();
    }
    return receiveQuotation;
  }
  
  private ReceiveQuotation(QuotationInterface quotation)
    throws Exception
  {
    if (quotation == null) {
      throw new IllegalArgumentException("quotation can't be null");
    }
    if (quotation.getHQServerInfoList() == null) {
      throw new IllegalArgumentException("HQServerInfoList can't be null");
    }
    if (quotation.getHQServerInfoList().size() == 0) {
      throw new IllegalArgumentException(
        "HQServerInfoList Have to be bigger than 0");
    }
    this.quotation = quotation;
    quotation.init();
    this.serverList = quotation.getHQServerInfoList();
    
    this.curServerInfo = getNextHQServer();
    if (this.curServerInfo == null)
    {
      String error = "没有行情服务器可以使用,不能启动行情接收线程!!!";
      this.logger.fatal(error);
      throw new Exception(error);
    }
    this.checkThread = new checkTransFormStatus();
    this.checkThread.start();
  }
  
  public void refreshHQserverinfo()
  {
    this.curServerIndex = this.serverList.size();
    this.curServerInfo = getNextHQServer();
    closeSocket();
  }
  
  private HQServerInfo getNextHQServer()
  {
    if ((this.serverList == null) || (this.serverList.size() == 0)) {
      return null;
    }
    if (this.curServerIndex == this.serverList.size())
    {
      this.curServerIndex = 0;
      if ((this.quotation.getHQServerInfoList() != null) && 
        (this.quotation.getHQServerInfoList().size() > 0)) {
        this.serverList = this.quotation.getHQServerInfoList();
      } else {
        this.logger.warn("quotation.getHQServerInfoList() Get HQServerInfoList acount is 0");
      }
    }
    return (HQServerInfo)this.serverList.get(this.curServerIndex++);
  }
  
  public void run()
  {
    this.process = new ProcessQuotationData(this.quotation);
    this.process.start();
    

    this.socketTimeOutThread = new SocketTimeOutThread();
    this.socketTimeOutThread.setDaemon(true);
    this.socketTimeOutThread.start();
    while (!this.stop) {
      if ((this.serverSocket == null) || (this.serverSocket.isClosed()))
      {
        if (!connectServer(this.curServerInfo))
        {
          if (this.curServerInfo.getReConnectLoop() % 5 == 0)
          {
            this.logger.info(" switch server from " + 
              this.curServerInfo.toString());
            this.curServerInfo = getNextHQServer();
            this.logger.info(" to " + this.curServerInfo.toString());
          }
          if (this.curServerInfo.getReConnectLoop() == 10000)
          {
            this.logger.info("正在与行情服务器[" + this.curServerInfo.toString() + 
              "]进行第" + this.curServerInfo.getReConnectLoop() + 
              "次重新连接的计数器被重置为1");
            this.curServerInfo.setReConnectLoop(1);
          }
          if (this.curServerInfo.getReConnectLoop() <= 5) {
            try
            {
              Thread.sleep(500L);
            }
            catch (InterruptedException e)
            {
              e.printStackTrace();
              this.logger.error("socket重连线程休眠中断，原因：" + 
                e.getMessage());
            }
          } else if (this.curServerInfo.getReConnectLoop() > 5) {
            try
            {
              Thread.sleep(10000L);
            }
            catch (InterruptedException e)
            {
              e.printStackTrace();
              this.logger.error("socket重连线程休眠中断，原因：" + 
                e.getMessage());
            }
          }
        }
      }
      else
      {
        this.curServerInfo.setReConnectLoop(1);
        String msg = receive(this.input);
        if (msg != null)
        {
          this.logger.info("receive msg=" + msg + "from server=" + 
            this.curServerInfo.toString());
          if ((checkMsg(msg)) || (!this.quotation.getTraderOrderStatus()))
          {
            try
            {
              processMsg(msg);
            }
            catch (Exception e)
            {
              this.logger.error("processMsg occur Error,Error info=" + 
                e.getMessage());
              e.printStackTrace();
            }
          }
          else
          {
            this.curServerInfo = getNextHQServer();
            
            closeSocket();
            this.logger.warn("当前使用转发器异常，切换至：" + this.curServerInfo.getServerAddr() + ":" + this.curServerInfo.getServerPort());
          }
        }
      }
    }
  }
  
  public boolean checkMsg(String msg)
  {
    String[] strs = msg.split("\\|", -1);
    if ((strs[2].equals("7000")) && 
      (strs[3].equals("1")))
    {
      this.logger.error("接收到行情转发器错误信息" + this.curServerInfo.toString());
      return false;
    }
    return true;
  }
  
  public boolean connectServer(HQServerInfo serverInfo)
  {
    this.logger.info("connectServer............");
    boolean result = false;
    try
    {
      this.serverSocket = new Socket(serverInfo.getServerAddr(), serverInfo
        .getServerPort());
      try
      {
        this.serverSocket.setSoTimeout(30000);
      }
      catch (SocketException e)
      {
        e.printStackTrace();
        this.logger.error("设置超时时间失败:" + e);
      }
      this.input = new DataInputStream(new BufferedInputStream(this.serverSocket
        .getInputStream()));
      
      result = true;
      

      this.logger.info("通知数据库已连接上：" + serverInfo.toString());
      this.quotation.setCurServerInfo(serverInfo);
      
      this.logger.info("connectServer success");
    }
    catch (UnknownHostException e)
    {
      this.logger.warn("connectServer UnknownHostException ");
      e.printStackTrace();
    }
    catch (IOException e)
    {
      this.logger.warn("connectServer IOException ");
      e.printStackTrace();
    }
    if (!result)
    {
      if (serverInfo.getReConnectLoop() == 0) {
        this.logger.error("尝试连接行情服务器[" + serverInfo.toString() + "]失败");
      } else {
        this.logger.error("正在与行情服务器[" + serverInfo.toString() + "]进行第" + 
          serverInfo.getReConnectLoop() + "次重新连接");
      }
      this.curServerInfo.setReConnectLoop(this.curServerInfo.getReConnectLoop() + 1);
    }
    return result;
  }
  
  private void closeSocket()
  {
    this.logger.info("close socket, close date:" + DateUtil.getCurDateTime());
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
  }
  
  private void processMsg(String msg)
  {
    if ((this.process != null) && (!this.process.stopped)) {
      try
      {
        this.process.quotationQueue.put(msg);
        this.logger.debug("process.processQueue add msg=" + msg);
      }
      catch (InterruptedException e)
      {
        e.printStackTrace();
        this.logger.error(" put data exception,msg=" + msg + 
          ";exception info=" + e.getMessage());
      }
    }
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
      this.socketTimeOutThread.setTime();
      byte b = input.readByte();
      while ((b = input.readByte()) != 0) {
        outputArray.write(b);
      }
      this.socketTimeOutThread.initTime();
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
  
  public void shutdown()
  {
    this.logger.info("*** Stop ReceiveQuotation.");
    this.stop = true;
    try
    {
      closeSocket();
      
      interrupt();
      if (this.process != null) {
        this.process.shutdown();
      }
      if (this.socketTimeOutThread != null)
      {
        this.socketTimeOutThread.shutdown();
        
        this.socketTimeOutThread = null;
      }
      if (this.quotation != null) {
        this.quotation.dispose();
      }
    }
    catch (Exception localException) {}
  }
  
  class SocketTimeOutThread
    extends Thread
  {
    boolean isRun = true;
    Date setDate = null;
    
    SocketTimeOutThread() {}
    
    void setTime()
    {
      this.setDate = new Date();
    }
    
    void initTime()
    {
      this.setDate = null;
    }
    
    public void run()
    {
      label160:
      while (this.isRun) {
        try
        {
          if ((ReceiveQuotation.this.serverSocket != null) && (this.setDate != null))
          {
            if (System.currentTimeMillis() - this.setDate.getTime() <= 5000L) {
              break label160;
            }
            ReceiveQuotation.this.logger.warn("SocketTimeOutThread checked timeout close socket");
            ReceiveQuotation.this.closeSocket();
          }
        }
        catch (Exception e)
        {
          ReceiveQuotation.this.logger.error("SocketTimeOutThread checked timeout occur Error:" + 
            e.getMessage());
          e.printStackTrace();
          try
          {
            sleep(1000L);
          }
          catch (InterruptedException e)
          {
            ReceiveQuotation.this.logger.warn("SocketTimeOutThread sleep occur Error;");
            e.printStackTrace();
          }
        }
        finally
        {
          try
          {
            sleep(1000L);
          }
          catch (InterruptedException e)
          {
            ReceiveQuotation.this.logger.warn("SocketTimeOutThread sleep occur Error;");
            e.printStackTrace();
          }
        }
      }
    }
    
    public void shutdown()
    {
      this.isRun = false;
      try
      {
        interrupt();
      }
      catch (Exception localException) {}
    }
  }
  
  class checkTransFormStatus
    extends Thread
  {
    boolean isRun = true;
    HQServerInfo curServer = null;
    
    checkTransFormStatus() {}
    
    public void run()
    {
      while (this.isRun) {
        try
        {
          if ((this.curServer == null) || (!checkMainServer((HQServerInfo)ReceiveQuotation.this.serverList.get(0))) || (ReceiveQuotation.this.transFormRMI == null))
          {
            this.curServer = ((HQServerInfo)ReceiveQuotation.this.serverList.get(0));
            while (!ReceiveQuotation.this.lookUPRMI(this.curServer.getServerAddr(), 
              String.valueOf(this.curServer.getRmiPort()))) {
              sleep(1000L);
            }
          }
          if ((!ReceiveQuotation.this.curServerInfo.equals(ReceiveQuotation.this.serverList.get(0))) && (ReceiveQuotation.this.quotation.getTraderOrderStatus()) && 
            (ReceiveQuotation.this.transFormRMI.isUsable()))
          {
            ReceiveQuotation.this.curServerInfo = this.curServer;
            ReceiveQuotation.this.closeSocket();
            
            ReceiveQuotation.this.curServerIndex = 1;
            ReceiveQuotation.this.logger.warn("由于主行情转发器恢复正常，切换回主行情转发器：" + ReceiveQuotation.this.curServerInfo.getServerAddr() + ":" + ReceiveQuotation.this.curServerInfo.getServerPort());
          }
          sleep(1000L);
        }
        catch (Exception e)
        {
          ReceiveQuotation.this.transFormRMI = null;
          this.curServer = null;
          ReceiveQuotation.this.logger.error("rmi error:" + e);
        }
      }
    }
    
    public boolean checkMainServer(HQServerInfo newServer)
    {
      if ((this.curServer.getServerAddr().equals(newServer.getServerAddr())) && 
        (this.curServer.getRmiPort() == newServer.getRmiPort())) {
        return true;
      }
      ReceiveQuotation.this.logger.warn("主行情转发器由：" + this.curServer.getServerAddr() + ":" + this.curServer.getRmiPort() + " 变更为：" + newServer.getServerAddr() + ":" + newServer.getRmiPort());
      return false;
    }
    
    public void shutdown()
    {
      this.isRun = false;
      try
      {
        interrupt();
      }
      catch (Exception localException) {}
    }
  }
  
  public boolean lookUPRMI(String host, String port)
  {
    try
    {
      this.transFormRMI = null;
      StringBuffer sb = new StringBuffer();
      sb.append("rmi://").append(host).append(":").append(port).append("/TransFormRMI");
      this.transFormRMI = ((TransFormRMI)Naming.lookup(sb.toString()));
      return true;
    }
    catch (Exception e)
    {
      this.logger.error("lookUPRMI rmi error :" + e);
      this.transFormRMI = null;
    }
    return false;
  }
  
  public static void main(String[] args)
    throws IOException
  {
    try
    {
      receiveQuotation = 
        Start(new QuotationTest());
    }
    catch (Exception e)
    {
      ReceiveQuotation receiveQuotation;
      e.printStackTrace();
    }
  }
}

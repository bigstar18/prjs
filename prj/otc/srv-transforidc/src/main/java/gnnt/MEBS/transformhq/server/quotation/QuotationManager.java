package gnnt.MEBS.transformhq.server.quotation;

import gnnt.MEBS.transformhq.server.clientManager.ClientManager;
import gnnt.MEBS.transformhq.server.model.HQBean;
import gnnt.MEBS.transformhq.server.model.LocalIpConfig;
import gnnt.MEBS.transformhq.server.rmi.TransFormRMI;
import gnnt.MEBS.transformhq.server.rmi.TransFormRMIImpl;
import gnnt.MEBS.transformhq.server.socketServer.ServerSocketManager;
import gnnt.MEBS.transformhq.server.tools.foctory.HQBeanFactory;
import java.net.InetAddress;
import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class QuotationManager
{
  private Log log = LogFactory.getLog(QuotationManager.class);
  private BlockingQueue<HQBean> hqBeanQue = new LinkedBlockingQueue();
  private ServerSocketManager serverSocketManager;
  private HQHeartThread hqHeart;
  private static QuotationManager instance;
  private LocalIpConfig localIPConfig;
  private HQStatus hqStatus = new HQStatus();
  private QuotationCheckThread quotationCheck;
  private QuotationInterFace quotationInterFace;
  private ClientManager clientManager;
  private CheckAndSendHQBean checkAndSendHQBean;
  private TransFormRMI transFormRMI;
  
  public static QuotationManager getInstance()
  {
    if (instance == null) {
      instance = new QuotationManager();
    }
    return instance;
  }
  
  public void init()
  {
    try
    {
      this.hqHeart = new HQHeartThread(this.hqStatus);
      this.hqHeart.init(this.hqBeanQue);
      this.hqHeart.start();
      this.log.info("初始化心跳包发送对象完成");
      

      this.checkAndSendHQBean = new CheckAndSendHQBean(this.hqBeanQue);
      this.log.info("初始化商品发送对象完成");
      

      this.quotationCheck = new QuotationCheckThread(this.hqStatus);
      this.quotationCheck.start();
      this.log.info("初始化行情接口完成");
      

      this.localIPConfig = ((LocalIpConfig)HQBeanFactory.getBean("localIpConfig"));
      this.serverSocketManager = new ServerSocketManager(this.localIPConfig.getSocketPort(), InetAddress.getByName(this.localIPConfig.getIp()));
      this.serverSocketManager.init(this.hqBeanQue);
      


      this.transFormRMI = new TransFormRMIImpl(this.hqStatus);
      LocateRegistry.createRegistry(this.localIPConfig.getRmiPort());
      Naming.rebind("rmi://" + this.localIPConfig.getIp() + ":" + this.localIPConfig.getRmiPort() + "/TransFormRMI", this.transFormRMI);
      this.log.info("初始化本地socket及RMI服务完成：" + this.localIPConfig.toString());
      

      this.quotationInterFace = new QuotationInterFaceImpl(this.checkAndSendHQBean, this.quotationCheck);
      

      this.clientManager = new ClientManager(this.quotationInterFace);
      this.clientManager.init();
      this.log.info("初始行情接收对象完成");
    }
    catch (Exception e)
    {
      e.printStackTrace();
      this.log.error("初始化行情转发报错：" + e);
      System.exit(1);
    }
  }
  
  public String getClientVersion()
  {
    return this.quotationInterFace.getClientVersion();
  }
  
  public CheckAndSendHQBean getCheckAndSendHQBean()
  {
    return this.checkAndSendHQBean;
  }
}

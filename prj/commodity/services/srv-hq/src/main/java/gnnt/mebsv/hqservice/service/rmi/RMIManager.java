package gnnt.mebsv.hqservice.service.rmi;

import gnnt.MEBS.timebargain.server.rmi.quotation.IQuotationRMI;
import gnnt.MEBS.util.Configuration;
import gnnt.mebsv.hqservice.dao.rmidao.HQRMIDAO;
import gnnt.mebsv.hqservice.dao.rmidao.HQRMIDAOFactory;
import java.rmi.Naming;
import java.util.Map;
import java.util.Properties;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class RMIManager
{
  protected final Log logger = LogFactory.getLog(RMIManager.class);
  private static RMIManager initRMI;
  private HQRMIDAO rmiDAO;
  public Properties params;
  private IQuotationRMI quotationRMI = null;
  private StringBuffer rmiConfig = new StringBuffer();
  Map rmiMap = null;

  public IQuotationRMI getQuotationRMI()
  {
    return this.quotationRMI;
  }

  private RMIManager()
  {
    try
    {
      this.params = new Configuration().getSection("MEBS.Quotation");
      this.params.list(System.out);
      this.rmiDAO = HQRMIDAOFactory.getDAO(this.params);
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }

  public static RMIManager getInstance()
  {
    if (initRMI == null)
      synchronized (RMIManager.class)
      {
        if (initRMI == null)
          initRMI = new RMIManager();
      }
    return initRMI;
  }

  public void initRMI()
  {
    try
    {
      this.logger.debug("正在初始化rmi配置");
      String str = new Configuration().getSection("MEBS.Quotation").getProperty("TradeModule");
      this.rmiMap = this.rmiDAO.getHQRMI(str);
      this.rmiConfig.append("rmi://").append(this.rmiMap.get("host")).append(":").append(this.rmiMap.get("port")).append("/QuotationRMI");
      this.logger.debug("------->quotationRMI server url:" + this.rmiConfig.toString());
      lookup();
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }

  public void lookup()
  {
    try
    {
      this.quotationRMI = null;
      this.quotationRMI = ((IQuotationRMI)Naming.lookup(this.rmiConfig.toString()));
      this.logger.debug("rmi连接成功。。。");
    }
    catch (Exception localException)
    {
      this.logger.debug("连接rmi发生异常。。。");
      try
      {
        Thread.sleep(1000L);
      }
      catch (InterruptedException localInterruptedException)
      {
        localInterruptedException.printStackTrace();
      }
      localException.printStackTrace();
    }
  }
}
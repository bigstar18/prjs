package gnnt.MEBS.timebargain.server.quotation;

import gnnt.MEBS.timebargain.server.Server;
import gnnt.MEBS.timebargain.server.ServerInit;
import gnnt.MEBS.timebargain.server.dao.DAOBeanFactory;
import gnnt.MEBS.timebargain.server.dao.ServerDAO;
import gnnt.MEBS.timebargain.server.model.Commodity;
import gnnt.MEBS.timebargain.server.model.ExchageRate;
import gnnt.quotation.ReceiveQuotation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class QuotationEngine
{
  private Log log = LogFactory.getLog(getClass());
  private static Map<String, QuotationObservable> quotationObservableMap = new HashMap();
  private static Map<String, ExchageRate> exchageRateMap = new HashMap();
  private static Map<String, ArrayList<String>> thirdCmdtyMappingMap = new HashMap();
  private static QuotationEngine instance;
  private ReceiveQuotation receiveQuotation;
  
  public static QuotationEngine getInstance()
  {
    if (instance == null) {
      instance = new QuotationEngine();
    }
    return instance;
  }
  
  public void init()
  {
    QuotationInterface quotationInterface = (QuotationInterface)DAOBeanFactory.getBean("quotationInterface");
    loadQuotationObservable(ServerInit.getCommodityMap());
    loadExchageRateMap();
    loadThirdCmdtyMappingMap();
    try
    {
      if (this.receiveQuotation != null)
      {
        this.log.info("停止接收行情服务。。。");
        this.receiveQuotation.shutdown();
        this.log.info("停止接收行情服务完毕！");
      }
      this.log.info("启动接收行情服务。。。");
      this.receiveQuotation = ReceiveQuotation.Start(quotationInterface);
      this.log.info("启动接收行情服务完毕！");
    }
    catch (Exception e)
    {
      e.printStackTrace();
      this.log.error("*******************警告：启动接收行情服务失败，timebargain整个应用停止服务，请手工重启此timebargain服务！******************");
      System.exit(1);
      return;
    }
  }
  
  public void refreshMemory()
  {
    loadQuotationObservable(ServerInit.getCommodityMap());
  }
  
  private void loadQuotationObservable(Map<String, Commodity> cmds)
  {
    this.log.debug("loadQuotationObservable...");
    this.log.debug("loadQuotationObservable.cmds=" + cmds);
    quotationObservableMap.clear();
    for (String commodityID : cmds.keySet())
    {
      QuotationObservable quotationObservable = new QuotationObservable(commodityID);
      quotationObservableMap.put(commodityID, quotationObservable);
    }
    this.log.debug("loadQuotationObservable.quotationObservableMap=" + quotationObservableMap);
  }
  
  public void loadExchageRateMap()
  {
    exchageRateMap.clear();
    List<ExchageRate> lst = Server.getServerDAO().getExchageRates();
    for (ExchageRate exchageRate : lst) {
      exchageRateMap.put(exchageRate.getCommodityID(), exchageRate);
    }
  }
  
  private void loadThirdCmdtyMappingMap()
  {
    thirdCmdtyMappingMap.clear();
    for (ExchageRate exchageRate : exchageRateMap.values()) {
      if (thirdCmdtyMappingMap.containsKey(exchageRate.getInCommodityID()))
      {
        ((ArrayList)thirdCmdtyMappingMap.get(exchageRate.getInCommodityID())).add(exchageRate.getCommodityID());
      }
      else
      {
        ArrayList<String> list = new ArrayList();
        list.add(exchageRate.getCommodityID());
        thirdCmdtyMappingMap.put(exchageRate.getInCommodityID(), list);
      }
    }
  }
  
  public static Map<String, QuotationObservable> getQuotationObservableMap()
  {
    return quotationObservableMap;
  }
  
  public static void setQuotationObservableMap(Map<String, QuotationObservable> quotationObservableMap)
  {
    quotationObservableMap = quotationObservableMap;
  }
  
  public void stop()
  {
    quotationObservableMap = null;
    if (this.receiveQuotation != null)
    {
      this.receiveQuotation.shutdown();
      this.receiveQuotation = null;
    }
    instance = null;
  }
  
  public ReceiveQuotation getReceiveQuotation()
  {
    return this.receiveQuotation;
  }
  
  public static Map<String, ExchageRate> getExchageRateMap()
  {
    return exchageRateMap;
  }
  
  public static void setExchageRateMap(Map<String, ExchageRate> exchageRateMap)
  {
    exchageRateMap = exchageRateMap;
  }
  
  public static Map<String, ArrayList<String>> getThirdCmdtyMappingMap()
  {
    return thirdCmdtyMappingMap;
  }
  
  public static void setThirdCmdtyMappingMap(Map<String, ArrayList<String>> thirdCmdtyMappingMap)
  {
    thirdCmdtyMappingMap = thirdCmdtyMappingMap;
  }
}

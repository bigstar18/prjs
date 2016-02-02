package gnnt.MEBS.timebargain.plugin.condition;

import gnnt.MEBS.timebargain.plugin.condition.db.ConditionDao;
import gnnt.MEBS.timebargain.plugin.condition.model.RmiConf;
import gnnt.MEBS.timebargain.plugin.condition.rmi.RMIManager;
import gnnt.MEBS.timebargain.plugin.condition.task.QuotationScanner;
import gnnt.MEBS.timebargain.server.model.Consigner;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ConditionServer
{
  public static Config config;
  public static RmiConf TRADE_RMICONF;
  
  public static void main(String[] paramArrayOfString)
  {
    Log localLog = LogFactory.getLog(ConditionServer.class);
    localLog.debug("条件下单服务1.0 启动...加载配置文件信息");
    config = new Config();
    Consigner localConsigner = new Consigner();
    localConsigner.setConsignerID("gnnt_condi");
    localConsigner.setPassword("gnnt8765");
    CalculateCenter localCalculateCenter = new CalculateCenter(config, localConsigner);
    if (!RMIManager.initCondditionRmi(CalculateCenter.getDAOInstance().getRmiConf(Integer.parseInt(config.ConditionModule)), localCalculateCenter))
    {
      localLog.error("RMI服务启动失败，系统将退出！");
      System.exit(1);
    }
    TRADE_RMICONF = CalculateCenter.getDAOInstance().getTradeRmi(Integer.parseInt(config.TradeModule));
    RMIManager.initTradeRmi(TRADE_RMICONF);
    localLog.info("计算中心加载数据...");
    localCalculateCenter.loadData();
    localLog.info("启动RMI服务...");
    localLog.info("启动行情扫描线程...");
    QuotationScanner localQuotationScanner = new QuotationScanner(localCalculateCenter);
    localQuotationScanner.start();
  }
}

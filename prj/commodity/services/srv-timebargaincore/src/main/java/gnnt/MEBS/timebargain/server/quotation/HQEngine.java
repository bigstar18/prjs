package gnnt.MEBS.timebargain.server.quotation;

import gnnt.MEBS.timebargain.server.dao.quotation.BaseLoader;
import gnnt.MEBS.timebargain.server.dao.quotation.IDBTransfer;
import gnnt.MEBS.timebargain.server.dao.quotation.loder.LoaderBill;
import gnnt.MEBS.timebargain.server.dao.quotation.loder.LoaderTimeAndDelay;
import gnnt.MEBS.timebargain.server.quotation.config.Config;
import gnnt.MEBS.timebargain.server.quotation.server.HQProcess;
import java.io.PrintStream;
import java.util.Date;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class HQEngine
{
  public static final String versionNum = "v4.0.0.0";
  private Log log = LogFactory.getLog(HQEngine.class);
  public static IDBTransfer trans;
  public static BaseLoader loader;
  public HQProcess quotation;
  private static int HQENGINE_STATUS = 0;
  public static Config config = new Config();
  
  public HQEngine(IDBTransfer paramIDBTransfer)
  {
    trans = paramIDBTransfer;
  }
  
  public boolean getIsReStart()
  {
    boolean bool = false;
    if (HQENGINE_STATUS == 0) {
      bool = true;
    } else if (HQENGINE_STATUS == 1) {
      bool = false;
    }
    return bool;
  }
  
  public void init()
  {
    if (config.commodityStyle == 0) {
      loader = new BaseLoader(config);
    } else if (config.commodityStyle == 1) {
      loader = new LoaderTimeAndDelay(config);
    } else if (config.commodityStyle == 2) {
      loader = new LoaderBill(config);
    }
    HQENGINE_STATUS = config.hqEngineStatus;
    this.quotation = new HQProcess();
  }
  
  public void hqStart()
  {
    System.out.println("*******************************************************");
    System.out.println("*");
    System.out.println("*    GNNT Quotation Ver v4.0.0.0");
    System.out.println("*");
    System.out.println("*    BeiJing GoldNet&Tec Co. ");
    System.out.println("*");
    System.out.println("*    北京金网安泰信息技术有限公司");
    System.out.println("*");
    System.out.println("*");
    System.out.println("*******************************************************");
    this.log.debug("==  method in hqStart   ==        ");
    this.log.debug("启动行情引擎模块   ， 启动时间：" + new Date());
    if (null != this.quotation) {
      this.quotation.start();
    }
    HQENGINE_STATUS = 1;
  }
  
  public void shutdown()
  {
    this.quotation.shutdown();
    this.quotation = null;
  }
}

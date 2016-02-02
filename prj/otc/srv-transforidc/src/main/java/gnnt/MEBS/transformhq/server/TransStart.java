package gnnt.MEBS.transformhq.server;

import gnnt.MEBS.transformhq.server.quotation.QuotationManager;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class TransStart
{
  private static Log log = LogFactory.getLog(TransStart.class);
  private static QuotationManager serverManager;
  public static final String serverVersion = "server_1.0.0";
  
  public static void main(String[] args)
  {
    log.info("行情转发器开始启动. . .");
    serverManager = QuotationManager.getInstance();
    serverManager.init();
    log.info("行情转发器启动成功. . .");
    log.info("*******************************************************");
    log.info("*");
    log.info("*    GNNT TransForm server_1.0.0 " + serverManager.getClientVersion());
    log.info("*");
    log.info("*    BeiJing GoldNet&Tec Co. ");
    log.info("*");
    log.info("*    北京金网安泰信息技术有限公司");
    log.info("*");
    log.info("*");
    log.info("*******************************************************");
  }
}

package gnnt.MEBS.billWarehoursInterface;

import gnnt.MEBS.billWarehoursInterface.server.Server;
import gnnt.MEBS.billWarehoursInterface.util.DateUtil;
import gnnt.MEBS.billWarehoursInterface.util.GnntBeanFactory;
import java.io.PrintStream;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ServerShell
{
  public static void main(String[] args)
  {
    Log log = LogFactory.getLog(ServerShell.class);
    
    log.info("正在启动与仓库对接系统．．．");
    try
    {
      Server server = new Server(GnntBeanFactory.getConfig("ip"), 
        Integer.parseInt(GnntBeanFactory.getConfig("port")));
      server.start();
      
      System.out
        .println("=============================================================================");
      System.out
        .println("=                                                                           =");
      System.out
        .println("=                                                                           =");
      System.out
        .println("=                      欢迎进入仓库对接系统!!                               =");
      System.out
        .println("=                                                                           =");
      System.out
        .println("=                                                                           =");
      System.out
        .println("=                                                                           =");
      System.out
        .println("=                            已成功启动!                                    =");
      System.out
        .println("=                                                                           =");
      System.out
        .println("=                                                                           =");
      System.out
        .println("=                                                                           =");
      System.out
        .println("=                      北京金网安泰信息技术有限公司.                        =");
      System.out
        .println("=                                                                           =");
      System.out
        .println("=                                                                           =");
      System.out
        .println("=                                                        " + 
        DateUtil.getCurDate() + "         =");
      System.out
        .println("=============================================================================");
    }
    catch (Exception e)
    {
      e.printStackTrace();
      log.error("服务启动失败");
    }
  }
}

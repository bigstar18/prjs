package gnnt.mebsv.hqtrans;

import gnnt.mebsv.hqtrans.service.HQTrans;
import java.io.PrintStream;
import java.util.Date;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ServerStart
{
  private static Log log = LogFactory.getLog(ServerStart.class);
  public static final String versionNum = "4.0.0.0";
  
  public static void main(String[] paramArrayOfString)
  {
    System.out.println("*******************************************************");
    System.out.println("*");
    System.out.println("*    GNNT HQTrans Server 4.0.0.0");
    System.out.println("*");
    System.out.println("*    BeiJing GoldNet&Tec Co. ");
    System.out.println("*");
    System.out.println("*    北京金网安泰信息技术有限公司");
    System.out.println("*");
    System.out.println("*");
    System.out.println("*******************************************************");
    System.out.println("Start at " + new Date());
    log.debug("==  method in ServerStart   ==        ");
    log.debug("启动K线生成服务   ， 启动时间：" + new Date());
    log.debug("启动版本：4.0.0.0");
    HQTrans localHQTrans = new HQTrans();
    Object localObject;
    if ((paramArrayOfString.length == 2) && (paramArrayOfString[0].equalsIgnoreCase("KLine")) && (paramArrayOfString[1].length() > 0))
    {
      localObject = paramArrayOfString[1];
      localHQTrans.init();
      Date localDate = new Date(System.currentTimeMillis());
      System.out.println(localDate.toLocaleString());
      System.out.println("Start to create day line file...");
      localHQTrans.createKLineFile("day", (String)localObject);
      System.out.println("Start to create 5 minutes line file...");
      localHQTrans.createKLineFile("5min", (String)localObject);
      System.out.println("Start to create 1 minutes line file...");
      localHQTrans.createKLineFile("min", (String)localObject);
      System.out.println("Finished !");
    }
    else
    {
      System.out.println("\r\n\r\n");
      localObject = new Date(System.currentTimeMillis());
      System.out.println(((Date)localObject).toLocaleString());
      System.out.println("HQTrans Start ...");
      if (!localHQTrans.init()) {
        return;
      }
      localHQTrans.start();
    }
  }
}

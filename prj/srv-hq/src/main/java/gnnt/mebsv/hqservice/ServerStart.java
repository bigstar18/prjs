package gnnt.mebsv.hqservice;

import gnnt.mebsv.hqservice.service.communication.AppServerX;
import gnnt.mebsv.hqservice.service.rmi.RMIManager;
import gnnt.mebsv.hqservice.tools.GetStartP;
import java.io.PrintStream;
import java.util.Date;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ServerStart
{
  private static Log log = LogFactory.getLog(ServerStart.class);
  private static final int PORT = 6101;
  private static final int POOLLENGTH = 300;
  private static RMIManager initRMI;
  public static final String versionNum = "4.1.0.1";

  public static void main(String[] paramArrayOfString)
  {
    System.out.println("*******************************************************");
    System.out.println("*");
    System.out.println("*    GNNT HQService Server 4.1.0.1");
    System.out.println("*");
    System.out.println("*    BeiJing GoldNet&Tec Co. ");
    System.out.println("*");
    System.out.println("*    北京金网安泰信息技术有限公司");
    System.out.println("*");
    System.out.println("*");
    System.out.println("*******************************************************");
    System.out.println("Start at " + new Date());
    log.debug("==  method in ServerStart   ==        ");
    log.debug("启动行情高速服务   ， 启动时间：" + new Date());
    log.debug("启动版本：4.1.0.1");
    int i = 6101;
    int j = 300;
    String str1 = null;
    String str2 = null;
    initRMI = RMIManager.getInstance();
    initRMI.initRMI();
    if (paramArrayOfString.length < 1)
    {
      System.out.println("参数错误。格式为：");
      System.out.println("rewin.service.AppServer  serverName [port=n] [poollength=n] [localIP=ip] [processMarketName=String]");
      System.exit(0);
    }
    String str3 = paramArrayOfString[0];
    int k = 0;
    for (int m = 1; m < paramArrayOfString.length; m++)
    {
      String str4 = GetStartP.getParameterName(paramArrayOfString[m]);
      if (("PORT".equals(str4.toUpperCase())) || ("POOLLENGTH".equals(str4.toUpperCase())))
        k = GetStartP.getParameterNum(paramArrayOfString[m]);
      if (str4.toUpperCase() == "ERROE")
      {
        System.out.println(str4 + " 是非法的选项。");
        System.exit(0);
      }
      if (k < 0)
      {
        System.out.println(str4 + " 的值必须是大于0的整数。");
        System.exit(0);
      }
      String str5 = str4.toUpperCase();
      if (str5.equals("PORT"))
      {
        i = k;
      }
      else if (str5.equals("POOLLENGTH"))
      {
        j = k;
      }
      else
      {
        int n;
        if (str5.equals("LOCALIP"))
        {
          n = paramArrayOfString[m].lastIndexOf("=");
          if (n == -1)
          {
            System.out.println(str4 + " 参数不对。");
            System.exit(0);
          }
          else
          {
            str1 = paramArrayOfString[m].substring(n + 1, paramArrayOfString[m].length());
          }
        }
        else if (str5.equals("PROCESSMARKETNAME"))
        {
          n = paramArrayOfString[m].lastIndexOf("=");
          if (n == -1)
          {
            System.out.println(str4 + " 参数不对。");
            System.exit(0);
          }
          else
          {
            str2 = paramArrayOfString[m].substring(n + 1, paramArrayOfString[m].length());
          }
        }
        else
        {
          System.out.println(str4 + " 是不识别的选项。");
          System.exit(0);
        }
      }
    }
    AppServerX localAppServerX = new AppServerX(str3, i, j, str1);
    localAppServerX.start();
  }
}
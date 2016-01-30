package gnnt.MEBS.common.core;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ServerShell
{
  public static boolean OpenRMIService = true;
  public static boolean OpenTask = true;
  
  public static void main(String[] paramArrayOfString)
  {
    Log localLog = LogFactory.getLog(ServerShell.class);
    parseArgs(paramArrayOfString);
    localLog.info("正在启动后台管理系统核心服务．．．");
    Server localServer = Server.getInstance();
    localServer.init();
    localServer.initServer();
  }
  
  private static void parseArgs(String[] paramArrayOfString)
  {
    for (int i = 0; i < paramArrayOfString.length; i++) {
      if (paramArrayOfString[i].startsWith("-o"))
      {
        if (paramArrayOfString[i].equalsIgnoreCase("-oOpenRMIService=false")) {
          OpenRMIService = false;
        }
        if (paramArrayOfString[i].equalsIgnoreCase("-oOpenTask=false")) {
          OpenTask = false;
        }
      }
    }
  }
}

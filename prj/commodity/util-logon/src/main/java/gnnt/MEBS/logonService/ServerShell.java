package gnnt.MEBS.logonService;

import gnnt.MEBS.logonService.util.Tool;
import java.io.PrintStream;
import java.util.Date;

public class ServerShell
{
  public static void main(String[] paramArrayOfString)
    throws Exception
  {
    Server localServer = Server.getInstance();
    localServer.initServer();
    printWelcome();
  }

  private static void printWelcome()
  {
    System.out.println("=============================================================================");
    System.out.println("=                                                                          =");
    System.out.println("=                                                                          =");
    System.out.println("=                      欢迎进入 ActiveUser RMI 服务!!                         =");
    System.out.println("=                                                                           =");
    System.out.println("=                                                                           =");
    System.out.println("=                                                                           =");
    System.out.println("=                            已成功启动!                                     =");
    System.out.println("=                                                                           =");
    System.out.println("=                                                                           =");
    System.out.println("=                                                                           =");
    System.out.println("=                      北京金网安泰信息技术有限公司.                          =");
    System.out.println("=                                                                           =");
    System.out.println("=                                                                           =");
    System.out.println("=                                             " + Tool.fmtTime(new Date()) + "         =");
    System.out.println("=============================================================================");
  }
}
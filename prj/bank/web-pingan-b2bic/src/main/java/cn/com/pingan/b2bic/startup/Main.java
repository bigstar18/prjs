package cn.com.pingan.b2bic.startup;

import cn.com.pingan.b2bic.app.B2bicMain;
import java.io.BufferedReader;
import java.io.Console;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Arrays;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class Main
{
  private static final Log logger = LogFactory.getLog(B2bicMain.class);
  private static final String ACTION_START = "start";
  private static final String ACTION_STOP = "close";
  private static final String ACTION_WEBONLY = "webonly";
  private static final String ACTION_PASSWORD = "password";
  
  public static void main(String[] args)
  {
    Main main = new Main();
    String action = System.getProperty("action", "start");
    if ("start".equals(action))
    {
      main.start(args);
    }
    else if ("close".equals(action))
    {
      main.close(args);
    }
    else if ("webonly".equals(action))
    {
      main.webOnly(args);
    }
    else if ("password".equals(action))
    {
      main.pwdEncode();
    }
    else
    {
      System.err.println("启动命令错误");
      System.exit(0);
    }
  }
  
  public void start(String[] args)
  {
    try
    {
      B2bicMain main = new B2bicMain();
      main.run(args);
    }
    catch (Exception e)
    {
      logger.error("企业前置启动失败:" + e.getMessage());
    }
  }
  
  public void webOnly(String[] args)
  {
    try
    {
      B2bicMain main = new B2bicMain();
      main.setWebOnly(true);
      main.run(args);
    }
    catch (Exception e)
    {
      logger.error("企业前置启动失败:" + e.getMessage());
    }
  }
  
  public void close(String[] args)
  {
    File closeInfo = new File("configuration", ".appCloseInfo");
    if (!closeInfo.exists())
    {
      System.err.println("关闭系统失败!系统可能未启动.");
      try
      {
        Thread.sleep(10000L);
      }
      catch (InterruptedException localInterruptedException) {}
      return;
    }
    String closeCmd = null;
    int closePort = 0;
    BufferedReader reader = null;
    try
    {
      reader = new BufferedReader(new FileReader(closeInfo));
      closePort = Integer.parseInt(reader.readLine());
      closeCmd = reader.readLine();
    }
    catch (FileNotFoundException e1)
    {
      e1.printStackTrace();
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
    finally
    {
      if (reader != null) {
        try
        {
          reader.close();
        }
        catch (IOException e)
        {
          e.printStackTrace();
        }
      }
    }
    try
    {
      Socket s = new Socket("127.0.0.1", closePort);
      OutputStream out = s.getOutputStream();
      out.write(closeCmd.getBytes());
      out.flush();
      System.out.println("关闭程序向企业前置发送关闭命令成功！");
      s.shutdownOutput();
      s.close();
    }
    catch (UnknownHostException e)
    {
      e.printStackTrace();
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
  }
  
  public void pwdEncode()
  {
    Console cons = System.console();
    if ((cons = System.console()) != null)
    {
      char[] passwd;
      if ((passwd = cons.readPassword("[%s]", new Object[] { "Input Password:" })) != null)
      {
        System.out.println(new DesReader().write(new String(passwd)));
        Arrays.fill(passwd, ' ');
      }
    }
  }
}

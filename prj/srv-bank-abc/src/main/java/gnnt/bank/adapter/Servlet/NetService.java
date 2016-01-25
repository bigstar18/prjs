package gnnt.bank.adapter.Servlet;

import gnnt.bank.adapter.ABCBankImpl;
import gnnt.bank.adapter.BankAdapter;
import gnnt.bank.adapter.rmi.serice.AdapterClient;
import gnnt.bank.adapter.rmi.serice.AdapterServer;
import gnnt.bank.adapter.socket.CommandService;
import gnnt.bank.adapter.timeTask.TimeTaskServer;
import gnnt.bank.adapter.util.Common;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class NetService extends HttpServlet
{
  private static final long serialVersionUID = 5497802245403790712L;
  private AdapterServer as = new AdapterServer();
  private AdapterClient ac = new AdapterClient();
  private CommandService cs = new CommandService();

  public void destroy()
  {
    super.destroy();
  }

  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
    throws ServletException, IOException
  {
    Date d = new Date();
    BankAdapter.log(">>>>>>>>>" + d.toLocaleString() + ">>>>>>>>>>>>>>>");

    BufferedReader reader = null;
    StringBuffer msg = null;
    int perchar = 0;
    try
    {
      reader = req.getReader();
      msg = new StringBuffer();
      while ((perchar = reader.read()) != -1)
      {
        msg.append((char)perchar);
      }

      BankAdapter.log("收到银行请求信息：" + msg);
    }
    catch (IOException e) {
      ABCBankImpl.log(Common.getExceptionTrace(e));
      try
      {
        if (reader != null)
          reader.close();
      }
      catch (IOException e1)
      {
        ABCBankImpl.log(Common.getExceptionTrace(e1));
      }
    }
    finally
    {
      try
      {
        if (reader != null)
          reader.close();
      }
      catch (IOException e1)
      {
        ABCBankImpl.log(Common.getExceptionTrace(e1));
      }
    }

    if ((msg == null) || ("".equalsIgnoreCase(msg.toString()))) {
      BankAdapter.log("请求信息为空白!!!");
      PrintWriter writer = null;
      try {
        writer = new PrintWriter(new OutputStreamWriter(resp.getOutputStream(), 
          "GB2312"));
        writer.write("请求信息为空白!!!");
        writer.flush();
        writer.close();
      }
      catch (Exception exxx) {
        ABCBankImpl.log(Common.getExceptionTrace(exxx));
      }
    }
    else {
      BankAdapter.log("-------------------------------");
    }
  }

  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
    throws ServletException, IOException
  {
    doGet(req, resp);
  }

  public void init()
    throws ServletException
  {
    System.out.println("-----------------------------------");
    this.as.startup();
    this.ac.initAdapter();
    this.cs.start();
    try
    {
      TimeTaskServer.start();
    }
    catch (Exception e) {
      ABCBankImpl.log(Common.getExceptionTrace(e));
    }
  }
}
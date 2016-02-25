package gnnt.MEBS.timebargain.manage.util;

import gnnt.MEBS.timebargain.manage.service.BroadcastManager;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HisBroadcastServlet1
  extends HttpServlet
{
  public void destroy()
  {
    super.destroy();
  }
  
  public void doGet(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws ServletException, IOException
  {
    paramHttpServletResponse.setContentType("text/html");
    PrintWriter localPrintWriter = paramHttpServletResponse.getWriter();
    localPrintWriter.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
    localPrintWriter.println("<HTML>");
    localPrintWriter.println("  <HEAD><TITLE>A Servlet</TITLE></HEAD>");
    localPrintWriter.println("  <BODY>");
    localPrintWriter.print("    This is ");
    localPrintWriter.print(getClass());
    localPrintWriter.println(", using the GET method");
    localPrintWriter.println("  </BODY>");
    localPrintWriter.println("</HTML>");
    localPrintWriter.flush();
    localPrintWriter.close();
  }
  
  public void doPost(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws ServletException, IOException
  {
    paramHttpServletResponse.setContentType("text/html");
    PrintWriter localPrintWriter = paramHttpServletResponse.getWriter();
    localPrintWriter.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
    localPrintWriter.println("<HTML>");
    localPrintWriter.println("  <HEAD><TITLE>A Servlet</TITLE></HEAD>");
    localPrintWriter.println("  <BODY>");
    localPrintWriter.print("    This is ");
    localPrintWriter.print(getClass());
    localPrintWriter.println(", using the POST method");
    localPrintWriter.println("  </BODY>");
    localPrintWriter.println("</HTML>");
    localPrintWriter.flush();
    localPrintWriter.close();
  }
  
  public void init()
    throws ServletException
  {
    try
    {
      BroadcastManager localBroadcastManager = (BroadcastManager)SysData.getBean("broadcastManager");
      HisBroadcast_Thread localHisBroadcast_Thread = HisBroadcast_Thread.getInstance(localBroadcastManager);
      System.out.println("Succeed to load broadcastThread");
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      System.out.println("Fail to load broadcastThread.");
    }
  }
}

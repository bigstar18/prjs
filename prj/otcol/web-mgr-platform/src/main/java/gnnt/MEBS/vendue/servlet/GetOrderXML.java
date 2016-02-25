package gnnt.MEBS.vendue.servlet;

import gnnt.MEBS.member.ActiveUser.LogonManager;
import gnnt.MEBS.vendue.server.GlobalContainer;
import gnnt.MEBS.vendue.server.SubmitAction;
import java.io.BufferedOutputStream;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetOrderXML
  extends HttpServlet
{
  private static final long serialVersionUID = 1000L;
  
  public void destroy()
  {
    super.destroy();
  }
  
  public void doGet(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws ServletException, IOException
  {
    long l = -1000L;
    try
    {
      Long localLong = (Long)paramHttpServletRequest.getSession().getAttribute("LOGINID");
      if (localLong != null) {
        l = localLong.longValue();
      }
    }
    catch (Exception localException1)
    {
      localException1.printStackTrace();
    }
    LogonManager localLogonManager = LogonManager.getInstance();
    String str1 = localLogonManager.getUserID(l);
    String str2 = paramHttpServletRequest.getParameter("lastTime");
    int i = Integer.parseInt(paramHttpServletRequest.getParameter("partitionID"));
    String str3 = "";
    try
    {
      SubmitAction localSubmitAction = GlobalContainer.getSubmitAction(i);
      str3 = localSubmitAction.getLastXML(str1, str2);
    }
    catch (Exception localException2)
    {
      localException2.printStackTrace();
    }
    ServletOutputStream localServletOutputStream = paramHttpServletResponse.getOutputStream();
    paramHttpServletResponse.setHeader("Content-Type", "text/xml");
    BufferedOutputStream localBufferedOutputStream = null;
    localBufferedOutputStream = new BufferedOutputStream(localServletOutputStream);
    localBufferedOutputStream.write(str3.getBytes());
    localBufferedOutputStream.flush();
    localBufferedOutputStream.close();
  }
  
  public void doPost(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws ServletException, IOException
  {
    doGet(paramHttpServletRequest, paramHttpServletResponse);
  }
  
  public void init()
    throws ServletException
  {}
}

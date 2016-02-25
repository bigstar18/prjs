package gnnt.MEBS.vendue.servlet;

import gnnt.MEBS.vendue.server.bus.BroadcastService;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.sql.Timestamp;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GetBroadcastXML
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
    String str1 = paramHttpServletRequest.getParameter("lastTime").replace('/', ' ');
    String str2 = "";
    try
    {
      BroadcastService localBroadcastService = new BroadcastService();
      str2 = localBroadcastService.getLastXML(Timestamp.valueOf(str1));
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    ServletOutputStream localServletOutputStream = paramHttpServletResponse.getOutputStream();
    paramHttpServletResponse.setHeader("Content-Type", "text/xml");
    BufferedOutputStream localBufferedOutputStream = null;
    localBufferedOutputStream = new BufferedOutputStream(localServletOutputStream);
    localBufferedOutputStream.write(str2.getBytes());
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

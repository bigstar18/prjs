package gnnt.MEBS.vendue.servlet;

import gnnt.MEBS.vendue.kernel.ActiveUserManager;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.util.Calendar;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class RefreshSession
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
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    ActiveUserManager localActiveUserManager = new ActiveUserManager();
    String str1 = localActiveUserManager.getUserID(l);
    Calendar localCalendar = Calendar.getInstance();
    int i = localCalendar.get(11);
    int j = localCalendar.get(12);
    int k = localCalendar.get(13);
    String str2 = (i < 10 ? "0" + i : new StringBuilder().append("").append(i).toString()) + ":" + (j < 10 ? "0" + j : new StringBuilder().append("").append(j).toString()) + ":" + (k < 10 ? "0" + k : new StringBuilder().append("").append(k).toString());
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("<R>");
    localStringBuffer.append("<N>" + str1 + "</N>");
    localStringBuffer.append("<T>" + str2 + "</T>");
    localStringBuffer.append("</R>");
    ServletOutputStream localServletOutputStream = paramHttpServletResponse.getOutputStream();
    paramHttpServletResponse.setHeader("Content-Type", "text/xml");
    BufferedOutputStream localBufferedOutputStream = null;
    localBufferedOutputStream = new BufferedOutputStream(localServletOutputStream);
    localBufferedOutputStream.write(localStringBuffer.toString().getBytes());
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

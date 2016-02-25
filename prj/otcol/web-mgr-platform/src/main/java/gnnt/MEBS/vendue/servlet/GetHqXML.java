package gnnt.MEBS.vendue.servlet;

import gnnt.MEBS.vendue.server.bus.QuotationService;
import java.io.BufferedOutputStream;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GetHqXML
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
    long l1 = Long.parseLong(paramHttpServletRequest.getParameter("hqID"));
    long l2 = Long.parseLong(paramHttpServletRequest.getParameter("partitionID"));
    String str = "";
    QuotationService localQuotationService = new QuotationService();
    localQuotationService.setPartitionId(new Long(l2));
    str = localQuotationService.getLastXML(l1);
    ServletOutputStream localServletOutputStream = paramHttpServletResponse.getOutputStream();
    paramHttpServletResponse.setHeader("Content-Type", "text/xml");
    BufferedOutputStream localBufferedOutputStream = null;
    localBufferedOutputStream = new BufferedOutputStream(localServletOutputStream);
    localBufferedOutputStream.write(str.getBytes());
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

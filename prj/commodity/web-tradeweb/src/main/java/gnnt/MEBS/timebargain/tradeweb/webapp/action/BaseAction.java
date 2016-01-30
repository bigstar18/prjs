package gnnt.MEBS.timebargain.tradeweb.webapp.action;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class BaseAction
{
  protected final Log log = LogFactory.getLog(getClass());
  
  protected void rendText(HttpServletResponse paramHttpServletResponse, String paramString)
    throws IOException
  {
    paramHttpServletResponse.setCharacterEncoding("GBK");
    paramHttpServletResponse.getWriter().write(paramString);
  }
  
  protected void renderXML(HttpServletResponse paramHttpServletResponse, String paramString)
  {
    try
    {
      paramHttpServletResponse.setContentType("text/xml;charset=GBK");
      paramHttpServletResponse.getWriter().print(paramString);
    }
    catch (IOException localIOException)
    {
      this.log.error(localIOException.getMessage(), localIOException);
    }
  }
}

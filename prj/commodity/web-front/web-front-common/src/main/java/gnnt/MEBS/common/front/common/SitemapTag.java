package gnnt.MEBS.common.front.common;

import gnnt.MEBS.common.front.model.sitemap.ReadSiteMap;
import java.io.PrintStream;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagSupport;

public class SitemapTag
  extends TagSupport
{
  private static final long serialVersionUID = 1416560963438146973L;
  
  public int doStartTag()
  {
    try
    {
      JspWriter localJspWriter = this.pageContext.getOut();
      localJspWriter.print(getOutter());
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return 1;
  }
  
  public int doEndTag()
  {
    return 6;
  }
  
  public String getOutter()
  {
    String str1 = "";
    HttpServletRequest localHttpServletRequest = (HttpServletRequest)this.pageContext.getRequest();
    String str2 = localHttpServletRequest.getRequestURI().replaceFirst(localHttpServletRequest.getContextPath(), "");
    System.out.println("currentFilePath[" + str2 + "]");
    str1 = (String)ReadSiteMap.getInstance(localHttpServletRequest).getSiteMap().get(str2);
    return str1;
  }
}

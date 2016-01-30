package gnnt.MEBS.common.front.common;

import gnnt.MEBS.common.front.model.integrated.User;
import gnnt.MEBS.common.front.statictools.ApplicationContextInit;
import gnnt.MEBS.common.front.webFrame.securityCheck.UrlCheck;
import gnnt.MEBS.common.front.webFrame.securityCheck.UrlCheckResult;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagSupport;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class RightButtonTag
  extends TagSupport
{
  private static final long serialVersionUID = -3558314216634059969L;
  private final transient Log logger = LogFactory.getLog(RightButtonTag.class);
  private String className;
  private String onclick;
  private String id;
  private String name;
  private String action;
  
  public String getName()
  {
    return this.name;
  }
  
  public void setName(String paramString)
  {
    this.name = paramString;
  }
  
  public String getClassName()
  {
    return this.className;
  }
  
  public void setClassName(String paramString)
  {
    this.className = paramString;
  }
  
  public String getOnclick()
  {
    return this.onclick;
  }
  
  public void setOnclick(String paramString)
  {
    this.onclick = paramString;
  }
  
  public String getId()
  {
    return this.id;
  }
  
  public void setId(String paramString)
  {
    this.id = paramString;
  }
  
  public String getAction()
  {
    return this.action;
  }
  
  public void setAction(String paramString)
  {
    this.action = paramString;
  }
  
  public int doEndTag()
  {
    try
    {
      HttpServletRequest localHttpServletRequest = (HttpServletRequest)this.pageContext.getRequest();
      User localUser = (User)localHttpServletRequest.getSession().getAttribute("CurrentUser");
      UrlCheck localUrlCheck = (UrlCheck)ApplicationContextInit.getBean("urlCheck");
      UrlCheckResult localUrlCheckResult = localUrlCheck.check(this.action, localUser);
      int i = 0;
      switch (localUrlCheckResult)
      {
      case SUCCESS: 
      case NEEDLESSCHECKRIGHT: 
      case NEEDLESSCHECK: 
        i = 1;
        break;
      case USERISNULL: 
      case AUOVERTIME: 
      case AUUSERKICK: 
      case NOPURVIEW: 
      case NOMODULEPURVIEW: 
        i = 0;
        break;
      default: 
        i = 0;
      }
      if (i != 0)
      {
        JspWriter localJspWriter = this.pageContext.getOut();
        localJspWriter.println("<button class=\"" + this.className + "\" id=\"" + this.id + "\" action=\"" + this.action + "\" onclick=\"" + this.onclick + "\">" + this.name + "</button>");
      }
    }
    catch (IOException localIOException)
    {
      this.logger.error("rightButton occor Exception ;exception info " + localIOException.getMessage());
    }
    return 6;
  }
}

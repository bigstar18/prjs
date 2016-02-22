package gnnt.MEBS.buttonTags;

import java.io.IOException;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagSupport;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ButtonTag
  extends TagSupport
{
  private final transient Log logger = LogFactory.getLog(ButtonTag.class);
  private String className;
  private String onclick;
  private String id;
  private String name;
  
  public String getName()
  {
    return this.name;
  }
  
  public void setName(String name)
  {
    this.name = name;
  }
  
  public String getClassName()
  {
    return this.className;
  }
  
  public void setClassName(String className)
  {
    this.className = className;
  }
  
  public String getOnclick()
  {
    return this.onclick;
  }
  
  public void setOnclick(String onclick)
  {
    this.onclick = onclick;
  }
  
  public String getId()
  {
    return this.id;
  }
  
  public void setId(String id)
  {
    this.id = id;
  }
  
  public int doEndTag()
  {
    try
    {
      HttpServletRequest request = (HttpServletRequest)this.pageContext.getRequest();
      Map<String, Boolean> rightMap = null;
      if (request.getSession().getAttribute("rightMapReal") != null)
      {
        this.logger.debug("本地线程有权限集合");
        rightMap = (Map)request.getSession().getAttribute("rightMapReal");
      }
      boolean flag = false;
      boolean checkRightFlag = false;
      this.logger.debug("id:" + this.id);
      if (rightMap != null) {
        for (String key : rightMap.keySet())
        {
          this.logger.debug("key:" + key);
          if (this.id.indexOf(key) == 0)
          {
            checkRightFlag = true;
            if (((Boolean)rightMap.get(key)).booleanValue()) {
              flag = true;
            }
          }
        }
      }
      if (!checkRightFlag) {
        flag = true;
      }
      if ("".equals(this.id)) {
        flag = true;
      }
      if (flag)
      {
        JspWriter out = this.pageContext.getOut();
        out.println("<button class='" + this.className + "' id='" + this.id + "' onclick='" + this.onclick + "'>" + this.name + "</button>");
      }
    }
    catch (IOException localIOException) {}
    return 6;
  }
}

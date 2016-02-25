package gnnt.MEBS.vendue.manage.servlet;

import gnnt.MEBS.vendue.manage.querybean.LinkOracle;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspWriter;

public class AddRight
{
  public void doGet(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
  {
    paramHttpServletResponse.setContentType("text/html;charset=gbk");
    String str = paramHttpServletRequest.getParameter("accessNum");
    long l = 0L;
    if ((str != null) && (!"".equals(str))) {
      l = Long.parseLong(str);
    }
  }
  
  public void doAction(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse, long paramLong, String paramString1, int paramInt, String paramString2, JspWriter paramJspWriter)
  {
    try
    {
      LinkOracle localLinkOracle = new LinkOracle();
      localLinkOracle.JNDI = paramString2;
      String str1 = "";
      ArrayList localArrayList = new ArrayList();
      boolean bool = localLinkOracle.judgeChild(paramString1);
      if (bool)
      {
        for (int i = 0; i < paramInt; i++) {
          str1 = str1 + "&nbsp;";
        }
        localArrayList = localLinkOracle.queryChildTree(paramString1);
        for (i = 0; i < localArrayList.size(); i++)
        {
          Right localRight = (Right)localArrayList.get(i);
          long l = localRight.getMarknum();
          String str2 = localRight.getParentId();
          String str3 = localRight.getId();
          paramJspWriter.print(str1);
          if ((paramLong & l) > 0L) {
            paramJspWriter.print("<input type=\"checkbox\" id=\"r_" + str2 + "\" name=\"ck\" onclick=\"selectAll1('r_" + str3 + "')\"  value=\"" + l + "\" d_value=\"r_" + str3 + "\" checked>");
          } else {
            paramJspWriter.print("<input type=\"checkbox\" id=\"r_" + str2 + "\" name=\"ck\" onclick=\"selectAll1('r_" + str3 + "')\"  value=\"" + l + "\" d_value=\"r_" + str3 + "\">");
          }
          paramJspWriter.print(localRight.getDescription());
          paramJspWriter.print("<br>");
          doAction(paramHttpServletRequest, paramHttpServletResponse, paramLong, str3, paramInt + 4, paramString2, paramJspWriter);
        }
      }
      else
      {
        return;
      }
    }
    catch (IOException localIOException)
    {
      localIOException.printStackTrace();
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }
}

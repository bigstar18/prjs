package gnnt.MEBS.entity.action;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class BaseController
{
  public String getValueFromResult(Map paramMap)
  {
    String str = (String)paramMap.get("transResult");
    return str;
  }
  
  public String delNull(String paramString)
  {
    if (paramString == null) {
      return "";
    }
    return paramString;
  }
  
  public void setResultMsg(HttpServletRequest paramHttpServletRequest, String paramString)
  {
    paramHttpServletRequest.getSession().setAttribute("resultMsg", paramString);
  }
}

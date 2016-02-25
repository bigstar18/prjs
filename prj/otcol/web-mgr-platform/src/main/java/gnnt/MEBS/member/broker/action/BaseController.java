package gnnt.MEBS.member.broker.action;

import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

public class BaseController
  extends MultiActionController
{
  public static final String PATH = "member/broker/";
  public static final int PAGESIZE = 15;
  
  public String delNull(String paramString)
  {
    if (paramString == null) {
      paramString = "";
    }
    if ((paramString != null) && (paramString.equals("null"))) {
      paramString = "";
    }
    return paramString;
  }
}

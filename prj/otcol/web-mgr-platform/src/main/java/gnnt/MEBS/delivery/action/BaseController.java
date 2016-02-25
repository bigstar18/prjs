package gnnt.MEBS.delivery.action;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

public class BaseController
  extends MultiActionController
{
  public static String enterApplyCommand = "enterApply";
  public static String enterWareCommand = "enterWare";
  public static String enterInformCommand = "enterInform";
  public static String outWareCommand = "outWare";
  public static String resStockApplyCommand = "regStockApply";
  public static String resStockToEnterWareCommand = "regStockToEnterWare";
  public static String AttributeForCommodityList = "@_Commodity";
  
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
  
  public String getValueFromResult(Map paramMap)
  {
    String str = (String)paramMap.get("transResult");
    return str;
  }
  
  public void setResultMsg(HttpServletRequest paramHttpServletRequest, String paramString)
  {
    paramHttpServletRequest.getSession().setAttribute("resultMsg", paramString);
  }
}

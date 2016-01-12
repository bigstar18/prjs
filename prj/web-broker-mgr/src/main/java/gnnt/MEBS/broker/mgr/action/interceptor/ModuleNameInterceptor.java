package gnnt.MEBS.broker.mgr.action.interceptor;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import gnnt.MEBS.common.mgr.action.StandardAction;
import gnnt.MEBS.common.mgr.service.StandardService;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts2.ServletActionContext;

public class ModuleNameInterceptor extends AbstractInterceptor
{
  public String intercept(ActionInvocation paramActionInvocation)
    throws Exception
  {
    HttpServletRequest localHttpServletRequest = ServletActionContext.getRequest();
    String str1 = "select * from c_trademodule where moduleId = 15";
    StandardAction localStandardAction = (StandardAction)paramActionInvocation.getAction();
    StandardService localStandardService = localStandardAction.getService();
    List localList = localStandardService.getListBySql(str1);
    String str2 = String.valueOf(((Map)localList.get(0)).get("SHORTNAME"));
    localHttpServletRequest.setAttribute("shortName", str2);
    String str3 = paramActionInvocation.invoke();
    return str3;
  }
}
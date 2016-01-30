package gnnt.MEBS.timebargain.broker.action.interceptor;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import gnnt.MEBS.common.broker.service.StandardService;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class TariffListInterceptor extends AbstractInterceptor
{

  @Autowired
  @Qualifier("com_standardService")
  private StandardService standardService;
  public String status;

  public void setStandardService(StandardService paramStandardService)
  {
    this.standardService = paramStandardService;
  }

  public void setStatus(String paramString)
  {
    this.status = paramString;
  }

  public String intercept(ActionInvocation paramActionInvocation)
    throws Exception
  {
    String str1 = "select distinct TariffID,TariffName,TariffRate from T_A_tariff ";
    if (this.status != null)
      str1 = str1 + "where status=" + this.status;
    str1 = str1 + " order by TariffRate";
    List localList = this.standardService.getListBySql(str1);
    ActionContext localActionContext = paramActionInvocation.getInvocationContext();
    HttpServletRequest localHttpServletRequest = (HttpServletRequest)localActionContext.get("com.opensymphony.xwork2.dispatcher.HttpServletRequest");
    localHttpServletRequest.setAttribute("tariffList", localList);
    String str2 = localHttpServletRequest.getParameter("tariffID");
    localHttpServletRequest.setAttribute("tariffID", str2);
    String str3 = paramActionInvocation.invoke();
    return str3;
  }
}
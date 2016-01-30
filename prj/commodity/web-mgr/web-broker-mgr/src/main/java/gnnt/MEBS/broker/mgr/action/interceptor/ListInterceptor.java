package gnnt.MEBS.broker.mgr.action.interceptor;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import gnnt.MEBS.common.mgr.action.StandardAction;
import gnnt.MEBS.common.mgr.service.StandardService;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

public class ListInterceptor extends AbstractInterceptor
{
  private String fields;
  private String tableName;
  private String condition;

  public void setFields(String paramString)
  {
    this.fields = paramString;
  }

  public void setTableName(String paramString)
  {
    this.tableName = paramString;
  }

  public void setCondition(String paramString)
  {
    this.condition = paramString;
  }

  public String intercept(ActionInvocation paramActionInvocation)
    throws Exception
  {
    ActionContext localActionContext = paramActionInvocation.getInvocationContext();
    HttpServletRequest localHttpServletRequest = (HttpServletRequest)localActionContext.get("com.opensymphony.xwork2.dispatcher.HttpServletRequest");
    String str1 = "select " + this.fields + " from " + this.tableName + " where " + this.condition;
    StandardAction localStandardAction = (StandardAction)paramActionInvocation.getAction();
    StandardService localStandardService = localStandardAction.getService();
    List localList = localStandardService.getListBySql(str1);
    localHttpServletRequest.setAttribute("list", localList);
    String str2 = paramActionInvocation.invoke();
    return str2;
  }
}
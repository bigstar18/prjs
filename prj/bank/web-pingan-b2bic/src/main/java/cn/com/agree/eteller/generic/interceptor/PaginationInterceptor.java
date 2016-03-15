package cn.com.agree.eteller.generic.interceptor;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.struts2.ServletActionContext;

public class PaginationInterceptor
  extends AbstractInterceptor
{
  private static final long serialVersionUID = 1L;
  
  public String intercept(ActionInvocation invocation)
    throws Exception
  {
    Map<String, Object> params = invocation.getInvocationContext().getParameters();
    
    Object pageNum = params.get("pageNum");
    Object numPerPage = params.get("numPerPage");
    Object orderField = params.get("orderField");
    Object orderDirection = params.get("orderDirection");
    if (pageNum != null) {
      params.put("page.curPage", ((String[])pageNum)[0]);
    }
    String SYS_PERPAGE_RECORDS = (String)ServletActionContext.getRequest().getSession().getAttribute("SYS_PERPAGE_RECORDS");
    if (numPerPage != null)
    {
      params.put("page.perPageRecords", ((String[])numPerPage)[0]);
      ServletActionContext.getRequest().getSession().setAttribute("SYS_PERPAGE_RECORDS", ((String[])numPerPage)[0]);
    }
    if (orderField != null) {
      params.put("page.orderField", ((String[])orderField)[0]);
    }
    if (orderDirection != null) {
      params.put("page.orderDirection", ((String[])orderDirection)[0]);
    }
    return invocation.invoke();
  }
}

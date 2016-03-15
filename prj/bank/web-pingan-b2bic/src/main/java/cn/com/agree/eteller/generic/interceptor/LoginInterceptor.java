package cn.com.agree.eteller.generic.interceptor;

import cn.com.agree.eteller.generic.vo.LoginUser;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.struts2.ServletActionContext;

public class LoginInterceptor
  extends AbstractInterceptor
{
  private static final long serialVersionUID = 677089508318175138L;
  private HttpServletRequest req;
  private HttpSession session;
  
  public String intercept(ActionInvocation invocation)
    throws Exception
  {
    this.req = ServletActionContext.getRequest();
    this.session = this.req.getSession();
    

    LoginUser user = (LoginUser)this.session.getAttribute("user");
    if (user == null) {
      return "login";
    }
    user.setLoginTime(System.currentTimeMillis());
    

    String result = invocation.invoke();
    


    return result;
  }
}

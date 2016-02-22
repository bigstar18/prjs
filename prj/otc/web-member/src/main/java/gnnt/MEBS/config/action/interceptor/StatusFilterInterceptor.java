package gnnt.MEBS.config.action.interceptor;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import gnnt.MEBS.account.model.CompMember;
import gnnt.MEBS.account.model.MemberInfo;
import gnnt.MEBS.account.service.MemberInfoService;
import gnnt.MEBS.base.util.ThreadStore;
import gnnt.MEBS.config.constant.ActionConstant;
import gnnt.MEBS.config.constant.ThreadStoreConstant;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class StatusFilterInterceptor
  extends AbstractInterceptor
{
  private final transient Log logger = LogFactory.getLog(StatusFilterInterceptor.class);
  private String statusIn;
  @Autowired
  @Qualifier("memberInfoService")
  private MemberInfoService memberInfoService;
  
  public String getStatusIn()
  {
    return this.statusIn;
  }
  
  public void setStatusIn(String statusIn)
  {
    this.statusIn = statusIn;
  }
  
  public String intercept(ActionInvocation invocation)
    throws Exception
  {
    HttpServletRequest request = ServletActionContext.getRequest();
    HttpSession session = request.getSession();
    String memberNo = (String)session.getAttribute(ActionConstant.REGISTERID);
    MemberInfo eInfo = new MemberInfo();
    eInfo.setId(memberNo);
    MemberInfo memberInfo = (MemberInfo)this.memberInfoService.get(eInfo);
    String status = memberInfo.getCompMember().getStatus();
    String result = "";
    if (!this.statusIn.contains(status))
    {
      result = invocation.invoke();
    }
    else
    {
      request.getSession().setAttribute(ActionConstant.RESULTMSG, "此会员为非正常状态，被禁止进行此操作！");
      ThreadStore.put(ThreadStoreConstant.INACTION, "false");
      request.getSession().setAttribute(ActionConstant.RESULTVAULE, Integer.valueOf(-1));
      result = "statusError";
    }
    return result;
  }
}

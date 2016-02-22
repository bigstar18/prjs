package gnnt.MEBS.config.action.interceptor;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import gnnt.MEBS.account.model.SpecialMember;
import gnnt.MEBS.account.service.SpecialMemberService;
import gnnt.MEBS.config.constant.ActionConstant;
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
  @Qualifier("specialMemberService")
  private SpecialMemberService specialMemberService;
  
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
    String smemberNo = (String)session.getAttribute(ActionConstant.REGISTERID);
    SpecialMember eInfo = new SpecialMember();
    eInfo.setS_memberNo(smemberNo);
    SpecialMember memberInfo = (SpecialMember)this.specialMemberService.get(eInfo);
    String status = memberInfo.getStatus();
    this.logger.debug("StatusFilterInterceptor");
    this.logger.debug("要过滤的status：" + this.statusIn);
    this.logger.debug("特别会员状态为：" + status);
    String result = "";
    if (!this.statusIn.contains(status))
    {
      this.logger.debug("正常结束！");
      result = invocation.invoke();
    }
    else
    {
      this.logger.debug("错误信息");
      request.getSession().setAttribute(ActionConstant.RESULTMSG, "此特别会员为非正常状态，被禁止进行此操作！");
      request.getSession().setAttribute(ActionConstant.RESULTVAULE, Integer.valueOf(-1));
      result = "statusError";
    }
    return result;
  }
}

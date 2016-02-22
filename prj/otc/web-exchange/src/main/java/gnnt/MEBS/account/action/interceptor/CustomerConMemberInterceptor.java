package gnnt.MEBS.account.action.interceptor;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import gnnt.MEBS.account.model.MemberInfo;
import gnnt.MEBS.account.service.MemberInfoService;
import gnnt.MEBS.base.query.hibernate.PageInfo;
import gnnt.MEBS.base.query.hibernate.QueryConditions;
import gnnt.MEBS.config.constant.ActionConstant;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class CustomerConMemberInterceptor
  extends AbstractInterceptor
{
  private final transient Log logger = LogFactory.getLog(CustomerConMemberInterceptor.class);
  @Autowired
  @Qualifier("memberInfoService")
  private MemberInfoService memberInfoService;
  private String status;
  
  public void setStatus(String status)
  {
    this.status = status;
  }
  
  public String intercept(ActionInvocation invocation)
    throws Exception
  {
    this.logger.debug("enter   CustomerStatusQueryInterceptor");
    HttpServletRequest request = ServletActionContext.getRequest();
    QueryConditions qc = new QueryConditions();
    qc.addCondition("primary.compMember.status", "in", this.status);
    this.logger.debug("status:" + this.status);
    PageInfo pageInfo = new PageInfo(ActionConstant.PAGEINFONO, ActionConstant.PAGEINFOSIZE, "primary.id", false);
    List<MemberInfo> memberInfoList = this.memberInfoService.getList(qc, pageInfo);
    request.setAttribute("memberInfoList", memberInfoList);
    String result = invocation.invoke();
    return result;
  }
}

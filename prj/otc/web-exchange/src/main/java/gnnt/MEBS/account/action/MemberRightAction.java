package gnnt.MEBS.account.action;

import gnnt.MEBS.account.model.MemberRight;
import gnnt.MEBS.account.model.MemberUser;
import gnnt.MEBS.account.service.MemberRightService;
import gnnt.MEBS.account.service.MemberUserService;
import gnnt.MEBS.packaging.action.BaseAction;
import gnnt.MEBS.packaging.service.InService;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("request")
public class MemberRightAction
  extends BaseAction
{
  private final transient Log logger = LogFactory.getLog(MemberRightAction.class);
  @Autowired
  @Qualifier("memberUserService")
  private MemberUserService memberUserService;
  @Autowired
  @Qualifier("memberRightService")
  private InService rightService;
  private MemberRight treeRight;
  
  public MemberRight getTreeRight()
  {
    return this.treeRight;
  }
  
  public void setTreeRight(MemberRight treeRight)
  {
    this.treeRight = treeRight;
  }
  
  public InService getService()
  {
    return this.rightService;
  }
  
  public String commonRightUserList()
    throws Exception
  {
    this.logger.debug("//--[CommonRightController]--enter commonRightList()---//");
    MemberUser user = this.memberUserService.loadUserById(this.request.getParameter("userId"), true, true, true);
    this.treeRight = ((MemberRightService)this.rightService).getTreeRight();
    this.request.setAttribute("user", user);
    return "success";
  }
}

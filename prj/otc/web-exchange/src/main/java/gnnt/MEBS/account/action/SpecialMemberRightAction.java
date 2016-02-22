package gnnt.MEBS.account.action;

import gnnt.MEBS.account.model.SpecialMemberRight;
import gnnt.MEBS.account.model.SpecialMemberUser;
import gnnt.MEBS.account.service.SpecialMemberRightService;
import gnnt.MEBS.account.service.SpecialMemberUserService;
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
public class SpecialMemberRightAction
  extends BaseAction
{
  private final transient Log logger = LogFactory.getLog(SpecialMemberRightAction.class);
  @Autowired
  @Qualifier("specialMemberUserService")
  private SpecialMemberUserService memberUserService;
  @Autowired
  @Qualifier("specialMemberRightService")
  private InService rightService;
  private SpecialMemberRight treeRight;
  
  public SpecialMemberRight getTreeRight()
  {
    return this.treeRight;
  }
  
  public void setTreeRight(SpecialMemberRight treeRight)
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
    SpecialMemberUser user = this.memberUserService.loadUserById(this.request.getParameter("userId"), true, true, true);
    this.treeRight = ((SpecialMemberRightService)this.rightService).getTreeRight();
    this.request.setAttribute("user", user);
    return "success";
  }
}

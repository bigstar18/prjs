package gnnt.MEBS.account.action;

import gnnt.MEBS.account.model.MemberUser;
import gnnt.MEBS.account.service.MemberUserService;
import gnnt.MEBS.account.service.TraderService;
import gnnt.MEBS.base.query.hibernate.PageInfo;
import gnnt.MEBS.base.query.hibernate.QueryConditions;
import gnnt.MEBS.member.ActiveUser.MD5;
import gnnt.MEBS.packaging.action.BaseAction;
import gnnt.MEBS.packaging.action.util.EcsideUtil;
import gnnt.MEBS.packaging.service.InService;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component("memberUserAction")
@Scope("request")
public class MemberUserAction
  extends BaseAction
{
  private final transient Log logger = LogFactory.getLog(MemberUserAction.class);
  @Autowired
  @Qualifier("memberUserService")
  private MemberUserService userService;
  @Autowired
  @Qualifier("traderService")
  private TraderService traderService;
  @Resource(name="styleNameMap")
  Map<String, String> skinMap;
  private PageInfo pageInfo;
  private List userList;
  
  public InService getService()
  {
    return this.userService;
  }
  
  public List getUserList()
  {
    return this.userList;
  }
  
  public void setUserList(List userList)
  {
    this.userList = userList;
  }
  
  public PageInfo getPageInfo()
  {
    return this.pageInfo;
  }
  
  public void setPageInfo(PageInfo pageInfo)
  {
    this.pageInfo = pageInfo;
  }
  
  public String commonUserModPassword()
    throws Exception
  {
    this.logger.debug("//--[CommonUserController]--enter commonUserModPassword()---//");
    MemberUser modUser = (MemberUser)this.userService.get((MemberUser)this.obj);
    this.request.setAttribute("modUser", modUser);
    return "success";
  }
  
  public String commonUserModPasswordForward()
    throws Exception
  {
    this.logger.debug("//--[CommonUserController]--enter commonUserModPassword()---//");
    String newPassword = this.request.getParameter("obj.password");
    MemberUser user = (MemberUser)this.userService.get((MemberUser)this.obj);
    user.setPassword(MD5.getMD5(user.getUserId(), newPassword));
    this.userService.update(user);
    addResultMsg(this.request, 3);
    return getReturnValue();
  }
  
  public String list()
  {
    String sortName = this.request.getParameter("sortName") != null ? this.request.getParameter("sortName") : "id";
    String sortOrder = this.request.getParameter("sortOrder") != null ? this.request.getParameter("sortOrder") : "false";
    Map<String, Object> map = EcsideUtil.getQurey(this.request, sortName, new Boolean(sortOrder).booleanValue());
    PageInfo pageInfo = getPageInfo(map);
    QueryConditions qc = getQueryConditions(map);
    this.resultList = this.userService.getUserList(qc, pageInfo, false, true, false);
    EcsideUtil.setRowAttributes(this.request, pageInfo.getTotalRecords());
    returnBaseMsg(pageInfo);
    return getReturnValue();
  }
}

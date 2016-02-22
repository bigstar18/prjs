package gnnt.MEBS.account.action;

import gnnt.MEBS.account.model.SpecialMember;
import gnnt.MEBS.account.service.SpecialMemberService;
import gnnt.MEBS.base.query.hibernate.PageInfo;
import gnnt.MEBS.base.query.hibernate.QueryConditions;
import gnnt.MEBS.base.util.SpringContextHelper;
import gnnt.MEBS.common.model.Role;
import gnnt.MEBS.common.model.User;
import gnnt.MEBS.common.security.AclCtrl;
import gnnt.MEBS.common.service.RoleService;
import gnnt.MEBS.common.service.UserCommonService;
import gnnt.MEBS.common.service.UserService;
import gnnt.MEBS.config.constant.ActionConstant;
import gnnt.MEBS.config.constant.LogConstant;
import gnnt.MEBS.globalLog.model.OperateLog;
import gnnt.MEBS.globalLog.service.OperateLogService;
import gnnt.MEBS.member.ActiveUser.ActiveUserManager;
import gnnt.MEBS.member.ActiveUser.MD5;
import gnnt.MEBS.packaging.action.util.EcsideUtil;
import gnnt.MEBS.packaging.service.InService;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component("commonUserAction")
@Scope("request")
public class CommonUserMemAction
  extends CommonUserAction
{
  private final transient Log logger = LogFactory.getLog(CommonUserMemAction.class);
  @Autowired
  @Qualifier("userService")
  private UserCommonService userService;
  @Autowired
  @Qualifier("roleService")
  public RoleService roleService;
  @Autowired
  @Qualifier("globalLogService")
  public OperateLogService operateLogService;
  @Autowired
  @Qualifier("specialMemberService")
  private SpecialMemberService specialMemberService;
  @Resource(name="isForbidMap")
  Map<String, String> isForbidMap;
  @Resource(name="styleNameMap")
  Map<String, String> skinMap;
  private PageInfo pageInfo;
  private List userList;
  
  public InService getService()
  {
    return this.userService;
  }
  
  public Map<String, String> getIsForbidMap()
  {
    return this.isForbidMap;
  }
  
  public String commonUserLogon()
    throws Exception
  {
    this.logger.debug("//--[CommonUserController]--enter commonUserLogon()---//");
    























    User user = new User();
    user.setUserId(this.request.getParameter("username"));
    user.setPassword(this.request.getParameter("pwd"));
    this.logger.debug("userId:" + user.getUserId());
    
    List<User> userlist = this.userService.getList(new QueryConditions(
      "primary.userId", "=", this.request.getParameter("username")), null);
    if (userlist.size() != 0)
    {
      List<SpecialMember> memberlist = this.specialMemberService.getList(
        new QueryConditions("primary.s_memberNo", "=", 
        ((User)userlist.get(0)).getMemberNo()), null);
      if (!((SpecialMember)memberlist.get(0)).getStatus().equals("D"))
      {
        String randNumSys = (String)this.request.getSession().getAttribute(
          "RANDOMICITYNUM");
        String randNumInput = this.request.getParameter("randNumInput");
        String resultMsg = this.userService
          .authenticateUser(user, randNumSys, randNumInput);
        if ("default,gray".contains(resultMsg))
        {
          this.request.getSession().invalidate();
          ActiveUserManager au = new ActiveUserManager();
          long sessionId = au.logon(user.getUserId(), 
            this.request.getRemoteAddr());
          User u = this.userService.loadUserById(
            user.getUserId(), true, true, true);
          u.setSessionId(sessionId);
          this.request.getSession().setAttribute("LOGINIDS", 
            sessionId);
          this.request.getSession().setAttribute("CURRENUSER", u);
          this.request.getSession().setAttribute(
            ActionConstant.REGISTERID, 
            u.getMemberInfo().getS_memberNo());
          this.request.getSession().setAttribute("CURRENUSERID", 
            user.getUserId());
          this.request.getSession().setAttribute("CURRENUSERNAME", u.getName());
          this.request.getSession().setAttribute("skinstyle", resultMsg);
          this.request.getSession().setAttribute("useKey", "Y");
          boolean flag = false;
          for (Role role : u.getRoleSet()) {
            if ("DEFAULT_SUPER_ADMIN".equals(role.getType())) {
              flag = true;
            }
          }
          this.request.getSession().setAttribute("ISSUPERADMIN", Boolean.valueOf(flag));
          Map<String, Integer> sessionMap = (Map)SpringContextHelper.getBean("sessionMap");
          Integer sessionTime = (Integer)sessionMap.get("session");
          this.request.getSession().setMaxInactiveInterval(sessionTime.intValue() * 60);
          OperateLog operateLog = new OperateLog();
          operateLog.setOperator(u.getId());
          operateLog.setOperateDate(new Date());
          operateLog.setMark((String)this.request.getSession().getAttribute(
            ActionConstant.REGISTERID));
          operateLog.setOperateIp(this.request.getRemoteAddr());
          operateLog.setOperateContent("特别会员端" + u.getId() + "登录了！");
          operateLog.setOperateLogType(3000);
          operateLog.setOperatorType(LogConstant.OPERATORTYPE);
          this.operateLogService.add(operateLog);
          return "success";
        }
        OperateLog operateLog = new OperateLog();
        User u = this.userService.loadUserById(
          user.getUserId(), true, true, true);
        if (u != null) {
          operateLog.setMark(u.getMemberInfo().getS_memberNo());
        }
        String id = this.request.getParameter("username");
        operateLog.setOperator(id);
        operateLog.setOperateDate(new Date());
        operateLog.setOperateIp(this.request.getRemoteAddr());
        int num = resultMsg.indexOf(',');
        String resultStr = resultMsg.substring(0, num);
        operateLog.setOperateContent("特别会员端" + id + "由于" + resultStr + "而登录失败！");
        operateLog.setOperateLogType(3000);
        operateLog.setOperatorType(LogConstant.OPERATORTYPE);
        this.operateLogService.add(operateLog);
        this.request.getSession().setAttribute(ActionConstant.RESULTMSG, 
          resultMsg);
        this.request.getSession().setAttribute(
          ActionConstant.RESULTVAULE, Integer.valueOf(-1));
        this.request.setAttribute("name", 
          this.request.getParameter("username"));
        return "error";
      }
      this.request.getSession().setAttribute(ActionConstant.RESULTMSG, 
        "会员状态异常，无法登录！");
      this.request.getSession().setAttribute(ActionConstant.RESULTVAULE, 
        Integer.valueOf(-1));
      this.request.setAttribute("name", this.request.getParameter("username"));
      return "error";
    }
    this.request.getSession().setAttribute(ActionConstant.RESULTMSG, 
      "用户名不存在，无法登录！");
    this.request.getSession().setAttribute(ActionConstant.RESULTVAULE, Integer.valueOf(-1));
    this.request.setAttribute("name", this.request.getParameter("username"));
    return "error";
  }
  
  public String commonUserLogout()
    throws Exception
  {
    this.logger.debug("//--[CommonUserController]--enter commonUserLogout()---//");
    String sessionIdinStr = (String)this.request.getSession().getAttribute("LOGINIDS");
    if (sessionIdinStr == null) {
      sessionIdinStr = "-1";
    }
    long sessionId = Long.parseLong(sessionIdinStr);
    ActiveUserManager au = new ActiveUserManager();
    au.logoff(sessionId);
    User u = (User)this.request.getSession().getAttribute("CURRENUSER");
    OperateLog operateLog = new OperateLog();
    operateLog.setOperator(u.getId());
    operateLog.setOperateDate(new Date());
    operateLog.setOperateIp(this.request.getRemoteAddr());
    operateLog.setOperateContent("特别会员端" + u.getId() + "退出了！");
    operateLog.setOperateLogType(3000);
    operateLog.setOperatorType(LogConstant.OPERATORTYPE);
    this.operateLogService.add(operateLog);
    this.request.getSession().removeAttribute("LOGINIDS");
    this.request.getSession().invalidate();
    return "success";
  }
  
  public String relatedRightForward()
  {
    this.logger.debug("enter relatedRight");
    Map<String, Object> map = EcsideUtil.getQurey(this.request, "", new Boolean(false).booleanValue());
    QueryConditions qc = getQueryConditions(map);
    String userId = this.request.getParameter("userId");
    User user = this.userService.loadUserById(userId, false, true, false);
    String logonIdString = AclCtrl.getLogonID(this.request);
    User logonUser = this.userService.loadUserById(logonIdString, false, true, false);
    boolean flag = false;
    for (Role role : logonUser.getRoleSet()) {
      if (role.getType().equals(ActionConstant.DEF_SUPERADMIN)) {
        flag = true;
      }
    }
    if (!flag)
    {
      this.request.setAttribute("roleList", logonUser.getOperateRoleSet());
    }
    else
    {
      List<Role> roleList = this.roleService.getList(qc, null);
      this.request.setAttribute("roleList", roleList);
    }
    this.request.setAttribute("ISSUPTERADMIN", this.request.getSession().getAttribute("ISSUPERADMIN"));
    this.request.setAttribute("user", user);
    return getReturnValue();
  }
  
  public String relatedRight()
  {
    this.logger.debug("enter relatedRight");
    String userId = this.request.getParameter("userId");
    Map<String, Object> map = EcsideUtil.getQurey(this.request, "", new Boolean(false).booleanValue());
    QueryConditions qc = getQueryConditions(map);
    String[] cksString = this.request.getParameterValues("ck");
    String[] operateCks = this.request.getParameterValues("operateCk");
    List<List<String>> inStrings = this.userService.getRelatedIds(userId, AclCtrl.getLogonID(this.request), cksString, qc, operateCks);
    int value = this.userService.relatedRight(userId, AclCtrl.getLogonID(this.request), inStrings, (String)this.request.getSession().getAttribute(ActionConstant.REGISTERID));
    addResultMsg(this.request, value);
    return getReturnValue();
  }
  
  public String add()
  {
    this.logger.debug("enter add");
    User user = (User)this.obj;
    

    user.setPassword(MD5.getMD5(user.getId(), user.getPassword()));
    
    int resultValue = getService().add(user);
    addResultMsg(this.request, resultValue);
    return getReturnValue();
  }
}

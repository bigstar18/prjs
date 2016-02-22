package gnnt.MEBS.account.action;

import gnnt.MEBS.base.model.Clone;
import gnnt.MEBS.base.query.hibernate.PageInfo;
import gnnt.MEBS.base.query.hibernate.QueryConditions;
import gnnt.MEBS.base.util.SpringContextHelper;
import gnnt.MEBS.base.util.ThreadStore;
import gnnt.MEBS.common.model.OnLineUser;
import gnnt.MEBS.common.model.Role;
import gnnt.MEBS.common.model.User;
import gnnt.MEBS.common.security.AclCtrl;
import gnnt.MEBS.common.security.util.IntegrateToMap;
import gnnt.MEBS.common.service.RoleService;
import gnnt.MEBS.common.service.UserService;
import gnnt.MEBS.config.constant.ActionConstant;
import gnnt.MEBS.config.constant.LogConstant;
import gnnt.MEBS.config.constant.ThreadStoreConstant;
import gnnt.MEBS.globalLog.model.OperateLog;
import gnnt.MEBS.globalLog.service.OperateLogService;
import gnnt.MEBS.member.ActiveUser.ActiveUserManager;
import gnnt.MEBS.member.ActiveUser.MD5;
import gnnt.MEBS.packaging.action.BaseAction;
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

@Component
@Scope("request")
public class CommonUserAction
  extends BaseAction
{
  private final transient Log logger = LogFactory.getLog(CommonUserAction.class);
  @Autowired
  @Qualifier("userService")
  private UserService userService;
  @Autowired
  @Qualifier("roleService")
  public RoleService roleService;
  @Autowired
  @Qualifier("globalLogService")
  public OperateLogService operateLogService;
  @Resource(name="styleNameMap")
  Map<String, String> skinMap;
  private PageInfo pageInfo;
  private List userList;
  @Resource(name="isForbidMap")
  Map<String, String> isForbidMap;
  
  public InService getService()
  {
    return this.userService;
  }
  
  public Map<String, String> getIsForbidMap()
  {
    return this.isForbidMap;
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
  
  public String commonUserLogon()
    throws Exception
  {
    this.logger.debug("//--[CommonUserController]--enter commonUserLogon()---//");
    



















    User user = new User();
    user.setUserId(this.request.getParameter("username"));
    user.setPassword(this.request.getParameter("pwd"));
    user.setKeyCode(this.request.getParameter("kcode"));
    String randNumSys = (String)this.request.getSession().getAttribute("RANDOMICITYNUM");
    String randNumInput = this.request.getParameter("randNumInput");
    String resultMsg = this.userService.authenticateUser(user, randNumSys, randNumInput);
    if ("default,gray".contains(resultMsg))
    {
      this.request.getSession().invalidate();
      ActiveUserManager au = new ActiveUserManager();
      long sessionId = au.logon(user.getUserId(), this.request.getRemoteAddr());
      User u = this.userService.loadUserById(user.getUserId(), true, true, true);
      boolean flag = false;
      for (Role role : u.getRoleSet()) {
        if ("DEFAULT_SUPER_ADMIN".equals(role.getType())) {
          flag = true;
        }
      }
      this.request.getSession().setAttribute("ISSUPERADMIN", Boolean.valueOf(flag));
      u.setSessionId(sessionId);
      this.request.getSession().setAttribute("LOGINIDS", sessionId);
      this.request.getSession().setAttribute("CURRENUSER", u);
      this.request.getSession().setAttribute("CURRENUSERNAME", u.getName());
      this.request.getSession().setAttribute("CURRENUSERID", user.getUserId());
      this.request.getSession().setAttribute("skinstyle", resultMsg);
      this.request.getSession().setAttribute("useKey", "Y");
      Map<String, Integer> sessionMap = (Map)SpringContextHelper.getBean("sessionMap");
      Integer sessionTime = (Integer)sessionMap.get("session");
      this.request.getSession().setMaxInactiveInterval(sessionTime.intValue() * 60);
      OperateLog operateLog = new OperateLog();
      operateLog.setOperator(u.getId());
      operateLog.setOperateDate(new Date());
      operateLog.setOperateIp(this.request.getRemoteAddr());
      operateLog.setOperateContent("交易所端" + u.getId() + "登录了！");
      operateLog.setOperateLogType(3000);
      operateLog.setOperatorType(LogConstant.OPERATORTYPE);
      this.operateLogService.add(operateLog);
      return "success";
    }
    OperateLog operateLog = new OperateLog();
    String id = this.request.getParameter("username");
    operateLog.setOperator(id);
    operateLog.setOperateDate(new Date());
    operateLog.setOperateIp(this.request.getRemoteAddr());
    int num = resultMsg.indexOf(',');
    String resultStr = resultMsg.substring(0, num);
    operateLog.setOperateContent("交易所端" + id + "由于" + resultStr + "而登录失败！");
    operateLog.setOperateLogType(3000);
    operateLog.setOperatorType(LogConstant.OPERATORTYPE);
    this.operateLogService.add(operateLog);
    this.request.getSession().setAttribute(ActionConstant.RESULTMSG, resultMsg);
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
    operateLog.setOperateContent("交易所端" + u.getId() + "退出了！");
    operateLog.setOperateLogType(3000);
    operateLog.setOperatorType(LogConstant.OPERATORTYPE);
    this.operateLogService.add(operateLog);
    this.request.getSession().removeAttribute("LOGINIDS");
    this.request.getSession().invalidate();
    return "success";
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
  
  public String delete()
  {
    this.logger.debug("//--[CommonUserController]--enter commonUserDelete()---//");
    String[] userIds = this.request.getParameterValues("ids");
    int resultValue = 1;
    boolean flag = false;
    String str1;
    String userId;
    if ((userIds != null) && (userIds.length > 0))
    {
      String logonIdString = AclCtrl.getLogonID(this.request);
      String[] arrayOfString2;
      int i = (arrayOfString2 = userIds).length;
      for (str1 = 0; str1 < i; str1++)
      {
        userId = arrayOfString2[str1];
        if (userId.equals(logonIdString))
        {
          flag = true;
          resultValue = -10;
        }
      }
    }
    if ((userIds != null) && (userIds.length > 0) && (!flag))
    {
      String[] arrayOfString1;
      str1 = (arrayOfString1 = userIds).length;
      for (userId = 0; userId < str1; userId++)
      {
        String userId = arrayOfString1[userId];
        String[] userIdString = userId.split(",");
        Clone obj = getService().getById(userIdString[0]);
        OperateLog operateLog = new OperateLog();
        operateLog.setObj(obj);
        this.logger.debug("enter delete operateLog:" + obj);
        operateLog.setOperator(AclCtrl.getLogonID(this.request));
        operateLog.setMark((String)this.request.getSession().getAttribute(ActionConstant.REGISTERID));
        ThreadStore.put(ThreadStoreConstant.OPERATELOGFORLOG, operateLog);
        this.userService.delete((User)obj);
      }
    }
    addResultSessionMsg(this.request, resultValue);
    return getReturnValue();
  }
  
  public String commonUserOnLineList()
  {
    this.logger.debug("//--[CommonUserController]--enter commonUserOnLineList()---//");
    String userId = this.request.getParameter("userId");
    ActiveUserManager au = new ActiveUserManager();
    String[] allUserMessages = au.getAllUsersSys(userId);
    this.logger.debug(Integer.valueOf(allUserMessages.length));
    Map<Integer, List<OnLineUser>> onLineUsersMap = IntegrateToMap.transforArrayToMap(allUserMessages);
    this.request.setAttribute("onLineUsersMap", onLineUsersMap);
    if (userId != null) {
      this.request.setAttribute("userId", userId);
    }
    return "success";
  }
  
  public String commonUserForcedOffline()
  {
    this.logger.debug("//--[CommonUserController]--enter commonUserForcedOffline()---//");
    String[] cks = this.request.getParameterValues("ck");
    if ((cks != null) && (cks.length > 0)) {
      for (int i = 0; i < cks.length; i++) {
        new ActiveUserManager().logoff(Long.parseLong(cks[i]));
      }
    }
    return "success";
  }
  
  public String update()
  {
    this.logger.debug("enter update");
    User user = (User)this.obj;
    int resultValue = getService().update(this.obj);
    addResultMsg(this.request, resultValue);
    return getReturnValue();
  }
  
  public String add()
  {
    this.logger.debug("enter add");
    User user = (User)this.obj;
    user.setPassword(MD5.getMD5(user.getId(), user.getPassword()));
    int resultValue = getService().add(this.obj);
    addResultMsg(this.request, resultValue);
    return getReturnValue();
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
    this.request.setAttribute("user", user);
    this.request.setAttribute("ISSUPTERADMIN", this.request.getSession().getAttribute("ISSUPERADMIN"));
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
}

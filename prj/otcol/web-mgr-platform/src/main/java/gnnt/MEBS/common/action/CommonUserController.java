package gnnt.MEBS.common.action;

import gnnt.MEBS.base.copy.ParamUtil;
import gnnt.MEBS.common.model.User;
import gnnt.MEBS.common.security.util.IntegrateToMap;
import gnnt.MEBS.common.services.UserService;
import gnnt.MEBS.common.util.SysData;
import gnnt.MEBS.common.util.query.PageInfo;
import gnnt.MEBS.common.util.query.QueryConditions;
import gnnt.MEBS.common.util.query.QueryHelper;
import gnnt.MEBS.member.ActiveUser.ActiveUserManager;
import gnnt.MEBS.member.ActiveUser.MD5;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.WebUtils;

public class CommonUserController
  extends BaseController
{
  private final transient Log logger = LogFactory.getLog(CommonUserController.class);
  
  public ModelAndView commonUserLogon(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    this.logger.debug("//--[CommonUserController]--enter commonUserLogon()---//");
    String str1 = (String)paramHttpServletRequest.getSession().getAttribute("LOGINIDS");
    if (str1 == null) {
      str1 = "-1";
    }
    long l1 = Long.parseLong(str1);
    ActiveUserManager localActiveUserManager1 = new ActiveUserManager();
    localActiveUserManager1.logoff(l1);
    paramHttpServletRequest.getSession().removeAttribute("LOGINIDS");
    String str2 = paramHttpServletRequest.getParameter("kcode");
    User localUser1 = new User();
    localUser1.setUserId(paramHttpServletRequest.getParameter("username"));
    localUser1.setPassword(paramHttpServletRequest.getParameter("pwd"));
    localUser1.setKeyCode(str2);
    String str3 = (String)paramHttpServletRequest.getSession().getAttribute("RANDOMICITYNUM");
    String str4 = paramHttpServletRequest.getParameter("randNumInput");
    UserService localUserService = (UserService)SysData.getBean("userService");
    String str5 = localUserService.authenticateUser(localUser1, str3, str4);
    paramHttpServletRequest.getSession().removeAttribute("RANDOMICITYNUM");
    String str6 = "登陆后台管理,登陆主机为:" + paramHttpServletRequest.getRemoteAddr();
    ModelAndView localModelAndView = null;
    if ("default,gray".contains(str5))
    {
      localModelAndView = new ModelAndView("redirect:/common/surface/index.jsp");
      ActiveUserManager localActiveUserManager2 = new ActiveUserManager();
      long l2 = localActiveUserManager2.logon(localUser1.getUserId(), paramHttpServletRequest.getRemoteAddr());
      User localUser2 = localUserService.loadUserById(localUser1.getUserId(), true, true, true);
      localUser2.setSessionId(l2);
      paramHttpServletRequest.getSession().setAttribute("LOGINIDS", l2 + "");
      paramHttpServletRequest.getSession().setAttribute("CURRENUSER", localUser2);
      paramHttpServletRequest.getSession().setAttribute("CURRENUSERID", localUser1.getUserId());
      paramHttpServletRequest.getSession().setAttribute("skinstyle", str5);
      paramHttpServletRequest.getSession().setAttribute("useKey", "Y");
      addSysLog(paramHttpServletRequest, "04", str6);
    }
    else
    {
      localModelAndView = new ModelAndView("logon", "resultMsg", str5);
      localModelAndView.addObject("user", localUser1);
    }
    return localModelAndView;
  }
  
  public ModelAndView commonUserLogout(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    this.logger.debug("//--[CommonUserController]--enter commonUserLogout()---//");
    String str1 = (String)paramHttpServletRequest.getSession().getAttribute("LOGINIDS");
    String str2 = "退出后台管理,登陆主机为:" + paramHttpServletRequest.getRemoteAddr();
    if (str1 == null) {
      str1 = "-1";
    }
    addSysLog(paramHttpServletRequest, "04", str2);
    long l = Long.parseLong(str1);
    ActiveUserManager localActiveUserManager = new ActiveUserManager();
    localActiveUserManager.logoff(l);
    paramHttpServletRequest.getSession().removeAttribute("LOGINIDS");
    paramHttpServletRequest.getSession().invalidate();
    return new ModelAndView("logon");
  }
  
  public ModelAndView commonUserModStyle(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    this.logger.debug("//--[CommonUserController]--enter commonUserModStyle()---//");
    Map localMap = (Map)SysData.getBean("styleNameMap");
    UserService localUserService = (UserService)SysData.getBean("userService");
    String str1 = (String)paramHttpServletRequest.getSession().getAttribute("CURRENUSERID");
    User localUser = localUserService.getUserById(str1);
    ModelAndView localModelAndView = new ModelAndView("common/users/modSkinStyle");
    localModelAndView.addObject("user", localUser);
    localModelAndView.addObject("skinMap", localMap);
    String str2 = paramHttpServletRequest.getParameter("modMark");
    if ("mod".equals(str2))
    {
      String str3 = paramHttpServletRequest.getParameter("skinStyle");
      localUser.setSkin(str3);
      localUserService.updateUser(localUser);
      paramHttpServletRequest.getSession().setAttribute("skinstyle", str3);
      localModelAndView.addObject("resultMsg", "更换应用界面风格成功!");
      localModelAndView.addObject("modSuccess", "modSuccess!");
    }
    return localModelAndView;
  }
  
  public ModelAndView commonUserAdd(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    this.logger.debug("//--[CommonUserController]--enter commonUserAdd()---//");
    Map localMap = (Map)SysData.getBean("styleNameMap");
    ModelAndView localModelAndView = new ModelAndView("common/users/addUser");
    localModelAndView.addObject("skinMap", localMap);
    return localModelAndView;
  }
  
  public ModelAndView commonUserAddForward(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    this.logger.debug("//--[CommonUserController]--enter commonUserAddForward()---//");
    User localUser = new User();
    ParamUtil.bindData(paramHttpServletRequest, localUser);
    if ((localUser.getSkin() == null) || (localUser.getSkin().equals(""))) {
      localUser.setSkin("default");
    }
    UserService localUserService = (UserService)SysData.getBean("userService");
    boolean bool = localUserService.addUser(localUser);
    ModelAndView localModelAndView = new ModelAndView("common/users/addUser");
    localModelAndView.addObject("resultMsg", bool ? "添加新用户成功!" : "用户代码已存在!");
    localModelAndView.addObject("addSuccess", "addSuccess");
    return localModelAndView;
  }
  
  public ModelAndView commonUserModPasswordForward(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    this.logger.debug("//--[CommonUserController]--enter commonUserModPasswordForward()---//");
    UserService localUserService = (UserService)SysData.getBean("userService");
    String str1 = paramHttpServletRequest.getParameter("userId");
    String str2 = paramHttpServletRequest.getParameter("sign");
    if (str1 == null) {
      str1 = (String)paramHttpServletRequest.getSession().getAttribute("CURRENUSERID");
    }
    User localUser = localUserService.getUserById(str1);
    ModelAndView localModelAndView = null;
    if (str2.equals("old")) {
      localModelAndView = new ModelAndView("common/users/modOldPassword");
    } else if (str2.equals("new")) {
      localModelAndView = new ModelAndView("common/users/modPassword");
    }
    localModelAndView.addObject("modUser", localUser);
    return localModelAndView;
  }
  
  public ModelAndView commonUserModPassword(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    this.logger.debug("//--[CommonUserController]--enter commonUserModPassword()---//");
    UserService localUserService = (UserService)SysData.getBean("userService");
    String str1 = paramHttpServletRequest.getParameter("userId");
    String str2 = paramHttpServletRequest.getParameter("oldpass");
    String str3 = paramHttpServletRequest.getParameter("password");
    String str4 = paramHttpServletRequest.getParameter("sign");
    str3 = MD5.getMD5(str1, str3);
    String str5 = "";
    ModelAndView localModelAndView = null;
    if (str4.equals("old"))
    {
      str2 = MD5.getMD5(str1, str2);
      int i = localUserService.modPassword(str1, str2, str3);
      if (i > 0) {
        str5 = "密码修改成功!";
      } else {
        str5 = "原密码错误！";
      }
      localModelAndView = new ModelAndView("common/users/modOldPassword");
    }
    else if (str4.equals("new"))
    {
      User localUser = localUserService.getUserById(str1);
      localUser.setPassword(str3);
      localUserService.updateUser(localUser);
      str5 = "密码修改成功!";
      localModelAndView = new ModelAndView("common/users/modPassword");
    }
    localModelAndView.addObject("resultMsg", str5);
    localModelAndView.addObject("modSuccess", "modSuccess");
    return localModelAndView;
  }
  
  public ModelAndView commonUserView(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    this.logger.debug("//--[CommonUserController]--enter commonUserView()---//");
    String str = paramHttpServletRequest.getParameter("userId");
    UserService localUserService = (UserService)SysData.getBean("userService");
    User localUser = localUserService.getUserById(str);
    ModelAndView localModelAndView = new ModelAndView("common/users/modUser");
    localModelAndView.addObject("user", localUser);
    return localModelAndView;
  }
  
  public ModelAndView commonUserModForward(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    this.logger.debug("//--[CommonUserController]--enter commonUserModForward()---//");
    String str = paramHttpServletRequest.getParameter("userId");
    UserService localUserService = (UserService)SysData.getBean("userService");
    User localUser = localUserService.getUserById(str);
    localUser.setPassword(paramHttpServletRequest.getParameter("password"));
    localUser.setName(paramHttpServletRequest.getParameter("name"));
    localUser.setSkin(paramHttpServletRequest.getParameter("skin"));
    localUser.setDescription(paramHttpServletRequest.getParameter("description"));
    localUser.setKeyCode(paramHttpServletRequest.getParameter("keyCode"));
    localUserService.updateUser(localUser);
    ModelAndView localModelAndView = new ModelAndView("common/users/modUser");
    localModelAndView.addObject("resultMsg", "修改管理员信息成功!");
    return localModelAndView;
  }
  
  public ModelAndView commonUserList(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    this.logger.debug("//--[CommonUserController]--enter commonUserList()---//");
    QueryConditions localQueryConditions = QueryHelper.getQueryConditionsFromRequest(paramHttpServletRequest);
    PageInfo localPageInfo = QueryHelper.getPageInfoFromRequest(paramHttpServletRequest);
    if (localPageInfo == null) {
      localPageInfo = new PageInfo(1, 20, "u.userId", false);
    }
    UserService localUserService = (UserService)SysData.getBean("userService");
    List localList = localUserService.getUserList(localQueryConditions, localPageInfo, false, true, false);
    this.logger.debug("userList:" + localList.size());
    ModelAndView localModelAndView = new ModelAndView("common/users/usersList");
    Map localMap = WebUtils.getParametersStartingWith(paramHttpServletRequest, "_");
    localModelAndView.addObject("pageInfo", localPageInfo);
    localModelAndView.addObject("oldParams", localMap);
    localModelAndView.addObject("userList", localList);
    return localModelAndView;
  }
  
  public ModelAndView commonUserReturn(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    this.logger.debug("//--[CommonUserController]--enter commonUserReturn()---//");
    return returnUsersList(paramHttpServletRequest, paramHttpServletResponse);
  }
  
  public ModelAndView commonUserDirect(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    this.logger.debug("//--[CommonUserController]--enter commonUserDirect()---//");
    return commonUserList(paramHttpServletRequest, paramHttpServletResponse);
  }
  
  public ModelAndView commonUserDelete(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    this.logger.debug("//--[CommonUserController]--enter commonUserDelete()---//");
    String[] arrayOfString = paramHttpServletRequest.getParameterValues("ck");
    UserService localUserService = (UserService)SysData.getBean("userService");
    localUserService.deleteUsers(arrayOfString);
    ModelAndView localModelAndView = commonUserList(paramHttpServletRequest, paramHttpServletResponse);
    localModelAndView.addObject("resultMsg", "删除成功!");
    return localModelAndView;
  }
  
  public ModelAndView returnUsersList(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
  {
    this.logger.debug("//--[CommonUserController]--enter returnUsersList()---//");
    return getModelAndView("usersList");
  }
  
  public ModelAndView commonUserOnLineDirect(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
  {
    this.logger.debug("//--[CommonUserController]--enter commonUserOnLineDirect()---//");
    return commonUserOnLineList(paramHttpServletRequest, paramHttpServletResponse);
  }
  
  public ModelAndView commonUserOnLineList(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
  {
    this.logger.debug("//--[CommonUserController]--enter commonUserOnLineList()---//");
    String str = paramHttpServletRequest.getParameter("userId[like]");
    ActiveUserManager localActiveUserManager = new ActiveUserManager();
    String[] arrayOfString = localActiveUserManager.getAllUsersSys(str);
    this.logger.debug(Integer.valueOf(arrayOfString.length));
    Map localMap = IntegrateToMap.transforArrayToMap(arrayOfString);
    ModelAndView localModelAndView = new ModelAndView("common/users/onLineUsers");
    localModelAndView.addObject("onLineUsersMap", localMap);
    if (str != null) {
      localModelAndView.addObject("userId", str);
    }
    return localModelAndView;
  }
  
  public ModelAndView commonUserForcedOffline(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
  {
    this.logger.debug("//--[CommonUserController]--enter commonUserForcedOffline()---//");
    String[] arrayOfString = paramHttpServletRequest.getParameterValues("ck");
    if ((arrayOfString != null) && (arrayOfString.length > 0)) {
      for (int i = 0; i < arrayOfString.length; i++) {
        new ActiveUserManager().logoff(Long.parseLong(arrayOfString[i]));
      }
    }
    return commonUserOnLineList(paramHttpServletRequest, paramHttpServletResponse);
  }
}

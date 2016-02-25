package gnnt.MEBS.delivery.action;

import gnnt.MEBS.base.query.PageInfo;
import gnnt.MEBS.base.query.QueryConditions;
import gnnt.MEBS.base.query.QueryHelper;
import gnnt.MEBS.delivery.model.User;
import gnnt.MEBS.delivery.services.UserService;
import gnnt.MEBS.delivery.util.SysData;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;

public class UserController
  extends BaseController
{
  private final transient Log logger = LogFactory.getLog(UserController.class);
  
  public ModelAndView userList(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    ModelAndView localModelAndView = new ModelAndView(Condition.PATH + "martModule/user/usersList");
    try
    {
      UserService localUserService = (UserService)SysData.getBean("w_userService");
      QueryConditions localQueryConditions = QueryHelper.getQueryConditionsFromRequest(paramHttpServletRequest);
      PageInfo localPageInfo = QueryHelper.getPageInfoFromRequest(paramHttpServletRequest);
      if (localPageInfo == null) {
        localPageInfo = new PageInfo(1, Condition.PAGESIZE, "userId", false);
      }
      Map localMap = QueryHelper.getMapFromRequest(paramHttpServletRequest);
      List localList = localUserService.queryUsers(localQueryConditions, localPageInfo);
      this.logger.debug("loglist.size:" + localList.size());
      localModelAndView.addObject("resultList", localList);
      localModelAndView.addObject("pageInfo", localPageInfo);
      localModelAndView.addObject("oldParams", localMap);
    }
    catch (Exception localException)
    {
      localModelAndView.addObject("resultMsg", "操作异常！");
      localException.printStackTrace();
    }
    return localModelAndView;
  }
  
  public ModelAndView userView(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    String str1 = paramHttpServletRequest.getParameter("userId");
    String str2 = paramHttpServletRequest.getParameter("warehouseId");
    UserService localUserService = (UserService)SysData.getBean("w_userService");
    User localUser = localUserService.getUser(str1, str2);
    ModelAndView localModelAndView = new ModelAndView(Condition.PATH + "martModule/user/viewUser", "user", localUser);
    return localModelAndView;
  }
  
  public ModelAndView userPwdForward(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    this.logger.debug("entering 'userPwdForward' method...");
    String str1 = paramHttpServletRequest.getParameter("userId");
    String str2 = paramHttpServletRequest.getParameter("warehouseId");
    ModelAndView localModelAndView = new ModelAndView(Condition.PATH + "martModule/user/enitUserPwd");
    localModelAndView.addObject("userId", str1);
    localModelAndView.addObject("warehouseId", str2);
    return localModelAndView;
  }
  
  public ModelAndView editUserPwd(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    this.logger.debug("entering 'editUserPwd' method...");
    String str1 = paramHttpServletRequest.getParameter("userId");
    String str2 = paramHttpServletRequest.getParameter("warehouseId");
    String str3 = paramHttpServletRequest.getParameter("password");
    UserService localUserService = (UserService)SysData.getBean("w_userService");
    int i = 0;
    try
    {
      i = localUserService.changPwd(str1, str2, str3);
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      i = -2;
    }
    String str4 = "";
    if (i == 1) {
      str4 = "修改成功！";
    } else if (i == -1) {
      str4 = "修改失败！";
    } else {
      str4 = "系统异常";
    }
    this.logger.debug("msg:" + str4);
    setResultMsg(paramHttpServletRequest, str4);
    ModelAndView localModelAndView = new ModelAndView("redirect:" + Condition.PATH + "servlet/userController." + Condition.POSTFIX + "?funcflg=userPwdForward");
    return localModelAndView;
  }
  
  public ModelAndView userReturn(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    return new ModelAndView("redirect:" + Condition.PATH + "servlet/userController." + Condition.POSTFIX + "?funcflg=userList");
  }
}

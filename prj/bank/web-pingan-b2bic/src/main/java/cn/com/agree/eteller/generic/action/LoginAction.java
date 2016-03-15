package cn.com.agree.eteller.generic.action;

import cn.com.agree.eteller.generic.utils.Base64Decoder;
import cn.com.agree.eteller.generic.utils.CookieUtil;
import cn.com.agree.eteller.generic.utils.TellerUtil;
import cn.com.agree.eteller.generic.utils.UserContext;
import cn.com.agree.eteller.generic.vo.CommonDataNode;
import cn.com.agree.eteller.generic.vo.DataTree;
import cn.com.agree.eteller.generic.vo.DwzResponse;
import cn.com.agree.eteller.generic.vo.LoginUser;
import cn.com.agree.eteller.usermanager.persistence.Userlist;
import cn.com.agree.eteller.usermanager.spring.IUserManager;
import java.net.URLDecoder;
import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

@Controller
@Scope("prototype")
@ParentPackage("eteller-default")
@Namespace("/")
public class LoginAction
  extends GenericAction
{
  private static final long serialVersionUID = 1L;
  @Resource(name="userManagerTarget")
  private IUserManager userMg;
  private LoginUser user;
  private String logout;
  private CommonDataNode funcList;
  private int getFuncCount = 0;
  
  public void validateLogin()
  {
    if (this.user == null)
    {
      addActionError("登录失败");
      this.req.setAttribute("loginMsg", "请输入登录信息");
      return;
    }
    if ((this.user.getUserId() == null) || ("".equals(this.user.getUserId())))
    {
      addActionError("登录失败");
      this.req.setAttribute("loginMsg", "请输入用户ID");
      return;
    }
    if ((this.user.getUserPwd() == null) || ("".equals(this.user.getUserPwd())))
    {
      addActionError("登录失败");
      this.req.setAttribute("loginMsg", "请输入密码");
      return;
    }
  }
  
  @Action(value="index", results={@org.apache.struts2.convention.annotation.Result(location="/WEB-INF/jsp/index/login.jsp")}, interceptorRefs={@org.apache.struts2.convention.annotation.InterceptorRef("defaultStack")})
  public String index()
    throws Exception
  {
    Cookie userIdCookie = CookieUtil.findCookie("userId", this.req);
    Cookie userPwdCookie = CookieUtil.findCookie("userPwd", this.req);
    if (this.user == null) {
      this.user = new LoginUser();
    }
    if (userIdCookie != null) {
      this.user.setUserId(URLDecoder.decode(userIdCookie.getValue(), "UTF-8"));
    }
    if (userPwdCookie != null) {
      this.user.setUserPwd(Base64Decoder.decode(URLDecoder.decode(userPwdCookie.getValue(), "UTF-8")));
    }
    return "success";
  }
  
  @Action(value="app", results={@org.apache.struts2.convention.annotation.Result(location="/WEB-INF/jsp/app.jsp"), @org.apache.struts2.convention.annotation.Result(name="input", location="/WEB-INF/jsp/index/login.jsp")}, interceptorRefs={@org.apache.struts2.convention.annotation.InterceptorRef("defaultStack")})
  public String login()
    throws Exception
  {
    try
    {
      this.user.setLoginTime(System.currentTimeMillis());
      this.userMg.login(this.user);
      if ("true".equals(this.logout))
      {
        CookieUtil.saveCookie("userId", this.user.getUserId(), 31536000, this.req, this.resp);
        
        Userlist u = (Userlist)this.userMg.getEntity(Userlist.class, this.user.getUserId());
        
        CookieUtil.saveCookie("userPwd", u.getTellerPasswd(), 31536000, this.req, this.resp);
        CookieUtil.saveCookie("rememberPwd", "true", 31536000, this.req, this.resp);
      }
      else
      {
        CookieUtil.deleteCookie("userId", this.req, this.resp);
        CookieUtil.deleteCookie("userPwd", this.req, this.resp);
        CookieUtil.deleteCookie("rememberPwd", this.req, this.resp);
      }
      if ("root".equals(this.user.getUserId()))
      {
        DataTree tree = createRootDataTree();
        this.funcList = createRootDataTree().getRootNode();
      }
      else
      {
        DataTree tree = createRootDataTree(this.user.getUserId());
        this.funcList = tree.getRootNode();
        this.user.setFuncs(tree.getTreeLeaf());
      }
      if (UserContext.isSingleLogin()) {
        UserContext.addUser(this.user);
      }
      this.session.setAttribute("user", this.user);
    }
    catch (Exception e)
    {
      this.req.setAttribute("loginMsg", e.getMessage());
      logger.error(ExceptionUtils.getStackTrace(e));
      return "input";
    }
    logger.info("用户：" + this.user.getUserId() + "-" + this.user.getUsername() + " 登录系统");
    
    return "success";
  }
  
  @Action(value="logout", results={@org.apache.struts2.convention.annotation.Result(type="redirect", location="index")}, interceptorRefs={@org.apache.struts2.convention.annotation.InterceptorRef("defaultStack")})
  public String logout()
    throws Exception
  {
    LoginUser u = TellerUtil.getLoginTeller();
    if (u != null)
    {
      if (UserContext.isSingleLogin()) {
        UserContext.removeUser(u);
      }
      logger.info("用户：" + u.getUserId() + "-" + u.getUsername() + " 登出系统");
    }
    this.session.invalidate();
    return "success";
  }
  
  @Action(value="defaultPage", results={@org.apache.struts2.convention.annotation.Result(type="json", params={"root", "dwzResp"}), @org.apache.struts2.convention.annotation.Result(name="input", type="redirect", location="index")}, interceptorRefs={@org.apache.struts2.convention.annotation.InterceptorRef("defaultStack")})
  public String defaultPage()
    throws Exception
  {
    if ((this.req.isRequestedSessionIdValid()) && (this.session.getAttribute("user") == null)) {
      return "input";
    }
    this.dwzResp.errorForward("抱歉，您访问的页面暂时不能访问，可能系统出错或者页面正在建设中...");
    return "success";
  }
  
  @Action(value="heartBeat", results={@org.apache.struts2.convention.annotation.Result(type="json", params={"root", "logout"})}, interceptorRefs={@org.apache.struts2.convention.annotation.InterceptorRef("defaultStack")})
  public String heartBeat()
    throws Exception
  {
    boolean sessionTimeout = !this.req.isRequestedSessionIdValid();
    boolean time2Logout = UserContext.isTime2Logout(TellerUtil.getLoginTeller());
    if ((!sessionTimeout) && (!time2Logout))
    {
      this.logout = "success";
    }
    else
    {
      if (time2Logout) {
        logout();
      }
      this.logout = "login";
    }
    return "success";
  }
  
  public boolean checkRememberPwd()
    throws Exception
  {
    Cookie rememberPwdCookie = CookieUtil.findCookie("rememberPwd", this.req);
    if (rememberPwdCookie == null) {
      return false;
    }
    return "true".equals(URLDecoder.decode(rememberPwdCookie.getValue(), "UTF-8"));
  }
  
  private DataTree createRootDataTree()
  {
    DataTree tree = new DataTree();
    CommonDataNode root = new CommonDataNode();
    root.setNodeName("管理系统");
    CommonDataNode root2 = new CommonDataNode();
    root2.setNodeName("系统管理");
    CommonDataNode applist = new CommonDataNode();
    applist.setNodeName("应用列表");
    applist.setNodeAddress("ListApplication");
    applist.setNodeId("usermg_1");
    CommonDataNode subappList = new CommonDataNode();
    subappList.setNodeName("子应用列表");
    subappList.setNodeAddress("ListSubappinfo");
    subappList.setNodeId("usermg_2");
    CommonDataNode funclist = new CommonDataNode();
    funclist.setNodeName("功能列表");
    funclist.setNodeAddress("ListFunction");
    funclist.setNodeId("usermg_3");
    CommonDataNode roleList = new CommonDataNode();
    roleList.setNodeName("级别列表");
    roleList.setNodeAddress("ListRole");
    roleList.setNodeId("usermg_4");
    CommonDataNode userlist = new CommonDataNode();
    userlist.setNodeAddress("ListUser");
    userlist.setNodeName("用户列表");
    userlist.setNodeId("usermg_5");
    CommonDataNode depList = new CommonDataNode();
    depList.setNodeAddress("ListDepartment");
    depList.setNodeName("机构列表");
    depList.setNodeId("usermg_6");
    
    root.addChild(root2);
    root2.addChild(applist);
    root2.addChild(subappList);
    root2.addChild(funclist);
    root2.addChild(roleList);
    root2.addChild(userlist);
    root2.addChild(depList);
    tree.setRootNode(root);
    
    return tree;
  }
  
  private DataTree createRootDataTree(String userId)
    throws Exception
  {
    try
    {
      return this.userMg.getFunctionTree(userId);
    }
    catch (Exception e)
    {
      if (this.getFuncCount > 10)
      {
        addActionError("数据库异常");
        throw e;
      }
      this.getFuncCount += 1;
    }
    return createRootDataTree(userId);
  }
  
  public CommonDataNode getFuncList()
  {
    return this.funcList;
  }
  
  public void setFuncList(CommonDataNode funcList)
  {
    this.funcList = funcList;
  }
  
  public IUserManager getUserMg()
  {
    return this.userMg;
  }
  
  public void setUserMg(IUserManager userMg)
  {
    this.userMg = userMg;
  }
  
  public LoginUser getUser()
  {
    return this.user;
  }
  
  public void setUser(LoginUser user)
  {
    this.user = user;
  }
  
  public String getLogout()
  {
    return this.logout;
  }
  
  public void setLogout(String logout)
  {
    this.logout = logout;
  }
}

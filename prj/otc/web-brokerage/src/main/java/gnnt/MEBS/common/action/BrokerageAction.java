package gnnt.MEBS.common.action;

import gnnt.MEBS.common.model.Brokerage;
import gnnt.MEBS.common.service.BrokerageService;
import gnnt.MEBS.common.util.MD5;
import gnnt.MEBS.config.constant.ActionConstant;
import gnnt.MEBS.config.constant.LogConstant;
import gnnt.MEBS.globalLog.model.OperateLog;
import gnnt.MEBS.globalLog.service.OperateLogService;
import gnnt.MEBS.packaging.action.BaseAction;
import gnnt.MEBS.packaging.service.InService;
import java.util.Date;
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
public class BrokerageAction
  extends BaseAction
{
  private final transient Log logger = LogFactory.getLog(BrokerageAction.class);
  @Autowired
  @Qualifier("brokerageService")
  private BrokerageService brokerageService;
  @Autowired
  @Qualifier("globalLogService")
  public OperateLogService operateLogService;
  
  public InService getService()
  {
    return this.brokerageService;
  }
  
  public String brokerageLogon()
    throws Exception
  {
    this.logger.debug("//--[CommonUserController]--enter commonUserLogon()---//");
    




    Brokerage user = new Brokerage();
    user.setBrokerageNo(this.request.getParameter("username"));
    user.setPassword(this.request.getParameter("pwd"));
    
    String randNumSys = (String)this.request.getSession().getAttribute("RANDOMICITYNUM");
    String randNumInput = this.request.getParameter("randNumInput");
    String resultMsg = this.brokerageService.authenticateUser(user, randNumSys, randNumInput);
    if ("default".equals(resultMsg))
    {
      this.request.getSession().invalidate();
      this.request.getSession().setAttribute("CURRENUSERID", user.getBrokerageNo());
      this.request.getSession().setAttribute("skinstyle", resultMsg);
      OperateLog operateLog = new OperateLog();
      operateLog.setOperator(user.getBrokerageNo());
      operateLog.setOperateDate(new Date());
      operateLog.setOperateIp(this.request.getRemoteAddr());
      operateLog.setOperateContent("居间端" + user.getBrokerageNo() + "登录了！");
      operateLog.setOperateLogType(3000);
      operateLog.setOperatorType(LogConstant.OPERATORTYPE);
      this.operateLogService.add(operateLog);
      return "success";
    }
    this.request.getSession().setAttribute(ActionConstant.RESULTMSG, resultMsg);
    this.request.getSession().setAttribute(ActionConstant.RESULTVAULE, Integer.valueOf(-1));
    return "error";
  }
  
  public String commonUserModPassword()
    throws Exception
  {
    this.logger.debug("//--[CommonUserController]--enter commonUserModPassword()---//");
    Brokerage modUser = (Brokerage)this.brokerageService.get(this.obj);
    this.request.setAttribute("modUser", modUser);
    return "success";
  }
  
  public String commonUserModPasswordForward()
    throws Exception
  {
    this.logger.debug("//--[CommonUserController]--enter commonUserModPassword()---//");
    String newPassword = this.request.getParameter("password1");
    String oldPassword = this.request.getParameter("oldPassword");
    

    Brokerage user = (Brokerage)this.brokerageService.get(this.obj);
    if (user.getPassword().equals(MD5.getMD5(user.getBrokerageNo(), oldPassword)))
    {
      user.setPassword(MD5.getMD5(user.getBrokerageNo(), newPassword));
      this.brokerageService.update(user);
      addResultMsg(this.request, 1);
    }
    else
    {
      addResultMsg(this.request, -4);
    }
    return getReturnValue();
  }
  
  public String commonUserLogout()
    throws Exception
  {
    this.logger.debug("//--[CommonUserController]--enter commonUserLogout()---//");
    this.request.getSession().removeAttribute("CURRENUSERID");
    this.request.getSession().invalidate();
    return "success";
  }
}

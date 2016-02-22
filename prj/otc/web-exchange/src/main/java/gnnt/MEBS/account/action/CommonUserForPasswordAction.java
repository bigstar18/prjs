package gnnt.MEBS.account.action;

import gnnt.MEBS.common.model.UserForPassword;
import gnnt.MEBS.common.service.UserForPasswordService;
import gnnt.MEBS.config.constant.ActionConstant;
import gnnt.MEBS.member.ActiveUser.MD5;
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
public class CommonUserForPasswordAction
  extends BaseAction
{
  private final transient Log logger = LogFactory.getLog(CommonUserForPasswordAction.class);
  @Autowired
  @Qualifier("userForPasswordService")
  private UserForPasswordService userForPasswordService;
  
  public InService getService()
  {
    return this.userForPasswordService;
  }
  
  public String commonUserModPasswordForward()
  {
    String temppass = new String(((UserForPassword)this.obj).getOld());
    UserForPassword user = 
      (UserForPassword)this.userForPasswordService.get(this.obj);
    this.logger.debug("before   password:" + user.getPassword());
    this.logger.debug("userId:" + user.getUserId());
    



    user.setPassword(MD5.getMD5(user.getUserId(), 
      ((UserForPassword)this.obj).getPassword()));
    this.logger.debug("after   password:" + user.getPassword());
    this.userForPasswordService.update(user);
    addResultMsg(this.request, 3);
    return getReturnValue();
  }
  
  public String commonUserModPasswordForwardSelf()
  {
    String temppass = new String(((UserForPassword)this.obj).getOld());
    UserForPassword user = 
      (UserForPassword)this.userForPasswordService.get(this.obj);
    this.logger.debug("before   password:" + user.getPassword());
    this.logger.debug("userId:" + user.getUserId());
    if (user.getPassword().equals(
      MD5.getMD5(user.getUserId(), 
      temppass)))
    {
      user.setPassword(MD5.getMD5(user.getUserId(), 
        ((UserForPassword)this.obj).getPassword()));
      this.logger.debug("after   password:" + user.getPassword());
      this.userForPasswordService.update(user);
      addResultMsg(this.request, 3);
      return getReturnValue();
    }
    this.request.setAttribute(ActionConstant.RESULTMSG, "原密码错误!");
    this.request.setAttribute(ActionConstant.RESULTVAULE, Integer.valueOf(-1));
    return getReturnValue();
  }
}

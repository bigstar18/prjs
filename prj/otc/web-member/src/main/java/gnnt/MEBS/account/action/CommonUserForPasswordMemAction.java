package gnnt.MEBS.account.action;

import gnnt.MEBS.common.model.UserForPassword;
import gnnt.MEBS.common.service.UserForPasswordMemService;
import gnnt.MEBS.member.ActiveUser.MD5;
import gnnt.MEBS.packaging.service.InService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component("commonUserForPasswordMemAction")
@Scope("request")
public class CommonUserForPasswordMemAction
  extends CommonUserForPasswordAction
{
  private final transient Log logger = LogFactory.getLog(CommonUserForPasswordAction.class);
  @Autowired
  @Qualifier("userForPasswordMemService")
  private UserForPasswordMemService userForPasswordService;
  
  public InService getService()
  {
    return this.userForPasswordService;
  }
  
  public String commonUserModPasswordForward()
  {
    String temppass = new String(((UserForPassword)this.obj).getOld());
    UserForPassword user = 
      (UserForPassword)this.userForPasswordService.get((UserForPassword)this.obj);
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
      (UserForPassword)this.userForPasswordService.get((UserForPassword)this.obj);
    this.logger.debug("before   password:" + user.getPassword());
    this.logger.debug("userId:" + user.getUserId());
    if (user.getPassword().equals(MD5.getMD5(user.getUserId(), temppass)))
    {
      user.setPassword(MD5.getMD5(user.getUserId(), 
        ((UserForPassword)this.obj).getPassword()));
      this.logger.debug("after   password:" + user.getPassword());
      this.userForPasswordService.update(user);
      addResultMsg(this.request, 3);
    }
    else
    {
      addResultMsg(this.request, -12);
    }
    return getReturnValue();
  }
}

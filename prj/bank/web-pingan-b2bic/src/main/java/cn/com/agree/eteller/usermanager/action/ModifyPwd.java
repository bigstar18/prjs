package cn.com.agree.eteller.usermanager.action;

import cn.com.agree.eteller.generic.action.GenericAction;
import cn.com.agree.eteller.generic.utils.Base64Encoder;
import cn.com.agree.eteller.generic.utils.MD5;
import cn.com.agree.eteller.generic.vo.DwzResponse;
import cn.com.agree.eteller.generic.vo.LoginUser;
import cn.com.agree.eteller.usermanager.persistence.Userlist;
import cn.com.agree.eteller.usermanager.spring.IUserManager;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.struts2.convention.annotation.Action;

@Action(value="ModifyPwd", results={@org.apache.struts2.convention.annotation.Result(type="json", params={"root", "dwzResp"})})
public class ModifyPwd
  extends GenericAction
{
  private static final long serialVersionUID = 5892180085504881981L;
  @Resource(name="userManagerTarget")
  private IUserManager userMg;
  private Userlist user;
  
  public String execute()
    throws Exception
  {
    LoginUser loginUser = (LoginUser)this.session.getAttribute("user");
    Userlist user = this.userMg.getUser(loginUser.getUserId());
    MD5 md5 = new MD5();
    

    String oldPwd = this.req.getParameter("password");
    if (!Base64Encoder.encode(oldPwd).equals(user.getTellerPasswd()))
    {
      this.dwzResp.errorForward("原密码输入错误，不允许修改密码");
    }
    else
    {
      String newPwd = this.req.getParameter("newPassword");
      user.setTellerPasswd(Base64Encoder.encode(newPwd));
      this.userMg.update(user);
      this.dwzResp.successForward("修改密码成功");
    }
    return "success";
  }
  
  @Action("ResetUserPwd")
  public String reset()
    throws Exception
  {
    try
    {
      this.user = this.userMg.getUser(this.user.getUserId());
      this.user.setTellerPasswd(Base64Encoder.encode("888888"));
      this.userMg.update(this.user);
      
      this.dwzResp.ajaxSuccessForward("重置成功");
    }
    catch (Exception e)
    {
      this.dwzResp.errorForward("重置失败");
      e.printStackTrace();
    }
    return "dwz";
  }
  
  public Userlist getUser()
  {
    return this.user;
  }
  
  public void setUser(Userlist user)
  {
    this.user = user;
  }
}

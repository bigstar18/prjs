package gnnt.MEBS.integrated.front.action.usermanage;

import gnnt.MEBS.common.front.action.StandardAction;
import gnnt.MEBS.common.front.model.integrated.MFirm;
import gnnt.MEBS.common.front.model.integrated.User;
import gnnt.MEBS.common.front.service.StandardService;
import gnnt.MEBS.common.front.service.UserService;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

@Controller("userAction")
@Scope("request")
public class UserAction
  extends StandardAction
{
  private static final long serialVersionUID = 8849362928513184976L;
  @Autowired
  @Qualifier("com_userService")
  private UserService com_userService;
  @Autowired
  @Resource(name="com_firmStatusMap")
  protected Map<String, String> com_firmStatusMap;
  @Autowired
  @Resource(name="com_firmTypeMap")
  protected Map<String, String> com_firmTypeMap;
  @Autowired
  @Resource(name="com_traderTypeMap")
  protected Map<String, String> com_traderTypeMap;
  
  public StandardService getService()
  {
    return this.com_userService;
  }
  
  public Map<String, String> getCom_firmStatusMap()
  {
    return this.com_firmStatusMap;
  }
  
  public Map<String, String> getCom_firmTypeMap()
  {
    return this.com_firmTypeMap;
  }
  
  public Map<String, String> getCom_traderTypeMap()
  {
    return this.com_traderTypeMap;
  }
  
  public String userInformation()
  {
    User localUser = (User)this.request.getSession().getAttribute("CurrentUser");
    MFirm localMFirm = (MFirm)getService().get(localUser.getBelongtoFirm());
    this.request.setAttribute("firm", localMFirm);
    return "success";
  }
}

package gnnt.MEBS.integrated.mgr.action;

import gnnt.MEBS.common.mgr.action.EcsideAction;
import gnnt.MEBS.common.mgr.model.Role;
import gnnt.MEBS.common.mgr.service.StandardService;
import gnnt.MEBS.common.mgr.statictools.Tools;
import javax.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

@Controller("roleAction")
@Scope("request")
public class RoleAction
  extends EcsideAction
{
  private static final long serialVersionUID = 2065992750914189118L;
  
  public RoleAction()
  {
    super.setEntityName(Role.class.getName());
  }
  
  public void setEntityName(String paramString) {}
  
  public String deleteRole()
    throws NoSuchFieldException, Exception
  {
    try
    {
      Role localRole = new Role();
      String[] arrayOfString1 = this.request.getParameterValues("ids");
      String str1 = "";
      for (String str2 : arrayOfString1)
      {
        if (!"".equals(str1)) {
          str1 = str1 + ",";
        }
        str1 = str1 + str2;
        Long localLong = Long.valueOf(Tools.strToLong(str2));
        localRole.setId(localLong);
        localRole = (Role)getService().get(localRole);
        getService().executeUpdateBySql("delete c_role_right t where t.roleid = " + localLong);
        getService().delete(localRole);
      }
      addReturnValue(1, 111609L);
    }
    catch (SecurityException localSecurityException)
    {
      localSecurityException.printStackTrace();
    }
    return "success";
  }
}

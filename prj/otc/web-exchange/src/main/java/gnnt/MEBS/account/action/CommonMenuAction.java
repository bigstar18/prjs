package gnnt.MEBS.account.action;

import gnnt.MEBS.common.model.Menu;
import gnnt.MEBS.common.model.Right;
import gnnt.MEBS.common.model.User;
import gnnt.MEBS.common.security.AclCtrl;
import gnnt.MEBS.common.security.util.IntegrateToMap;
import gnnt.MEBS.packaging.action.BaseAction;
import gnnt.MEBS.packaging.service.InService;
import java.util.Map;
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
public class CommonMenuAction
  extends BaseAction
{
  private final transient Log logger = LogFactory.getLog(CommonMenuAction.class);
  @Autowired
  @Qualifier("menuService")
  protected InService menuService;
  
  public InService getService()
  {
    return this.menuService;
  }
  
  public String commonMenuList()
    throws Exception
  {
    this.logger.debug("//--[CommonMenuController]--enter commonMenuList()---//");
    User user = (User)this.request.getSession().getAttribute("CURRENUSER");
    Map<Long, Right> rightMap = user.getRightMap();
    Menu allMenu = AclCtrl.ROOTMENU;
    Map<Long, Menu> menuMap = IntegrateToMap.integrateToMap(allMenu, rightMap);
    this.request.setAttribute("menuMap", menuMap);
    this.request.setAttribute("allMenu", allMenu);
    this.request.setAttribute("rightMap", rightMap);
    return "success";
  }
}

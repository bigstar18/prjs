package gnnt.MEBS.common.action;

import gnnt.MEBS.common.model.Menu;
import gnnt.MEBS.common.model.User;
import gnnt.MEBS.common.security.AclCtrl;
import gnnt.MEBS.common.security.util.IntegrateToMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;

public class CommonMenuController
  extends BaseController
{
  private final transient Log logger = LogFactory.getLog(CommonMenuController.class);
  
  public ModelAndView commonMenuList(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    this.logger.debug("//--[CommonMenuController]--enter commonMenuList()---//");
    User localUser = (User)paramHttpServletRequest.getSession().getAttribute("CURRENUSER");
    Map localMap1 = localUser.getRightMap();
    Menu localMenu = AclCtrl.ROOTMENU;
    Map localMap2 = IntegrateToMap.integrateToMap(localMenu, localMap1);
    ModelAndView localModelAndView = new ModelAndView("common/surface/leftMenu", "menuMap", localMap2);
    localModelAndView.addObject("allMenu", localMenu);
    localModelAndView.addObject("rightMap", localMap1);
    return localModelAndView;
  }
}

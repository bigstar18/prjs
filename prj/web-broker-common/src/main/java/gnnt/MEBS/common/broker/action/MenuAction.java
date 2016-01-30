package gnnt.MEBS.common.broker.action;

import gnnt.MEBS.common.broker.common.Global;
import gnnt.MEBS.common.broker.model.Broker;
import gnnt.MEBS.common.broker.model.BrokerAge;
import gnnt.MEBS.common.broker.model.Menu;
import gnnt.MEBS.common.broker.model.User;
import gnnt.MEBS.common.broker.service.MenuService;
import gnnt.MEBS.common.broker.service.StandardService;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

@Controller("com_menuAction")
@Scope("request")
public class MenuAction extends StandardAction
{
  private static final long serialVersionUID = 6951086182615239337L;

  @Autowired
  @Qualifier("com_menuService")
  private MenuService menuService;

  public MenuAction()
  {
    super.setEntityName(Menu.class.getName());
  }

  public void setEntityName(String paramString)
  {
  }

  public StandardService getService()
  {
    return this.menuService;
  }

  public String menuList()
  {
    User localUser = (User)this.request.getSession().getAttribute("CurrentUser");
    if (localUser != null)
    {
      Menu localMenu1 = Global.getRootMenu();
      Map localMap1 = null;
      Map localMap2 = null;
      if (localUser.getType().equals("0"))
        localMap2 = localUser.getBroker().getRightMap();
      else
        localMap2 = localUser.getBrokerAge().getRightMap();
      Menu localMenu2 = this.menuService.getHaveRightMenu(localMenu1, localMap2);
      this.request.setAttribute("HaveRightMenu", localMenu2);
      localMap1 = getMenuMap(localMenu2, localMap1);
      return "success";
    }
    return "error";
  }

  private Map<Long, Menu> getMenuMap(Menu paramMenu, Map<Long, Menu> paramMap)
  {
    if (paramMenu == null)
      return paramMap;
    if (paramMap == null)
      paramMap = new LinkedHashMap();
    paramMap.put(paramMenu.getId(), paramMenu);
    if (paramMenu.getChildMenuSet() != null)
    {
      Iterator localIterator = paramMenu.getChildMenuSet().iterator();
      while (localIterator.hasNext())
      {
        Menu localMenu = (Menu)localIterator.next();
        getMenuMap(localMenu, paramMap);
      }
    }
    return paramMap;
  }

  public String modMyMenu()
  {
    return "success";
  }
}
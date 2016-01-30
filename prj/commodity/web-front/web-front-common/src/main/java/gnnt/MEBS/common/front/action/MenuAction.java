package gnnt.MEBS.common.front.action;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import gnnt.MEBS.common.front.common.Global;
import gnnt.MEBS.common.front.common.Page;
import gnnt.MEBS.common.front.common.PageRequest;
import gnnt.MEBS.common.front.model.StandardModel;
import gnnt.MEBS.common.front.model.front.Menu;
import gnnt.MEBS.common.front.model.front.MyMenu;
import gnnt.MEBS.common.front.model.front.Right;
import gnnt.MEBS.common.front.model.integrated.MFirmModule;
import gnnt.MEBS.common.front.model.integrated.TraderModule;
import gnnt.MEBS.common.front.model.integrated.User;
import gnnt.MEBS.common.front.service.MenuService;
import gnnt.MEBS.common.front.service.StandardService;
import gnnt.MEBS.common.front.statictools.Tools;

@Controller("com_menuAction")
@Scope("request")
public class MenuAction extends StandardAction {
	private static final long serialVersionUID = 6951086182615239337L;
	@Autowired
	@Qualifier("com_menuService")
	private MenuService com_menuService;

	public MenuAction() {
		super.setEntityName(Menu.class.getName());
	}

	public void setEntityName(String paramString) {
	}

	public StandardService getService() {
		return this.com_menuService;
	}

	public String menuList() {
		this.logger.debug("获取当前用户的菜单列表");
		User localUser = (User) this.request.getSession().getAttribute("CurrentUser");
		if (localUser != null) {
			boolean bool = false;
			bool = ((Boolean) this.request.getSession().getAttribute("IsSuperAdmin")).booleanValue();
			Menu localMenu = (Menu) Global.getRootMenu().clone();
			LinkedHashSet localLinkedHashSet = new LinkedHashSet();
			Object localObject1 = localMenu.getChildMenuSet().iterator();
			while (((Iterator) localObject1).hasNext()) {
				Menu localObject2 = (Menu) ((Iterator) localObject1).next();
				Iterator localObject3 = localUser.getBelongtoFirm().getFirmModuleSet().iterator();
				while (((Iterator) localObject3).hasNext()) {
					MFirmModule localObject4 = (MFirmModule) ((Iterator) localObject3).next();
					if ((((MFirmModule) localObject4).getModuleID() != null)
							&& (((MFirmModule) localObject4).getModuleID().equals(((Menu) localObject2).getModuleId()))
							&& ("Y".equalsIgnoreCase(((MFirmModule) localObject4).getEnabled()))) {
						localLinkedHashSet.add(localObject2);
						break;
					}
				}
			}
			localMenu.setChildMenuSet(localLinkedHashSet);
			localObject1 = new LinkedHashSet();
			Object localObject2 = localMenu.getChildMenuSet().iterator();
			while (((Iterator) localObject2).hasNext()) {
				Menu localObject3 = (Menu) ((Iterator) localObject2).next();
				Iterator localObject4 = localUser.getTraderModule().iterator();
				while (((Iterator) localObject4).hasNext()) {
					TraderModule localObject5 = (TraderModule) ((Iterator) localObject4).next();
					if ((((TraderModule) localObject5).getModuleID() != null)
							&& (((TraderModule) localObject5).getModuleID().equals(((Menu) localObject3).getModuleId()))
							&& ("Y".equalsIgnoreCase(((TraderModule) localObject5).getEnabled()))) {
						((Set) localObject1).add(localObject3);
						break;
					}
				}
			}
			localMenu.setChildMenuSet((Set) localObject1);
			if (bool) {
				this.request.setAttribute("HaveRightMenu", localMenu);
			} else {
				localObject2 = localUser.getRightMap();
				Menu localObject3 = this.com_menuService.getHaveRightMenu(localMenu, (Map) localObject2);
				this.request.setAttribute("HaveRightMenu", localObject3);
			}
			localObject2 = (Menu) this.request.getAttribute("HaveRightMenu");
			Object localObject3 = getMenuMap((Menu) localObject2, null);
			Object localObject4 = new PageRequest(1, 1000, " and userID='" + localUser.getTraderID() + "'", null);
			Object localObject5 = getService().getPage((PageRequest) localObject4, new MyMenu());
			List localList = ((Page) localObject5).getResult();
			LinkedHashMap localLinkedHashMap = new LinkedHashMap();
			if (localList != null) {
				Iterator localIterator = localList.iterator();
				while (localIterator.hasNext()) {
					StandardModel localStandardModel = (StandardModel) localIterator.next();
					MyMenu localMyMenu = (MyMenu) localStandardModel;
					if (((Map) localObject3).get(localMyMenu.getBelongtoRight().getId()) != null) {
						localLinkedHashMap.put(localMyMenu.getBelongtoRight().getId(), localMyMenu.getBelongtoRight());
					}
				}
			}
			this.request.setAttribute("mymenu", localLinkedHashMap);
			return "success";
		}
		return "error";
	}

	private Map<Long, Menu> getMenuMap(Menu paramMenu, Map<Long, Menu> paramMap) {
		if (paramMenu == null) {
			return paramMap;
		}
		if (paramMap == null) {
			paramMap = new LinkedHashMap();
		}
		paramMap.put(paramMenu.getId(), paramMenu);
		if (paramMenu.getChildMenuSet() != null) {
			Iterator localIterator = paramMenu.getChildMenuSet().iterator();
			while (localIterator.hasNext()) {
				Menu localMenu = (Menu) localIterator.next();
				getMenuMap(localMenu, paramMap);
			}
		}
		return paramMap;
	}

	public String changemymenu() {
		User localUser = (User) this.request.getSession().getAttribute("CurrentUser");
		if (localUser != null) {
			ArrayList localArrayList = new ArrayList();
			String[] arrayOfString1 = this.request.getParameterValues("mymenu");
			if ((arrayOfString1 != null) && (arrayOfString1.length > 0)) {
				Right localObject = null;
				MyMenu localMyMenu = null;
				for (String str : arrayOfString1) {
					long l = Tools.strToLong(str, -1000L);
					if (l > 0L) {
						localObject = new Right();
						((Right) localObject).setId(Long.valueOf(l));
						localMyMenu = new MyMenu();
						localMyMenu.setUserID(localUser.getTraderID());
						localMyMenu.setBelongtoRight((Right) localObject);
						localArrayList.add(localMyMenu);
					}
				}
			}
			Object localObject = new PageRequest(1, 1000, " and userID='" + localUser.getTraderID() + "'", null);
			this.com_menuService.changemymenu((PageRequest) localObject, localArrayList);
			addReturnValue(1, 9910401L, null, 11);
			return "success";
		}
		addReturnValue(-1, 9930401L);
		return "error";
	}
}

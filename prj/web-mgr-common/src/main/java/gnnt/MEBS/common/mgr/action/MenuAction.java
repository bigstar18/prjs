package gnnt.MEBS.common.mgr.action;

import gnnt.MEBS.common.mgr.common.Global;
import gnnt.MEBS.common.mgr.common.Page;
import gnnt.MEBS.common.mgr.common.PageRequest;
import gnnt.MEBS.common.mgr.model.Menu;
import gnnt.MEBS.common.mgr.model.MyMenu;
import gnnt.MEBS.common.mgr.model.Right;
import gnnt.MEBS.common.mgr.model.StandardModel;
import gnnt.MEBS.common.mgr.model.User;
import gnnt.MEBS.common.mgr.service.MenuService;
import gnnt.MEBS.common.mgr.service.StandardService;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

/**
 * 菜单Action
 * 
 * @author xuejt
 * 
 */
@Controller("com_menuAction")
@Scope("request")
public class MenuAction extends StandardAction {

	private static final long serialVersionUID = 6951086182615239337L;

	public MenuAction() {
		super.setEntityName(Menu.class.getName());
	}

	/**
	 * 在构造中强制设置为角色类型，重写设置实体名让重写实体名方法不做任何事
	 */
	public void setEntityName(String entityName) {

	}

	/**
	 * 注入标准service实例
	 */
	@Autowired
	@Qualifier("com_menuService")
	private MenuService menuService;

	/**
	 * 获取Action使用的service实例
	 * 
	 * @return service对象
	 */
	public StandardService getService() {
		return this.menuService;
	}


	/**
	 * 获取菜单列表
	 * 跳转 快捷菜单设置
	 * 
	 * @return
	 */
	public String menuList() {
		User user = (User) request.getSession().getAttribute(
				Global.CURRENTUSERSTR);
		if (user != null) {
			boolean isSuperAdminRole = false;
			isSuperAdminRole = (Boolean) request.getSession().getAttribute(
					Global.ISSUPERADMIN);

			Menu allMenu = Global.getRootMenu();

			//保存当前用户有权限的菜单 Map key：菜单编号，value 菜单
			Map<Long,Menu> menuMap = null;
			// 如果是超级管理员则拥有所有菜单
			if (isSuperAdminRole) {
				request.setAttribute(Global.HAVERIGHTMENU, allMenu);
				menuMap = getMenuMap(allMenu,menuMap);
			}
			// 不是超级管理员获取自己有权限的菜单
			else {
				Map<Long, Right> rightMap = user.getRightMap();
				Menu menu = menuService.getHaveRightMenu(allMenu, rightMap);
				request.setAttribute(Global.HAVERIGHTMENU, menu);
				menuMap = getMenuMap(menu,menuMap);
			}

			//当前系统所加载的菜单模块
			List<Integer> moduleIDList = Global.getModuleIDList();
			String filter = " and primary.user.userId='"+user.getUserId()+"' ";
			if(moduleIDList != null){
				String modules = ""+Global.COMMONMODULEID;
				for(Integer moduleID : moduleIDList){
					if(moduleID != Global.COMMONMODULEID){
						modules += ","+moduleID;
					}
				}
				filter += " and primary.right.moduleId in ("+modules+") ";
			}

			//获取该登录用户下的快捷菜单中菜单ID的集合并存入数组中
			PageRequest<String> pageRequest=new PageRequest<String>(filter);
			Page<StandardModel> page=getService().getPage(pageRequest, new MyMenu());
			List<MyMenu> myMenuList = new ArrayList<MyMenu>();

			//遍历查看我的菜单中菜单权限，防止设置我的菜单后，管理员撤销了本用户的权限
			if(page != null && page.getResult() != null && menuMap != null){
				for(StandardModel standardModel : page.getResult()){
					MyMenu myMenu = (MyMenu)standardModel;
					if(menuMap.get(myMenu.getRight().getId()) != null){
						myMenuList.add(myMenu);
					}
				}
			}
			request.setAttribute("myMenuList", page.getResult());
			return SUCCESS;
		}else{
			return ERROR;
		}
		
	}

	/**
	 * 遍历 menu 将子 menu 添加到 map 中<br/>
	 * key：菜单编号，value 菜单
	 * @param menu
	 * @param map
	 * @return
	 */
	private Map<Long,Menu> getMenuMap(Menu menu,Map<Long,Menu> map){
		if(menu == null) return map;
		if(map == null) map = new LinkedHashMap<Long,Menu>();
		map.put(menu.getId(), menu);
		if(menu.getChildMenuSet() != null){
			for(Menu child : menu.getChildMenuSet()){
				getMenuMap(child,map);
			}
		}
		return map;
	}
	
	/**
	 * 添加快捷菜单设置
	 * @return
	 */
	public String modMyMenu(){
		//获取选中的菜单
		String[] rightIds = request.getParameterValues("ck");
		User user = (User) request.getSession().getAttribute(
				Global.CURRENTUSERSTR);

		if (user != null) {
	
			int maxRightSize = 5;
			if(rightIds==null || rightIds.length<=maxRightSize){
				List<Integer> moduleIDList = Global.getModuleIDList();
				String filter = " and primary.user.userId='"+user.getUserId()+"' ";
				if(moduleIDList != null){
					String modules = "0";
					for(Integer moduleID : moduleIDList){
						if(moduleID != 0){
							modules += ","+moduleID;
						}
					}
					filter += " and primary.right.moduleId in ("+modules+") ";
				}
				//获取该登录用户下的快捷菜单中菜单ID的集合并存入数组中
				PageRequest<String> pageRequest=new PageRequest<String>(filter);
				Page<StandardModel> page=getService().getPage(pageRequest, new MyMenu());
				//日志内容串
				menuService.modMyMenu(user,rightIds,page);
				//设置我的菜单成功
				this.addReturnValue(1, 111001);
			}
			else{
				//设置我的菜单失败，我的菜单最多不能多于%s个
				this.addReturnValue(1, 111001,new Object[]{maxRightSize});
			}
		
		}
		return SUCCESS;
		
	}
}

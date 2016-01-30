package gnnt.MEBS.common.mgr.service;

import gnnt.MEBS.common.mgr.common.Page;
import gnnt.MEBS.common.mgr.dao.MenuDao;
import gnnt.MEBS.common.mgr.dao.StandardDao;
import gnnt.MEBS.common.mgr.model.Menu;
import gnnt.MEBS.common.mgr.model.MyMenu;
import gnnt.MEBS.common.mgr.model.Right;
import gnnt.MEBS.common.mgr.model.StandardModel;
import gnnt.MEBS.common.mgr.model.User;
import gnnt.MEBS.common.mgr.statictools.Tools;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * 菜单service
 * 
 * @author xuejt
 * 
 */
@Service("com_menuService")
@Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class MenuService extends StandardService {
	@Autowired
	@Qualifier("com_menuDao")
	private MenuDao menuDao;

	@Override
	public StandardDao getDao() {
		return menuDao;
	}

	/**
	 * 根据子过滤器获取菜单列表
	 * <P>
	 * 子过滤器 信息 ((TYPE=:type1 or TYPE=:type2) and VISIBLE=:visible)用来过滤子菜单集合<br>
	 * type说明 -2：无需判断权限的URL -1： 父菜单类型 0：子菜单类型 1：页面内增删改查权限<br>
	 * visible说明 0 可见 其它不可见
	 * 
	 * @param type1
	 *            过滤器中的type1
	 * @param type2
	 *            过滤器中的type2
	 * @param visible
	 *            菜单可见性 0 可见 其它不可见
	 * @return 菜单列表
	 */
	public List<Menu> getMenuBySubFilter(int type1, int type2, int visible) {
		return menuDao.getMenuBySubFilter(type1, type2, visible);
	}

	/**
	 * 根据菜单代码获取菜单信息
	 * <P>
	 * 子过滤器 信息 (TYPE=:type1 or TYPE=:type2) and VISIBLE=:visible and MODULEID
	 * in(:moduleID)用来过滤子菜单集合<br>
	 * type说明 -2：无需判断权限的URL -1： 父菜单类型 0：子菜单类型 1：页面内增删改查权限<br>
	 * visible说明 0 可见 其它不可见 moduleID为系统的模块号
	 * 
	 * @param id
	 *            菜单代码
	 * @param moduleList
	 *            子菜单只保留moduleList中所包含的模块菜单
	 * @return 菜单
	 */
	public Menu getMenuById(long id, List<Integer> moduleList) {
		return menuDao.getMenuById(id, -1, 0, 0, moduleList);
	}

	/**
	 * 根据菜单树和权限 获取有权限的菜单集合
	 * 
	 * @param allMenu
	 *            所有菜单
	 * @param rightMap
	 *            权限
	 * @return 有权限的菜单树
	 */
	public Menu getHaveRightMenu(Menu allMenu, Map<Long, Right> rightMap) {
		// 新菜单对象 将有权限的菜单复制到新菜单对象
		Menu newMenu = (Menu) allMenu.clone();

		// 因为是克隆过来的所以 子菜单有内容，清空子菜单;子菜单使用seq排序
		newMenu.setChildMenuSet(new TreeSet<Menu>(new Comparator<Menu>() {
			public int compare(Menu menu1, Menu menu2) {
				if (menu1.getSeq() == menu2.getSeq()) {
					return 0;
				} else if (menu1.getSeq() > menu2.getSeq()) {
					return 1;
				} else {
					return -1;
				}
			}
		}));

		// 源菜单的子菜单集合
		Set<Menu> childMenuSet = allMenu.getChildMenuSet();

		// 新菜单子菜单集合
		Set<Menu> newChildMenuSet = newMenu.getChildMenuSet();

		// 遍历子菜单 查看是否有权限 有权限则添加到新菜单
		for (Menu childMenu : childMenuSet) {
			// 权限中是否包含菜单权限标志
			boolean includeFlag = false;
			for (Long rightID : rightMap.keySet()) {
				if (childMenu.getId().longValue() == rightID.longValue()) {
					includeFlag = true;
					break;
				}
			}
			// 如果有权限
			if (includeFlag) {
				// 新子菜单对象
				Menu newChildMenu = (Menu) childMenu.clone();
				// 递归判断子菜单是否还有子菜单 如果有递归
				if (newChildMenu.getChildMenuSet() != null
						&& newChildMenu.getChildMenuSet().size() > 0) {
					newChildMenu = getHaveRightMenu(newChildMenu, rightMap);
				}
				newChildMenuSet.add(newChildMenu);
			}

		}

		return newMenu;
	}

	/**
	 * 针对快捷菜单的操作
	 * 
	 * @param userid
	 * @param rightId
	 */
	public void modMyMenu(User user, String[] rightId, Page<StandardModel> page) {
		// 删除已存在的快捷菜单
		if (page.getResult() != null && page.getResult().size() > 0) {
			getDao().deleteBYBulk(page.getResult());
		}
		if (rightId != null && rightId.length != 0) {
			// 重新添加菜单
			for (String id : rightId) {
				MyMenu menu = new MyMenu();
				Right right = new Right();
				right.setId(Tools.strToLong(id));
				menu.setRight((Right) getDao().get(right));
				menu.setUser(user);
				getDao().add(menu);
			}
		}

	}
}

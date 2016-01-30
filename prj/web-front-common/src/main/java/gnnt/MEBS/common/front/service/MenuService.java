package gnnt.MEBS.common.front.service;

import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import gnnt.MEBS.common.front.common.Page;
import gnnt.MEBS.common.front.common.PageRequest;
import gnnt.MEBS.common.front.dao.MenuDao;
import gnnt.MEBS.common.front.dao.StandardDao;
import gnnt.MEBS.common.front.model.front.Menu;
import gnnt.MEBS.common.front.model.front.MyMenu;
import gnnt.MEBS.common.front.model.front.Right;

@Service("com_menuService")
@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
public class MenuService extends StandardService {
	@Autowired
	@Qualifier("com_menuDao")
	private MenuDao com_menuDao;

	public StandardDao getDao() {
		return this.com_menuDao;
	}

	public List<Menu> getMenuBySubFilter(int paramInt1, int paramInt2, int paramInt3) {
		return this.com_menuDao.getMenuBySubFilter(paramInt1, paramInt2, paramInt3);
	}

	public Menu getMenuById(long paramLong, List<Integer> paramList) {
		return this.com_menuDao.getMenuById(paramLong, paramList, -1, 0, 0);
	}

	public Menu getHaveRightMenu(Menu paramMenu, Map<Long, Right> paramMap) {
		Menu localMenu1 = (Menu) paramMenu.clone();
		localMenu1.setChildMenuSet(new TreeSet(new Comparator<Menu>() {
			public int compare(Menu paramAnonymousMenu1, Menu paramAnonymousMenu2) {
				if (paramAnonymousMenu1.getSeq() == paramAnonymousMenu2.getSeq()) {
					return 0;
				}
				if (paramAnonymousMenu1.getSeq().intValue() > paramAnonymousMenu2.getSeq().intValue()) {
					return 1;
				}
				return -1;
			}
		}));
		Set localSet1 = paramMenu.getChildMenuSet();
		Set localSet2 = localMenu1.getChildMenuSet();
		Iterator localIterator = localSet1.iterator();
		while (localIterator.hasNext()) {
			Menu localMenu2 = (Menu) localIterator.next();
			int i = 0;
			Object localObject = paramMap.keySet().iterator();
			while (((Iterator) localObject).hasNext()) {
				Long localLong = (Long) ((Iterator) localObject).next();
				if (localMenu2.getId().longValue() == localLong.longValue()) {
					i = 1;
					break;
				}
			}
			if (i != 0) {
				localObject = (Menu) localMenu2.clone();
				if ((((Menu) localObject).getChildMenuSet() != null) && (((Menu) localObject).getChildMenuSet().size() > 0)) {
					localObject = getHaveRightMenu((Menu) localObject, paramMap);
				}
				localSet2.add(localObject);
			}
		}
		return localMenu1;
	}

	public int changemymenu(PageRequest<String> paramPageRequest, List<MyMenu> paramList) {
		Page localPage = getPage(paramPageRequest, new MyMenu());
		deleteBYBulk(localPage.getResult());
		if (paramList != null) {
			Iterator localIterator = paramList.iterator();
			while (localIterator.hasNext()) {
				MyMenu localMyMenu = (MyMenu) localIterator.next();
				add(localMyMenu);
			}
		}
		return 1;
	}
}

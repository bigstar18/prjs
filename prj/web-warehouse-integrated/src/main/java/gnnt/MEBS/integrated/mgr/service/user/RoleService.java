package gnnt.MEBS.integrated.mgr.service.user;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import gnnt.MEBS.common.mgr.common.Page;
import gnnt.MEBS.common.mgr.common.PageRequest;
import gnnt.MEBS.common.mgr.dao.StandardDao;
import gnnt.MEBS.common.mgr.model.MyMenu;
import gnnt.MEBS.common.mgr.model.Right;
import gnnt.MEBS.common.mgr.model.Role;
import gnnt.MEBS.common.mgr.model.StandardModel;
import gnnt.MEBS.common.mgr.model.User;
import gnnt.MEBS.common.mgr.service.StandardService;
import gnnt.MEBS.common.mgr.statictools.Tools;
import gnnt.MEBS.integrated.mgr.dao.RoleDao;

@Service("roleService")
@Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = { Exception.class })
public class RoleService extends StandardService {
	@Autowired
	@Qualifier("roleDao")
	private RoleDao roleDao;

	public StandardDao getDao() {
		return this.roleDao;
	}

	public Role getRoleById(long paramLong, boolean paramBoolean1, boolean paramBoolean2) {
		Role localRole = this.roleDao.getRoleById(Long.valueOf(paramLong));
		if (paramBoolean1) {
			localRole.getRightSet().size();
		}
		if (paramBoolean2) {
			localRole.getUserSet().size();
		}
		return localRole;
	}

	public void saveRoleRights(Role paramRole, String[] paramArrayOfString) {
		Object localObject2;
		if ((paramRole.getUserSet() != null) && (paramRole.getUserSet().size() > 0)) {
			Iterator localObject1 = paramRole.getUserSet().iterator();
			while (((Iterator) localObject1).hasNext()) {
				User localUser = (User) ((Iterator) localObject1).next();
				localObject2 = new PageRequest(" and primary.user.userId='" + localUser.getUserId() + "' ");
				Page localPage = getDao().getPage((PageRequest) localObject2, new MyMenu());
				if ((localPage.getResult() != null) && (localPage.getResult().size() > 0)) {
					ArrayList localArrayList = new ArrayList();
					Iterator localIterator = localPage.getResult().iterator();
					while (localIterator.hasNext()) {
						StandardModel localStandardModel = (StandardModel) localIterator.next();
						MyMenu localMyMenu = (MyMenu) localStandardModel;
						int j = 0;
						if ((paramArrayOfString != null) && (paramArrayOfString.length > 0)) {
							for (String str : paramArrayOfString) {
								if (localMyMenu.getRight().getId().longValue() == Tools.strToLong(str)) {
									j = 1;
								}
							}
						}
						if (j == 0) {
							localArrayList.add(localMyMenu);
						}
					}
					getDao().deleteBYBulk(localArrayList);
				}
			}
		}
		Object localObject1 = new HashSet();
		if ((paramArrayOfString != null) && (paramArrayOfString.length > 0)) {
			for (int i = 0; i < paramArrayOfString.length; i++) {
				localObject2 = new Right();
				((Right) localObject2).setId(Long.valueOf(Long.parseLong(paramArrayOfString[i])));
				localObject2 = (Right) get((StandardModel) localObject2);
				if (localObject2 != null) {
					((Set) localObject1).add(localObject2);
				}
			}
		}
		paramRole.setRightSet((Set) localObject1);
		update(paramRole);
	}

	public Set<Role> getRoleByWarehouseID(String paramString) {
		LinkedHashSet localLinkedHashSet = new LinkedHashSet();
		PageRequest localPageRequest = new PageRequest(" and primary.warehouseID = '" + paramString + "'");
		localPageRequest.setSortColumns(" order by id ");
		localPageRequest.setPageSize(100000);
		Page localPage = getPage(localPageRequest, new Role());
		List localList = localPage.getResult();
		Iterator localIterator = localList.iterator();
		while (localIterator.hasNext()) {
			StandardModel localStandardModel = (StandardModel) localIterator.next();
			localLinkedHashSet.add((Role) localStandardModel);
		}
		return localLinkedHashSet;
	}
}

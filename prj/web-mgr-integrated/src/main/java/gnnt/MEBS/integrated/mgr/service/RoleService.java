package gnnt.MEBS.integrated.mgr.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
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

	public void saveRoleRights(Role paramRole, String[] paramArrayOfString1, String[] paramArrayOfString2) {
		if ((paramArrayOfString2 != null) && (paramArrayOfString2.length > 0)) {
			String str1 = "";
			for (String localObject4 : paramArrayOfString2) {
				if (str1.trim().length() > 0) {
					str1 = str1 + ",";
				}
				str1 = str1 + (String) localObject4;
			}
			Object localObject3;
			if ((paramRole.getUserSet() != null) && (paramRole.getUserSet().size() > 0)) {
				Iterator it = paramRole.getUserSet().iterator();
				while (((Iterator) it).hasNext()) {
					User localObject2 = (User) ((Iterator) it).next();
					localObject3 = new PageRequest(
							" and primary.user.userId='" + ((User) localObject2).getUserId() + "' and primary.right.moduleId in(" + str1 + ") ");
					Page<StandardModel> localObject4 = getDao().getPage((PageRequest) localObject3, new MyMenu());
					if ((((Page) localObject4).getResult() != null) && (((Page) localObject4).getResult().size() > 0)) {
						ArrayList localArrayList = new ArrayList();
						Iterator localIterator = ((Page) localObject4).getResult().iterator();
						while (localIterator.hasNext()) {
							StandardModel localStandardModel = (StandardModel) localIterator.next();
							MyMenu localMyMenu = (MyMenu) localStandardModel;
							int i1 = 0;
							if ((paramArrayOfString1 != null) && (paramArrayOfString1.length > 0)) {
								for (String str2 : paramArrayOfString1) {
									if (localMyMenu.getRight().getId().longValue() == Tools.strToLong(str2)) {
										i1 = 1;
									}
								}
							}
							if (i1 == 0) {
								localArrayList.add(localMyMenu);
							}
						}
						getDao().deleteBYBulk(localArrayList);
					}
				}
			}
			Set set = new HashSet();
			Object localObject2 = paramRole.getRightSet().iterator();
			while (((Iterator) localObject2).hasNext()) {
				localObject3 = (Right) ((Iterator) localObject2).next();
				int m = 1;
				for (int n = 0; n < paramArrayOfString2.length; n++) {
					if (((Right) localObject3).getModuleId().intValue() == Tools.strToLong(paramArrayOfString2[n])) {
						m = 0;
						break;
					}
				}
				if (m != 0) {
					((Set) set).add(localObject3);
				}
			}
			if ((paramArrayOfString1 != null) && (paramArrayOfString1.length > 0)) {
				for (int j = 0; j < paramArrayOfString1.length; j++) {
					localObject3 = new Right();
					((Right) localObject3).setId(Long.valueOf(Long.parseLong(paramArrayOfString1[j])));
					localObject3 = (Right) get((StandardModel) localObject3);
					if (localObject3 != null) {
						((Set) set).add(localObject3);
					}
				}
			}
			paramRole.setRightSet((Set) set);
			update(paramRole);
		}
	}
}

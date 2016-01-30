package gnnt.MEBS.common.mgr.service;

import gnnt.MEBS.common.mgr.common.Page;
import gnnt.MEBS.common.mgr.common.PageRequest;
import gnnt.MEBS.common.mgr.model.Role;
import gnnt.MEBS.common.mgr.model.StandardModel;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * 角色service
 * 
 * @author xuejt
 * 
 */
@Service("com_roleService")
@Transactional(propagation = Propagation.REQUIRED, readOnly = false,rollbackFor=Exception.class)
public class RoleService extends StandardService {
	/**
	 * 获取所有角色
	 * 
	 * @return
	 */
	public Set<Role> getAllRole() {
		// 所有角色
		Set<Role> allRoleSet = new LinkedHashSet<Role>();

		PageRequest<String> pageRequest = new PageRequest<String>(" ");
		pageRequest.setSortColumns(" order by id ");
		pageRequest.setPageSize(100000);
		Page<StandardModel> page = this.getPage(pageRequest, new Role());
		List<StandardModel> allRoleList = page.getResult();
		for (StandardModel model : allRoleList) {
			allRoleSet.add((Role) model);
		}
		return allRoleSet;
	}
}

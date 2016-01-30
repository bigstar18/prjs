package gnnt.MEBS.common.mgr.service;

import gnnt.MEBS.common.mgr.common.Global;
import gnnt.MEBS.common.mgr.common.Page;
import gnnt.MEBS.common.mgr.common.PageRequest;
import gnnt.MEBS.common.mgr.dao.RightDao;
import gnnt.MEBS.common.mgr.dao.StandardDao;
import gnnt.MEBS.common.mgr.model.Right;
import gnnt.MEBS.common.mgr.model.StandardModel;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * 权限service
 * 
 * @author xuejt
 * 
 */
@Service("com_rightService")
@Transactional(propagation = Propagation.REQUIRED, readOnly = false,rollbackFor=Exception.class)
public class RightService extends StandardService {

	@Autowired
	@Qualifier("com_rightDao")
	private RightDao rightDao;

	@Override
	public StandardDao getDao() {
		return rightDao;
	}

	/**
	 * 根据 id 获取权限信息<br>
	 * 返回权限信息的子权限使用子过滤器过滤<br>
	 * 子过滤器 信息 (TYPE=:-1 or TYPE=:0)用来过滤子权限集合<br>
	 * type说明 -2：无需判断权限的URL -1： 父菜单类型 0：子菜单类型 1：页面内增删改查权限
	 * 
	 * @param id
	 *            权限id
	 * @return 权限
	 */
	public Right getRightBySubFilter(long id) {
		return rightDao.getRightBySubFilter(id, -1, 0);
	}

	/**
	 * 获取权限树<br>
	 * 返回权限信息的子权限使用子过滤器过滤<br>
	 * 子过滤器 信息 (TYPE!=:-2 and VISIBLE=:0)用来过滤子菜单集合<br>
	 * type说明 -2：无需判断权限的URL -1： 父菜单类型 0：子菜单类型 1：页面内增删改查权限<br>
	 * visible说明 0 可见 其它不可见
	 * 
	 * @return 权限
	 */
	public Right loadRightTree() {
		return rightDao.loadRightTree(Global.ROOTRIGHTID, -2, 0);
	}

	/**
	 * 获取所有权限
	 * 
	 * @return
	 */
	public Set<Right> getAllRight() {
		// 所有权限
		Set<Right> allRightSet = new HashSet<Right>();

		PageRequest<String> pageRequest = new PageRequest<String>(
				" and type!=-2 ");
		pageRequest.setPageSize(100000);
		Page<StandardModel> page = this.getPage(pageRequest, new Right());
		List<StandardModel> allRoleList = page.getResult();
		for (StandardModel model : allRoleList) {
			allRightSet.add((Right) model);
		}
		return allRightSet;
	}
}

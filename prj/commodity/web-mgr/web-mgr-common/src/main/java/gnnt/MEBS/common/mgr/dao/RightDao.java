package gnnt.MEBS.common.mgr.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import gnnt.MEBS.common.mgr.model.Right;

/**
 * 权限Dao
 * 
 * @author xuejt
 * 
 */
@Repository("com_rightDao")
public class RightDao extends StandardDao {
	/**
	 * 根据 id 和子过滤器获取权限<br>
	 * 子过滤器 信息 (TYPE=:type1 or TYPE=:type2)用来过滤子权限集合<br>
	 * type说明 -2：无需判断权限的URL -1： 父菜单类型 0：子菜单类型 1：页面内增删改查权限
	 * 
	 * @param id
	 *            权限id
	 * @param type1
	 *            过滤器中类型1
	 * @param type2
	 *            过滤器中类型2
	 * @return 权限
	 */
	@SuppressWarnings("unchecked")
	public Right getRightBySubFilter(long id, int type1, int type2) {
		String queryString = "from Right as model where model." + "id= ?";

		getHibernateTemplate().enableFilter("rightFilter").setParameter(
				"type1", type1).setParameter("type2", type2);

		List list = getHibernateTemplate().find(queryString, id);
		if (list.size() > 0) {
			return (Right) list.get(0);
		} else
			return null;
	}

	/**
	 * 根据 id 和子过滤器获取权限<br>
	 * 子过滤器 信息 (TYPE!=:type and VISIBLE=:visible)用来过滤子菜单集合<br>
	 * type说明 -2：无需判断权限的URL -1： 父菜单类型 0：子菜单类型 1：页面内增删改查权限<br>
	 * visible说明 0 可见 其它不可见
	 * 
	 * @param id
	 *            权限id
	 * @param type
	 *            过滤器中的类型
	 * @param visible
	 *            过滤器中的权限可见性
	 * @return 权限
	 */
	@SuppressWarnings("unchecked")
	public Right loadRightTree(long id, int type, int visible) {
		String queryString = "from Right as model where model." + "id= ?";

		getHibernateTemplate().enableFilter("rightTreeFilter").setParameter(
				"type", type).setParameter("visible", visible);

		List list = getHibernateTemplate().find(queryString, id);
		if (list.size() > 0) {
			return (Right) list.get(0);
		} else
			return null;
	}
}

package gnnt.MEBS.common.mgr.dao;

import gnnt.MEBS.common.mgr.model.Menu;

import java.util.List;

import org.springframework.stereotype.Repository;

/**
 * 菜单Dao
 * 
 * @author xuejt
 * 
 */
@Repository("com_menuDao")
public class MenuDao extends StandardDao {

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
	@SuppressWarnings("unchecked")
	public List<Menu> getMenuBySubFilter(int type1, int type2, int visible) {
		// 过滤出parentID==-1 的菜单 不过滤 以下映射会报错
		// <many-to-one name="parentMenu"
		// column="PARENTID" not-null="false"
		// class="gnnt.MEBS.common.mgr.security.model.Menu">
		// </many-to-one>
		String queryString = "from Menu as model where model.parentID!=-1 order by model.moduleId,model.seq ";

		getHibernateTemplate().enableFilter("menuFilter").setParameter("type1",
				type1).setParameter("type2", type2).setParameter("visible",
				visible).setParameter("m1", 0).setParameter("m2", 6);

		return getHibernateTemplate().find(queryString);
	}

	/**
	 * 根据菜单代码和子过滤器获取菜单
	 * <P>
	 * 子过滤器 信息 (TYPE=:type1 or TYPE=:type2) and VISIBLE=:visible and MODULEID
	 * in(:moduleID)用来过滤子菜单集合<br>
	 * type说明 -2：无需判断权限的URL -1： 父菜单类型 0：子菜单类型 1：页面内增删改查权限<br>
	 * visible说明 0 可见 其它不可见
	 * moduleID为系统的模块号
	 * 
	 * @param id
	 *            菜单代码
	 * @param type1
	 *            过滤器中的type1
	 * @param type2
	 *            过滤器中的type2
	 * @param visible
	 *            菜单可见性 0 可见 其它不可见
	 * @param moduleList
	 *            过滤器中的moduleID moduleID是模块号
	 * @return 菜单
	 */
	@SuppressWarnings("unchecked")
	public Menu getMenuById(long id, int type1, int type2, int visible,List<Integer> moduleList) {
		String queryString = "from Menu as model where model.id= ? order by seq ";

		getHibernateTemplate().enableFilter("menuFilter").setParameter("type1",
				type1).setParameter("type2", type2).setParameter("visible",
				visible).setParameterList("moduleID", moduleList);
		List list = getHibernateTemplate().find(queryString, id);
		if (list.size() > 0) {
			return (Menu) list.get(0);
		} else
			return null;
	}
}

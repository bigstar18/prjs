package cn.com.agree.eteller.usermanager.dao.hibernate;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import cn.com.agree.eteller.generic.utils.ComFunction;
import cn.com.agree.eteller.generic.utils.Pagination;
import cn.com.agree.eteller.usermanager.dao.IUserManagerDao;
import cn.com.agree.eteller.usermanager.persistence.Department;
import cn.com.agree.eteller.usermanager.persistence.DepartmentRole;
import cn.com.agree.eteller.usermanager.persistence.EtellerSubappinfo;
import cn.com.agree.eteller.usermanager.persistence.Rolelist;
import cn.com.agree.eteller.usermanager.persistence.Userlist;

public class UserManagerDao extends HibernateDaoSupport implements IUserManagerDao {
	public void add(Object obj) {
		getHibernateTemplate().save(obj);
	}

	public void update(Object obj) {
		getHibernateTemplate().update(obj);
	}

	public void delete(Object obj) {
		getHibernateTemplate().delete(obj);
	}

	public List getBranchList() {
		return getHibernateTemplate().find("FROM Department dep WHERE dep.type=?", new Object[] { "1", Hibernate.STRING });
	}

	public List getRootDepartmentList() {
		return getHibernateTemplate().find("FROM Department dep WHERE dep.superiorDepartmentId='' or dep.superiorDepartmentId is null");
	}

	public List getDepartmentList() {
		return getHibernateTemplate().loadAll(Department.class);
	}

	public List getRoleList() {
		return getHibernateTemplate().loadAll(Rolelist.class);
	}

	public List getRoleList(final String departmentId) {
		return getHibernateTemplate().executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				List list = UserManagerDao.this.getHibernateTemplate()
						.find("FROM DepartmentRole deprole WHERE deprole.departmentId='" + (departmentId == null ? "" : departmentId) + "'");
				List listTemp = new ArrayList();
				List listReturn = new ArrayList();
				if (list != null) {
					for (int k = 0; k < list.size(); k++) {
						DepartmentRole depr = (DepartmentRole) list.get(k);
						List list1 = UserManagerDao.this.getHibernateTemplate().find("FROM Rolelist role WHERE role.roleId=" + depr.getRoleId());
						listTemp.addAll(list1);
						if (list1 != null) {
							Rolelist role = (Rolelist) list1.get(0);
							listTemp = UserManagerDao.this.getSubRoleList_final(session, role);
							listTemp.add(0, role);
						}
						if (k == 0) {
							listReturn.clear();
							listReturn.addAll(listTemp);
						} else {
							listReturn.addAll(listTemp);
						}
					}
					for (int k = 0; k < listReturn.size(); k++) {
						int i = 0;
						Object ObTemp = listReturn.get(k);
						while ((i = listReturn.lastIndexOf(ObTemp)) != k) {
							listReturn.remove(i);
						}
					}
					return listReturn;
				}
				return new ArrayList();
			}
		});
	}

	private List getSubRoleList_final(Session session, Rolelist role) throws HibernateException {
		ArrayList list = new ArrayList();
		if (role != null) {
			List roles = getHibernateTemplate().find("FROM Rolelist role WHERE  role.superiorRoleId=" + role.getRoleId());
			list.addAll(roles);
			for (int i = 0; i < roles.size(); i++) {
				list.addAll(getSubRoleList_final(session, (Rolelist) roles.get(i)));
			}
		}
		return list;
	}

	public Department getDepartment(Serializable departmentId) {
		return (Department) getHibernateTemplate().get(Department.class, departmentId);
	}

	public List getRoleUnderlingListFromUser(final Serializable userId) {
		return getHibernateTemplate().executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				return UserManagerDao.this.getRoleUnderlingListFromUser(session, userId);
			}
		});
	}

	public List getRoleUnderlingList(final Long roleId) {
		return getHibernateTemplate().executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				return UserManagerDao.this.getRoleUnderlingList(session, roleId);
			}
		});
	}

	private List getRoleUnderlingList(Session session, Long roleId) throws HibernateException {
		ArrayList underlingRoles = new ArrayList();

		List<Rolelist> list = getHibernateTemplate().find("FROM Rolelist role WHERE role.superiorRoleId=" + roleId);

		underlingRoles.addAll(list);
		for (int i = 0; i < list.size(); i++) {
			underlingRoles.addAll(getRoleUnderlingList(session, ((Rolelist) list.get(i)).getRoleId()));
		}
		return underlingRoles;
	}

	public List getRoleUnderlingListInDepartment(Long roleId, String departmentId) {
		ArrayList underlingRoleListInDepartment = new ArrayList();
		Department department = getDepartment(departmentId);
		List underlingRoleList = getRoleUnderlingList(roleId);
		for (int i = 0; i < underlingRoleList.size(); i++) {
			Rolelist role = (Rolelist) underlingRoleList.get(i);
			if (role.getDepartments().contains(department)) {
				underlingRoleListInDepartment.add(role);
			}
		}
		return underlingRoleListInDepartment;
	}

	private List getRoleUnderlingListFromUser(Session session, Serializable userId) throws HibernateException {
		if ("root".equals(userId)) {
			return session.createCriteria(Rolelist.class).list();
		}
		ArrayList underlingRoles = new ArrayList();
		Userlist currentUser = (Userlist) session.createQuery("FROM Userlist user WHERE user.userId='" + userId + "'").uniqueResult();
		Long roleId = currentUser.getRoleId();
		underlingRoles.addAll(getRoleUnderlingList(session, roleId));
		return underlingRoles;
	}

	public List getRoleUnderlingWithCurrentList(final Long roleId) {
		return getHibernateTemplate().executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				return UserManagerDao.this.getRoleUnderlingWithCurrentList(session, roleId);
			}
		});
	}

	private List getRoleUnderlingWithCurrentList(Session session, Long roleId) throws HibernateException {
		ArrayList underlingRoles = new ArrayList();
		Rolelist currentRole = (Rolelist) session.createQuery("FROM Rolelist role WHERE role.roleId=" + roleId).uniqueResult();
		underlingRoles.add(currentRole);
		underlingRoles.addAll(getRoleUnderlingList(session, roleId));
		return underlingRoles;
	}

	public List getRoleUnderlingWithCurrentList(final Long roleId, final boolean isFinal) {
		return getHibernateTemplate().executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				return UserManagerDao.this.getRoleUnderlingWithCurrentList(session, roleId, isFinal);
			}
		});
	}

	private List getRoleUnderlingWithCurrentList(Session session, Long roleId, boolean isFinal) throws HibernateException {
		ArrayList underlingRoles = new ArrayList();
		Rolelist currentRole = (Rolelist) session.createQuery("FROM Rolelist role WHERE role.roleId=" + roleId).uniqueResult();
		underlingRoles.add(currentRole);
		underlingRoles.addAll(getRoleUnderlingList(session, roleId, isFinal));
		return underlingRoles;
	}

	private List getRoleUnderlingList(Session session, Long roleId, boolean isFinal) throws HibernateException {
		ArrayList underlingRoles = new ArrayList();
		List list = getHibernateTemplate()
				.find("FROM Rolelist role WHERE role.superiorRoleId=" + roleId + " AND role.finalFlag=" + (isFinal ? "'1'" : "'0'"));
		underlingRoles.addAll(list);
		for (int i = 0; i < list.size(); i++) {
			underlingRoles.addAll(getRoleUnderlingList(session, ((Rolelist) list.get(i)).getRoleId()));
		}
		return underlingRoles;
	}

	public List getUnderlingList(final Serializable userId, final Map condition) {
		return getHibernateTemplate().executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Userlist currentUser = (Userlist) session.createQuery("FROM Userlist user WHERE user.userId='" + userId + "'").uniqueResult();
				List underlingRoles;
				if ("root".equals(userId)) {
					underlingRoles = UserManagerDao.this.getRoleList();
				} else {
					underlingRoles = UserManagerDao.this.getRoleUnderlingWithCurrentList(session, currentUser.getRoleId());
				}
				List subDepartmentList;
				if ("root".equals(userId)) {
					subDepartmentList = UserManagerDao.this.getDepartmentList();
				} else {
					subDepartmentList = UserManagerDao.this.getSubDepartmentWithCurrentList(currentUser.getDepartmentId());
				}
				String depIds = "";
				for (int i = 0; i < subDepartmentList.size(); i++) {
					String id = ((Department) subDepartmentList.get(i)).getId();
					if (i > 0) {
						depIds = depIds + ",";
					}
					depIds = depIds + "'" + id + "'";
				}
				String roleIds = "";
				for (int i = 0; i < underlingRoles.size(); i++) {
					Rolelist role = (Rolelist) underlingRoles.get(i);
					if (i > 0) {
						roleIds = roleIds + ",";
					}
					roleIds = roleIds + role.getRoleId();
				}
				if ((roleIds == null) || ("".equals(roleIds)) || (depIds == null) || ("".equals(depIds))) {
					return new ArrayList();
				}
				if (currentUser.getRoleId() != null) {
					roleIds = roleIds + "," + currentUser.getRoleId();
				}
				String sql = "SELECT user FROM Userlist user WHERE 1=1";
				if (!"root".equals(userId)) {
					sql = sql + " AND NOT (user.roleId=" + currentUser.getRoleId() + " AND user.departmentId='" + currentUser.getDepartmentId()
							+ "') AND";
				}
				sql = sql + " AND user.roleId in (" + roleIds + ")" + " AND user.departmentId in (" + depIds + ")";
				if (condition != null) {
					String condition_departmentId = (String) condition.get("departmentId");
					if ((condition_departmentId != null) && (!"".equals(condition_departmentId))) {
						sql = sql + " AND user.departmentId='" + condition_departmentId + "'";
					}
					String condition_userId = (String) condition.get("userId");
					if ((condition_userId != null) && (!"".equals(condition_userId))) {
						sql = sql + " AND user.userId like '%" + condition_userId + "%'";
					}
					String condition_tellerName = (String) condition.get("tellerName");
					if ((condition_tellerName != null) && (!"".equals(condition_tellerName))) {
						sql = sql + " AND user.tellerName like '%" + condition_tellerName + "%'";
					}
					if ((condition.get("roleId") != null) && (!"".equals(condition.get("roleId")))) {
						Long condition_roleId = new Long((String) condition.get("roleId"));
						if ((condition_roleId != null) && (!"".equals(condition.get("roleId")))) {
							sql = sql + " AND user.roleId=" + condition_roleId;
						}
					}
				}
				List uList = UserManagerDao.this.getSession().createQuery(sql).list();

				List<Object> dataList = new ArrayList(3);
				dataList.add(uList);
				dataList.add(underlingRoles);
				dataList.add(subDepartmentList);
				return dataList;
			}
		});
	}

	public int countDepartmentDeepness(final String departmentId) {

		return ((Integer) getHibernateTemplate().execute(new HibernateCallback() {
			private int deepness = 0;

			private void countDeepness(Session session, String departmentId) throws HibernateException {
				if ((departmentId == null) || ("".equals(departmentId.trim()))) {
					return;
				}
				Department dep = (Department) session.createQuery("FROM Department dep WHERE dep.id='" + departmentId + "'").uniqueResult();
				String superiorDepartmentId = dep.getSuperiorDepartmentId();
				this.deepness += 1;
				countDeepness(session, superiorDepartmentId);
			}

			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				countDeepness(session, departmentId);
				return new Integer(this.deepness);
			}
		})).intValue();
	}

	public int countDepartmentDeepness(final Department department) {

		return ((Integer) getHibernateTemplate().execute(new HibernateCallback() {
			private int deepness = 1;

			private void countDeepness(Session session, Department department) throws HibernateException {
				String superiorDepartmentId = department.getSuperiorDepartmentId();
				if ((superiorDepartmentId == null) || ("".equals(superiorDepartmentId))) {
					return;
				}
				this.deepness += 1;
				Department superiorDepartment = (Department) session.createQuery("FROM Department dep WHERE dep.id='" + superiorDepartmentId + "'")
						.uniqueResult();
				countDeepness(session, superiorDepartment);
			}

			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				countDeepness(session, department);
				return new Integer(this.deepness);
			}
		})).intValue();
	}

	private List getSubDepartmentList(Session session, Department superiorDepartment, Map<String, String> map) throws HibernateException {
		ArrayList<Department> list = new ArrayList();
		if (superiorDepartment != null) {
			Criteria c = getSession().createCriteria(Department.class).add(Restrictions.eq("superiorDepartmentId", superiorDepartment.getId()));
			if (map != null) {
				if ((map.get("id") != null) && (!((String) map.get("id")).equals(""))) {
					c.add(Restrictions.like("id", (String) map.get("id"), MatchMode.START));
				}
				if ((map.get("name") != null) && (!((String) map.get("name")).equals(""))) {
					c.add(Restrictions.like("name", (String) map.get("name"), MatchMode.START));
				}
			}
			List<Department> departments = c.list();
			if ((departments != null) && (map != null)) {
				for (Department d : departments) {
					d.setSuperiorDept(superiorDepartment);
				}
			}
			list.addAll(departments);
			for (int i = 0; i < departments.size(); i++) {
				Object subList = getSubDepartmentList(session, (Department) departments.get(i), map);
				list.addAll((Collection) subList);
			}
		}
		return list;
	}

	private List getSubDepartmentList_final(Session session, Department superiorDepartment) throws HibernateException {
		ArrayList list = new ArrayList();
		if (superiorDepartment != null) {
			List departments = getHibernateTemplate().find("FROM Department dep WHERE dep.finalFlag='0' and dep.superiorDepartmentId='"
					+ superiorDepartment.getId() + "' ORDER BY dep.id DESC");
			list.addAll(departments);
			for (int i = 0; i < departments.size(); i++) {
				list.addAll(getSubDepartmentList_final(session, (Department) departments.get(i)));
			}
		}
		return list;
	}

	public List getSubDepartmentList(final String departmentId, final Map<String, String> map) {
		return getHibernateTemplate().executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				List list = UserManagerDao.this.getSubDepartmentList(
						session, (Department) session
								.createQuery("FROM Department dep WHERE dep.id='" + (departmentId == null ? "" : departmentId) + "'").uniqueResult(),
						map);
				return list;
			}
		});
	}

	public List getSubDepartmentList1(final String departmentId, final Department dep) {
		final List ls = new ArrayList();
		try {
			getHibernateTemplate().execute(new HibernateCallback() {
				public Object doInHibernate(Session session) throws HibernateException, SQLException {
					Criteria criteria = session.createCriteria(Department.class);
					if (departmentId != null) {
						criteria.add(Expression.eq("superiorDepartmentId", departmentId));
					}
					if (!dep.getId().equals("00000")) {
						criteria.add(Expression.eq("id", dep.getId()));
					}
					if (!dep.getDepnotype().equals("00000")) {
						criteria.add(Expression.eq("depnotype", dep.getDepnotype()));
					}
					if (!dep.getName().equals("00000")) {
						criteria.add(Expression.like("name", "%" + dep.getName() + "%"));
					}
					ls.addAll(criteria.list());
					return null;
				}
			});
		} catch (Exception localException) {
		}
		return ls;
	}

	public List getSubDepartmentWithCurrentList(final String departmentId) {
		return getHibernateTemplate().executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Department department = (Department) session
						.createQuery("FROM Department dep WHERE dep.id='" + (departmentId == null ? "" : departmentId) + "'").uniqueResult();
				if (department != null) {
					long time1 = System.currentTimeMillis();
					List list = UserManagerDao.this.getSubDepartmentList(session, department, null);
					long time2 = System.currentTimeMillis();
					System.out.println("获取所有子机构列表用时：" + (time2 - time1) + "毫秒");
					list.add(0, department);
					return list;
				}
				return new ArrayList();
			}
		});
	}

	public List getSubDepartmentWithCurrentList_final(final String departmentId) {
		return getHibernateTemplate().executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Department department = (Department) session
						.createQuery("FROM Department dep WHERE dep.id='" + (departmentId == null ? "" : departmentId) + "' ORDER BY dep.id DESC")
						.uniqueResult();
				if (department != null) {
					List list = UserManagerDao.this.getSubDepartmentList_final(session, department);
					list.add(0, department);
					return list;
				}
				return new ArrayList();
			}
		});
	}

	public List getDirectSubDepartmentList(String departmentId) {
		return getHibernateTemplate().find("FROM Department dep WHERE dep.superiorDepartmentId='" + departmentId + "'");
	}

	public List getDirectSubDepartmentWithCurrentList(String departmentId) {
		return getHibernateTemplate()
				.find("FROM Department dep WHERE dep.id='" + departmentId + "' OR dep.superiorDepartmentId='" + departmentId + "'");
	}

	public Department getDepartmentByNewSystemNo(String newSystemNo) {
		if ((newSystemNo == null) || (newSystemNo.length() == 0)) {
			return null;
		}
		List list = getHibernateTemplate().find("FROM Department dep WHERE dep.newSystemNo=?", new Object[] { newSystemNo, Hibernate.STRING });
		if ((list == null) || (list.size() == 0)) {
			return null;
		}
		return (Department) list.get(0);
	}

	public List getRoleListInDepartment(String departmentId) {
		return getHibernateTemplate()
				.find("SELECT role FROM Rolelist role, DepartmentRole dr WHERE role.roleId=dr.roleId AND dr.departmentId='" + departmentId + "'");
	}

	public boolean hasRoleUnderling(Serializable roleId) {
		return ((Number) getHibernateTemplate().find("SELECT COUNT(*) FROM Rolelist role WHERE role.superiorRoleId=" + roleId).get(0)).intValue() > 0;
	}

	public List getDeptListByMap(Map<String, Object> map, Pagination page) {
		Criteria c = getSession().createCriteria(Department.class);
		if ((map.get("id") != null) && (!map.get("id").equals(""))) {
			c.add(Restrictions.like("id", (String) map.get("id"), MatchMode.ANYWHERE));
		}
		if ((map.get("name") != null) && (!map.get("name").equals(""))) {
			c.add(Restrictions.like("name", (String) map.get("name"), MatchMode.ANYWHERE));
		}
		c.setProjection(Projections.count("id"));
		page.setAllRecords((Integer) c.uniqueResult());
		c.setProjection(null);
		return c.setFirstResult(page.getFirstRecord().intValue()).setMaxResults(page.getPerPageRecords().intValue()).list();
	}

	public List<EtellerSubappinfo> getBySubAppCondition(EtellerSubappinfo subApp, Pagination page) {
		Criteria c = getSession().createCriteria(EtellerSubappinfo.class);
		if ((subApp != null) && (subApp.getComp_id() != null) && (!ComFunction.isEmpty(subApp.getComp_id().getAppid()))) {
			c.add(Restrictions.eq("comp_id.appid", subApp.getComp_id().getAppid()));
		}
		if ((subApp != null) && (subApp.getAppname() != null)) {
			c.add(Restrictions.like("appname", subApp.getAppname(), MatchMode.ANYWHERE));
		}
		page.setAllRecords((Integer) c.setProjection(Projections.count("appname")).uniqueResult());
		return c.setProjection(null).setFirstResult(page.getFirstRecord().intValue()).setMaxResults(page.getPerPageRecords().intValue()).list();
	}
}

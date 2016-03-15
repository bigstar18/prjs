package cn.com.agree.eteller.usermanager.dao.hibernate;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Expression;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import cn.com.agree.eteller.generic.exception.ServiceException;
import cn.com.agree.eteller.usermanager.dao.IRolelistDao;
import cn.com.agree.eteller.usermanager.persistence.Department;
import cn.com.agree.eteller.usermanager.persistence.Funclist;
import cn.com.agree.eteller.usermanager.persistence.Rolelist;

public class RolelistDao extends HibernateDaoSupport implements IRolelistDao {
	public boolean addRole(final Rolelist role, final Funclist[] funclist) {
		boolean isAdded = true;
		try {
			getHibernateTemplate().execute(new HibernateCallback() {
				public Object doInHibernate(Session session) throws HibernateException {
					Set set = new HashSet();
					for (int i = 0; i < funclist.length; i++) {
						set.add(funclist[i]);
					}
					role.setFunctions(set);
					session.save(role);
					return null;
				}
			});
			isAdded = true;
		} catch (Exception ex) {
			ex.printStackTrace();
			isAdded = false;
		}
		return isAdded;
	}

	public Rolelist[] getAllRole() {
		List list = getHibernateTemplate().loadAll(Rolelist.class);
		return (Rolelist[]) list.toArray(new Rolelist[0]);
	}

	public List getRoles(final Long[] roleIds) {
		return (List) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Criteria criteria = session.createCriteria(Rolelist.class);
				for (int i = 0; i < roleIds.length; i++) {
					criteria.add(Expression.eq("roleId", roleIds[i]));
				}
				return criteria.list();
			}
		});
	}

	public Rolelist getRole(Serializable id) {
		return (Rolelist) getHibernateTemplate().get(Rolelist.class, id);
	}

	public void checkCycleSuperior(Rolelist role) throws Exception {
		Serializable roleId = role.getRoleId();
		Serializable superiorRoleId = role.getSuperiorRoleId();
		if ((superiorRoleId != null) && (!"".equals(superiorRoleId))) {
			Rolelist superiorRole = (Rolelist) getHibernateTemplate().get(Rolelist.class, superiorRoleId);
			String path = superiorRole.getRoleName();
			for (;;) {
				if (superiorRoleId.equals(roleId)) {
					throw new ServiceException("检测到循环引用上级级别(" + path + ")");
				}
				superiorRoleId = superiorRole.getSuperiorRoleId();
				if (superiorRoleId == null) {
					break;
				}
				superiorRole = (Rolelist) getHibernateTemplate().get(Rolelist.class, superiorRoleId);
				path = path + " -> " + superiorRole.getRoleName();
			}
		}
	}

	public void deleteRole(final Serializable roleId) {
		getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				if (RolelistDao.this.getHibernateTemplate().find("FROM Userlist user WHERE user.roleId=" + roleId).size() > 0) {
					throw new RuntimeException("系统中还有用户使用该级别");
				}
				if (((Long) RolelistDao.this.getHibernateTemplate().find("SELECT COUNT(role) FROM Rolelist role WHERE role.superiorRoleId=" + roleId)
						.get(0)).intValue() > 0) {
					throw new RuntimeException("该级别还有下属级别");
				}
				Rolelist currentRole = (Rolelist) session.get(Rolelist.class, roleId);
				if (currentRole != null) {
					try {
						Long newSuperiorRoleId = currentRole.getSuperiorRoleId();
						List roles = RolelistDao.this.getHibernateTemplate().find("FROM Rolelist role WHERE role.superiorRoleId=" + roleId);
						for (int i = 0; i < roles.size(); i++) {
							Rolelist role = (Rolelist) roles.get(i);
							role.setSuperiorRoleId(newSuperiorRoleId);
							session.update(role);
						}
						session.delete(currentRole);
					} catch (HibernateException e) {
						throw e;
					}
				}
				return null;
			}
		});
	}

	public List getRoleDepartmentList(final Serializable roleId, final List departmentList) {
		return getHibernateTemplate().executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				String depStr = "";
				for (int i = 0; i < departmentList.size(); i++) {
					if (!"".equals(depStr)) {
						depStr = depStr + ",";
					}
					depStr = depStr + "'" + ((Department) departmentList.get(i)).getId() + "'";
				}
				return RolelistDao.this.getHibernateTemplate()
						.find("SELECT dep FROM DepartmentRole dr, Department dep WHERE dep.id=dr.departmentId AND dr.roleId=" + roleId
								+ ("".equals(depStr) ? "" : new StringBuilder(" AND dr.departmentId in (").append(depStr).append(")").toString()));
			}
		});
	}

	public List getRootRoleList() {
		return getHibernateTemplate().find("FROM Rolelist role WHERE role.superiorRoleId is null");
	}

	public List getRoleNotFinalList() {
		return getHibernateTemplate().find("FROM Rolelist role WHERE role.finalFlag='0'");
	}
}

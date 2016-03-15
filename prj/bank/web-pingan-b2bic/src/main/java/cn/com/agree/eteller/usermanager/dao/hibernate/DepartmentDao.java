package cn.com.agree.eteller.usermanager.dao.hibernate;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.Vector;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Expression;
import org.hibernate.type.Type;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import cn.com.agree.eteller.generic.exception.ServiceException;
import cn.com.agree.eteller.usermanager.dao.IDepartmentDao;
import cn.com.agree.eteller.usermanager.persistence.Department;
import cn.com.agree.eteller.usermanager.persistence.Rolelist;

public class DepartmentDao extends HibernateDaoSupport implements IDepartmentDao {
	public boolean addDepartment(final Department ca) {
		boolean isAdded = true;
		try {
			getHibernateTemplate().execute(new HibernateCallback() {
				public Object doInHibernate(Session session) throws HibernateException {
					session.save(ca);
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

	public boolean updateDepartment(Department ca) {
		boolean isUpdated = false;
		try {
			getHibernateTemplate().merge(ca);
			isUpdated = true;
		} catch (Exception ex) {
			ex.printStackTrace();
			isUpdated = false;
		}
		return isUpdated;
	}

	public boolean deleteDepartment(final Department ca) {
		boolean isDeleted = false;
		try {
			getHibernateTemplate().execute(new HibernateCallback() {
				public Object doInHibernate(Session session) throws HibernateException, SQLException {
					session.delete(session.load(Department.class, ca.getId()));
					return null;
				}
			});
			isDeleted = true;
		} catch (Exception ex) {
			ex.printStackTrace();
			isDeleted = false;
		}
		return isDeleted;
	}

	public List getAllDep() {
		List ls = new ArrayList();
		ls = getHibernateTemplate().loadAll(Department.class);
		for (int i = 0; i < ls.size(); i++) {
		}
		return null;
	}

	public List getDep_finalFlag() {
		return getHibernateTemplate().find("FROM Department dep WHERE dep.finalFlag=? ORDER BY dep.id DESC", "0");
	}

	public Department[] getAllDepartment() {
		List ls = new ArrayList();
		try {
			ls = getHibernateTemplate().loadAll(Department.class);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return (Department[]) ls.toArray(new Department[0]);
	}

	public Department[] getAllDepartment1(final Department dep) {
		final List ls = new ArrayList();
		try {
			getHibernateTemplate().execute(new HibernateCallback() {
				public Object doInHibernate(Session session) throws HibernateException, SQLException {
					Criteria criteria = session.createCriteria(Department.class);
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
		return (Department[]) ls.toArray(new Department[0]);
	}

	public boolean isHaveTeller(final String depid) {
		final List list = new ArrayList();
		getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				List bp = DepartmentDao.this.getHibernateTemplate().find("from Userlist t where t.departmentId=?", depid);
				list.addAll(bp);
				return null;
			}
		});
		if (list.isEmpty()) {
			return false;
		}
		return true;
	}

	public boolean isHaveSubDep(final String depid) {
		final List list = new ArrayList();
		getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				List bp = DepartmentDao.this.getHibernateTemplate().find("from Department t where t.superiorDepartmentId=?", depid);
				list.addAll(bp);
				return null;
			}
		});
		if (list.isEmpty()) {
			return false;
		}
		return true;
	}

	public String getNewSysDepNo(String depno) {
		List list = getHibernateTemplate().find("from Department t where t.id=?", depno);
		if ((list == null) || (list.size() == 0)) {
			return null;
		}
		Department d = (Department) list.get(0);
		return d.getNewSystemNo();
	}

	public String getId(String newSysDepNo) {
		List list = getHibernateTemplate().find("from Department t where t.newSystemNo=?", new Object[] { newSysDepNo, Hibernate.STRING });
		if ((list == null) || (list.size() == 0)) {
			return null;
		}
		Department d = (Department) list.get(0);
		return d.getId();
	}

	public List getDepByType(String type) {
		List list = new ArrayList();
		if (type.equals("0")) {
			return list;
		}
		int i = Integer.parseInt(type) - 1;
		String ls = Integer.toString(i);
		list = getHibernateTemplate().find("from Department t where t.type=?", ls);
		return list;
	}

	public Department[] getDepartmentByType(String type) {
		if (type.equals("0")) {
			return new Department[0];
		}
		int i = Integer.parseInt(type) - 1;
		String ls = Integer.toString(i);
		List list = getHibernateTemplate().find("from Department t where t.type=?", ls);
		return (Department[]) list.toArray(new Department[0]);
	}

	public Department[] getDepartmentBySupDepId(String supDepId) {
		List list = getHibernateTemplate().find("from Department t where t.superiorDepartmentId=?", supDepId);
		return (Department[]) list.toArray(new Department[0]);
	}

	public Department[] getDepartmentByDepId(String depId) {
		List list = getHibernateTemplate().find("from Department t where t.id=?", depId);
		return (Department[]) list.toArray(new Department[0]);
	}

	public void checkCycleSuperior(Department dep) throws Exception {
		Serializable depId = dep.getId();
		Serializable superiorDepId = dep.getSuperiorDepartmentId();
		if ((superiorDepId != null) && (!superiorDepId.equals(""))) {
			Department superiorDep = (Department) getHibernateTemplate().get(Department.class, superiorDepId);
			String path = superiorDep.getName();
			for (;;) {
				if (superiorDepId.equals(depId)) {
					throw new ServiceException("检测到循环引用上级部门(" + path + ")");
				}
				superiorDepId = superiorDep.getSuperiorDepartmentId();
				if ((superiorDepId == null) || (superiorDepId.equals(""))) {
					break;
				}
				superiorDep = (Department) getHibernateTemplate().get(Department.class, superiorDepId);
				path = path + " -> " + superiorDep.getName();
			}
		}
	}

	public List getDepartmentRoleList(final Serializable departmentId) {
		return getHibernateTemplate().executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Department dep = (Department) session.createQuery("FROM Department dep WHERE dep.id='" + departmentId + "'").uniqueResult();
				Set roles = dep.getRoles();
				return new ArrayList(roles);
			}
		});
	}

	public void checkRoleUserList(String depid, List oldrole, List newrole) throws Exception {
		Rolelist[] orole = (Rolelist[]) null;
		Rolelist[] nrole = (Rolelist[]) null;
		if (oldrole != null) {
			if (!oldrole.isEmpty()) {
				orole = (Rolelist[]) oldrole.toArray(new Rolelist[0]);
			}
		} else {
			return;
		}
		if (newrole != null) {
			if (!newrole.isEmpty()) {
				nrole = (Rolelist[]) newrole.toArray(new Rolelist[0]);
			}
		} else {
			return;
		}
		Vector ovec = new Vector();
		for (int i = 0; i < orole.length; i++) {
			int k = 1;
			for (int j = 0; j < nrole.length; j++) {
				if (orole[i].getRoleId().equals(nrole[j].getRoleId())) {
					k = 0;
					break;
				}
			}
			if (k == 1) {
				ovec.addElement(orole[i]);
			}
		}
		for (int i = 0; i < ovec.size(); i++) {
			Object[] obj = new Object[2];
			obj[0] = new Object();
			obj[0] = depid;
			obj[1] = ((Rolelist) ovec.get(i)).getRoleId();
			Type[] type = new Type[2];
			type[0] = Hibernate.STRING;
			type[1] = Hibernate.LONG;
			List list = getHibernateTemplate().find("from Userlist t where t.departmentId=? and t.roleId=?", new Object[] { obj, type });
			if ((list != null) && (list.size() > 0)) {
				throw new Exception("检测到级别[" + ((Rolelist) ovec.get(i)).getRoleName() + "]存在用户");
			}
		}
	}

	public Department getUserDepnoInfo(String dptid) {
		List list = new ArrayList();
		try {
			list = getHibernateTemplate().find("from Department t where t.id=?", dptid);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		if (!list.isEmpty()) {
			return (Department) list.get(0);
		}
		return null;
	}
}

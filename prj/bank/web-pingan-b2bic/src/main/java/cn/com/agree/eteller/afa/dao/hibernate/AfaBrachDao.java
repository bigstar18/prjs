package cn.com.agree.eteller.afa.dao.hibernate;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import cn.com.agree.eteller.afa.dao.IAfaBrachDao;
import cn.com.agree.eteller.afa.persistence.AfaBranch;
import cn.com.agree.eteller.generic.utils.Pagination;

public class AfaBrachDao extends HibernateDaoSupport implements IAfaBrachDao {
	public boolean addAfaBrach(final AfaBranch ca) {
		boolean isAdded = false;
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

	public boolean deleteAfaBrach(final AfaBranch ca) {
		boolean isDeleted = false;
		try {
			getHibernateTemplate().execute(new HibernateCallback() {
				public Object doInHibernate(Session session) throws HibernateException, SQLException {
					session.delete(ca);
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

	public AfaBranch[] getAfaBrachByMap(Map map) {
		StringBuffer sql = new StringBuffer("select * from afa_branch ");
		if ((!map.get("type").toString().equals("xxxxx")) || (!map.get("branchno").toString().equals("xxxxx"))
				|| (!map.get("branchcname").toString().equals("xxxxx")) || (!map.get("note1").toString().equals("xxxxx"))) {
			sql.append("t where 1=1 ");
			if (!map.get("type").toString().equals("xxxxx")) {
				sql.append(" and t.type='" + (String) map.get("type") + "'");
			}
			if (!map.get("branchno").toString().equals("xxxxx")) {
				sql.append("and  t.branchno='" + (String) map.get("branchno") + "'");
			}
			if (!map.get("branchcname").toString().equals("xxxxx")) {
				String tmpStr = "'%" + map.get("branchcname") + "%'";
				sql.append("and  t.branchname like " + tmpStr);
			}
			if ((map.get("note1") != null) && (!map.get("note1").toString().equals("xxxxx"))) {
				sql.append("and  t.upbranchno='" + (String) map.get("note1") + "'");
				sql.append("and  t.branchno!='" + (String) map.get("note1") + "'");
			}
		}
		List list = new ArrayList();
		SessionFactory session = null;
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		Session ss = null;
		try {
			session = getHibernateTemplate().getSessionFactory();
			ss = session.openSession();
			con = ss.connection();
			st = con.createStatement();
			rs = st.executeQuery(sql.toString());
			while (rs.next()) {
				AfaBranch ab = new AfaBranch();
				try {
					ab.setBranchcode(rs.getString("branchcode"));
					ab.setBranchno(rs.getString("branchno"));
					ab.setType(rs.getString("type"));
					ab.setUpbranchno(rs.getString("upbranchno"));
					ab.setNote1(rs.getString("note1"));
					ab.setNote2(rs.getString("note2"));
					ab.setBranchname(rs.getString("branchname"));
					ab.setBranchnames(rs.getString("branchnames"));
				} catch (Exception e) {
					System.out.println("e==" + e);
				}
				list.add(ab);
			}
		} catch (Exception e) {
			e.printStackTrace();
			try {
				rs.close();
				st.close();
				ss.close();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		} finally {
			try {
				rs.close();
				st.close();
				ss.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return (AfaBranch[]) list.toArray(new AfaBranch[0]);
	}

	public AfaBranch[] getAllAfaBrach() {
		List ls = new ArrayList();
		try {
			ls = getHibernateTemplate().loadAll(AfaBranch.class);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return (AfaBranch[]) ls.toArray(new AfaBranch[0]);
	}

	public boolean updateAfaBrach(AfaBranch ca) {
		boolean isUpdated = false;
		StringBuffer sql = new StringBuffer("update  afa_branch ");

		String branchno = ca.getBranchno();
		String branchcode = ca.getBranchcode();
		sql.append(" set branchcode='" + branchcode + "',");
		String type = ca.getType();
		sql.append("  type='" + type + "',");
		String upbranchno = ca.getUpbranchno();
		sql.append("  upbranchno='" + upbranchno + "',");
		String branchnames = ca.getBranchnames();
		sql.append("  branchnames='" + branchnames + "',");
		String branchname = ca.getBranchname();
		sql.append("  branchname='" + branchname + "',");
		String note1 = ca.getNote1();
		sql.append("  note1='" + note1 + "',");
		String note2 = ca.getNote2();
		sql.append("  note2='" + note2 + "'");

		sql.append(" where branchno='" + branchno + "'");

		SessionFactory session = null;
		Connection con = null;
		Statement st = null;
		Session ss = null;
		try {
			session = getHibernateTemplate().getSessionFactory();
			ss = session.openSession();
			con = ss.connection();
			st = con.createStatement();
			st.executeUpdate(sql.toString());

			isUpdated = true;
		} catch (Exception ex) {
			ex.printStackTrace();
			isUpdated = false;
			try {
				st.close();
				ss.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} finally {
			try {
				st.close();
				ss.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return isUpdated;
	}

	public AfaBranch getAfaBrachBysql(String sql) {
		List list = new ArrayList();
		list = getHibernateTemplate().find(sql);
		return (AfaBranch) list.get(0);
	}

	public AfaBranch[] getAfaBrachBysql2(String sql) {
		List list = new ArrayList();
		list = getHibernateTemplate().find(sql);
		return (AfaBranch[]) list.toArray(new AfaBranch[0]);
	}

	public List<AfaBranch> getAfaBranchByMap(Map<String, String> map, Pagination page) {
		Criteria criteria = getSession(true).createCriteria(AfaBranch.class);
		if (map.get("branchno") != null) {
			criteria.add(Restrictions.eq("branchno", map.get("branchno")));
		}
		if (map.get("type") != null) {
			criteria.add(Restrictions.eq("type", map.get("type")));
		}
		if (map.get("branchname") != null) {
			criteria.add(Restrictions.like("branchname", (String) map.get("branchname"), MatchMode.ANYWHERE));
		}
		criteria.addOrder(Order.asc("branchno"));
		page.setAllRecords(Integer.valueOf(criteria.list().size()));
		criteria.setFirstResult(page.getFirstRecord().intValue());
		criteria.setMaxResults(page.getPerPageRecords().intValue());

		return criteria.list();
	}

	public String getBranchNameByNo(String branchno) {
		String name = "";
		String hql = "FROM AfaBranch ab WHERE ab.branchno = '" + branchno + "'";
		AfaBranch[] branchs = getAfaBrachBysql2(hql);
		if ((branchs != null) && (branchs.length > 0)) {
			name = branchs[0].getBranchname();
		}
		return name;
	}
}

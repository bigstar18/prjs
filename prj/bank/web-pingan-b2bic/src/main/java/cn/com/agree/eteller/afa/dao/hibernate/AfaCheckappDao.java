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
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import cn.com.agree.eteller.afa.dao.IAfaCheckappDao;
import cn.com.agree.eteller.afa.persistence.EtellerCheckapp;
import cn.com.agree.eteller.generic.utils.Pagination;

public class AfaCheckappDao extends HibernateDaoSupport implements IAfaCheckappDao {
	public EtellerCheckapp[] loadAfaCheckapp(final Map map) {
		final List list = new ArrayList();
		getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Criteria criteria = session.createCriteria(EtellerCheckapp.class);
				if (map.size() != 0) {
					if ((map.get("serinalno") != null) && (!map.get("serinalno").toString().equals(""))) {
						criteria.add(Expression.eq("serinalno", map.get("serinalno")));
					}
					if ((map.get("checkflag") != null) && (!map.get("checkflag").toString().equals(""))) {
						criteria.add(Expression.eq("checkflag", map.get("checkflag")));
					}
					if ((map.get("changdate") != null) && (!map.get("changdate").toString().equals(""))) {
						criteria.add(Expression.eq("changdate", map.get("changdate")));
					}
					if ((map.get("usrid") != null) && (!map.get("usrid").toString().equals(""))) {
						criteria.add(Expression.eq("usrid", map.get("usrid")));
					}
					if ((map.get("tablename") != null) && (!map.get("tablename").toString().equals(""))) {
						criteria.add(Expression.eq("tablename", map.get("tablename")));
					}
				}
				list.addAll(criteria.list());
				return null;
			}
		});
		return (EtellerCheckapp[]) list.toArray(new EtellerCheckapp[0]);
	}

	public String addAfaCheckapp(final EtellerCheckapp ca) {
		String serialno = String.valueOf(Integer.parseInt(getMaxserinalno()) + 1);
		ca.setSerinalno(serialno);

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
		if (isAdded) {
			return serialno;
		}
		return null;
	}

	public boolean deleteAfaCheckapp(final EtellerCheckapp ca) {
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

	public boolean updateAfaCheckapp(EtellerCheckapp ca) {
		boolean isUpdated = false;
		try {
			getHibernateTemplate().update(ca);
			isUpdated = true;
		} catch (Exception ex) {
			ex.printStackTrace();
			isUpdated = false;
		}
		return isUpdated;
	}

	private String getMaxserinalno() {
		String serinalno = "0";
		SessionFactory sf = null;
		Session session = null;
		Statement st = null;
		ResultSet rs = null;
		int serinalnoMax = 0;
		try {
			sf = getHibernateTemplate().getSessionFactory();
			session = sf.openSession();
			Connection con = session.connection();
			st = con.createStatement();
			String sql = "select t.serinalno from eteller_checkapp t";
			rs = st.executeQuery(sql);
			while (rs.next()) {
				String SerinalnoStr = rs.getString(1);
				int SeriTemp = Integer.parseInt(SerinalnoStr);
				if (SeriTemp > serinalnoMax) {
					serinalnoMax = SeriTemp;
					serinalno = SerinalnoStr;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			try {
				rs.close();
				st.close();
				if (session != null) {
					session.close();
				}
			} catch (Exception e1) {
				System.out.println(e1.getMessage());
				e1.printStackTrace();
			}
		} finally {
			try {
				rs.close();
				st.close();
				if (session != null) {
					session.close();
				}
			} catch (Exception e) {
				System.out.println(e.getMessage());
				e.printStackTrace();
			}
		}
		if (serinalno == null) {
			serinalno = "0";
		}
		return serinalno;
	}

	public EtellerCheckapp getEtellerCheckappBysql(final String sql) {
		final List list = new ArrayList();
		getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Criteria criteria = session.createCriteria(EtellerCheckapp.class);
				criteria.add(Expression.eq("checkflag", "1"));
				criteria.add(Expression.eq("changsql", sql));
				list.addAll(criteria.list());
				return null;
			}
		});
		if (list.size() == 0) {
			return null;
		}
		return (EtellerCheckapp) list.get(0);
	}

	public List<EtellerCheckapp> getEtellerCheckappByMap(Map<String, String> map, Pagination page) {
		Criteria criteria = getSession(true).createCriteria(EtellerCheckapp.class);
		if (map.get("serinalno") != null) {
			criteria.add(Restrictions.eq("serinalno", map.get("serinalno")));
		}
		if (map.get("checkflag") != null) {
			criteria.add(Restrictions.eq("checkflag", map.get("checkflag")));
		}
		if (map.get("changdate") != null) {
			criteria.add(Restrictions.eq("changdate", map.get("changdate")));
		}
		if (map.get("usrid") != null) {
			criteria.add(Restrictions.eq("usrid", map.get("usrid")));
		}
		if (map.get("tablename") != null) {
			criteria.add(Restrictions.eq("tablename", map.get("tablename")));
		}
		criteria.addOrder(Order.asc("serinalno"));
		page.setAllRecords(Integer.valueOf(criteria.list().size()));
		criteria.setFirstResult(page.getFirstRecord().intValue());
		criteria.setMaxResults(page.getPerPageRecords().intValue());

		return criteria.list();
	}
}

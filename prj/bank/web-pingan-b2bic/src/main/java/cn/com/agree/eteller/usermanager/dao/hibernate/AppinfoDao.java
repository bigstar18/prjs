// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi
// Source File Name: AppinfoDao.java

package cn.com.agree.eteller.usermanager.dao.hibernate;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import cn.com.agree.eteller.generic.utils.ComFunction;
import cn.com.agree.eteller.generic.utils.Pagination;
import cn.com.agree.eteller.usermanager.dao.IAppinfoDao;
import cn.com.agree.eteller.usermanager.persistence.Appinfo;
import cn.com.agree.eteller.usermanager.persistence.EtellerSubappinfo;
import cn.com.agree.eteller.usermanager.persistence.Funclist;
import cn.com.agree.eteller.usermanager.persistence.Rolelist;

public class AppinfoDao extends HibernateDaoSupport implements IAppinfoDao {

	public AppinfoDao() {
	}

	public Appinfo getAppinfo(String appid) {
		List list = getHibernateTemplate().find((new StringBuilder("FROM Appinfo WHERE appid='")).append(appid).append("'").toString());
		if (list == null || list.size() == 0)
			return null;
		else
			return (Appinfo) list.get(0);
	}

	public boolean addAppinfo(final Appinfo ca) {
		boolean isAdded = true;
		if (getAppinfo(ca.getAppid()) != null) {
			isAdded = false;
			return isAdded;
		}
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

	public boolean updateAppinfo(Appinfo ca) {
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

	public boolean deleteAppinfo(final Appinfo ca) {
		boolean isDeleted = false;
		try {
			getHibernateTemplate().delete(ca);
			getHibernateTemplate().execute(new HibernateCallback() {

				public Object doInHibernate(Session session) throws HibernateException, SQLException {
					List listsub = getHibernateTemplate().find("from EtellerSubappinfo f where f.comp_id.appid=?", ca.getAppid());
					for (Iterator iter = listsub.iterator(); iter.hasNext();) {
						EtellerSubappinfo subappinfo = (EtellerSubappinfo) iter.next();
						session.delete(subappinfo);
						List listfunc = getHibernateTemplate().find("from Funclist f where f.appid=?", subappinfo.getComp_id().getSubappid());
						Funclist fun;
						for (Iterator iter1 = listfunc.iterator(); iter1.hasNext(); session.delete(fun)) {
							fun = (Funclist) iter1.next();
							Set set = fun.getRoles();
							Rolelist element;
							for (Iterator iterator = set.iterator(); iterator.hasNext(); element.getFunctions().remove(fun))
								element = (Rolelist) iterator.next();

						}

					}

					List list = getHibernateTemplate().find("from Funclist f where f.appid=?", ca.getAppid());
					Funclist fun;
					for (Iterator iter = list.iterator(); iter.hasNext(); session.delete(fun)) {
						fun = (Funclist) iter.next();
						Set set = fun.getRoles();
						Rolelist element;
						for (Iterator iterator = set.iterator(); iterator.hasNext(); element.getFunctions().remove(fun))
							element = (Rolelist) iterator.next();

					}

					return null;
				}

			});
			isDeleted = true;
		} catch (Exception ex) {
			isDeleted = false;
			ex.printStackTrace();
			throw new RuntimeException(ex);
		}
		return isDeleted;
	}

	public Appinfo[] getAllAppinfo() {
		List ls = new ArrayList();
		try {
			ls = getHibernateTemplate().loadAll(Appinfo.class);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return (Appinfo[]) ls.toArray(new Appinfo[0]);
	}

	public EtellerSubappinfo[] getAllSubAppinfo() {
		List ls = new ArrayList();
		try {
			ls = getHibernateTemplate().loadAll(EtellerSubappinfo.class);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return (EtellerSubappinfo[]) ls.toArray(new EtellerSubappinfo[0]);
	}

	public int getMaxAppid() {
		List list = getHibernateTemplate().find("select max(a.appid) from Appinfo a where 1=1");
		String appid = (String) list.get(0);
		if (appid == null) {
			return 0;
		} else {
			int id = Integer.parseInt(appid);
			return ++id;
		}
	}

	public List getAppinfosByCondition(Appinfo appinfo, Pagination page) throws Exception {
		Criteria c = getSession().createCriteria(Appinfo.class);
		if (appinfo != null) {
			if (!ComFunction.isEmpty(appinfo.getAppid()))
				c.add(Restrictions.like("appid", appinfo.getAppid(), MatchMode.ANYWHERE));
			if (!ComFunction.isEmpty(appinfo.getAppname()))
				c.add(Restrictions.like("appname", appinfo.getAppname(), MatchMode.ANYWHERE));
		}
		if (page != null) {
			page.setAllRecords((Integer) c.setProjection(Projections.count("appid")).uniqueResult());
			return c.setProjection(null).setFirstResult(page.getFirstRecord().intValue()).setMaxResults(page.getPerPageRecords().intValue()).list();
		} else {
			return c.list();
		}
	}
}

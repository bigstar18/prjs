package gnnt.MEBS.common.dao.impl;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import gnnt.MEBS.common.util.query.OrderField;
import gnnt.MEBS.common.util.query.PageInfo;

public class DaoHelperImpl extends HibernateDaoSupport {
	public List queryBySQL(String paramString, String[] paramArrayOfString, Object[] paramArrayOfObject, PageInfo paramPageInfo) {
		if (paramArrayOfObject != null) {
			for (int i = 0; i < paramArrayOfObject.length; i++) {
				Object localObject = paramArrayOfObject[i];
				if ((localObject instanceof Date)) {
					paramArrayOfObject[i] = new Timestamp(((Date) localObject).getTime());
				}
				this.logger.debug("param[" + i + "]:" + paramArrayOfObject[i]);
			}
		}
		this.logger.debug("Query Start!");
		int i = 0;
		if (paramPageInfo != null) {
			i = Integer.valueOf(totalRow("select count(*) " + paramString, paramArrayOfString, paramArrayOfObject)).intValue();
		}
		int j = 0;
		int m;
		if (paramPageInfo != null) {
			j = (paramPageInfo.getPageNo() - 1) * paramPageInfo.getPageSize();
			int k = paramPageInfo.getPageSize() > 0 ? 1 : 0;
			m = i - (paramPageInfo.getPageNo() - 1) * paramPageInfo.getPageSize();
			if (paramPageInfo.getOrderFields() != null) {
				List localList = paramPageInfo.getOrderFields();
				String str1 = " order by ";
				for (int n = 0; n < localList.size(); n++) {
					OrderField localOrderField = (OrderField) localList.get(n);
					String str2 = localOrderField.getOrderField();
					boolean bool = localOrderField.isOrderDesc();
					if (bool) {
						str1 = str1 + str2 + " desc";
					} else {
						str1 = str1 + str2 + " asc";
					}
					if (n != localList.size() - 1) {
						str1 = str1 + ",";
					}
				}
				paramString = paramString + str1;
			}
		}
		int k = 100000;
		if (paramPageInfo != null) {
			k = paramPageInfo.getPageSize();
			if (k > 0) {
				if (i % k == 0) {
					m = i / k;
				} else if (i < k) {
					m = 1;
				} else {
					m = i / k + 1;
				}
			} else {
				m = 1;
			}
			paramPageInfo.setTotalRecords(i);
			paramPageInfo.setTotalPages(m);
		}
		return findAll(j, k, paramString, paramArrayOfString, paramArrayOfObject);
	}

	public List findAll(final int paramInt1, final int paramInt2, final String paramString, final String[] paramArrayOfString,
			final Object[] paramArrayOfObject) {
		return getHibernateTemplate().executeFind(new HibernateCallback() {
			public Object doInHibernate(Session paramAnonymousSession) throws HibernateException, SQLException {
				StringBuffer localStringBuffer = new StringBuffer(paramString);
				Query localQuery = paramAnonymousSession.createQuery(localStringBuffer.toString());
				if (paramArrayOfString != null) {
					for (int i = 0; i < paramArrayOfString.length; i++) {
						localQuery.setParameter(paramArrayOfString[i], paramArrayOfObject[i]);
					}
				}
				localQuery.setFirstResult(paramInt1);
				localQuery.setMaxResults(paramInt2);
				return localQuery.list();
			}
		});
	}

	public int totalRow(final String paramString, final String[] paramArrayOfString, final Object[] paramArrayOfObject) {
		return ((Integer) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session paramAnonymousSession) throws HibernateException, SQLException {
				StringBuffer localStringBuffer = new StringBuffer(paramString);
				Query localQuery = paramAnonymousSession.createQuery(localStringBuffer.toString());
				if (paramArrayOfString != null) {
					for (int i = 0; i < paramArrayOfString.length; i++) {
						localQuery.setParameter(paramArrayOfString[i], paramArrayOfObject[i]);
					}
				}
				Long localLong = (Long) localQuery.uniqueResult();
				return Integer.valueOf(localLong.intValue());
			}
		})).intValue();
	}
}
